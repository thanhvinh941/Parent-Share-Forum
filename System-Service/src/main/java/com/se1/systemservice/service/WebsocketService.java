package com.se1.systemservice.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import com.se1.systemservice.model.ChatDto;
import com.se1.systemservice.payload.ChatRequest;
import com.se1.systemservice.payload.CommentRequest;
import com.se1.systemservice.payload.ContactRequest;
import com.se1.systemservice.payload.NotifyRequest;

@Service
public class WebsocketService {

	@Autowired
    private SimpMessagingTemplate simpMessagingTemplate;
	
	private final String CHAT_TOPIC = "/topic/chat/";
	private final String CONTACT_TOPIC = "/topic/contact";
	private final String COMMENT_TOPIC = "/topic/comment";
	private final String NOTIFY_TOPIC = "/topic/notify";
	private final String USER_TOPIC = "/topic/user";
	public void sendMessageChat(String topicId, ChatRequest chatRequest) {
		ChatDto chatDto = new ChatDto();
		chatDto.setTopicId(topicId);
		chatDto.setContent(chatRequest.getContent());
		chatDto.setCreateAt(new Date());
		
		simpMessagingTemplate.convertAndSend(CHAT_TOPIC + topicId , chatDto);
	}

	public void sendComment(String topic, CommentRequest commentRequest) {
		
	}
	
	public void sendContact(String topicId, ChatRequest request) {
		ChatDto chatDto = new ChatDto();
		chatDto.setContent(request.getContent());
		chatDto.setCreateAt(new Date());
		simpMessagingTemplate.convertAndSend(USER_TOPIC + topicId , chatDto);
	}
	
	public void sendNotify(String topicId, NotifyRequest request) {
		
	}
}
