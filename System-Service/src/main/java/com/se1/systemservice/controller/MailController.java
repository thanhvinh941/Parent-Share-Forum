package com.se1.systemservice.controller;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.se1.systemservice.model.MailDetail;
import com.se1.systemservice.service.MailService;

@RestController
public class MailController {
	@Autowired private MailService emailService;
	 
    // Sending a simple Email
    @PostMapping("/sendMail")
    public String
    sendMail(@RequestBody MailDetail details)
    {
        String status
            = emailService.sendSimpleMail(details);
 
        return status;
    }
 
    // Sending email with attachment
    @PostMapping("/sendMailWithAttachment")
    public String sendMailWithAttachment(
        @RequestBody MailDetail details) throws MessagingException
    {
        String status
            = emailService.sendMailWithAttachment(details);
 
        return status;
    }
}
