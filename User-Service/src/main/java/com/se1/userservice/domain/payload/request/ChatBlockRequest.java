package com.se1.userservice.domain.payload.request;

import lombok.Data;

@Data
public class ChatBlockRequest {

	private String topicId;
	private Long userBlockedId;
}
