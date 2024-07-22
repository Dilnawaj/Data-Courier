package com.datacourier.email;

import java.io.File;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Properties;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.datacourier.entities.DataCourierConstant;
import com.datacourier.entities.FileData;
import com.datacourier.entities.UnsubscribeEmail;
import com.datacourier.entities.User;
import com.datacourier.model.UserDto;
import com.datacourier.model.UserType;
import com.datacourier.repo.FileDataRepo;
import com.datacourier.repo.UnsubscribeEmailRepo;
import com.datacourier.repo.UserRepo;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;

@Service
public class EmailService {

	@Value("${gmail}")
	private String from;

	@Value("${password.email}")
	private String password;

	@Value("${username.email}")
	private String username;
	@Value("${receiver.email}")
	private String receiverEmail;
	@Autowired
	private UserRepo userRepo;
	@Autowired
	private FileDataRepo fileDataRepo;
	@Autowired
	private UnsubscribeEmailRepo unsubscribeEmailRepo;

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private RedisTemplate<String, Object> redisTemplate;

	@Value("${logFilePath}")
	private String logFilePath;

	final static Logger logger = LoggerFactory.getLogger(EmailService.class);

	/**
	 * The function `sendEmailtoUser` sends email to User along with attachment
	 * files they provide.
	 *
	 * @param page  The `email` parameter is used to send mail to user over his
	 *              account.
	 * @param files The `files` parameter in the `sendEmailtoUser` method is the
	 *              attachment files user provide as a input to the Api.
	 */
	@Async
	public void sendEmailtoUser(String files, String email)
			throws UnsupportedEncodingException, JsonMappingException, JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
		JsonNode root = mapper.readTree(files);
		JsonNode filesNode = root.get("files");
		if (filesNode.isArray()) {
			HashMap<String, String> multifiles = new HashMap<>();
			for (JsonNode fileNode : filesNode) {
				multifiles.put(fileNode.get("fileName").asText(), fileNode.get("fileData").asText().split(",")[1]);
			}
			sendEmailtoUser(multifiles, email);
		}
	}

	@Async
	public void sendEmailtoUser(HashMap<String, String> multifiles, String email) throws UnsupportedEncodingException {

		List<String> fileNames = new ArrayList<>();
		List<byte[]> fileContents = new ArrayList<>();

		for (Map.Entry<String, String> entry : multifiles.entrySet()) {
			String fileName = entry.getKey();
			String fileData = entry.getValue();
			fileContents.add(Base64.getDecoder().decode(fileData));
			fileNames.add(fileName);
		}

		String cacheKey = DataCourierConstant.USER_EMAIL + DataCourierConstant.DASH + email;

		// Check if availability information is cache
		String hasEmail = (String) redisTemplate.opsForValue().get(cacheKey);

		if (hasEmail != null) {
			return;
		}
		if (isUserUnsubscribe(email)) {
			redisTemplate.opsForValue().set(cacheKey, email, 60, TimeUnit.MINUTES);
			return;
		}

		User user = new User(extractNameFromEmail(email), email, UserType.NORMAL_USER.toString());
		userRepo.save(user);
		List<FileData> fileDataList = fileNames.stream()
				.map(fileName -> new FileData(user.getUserId(), fileName, getFileType(fileName)))
				.collect(Collectors.toList());
		fileDataRepo.saveAll(fileDataList);
		sendEmail(email, user.getUserName(), fileNames, fileContents);
	}

	private boolean isUserUnsubscribe(String email) {
		Optional<UnsubscribeEmail> unsubscribeEmail = unsubscribeEmailRepo.findByEmail(email);
		if (unsubscribeEmail.isPresent()) {
			return true;
		}
		return false;
	}

	@Async
	private String getFileType(String fileName) {
		if (fileName == null || fileName.isEmpty()) {
			return null;
		}
		int dotIndex = fileName.lastIndexOf('.');
		if (dotIndex == -1 || dotIndex == fileName.length() - 1) {

			return null;
		}

		String extension = fileName.substring(dotIndex + 1).toLowerCase();

		switch (extension) {
		case "pdf":
			return "PDF Document";
		case "doc":
		case "docx":
			return "Microsoft Word Document";
		case "txt":
			return "Text File";
		case "zip":
			return "ZIP Archive";
		case "rtf":
			return "Rich Text Format";
		default:
			return "Unknown File Type";
		}
	}

	@Async
	private void sendEmail(String email, String userName, List<String> fileNames, List<byte[]> fileContents)
			throws UnsupportedEncodingException {
		Properties props = new Properties();
		props.put("mail.smtp.host", "smtp.gmail.com"); // SMTP Host
		props.put("mail.smtp.port", "587"); // TLS Port
		props.put("mail.smtp.auth", "true"); // enable authentication
		props.put("mail.smtp.starttls.enable", "true"); // enable STARTTLS

		// create Authenticator object to pass in Session.getInstance argument
		Authenticator auth = new Authenticator() {
			// override the getPasswordAuthentication method
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(from, password);
			}
		};
		Session session = Session.getInstance(props, auth);
		// replace with actual user name
		String unsubscribeLink = DataCourierConstant.UI_URL + "unsubscribe?email=" + email;

		String companyName = "Data Courier";

		EmailUtil.sendAttachmentEmail(session, email, "Your Uploaded Files Are Ready - Please Review",
				getBody(userName, unsubscribeLink, companyName), fileNames, fileContents);

	}

	@Async
	private void sendEmail(String email, String userName) throws UnsupportedEncodingException {
		Properties props = new Properties();
		props.put("mail.smtp.host", "smtp.gmail.com"); // SMTP Host
		props.put("mail.smtp.port", "587"); // TLS Port
		props.put("mail.smtp.auth", "true"); // enable authentication
		props.put("mail.smtp.starttls.enable", "true"); // enable STARTTLS

		// create Authenticator object to pass in Session.getInstance argument
		Authenticator auth = new Authenticator() {
			// override the getPasswordAuthentication method
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(from, password);
			}
		};
		Session session = Session.getInstance(props, auth);
		// replace with actual user name
		String unsubscribeLink = DataCourierConstant.UI_URL + "unsubscribe?email=" + email;

		EmailUtil.sendAttachmentEmail(session, email, "BombitUp Email", getBody(unsubscribeLink));

	}

	private String getBody(String unsubscribeLink) {
		String[] greetings = { "Hi", "Hello", "Hey" };
		String[] services = { "service", "platform", "tool" };
		String[] endings = { "Thank you for your attention.", "We hope this information is useful to you.",
				"Let us know if you have any questions." };

		Random random = new Random();
		String greeting = greetings[random.nextInt(greetings.length)];
		String service = services[random.nextInt(services.length)];
		String ending = endings[random.nextInt(endings.length)];

		return "<div style=\"max-width:550px; padding:18px; border:1px solid #dadada; -webkit-border-radius:10px; -moz-border-radius:10px; border-radius:10px; font-family:Arial, Helvetica, sans-serif; font-size:15px; color:#495057;\">"
				+ "<p style=\"font-size:17px; font-weight:bold;\">" + greeting + ",</p>" + "<p>Thank you for using our "
				+ service + ". Attached are the files you uploaded to our website.</p>"
				+ "<p>If you prefer not to receive future emails from us, please <a href=\"" + unsubscribeLink
				+ "\">unsubscribe here</a>.</p>" + "<p>" + ending + "</p>" + "<p>Best regards,<br>Data Courier</p>"
				+ "</div>";
	}

	private String getBody(String userName, String unsubscribeLink, String companyName) {
		return "<div style=\"max-width:550px; padding:18px; border:1px solid #dadada; -webkit-border-radius:10px; -moz-border-radius:10px; border-radius:10px; font-family:Arial, Helvetica, sans-serif; font-size:15px; color:#495057;\">"
				+ "<p style=\"font-size:17px; font-weight:bold;\">Dear " + userName + ",</p>"
				+ "<p>Thank you for using our service. Attached are the files you uploaded to our website.</p>"
				+ "<p>If you prefer not to receive future emails from us, please <a href=\"" + unsubscribeLink
				+ "\">unsubscribe here</a>.</p>" + "<p>Best regards,<br>" + companyName + "</p>" + "</div>";
	}

	@Async
	public static String extractNameFromEmail(String emailAddress) {
		try {
			InternetAddress internetAddress = new InternetAddress(emailAddress);
			String personal = internetAddress.getPersonal();
			if (personal != null && !personal.isEmpty()) {
				return personal;
			} else {
				// If personal name is not available, return the username part before '@'
				String username = emailAddress.substring(0, emailAddress.indexOf('@'));
				// Convert the first letter to uppercase and the rest to lowercase
				return username.substring(0, 1).toUpperCase() + username.substring(1).toLowerCase();
			}
		} catch (Exception e) {
			logger.error("extractNameFromEmail::", e);
		}
		return "";
	}

	/**
	 * The function `getUsers` sends email to User along with attachment files they
	 * provide.
	 *
	 * @param page The function returns a page of User entities from the User
	 *             repository based on the specified page number and size.
	 * @param size The function returns a page of User entities from the User
	 *             repository based on the specified page number and size.
	 * @return A List of User entities from the userRepo, with the specified size.
	 */
	public List<UserDto> getUsers(int page, int size) {
		List<User> users = userRepo.findAll(Pageable.ofSize(size)).getContent();
		List<UserDto> userDto = users.stream().map(user -> modelMapper.map(user, UserDto.class))
				.collect(Collectors.toList());
		userDto.stream().forEach(user -> {
			user.setFiles(fileDataRepo.findByUserId(user.getUserId()));
		});
		return userDto;
	}

	/**
	 * The function `sendEmailtoUser` sends email to User along with attachment
	 * files they provide.
	 *
	 * @param page The `email` parameter is used to send mail to user over his
	 *             account. return A String of success message.
	 */
	public String unsubscribeUser(String email) {
		UnsubscribeEmail unsubscribeEmail = new UnsubscribeEmail(email);
		try {
			unsubscribeEmailRepo.save(unsubscribeEmail);
		} catch (Exception e) {
			logger.error("unsubscribeUser::", e);
		}
		return "Unsubscribe Successful";
	}

	/**
	 * The function `getResource` get image data from the folder and convert into
	 * input stream.
	 *
	 * @param imageName This tells image path where image data present.
	 */
	public InputStream getResource(String path, String fileName) throws FileNotFoundException {
		String fullPath = path + File.separator + fileName;
		return new FileInputStream(fullPath);

	}

	/**
	 * The function `sendMultipleEmailToUser` sends email to third person according
	 * to count user provide
	 *
	 */
	@Async
	public void sendMultipleEmailToUser(String email, Integer count, Long id) throws UnsupportedEncodingException {
		User user = new User(extractNameFromEmail(email), email, UserType.BOMBIT.toString());
		user = userRepo.save(user);
		for (int i = 0; i < count; i++) {
			FileData fileData = new FileData();
			fileData.setFileNames(email);
			fileData.setFileStatus(false);
			fileData.setReqId(id);
			fileData.setUserId(user.getUserId());
			fileDataRepo.save(fileData);
		}
		processMultipleEmails(id, email);
	}

	public long getId() {
		try {
			Random random = SecureRandom.getInstanceStrong();
			return (random.nextLong() % 100000000000000L) + 5200000000000000L;
		} catch (Exception e) {
			logger.error("getId", e);
		}
		return 0l;

	}

	@Async
	public void processMultipleEmails(Long id, String email) throws UnsupportedEncodingException {
		List<FileData> files = fileDataRepo.findByReqId(id);
		for (FileData file : files) {
			sendEmail(email, extractNameFromEmail(email));
			file.setFileStatus(true);
			fileDataRepo.save(file);
		}

	}

	/**
	 * The function `getCountList` gives count of the files which are processed in
	 * our database
	 *
	 * @return it return count of processed files.
	 */
	public Integer getCountList(Long id) {
		return fileDataRepo.getCount(id);
	}

	/**
	 * The function `sendLogFileInEmail` send log data to email address of the user
	 *
	 * @return it return success if it executes properly else invalid request.
	 */
	public ResponseEntity<String> sendLogFileInEmail() {
		try {
			String logFilePathNew = "";

			String fileName = DataCourierConstant.LOG_FILE_NAME;
			logFilePathNew = logFilePath + DataCourierConstant.LOG_FILE_NAME;

			byte[] fileData = Files.readAllBytes(Paths.get(logFilePathNew));
			List<String> fileNames = new ArrayList<>();
			List<byte[]> fileContents = new ArrayList<>();
			fileNames.add(fileName);
			fileContents.add(fileData);
			sendEmail(receiverEmail, "Dilnawaj", fileNames, fileContents);
			return new ResponseEntity<>("Success", HttpStatus.OK);
		} catch (Exception e) {
			logger.error("sendLogFileInEmail", e);
			return new ResponseEntity<>("Invalid request", HttpStatus.BAD_REQUEST);
		}

	}

}
