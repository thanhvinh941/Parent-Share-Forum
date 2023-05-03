package com.se1.authservice.service;

import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.se1.authservice.config.UrlConstance;
import com.se1.authservice.payload.Verification;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class VerifyService {
	private final CallApiService<Verification> apiService;
	private final ObjectMapper mapper;
	public Verification findByToken(MultiValueMap<String, String> request) throws JsonMappingException, JsonProcessingException {
		String resultStr = apiService.callPostMenthodForParam(request, CallApiService.USER_SERVICE, UrlConstance.VERIFY_FINDBYTOKEN);
		return mapper.readValue(resultStr, Verification.class);
	}
}
