package com.se1.postservice.api.internalApi;

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
import com.se1.postservice.domain.payload.PostRequest;
import com.se1.postservice.domain.payload.UserDetail;
import com.se1.postservice.domain.service.PostService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/post/internal")
@RequiredArgsConstructor
public class PostInternalController {

	private final PostService postService;

	private final ObjectMapper objectMapper;

	private final ApiResponseEntity apiResponseEntity;

	@PostMapping("/save")
	public ResponseEntity<?> save(@RequestBody PostRequest postRequest, @RequestHeader("user_detail") String userDetail)
			throws JsonMappingException, JsonProcessingException {

		UserDetail detail = objectMapper.readValue(userDetail, UserDetail.class);
		
		try {
			postService.processSavePost(postRequest, detail, apiResponseEntity);
		
			apiResponseEntity.setData(true);
			apiResponseEntity.setErrorList(null);
			apiResponseEntity.setStatus(0);
		} catch (Exception e) {
			apiResponseEntity.setData(null);
			apiResponseEntity.setErrorList(List.of(e.getMessage()));
			apiResponseEntity.setStatus(0);
		}
		return ResponseEntity.ok().body(apiResponseEntity);

	}

	@PostMapping("/findUserPostByPostId")
	public ResponseEntity<?> findUserPost(@RequestParam("id") Long id){
		
		postService.processFindUserPost(id, apiResponseEntity);
		
		return ResponseEntity.ok().body(apiResponseEntity);

	}
}
