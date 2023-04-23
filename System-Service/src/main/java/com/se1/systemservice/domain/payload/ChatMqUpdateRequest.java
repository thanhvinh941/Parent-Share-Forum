package com.se1.systemservice.domain.payload;

import lombok.Data;

@Data
public class ChatMqUpdateRequest {
	private Long id;
	private String content;
	private Long userId;
	private String topicId;
	private Long chatParentId;
	private Boolean isFile;
	private String action;
}
