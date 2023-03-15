package com.se1.commentservice.controller;

import java.util.Date;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.se1.commentservice.domain.entity.Comment;
import com.se1.commentservice.domain.payload.ApiResponseEntity;
import com.se1.commentservice.domain.payload.RegisCommentRequest;
import com.se1.commentservice.domain.payload.UserDetail;
import com.se1.commentservice.domain.service.CommentService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/comments")
@RequiredArgsConstructor
public class CommentController {

	private final CommentService commentService;
	private final ApiResponseEntity responseEntity;
	private final ObjectMapper objectMapper;
	
	@PostMapping("/create")
	public ResponseEntity<?> created(@RequestHeader("user_detail") String userDetailHeader,
			@RequestBody RegisCommentRequest request) {
		
		try {
			UserDetail userDetail = objectMapper.readValue(userDetailHeader, UserDetail.class);
			
			Comment commentSave = new Comment();
			commentSave.setContent(request.getContent());
			commentSave.setComemntParentId(request.getComemntParentId());
			commentSave.setPostId(request.getPostId());
			commentSave.setUserId(userDetail.getId());
			commentSave.setCreateAt(new Date());
			commentSave.setDelFlg(true);
			
			commentService.processCreat(commentSave, responseEntity);
		} catch (Exception e) {
			responseEntity.setData(null);
			responseEntity.setErrorList(List.of(e.getMessage()));
			responseEntity.setStatus(0);
		}

		return ResponseEntity.ok(responseEntity);
	}
}
