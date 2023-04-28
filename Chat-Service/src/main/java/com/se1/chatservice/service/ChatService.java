package com.se1.chatservice.service;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.se1.chatservice.config.MqConfig;
import com.se1.chatservice.config.SCMConstant;
import com.se1.chatservice.config.UrlConstant;
import com.se1.chatservice.domain.db.read.RChatMapper;
import com.se1.chatservice.model.Chat;
import com.se1.chatservice.payload.ApiResponseEntity;
import com.se1.chatservice.payload.ChatDto;
import com.se1.chatservice.payload.ContactDto;
import com.se1.chatservice.payload.GetAllChatRequest;
import com.se1.chatservice.payload.RabbitRequest;
import com.se1.chatservice.payload.UpdateChatRequest;
import com.se1.chatservice.payload.UserDetail;
import com.se1.chatservice.repository.ChatRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class ChatService {

	private final ChatRepository chatRepository;
	private final RabbitTemplate rabbitTemplate;
	private final ObjectMapper objectMapper;
	private final RChatMapper rChatMapper;
	private final CallApiService<ChatDto.User> callApiService;
	private final ContactService contactService;
	
	public void processCreate(UpdateChatRequest chatRequest) throws JsonProcessingException {
		log.info("processCreate ");
		if(chatRequest.getId()== null) {
			
			Chat chat = new Chat();
			BeanUtils.copyProperties(chatRequest, chat);
			chat.setCreateAt(new Date());
			chat.setStatus(0);
			
			Chat chatNew = chatRepository.save(chat);
			ChatDto chatDto = new ChatDto();
			BeanUtils.copyProperties(chatNew, chatDto);
			if(getUSerChat(chatNew.getUserId()) != null) {
				chatDto.setUser(getUSerChat(chatNew.getUserId()));
					if (chatRequest.getChatParentId() != null) {
						Chat chatParent = chatRepository.findById(chatRequest.getChatParentId()).get();
						ChatDto chatParentDto = new ChatDto();
						BeanUtils.copyProperties(chatParent, chatParentDto);
						if(getUSerChat(chatParent.getUserId()) == null) {
							return;
						}
						chatParentDto.setUser(getUSerChat(chatParent.getUserId()));
						chatDto.setChatParent(chatParentDto);
					}
					
					RabbitRequest request = new RabbitRequest();
					request.setAction(SCMConstant.SYSTEM_CHAT);
					request.setData(chatDto);
					rabbitTemplate.convertAndSend(MqConfig.SYSTEM_EXCHANGE, MqConfig.SYSTEM_ROUTING_KEY, request);
			}
			
		}
	}

	public void processExistChat(String topicId, ApiResponseEntity apiResponseEntity) {
		boolean isExist = chatRepository.existsByTopicId(topicId);
		apiResponseEntity.setData(isExist);
		apiResponseEntity.setErrorList(null);
		apiResponseEntity.setStatus(1);
	}

	public void processGetNewChat(String topicId, ApiResponseEntity apiResponseEntity) {
		List<Chat> newChatList = chatRepository.findNewChat(topicId);
		List<ChatDto> listChatResponse = newChatList.stream().map(c -> {
			ChatDto chatDto = new ChatDto();
			BeanUtils.copyProperties(c, chatDto);
			chatDto.setUser(getUSerChat(c.getUserId()));
			if (c.getChatParent() != null) {
				Chat chatParent = chatRepository.findById(c.getChatParent()).get();
				ChatDto chatParentDto = new ChatDto();
				BeanUtils.copyProperties(chatParent, chatParentDto);
				chatParentDto.setUser(getUSerChat(chatParent.getUserId()));
				chatDto.setChatParent(chatParentDto);
			}

			return chatDto;
		}).filter(res->res.getUser() != null).collect(Collectors.toList());

		apiResponseEntity.setData(listChatResponse);
		apiResponseEntity.setErrorList(null);
		apiResponseEntity.setStatus(1);
	}

	private ChatDto.User getUSerChat(Long userId) {

		MultiValueMap<String, String> request = new LinkedMultiValueMap<String, String>();
		request.add("id", userId.toString());

		ChatDto.User userChatParent = null;
		try {
			userChatParent = objectMapper.readValue(callApiService.callPostMenthodForParam(request,
					CallApiService.USER_SERVICE, UrlConstant.USER_FINDBYID),ChatDto.User.class);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}

		return userChatParent;
	}

	public void processUpdateStatus(List<Long> chatIds, ApiResponseEntity apiResponseEntity) throws Exception {
		List<Integer> update = chatRepository.updateStatusChat(chatIds);
		if (update != null && update.size() > 0) {
			apiResponseEntity.setData(true);
			apiResponseEntity.setErrorList(null);
			apiResponseEntity.setStatus(1);
		} else {
			throw new Exception("update false");
		}
	}

	public void processGetAllChat(GetAllChatRequest request, UserDetail userDetail, ApiResponseEntity apiResponseEntity) throws Exception {
		ContactDto contactDto = contactService.findContactByUserIdAndTopicId(request.getTopicId(), userDetail.getId());
		if(Objects.isNull(contactDto)) {
			throw new Exception("Hành động không cho phép");
		}
		
		List<com.se1.chatservice.domain.db.dto.ChatDto> listChat = rChatMapper.getAllChat(request.getTopicId(),
				request.getLimit(), request.getOffset(), userDetail.getId());
		Collections.reverse(listChat);
		List<ChatDto> listChatResponse = listChat.stream().map(c -> {
			ChatDto chatDto = new ChatDto();
			BeanUtils.copyProperties(c, chatDto);
			chatDto.setIsFile(c.getIsFile().equals(1));
			chatDto.setUser(getUSerChat(c.getUserId()));
			if (c.getChatParent() != null) {
				Chat chatParent = chatRepository.findById(c.getChatParent()).get();
				ChatDto chatParentDto = new ChatDto();
				BeanUtils.copyProperties(chatParent, chatParentDto);
				chatParentDto.setUser(getUSerChat(chatParent.getUserId()));
				chatDto.setChatParent(chatParentDto);
			}

			return chatDto;
		}).filter(res->res.getUser() != null).collect(Collectors.toList());

		apiResponseEntity.setData(listChatResponse);
		apiResponseEntity.setErrorList(null);
		apiResponseEntity.setStatus(1);
	}

}
