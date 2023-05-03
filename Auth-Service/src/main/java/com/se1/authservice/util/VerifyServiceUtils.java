package com.se1.authservice.util;

import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.se1.authservice.config.UrlConstance;
import com.se1.authservice.model.User;
import com.se1.authservice.service.CallApiService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class VerifyServiceUtils {

	private final ObjectMapper mapper;
	private final CallApiService<User> apiService;
	
	public Boolean createVerify(MultiValueMap<String, String> request) throws JsonProcessingException {
		String resultStr = apiService.callPostMenthodForParam(request, CallApiService.USER_SERVICE, UrlConstance.VERIFY_CREATE);
		return mapper.readValue(resultStr, Boolean.class);
	}

}
