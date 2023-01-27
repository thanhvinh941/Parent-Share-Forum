package com.se1.postservice.domain.service;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.se1.postservice.api.form.ApiResponseEntity;
import com.se1.postservice.domain.entity.Post;

@Service
public class CallApiService {

	public static final String POST_HOST = "http://localhost:8082";
	
	@Autowired
	private RestTemplate restTemplate;

	public ApiResponseEntity callApiPost(String host, String api, Object request) throws URISyntaxException {
		URI uri = new URI(host + api);
		ApiResponseEntity<?> apiResponseEntity = (ApiResponseEntity) restTemplate.postForObject(uri, request, ApiResponseEntity.class);
		return apiResponseEntity;
	}
}
