package com.se1.userservice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.se1.userservice.domain.payload.ApiResponseEntity;
import com.se1.userservice.domain.payload.UserRequestDto;
import com.se1.userservice.domain.service.VerifycationService;


@RestController
@RequestMapping("/verify/internal")
public class VerifycationInternalController {

	@Autowired
	private VerifycationService verifycationService;
	
	@Autowired
	private ApiResponseEntity apiResponseEntity;
	
	@PostMapping("/create")
	public ResponseEntity<?> save(@RequestParam("user_id") Long userId, @RequestParam("email") String mail, @RequestParam("name") String name) {

		try {
			verifycationService.processCreate(userId, mail, name, apiResponseEntity);
		} catch (Exception e) {
			apiResponseEntity.setData(null);
			apiResponseEntity.setErrorList(List.of());
			apiResponseEntity.setStatus(0);
		}
		
		return ResponseEntity.ok(apiResponseEntity);
	}
}
