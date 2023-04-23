package com.se1.postservice.domain.service.impl;

import java.util.Date;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.se1.postservice.domain.entity.PostLike;
import com.se1.postservice.domain.payload.ApiResponseEntity;
import com.se1.postservice.domain.payload.UserDetail;
import com.se1.postservice.domain.repository.LikePostRepository;

@Service
public class LikePostService {

	@Autowired
	private LikePostRepository postRepository;
	
	@SuppressWarnings("removal")
	public void likePost(Long postId, ApiResponseEntity apiResponseEntity, UserDetail detail) {
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
		
		postRepository.save(postLike);
		
		apiResponseEntity.setData(true);
		apiResponseEntity.setErrorList(null);
		apiResponseEntity.setStatus(1);
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
