package com.se1.systemservice.payload;

import lombok.Data;

@Data
public class ChatRequest {
	private Long userId;
	private String content;
}
