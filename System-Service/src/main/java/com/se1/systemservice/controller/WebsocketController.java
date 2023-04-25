package com.se1.systemservice.controller;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

import com.se1.systemservice.config.MqConfig;
import com.se1.systemservice.domain.common.utils.CommonUtils;
import com.se1.systemservice.domain.payload.ChatMqUpdateRequest;
import com.se1.systemservice.domain.payload.ChatRequest;
import com.se1.systemservice.domain.service.CommonService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class WebsocketController {

	private final CommonService commonService;
	private final RabbitTemplate rabbitTemplate;
	
	@MessageMapping("/chat/{topicId}")
	public void sendChat(
			@DestinationVariable String topicId, ChatRequest request) throws Exception {
			createChat(request.getUserId(), request.getContent(), request.getIsFile(), topicId, request.getChatParent());
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

}
