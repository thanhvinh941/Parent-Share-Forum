package com.se1.userservice.domain.service;

import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.se1.userservice.domain.payload.ApiResponseEntity;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class CallApiService2 {
	private final RestTemplate restTemplate;

	private final ObjectMapper objectMapper;

	private final Environment environment;

	public final static String CHAT_SERVICE = "micro-service.chat-service";
	public final static String USER_SERVICE = "micro-service.user-service";
	public final static String POST_SERVICE = "micro-service.post-service";
	public final static String NOTIFY_SERVICE = "micro-service.notify-service";
	public final static String SYSTEM_SERVICE = "micro-service.system-service";

	public <T> T callPostMenthodForObject(Object requestObject, String target, String path, TypeReference<T> returnType)
			throws JsonProcessingException {
		long cp1 = System.currentTimeMillis();
		String url = getFullUrl(target, path);
		String requestJson = objectMapper.writeValueAsString(requestObject);

		HttpEntity<String> request = new HttpEntity<String>(requestJson, getJsonHeaders());
		ResponseEntity<ApiResponseEntity> restExchange = 
				restTemplate.postForEntity(url, request, ApiResponseEntity.class);

		if(restExchange.getBody().getStatus().equals(1) && restExchange.getBody().getData() != null) {
			long cp2 = System.currentTimeMillis();
			return objectMapper.convertValue(restExchange.getBody().getData(), returnType);
		}
		return null;
	}

	private String getFullUrl(String target, String path) {
		String host = environment.getProperty(target);
		String fullUri = host + "/" + path;
		return fullUri;
	}

	private HttpHeaders getJsonHeaders() {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		return headers;
	}
}
