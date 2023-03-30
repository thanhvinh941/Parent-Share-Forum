package com.se1.systemservice.domain.payload.request;

import java.util.Map;

import lombok.Data;

@Data
public class MailRequest {

	private String to;
	private String mailTemplate;
	private Map<String, Object> data;
}
