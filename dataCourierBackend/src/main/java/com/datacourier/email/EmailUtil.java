package com.datacourier.email;
import java.io.UnsupportedEncodingException;

import java.util.Date;
import java.util.List;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.util.ByteArrayDataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;

public class EmailUtil {
	
	final static Logger logger = LoggerFactory.getLogger(EmailUtil.class);
	/**
	 * Utility method to send simple HTML email
	 * @param session
	 * @param toEmail
	 * @param subject
	 * @param body
	 */
	/**
	 * Utility method to send email with attachment
	 * @param session
	 * @param toEmail
	 * @param subject
	 * @param body
	 * @throws UnsupportedEncodingException 
	 */
	@Async
	 public static void sendAttachmentEmail(Session session, String toEmail, String subject, String htmlContent, List<String> fileNames, List<byte[]> fileContents) throws UnsupportedEncodingException {
        try {
            MimeMessage msg = new MimeMessage(session);
            msg.addHeader("Content-type", "text/HTML; charset=UTF-8");
            msg.addHeader("format", "flowed");
            msg.addHeader("Content-Transfer-Encoding", "8bit");

            msg.setFrom(new InternetAddress("it.1703302@gmail.com", "Data Courier"));

            msg.setReplyTo(InternetAddress.parse("it.1703302@gmail.com ", false));

            msg.setSubject(subject, "UTF-8");

            msg.setSentDate(new Date());

            msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail, false));

            // Create the HTML message body part
            MimeBodyPart messageBodyPart = new MimeBodyPart();
            messageBodyPart.setContent(htmlContent, "text/html; charset=UTF-8");

            // Create a multipart message for attachment
            Multipart multipart = new MimeMultipart();

            // Set HTML message part
            multipart.addBodyPart(messageBodyPart);

            // Add attachments
            for (int i = 0; i < fileNames.size(); i++) {
                String filename = fileNames.get(i);
                byte[] content = fileContents.get(i);

                // Create new body part for each attachment
                MimeBodyPart attachmentBodyPart = new MimeBodyPart();

                // Set the data handler for the body part
                DataSource source = new ByteArrayDataSource(content, "application/octet-stream");
                attachmentBodyPart.setDataHandler(new DataHandler(source));
                attachmentBodyPart.setFileName(filename);

                // Add the attachment body part to the multipart
                multipart.addBodyPart(attachmentBodyPart);
            }

            // Set content of the message
            msg.setContent(multipart);

            // Send message
            Transport.send(msg);
        } catch (MessagingException e) {
        	logger.error("sendAttachmentEmail::",e);
        }
    }
	@Async
	 public static void sendAttachmentEmail(Session session, String toEmail, String subject, String htmlContent) throws UnsupportedEncodingException {
       try {
           MimeMessage msg = new MimeMessage(session);
           msg.addHeader("Content-type", "text/HTML; charset=UTF-8");
           msg.addHeader("format", "flowed");
           msg.addHeader("Content-Transfer-Encoding", "8bit");

           msg.setFrom(new InternetAddress("it.1703302@gmail.com", "Data Courier"));

           msg.setReplyTo(InternetAddress.parse("it.1703302@gmail.com ", false));

           msg.setSubject(subject, "UTF-8");

           msg.setSentDate(new Date());

           msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail, false));

           // Create the HTML message body part
           MimeBodyPart messageBodyPart = new MimeBodyPart();
           messageBodyPart.setContent(htmlContent, "text/html; charset=UTF-8");

           // Create a multipart message for attachment
           Multipart multipart = new MimeMultipart();

           // Set HTML message part
           multipart.addBodyPart(messageBodyPart);

      

           // Set content of the message
           msg.setContent(multipart);

           // Send message
           Transport.send(msg);
       } catch (MessagingException e) {
       	logger.error("sendAttachmentEmail::",e);
       }
   }

	
}
