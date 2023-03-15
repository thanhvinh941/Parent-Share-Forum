package com.se1.postservice.domain.service;

import java.util.List;

import com.se1.postservice.domain.entity.Post;
import com.se1.postservice.domain.payload.PostDto.PostTopicTagDto;
import com.se1.postservice.domain.payload.PostRequest;
import com.se1.postservice.domain.payload.UserDetail;

public interface PostService {

	List<Post> saveAll(List<Post> request);

	Post save(Post post);

	List<String> validation(PostRequest postRequest);

	PostTopicTagDto getPostTopicTagDtoById(Integer topicTagId);

}
