package com.se1.systemservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.se1.systemservice.domain.payload.request.MailRequest;
import com.se1.systemservice.domain.service.MailService;

@RestController
@RequestMapping("/system/internal/send-mail")
public class MailController {
	@Autowired private MailService emailService;
	 
    @PostMapping
    public void sendMail(@RequestBody MailRequest mailRequest)
    {
        emailService.sendSimpleMail(mailRequest);
 
    }
 
}
