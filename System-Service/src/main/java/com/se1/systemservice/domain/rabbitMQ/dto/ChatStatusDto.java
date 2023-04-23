package com.se1.systemservice.domain.rabbitMQ.dto;

import lombok.Data;

@Data
public class ChatStatusDto {
	private Long chatId;
	private String chatStatus;
	private String topicId;
	private Object chat;
}
