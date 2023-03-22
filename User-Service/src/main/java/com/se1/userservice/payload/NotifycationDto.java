package com.se1.userservice.payload;

import lombok.Data;

@Data
public class NotifycationDto {

	private String action;
	private String topicUserId;
	private Object request;
}
