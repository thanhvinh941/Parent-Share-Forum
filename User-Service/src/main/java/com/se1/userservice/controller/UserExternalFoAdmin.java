package com.se1.userservice.controller;

import java.util.List;

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
import com.se1.userservice.domain.payload.FindAllReportRequest;
import com.se1.userservice.domain.payload.UserDetail;
import com.se1.userservice.domain.payload.request.CreateUserRequest;
import com.se1.userservice.domain.payload.request.UpdateUserRequest;
import com.se1.userservice.domain.service.UserService;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/user/external")
@RequiredArgsConstructor
public class UserExternalFoAdmin {
	
	private final UserService service;
	private final ApiResponseEntity apiResponseEntity;
	private final ObjectMapper objectMapper;
	
	@PostMapping("/create")
	public ResponseEntity<?> create(@RequestBody CreateUserRequest request,
			@RequestHeader("user_detail") String userDetailHeader)
			throws JsonMappingException, JsonProcessingException {
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
	public ResponseEntity<?> findAll(@RequestBody FindAllUserRequest request,
			@RequestHeader("user_detail") String userDetailHeader, @RequestParam("offset") Integer offset)
			throws JsonMappingException, JsonProcessingException {
		UserDetail userDetail = objectMapper.readValue(userDetailHeader, UserDetail.class);
		try {
			service.findAll(request, userDetail, offset, apiResponseEntity);
		} catch (Exception e) {
			apiResponseEntity.setData(null);
			apiResponseEntity.setErrorList(List.of(e.getMessage()));
			apiResponseEntity.setStatus(0);
		}
		return ResponseEntity.ok().body(apiResponseEntity);
	}
	
	@PostMapping("/findAllReport")
	public ResponseEntity<?> findAllReport(@RequestBody FindAllReportRequest request,
			@RequestHeader("user_detail") String userDetailHeader, @RequestParam("offset") Integer offset)
			throws JsonMappingException, JsonProcessingException {
		UserDetail userDetail = objectMapper.readValue(userDetailHeader, UserDetail.class);
		try {
			service.findAllReport(request, userDetail, offset, apiResponseEntity);
		} catch (Exception e) {
			apiResponseEntity.setData(null);
			apiResponseEntity.setErrorList(List.of(e.getMessage()));
			apiResponseEntity.setStatus(0);
		}
		return ResponseEntity.ok().body(apiResponseEntity);
	}
	
	@PostMapping("/update")
	public ResponseEntity<?> update(@RequestBody UpdateUserRequest request,
			@RequestHeader("user_detail") String userDetailHeader)
			throws JsonMappingException, JsonProcessingException {
		UserDetail userDetail = objectMapper.readValue(userDetailHeader, UserDetail.class);

		try {
			service.update(request, userDetail, apiResponseEntity);
		} catch (Exception e) {
			apiResponseEntity.setData(null);
			apiResponseEntity.setErrorList(List.of(e.getMessage()));
			apiResponseEntity.setStatus(0);
		}
		return ResponseEntity.ok().body(apiResponseEntity);
	}
	
	@PostMapping("/delete")
	public ResponseEntity<?> delete(@RequestParam("id") Long id,
			@RequestHeader("user_detail") String userDetailHeader)
			throws JsonMappingException, JsonProcessingException {
		UserDetail userDetail = objectMapper.readValue(userDetailHeader, UserDetail.class);

		try {
			service.delete(id, userDetail, apiResponseEntity);
		} catch (Exception e) {
			apiResponseEntity.setData(null);
			apiResponseEntity.setErrorList(List.of(e.getMessage()));
			apiResponseEntity.setStatus(0);
		}
		return ResponseEntity.ok().body(apiResponseEntity);
	}
}
