package com.se1.postservice.controller;

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
import com.se1.postservice.domain.payload.ApiResponseEntity;
import com.se1.postservice.domain.payload.CreateCommentRequest;
import com.se1.postservice.domain.payload.UserDetail;
import com.se1.postservice.domain.service.impl.CommentService;

@RestController
@RequestMapping("/comment/external")
public class CommentExternalController {

	@Autowired
	private ApiResponseEntity apiResponseEntity;
	
	@Autowired
	private CommentService commentService;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@PostMapping
	public ResponseEntity<?> create(@RequestHeader("user_detail") String userDetailHeader, @RequestBody CreateCommentRequest request) throws JsonMappingException, JsonProcessingException{
		
		UserDetail userDetail = objectMapper.readValue(userDetailHeader, UserDetail.class);
		
		try {
			commentService.processCreat(null, apiResponseEntity);
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		return ResponseEntity.ok(apiResponseEntity);
	}
}
