package com.se1.systemservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

import com.se1.systemservice.payload.ChatRequest;
import com.se1.systemservice.payload.ContactRequest;
import com.se1.systemservice.service.WebsocketService;

@Controller
public class WebsocketController {

	@Autowired
	private WebsocketService websocketService;
	
	@MessageMapping("/chat/{topicId}")
    public void sendChat(@DestinationVariable String topicId,ChatRequest request) throws Exception {
		websocketService.sendMessageChat(topicId, request);
    }
	
	@MessageMapping("/comment/{topicId}")
	public void sendComment() {
		
	}
	
	@MessageMapping("/contact/{topicId}")
	public void sendContact(@DestinationVariable String topicId,ContactRequest request) {
		websocketService.sendContact(topicId, request);
	}
}
