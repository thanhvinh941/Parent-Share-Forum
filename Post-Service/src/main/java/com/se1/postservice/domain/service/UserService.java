package com.se1.postservice.domain.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.se1.postservice.domain.payload.ApiResponseEntity;
import com.se1.postservice.domain.payload.UserPost;
import com.se1.postservice.domain.util.UserServiceRestTemplateClient;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

	private final ObjectMapper objectMapper;
	private final UserServiceRestTemplateClient restTemplateClient;
	
	public UserPost findById(Integer id) throws JsonMappingException, JsonProcessingException {
		UserPost user = null;

		ApiResponseEntity apiResponseEntityResult = (ApiResponseEntity) restTemplateClient.findById(id.longValue());
		if (apiResponseEntityResult.getStatus() == 1) {
			String apiResultStr = objectMapper.writeValueAsString(apiResponseEntityResult.getData());
			user = objectMapper.readValue(apiResultStr, UserPost.class);
		}
		return user;
	}
}
