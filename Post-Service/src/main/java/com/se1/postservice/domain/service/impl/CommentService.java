package com.se1.postservice.domain.service.impl;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.se1.postservice.common.SCMConstant;
import com.se1.postservice.domain.entity.Comment;
import com.se1.postservice.domain.entity.Post;
import com.se1.postservice.domain.payload.ApiResponseEntity;
import com.se1.postservice.domain.payload.NotifycationDto;
import com.se1.postservice.domain.payload.UserPost;
import com.se1.postservice.domain.repository.CommentRepository;
import com.se1.postservice.domain.service.PostService;
import com.se1.postservice.domain.service.UserService;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class CommentService {

	private final CommentRepository commentRepository;
	private final PostService postService;
	private final UserService userService;
	
	public void processCreat(Comment commentSave, ApiResponseEntity responseEntity) throws Exception {
		Long postId = commentSave.getPostId();
		Long commentParentId = commentSave.getComemntParentId();
		if (commentParentId != null) {
			Long commentParentIdValid = null;
			boolean isExistCommentParent = commentRepository.existsById(commentParentId);
			if (isExistCommentParent) {
				commentParentIdValid = commentParentId;
			}
			commentSave.setComemntParentId(commentParentIdValid);
		}
//		Post post = postService.findById(postId);
		
		UserPost userPostDto = new UserPost();
//		BeanUtils.copyProperties(postService.findUserPostByPostId(postId), userPostDto);

		if (userPostDto != null) {
			NotifycationDto notifycationToUserReciver = new NotifycationDto();
			notifycationToUserReciver.setAction(SCMConstant.COMMENT_ACTION);
//			notifycationToUserReciver.setTopicUserId(userReciver.getTopicId());
//			notifycationToUserReciver.setRequest(contact);
		} else {
			throw new Exception("Bài đăng không hợp lệ");
		}

		validator(commentSave);

		try {
			Comment comment = commentRepository.save(commentSave);
			responseEntity.setData(comment);
			responseEntity.setStatus(1);
			responseEntity.setErrorList(null);
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}

	}

	private void validator(Comment commentSave) {

	}

}
