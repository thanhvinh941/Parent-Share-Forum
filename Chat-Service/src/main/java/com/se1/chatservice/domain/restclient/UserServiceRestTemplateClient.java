package com.se1.chatservice.domain.restclient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.se1.chatservice.payload.ApiResponseEntity;

@Component
public class UserServiceRestTemplateClient {

	@Autowired
	RestTemplate restTemplate;
	
	@Autowired
	ObjectMapper mapper;
	
	
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

}
