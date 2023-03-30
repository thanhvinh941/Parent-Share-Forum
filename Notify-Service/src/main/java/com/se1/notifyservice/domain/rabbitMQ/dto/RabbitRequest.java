package com.se1.notifyservice.domain.rabbitMQ.dto;

import lombok.Data;

@Data
public class RabbitRequest {

	private String action;
	private Object data;
}
