package com.se1.systemservice.domain.restclient;

import java.net.MalformedURLException;
import java.net.URL;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.se1.systemservice.domain.payload.ApiResponseEntity;

@Component
public class UserServiceRestTemplateClient {

	@Autowired
	RestTemplate restTemplate;
	
	@Autowired
	ObjectMapper mapper;
	
	@Value("${micro-service.user-service}")
	String userHost;
	
	public Object updateStatus(Long id, int status) throws MalformedURLException {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

		MultiValueMap<String, Object> map= new LinkedMultiValueMap<String, Object>();
		map.add("id", id);
		map.add("status", status);
		
		HttpEntity<MultiValueMap<String, Object>> request = new HttpEntity<MultiValueMap<String, Object>>(map, headers);
		
		ResponseEntity<?> restExchange =
                restTemplate.postForEntity(
                		userHost + "/user/internal/updateStatus",
                        request,
                        ApiResponseEntity.class);
        return restExchange.getBody();
	}
	
	public Object getContactByTopicId(String topicId) throws MalformedURLException {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

		MultiValueMap<String, Object> map= new LinkedMultiValueMap<String, Object>();
		map.add("topicId", topicId);
		
		HttpEntity<MultiValueMap<String, Object>> request = new HttpEntity<MultiValueMap<String, Object>>(map, headers);
		
		ResponseEntity<?> restExchange =
                restTemplate.postForEntity(
                		userHost + "/contact/internal/getContactByTopicId",
                        request,
                        ApiResponseEntity.class);
        return restExchange.getBody();
	}

}
