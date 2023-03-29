package com.se1.chatservice.payload;

import lombok.Data;

@Data
public class RabbitRequest {

	private String action;
	private Object data;
}
