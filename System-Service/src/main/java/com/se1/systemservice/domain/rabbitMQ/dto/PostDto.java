package com.se1.systemservice.domain.rabbitMQ.dto;

import com.se1.systemservice.domain.payload.dto.UserDetail;

import lombok.Data;

@Data
public class PostDto {
	
	private Long postId;
	private UserDetail userReciver;
	private UserDetail userSender;
	private String action;
	private Long commentId;
}
