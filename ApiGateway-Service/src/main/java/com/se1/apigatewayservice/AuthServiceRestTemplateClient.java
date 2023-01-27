package com.se1.apigatewayservice;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class AuthServiceRestTemplateClient {

	@Autowired
	RestTemplate restTemplate;
	
	public Boolean isTokenValid(String token) {
		
		Map<String, String> uriVariables = new HashMap<>();
		uriVariables.put("token", token);
		
		ResponseEntity<Boolean> restExchange =
                restTemplate.exchange(
                        "http://localhost:8089/auth/isTokenValid",
                        HttpMethod.GET,
                        null, Boolean.class, uriVariables);
        return restExchange.getBody();
	}
}
