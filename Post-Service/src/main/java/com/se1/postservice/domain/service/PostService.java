package com.se1.postservice.domain.service;

import java.util.Map;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.se1.postservice.domain.payload.ApiResponseEntity;
import com.se1.postservice.domain.payload.CreatePostRequest;
import com.se1.postservice.domain.payload.UpdatePostRequest;
import com.se1.postservice.domain.payload.UserDetail;

public interface PostService {

	void processSavePost(CreatePostRequest postRequest, UserDetail detail, ApiResponseEntity apiResponseEntity) throws Exception;

	void findAllPost(UserDetail detail, ApiResponseEntity apiResponseEntity, int offset) throws JsonMappingException, JsonProcessingException;

	void findAllPostByUserId(Long userId, ApiResponseEntity apiResponseEntity, int offset);

	void findAllPostByCondition(Long userId, Map<String, Object> param, ApiResponseEntity apiResponseEntity, int offset);

	void findById(Long id, Long id2, ApiResponseEntity apiResponseEntity);

	void findPostAllMost(Long id, ApiResponseEntity apiResponseEntity);

	void update(UpdatePostRequest postRequest, UserDetail detail, ApiResponseEntity apiResponseEntity) throws Exception;

	void delete(Long postId, UserDetail detail, ApiResponseEntity apiResponseEntity) throws Exception;

}
