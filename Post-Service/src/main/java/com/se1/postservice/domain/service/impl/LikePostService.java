package com.se1.postservice.domain.service.impl;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.se1.postservice.common.SCMConstant;
import com.se1.postservice.domain.entity.Post;
import com.se1.postservice.domain.entity.PostLike;
import com.se1.postservice.domain.payload.ApiResponseEntity;
import com.se1.postservice.domain.payload.UserDetail;
import com.se1.postservice.domain.payload.dto.PostDto;
import com.se1.postservice.domain.payload.request.RabbitRequest;
import com.se1.postservice.domain.repository.LikePostRepository;
import com.se1.postservice.domain.repository.PostRepository;
import com.se1.postservice.domain.service.RabbitSenderService;
import com.se1.postservice.domain.util.UserServiceRestTemplateClient;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LikePostService {

	private final LikePostRepository postRepository;
	private final PostRepository postRepository2;
	private final UserServiceRestTemplateClient restTemplateClient;
	private final ObjectMapper objectMapper;
	private final RabbitSenderService rabbitSenderService;
	
	@SuppressWarnings("removal")
	public void likePost(Long postId, ApiResponseEntity apiResponseEntity, UserDetail detail) throws Exception {
		Optional<Post> postFind = postRepository2.findById(postId);
		if(postFind.isEmpty()) {
			throw new Exception("bài đăng không tồn tại");
		}
		PostLike postLikeFind = postRepository.findByPostIdAndUserId(postId,detail.getId());
		
		PostLike postLike = new PostLike();
		if(postLikeFind != null) {
			BeanUtils.copyProperties(postLikeFind, postLike);
			if(postLikeFind.getStatus().equals(new Byte((byte) 1))) {
				postLike.setStatus(new Byte((byte) 0));
			}else {
				postLike.setStatus(new Byte((byte) 1));
			}
		}else {			
			postLike.setCreateAt(new Date());
			postLike.setPostId(postId);
			postLike.setStatus(new Byte((byte) 1));
			postLike.setUserId(detail.getId());
		}
		
		PostLike postLikeSave = postRepository.save(postLike);
		if(postLikeSave.getStatus().equals(new Byte((byte) 1))) {
			sendNotify(postFind.get(), postLike);			
		}
		
		apiResponseEntity.setData(true);
		apiResponseEntity.setErrorList(null);
		apiResponseEntity.setStatus(1);
	}
	
	private void sendNotify(Post post, PostLike postLike) {
		PostDto postDto = new PostDto();
		postDto.setPostId(post.getId());
		postDto.setUserReciver(getUser(postLike.getUserId()));
		postDto.setUserSender(getUser(post.getUserId()));
		postDto.setAction(SCMConstant.POST_LIKE);

		RabbitRequest rabbitRequest = new RabbitRequest();
		rabbitRequest.setAction(SCMConstant.SYSTEM_POST);
		rabbitRequest.setData(postDto);
		
		rabbitSenderService.convertAndSendSysTem(rabbitRequest);
	}
	
	private PostDto.UserDetail getUser(Long userId) {
		PostDto.UserDetail user = new PostDto.UserDetail();
		ApiResponseEntity userResult = (ApiResponseEntity) restTemplateClient.findById(userId);
		if (userResult.getStatus() == 1) {
			String apiResultStr;
			try {
				apiResultStr = objectMapper.writeValueAsString(userResult.getData());
				user = objectMapper.readValue(apiResultStr, PostDto.UserDetail.class);
			} catch (JsonProcessingException e) {
				e.printStackTrace();
			}
		}
		return user;
	}

	@SuppressWarnings("removal")
	public void dislikePost(Long postId, ApiResponseEntity apiResponseEntity, UserDetail detail) {
		PostLike postLikeFind = postRepository.findByPostIdAndUserId(postId, detail.getId());
		
		PostLike postLike = new PostLike();
		if(postLikeFind != null) {
			BeanUtils.copyProperties(postLikeFind, postLike);
			if(postLikeFind.getStatus().equals(new Byte((byte) 2))) {
				postLike.setStatus(new Byte((byte) 0));
			}else {
				postLike.setStatus(new Byte((byte) 2));
			}
		}else {
			postLike.setCreateAt(new Date());
			postLike.setPostId(postId);
			postLike.setStatus(new Byte((byte) 2));
			postLike.setUserId(detail.getId());
			
		}
		postRepository.save(postLike);
		
		apiResponseEntity.setData(true);
		apiResponseEntity.setErrorList(null);
		apiResponseEntity.setStatus(1);
	}

}
