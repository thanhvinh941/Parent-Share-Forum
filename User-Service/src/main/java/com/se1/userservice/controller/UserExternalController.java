package com.se1.userservice.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
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
@RequestMapping("/user/external")
@RequiredArgsConstructor
public class UserExternalController {

	private final UserService service;
	private final ApiResponseEntity apiResponseEntity;
	private final ObjectMapper objectMapper;

	@PostMapping("/findById")
	public ResponseEntity<?> findById(@RequestHeader("user_detail") String userDetailHeader,
			@RequestParam("id") Long id) throws Exception {

		UserDetail userDetail = objectMapper.readValue(userDetailHeader, UserDetail.class);

		try {
			service.processFindUserById(userDetail.getId(), id, apiResponseEntity);
		} catch (Exception e) {
			apiResponseEntity.setData(null);
			apiResponseEntity.setErrorList(List.of(e.getMessage()));
			apiResponseEntity.setStatus(0);
		}
		return ResponseEntity.ok().body(apiResponseEntity);
	}

	@PostMapping("/findByName")
	public ResponseEntity<?> findByName(@RequestParam("name") String name, @RequestParam("offset") Integer offset,
			@RequestHeader("user_detail") String userDetailHeader)
			throws JsonMappingException, JsonProcessingException {

		UserDetail userDetail = objectMapper.readValue(userDetailHeader, UserDetail.class);

		try {
			service.processFindByName(userDetail.getId(), name.trim(), apiResponseEntity, offset);
		} catch (Exception e) {
			apiResponseEntity.setData(null);
			apiResponseEntity.setErrorList(List.of(e.getMessage()));
			apiResponseEntity.setStatus(0);
		}
		return ResponseEntity.ok().body(apiResponseEntity);
	}

	@PostMapping("/findAllExpert")
	public ResponseEntity<?> findAllExpert(@RequestHeader("user_detail") String userDetailHeader,
			@RequestParam("offset") Integer offset) throws JsonMappingException, JsonProcessingException {
		UserDetail userDetail = objectMapper.readValue(userDetailHeader, UserDetail.class);
		try {
			service.findAllExpert(userDetail, offset, apiResponseEntity);
		} catch (Exception e) {
			apiResponseEntity.setData(null);
			apiResponseEntity.setErrorList(List.of(e.getMessage()));
			apiResponseEntity.setStatus(0);
		}
		return ResponseEntity.ok().body(apiResponseEntity);
	}

	@PostMapping("/report/{id}")
	public ResponseEntity<?> report(@PathVariable("id") Long id, @RequestHeader("user_detail") String userDetailHeader)
			throws JsonMappingException, JsonProcessingException {
		UserDetail userDetail = objectMapper.readValue(userDetailHeader, UserDetail.class);

		try {
			service.report(id, userDetail, apiResponseEntity);
		} catch (Exception e) {
			apiResponseEntity.setData(null);
			apiResponseEntity.setErrorList(List.of(e.getMessage()));
			apiResponseEntity.setStatus(0);
		}
		return ResponseEntity.ok().body(apiResponseEntity);
	}
}
