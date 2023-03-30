package com.se1.chatservice.payload;

import lombok.Data;

@Data
public class UpdateChatRequest {
	private long id;
	private Long userId;
	private String action;
}
