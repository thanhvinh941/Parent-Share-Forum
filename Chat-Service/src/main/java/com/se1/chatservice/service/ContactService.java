package com.se1.chatservice.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.se1.chatservice.payload.ContactDto;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ContactService {

	private final String FIND_CONTACT_BY_USER_ID_AND_TOPIC_ID = "contact/internal/findContactByUserIdAndTopicId";
	
	private final CallApiService<ContactDto> callApiService;
	
	private final ObjectMapper mapper;
	
	public ContactDto findContactByUserIdAndTopicId(String topicId,Long userId) throws JsonProcessingException {
		MultiValueMap<String, String> param = new LinkedMultiValueMap<>();
		param.set("topicId", topicId);
		param.set("userId", userId.toString());
		
		try {
			String contact = callApiService.callPostMenthodForParam(param, CallApiService.USER_SERVICE, FIND_CONTACT_BY_USER_ID_AND_TOPIC_ID);
			return mapper.readValue(contact, new TypeReference<List<ContactDto>>() {}).get(0);
		} catch (Exception e) {
			return null;
		}
	}
}
