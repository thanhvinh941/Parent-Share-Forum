package com.se1.systemservice.controller;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.se1.systemservice.config.MqConfig;
import com.se1.systemservice.config.SCMConstant;
import com.se1.systemservice.config.WebSocketSessionListener;
import com.se1.systemservice.domain.common.utils.CommonUtils;
import com.se1.systemservice.domain.payload.ChatMqCreateRequest;
import com.se1.systemservice.domain.payload.ChatMqUpdateRequest;
import com.se1.systemservice.domain.payload.ChatRequest;
import com.se1.systemservice.domain.service.CommonService;
import com.se1.systemservice.domain.service.UserContactService;
import com.se1.systemservice.domain.service.WebsocketService;

import lombok.extern.slf4j.Slf4j;

@Controller
public class WebsocketController {

	@Autowired
	private WebsocketService websocketService;

	@Autowired
	private UserContactService contactService;

	@Autowired
	private CommonService commonService;

	@Autowired
	private RabbitTemplate rabbitTemplate;
	
	@MessageMapping("/chat/{topicId}")
	public void sendChat(
			@DestinationVariable String topicId, ChatRequest request) throws Exception {
		String action = request.getAction() != null ? request.getAction() : "create";
		switch (action) {
		case "delete":
			deleteProcess(request.getUserId(), request.getId(), topicId, action);
			break;
		case "revoke":
			revokeProcess(request.getUserId(), request.getId(), topicId,request.getChatParent(), action);
			break;
		default:
			createChat(request.getUserId(), request.getContent(), request.getIsFile(), topicId, request.getChatParent());
			break;
		}
		
		
	}

	private void revokeProcess(String userId, Long chatId, String topicId, Long chatParentId, String action) {
		String contentToMQ = "Tin nhắn bị thu hồi";
		
		ChatMqUpdateRequest mqRequest = new ChatMqUpdateRequest();
		mqRequest.setChatParentId(chatParentId);
		mqRequest.setContent(contentToMQ);
		mqRequest.setUserId(Long.parseLong(userId));
		mqRequest.setTopicId(topicId);
		mqRequest.setIsFile(false);
		mqRequest.setId(chatId);
		mqRequest.setAction(action);
		
		rabbitTemplate.convertAndSend(MqConfig.CHAT_EXCHANGE, MqConfig.CHAT_ROUTING_KEY_UPDATE, mqRequest);
		
	}

	private void createChat(String userId, String content, Boolean isFile, String topicId, Long chatParentId ) throws IOException {
		String contentToMQ = content;
		if (isFile) {
			String[] contentArray = content.split(",");

			String imageName = CommonUtils.getFileName(contentArray[0].split("/")[1]);
			byte[] imageByte = Base64.getDecoder().decode(contentArray[1].trim());
			InputStream inputStream = new ByteArrayInputStream(imageByte);

			contentToMQ = imageName;
			commonService.saveFile(imageName, inputStream);
		}
		
		ChatMqUpdateRequest mqRequest = new ChatMqUpdateRequest();
		mqRequest.setChatParentId(chatParentId);
		mqRequest.setContent(contentToMQ);
		mqRequest.setUserId(Long.parseLong(userId));
		mqRequest.setTopicId(topicId);
		mqRequest.setIsFile(isFile);
		
		rabbitTemplate.convertAndSend(MqConfig.CHAT_EXCHANGE, MqConfig.CHAT_ROUTING_KEY_CREATE, mqRequest);
		
	}

	private void deleteProcess(String userId, Long chatId, String topicId, String action) {
		ChatMqUpdateRequest mqRequest = new ChatMqUpdateRequest();
		mqRequest.setChatParentId(null);
		mqRequest.setContent(null);
		mqRequest.setUserId(Long.parseLong(userId));
		mqRequest.setTopicId(topicId);
		mqRequest.setIsFile(false);
		mqRequest.setId(chatId);
		mqRequest.setAction(action);
		
		rabbitTemplate.convertAndSend(MqConfig.CHAT_EXCHANGE, MqConfig.CHAT_ROUTING_KEY_UPDATE, mqRequest);
	}

}
