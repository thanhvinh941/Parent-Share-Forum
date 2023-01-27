package com.se1.postservice.domain.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.se1.postservice.domain.entity.Post;
import com.se1.postservice.domain.repository.PostRepository;
import com.se1.postservice.domain.service.PostService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class PostServiceImpl implements PostService{
	
	private final PostRepository postRepository;
	
	@Override
	public List<Post> saveAll(List<Post> records) {
		return postRepository.saveAll(records);
	}

	@Override
	public Boolean uDelete(Integer postId) {
		return postRepository.uDelete(postId);
	}

	@Override
	public Boolean uPublish(Integer postId, Byte publishedFlg ) {
		return postRepository.uPublish(postId, publishedFlg);
	}
}
