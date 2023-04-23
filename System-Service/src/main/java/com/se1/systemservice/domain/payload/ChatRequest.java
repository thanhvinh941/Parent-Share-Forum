package com.se1.systemservice.domain.payload;

import lombok.Data;

@Data
public class ChatRequest {
	private String userId;
	private Long id;
	private String content;
	private Long chatParent;
	private Boolean isFile;
	private String action;
	private Long userDelete;
}
