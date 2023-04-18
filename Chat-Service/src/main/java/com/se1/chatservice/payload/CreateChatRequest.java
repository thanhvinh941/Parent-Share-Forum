package com.se1.chatservice.payload;

import lombok.Data;

@Data
public class CreateChatRequest {
	private String content;
	private long userId;
	private String topicId;
	private Long chatParentId;
	private Boolean isFile;
}
