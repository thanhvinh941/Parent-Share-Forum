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

import com.se1.authservice.payload.ApiResponseEntity;
import com.se1.authservice.payload.UserRequestDto;

@Component
public class UserServiceRestTemplateClient {

	@Autowired
	RestTemplate restTemplate;
	
	public Object saveUser(UserRequestDto userRequestDto) {
		ResponseEntity<?> restExchange =
                restTemplate.exchange(
                        "http://localhost:8088/user/internal/save",
                        HttpMethod.POST,
                        null, ApiResponseEntity.class, userRequestDto);
        return restExchange.getBody();
	}
	
	public Object findByEmail(String email) {
		Map<String, String> uriVariables = new HashMap<>();
		uriVariables.put("email", email);
		
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
		Map<String, Long> uriVariables = new HashMap<>();
		uriVariables.put("id", id);
		
		ResponseEntity<?> restExchange =
                restTemplate.exchange(
                        "http://localhost:8088/user/internal/findById",
                        HttpMethod.POST,
                        null, ApiResponseEntity.class, uriVariables);
        return restExchange.getBody();
	}

	public Boolean existsByEmail(String email) {
		Map<String, String> uriVariables = new HashMap<>();
		uriVariables.put("email", email);
		
		ResponseEntity<?> restExchange =
                restTemplate.exchange(
                        "http://localhost:8088/user/internal/existsByEmail",
                        HttpMethod.POST,
                        null, Boolean.class, uriVariables);
        return (Boolean) restExchange.getBody();
	}
}
