package com.se1.userservice.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.se1.userservice.domain.model.FindAllUserRequest;
import com.se1.userservice.domain.payload.ApiResponseEntity;
import com.se1.userservice.domain.payload.FindRequest;
import com.se1.userservice.domain.payload.UserDetail;
import com.se1.userservice.domain.payload.request.CreateUserRequest;
import com.se1.userservice.domain.repository.UserRepository;
import com.se1.userservice.domain.service.UserService;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/user/external")
@RequiredArgsConstructor
public class UserExternalController {
	
	private final UserService service;

	private final UserRepository repository;

	private final ApiResponseEntity apiResponseEntity;

	private final ObjectMapper objectMapper;
	
	@PostMapping("/findById")
	public ResponseEntity<?> findById(@RequestParam("id") Long id) throws Exception {

		try {
			service.processFindUserById(id, apiResponseEntity);
		} catch (Exception e) {
			apiResponseEntity.setData(null);
			apiResponseEntity.setErrorList(List.of(e.getMessage()));
			apiResponseEntity.setStatus(0);
		}
		return ResponseEntity.ok().body(apiResponseEntity);
	}

	@PostMapping("/find")
	public ResponseEntity<?> find(@RequestBody FindRequest findRequest) {
		String findRequestStr;
		try {
			findRequestStr = objectMapper.writeValueAsString(findRequest);
			Map<String, Object> findRequestMap = objectMapper.readValue(findRequestStr, Map.class);
			service.processFindUser(findRequestMap, apiResponseEntity);
		} catch (JsonProcessingException e) {
			apiResponseEntity.setData(null);
			apiResponseEntity.setErrorList(List.of(e.getMessage()));
			apiResponseEntity.setStatus(0);
		}
		return ResponseEntity.ok().body(apiResponseEntity);
	}

	@PostMapping("/findByName")
	public ResponseEntity<?> findByName(@RequestParam("name") String name) {
		try {
			service.processFindByName(name, apiResponseEntity);
		} catch (Exception e) {
			apiResponseEntity.setData(null);
			apiResponseEntity.setErrorList(List.of(e.getMessage()));
			apiResponseEntity.setStatus(0);
		}
		return ResponseEntity.ok().body(apiResponseEntity);
	}
	
	@PostMapping("/create")
	public ResponseEntity<?> create(@RequestBody CreateUserRequest request, @RequestHeader("user_detail") String userDetailHeader) throws JsonMappingException, JsonProcessingException{
		UserDetail userDetail = objectMapper.readValue(userDetailHeader, UserDetail.class);
		
		try {
			service.processcreate(request, userDetail, apiResponseEntity);
		} catch (Exception e) {
			apiResponseEntity.setData(null);
			apiResponseEntity.setErrorList(List.of(e.getMessage()));
			apiResponseEntity.setStatus(0);
		}
		return ResponseEntity.ok().body(apiResponseEntity);
	}
	
	@PostMapping("/findAll")
	public ResponseEntity<?> findAll(@RequestBody FindAllUserRequest request, @RequestHeader("user_detail") String userDetailHeader) throws JsonMappingException, JsonProcessingException{
		UserDetail userDetail = objectMapper.readValue(userDetailHeader, UserDetail.class);
		try {
			service.findAll(request,userDetail, apiResponseEntity);
		} catch (Exception e) {
			apiResponseEntity.setData(null);
			apiResponseEntity.setErrorList(List.of(e.getMessage()));
			apiResponseEntity.setStatus(0);
		}
		return ResponseEntity.ok().body(apiResponseEntity);
	}
}
