package com.se1.userservice.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.se1.userservice.domain.common.SCMConstant;
import com.se1.userservice.domain.payload.ApiResponseEntity;

@RestController
@RequestMapping("/author-item/external")
public class AuthorizationItemController {

	@Autowired
	ApiResponseEntity apiResponseEntity;
	
	@PostMapping("/getAllAuthorItem")
	public ResponseEntity<?> getAllAuthorItem(){
		Map<Integer, String> getAllAuthorItem = SCMConstant.getAllAuthorItem();
		
		apiResponseEntity.setData(getAllAuthorItem);
		apiResponseEntity.setErrorList(null);
		apiResponseEntity.setStatus(1);
		
		return ResponseEntity.ok(apiResponseEntity);
	}
}
