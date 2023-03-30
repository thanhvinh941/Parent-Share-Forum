package com.se1.userservice.domain.payload.response;

import lombok.Data;

@Data
public class RabbitRequest {

	private String action;
	private Object data;
}
