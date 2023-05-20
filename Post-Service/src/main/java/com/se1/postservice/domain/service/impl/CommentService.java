package com.se1.postservice.domain.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.se1.postservice.common.SCMConstant;
import com.se1.postservice.common.UrlConstant;
import com.se1.postservice.domain.entity.Comment;
import com.se1.postservice.domain.entity.Post;
import com.se1.postservice.domain.payload.ApiResponseEntity;
import com.se1.postservice.domain.payload.CreateCommentRequest;
import com.se1.postservice.domain.payload.CreateCommentResponse;
import com.se1.postservice.domain.payload.UserDetail;
import com.se1.postservice.domain.payload.dto.PostDto;
import com.se1.postservice.domain.payload.request.RabbitRequest;
import com.se1.postservice.domain.repository.CommentRepository;
import com.se1.postservice.domain.repository.PostRepository;
import com.se1.postservice.domain.service.CallApiService;
import com.se1.postservice.domain.service.RabbitSenderService;
import com.se1.postservice.domain.util.UserServiceRestTemplateClient;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class CommentService {

	private final CommentRepository commentRepository;
	private final PostRepository postRepository;
	private final ObjectMapper objectMapper;
	private final CallApiService<CreateCommentResponse.User> callApiService;
	private final UserServiceRestTemplateClient restTemplateClient;
	private final RabbitSenderService rabbitSenderService;
	
	public void processCreat(CreateCommentRequest request, ApiResponseEntity responseEntity, UserDetail userDetail)
			throws Exception {
		Long postId = request.getPostId();
		Long commentParentId = request.getComemntParentId();
		Comment comment = new Comment();
		BeanUtils.copyProperties(request, comment);
		comment.setCreateAt(new Date());
		comment.setDelFlg(false);
		comment.setUserId(userDetail.getId());
		if (commentParentId != null) {
			Long commentParentIdValid = null;
			Optional<Comment> commentParent = commentRepository.findById(commentParentId);
			if (!commentParent.isEmpty()) {
				commentParentIdValid = commentParentId;
			}
			comment.setComemntParentId(commentParentIdValid);
		}
		Optional<Post> post = postRepository.findById(postId);
		if (post.isEmpty()) {
			throw new Exception("Bài viết không tồn tại");
		}

		try {
			Comment commentSave = commentRepository.save(comment);

			CreateCommentResponse response = new CreateCommentResponse();
			BeanUtils.copyProperties(commentSave, response);
			response.setUserId(getUSerComment(commentSave.getUserId()));
			if(commentSave.getComemntParentId()!= null) {
				CreateCommentResponse responseParent = new CreateCommentResponse();
				Comment commentParent = commentRepository.findById(commentSave.getComemntParentId()).get();
				BeanUtils.copyProperties(commentParent, responseParent);
				responseParent.setUserId(getUSerComment(commentParent.getUserId()));
				response.setComemntParent(responseParent);
			}
			
			sendNotify(post.get(), commentSave);
			
			responseEntity.setData(response);
			responseEntity.setStatus(1);
			responseEntity.setErrorList(null);
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}

	}

	private void sendNotify(Post post, Comment commentSave) {
		PostDto postDto = new PostDto();
		postDto.setPostId(post.getId());
		postDto.setUserReciver(getUser(commentSave.getUserId()));
		postDto.setUserSender(getUser(post.getUserId()));
		postDto.setAction(SCMConstant.POST_COMMNET);
		postDto.setCommentId(commentSave.getId());
		
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

	private CreateCommentResponse.User getUSerComment(Long userId) {

		MultiValueMap<String, Object> request = new LinkedMultiValueMap<String, Object>();
		request.add("id", userId);

		CreateCommentResponse.User userChatParent = null;
		try {
			userChatParent = objectMapper.readValue(callApiService.callPostMenthodForParam(request,
					CallApiService.USER_SERVICE, UrlConstant.USER_FINDBYID), CreateCommentResponse.User.class);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}

		return userChatParent;
	}

	public void processGetAllComment(Long postId, ApiResponseEntity responseEntity, UserDetail userDetail) {
		List<Comment> comments = commentRepository.findByPostId(postId, Sort.by(Sort.Direction.DESC, "createAt"));
		List<CreateCommentResponse> commentResponses = comments.stream().map(c->{
			CreateCommentResponse response = new CreateCommentResponse();
			BeanUtils.copyProperties(c, response);
			response.setUserId(getUSerComment(c.getUserId()));
			if(c.getComemntParentId()!= null) {
				CreateCommentResponse responseParent = new CreateCommentResponse();
				Comment commentParent = commentRepository.findById(c.getComemntParentId()).get();
				BeanUtils.copyProperties(commentParent, responseParent);
				responseParent.setUserId(getUSerComment(commentParent.getUserId()));
				response.setComemntParent(responseParent);
			}
			
			return response;
		}).collect(Collectors.toList());
		
		responseEntity.setData(commentResponses);
		responseEntity.setStatus(1);
		responseEntity.setErrorList(null);
	}
}
