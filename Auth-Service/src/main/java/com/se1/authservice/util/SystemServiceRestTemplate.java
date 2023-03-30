package com.se1.authservice.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.se1.authservice.payload.ApiResponseEntity;
import com.se1.authservice.payload.MailRequest;
import com.se1.authservice.payload.UserRequestDto;

@Component
public class SystemServiceRestTemplate {

	@Autowired
	RestTemplate restTemplate;
	
	@Autowired
	ObjectMapper mapper;
	
	public void sendMail(MailRequest mailRequest) throws JsonProcessingException {
		
		String requestJson = mapper.writeValueAsString(mailRequest);
		
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		
		HttpEntity<String> entity = new HttpEntity<String>(requestJson,headers);
		
        restTemplate.postForEntity(
                "lb://system-service/system/send-mail",
                entity,
                ApiResponseEntity.class);
	}
}
