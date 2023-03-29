package com.se1.chatservice.service;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.se1.chatservice.config.MqConfig;
import com.se1.chatservice.config.SCMConstant;
import com.se1.chatservice.domain.common.CommonUtils;
import com.se1.chatservice.domain.db.write.WChatMapper;
import com.se1.chatservice.domain.restclient.UserServiceRestTemplateClient;
import com.se1.chatservice.model.Chat;
import com.se1.chatservice.payload.ApiResponseEntity;
import com.se1.chatservice.payload.ChatDto;
import com.se1.chatservice.payload.ChatDto.User;
import com.se1.chatservice.payload.CreateChatRequest;
import com.se1.chatservice.payload.RabbitRequest;
import com.se1.chatservice.payload.UpdateChatRequest;
import com.se1.chatservice.repository.ChatRepository;

import ch.qos.logback.core.joran.util.beans.BeanUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class ChatService {

	private final ChatRepository chatRepository;
	private final WChatMapper chatMapper;
	private final RabbitTemplate rabbitTemplate;
	private final ObjectMapper objectMapper;
	private final UserServiceRestTemplateClient restTemplateClient;
	
	public void processCreate(CreateChatRequest chatRequest, ApiResponseEntity apiResponseEntity) throws JsonProcessingException {
		log.info("processCreate ");
		Chat chat = new Chat();
		chat.setChatParent(chatRequest.getChatParentId());
		chat.setUserId(chatRequest.getUserId());
		chat.setCreateAt(new Date());
		chat.setStatus(1);
		chat.setTopicId(chatRequest.getTopicId());
		
		String content = chatRequest.getContent();
		String contentToMQ = content;
		if (chatRequest.isFile()) {
			String[] contentArray = content.split(",");

			String imageName = CommonUtils.getFileName(contentArray[0].split("/")[1]);
			byte[] imageByte = Base64.getDecoder().decode(contentArray[1].trim());
			InputStream inputStream = new ByteArrayInputStream(imageByte);

			contentToMQ = imageName;
			//TODO CALL API STORE FILE
//			commonService.saveFile("/chat", imageName, inputStream);
		}
		chat.setContent(contentToMQ);
		
		Chat chatNew = chatRepository.save(chat);
		apiResponseEntity.setData(true);
		apiResponseEntity.setErrorList(null);
		apiResponseEntity.setStatus(1);
		
		
		ChatDto chatDto = new ChatDto();
		BeanUtils.copyProperties(chatNew, chatDto);
		ChatDto.User user = new User();
		ApiResponseEntity apiResponseEntityResult = (ApiResponseEntity) restTemplateClient.findById(chatNew.getUserId());
		if (apiResponseEntityResult.getStatus() == 1) {
			String apiResultStr = objectMapper.writeValueAsString(apiResponseEntityResult.getData());
			user = objectMapper.readValue(apiResultStr, ChatDto.User.class);
		}
		chatDto.setUserId(user);
		
		RabbitRequest request = new RabbitRequest();
		request.setAction(SCMConstant.SYSTEM_CHAT);
		request.setData(chatDto);
		rabbitTemplate.convertAndSend(MqConfig.SYSTEM_EXCHANGE, MqConfig.SYSTEM_ROUTING_KEY, request);
	}

	public void processChangeStatus(UpdateChatRequest chatRequest, ApiResponseEntity apiResponseEntity) {
		log.info("processChangeStatus ");
		int action = 0;
		Long userId = null;
		String content = "";
		switch (chatRequest.getAction()) {
		case "delete":
			action = 0;
			userId = chatRequest.getUserId();
			break;
		case "revoke":
			action = 2;
			content = "Tin nhắn đã được thu hồi";
		default:
			break;
		}

		Long chatId = chatMapper.updateStatus(chatRequest.getId(), action, content, userId);
		Chat updateChat = chatRepository.findById(chatId).get();
		if(chatId > 0) {
			apiResponseEntity.setData(true);
			apiResponseEntity.setErrorList(null);
			apiResponseEntity.setStatus(1);
		}
		
		Map<String, Object> chatStatus = new HashMap<>();
		chatStatus.put("chatId", chatId);
		chatStatus.put("chatStatus", action);
		chatStatus.put("topicId",updateChat.getTopicId());
		
		RabbitRequest request = new RabbitRequest();
		request.setAction(SCMConstant.SYSTEM_CHAT_STATUS);
		request.setData(chatStatus);
		rabbitTemplate.convertAndSend(MqConfig.SYSTEM_EXCHANGE, MqConfig.SYSTEM_ROUTING_KEY, request);
	}

}
