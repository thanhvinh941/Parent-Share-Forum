package com.se1.chatservice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.se1.chatservice.payload.ApiResponseEntity;
import com.se1.chatservice.payload.CreateChatRequest;
import com.se1.chatservice.service.ChatService;

@RestController
public class ChatController {

	@Autowired
	private ChatService chatService;
	
	@Autowired
	private ApiResponseEntity apiResponseEntity;
	
	@Autowired
	public ResponseEntity<?> createChat(CreateChatRequest chatRequest){
		try {
			chatService.processCreate(chatRequest, apiResponseEntity);
		} catch (Exception e) {
			apiResponseEntity.setData(null);
			apiResponseEntity.setErrorList(List.of(e.getMessage()));
			apiResponseEntity.setStatus(0);
		}
		return ResponseEntity.ok(apiResponseEntity);
	}
}
