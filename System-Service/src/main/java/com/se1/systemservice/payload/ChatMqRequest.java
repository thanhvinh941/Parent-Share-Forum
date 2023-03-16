package com.se1.systemservice.payload;

import java.util.Map;

import lombok.Data;

@Data
public class ChatMqRequest {
	private String action;
	private Map<String, Object> record;
}
