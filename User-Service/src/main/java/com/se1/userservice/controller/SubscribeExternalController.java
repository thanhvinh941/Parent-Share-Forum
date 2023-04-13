package com.se1.userservice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.se1.userservice.domain.payload.ApiResponseEntity;
import com.se1.userservice.domain.payload.UserDetail;
import com.se1.userservice.domain.service.SubscribeService;

@RestController
@RequestMapping("/subscriber/external")
public class SubscribeExternalController {

	@Autowired
	private ApiResponseEntity apiResponseEntity;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@Autowired
	private SubscribeService subscribeService;
	
	@PostMapping("/subscribe")
	public ResponseEntity<?> doSub(@RequestParam("expert_id")Long expertid, @RequestHeader("user_detail") String userDetailHeader) throws JsonMappingException, JsonProcessingException{
		
		UserDetail userDetail = objectMapper.readValue(userDetailHeader, UserDetail.class);
		
		try {
			subscribeService.processDoSub(expertid,userDetail, apiResponseEntity);
		} catch (Exception e) {
			apiResponseEntity.setData(false);
			apiResponseEntity.setErrorList(List.of(e.getMessage()));
			apiResponseEntity.setStatus(0);
		}
		
		
		return ResponseEntity.ok(apiResponseEntity);
	}
	
	@PostMapping("/unsubscribe")
	public ResponseEntity<?> doUnSub(@RequestParam("expert_id")Long expertid, @RequestHeader("user_detail") String userDetailHeader) throws JsonMappingException, JsonProcessingException{
		
		UserDetail userDetail = objectMapper.readValue(userDetailHeader, UserDetail.class);
		
		try {
			subscribeService.processDoUnSub(expertid,userDetail, apiResponseEntity);
		} catch (Exception e) {
			apiResponseEntity.setData(false);
			apiResponseEntity.setErrorList(List.of(e.getMessage()));
			apiResponseEntity.setStatus(0);
		}
		
		
		return ResponseEntity.ok(apiResponseEntity);
	}
}
