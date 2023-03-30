package com.se1.postservice.domain.payload;

import lombok.Data;

@Data
public class NotifycationDto {

	private String action;
	private String topicUserId;
	private Object request;
}
