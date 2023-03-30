package com.se1.systemservice.domain.rabbitMQ.dto;

import java.util.Date;

import lombok.Data;

@Data
public class ChatDto {
    private Long id;
	private User user;
	private String content;
	private int status;
	private String topicId;
	private ChatDto chatParent;
	private Date createAt;
	private Long userDeleteId;
	
	@Data
	public static class User {
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
