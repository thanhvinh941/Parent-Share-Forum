package com.se1.systemservice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.se1.systemservice.model.UserContactDto;
import com.se1.systemservice.service.UserContactService;

@RestController
public class UserContactController {
	
	@Autowired
	UserContactService contactService;
	
	@GetMapping("/system/user-contact/{id}")
	public @ResponseBody UserContactDto getUserContactById(@PathVariable("id") Integer userId){
		UserContactDto userContactDto = contactService.getUserContactById(userId);
		return userContactDto;
	}
	
	@GetMapping("/system/user-contact-list/{id}")
	public @ResponseBody List<UserContactDto>  getUserContacListtById(@PathVariable("id") Integer userId){
		List<UserContactDto> userContactDto = contactService.getUserContactListById(userId);
		return userContactDto;
	}

}
