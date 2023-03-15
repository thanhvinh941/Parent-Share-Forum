package com.se1.postservice.api.internalApi;

import java.util.Date;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.se1.postservice.domain.entity.Post;
import com.se1.postservice.domain.payload.ApiResponseEntity;
import com.se1.postservice.domain.payload.PostDto;
import com.se1.postservice.domain.payload.PostDto.PostTopicTagDto;
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
	public ResponseEntity<?> save(@RequestBody PostRequest postRequest,
			@RequestHeader("user_detail") String userDetail) throws JsonMappingException, JsonProcessingException{
		
		UserDetail detail = objectMapper.readValue(userDetail, UserDetail.class);
		
		List<String> errorList = postService.validation(postRequest);
		
		if(errorList.size() > 0) {
			return this.badResponse(errorList);
		}
		
		Post post = convertPostRequestToPostEntity(postRequest,detail.getId().intValue(),detail.getName());
		
		try {
			Post postSave = postService.save(post);
			
			PostTopicTagDto postTopicTagDto = postService.getPostTopicTagDtoById(post.getTopicTagId());
			
			PostDto postDto = convertPostEntityToPostDto(postSave);
			return this.okResponse(postDto, null);
		} catch (Exception e) {
			return this.badResponse(List.of(e.getMessage()));
		}
	}
	
	Post convertPostRequestToPostEntity(PostRequest postRequest,Integer userId, String userName) {
		Post post = new Post();
		post.setUserId(userId);
		post.setUserName(userName);
		post.setTitle(postRequest.getTitle());
		post.setMetaTitle(postRequest.getMetaTitle());
		post.setSlug(postRequest.getSlug());
		post.setSummary(postRequest.getSummary());
		post.setValidFlag(true);
		post.setContext(postRequest.getContext());
		post.setLikeCount(0);
		post.setHashTag(postRequest.getHashTag());
		post.setTopicTagId(postRequest.getTopicTagId());
		post.setCreateAt(new Date());
		post.setUpdateAt(new Date());
		post.setImageList(postRequest.getImageList());
		
		return post;
		
	}
	
	PostDto convertPostEntityToPostDto(Post post) {
		
		PostDto postDto = new PostDto();
		postDto.setId(post.getId());
		postDto.setUserId(post.getUserId());
		postDto.setUserName(post.getUserName());
		postDto.setTitle(post.getTitle());
		postDto.setMetaTitle(post.getMetaTitle());
		postDto.setSlug(post.getSlug());
		postDto.setSummary(post.getSummary());
		postDto.setContext(post.getContext());
		postDto.setLikeCount(post.getLikeCount());
		postDto.setHashTag(post.getHashTag());
		postDto.setImageList(post.getImageList());
		
		return postDto;
	}
	
	private ResponseEntity<?> badResponse(List<String> errorMessage){
		apiResponseEntity.setData(null);
		apiResponseEntity.setErrorList(errorMessage);
		apiResponseEntity.setStatus(0);
		return ResponseEntity.badRequest().body(apiResponseEntity);
	}
	
	private ResponseEntity<?> okResponse(Object data, List<String> errorMessage){
		apiResponseEntity.setData(data);
		apiResponseEntity.setErrorList(errorMessage);
		apiResponseEntity.setStatus(1);
		return ResponseEntity.ok().body(apiResponseEntity);
	}
}
