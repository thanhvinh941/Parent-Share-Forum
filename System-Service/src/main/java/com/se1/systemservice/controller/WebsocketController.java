package com.se1.systemservice.controller;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.se1.systemservice.config.MqConfig;
import com.se1.systemservice.config.UrlConstant;
import com.se1.systemservice.domain.common.utils.CommonUtils;
import com.se1.systemservice.domain.payload.ApiRequestEntity;
import com.se1.systemservice.domain.payload.ChatMqUpdateRequest;
import com.se1.systemservice.domain.payload.ChatRequest;
import com.se1.systemservice.domain.payload.dto.ChatBlockDto;
import com.se1.systemservice.domain.service.CallApiService2;
import com.se1.systemservice.domain.service.CommonService;
import com.se1.systemservice.domain.service.WebsocketService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class WebsocketController {

	private final CommonService commonService;
	private final RabbitTemplate rabbitTemplate;
	private final CallApiService2 callApiService2;
	private final WebsocketService websocketService;
	@MessageMapping("/chat/{topicId}")
	public void sendChat(@DestinationVariable String topicId, ChatRequest request) throws Exception {
		Boolean isBlock = checkBlock(topicId, request.getUserId());
		if(isBlock) {			
			createChat(request.getUserId(), request.getContent(), request.getIsFile(), topicId, request.getChatParent());
		} else {
			Map<String, Object> mapChat = new HashMap<>();
			mapChat.put("status", 0);
			mapChat.put("error", "chat-block");
			mapChat.put("data", request.getUserId());
			websocketService.sendMessageChat(topicId, mapChat);
		}
	}

	private Boolean checkBlock(String topicId, String userId) throws JsonProcessingException {
		ApiRequestEntity request = new ApiRequestEntity();
		
		StringBuffer conditionStr = new StringBuffer();
		conditionStr.append(String.format(" topic_id = '%s'", topicId));
		conditionStr.append(String.format(" AND user_blocked_id = %s", userId));
		request.setConditionStr(conditionStr.toString());
		
		ChatBlockDto chatBlockDto = callApiService2.callPostMenthodForObject(request, CallApiService2.USER_SERVICE, UrlConstant.USER_GET_CHAT_BLOCK, new TypeReference<ChatBlockDto>() {});
		if(Objects.isNull(chatBlockDto)) {
			return true;
		}
		
		return false;
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
