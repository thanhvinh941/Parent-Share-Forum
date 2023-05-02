package com.se1.userservice.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.se1.userservice.domain.payload.ApiResponseEntity;
import com.se1.userservice.domain.payload.UserDetail;
import com.se1.userservice.domain.service.UserService;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserNoAuthenController {
	
	private final UserService service;
	private final ApiResponseEntity apiResponseEntity;
	
	@PostMapping("/findAllExpert")
	public ResponseEntity<?> findAllExpert(
			@RequestParam("offset") Integer offset) throws JsonMappingException, JsonProcessingException {
		UserDetail userDetail = new UserDetail();
		userDetail.setId(new Long(0));
		try {
			service.findAllExpert(userDetail, offset, apiResponseEntity);
		} catch (Exception e) {
			apiResponseEntity.setData(null);
			apiResponseEntity.setErrorList(List.of(e.getMessage()));
			apiResponseEntity.setStatus(0);
		}
		return ResponseEntity.ok().body(apiResponseEntity);
	}
}
