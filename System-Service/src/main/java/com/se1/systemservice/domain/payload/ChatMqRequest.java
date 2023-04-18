package com.se1.systemservice.domain.payload;

import lombok.Data;

@Data
public class ChatMqRequest {
	private String content;
	private long userId;
	private String topicId;
	private Long chatParentId;
	private Boolean isFile;
}
