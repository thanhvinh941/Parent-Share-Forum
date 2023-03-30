package com.se1.chatservice.payload;

import lombok.Data;

@Data
public class UserDetail {
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
