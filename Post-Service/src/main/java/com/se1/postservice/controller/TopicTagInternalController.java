package com.se1.postservice.controller;

import java.util.List;

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
import com.se1.postservice.domain.payload.CreateTopicTagRequest;
import com.se1.postservice.domain.payload.UpdateTopicTagRequest;
import com.se1.postservice.domain.payload.UserDetail;
import com.se1.postservice.domain.service.impl.TopicTagService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/topic-tag/external")
@RequiredArgsConstructor
public class TopicTagInternalController {

	private final TopicTagService topicTagService;
	private final ObjectMapper objectMapper;
	
	@RequestMapping("/create")
	public ResponseEntity<?> savePropertyTag(@RequestBody CreateTopicTagRequest topicTagRequest,
			@RequestHeader("user_detail") String userDetailHeader) throws JsonMappingException, JsonProcessingException{
		ApiResponseEntity apiResponseEntity = new ApiResponseEntity();
		UserDetail userDetail;
		try {
			userDetail = objectMapper.readValue(userDetailHeader, UserDetail.class);
			
			topicTagService.processCreate(topicTagRequest, userDetail, apiResponseEntity);
		} catch (Exception e) {
			apiResponseEntity.setData(null);
			apiResponseEntity.setErrorList(List.of(e.getMessage()));
			apiResponseEntity.setStatus(0);
		}
		return ResponseEntity.ok().body(apiResponseEntity);
		
	}
	
	@PostMapping("update")
	public ResponseEntity<?> update(@RequestBody UpdateTopicTagRequest topicTagRequest,
			@RequestHeader("user_detail") String userDetailHeader){
		ApiResponseEntity apiResponseEntity = new ApiResponseEntity();
		UserDetail userDetail;
		try {
			userDetail = objectMapper.readValue(userDetailHeader, UserDetail.class);
			
			topicTagService.processUpdate(topicTagRequest, userDetail, apiResponseEntity);
		} catch (Exception e) {
			apiResponseEntity.setData(null);
			apiResponseEntity.setErrorList(List.of(e.getMessage()));
			apiResponseEntity.setStatus(0);
		}
		
		return ResponseEntity.ok().body(apiResponseEntity);
	}
	
	@RequestMapping("/getAllTopicTag")
	public ResponseEntity<?> getAllTopicTag(@RequestParam("tag-name") String tagName,
			@RequestHeader("user_detail") String userDetailHeader) throws JsonMappingException, JsonProcessingException{
		ApiResponseEntity apiResponseEntity = new ApiResponseEntity();
		try {
			topicTagService.processGetAllTopicTag(tagName, apiResponseEntity);
		} catch (Exception e) {
			apiResponseEntity.setData(null);
			apiResponseEntity.setErrorList(List.of(e.getMessage()));
			apiResponseEntity.setStatus(0);
		}
		return ResponseEntity.ok().body(apiResponseEntity);
		
	}
}
