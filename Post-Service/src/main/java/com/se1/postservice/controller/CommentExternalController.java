package com.se1.postservice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
	
	@PostMapping("/create")
	public ResponseEntity<?> create(@RequestHeader("user_detail") String userDetailHeader, @RequestBody CreateCommentRequest request) throws JsonMappingException, JsonProcessingException{
		
		UserDetail userDetail = objectMapper.readValue(userDetailHeader, UserDetail.class);
		
		try {
			commentService.processCreat(request, apiResponseEntity, userDetail);
		} catch (Exception e) {
			apiResponseEntity.setData(null);
			apiResponseEntity.setErrorList(List.of(e.getMessage()));
			apiResponseEntity.setStatus(0);
		}
		
		return ResponseEntity.ok(apiResponseEntity);
	}
	
	@PostMapping("/getAllComment")
	public ResponseEntity<?> getAllComment(@RequestParam("postId") Long postId, @RequestHeader("user_detail") String userDetailHeader) throws JsonMappingException, JsonProcessingException{
		
		UserDetail userDetail = objectMapper.readValue(userDetailHeader, UserDetail.class);
		
		try {
			commentService.processGetAllComment(postId, apiResponseEntity, userDetail);
		} catch (Exception e) {
			apiResponseEntity.setData(null);
			apiResponseEntity.setErrorList(List.of(e.getMessage()));
			apiResponseEntity.setStatus(0);
		}
		
		return ResponseEntity.ok(apiResponseEntity);
	}
}
