package com.se1.userservice.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.se1.userservice.ApiResponseEntity;
import com.se1.userservice.model.AuthProvider;
import com.se1.userservice.model.User;
import com.se1.userservice.model.UserRole;
import com.se1.userservice.payload.UserRequestDto;
import com.se1.userservice.payload.UserResponseDto;
import com.se1.userservice.repository.UserRepository;
import com.se1.userservice.service.UserService;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/user/internal")
@RequiredArgsConstructor
public class UserController {

	private final UserService service;
	private final UserRepository repository;
	
	private DateTimeFormatter localDateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	
	@PostMapping("/save")
	public ResponseEntity<?> save(@RequestBody UserRequestDto userRequestDto){
		ApiResponseEntity<UserResponseDto> apiResponseEntity = new ApiResponseEntity<>();
		
		User user = convertUserRequestDtoToNewUserEntity(userRequestDto);
		
		User userSave = null;
		try {
			userSave = service.save(user);
			
			UserResponseDto userResponseDto = convertUserEntityToUserResponseEntity(userSave);
			apiResponseEntity.setData(userResponseDto);
			apiResponseEntity.setErrorList(null);
			apiResponseEntity.setStatus(1);
		} catch (Exception e) {
			apiResponseEntity.setData(null);
			apiResponseEntity.setErrorList(List.of(e.getMessage()));
			apiResponseEntity.setStatus(0);
		}
		
		return ResponseEntity.ok(apiResponseEntity);
	}
	
	
	@PostMapping("/findByEmail")
	public ResponseEntity<?> findByEmail(@RequestParam("email") String email) throws Exception{
		ApiResponseEntity<UserResponseDto> apiResponseEntity = new ApiResponseEntity<>();

		User userFind = null;
		try {
			userFind = service.findByEmail(email);
			UserResponseDto userResponseDto = convertUserEntityToUserResponseEntity(userFind);
			apiResponseEntity.setData(userResponseDto);
			apiResponseEntity.setErrorList(null);
			apiResponseEntity.setStatus(1);
		} catch (Exception e) {
			apiResponseEntity.setData(null);
			apiResponseEntity.setErrorList(List.of(e.getMessage()));
			apiResponseEntity.setStatus(0);
		}
		
		return ResponseEntity.ok(apiResponseEntity);

	}
	
	@PostMapping("/findById")
	public ResponseEntity<?> findById(@RequestParam("Id") Long id) throws Exception{
		ApiResponseEntity<UserResponseDto> apiResponseEntity = new ApiResponseEntity<>();

		User userFind = null;
		try {
			userFind = service.findById(id);
			UserResponseDto userResponseDto = convertUserEntityToUserResponseEntity(userFind);
			apiResponseEntity.setData(userResponseDto);
			apiResponseEntity.setErrorList(null);
			apiResponseEntity.setStatus(1);
		} catch (Exception e) {
			apiResponseEntity.setData(null);
			apiResponseEntity.setErrorList(List.of(e.getMessage()));
			apiResponseEntity.setStatus(0);
		}
		
		return ResponseEntity.ok(apiResponseEntity);

	}
	
	@PostMapping("/existsByEmail")
	public ResponseEntity<?> existsByEmail(@RequestParam("email") String email){
		ApiResponseEntity<Boolean> apiResponseEntity = new ApiResponseEntity<>();

		Boolean existsByEmail = repository.existsByEmail(email);
	
		try {
			apiResponseEntity.setData(existsByEmail);
			apiResponseEntity.setErrorList(null);
			apiResponseEntity.setStatus(1);
		} catch (Exception e) {
			apiResponseEntity.setData(null);
			apiResponseEntity.setErrorList(List.of(e.getMessage()));
			apiResponseEntity.setStatus(0);
		}
		
		return ResponseEntity.ok(apiResponseEntity);
	}
	
	private User convertUserRequestDtoToNewUserEntity(UserRequestDto userRequestDto) {
		User user = new User();
		user.setName(userRequestDto.getName());
		user.setEmail(userRequestDto.getEmail());
		user.setImageUrl(userRequestDto.getImageUrl());
		user.setBirthday(LocalDate.parse(userRequestDto.getBirthday(), localDateFormatter));
		user.setEmailVerified(false);
		user.setPassword(userRequestDto.getPassword());
		user.setProvider(AuthProvider.valueOf(userRequestDto.getProvider()));
		user.setProviderId(userRequestDto.getProviderId());
		user.setRole(UserRole.valueOf(userRequestDto.getRole()));
		user.setValidFlg(false);
		user.setDelFlg(false);
		user.setCreateAt(LocalDateTime.now());
		user.setUpdateAt(LocalDateTime.now());
		
		return user;
	}
	
	private User convertUserRequestDtoToUpdateUserEntity(UserRequestDto userRequestDto) {
		User user = new User();
		user.setName(userRequestDto.getName());
		user.setEmail(userRequestDto.getEmail());
		user.setImageUrl(userRequestDto.getImageUrl());
		user.setBirthday(LocalDate.parse(userRequestDto.getBirthday(), localDateFormatter));
		user.setEmailVerified(false);
		user.setPassword(userRequestDto.getPassword());
		user.setProvider(AuthProvider.valueOf(userRequestDto.getProvider()));
		user.setProviderId(userRequestDto.getProviderId());
		user.setRole(UserRole.valueOf(userRequestDto.getRole()));
		user.setValidFlg(false);
		user.setDelFlg(false);
		user.setCreateAt(LocalDateTime.now());
		user.setUpdateAt(LocalDateTime.now());
		
		return user;
	}
	
	private UserResponseDto convertUserEntityToUserResponseEntity(User user) {
		UserResponseDto userResponseDto = null;
		if(user != null) {
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
		}
		
		return userResponseDto;
	}
}
