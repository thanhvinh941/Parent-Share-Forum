package com.se1.systemservice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.se1.systemservice.model.UserContactDto;
import com.se1.systemservice.service.UserContactService;

@Controller
@RequestMapping("/system")
public class SystemController {

	@Autowired
	UserContactService contactService;
	
	@GetMapping("/websocket-test")
	public String viewWebsocketTest( Model model) throws JsonMappingException, JsonProcessingException {
		List<UserContactDto> list = contactService.getContactDto();
		
		model.addAttribute("userContacts", list);
		return "index";
	}
}
