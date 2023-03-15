package com.se1.postservice.domain.service;

import java.util.List;

import com.se1.postservice.domain.entity.Post;
import com.se1.postservice.domain.payload.ApiResponseEntity;
import com.se1.postservice.domain.payload.PostRequest;
import com.se1.postservice.domain.payload.UserDetail;

public interface PostService {

	List<Post> saveAll(List<Post> request);

	Post save(Post post);

	void processSavePost(PostRequest postRequest, UserDetail detail, ApiResponseEntity apiResponseEntity) throws Exception;

	void processFindUserPost(Long id, ApiResponseEntity apiResponseEntity);

}
