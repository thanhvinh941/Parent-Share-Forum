package com.se1.chatservice.controller;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.se1.chatservice.config.MqConfig;
import com.se1.chatservice.payload.CreateChatRequest;
import com.se1.chatservice.payload.UpdateChatRequest;
import com.se1.chatservice.service.ChatService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
@RequiredArgsConstructor
public class ChatListener {

	private final ObjectMapper objectMapper;
	private final ChatService chatService;
	
	
	
	@RabbitListener(queues = MqConfig.CHAT_QUEUE_CREATE)
	public void listenerCreate(CreateChatRequest chatRequest) throws JsonProcessingException {
		log.info("Listener {} : {}" , MqConfig.CHAT_QUEUE_CREATE , objectMapper.writeValueAsString(chatRequest));
		chatService.processCreate(chatRequest);
	}

	@RabbitListener(queues = MqConfig.CHAT_QUEUE_UPDATE)
	public void listenerUpdate(UpdateChatRequest chatRequest) throws JsonProcessingException {
		log.info("Listener {} : {}" , MqConfig.CHAT_QUEUE_UPDATE , objectMapper.writeValueAsString(chatRequest));
		chatService.processChangeStatus(chatRequest);;
	}
}
