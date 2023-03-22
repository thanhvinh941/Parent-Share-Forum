package com.se1.systemservice.payload;

import lombok.Data;

@Data
public class ChatMqRequest {
	private String content;
	private long userId;
	private String topicId;
	private Long chatParentId;
}
