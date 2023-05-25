package com.se1.systemservice.domain.payload.dto;

import lombok.Data;

@Data
public class ChatBlockDto {
    private Long id;
	private Long userBlockId; // người chặn tin nhắn
	private Long userBlockedId; // người bị chặn tin nhắn
	private String topicId;
}
