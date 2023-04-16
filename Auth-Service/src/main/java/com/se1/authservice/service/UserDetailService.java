package com.se1.authservice.service;

import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.se1.authservice.config.UrlConstance;
import com.se1.authservice.model.AuthProvider;
import com.se1.authservice.model.User;
import com.se1.authservice.payload.UserRequestDto;
import com.se1.authservice.util.UserService;
import com.se1.authservice.util.VerifyService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserDetailService {

	private final UserService userService;
	private final VerifyService verifyService;

	public User save(User userSave) throws JsonMappingException, JsonProcessingException {
		UserRequestDto userRequestDto = convertUserToUserRequestDto(userSave);
		return userService.saveUser(userRequestDto);
	}

	public Optional<User> findByEmail(String email) throws JsonProcessingException {
		MultiValueMap<String, String> map = new LinkedMultiValueMap<String, String>();
		map.add("email", email);

		User user = userService.findByCondition(map, UrlConstance.USER_FIND_BY_EMAIL);

		if (user != null) {
			return Optional.of(user);
		}

		return Optional.empty();
	}

	public User findById(Integer id) throws JsonMappingException, JsonProcessingException {
		MultiValueMap<String, String> map = new LinkedMultiValueMap<String, String>();
		map.add("id", id.toString());

		return userService.findByCondition(map, UrlConstance.USER_FIND_BY_ID);
	}

	public Boolean existsByEmail(String email) throws JsonProcessingException {
		MultiValueMap<String, String> map = new LinkedMultiValueMap<String, String>();
		map.add("email", email);
		
		return userService.existsByEmail(map);
	}

	private boolean isSocalProvider(AuthProvider authProvider) {
		return authProvider == AuthProvider.google || authProvider == AuthProvider.facebook
				|| authProvider == AuthProvider.github;
	}

	private UserRequestDto convertUserToUserRequestDto(User userSave) {
		UserRequestDto userRequestDto = new UserRequestDto();
		BeanUtils.copyProperties(userSave, userRequestDto);
		userRequestDto.setRole("user");
		userRequestDto.setEmailVerified(isSocalProvider(userSave.getProvider()) ? true : false);
		userRequestDto.setProvider(userSave.getProvider().name());
		userRequestDto.setProviderId(userSave.getProviderId() != null ? userSave.getProviderId() : null);
		userRequestDto.setRole("user");
		return userRequestDto;
	}

	public void sendMail(Long userId, String mail, String userName) throws JsonProcessingException {
		MultiValueMap<String, String> map = new LinkedMultiValueMap<String, String>();
		map.add("email", mail);
		map.add("user_id", userId.toString());
		map.add("name", userName);
		
		verifyService.createVerify(map);
	}

}
