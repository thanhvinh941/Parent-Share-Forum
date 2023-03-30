package com.se1.chatservice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.se1.chatservice.payload.ApiResponseEntity;
import com.se1.chatservice.service.ChatService;

@RestController
@RequestMapping("/chat/internal")
public class ChatController {

	@Autowired
	private ChatService chatService;
	
	@Autowired
	private ApiResponseEntity apiResponseEntity;
	
	@PostMapping("/existChat")
	public ResponseEntity<?> existChat(@RequestParam("topicId") String topicId){
		
		try {
			chatService.processExistChat(topicId, apiResponseEntity);
		} catch (Exception e) {
			apiResponseEntity.setData(false);
			apiResponseEntity.setErrorList(List.of(e.getMessage()));
			apiResponseEntity.setStatus(0);
		}
		return ResponseEntity.ok(apiResponseEntity);
	}
	
	@PostMapping("/getNewChat")
	public ResponseEntity<?> getNewChat(@RequestParam("topicId") String topicId){
		
		try {
			chatService.processGetNewChat(topicId, apiResponseEntity);
		} catch (Exception e) {
			apiResponseEntity.setData(null);
			apiResponseEntity.setErrorList(List.of(e.getMessage()));
			apiResponseEntity.setStatus(0);
		}
		return ResponseEntity.ok(apiResponseEntity);
	}
	
	@PostMapping("/updateStatus")
	public ResponseEntity<?> updateStatus(@RequestParam("id") List<Long> chatIds){
		
		try {
			chatService.processUpdateStatus(chatIds, apiResponseEntity);
		} catch (Exception e) {
			apiResponseEntity.setData(false);
			apiResponseEntity.setErrorList(List.of(e.getMessage()));
			apiResponseEntity.setStatus(0);
		}
		return ResponseEntity.ok(apiResponseEntity);
	}
	
	
}
