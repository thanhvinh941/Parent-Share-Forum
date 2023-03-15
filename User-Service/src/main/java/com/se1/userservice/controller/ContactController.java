package com.se1.userservice.controller;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.se1.userservice.domain.common.SCMConstant;
import com.se1.userservice.model.Contact;
import com.se1.userservice.payload.ApiResponseEntity;
import com.se1.userservice.payload.UserDetail;
import com.se1.userservice.service.ContactService;

@RestController
@RequestMapping("/users/internal/contact")
public class ContactController {

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private ContactService contactService;

	@Autowired
	private ApiResponseEntity apiResponseEntity;

	@PostMapping("/create")
	public ResponseEntity<?> createContact(@RequestParam("reciver_id") long userReciverId,
			@RequestParam("action") String action, @RequestHeader("user_detail") String userDetailHeader) {
		UserDetail userDetail;
		try {
			int status = 0;
			switch (action) {
			case SCMConstant.CONTACT_REQUEST:
				status = 1;
				break;
			default:
				break;
			}
			
			userDetail = objectMapper.readValue(userDetailHeader, UserDetail.class);
			Contact contactCreate = new Contact();
			contactCreate.setStatus(status);
			contactCreate.setTopicId(UUID.randomUUID().toString());
			contactCreate.setUserReciverId(userReciverId);
			contactCreate.setUserSenderId(userDetail.getId());
			contactCreate.setCreateAt(new Date());

			contactService.processCreate(contactCreate, apiResponseEntity);
		} catch (Exception e) {
			apiResponseEntity.setData(null);
			apiResponseEntity.setErrorList(List.of(e.getMessage()));
			apiResponseEntity.setStatus(0);
		}

		return ResponseEntity.ok().body(apiResponseEntity);
	}

	@PostMapping("/update")
	public ResponseEntity<?> updateContact(@RequestParam("id") long userId, @RequestParam("action") String action,
			@RequestHeader("user_detail") String userDetailHeader) {
		// 0: unfriend, 1:request, 2:friend
		UserDetail userDetail;
		try {
			userDetail = objectMapper.readValue(userDetailHeader, UserDetail.class);

			int statusUpdate = 0;
			switch (action) {
			case SCMConstant.CONTACT_FRIEND:
				statusUpdate = 2;
				break;
			case SCMConstant.CONTACT_REQUEST:
				statusUpdate = 1;
				break;
			default:
				break;
			}

			contactService.processUpdate(userId, userDetail.getId(), statusUpdate, apiResponseEntity);
		} catch (Exception e) {
			apiResponseEntity.setData(null);
			apiResponseEntity.setErrorList(List.of(e.getMessage()));
			apiResponseEntity.setStatus(0);
		}

		return ResponseEntity.ok().body(apiResponseEntity);
	}
}
