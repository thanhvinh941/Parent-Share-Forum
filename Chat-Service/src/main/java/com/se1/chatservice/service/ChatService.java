package com.se1.chatservice.service;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.se1.chatservice.config.MqConfig;
import com.se1.chatservice.config.SCMConstant;
import com.se1.chatservice.domain.common.CommonUtils;
import com.se1.chatservice.domain.db.read.RChatMapper;
import com.se1.chatservice.domain.db.write.WChatMapper;
import com.se1.chatservice.domain.restclient.UserServiceRestTemplateClient;
import com.se1.chatservice.model.Chat;
import com.se1.chatservice.payload.ApiResponseEntity;
import com.se1.chatservice.payload.ChatDto;
import com.se1.chatservice.payload.ChatDto.User;
import com.se1.chatservice.payload.CreateChatRequest;
import com.se1.chatservice.payload.GetAllChatRequest;
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
	private final RChatMapper rChatMapper;
	
	public void processCreate(CreateChatRequest chatRequest) throws JsonProcessingException {
		log.info("processCreate ");
		Chat chat = new Chat();
		chat.setChatParent(chatRequest.getChatParentId());
		chat.setUserId(chatRequest.getUserId());
		chat.setCreateAt(new Date());
		chat.setStatus(0);
		chat.setTopicId(chatRequest.getTopicId());
		chat.setContent(chatRequest.getContent());
		
		Chat chatNew = chatRepository.save(chat);
		ChatDto chatDto = new ChatDto();
		BeanUtils.copyProperties(chatNew, chatDto);
		chatDto.setUser(getUSerChat(chatNew.getUserId()));
		
		if(chatRequest.getChatParentId() != null) {
			Chat chatParent = chatRepository.findById(chatRequest.getChatParentId()).get();
			ChatDto chatParentDto = new ChatDto();
			BeanUtils.copyProperties(chatParent, chatParentDto);
			chatParentDto.setUser(getUSerChat(chatParent.getUserId()));
			chatDto.setChatParent(chatParentDto);
		}
		
		RabbitRequest request = new RabbitRequest();
		request.setAction(SCMConstant.SYSTEM_CHAT);
		request.setData(chatDto);
		rabbitTemplate.convertAndSend(MqConfig.SYSTEM_EXCHANGE, MqConfig.SYSTEM_ROUTING_KEY, request);
	}

	public void processChangeStatus(UpdateChatRequest chatRequest) {
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
		Map<String, Object> chatStatus = new HashMap<>();
		chatStatus.put("chatId", chatId);
		chatStatus.put("chatStatus", action);
		chatStatus.put("topicId",updateChat.getTopicId());
		
		RabbitRequest request = new RabbitRequest();
		request.setAction(SCMConstant.SYSTEM_CHAT_STATUS);
		request.setData(chatStatus);
		rabbitTemplate.convertAndSend(MqConfig.SYSTEM_EXCHANGE, MqConfig.SYSTEM_ROUTING_KEY, request);
	}

	public void processExistChat(String topicId, ApiResponseEntity apiResponseEntity) {
		boolean isExist = chatRepository.existsByTopicId(topicId);
		apiResponseEntity.setData(isExist);
		apiResponseEntity.setErrorList(null);
		apiResponseEntity.setStatus(1);
	}

	public void processGetNewChat(String topicId, ApiResponseEntity apiResponseEntity) {
		List<Chat> newChatList = chatRepository.findNewChat(topicId);
		List<ChatDto> listChatResponse = newChatList.stream().map(c->{
			ChatDto chatDto = new ChatDto();
			BeanUtils.copyProperties(c, chatDto);
			chatDto.setUser(getUSerChat(c.getUserId()));
			if(c.getChatParent() != null) {
				Chat chatParent = chatRepository.findById(c.getChatParent()).get();
				ChatDto chatParentDto = new ChatDto();
				BeanUtils.copyProperties(chatParent, chatParentDto);
				chatParentDto.setUser(getUSerChat(chatParent.getUserId()));
				chatDto.setChatParent(chatParentDto);
			}
			
			return chatDto;
		}).collect(Collectors.toList());
		
		apiResponseEntity.setData(listChatResponse);
		apiResponseEntity.setErrorList(null);
		apiResponseEntity.setStatus(1);
	}
	
	private ChatDto.User getUSerChat(Long userId) {
		ChatDto.User userChatParent = new User();
		ApiResponseEntity userChatParentResult = (ApiResponseEntity) restTemplateClient.findById(userId);
		if (userChatParentResult.getStatus() == 1) {
			String apiResultStr;
			try {
				apiResultStr = objectMapper.writeValueAsString(userChatParentResult.getData());
				userChatParent = objectMapper.readValue(apiResultStr, ChatDto.User.class);
			} catch (JsonProcessingException e) {
				e.printStackTrace();
			}
		}
		return userChatParent;
	}

	public void processUpdateStatus(List<Long> chatIds, ApiResponseEntity apiResponseEntity) throws Exception {
		List<Integer> update = chatRepository.updateStatusChat(chatIds);
		if(update != null && update.size() > 0) {
			apiResponseEntity.setData(true);
			apiResponseEntity.setErrorList(null);
			apiResponseEntity.setStatus(1);
		}else {
			throw new Exception("update false");
		}
	}

	public void processGetAllChat(GetAllChatRequest request, ApiResponseEntity apiResponseEntity) {
		List<Chat> listChat = rChatMapper.getAllChat(request.getTopicId(), request.getLimit(), request.getOffset());
		List<ChatDto> listChatResponse = listChat.stream().map(c->{
			ChatDto chatDto = new ChatDto();
			BeanUtils.copyProperties(c, chatDto);
			chatDto.setUser(getUSerChat(c.getUserId()));
			if(c.getChatParent() != null) {
				Chat chatParent = chatRepository.findById(c.getChatParent()).get();
				ChatDto chatParentDto = new ChatDto();
				BeanUtils.copyProperties(chatParent, chatParentDto);
				chatParentDto.setUser(getUSerChat(chatParent.getUserId()));
				chatDto.setChatParent(chatParentDto);
			}
			
			return chatDto;
		}).collect(Collectors.toList());
		
		apiResponseEntity.setData(listChatResponse);
		apiResponseEntity.setErrorList(null);
		apiResponseEntity.setStatus(1);
	}

}
