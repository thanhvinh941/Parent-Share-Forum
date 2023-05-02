package com.se1.postservice.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.se1.postservice.domain.payload.ApiResponseEntity;
import com.se1.postservice.domain.payload.UserDetail;
import com.se1.postservice.domain.service.PostService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/post")
@RequiredArgsConstructor
public class PostNoAuthenController {

	private final PostService postService;
	private final ApiResponseEntity apiResponseEntity;

	@PostMapping("/findAllPost")
	public ResponseEntity<?> findAllPost(@RequestParam("offset") Integer offset)
			throws JsonMappingException, JsonProcessingException {

		try {
			postService.findAllPost(null, apiResponseEntity, offset);
		} catch (Exception e) {
			apiResponseEntity.setData(null);
			apiResponseEntity.setErrorList(List.of(e.getMessage()));
			apiResponseEntity.setStatus(0);
		}
		return ResponseEntity.ok().body(apiResponseEntity);
	}

	@PostMapping("/findPostAllMost")
	public ResponseEntity<?> findPostAllMost() throws JsonMappingException, JsonProcessingException {
		UserDetail detail = new UserDetail();
		detail.setId(new Long(0));

		try {
			postService.findPostAllMost(detail.getId(), apiResponseEntity);
		} catch (Exception e) {
			apiResponseEntity.setData(null);
			apiResponseEntity.setErrorList(List.of(e.getMessage()));
			apiResponseEntity.setStatus(0);
		}
		return ResponseEntity.ok().body(apiResponseEntity);

	}
}
