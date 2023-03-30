package com.se1.chatservice.payload;

import lombok.Data;

@Data
public class GetAllChatRequest {

	private String topicId;
	private int limit;
	private int offset;
}
