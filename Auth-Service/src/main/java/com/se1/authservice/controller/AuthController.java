package com.se1.authservice.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
import com.se1.authservice.security.TokenProvider;
import com.se1.authservice.service.UserService;
import com.se1.authservice.util.UserServiceRestTemplateClient;

@RestController
@RequestMapping("/auth")
public class AuthController {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	UserService service;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private TokenProvider tokenProvider;

	@Autowired
	private UserServiceRestTemplateClient userServiceRestTemplateClient;
	
	@PostMapping("/login")
	public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

		ApiResponseEntity<AuthResponse> apiResponseEntity = new ApiResponseEntity<>();
		
		try {
			Authentication authentication = authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
			
			SecurityContextHolder.getContext().setAuthentication(authentication);
			
			String token = tokenProvider.createToken(authentication);
			apiResponseEntity.setData(new AuthResponse(token));
			apiResponseEntity.setErrorList(null);
			apiResponseEntity.setStatus(1);
		} catch (Exception e) {
			//TODO:
			apiResponseEntity.setData(null);
			apiResponseEntity.setErrorList(List.of(e.getMessage()));
			apiResponseEntity.setStatus(0);
		}


		return ResponseEntity.ok(apiResponseEntity);
	}

	@GetMapping("/isTokenValid")
	public ResponseEntity<Boolean> isTokenValid(@RequestParam("token") String token) {
		Boolean isTokenValid = tokenProvider.validateToken(token);
		return ResponseEntity.ok(isTokenValid);
	}

	@GetMapping("/getMemberIdByToken")
	public ResponseEntity<?> getMemberIdByToken(@RequestParam("token") String token) {
		Long memberId = tokenProvider.getUserIdFromToken(token);
		return ResponseEntity.ok(memberId);
	}

	@PostMapping("/signup")
	public ResponseEntity<?> registerUser(@Valid @RequestBody SignUpRequest signUpRequest) throws JsonProcessingException {
		ApiResponseEntity<User> apiResponseEntity = new ApiResponseEntity<>();

        if(service.existsByEmail(signUpRequest.getEmail())) {
        	apiResponseEntity.setData(null);
			apiResponseEntity.setErrorList(List.of("Email address already in use."));
			apiResponseEntity.setStatus(0);
			return ResponseEntity.badRequest().body(apiResponseEntity);
        }


		User user = new User();
		user.setName(signUpRequest.getName());
		user.setEmail(signUpRequest.getEmail());
		user.setPassword(signUpRequest.getPassword());
		user.setProvider(AuthProvider.local);

		user.setPassword(passwordEncoder.encode(user.getPassword()));

		try {
			User result = service.save(user);
			apiResponseEntity.setData(result);
			apiResponseEntity.setErrorList(null);
			apiResponseEntity.setStatus(1);

			return ResponseEntity.ok(apiResponseEntity);
		} catch (JsonMappingException e) {
			apiResponseEntity.setData(null);
			apiResponseEntity.setErrorList(List.of(e.getMessage()));
			apiResponseEntity.setStatus(0);
			return ResponseEntity.badRequest().body(apiResponseEntity);
		} catch (JsonProcessingException e) {
			apiResponseEntity.setData(null);
			apiResponseEntity.setErrorList(List.of(e.getMessage()));
			apiResponseEntity.setStatus(0);
			return ResponseEntity.badRequest().body(apiResponseEntity);
		}

	}

}