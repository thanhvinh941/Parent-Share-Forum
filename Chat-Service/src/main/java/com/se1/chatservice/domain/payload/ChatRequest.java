package com.se1.chatservice.domain.payload;

import java.util.Map;

import lombok.Data;

@Data
public class ChatRequest {

	private String action;
	private Map<String, Object> record;
//	private Long id;
//	private String content;
//	private long userId;
//	private String topicId;
//	private Long chatParentId;
//	private int status; // 1: display, 0: no display, 2: thu hoi, 3
}
