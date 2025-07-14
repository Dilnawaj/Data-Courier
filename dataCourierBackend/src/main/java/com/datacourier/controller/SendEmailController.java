package com.datacourier.controller;

import java.io.FileNotFoundException;
import java.io.IOException;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.datacourier.email.EmailService;
import com.datacourier.email.EmailUtil;
import com.datacourier.model.UserDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;

/**
 * @author Dilnawaj
 *         <h2>This class contains the method related to Data courier to user
 *         and other functionality</h2>
 */
@Tag(name = "Data Courier")
@CrossOrigin
@RestController
public class SendEmailController {

	@Autowired
	private EmailService smtpEmailService;

	@Value("${project.image}")
	private String path;

	@Value("${logFileDownloadKey}")
	private String logFileDownloadKey;

	final static Logger logger = LoggerFactory.getLogger(EmailUtil.class);

	/**
	 * This function is used to send email to user with files.
	 *
	 * @param email User email to sent mail on it,
	 * @return The method `sendEmailToUser` is returning a `ResponseEntity` String
	 *         with a status of `HttpStatus.OK` and the body containing String
	 *         success message of the Api method call.
	 */
	@PostMapping(value = "/sendemail")
	@Operation(summary = "This API is used for send email to user.", description = "Send Email")
	public ResponseEntity<String> sendEmailToUser(@RequestBody String files, @RequestParam String email)
			throws JsonMappingException, JsonProcessingException, UnsupportedEncodingException {
		smtpEmailService.sendEmailtoUser(files, email);
		return ResponseEntity.ok("Email send Successfully");
	}

	/**
	 * This function is used to get All the User present in our project.
	 *
	 * @param page page number of the data set,
	 * @param size size of the page.,
	 * @return The method `getUsers` is returning a `ResponseEntity` List data of
	 *         the User with a status of `HttpStatus.OK` and the body containing
	 *         Users data success message of the Api method call.
	 */
	@GetMapping("/users")
	@Operation(summary = "This API is used for get all the Users present in our Application.", description = "All Users")
	public ResponseEntity<List<UserDto>> getUsers(@RequestParam int page, @RequestParam int size) {
		return ResponseEntity.ok(smtpEmailService.getUsers(page, size));
	}

	/**
	 * This function is used to send email to user with files.
	 *
	 * @param email User email to Un-subscribe it,
	 * @return The method `unsubscribeUser` is returning a `ResponseEntity` String
	 *         with a status of `HttpStatus.OK` and the body containing String
	 *         success message of the Api method call.
	 */
	@GetMapping("/unsubscribe")
	@Operation(summary = "This API is used for Unsubscribe to user.", description = "unsubscribe User")
	public ResponseEntity<String> unsubscribeUser(@RequestParam String email) {
		return ResponseEntity.ok(smtpEmailService.unsubscribeUser(email));
	}

	/**
	 * This function is used to send Multiple email to user according to count.
	 *
	 * @param email Receiver email whom user wants to send email,
	 * @return The method returns requestId of the specific data set.
	 */
	@GetMapping("/bombit/email")
	@Operation(summary = "This API is used for send  multiple email to receiver according to count user provides.", description = "Send Multiple Email")
	public ResponseEntity<Long> sendMultipleEmailToUser(@RequestParam String email, @RequestParam Integer count)
			throws UnsupportedEncodingException {
		Long id = smtpEmailService.getId();
		smtpEmailService.sendMultipleEmailToUser(email, count, id);
		return new ResponseEntity<>(id, HttpStatus.OK);
	}

	/**
	 * This function is used to get status of emails how many files has processed
	 *
	 * @param id requestId used to get specific data from the data set,
	 * @return It return Integer values how many many files has been processed.
	 */
	@GetMapping("/bombit/email/status")
	@Operation(summary = "This API is used to get count pf files which are processed.", description = "Count Multiple Email")
	public ResponseEntity<Integer> getCountList(@RequestParam(value = "id") Long id) {
		return new ResponseEntity<>(smtpEmailService.getCountList(id), HttpStatus.OK);
	}

	/**
	 * This function is used to get background image from the folder and display to
	 * the screen.
	 *
	 * @param imageName it takes images name as input.
	 */
	@GetMapping(value = "/image/{imageName}", produces = MediaType.IMAGE_JPEG_VALUE)
	@Operation(summary = "This API is used to set image on the server.", description = "Background Image")
	public void downloadImage(@PathVariable("imageName") String imageName, HttpServletResponse response) throws IOException {
		logger.info("Fetching image: {}", imageName);

		// Adjust if you support only png or jpg
		String resourcePath = "images/" + imageName;

		InputStream resource = getClass().getClassLoader().getResourceAsStream(resourcePath);

		if (resource == null) {
			throw new FileNotFoundException("Could not find " + resourcePath + " in classpath");
		}

		// Optionally detect content type dynamically instead of always IMAGE_JPEG_VALUE
		// response.setContentType(Files.probeContentType(Paths.get(imageName)));
		response.setContentType(MediaType.IMAGE_JPEG_VALUE);
		StreamUtils.copy(resource, response.getOutputStream());
	}


	/**
	 * This function is used to logs server status.
	 *
	 * @param key User have key to access this api,
	 * @return The method `emailLogFile` is returning a `ResponseEntity` String with
	 *         a status of `HttpStatus.OK` and the body containing String success
	 *         message of the Api method call.
	 */
	@GetMapping("/account/logfile/process/sendEmail")
	@Operation(summary = "This API is used to get logs of server.", description = "Server logs")
	public ResponseEntity<String> emailLogFile(@RequestParam String key) {
		if (logFileDownloadKey.equals(key)) {
			return smtpEmailService.sendLogFileInEmail();
		} else {
			return new ResponseEntity<>("Invalid request", HttpStatus.BAD_REQUEST);
		}
	}

	/**
	 * This function is used to send Multiple message to user according to count.
	 *
	 * @param email Receiver message whom user wants to send message,
	 * @return The method returns requestId of the specific data set.
	 */
	@GetMapping("/bombit/message")
	@Operation(summary = "This API is used for send  multiple email to receiver according to count user provides.", description = "Send Multiple Email")
	public ResponseEntity<Long> sendMultipleMultipleToUser(@RequestParam String number, @RequestParam Integer count)
			throws UnsupportedEncodingException {
		Long id = smtpEmailService.getId();
		smtpEmailService.sendMultipleMessageToUser(number, count, id);
		return new ResponseEntity<>(id, HttpStatus.OK);
	}

	/**
	 * This function is used to get status of messages how many files has processed
	 *
	 * @param id requestId used to get specific data from the data set,
	 * @return It return Integer values how many messages has been processed.
	 */
	@GetMapping("/bombit/sms/status")
	@Operation(summary = "This API is used to get count pf files which are processed.", description = "Count Multiple Email")
	public ResponseEntity<Integer> getCountListOfMessage(@RequestParam(value = "id") Long id) {
		return new ResponseEntity<>(smtpEmailService.getCountListOfMessage(id), HttpStatus.OK);
	}

}
