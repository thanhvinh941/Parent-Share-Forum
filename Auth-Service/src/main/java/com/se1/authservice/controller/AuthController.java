package com.se1.authservice.controller;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
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
import com.se1.authservice.payload.MailRequest;
import com.se1.authservice.payload.SignUpRequest;
import com.se1.authservice.payload.SignUpResponseDto;
import com.se1.authservice.payload.UserDetail;
import com.se1.authservice.payload.UserResponseForClient;
import com.se1.authservice.payload.Verification;
import com.se1.authservice.security.TokenProvider;
import com.se1.authservice.security.UserPrincipal;
import com.se1.authservice.service.UserDetailService;
import com.se1.authservice.service.VerifyService;
import com.se1.authservice.util.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

	private final UserService userService;
	private final UserDetailService service;
	private final VerifyService verifyService;
	private final PasswordEncoder passwordEncoder;
	private final TokenProvider tokenProvider;
	private final AuthenticationManager authenticationManager;

	@Value("${front-end.url.login}")
	String urlFronEnd;

	@PostMapping("/login")
	public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
		
		String email = loginRequest.getEmail();
		try {

			User user = service.findByEmail(email).orElse(null);
			if (user != null && user.getProvider() != AuthProvider.local) {
				return this.badResponse(List.of("User not login local with email : " + email));
			}

			if (user == null) {
				return this.badResponse(List.of("User not found with email : " + email));
			}

			try {
				Authentication authentication = authenticationManager.authenticate(
						new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));

				SecurityContextHolder.getContext().setAuthentication(authentication);

				UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();

				UserDetail userDetail = new UserDetail();
				BeanUtils.copyProperties(user, userDetail);
				AuthResponse authResponse = tokenProvider.createToken(userPrincipal.getEmail(), userDetail);
				return this.okResponse(authResponse);
			} catch (Exception e) {
				return this.badResponse(List.of("Password not correst"));
			}
		} catch (Exception e) {
			return this.badResponse(List.of(e.getMessage()));
		}

	}

	@GetMapping("/verify-email")
	public ResponseEntity<?> verifyEmail(@RequestParam("token") String token)
			throws JsonMappingException, JsonProcessingException, URISyntaxException {
		MultiValueMap<String, String> map = new LinkedMultiValueMap<String, String>();
		map.add("token", token);
		Verification verification = verifyService.findByToken(map);
		Boolean isVerify = false;
		if (verification != null) {
			MultiValueMap<String, String> map2 = new LinkedMultiValueMap<String, String>();
			map2.add("id", verification.getUserId().toString());
			isVerify = userService.updateEmailStatus(map2);
		}
		if (!isVerify) {
			urlFronEnd = urlFronEnd + "?verify=false";
		}
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setLocation(new URI("http://" + urlFronEnd));
		return new ResponseEntity<>(httpHeaders, HttpStatus.SEE_OTHER);
	}

	@PostMapping("/getUserInfoByToken")
	public ResponseEntity<?> getUserEmailByToken(@RequestHeader("Authorization") String token) {
		Boolean isTokenValid = tokenProvider.validateToken(token);
		if (!isTokenValid) {
			return this.badResponse(List.of("token not valid"));
		}

		String userEmail = tokenProvider.getUserEmailFromToken(token);
		User user;
		try {
			user = service.findByEmail(userEmail).orElse(null);
			if (user == null) {
				return this.badResponse(List.of("User not found"));
			}
			UserResponseForClient userDetail = new UserResponseForClient();
			BeanUtils.copyProperties(user, userDetail);
			return this.okResponse(userDetail);
		} catch (JsonProcessingException e) {
			return this.badResponse(List.of(e.getMessage()));
		}

	}

	@PostMapping("/signup")
	public ResponseEntity<?> registerUser(@Valid @RequestBody SignUpRequest signUpRequest)
			throws JsonProcessingException {

		List<String> errors = new ArrayList<>();

		errors = this.validRequest(signUpRequest, errors);

		if (errors.size() > 0) {
			return this.badResponse(errors);
		}

		if (service.existsByEmail(signUpRequest.getEmail())) {
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
			if (result != null) {
				MailRequest mailRequest = new MailRequest();
				mailRequest.setMailTemplate("signup-template");
				mailRequest.setTo(result.getEmail());

				service.sendMail(result.getId(), result.getEmail(), result.getName());

				signUpResponseDto.setMessage(List.of("Please check your email to login"));
				signUpResponseDto.setSignUp(true);
			} else {
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

		// TODO check email
		// TODO check name
		// TODO check password

		if (!signUpRequest.getPassword().equals(signUpRequest.getConfirmPassword())) {
			errors.add("ConfirmPassword not work");
		}

		return errors;
	}

	private ResponseEntity<?> badResponse(List<String> errorMessage) {
		ApiResponseEntity apiResponseEntity = new ApiResponseEntity();
		apiResponseEntity.setData(null);
		apiResponseEntity.setErrorList(errorMessage);
		apiResponseEntity.setStatus(0);
		return ResponseEntity.badRequest().body(apiResponseEntity);
	}

	private ResponseEntity<?> okResponse(Object data) {
		ApiResponseEntity apiResponseEntity = new ApiResponseEntity();
		apiResponseEntity.setData(data);
		apiResponseEntity.setErrorList(null);
		apiResponseEntity.setStatus(1);
		return ResponseEntity.ok().body(apiResponseEntity);
	}
}