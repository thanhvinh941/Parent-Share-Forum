package com.se1.systemservice.payload;

import lombok.Data;

@Data
public class ChatRequest {
	private String content;
	private Long chatParent;
	private boolean isFile;
}
