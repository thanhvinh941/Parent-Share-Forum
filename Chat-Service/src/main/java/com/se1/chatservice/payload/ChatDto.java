package com.se1.chatservice.payload;

import java.util.Date;

import lombok.Data;

@Data
public class ChatDto {
    private Long id;
	private User userId;
	private String content;
	private int status;
	private String topicId;
	private Long chatParent;
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
