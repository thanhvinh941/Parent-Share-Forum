package com.se1.postservice.domain.service.impl;

import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.se1.postservice.common.SCMConstant;
import com.se1.postservice.domain.entity.Comment;
import com.se1.postservice.domain.entity.Post;
import com.se1.postservice.domain.payload.ApiResponseEntity;
import com.se1.postservice.domain.payload.CreateCommentRequest;
import com.se1.postservice.domain.payload.CreateCommentResponse;
import com.se1.postservice.domain.payload.NotifycationDto;
import com.se1.postservice.domain.payload.UserDetail;
import com.se1.postservice.domain.payload.UserPost;
import com.se1.postservice.domain.repository.CommentRepository;
import com.se1.postservice.domain.repository.PostRepository;
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
	private final PostRepository postRepository;
	
	public void processCreat(CreateCommentRequest request, ApiResponseEntity responseEntity) throws Exception {
		Long postId = request.getPostId();
		Long commentParentId = request.getComemntParentId();
		Boolean isPrisentParent = null;
		Comment comment = new Comment();
		BeanUtils.copyProperties(request, comment);
		if (commentParentId != null) {
			Long commentParentIdValid = null;
			Optional<Comment> commentParent = commentRepository.findById(commentParentIdValid);
			isPrisentParent = !commentParent.isEmpty();
			if(!commentParent.isEmpty()) {
				commentParentIdValid = commentParentId;
			}
			comment.setComemntParentId(commentParentIdValid);
		}
		Optional<Post> post = postRepository.findById(postId);
		if(post.isEmpty()) {
			throw new Exception("Bài viết không tồn tại");
		}

//		if (userPostDto != null) {
//			NotifycationDto notifycationToUserReciver = new NotifycationDto();
//			notifycationToUserReciver.setAction(SCMConstant.COMMENT_ACTION);
//			notifycationToUserReciver.setTopicUserId(userReciver.getTopicId());
//			notifycationToUserReciver.setRequest(contact);
//		} else {
//			throw new Exception("Bài đăng không hợp lệ");
//		}

//		validator(commentSave);

		try {
			Comment commentSave = commentRepository.save(comment);
			
			CreateCommentResponse response = new CreateCommentResponse();
			BeanUtils.copyProperties(commentSave, response);
			
			if(isPrisentParent ) {
				BeanUtils.copyProperties(commentSave, response);
			}
			
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
