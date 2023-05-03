package com.se1.systemservice.domain.service;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.messaging.MessagingException;
import org.springframework.stereotype.Service;

import com.se1.systemservice.domain.model.Mail;
import com.se1.systemservice.domain.payload.MailDetail;
import com.se1.systemservice.domain.payload.request.MailRequest;
import com.se1.systemservice.domain.repository.MailRepository;


@Service
public class MailService {
	@Autowired 
	private JavaMailSender javaMailSender;
	 
	@Autowired
	private MailRepository mailRepository;
	
    @Value("${spring.mail.username}") 
    private String sender;
	
	// Method 1
    // To send a simple email
    public String sendSimpleMail(MailRequest mailRequest)
    {
 
        // Try block to check for exceptions
        try {
 
        	Mail mail = mailRepository.findByMailTemplate(mailRequest.getMailTemplate());
        	
    		MimeMessage message = javaMailSender.createMimeMessage();
    		MimeMessageHelper helper = new MimeMessageHelper(message, false, "utf-8");
    		
    		helper.setTo(mailRequest.getTo());
    		helper.setSubject(mail.getSubject());
    				
        	
            
            String body = mail.getBody();
            
            List<String> listKey = new ArrayList<String>(mailRequest.getData().keySet());
            for(String key : listKey) {
            	String data = mailRequest.getData().get(key).toString();
            	body = body.replace(key, data);
            }
            body = body.replace("&lt;", "<");
            body = body.replace("&gt;", ">");
            body = body.replace("tdalign", "td align");
            body = body.replace("tableborder", "table border");
            body = body.replace("bodystyle", "body style");
            
            helper.setText(body, true);
            // Sending the mail
            javaMailSender.send(message);
            return "Mail Sent Successfully...";
        }
 
        // Catch block to handle the exceptions
        catch (Exception e) {
            return "Error while Sending Mail";
        }
    }
 
    // Method 2
    // To send an email with attachment
    public String
    sendMailWithAttachment(MailDetail details) throws javax.mail.MessagingException
    {
        // Creating a mime message
        MimeMessage mimeMessage
            = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper;
 
        try {
 
            // Setting multipart as true for attachments to
            // be send
            mimeMessageHelper
                = new MimeMessageHelper(mimeMessage, true);
            mimeMessageHelper.setFrom(sender);
            mimeMessageHelper.setTo(details.getRecipient());
            mimeMessageHelper.setText(details.getMsgBody());
            mimeMessageHelper.setSubject(
                details.getSubject());
 
            // Adding the attachment
            FileSystemResource file
                = new FileSystemResource(
                    new File(details.getAttachment()));
 
            mimeMessageHelper.addAttachment(
                file.getFilename(), file);
 
            // Sending the mail
            javaMailSender.send(mimeMessage);
            return "Mail sent Successfully";
        }
 
        // Catch block to handle MessagingException
        catch (MessagingException e) {
 
            // Display message when exception occurred
            return "Error while sending mail!!!";
        }
    }
}
