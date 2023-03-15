package com.se1.systemservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.se1.systemservice.payload.ChatRequest;
import com.se1.systemservice.payload.ContactRequest;
import com.se1.systemservice.service.UserContactService;
import com.se1.systemservice.service.WebsocketService;

@Controller
@RequestMapping("/ws")
public class WebsocketController {

	@Autowired
	private WebsocketService websocketService;
	
	@Autowired
	private UserContactService contactService;
	
	@MessageMapping("/chat/{topicId}")
    public void sendChat(@DestinationVariable String topicId,ChatRequest request) throws Exception {
		websocketService.sendMessageChat(topicId, request);
    }
	
	@MessageMapping("/comment/{topicId}")
	public void sendComment() {
		
	}
	
	@MessageMapping("/contact/{topicId}")
	public void sendContact(@DestinationVariable String topicId,ChatRequest request) {
		websocketService.sendContact(topicId, request);
	}
	
	@MessageMapping("/user/{topicId}")
	public void sendUser(@DestinationVariable String topicId,ChatRequest request) {
		
		websocketService.sendContact(topicId, request);
	}
	
	@MessageMapping("/userContact/{userId}")
	public void updateUserContact(@DestinationVariable Integer userId,String action) {
		boolean isChoose = action.equals("CONNECT");
		System.out.println(userId + "/" + action);
		contactService.updateUserChoose(userId, isChoose);
	}
	
	@MessageMapping("/user/54f2e54b-f4fb-4f1e-8358-624a2f4017d7")
	public void sendPrivateUser(@Payload ChatRequest request) {
		System.out.println(request);
		websocketService.sendMessageChat("54f2e54b-f4fb-4f1e-8358-624a2f4017d7", request);
	}
}
