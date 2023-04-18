package com.se1.systemservice.controller;

import java.io.ByteArrayInputStream;
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
import com.se1.systemservice.domain.payload.ChatMqRequest;
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
	
	@Autowired
	private WebSocketSessionListener webSocketSessionListener;
	
	@MessageMapping("/chat/{topicId}")
	public void sendChat(@RequestHeader(required = false, name = "user_detail") String userDetail,
			@DestinationVariable String topicId, ChatRequest request) throws Exception {
		
		String userId = webSocketSessionListener.getConnectedClientId().get(0);
		String content = request.getContent();
		String contentToMQ = content;
		if (request.getIsFile()) {
			String[] contentArray = content.split(",");

			String imageName = CommonUtils.getFileName(contentArray[0].split("/")[1]);
			byte[] imageByte = Base64.getDecoder().decode(contentArray[1].trim());
			InputStream inputStream = new ByteArrayInputStream(imageByte);

			contentToMQ = imageName;
			commonService.saveFile(imageName, inputStream);
		}
		
		ChatMqRequest mqRequest = new ChatMqRequest();
		mqRequest.setChatParentId(request.getChatParent());
		mqRequest.setContent(contentToMQ);
		mqRequest.setUserId(Long.parseLong(userId));
		mqRequest.setTopicId(topicId);
		mqRequest.setIsFile(request.getIsFile());
		
		rabbitTemplate.convertAndSend(MqConfig.CHAT_EXCHANGE, MqConfig.CHAT_ROUTING_KEY_CREATE, mqRequest);
	}

}
