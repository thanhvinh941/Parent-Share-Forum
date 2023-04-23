package com.se1.chatservice.payload;

import lombok.Data;

@Data
public class UpdateChatRequest {
	private Long id;
	private String content;
	private Long userId;
	private String topicId;
	private Long chatParentId;
	private Boolean isFile;
	private String action;
}
