package com.se1.postservice.domain.payload;

import lombok.Data;

@Data
public class WebsocketResponse {

	private String action;
	private Object data;
}
