package com.se1.userservice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.se1.userservice.domain.payload.ApiResponseEntity;
import com.se1.userservice.domain.payload.UserDetail;
import com.se1.userservice.domain.payload.request.AcceptExpertRequest;
import com.se1.userservice.domain.service.LicenceService;

@RestController
@RequestMapping("/licence/external")
public class LicenceExternalController {

	@Autowired
	private ApiResponseEntity apiResponseEntity;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@Autowired
	private LicenceService licenceService;
	
	@PostMapping("/accept-expert")
	public ResponseEntity<?> acceptExpert(@RequestHeader("user_detail") String userDetailHeader,
			@RequestBody AcceptExpertRequest request) throws JsonMappingException, JsonProcessingException{
		
		UserDetail userDetail = objectMapper.readValue(userDetailHeader, UserDetail.class);
		
		try {
			licenceService.processAcceptExpert(userDetail, request, apiResponseEntity);
		} catch (Exception e) {
			apiResponseEntity.setData(null);
			apiResponseEntity.setErrorList(List.of(e.getMessage()));
			apiResponseEntity.setStatus(0);
		}
		
		return ResponseEntity.ok(apiResponseEntity);
	}
}
