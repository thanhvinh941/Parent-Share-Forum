package com.se1.userservice.controller;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.se1.userservice.domain.model.AuthProvider;
import com.se1.userservice.domain.model.User;
import com.se1.userservice.domain.model.UserRole;
import com.se1.userservice.domain.payload.ApiResponseEntity;
import com.se1.userservice.domain.payload.UserDetail;
import com.se1.userservice.domain.payload.UserRequestDto;
import com.se1.userservice.domain.payload.UserResponseDto;
import com.se1.userservice.domain.repository.UserRepository;
import com.se1.userservice.domain.service.UserService;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/user/internal")
@RequiredArgsConstructor
public class UserInternalController {

	private final UserService service;

	private final UserRepository repository;


	@PostMapping("/findAllExpert")
	public ResponseEntity<?> findAllExpert(@RequestParam("offset") Integer offset)
			throws JsonMappingException, JsonProcessingException {
		ApiResponseEntity apiResponseEntity = new ApiResponseEntity();
		try {
			Object response = service.findAllExpert(null, offset);
			apiResponseEntity.setData(response);
			apiResponseEntity.setErrorList(null);
			apiResponseEntity.setStatus(1);
		} catch (Exception e) {
			apiResponseEntity.setData(null);
			apiResponseEntity.setErrorList(List.of(e.getMessage()));
			apiResponseEntity.setStatus(0);
		}
		return ResponseEntity.ok().body(apiResponseEntity);
	}

	@PostMapping("/save")
	public ResponseEntity<?> save(@RequestBody UserRequestDto userRequestDto) {

		User user = convertUserRequestDtoToNewUserEntity(userRequestDto);

		User userSave = null;
		try {
			userSave = service.save(user);

			UserResponseDto userResponseDto = convertUserEntityToUserResponseEntity(userSave);
			return this.okResponse(userResponseDto, null);
		} catch (Exception e) {
			return this.badResponse(List.of(e.getMessage()));
		}
	}

	@PostMapping("/findByEmail")
	public ResponseEntity<?> findByEmail(@RequestParam("email") String email) throws Exception {
		ApiResponseEntity apiResponseEntity = new ApiResponseEntity();
		try {
			service.processFindUserByEmail(email, apiResponseEntity);
		} catch (Exception e) {
			apiResponseEntity.setData(null);
			apiResponseEntity.setErrorList(List.of(e.getMessage()));
			apiResponseEntity.setStatus(0);
		}
		return ResponseEntity.ok().body(apiResponseEntity);
	}

	@PostMapping("/findById")
	public ResponseEntity<?> findById(@RequestParam("id") Long id) throws Exception {
		ApiResponseEntity apiResponseEntity = new ApiResponseEntity();
		try {
			Object response = service.processFindUserById(null, id);
			apiResponseEntity.setData(response);
			apiResponseEntity.setErrorList(null);
			apiResponseEntity.setStatus(1);
		} catch (Exception e) {
			apiResponseEntity.setData(null);
			apiResponseEntity.setErrorList(List.of(e.getMessage()));
			apiResponseEntity.setStatus(0);
		}
		return ResponseEntity.ok().body(apiResponseEntity);
	}

	@PostMapping("/existsByEmail")
	public ResponseEntity<?> existsByEmail(@RequestParam("email") String email) {

		Boolean existsByEmail = repository.existsByEmail(email);

		try {
			return this.okResponse(existsByEmail, null);
		} catch (Exception e) {
			return this.badResponse(List.of(e.getMessage()));
		}
	}

	@PostMapping("/updateStatus")
	public ResponseEntity<?> updateStatus(@RequestParam("id") Long id, @RequestParam("status") Integer status) {
		ApiResponseEntity apiResponseEntity = new ApiResponseEntity();
		try {
			service.processUpdateStatus(id, status, apiResponseEntity);
		} catch (Exception e) {
			apiResponseEntity.setData(null);
			apiResponseEntity.setErrorList(List.of(e.getMessage()));
			apiResponseEntity.setStatus(0);
		}
		return ResponseEntity.ok().body(apiResponseEntity);
	}

	@PostMapping("/updateEmailStatus")
	public ResponseEntity<?> updateEmailStatus(@RequestParam("id") Long id) {
		ApiResponseEntity apiResponseEntity = new ApiResponseEntity();
		try {
			Object response = service.processUpdateEmailStatus(id);
			apiResponseEntity.setData(response);
			apiResponseEntity.setErrorList(null);
			apiResponseEntity.setStatus(1);
		} catch (Exception e) {
			apiResponseEntity.setData(null);
			apiResponseEntity.setErrorList(List.of(e.getMessage()));
			apiResponseEntity.setStatus(0);
		}
		return ResponseEntity.ok().body(apiResponseEntity);
	}

	private User convertUserRequestDtoToNewUserEntity(UserRequestDto userRequestDto) {
		User user = new User();
		user.setId(userRequestDto.getId() != null ? userRequestDto.getId() : null);
		user.setName(userRequestDto.getName());
		user.setEmail(userRequestDto.getEmail());
		user.setImageUrl(userRequestDto.getImageUrl());
		user.setEmailVerified(false);
		user.setPassword(userRequestDto.getPassword());
		user.setProvider(AuthProvider.valueOf(userRequestDto.getProvider()));
		user.setProviderId(userRequestDto.getProviderId());
		user.setRole(UserRole.valueOf(userRequestDto.getRole()));
		user.setTopicId(UUID.randomUUID().toString());
		user.setIsExpert(false);
		user.setStatus(new Byte("0"));
		user.setDelFlg(false);
		user.setCreateAt(new Date());
		user.setUpdateAt(new Date());
		user.setLastTime(new Date());

		return user;
	}

	private UserResponseDto convertUserEntityToUserResponseEntity(User user) {
		UserResponseDto userResponseDto = null;
		if (user != null) {
			userResponseDto = new UserResponseDto();
			userResponseDto.setId(user.getId());
			userResponseDto.setEmail(user.getEmail());
			userResponseDto.setEmailVerified(user.getEmailVerified());
			userResponseDto.setImageUrl(user.getImageUrl());
			userResponseDto.setName(user.getName());
			userResponseDto.setPassword(user.getPassword());
			userResponseDto.setProvider(user.getProvider());
			userResponseDto.setProviderId(user.getProviderId());
			userResponseDto.setRole(user.getRole().name());
			userResponseDto.setStatus(user.getStatus());
		}

		return userResponseDto;
	}

	private ResponseEntity<?> badResponse(List<String> errorMessage) {
		ApiResponseEntity apiResponseEntity = new ApiResponseEntity();
		apiResponseEntity.setData(null);
		apiResponseEntity.setErrorList(errorMessage);
		apiResponseEntity.setStatus(0);
		return ResponseEntity.badRequest().body(apiResponseEntity);
	}

	private ResponseEntity<?> okResponse(Object data, List<String> errorMessage) {
		ApiResponseEntity apiResponseEntity = new ApiResponseEntity();
		apiResponseEntity.setData(data);
		apiResponseEntity.setErrorList(errorMessage);
		apiResponseEntity.setStatus(1);
		return ResponseEntity.ok().body(apiResponseEntity);
	}
}
