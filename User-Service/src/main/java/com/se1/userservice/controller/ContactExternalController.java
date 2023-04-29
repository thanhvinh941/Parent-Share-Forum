package com.se1.userservice.controller;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.se1.userservice.domain.common.SCMConstant;
import com.se1.userservice.domain.model.Contact;
import com.se1.userservice.domain.payload.ApiResponseEntity;
import com.se1.userservice.domain.payload.UserDetail;
import com.se1.userservice.domain.service.ContactService;

@RestController
@RequestMapping("/contact/external")
public class ContactExternalController {

	public ContactExternalController(ObjectMapper objectMapper, ContactService contactService,
			ApiResponseEntity apiResponseEntity) {
		super();
		this.objectMapper = objectMapper;
		this.contactService = contactService;
		this.apiResponseEntity = apiResponseEntity;
	}

	private final ObjectMapper objectMapper;
	private final ContactService contactService;
	private final ApiResponseEntity apiResponseEntity;

	@PostMapping("/create")
	public ResponseEntity<?> createContact(@RequestParam("reciver_id") long userReciverId,
			@RequestParam(name = "action", required = false) String action,
			@RequestHeader("user_detail") String userDetailHeader) {
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
	public ResponseEntity<?> updateContact(@RequestParam("user_id") long userId,
			@RequestParam(name = "action", required = false) String action,
			@RequestHeader("user_detail") String userDetailHeader) {
		UserDetail userDetail;
		try {
			userDetail = objectMapper.readValue(userDetailHeader, UserDetail.class);

			int statusUpdate = 0;
			long userReciverId = 0;
			long userSenderId = 0;
			switch (action) {
			case SCMConstant.CONTACT_FRIEND:
				statusUpdate = 2;
				userReciverId = userDetail.getId();
				userSenderId = userId;
				break;
			case SCMConstant.CONTACT_UNFRIEND:
				statusUpdate = 0;
				userReciverId = userDetail.getId();
				userSenderId = userId;
				break;
			default:
				break;
			}

			contactService.processUpdate(userReciverId, userSenderId, statusUpdate, apiResponseEntity);
		} catch (Exception e) {
			apiResponseEntity.setData(null);
			apiResponseEntity.setErrorList(List.of(e.getMessage()));
			apiResponseEntity.setStatus(0);
		}

		return ResponseEntity.ok().body(apiResponseEntity);
	}

	@PostMapping("/getListFriendChat")
	public ResponseEntity<?> getListFriendChat(@RequestHeader("user_detail") String userDetailHeader) {
		UserDetail userDetail;
		try {
			userDetail = objectMapper.readValue(userDetailHeader, UserDetail.class);

			return ResponseEntity.ok().body(contactService.processGetListContactForChat(userDetail, apiResponseEntity));
		} catch (Exception e) {
			apiResponseEntity.setData(null);
			apiResponseEntity.setErrorList(List.of(e.getMessage()));
			apiResponseEntity.setStatus(0);
		}

		return ResponseEntity.ok().body(apiResponseEntity);
	}
	
	@PostMapping("/getListFriend")
	public ResponseEntity<?> getListFriend(@RequestHeader("user_detail") String userDetailHeader) {
		UserDetail userDetail;
		try {
			userDetail = objectMapper.readValue(userDetailHeader, UserDetail.class);

			return ResponseEntity.ok().body(contactService.processGetListContact(userDetail, apiResponseEntity));
		} catch (Exception e) {
			apiResponseEntity.setData(null);
			apiResponseEntity.setErrorList(List.of(e.getMessage()));
			apiResponseEntity.setStatus(0);
		}

		return ResponseEntity.ok().body(apiResponseEntity);
	}

	@PostMapping("/getListContactRequest")
	public ResponseEntity<?> getContactRequest(@RequestHeader("user_detail") String userDetailHeader) {
		UserDetail userDetail;
		try {
			userDetail = objectMapper.readValue(userDetailHeader, UserDetail.class);

			contactService.processGetContactRequest(userDetail, apiResponseEntity);
		} catch (Exception e) {
			apiResponseEntity.setData(null);
			apiResponseEntity.setErrorList(List.of(e.getMessage()));
			apiResponseEntity.setStatus(0);
		}

		return ResponseEntity.ok().body(apiResponseEntity);
	}
}
