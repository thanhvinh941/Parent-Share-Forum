package com.se1.userservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.se1.userservice.domain.payload.ApiResponseEntity;
import com.se1.userservice.domain.payload.UserRequestDto;
import com.se1.userservice.domain.service.VerifycationService;


@RestController
public class VerifycationController {

	@Autowired
	private VerifycationService verifycationService;
	
	@Autowired
	private ApiResponseEntity apiResponseEntity;
	
	@PostMapping("/save")
	public ResponseEntity<?> save(@RequestBody UserRequestDto userRequestDto) {

		return ResponseEntity.ok(apiResponseEntity);
	}
}
