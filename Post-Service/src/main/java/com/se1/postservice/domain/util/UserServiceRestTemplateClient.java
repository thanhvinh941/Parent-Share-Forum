package com.se1.postservice.domain.util;

import java.util.ArrayList;
import java.util.List;

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
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.se1.postservice.domain.payload.ApiResponseEntity;
import com.se1.postservice.domain.payload.ContactDto;
import com.se1.postservice.domain.payload.GetPostResponseDto;
import com.se1.postservice.domain.payload.SubscribeDto;

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
	
	public List<ContactDto> getListFriend(Long id) throws JsonMappingException, JsonProcessingException{
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

		MultiValueMap<String, Long> map= new LinkedMultiValueMap<String, Long>();
		map.add("id", id);

		HttpEntity<MultiValueMap<String, Long>> request = new HttpEntity<MultiValueMap<String, Long>>(map, headers);
		
		ResponseEntity<ApiResponseEntity> restExchange =
                restTemplate.postForEntity(
                        "http://localhost:8088/contact/internal/getListContact",
                        request,
                        ApiResponseEntity.class);
		
		List<Object> objects = mapper.readValue(mapper.writeValueAsString(restExchange.getBody().getData()), List.class);
		List<ContactDto> contactDtos = new ArrayList<>();
		for(Object object : objects) {
			contactDtos.add(mapper.readValue(mapper.writeValueAsString(object), ContactDto.class));
		}
        return contactDtos;
	}

	public List<SubscribeDto> getAllExpertSubscribe(Long userId) throws JsonMappingException, JsonProcessingException {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

		MultiValueMap<String, Long> map= new LinkedMultiValueMap<String, Long>();
		map.add("id", userId);

		HttpEntity<MultiValueMap<String, Long>> request = new HttpEntity<MultiValueMap<String, Long>>(map, headers);
		
		ResponseEntity<ApiResponseEntity> restExchange =
                restTemplate.postForEntity(
                        "http://localhost:8088/subscriber/internal/getAllExpertSubscribe",
                        request,
                        ApiResponseEntity.class);
		List<Object> objects = mapper.readValue(mapper.writeValueAsString(restExchange.getBody().getData()), List.class);
		List<SubscribeDto> subscribeDtos = new ArrayList<>();
		for(Object object : objects) {
			subscribeDtos.add(mapper.readValue(mapper.writeValueAsString(object), SubscribeDto.class));
		}
        return subscribeDtos;
	}

}
