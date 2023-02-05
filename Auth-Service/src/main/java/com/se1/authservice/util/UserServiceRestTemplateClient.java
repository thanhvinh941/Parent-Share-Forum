package com.se1.authservice.util;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.se1.authservice.payload.ApiResponseEntity;
import com.se1.authservice.payload.UserRequestDto;

@Component
public class UserServiceRestTemplateClient {

	@Autowired
	RestTemplate restTemplate;
	
	@Autowired
	ObjectMapper mapper;
	
	public Object saveUser(UserRequestDto userRequestDto) throws JsonProcessingException {
		
		String requestJson = mapper.writeValueAsString(userRequestDto);
		
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		
		HttpEntity<String> entity = new HttpEntity<String>(requestJson,headers);
		
		ResponseEntity<?> restExchange =
                restTemplate.postForEntity(
                        "http://localhost:8088/user/internal/save",
                        entity,
                        ApiResponseEntity.class);
        return restExchange.getBody();
	}
	
	public Object findByEmail(String email) {
		
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

		MultiValueMap<String, String> map= new LinkedMultiValueMap<String, String>();
		map.add("email", email);

		HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String, String>>(map, headers);

		ResponseEntity<?> restExchange =
                restTemplate.postForEntity(
                        "http://localhost:8088/user/internal/findByEmail",
                        request,
                        ApiResponseEntity.class);
        return restExchange.getBody();
	}
	
	public Object findById(Long id) {
		
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

		MultiValueMap<String, Long> map= new LinkedMultiValueMap<String, Long>();
		map.add("id", id);

		HttpEntity<MultiValueMap<String, Long>> request = new HttpEntity<MultiValueMap<String, Long>>(map, headers);
		
		ResponseEntity<?> restExchange =
                restTemplate.postForEntity(
                        "http://localhost:8088/user/internal/findById",
                        request,
                        ApiResponseEntity.class);
        return restExchange.getBody();
	}

	public Object existsByEmail(String email) {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

		MultiValueMap<String, String> map= new LinkedMultiValueMap<String, String>();
		map.add("email", email);

		HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String, String>>(map, headers);

		ResponseEntity<?> restExchange =
                restTemplate.postForEntity(
                        "http://localhost:8088/user/internal/existsByEmail",
                        request,
                        ApiResponseEntity.class);
        return restExchange.getBody();
	}
}
