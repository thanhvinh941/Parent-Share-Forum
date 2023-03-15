package com.se1.postservice.domain.service.impl;

import java.util.List;
import java.util.Objects;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.se1.postservice.domain.entity.Post;
import com.se1.postservice.domain.repository.PostRepository;
import com.se1.postservice.domain.repository.TopicTagRepository;
import com.se1.postservice.domain.service.PostService;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

	private final PostRepository postRepository;
	
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
	public Boolean uPublish(Integer postId, Byte publishedFlg ) {
		return postRepository.uPublish(postId, publishedFlg);
	}

}
