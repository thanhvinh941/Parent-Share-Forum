package com.se1.notifyservice.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.se1.notifyservice.domain.payload.ApiResponseEntity;
import com.se1.notifyservice.domain.payload.UserDetail;
import com.se1.notifyservice.domain.service.NotifyService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/notify/external")
public class NotifyController {

	private final NotifyService notifyService;
	private final ObjectMapper objectMapper;
	
	
	@PostMapping("/findByUserId")
	public ResponseEntity<?> findById(@RequestHeader("user_detail") String userDetailHeader){
		ApiResponseEntity apiResponseEntity = new ApiResponseEntity();
		UserDetail userDetail;
		try {
			userDetail = objectMapper.readValue(userDetailHeader, UserDetail.class);
			notifyService.processFind(userDetail.getId(), apiResponseEntity);
		} catch (Exception e) {
			apiResponseEntity.setData(null);
			apiResponseEntity.setErrorList(List.of(e.getMessage()));
			apiResponseEntity.setStatus(0);
		}
		
		return ResponseEntity.ok(apiResponseEntity);
	}
	
	@PostMapping("/deleteAll")
	public ResponseEntity<?> deleteAllByUserId(@RequestHeader("user_detail") String userDetailHeader){
		ApiResponseEntity apiResponseEntity = new ApiResponseEntity();
		UserDetail userDetail;
		try {
			userDetail = objectMapper.readValue(userDetailHeader, UserDetail.class);
			notifyService.processDeleteAll(userDetail.getId(), apiResponseEntity);
		} catch (Exception e) {
			apiResponseEntity.setData(null);
			apiResponseEntity.setErrorList(List.of(e.getMessage()));
			apiResponseEntity.setStatus(0);
		}
		
		return ResponseEntity.ok(apiResponseEntity);
	}
	
	@PostMapping("/updateAllStatus")
	public ResponseEntity<?> updateAllByUserId(@RequestHeader("user_detail") String userDetailHeader){
		ApiResponseEntity apiResponseEntity = new ApiResponseEntity();
		UserDetail userDetail;
		try {
			userDetail = objectMapper.readValue(userDetailHeader, UserDetail.class);
			notifyService.processUpdateAll(userDetail.getId(), apiResponseEntity);
		} catch (Exception e) {
			apiResponseEntity.setData(null);
			apiResponseEntity.setErrorList(List.of(e.getMessage()));
			apiResponseEntity.setStatus(0);
		}
		
		return ResponseEntity.ok(apiResponseEntity);
	}
	
	@PostMapping("/updateStatus")
	public ResponseEntity<?> updateAllByUserId(@RequestParam("notifyId") Long notifyId){
		ApiResponseEntity apiResponseEntity = new ApiResponseEntity();
		try {
			notifyService.processUpdate(notifyId, apiResponseEntity);
		} catch (Exception e) {
			apiResponseEntity.setData(null);
			apiResponseEntity.setErrorList(List.of(e.getMessage()));
			apiResponseEntity.setStatus(0);
		}
		
		return ResponseEntity.ok(apiResponseEntity);
	}
}
