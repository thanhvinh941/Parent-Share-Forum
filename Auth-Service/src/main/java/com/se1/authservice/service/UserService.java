package com.se1.authservice.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.se1.authservice.model.AuthProvider;
import com.se1.authservice.model.User;
import com.se1.authservice.payload.ApiResponseEntity;
import com.se1.authservice.payload.UserRequestDto;
import com.se1.authservice.util.UserServiceRestTemplateClient;

@Service
public class UserService {

	@Autowired
	ObjectMapper objectMapper;

	@Autowired
	UserServiceRestTemplateClient restTemplateClient;

	public User save(User userSave) throws JsonMappingException, JsonProcessingException {
		User user = null;

		UserRequestDto userRequestDto = convertUserToUserRequestDto(userSave);

		ApiResponseEntity apiResponseEntityResult = (ApiResponseEntity) restTemplateClient
				.saveUser(userRequestDto);
		if (apiResponseEntityResult.getStatus() == 1) {
			String apiResultStr = objectMapper.writeValueAsString(apiResponseEntityResult.getData());
			user = objectMapper.readValue(apiResultStr, User.class);
		}
		return user;
	}

	public Optional<User> findByEmail(String email) throws JsonProcessingException {
		User user = null;

		ApiResponseEntity apiResponseEntityResult = (ApiResponseEntity) restTemplateClient.findByEmail(email);
		if (apiResponseEntityResult.getStatus() == 1 && apiResponseEntityResult.getData() != null) {
			String apiResultStr = objectMapper.writeValueAsString(apiResponseEntityResult.getData());
			user = objectMapper.readValue(apiResultStr, User.class);
		}
		
		if(user != null) {
			return Optional.of(user);
		}

		return Optional.empty();
	}

	public User findById(Integer id) throws JsonMappingException, JsonProcessingException {
		User user = null;

		ApiResponseEntity apiResponseEntityResult = (ApiResponseEntity) restTemplateClient.findById(id.longValue());
		if (apiResponseEntityResult.getStatus() == 1) {
			String apiResultStr = objectMapper.writeValueAsString(apiResponseEntityResult.getData());
			user = objectMapper.readValue(apiResultStr, User.class);
		}
		return user;
	}

	public boolean existsByEmail(String email) throws JsonProcessingException {
		boolean existsEmail = false;
		
		ApiResponseEntity apiResponseEntityResult = (ApiResponseEntity) restTemplateClient.existsByEmail(email);
		if (apiResponseEntityResult.getStatus() == 1) {
			String apiResultStr = objectMapper.writeValueAsString(apiResponseEntityResult.getData());
			existsEmail = objectMapper.readValue(apiResultStr, Boolean.class);
		}
		
		return existsEmail;
	}
	
	private boolean isSocalProvider(AuthProvider authProvider) {
		return authProvider == AuthProvider.google || authProvider == authProvider.facebook
				|| authProvider == AuthProvider.github;
	}

	private UserRequestDto convertUserToUserRequestDto(User userSave) {
		UserRequestDto userRequestDto = new UserRequestDto();
		userRequestDto.setId(userSave.getId() != null ? userSave.getId() : null);
		userRequestDto.setName(userSave.getName());
		userRequestDto.setEmail(userSave.getEmail());
		userRequestDto.setImageUrl(userSave.getImageUrl() != null ? userRequestDto.getImageUrl() : null);
		userRequestDto.setEmailVerified(isSocalProvider(userSave.getProvider()) ? true : false);
		userRequestDto.setPassword(userSave.getPassword());
		userRequestDto.setProvider(userSave.getProvider().name());
		userRequestDto.setProviderId(userSave.getProviderId() != null ? userSave.getProviderId() : null);
		userRequestDto.setRole("user");

		return userRequestDto;
	}

	
}
