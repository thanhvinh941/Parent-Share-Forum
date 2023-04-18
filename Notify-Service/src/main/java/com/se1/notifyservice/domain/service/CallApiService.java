package com.se1.notifyservice.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.se1.notifyservice.domain.payload.ApiResponseEntity;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CallApiService<T> {

	@Autowired
	private final RestTemplate restTemplate;

	@Autowired
	private final ObjectMapper objectMapper;

	private final Environment environment;

	public final static String USER_SERVICE = "micro-service.user-service";

	public final static String NOTIFY_SERVICE = "micro-service.notify-service";

	public final static String POST_SERVICE = "micro-service.post-service";

	public final static String SYSTEM_SERVICE = "micro-service.system-service";

	public String callPostMenthodForParam(MultiValueMap<String, String> param, String target, String path)
			throws JsonProcessingException {

		String url = getFullUrl(target, path);

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

		HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String, String>>(param, headers);

		ResponseEntity<ApiResponseEntity> restExchange = restTemplate.postForEntity(url, request,
				ApiResponseEntity.class);

		String restExchangeSrt = objectMapper.writeValueAsString(restExchange.getBody().getData());
		T apiResult = objectMapper.readValue(restExchangeSrt, new TypeReference<T>() {
		});
		return objectMapper.writeValueAsString(apiResult);
	}

	public String callPostMenthodForObject(Object requestObject, String target, String path)
			throws JsonProcessingException {

		String url = getFullUrl(target, path);

		String requestJson = objectMapper.writeValueAsString(requestObject);
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<String> request = new HttpEntity<String>(requestJson, headers);

		ResponseEntity<ApiResponseEntity> restExchange = restTemplate.postForEntity(url, request, ApiResponseEntity.class);

		String restExchangeSrt = objectMapper.writeValueAsString(restExchange.getBody().getData());
		T apiResult = objectMapper.readValue(restExchangeSrt, new TypeReference<T>() {
		});
		return objectMapper.writeValueAsString(apiResult);
	}

	private String getFullUrl(String target, String path) {
		String host = environment.getProperty(target);
		String fullUri = host + "/" + path;
		return fullUri;
	}
}
