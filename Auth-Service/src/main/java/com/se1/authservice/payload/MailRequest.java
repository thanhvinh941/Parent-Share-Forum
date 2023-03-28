package com.se1.authservice.payload;

import java.util.Map;

import lombok.Data;

@Data
public class MailRequest {

	private String to;
	private String mailTemplate;
	private Map<String, Object> data;
}
