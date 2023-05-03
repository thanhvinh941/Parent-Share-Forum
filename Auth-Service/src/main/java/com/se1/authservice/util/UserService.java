package com.se1.authservice.util;

import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.se1.authservice.config.UrlConstance;
import com.se1.authservice.model.User;
import com.se1.authservice.payload.UserRequestDto;
import com.se1.authservice.service.CallApiService;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class UserService {

	private final ObjectMapper mapper;
	
	private final CallApiService<User> apiService;

	public User saveUser(UserRequestDto userRequestDto) throws JsonProcessingException {
		String resultStr = apiService.callPostMenthodForObject(userRequestDto, CallApiService.USER_SERVICE, UrlConstance.USER_SAVE);
		return mapper.readValue(resultStr, User.class);
	}

	public User findByCondition(MultiValueMap<String, String> request, String url) throws JsonProcessingException {
		String resultStr = apiService.callPostMenthodForParam(request, CallApiService.USER_SERVICE, url);
		return mapper.readValue(resultStr, User.class);
	}

	public Boolean existsByEmail(MultiValueMap<String, String> request) throws JsonProcessingException {
		String resultStr = apiService.callPostMenthodForParam(request, CallApiService.USER_SERVICE, UrlConstance.USER_EXIST_BY_EMAIL);
		return mapper.readValue(resultStr, Boolean.class);
	}
	
	public Boolean updateEmailStatus(MultiValueMap<String, String> request) throws JsonProcessingException {
		String resultStr = apiService.callPostMenthodForParam(request, CallApiService.USER_SERVICE, UrlConstance.USER_UPDATE_EMAIL_STATUS);
		return mapper.readValue(resultStr, Boolean.class);
	}

}
