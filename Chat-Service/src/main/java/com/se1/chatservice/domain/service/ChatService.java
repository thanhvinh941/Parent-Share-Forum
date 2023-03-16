package com.se1.chatservice.domain.service;

import org.springframework.stereotype.Service;

import com.se1.chatservice.domain.payload.ChatRequest;
import com.se1.chatservice.domain.repository.ChatRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class ChatService {
	
	private final ChatRepository chatRepository;
	
	public void processCreate(ChatRequest chatRequest) {
		log.info("processCreate ");
	}

	public void processChangeStatus(ChatRequest chatRequest) {
		log.info("processChangeStatus ");
	}

}
