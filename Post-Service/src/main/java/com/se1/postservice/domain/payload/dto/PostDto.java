package com.se1.postservice.domain.payload.dto;

import lombok.Data;

@Data
public class PostDto {

	private Long postId;
	private UserDetail userReciver;
	private UserDetail userSender;
	private String action;
	private Long commentId;
	
	@Data
	public static class UserDetail {
		private Long id;
		private String email;
		private String name;
		private String imageUrl;
		private String role;
		private Boolean isExpert;
		private Double rating;
		private int status;
		private String topicId;
	}
}
