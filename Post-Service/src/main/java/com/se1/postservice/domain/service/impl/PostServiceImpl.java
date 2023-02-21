package com.se1.postservice.domain.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.se1.postservice.domain.entity.Post;
import com.se1.postservice.domain.entity.TopicTag;
import com.se1.postservice.domain.payload.PostDto;
import com.se1.postservice.domain.payload.PostDto.PostTopicTagDto;
import com.se1.postservice.domain.payload.PostRequest;
import com.se1.postservice.domain.payload.UserDetail;
import com.se1.postservice.domain.repository.PostRepository;
import com.se1.postservice.domain.service.PostService;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class PostServiceImpl implements PostService{
	
	private final PostRepository postRepository;
	private final TopicTagService topicTagService;
	
	@Override
	public List<Post> saveAll(List<Post> records) {
		return postRepository.saveAll(records);
	}

	@Override
	public Post save(Post post) {
		return postRepository.save(post);
	}

	@Override
	public List<String> validation(PostRequest postRequest) {
		List<String> error = new ArrayList<>();
		
		Integer topicTagId = postRequest.getTopicTagId();

		TopicTag propertyTag = topicTagService.findById(topicTagId);
		if(Objects.isNull(propertyTag)) {
			error.add("Tag thuột tính không tồn tại");
		}
		
		return error;
	}

	@Override
	public PostTopicTagDto getPostTopicTagDtoById(Integer topicTagId) {
		TopicTag topicTag = topicTagService.findById(topicTagId);
		
		PostDto.PostTopicTagDto postTopicTagDto = new PostDto().new PostTopicTagDto();
		postTopicTagDto.setId(topicTag.getId());
		postTopicTagDto.setTopicTagName(topicTag.getTagName());
		
		return postTopicTagDto;
	}

}
