package com.se1.postservice.domain.payload.request;

import lombok.Data;

@Data
public class RabbitRequest {

	private String action;
	private Object data;
}
