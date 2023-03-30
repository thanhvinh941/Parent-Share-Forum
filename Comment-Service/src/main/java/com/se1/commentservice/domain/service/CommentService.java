package com.se1.commentservice.domain.service;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.se1.commentservice.common.SCMConstant;
import com.se1.commentservice.domain.entity.Comment;
import com.se1.commentservice.domain.payload.ApiResponseEntity;
import com.se1.commentservice.domain.payload.NotifycationDto;
import com.se1.commentservice.domain.payload.UserPostDto;
import com.se1.commentservice.domain.repository.CommentRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class CommentService {

	private final CommentRepository commentRepository;
	private final PostService postService;

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
		UserPostDto userPostDto = new UserPostDto();
		BeanUtils.copyProperties(postService.findUserPostByPostId(postId), userPostDto);

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
