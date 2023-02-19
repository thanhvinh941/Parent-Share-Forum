package com.se1.authservice.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import javax.validation.Valid;
import javax.validation.constraints.Email;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.se1.authservice.model.AuthProvider;
import com.se1.authservice.model.User;
import com.se1.authservice.payload.ApiResponseEntity;
import com.se1.authservice.payload.AuthResponse;
import com.se1.authservice.payload.LoginRequest;
import com.se1.authservice.payload.SignUpRequest;
import com.se1.authservice.payload.SignUpResponseDto;
import com.se1.authservice.payload.UserDetail;
import com.se1.authservice.payload.UserResponseDto;
import com.se1.authservice.security.TokenProvider;
import com.se1.authservice.security.UserPrincipal;
import com.se1.authservice.service.UserService;
import com.se1.authservice.util.UserServiceRestTemplateClient;

@RestController
@RequestMapping("/auth")
public class AuthController {

	@Autowired
	UserService service;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private TokenProvider tokenProvider;

	@Autowired
	ApiResponseEntity apiResponseEntity;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@PostMapping("/login")
	public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
		String email = loginRequest.getEmail();
		String password = loginRequest.getPassword();
		
		try {
			
			User user = service.findByEmail(email).orElse(null);
			if(user != null && user.getProvider() != AuthProvider.local) {
				return this.badResponse(List.of("User not login local with email : " + email));
			}
			
			if(user == null) {
				return this.badResponse(List.of("User not found with email : " + email));
			}
			
			try {
				Authentication authentication = authenticationManager.authenticate(
						new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
				
				SecurityContextHolder.getContext().setAuthentication(authentication);
				
				UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
				
				UserDetail userDetail = new UserDetail(user.getId(), user.getName(), user.getImageUrl(), user.getRole());
				AuthResponse authResponse = tokenProvider.createToken(userPrincipal.getEmail(),userDetail);
				return this.okResponse(authResponse);
			} catch (Exception e) {
				return this.badResponse(List.of("Password not correst"));
			}
		} catch (Exception e) {
			return this.badResponse(List.of(e.getMessage()));
		}

	}


	@PostMapping("/getUserInfoByToken")
	public ResponseEntity<?> getUserEmailByToken(@RequestHeader("Authorization") String token) {
		Boolean isTokenValid = tokenProvider.validateToken(token);
		if(!isTokenValid) {
			return this.badResponse(List.of("token not valid"));
		}
		
		String userEmail = tokenProvider.getUserEmailFromToken(token);
		User user;
		try {
			user = service.findByEmail(userEmail).orElse(null);
			if(user == null) {
				return this.badResponse(List.of("User not found"));
			}
			UserResponseDto userResponseDto = new UserResponseDto();
			userResponseDto.setEmail(user.getEmail());
			userResponseDto.setId(user.getId());
			userResponseDto.setName(user.getName());
			userResponseDto.setImageUrl(user.getImageUrl());
			userResponseDto.setEmailVerified(user.getEmailVerified());
			userResponseDto.setRole(user.getRole());
			
			return this.okResponse(userResponseDto);
		} catch (JsonProcessingException e) {
			return this.badResponse(List.of(e.getMessage()));
		}
		
	}

	@PostMapping("/signup")
	public ResponseEntity<?> registerUser(@Valid @RequestBody SignUpRequest signUpRequest) throws JsonProcessingException {

		List<String> errors = new ArrayList<>();

		errors = this.validRequest(signUpRequest, errors);
		
		if(errors.size() > 0) {
			return this.badResponse(errors);
		}
		
        if(service.existsByEmail(signUpRequest.getEmail())) {
        	return this.badResponse(List.of("Email address already in use."));
        }


		User user = new User();
		user.setName(signUpRequest.getName());
		user.setEmail(signUpRequest.getEmail());
		user.setPassword(signUpRequest.getPassword());
		user.setProvider(AuthProvider.local);

		user.setPassword(passwordEncoder.encode(user.getPassword()));

		try {
			User result = service.save(user);
			
			SignUpResponseDto signUpResponseDto = new SignUpResponseDto();
			if(result != null) {
				signUpResponseDto.setMessage(List.of("Please check your email to login"));
				signUpResponseDto.setSignUp(true);
			}else {
				signUpResponseDto.setMessage(List.of("Signup fail"));
				signUpResponseDto.setSignUp(false);
			}

			return this.okResponse(signUpResponseDto);
		} catch (JsonMappingException e) {
			return this.badResponse(List.of(e.getMessage()));
		} catch (JsonProcessingException e) {
			return this.badResponse(List.of(e.getMessage()));
		}

	}

	private List<String> validRequest(@Valid SignUpRequest signUpRequest, List<String> errors) {
		
		//TODO check email
		//TODO check name
		//TODO chech password
		
		if(!signUpRequest.getPassword().equals(signUpRequest.getConfirmPassword())) {
			errors.add("ConfirmPassword not work");
		}
		
		return errors;
	}

	private ResponseEntity<?> badResponse(List<String> errorMessage){
		apiResponseEntity.setData(null);
		apiResponseEntity.setErrorList(errorMessage);
		apiResponseEntity.setStatus(0);
		return ResponseEntity.badRequest().body(apiResponseEntity);
	}
	
	private ResponseEntity<?> okResponse(Object data){
		apiResponseEntity.setData(data);
		apiResponseEntity.setErrorList(null);
		apiResponseEntity.setStatus(1);
		return ResponseEntity.ok().body(apiResponseEntity);
	}
}