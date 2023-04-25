package com.se1.postservice.domain.service;

import java.util.Map;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.se1.postservice.domain.entity.Post;
import com.se1.postservice.domain.payload.ApiResponseEntity;
import com.se1.postservice.domain.payload.PostRequest;
import com.se1.postservice.domain.payload.UserDetail;

public interface PostService {

//	List<Post> saveAll(List<Post> request);

	Post save(Post post);

	void processSavePost(PostRequest postRequest, UserDetail detail, ApiResponseEntity apiResponseEntity) throws Exception;

	void processFindUserPost(Long id, ApiResponseEntity apiResponseEntity);

	void processFindPost(Map<String, Object> response);

	void processGetByTitle(String title, UserDetail detail, ApiResponseEntity apiResponseEntity);

	void processGetAllPost(UserDetail detail, ApiResponseEntity apiResponseEntity);

	void findPostById(Long postId, ApiResponseEntity apiResponseEntity) throws Exception;

	void findAllPost(UserDetail detail, ApiResponseEntity apiResponseEntity, int offset) throws JsonMappingException, JsonProcessingException;

	void findAllPostByUserId(Long userId, ApiResponseEntity apiResponseEntity, int offset);

	void findAllPostByCondition(Long userId, Map<String, Object> param, ApiResponseEntity apiResponseEntity, int offset);

}
