package com.se1.userservice.controller.contact;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.se1.userservice.domain.payload.ApiResponseEntity;
import com.se1.userservice.domain.payload.UserDetail;
import com.se1.userservice.domain.service.ContactServiceImpl;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/contact/external")
@RequiredArgsConstructor
public class GetListFriendController {
	private final ObjectMapper objectMapper;
	private final ContactServiceImpl contactService;
	
	@PostMapping("/getListFriend")
	public ResponseEntity<?> getListFriend(@RequestHeader("user_detail") String userDetailHeader) {
		ApiResponseEntity apiResponseEntity = new ApiResponseEntity();
		UserDetail userDetail;
		try {
			userDetail = objectMapper.readValue(userDetailHeader, UserDetail.class);
			Object response = contactService.processGetListContact(userDetail);
			apiResponseEntity.setData(response);
			apiResponseEntity.setErrorList(null);
			apiResponseEntity.setStatus(1);
		} catch (Exception e) {
			apiResponseEntity.setData(null);
			apiResponseEntity.setErrorList(List.of(e.getMessage()));
			apiResponseEntity.setStatus(0);
		}

		return ResponseEntity.ok().body(apiResponseEntity);
	}
}
