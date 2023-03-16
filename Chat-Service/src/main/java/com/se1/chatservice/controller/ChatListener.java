package com.se1.chatservice.controller;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.se1.chatservice.config.MqConfig;
import com.se1.chatservice.domain.payload.ChatRequest;
import com.se1.chatservice.domain.service.ChatService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
@RequiredArgsConstructor
public class ChatListener {

	private final ObjectMapper objectMapper;
	private final ChatService chatService;
	
	@RabbitListener(queues = MqConfig.CHAT_QUEUE)
	public void listener(ChatRequest chatRequest) throws JsonProcessingException {
		log.info("Listener {} : {}" , MqConfig.CHAT_QUEUE , objectMapper.writeValueAsString(chatRequest));
		String action = chatRequest.getAction();
		switch (action) {
		case "create":
			chatService.processCreate(chatRequest);
			break;
		case "change-status":
			chatService.processChangeStatus(chatRequest);
			break;
		default:
			break;
		}
	}
}
