package com.se1.userservice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.se1.userservice.domain.payload.ApiResponseEntity;
import com.se1.userservice.domain.service.SubscribeService;

@RestController
@RequestMapping("/subscriber/internal")
public class SubscribeInternalController {

	@Autowired
	ApiResponseEntity apiResponseEntity;
	
	@Autowired
	SubscribeService subscribeService;
	
	@PostMapping("/getAllExpertSubscribe")
	public ResponseEntity<?> getAllExpertSubscribe(@RequestParam("id") Long id){
		try {
			subscribeService.processGetAllExpertSubscribe(id, apiResponseEntity);
		} catch (Exception e) {
			apiResponseEntity.setData(null);
			apiResponseEntity.setErrorList(List.of(e.getMessage()));
			apiResponseEntity.setStatus(1);
		}
		
		return ResponseEntity.ok(apiResponseEntity);
	}
	
	@PostMapping("/getAllSubscribeForExpert")
	public ResponseEntity<?> getAllSubscribeForExpert(@RequestParam("id") Long id){
		try {
			subscribeService.getAllSubscribeForExpert(id, apiResponseEntity);
		} catch (Exception e) {
			apiResponseEntity.setData(null);
			apiResponseEntity.setErrorList(List.of(e.getMessage()));
			apiResponseEntity.setStatus(1);
		}
		
		return ResponseEntity.ok(apiResponseEntity);
	}
}
