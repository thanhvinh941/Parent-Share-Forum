package com.se1.systemservice.domain.service;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.se1.systemservice.config.SCMConstant;
import com.se1.systemservice.domain.payload.ApiResponseEntity;
import com.se1.systemservice.domain.payload.Contact;
import com.se1.systemservice.domain.payload.dto.ContactDto;
import com.se1.systemservice.domain.payload.dto.NotifyDto;
import com.se1.systemservice.domain.payload.dto.UserDetail;
import com.se1.systemservice.domain.rabbitMQ.dto.ChatDto;
import com.se1.systemservice.domain.rabbitMQ.dto.ChatStatusDto;
import com.se1.systemservice.domain.rabbitMQ.dto.NotifyDtoRequest;
import com.se1.systemservice.domain.rabbitMQ.dto.PostDto;
import com.se1.systemservice.domain.rabbitMQ.dto.SubScriberDto;
import com.se1.systemservice.domain.restclient.UserServiceRestTemplateClient;

@Service
public class SystemListenerService {

	@Autowired
	private WebsocketService websocketService;

	@Autowired
	private RabbitSenderService rabbitSenderService;

	@Autowired
	private UserServiceRestTemplateClient restTemplateClient;
	
	@Autowired
	private ObjectMapper objectMapper;

	public void processActionSystemContact(ContactDto data) throws JsonProcessingException {
		int status = data.getStatus();
		UserDetail userReciver = data.getUserReciver();
		UserDetail userSender = data.getUserSender();
		String type = "contact";
		switch (status) {
		case 1: // Request Friend
			NotifyDtoRequest notifyRequestFriend = generatorNotifyDtoForContact(userSender, userReciver, data.getTopicId(),
					status, type);

			rabbitSenderService.convertAndSendNotify(notifyRequestFriend);
			break;
		case 0: // Unfiend
		case 2: // Accept Friend
			NotifyDtoRequest notifyUnfiendOrAcceptFriend = generatorNotifyDtoForContact(userReciver, userSender,
					data.getTopicId(), status, type);

			rabbitSenderService.convertAndSendNotify(notifyUnfiendOrAcceptFriend);
			break;
		default:
			break;
		}

	}

	private NotifyDtoRequest generatorNotifyDtoForContact(UserDetail userFrom, UserDetail userTo, String TopicId, int status,
			String type) throws JsonProcessingException {
		Map<String, Object> notifyValue = new HashMap<>();
		notifyValue.put("user", objectMapper.writeValueAsString(userFrom));
		notifyValue.put("action", SCMConstant.getContactActionByStatus(status));
		String notifyContactParam = String.format("topicId=%s", TopicId);
		String notifyStatusParam = String.format("status=%d", status);

		NotifyDtoRequest notifyDto = new NotifyDtoRequest();
		notifyDto.setUserId(userTo.getId());
		notifyDto.setParam(String.join("&", List.of(notifyStatusParam, notifyContactParam)));
		notifyDto.setValue(objectMapper.writeValueAsString(notifyValue));
		notifyDto.setType(type);

		return notifyDto;
	}

	public void processActionSystemNotify(NotifyDto notifyDto) throws JsonProcessingException {
		websocketService.sendUser(notifyDto.getUser().getTopicId(), notifyDto);
	}

	public void processActionSystemChat(ChatDto chatDto) throws MalformedURLException, JsonProcessingException {
		String topicId = chatDto.getTopicId();
		Map<String, Object> mapChat = new HashMap<>();
		mapChat.put("action", "");
		mapChat.put("data", chatDto);
		websocketService.sendMessageChat(topicId, mapChat);
		
		ApiResponseEntity apiResponseEntity = (ApiResponseEntity) restTemplateClient.getContactByTopicId(topicId);
		ContactDto contact = objectMapper.convertValue(apiResponseEntity.getData(), ContactDto.class) ;
		
		NotifyDto notifyDto = new NotifyDto();
		Map<String, Object> notifyValue = new HashMap<>();
		notifyValue.put("action", "new-message");
		notifyDto.setValue(objectMapper.writeValueAsString(notifyValue));
		websocketService.sendUser(contact.getUserSender().getTopicId(), notifyDto);
		websocketService.sendUser(contact.getUserReciver().getTopicId(), notifyDto);
	}

	public void processActionSystemChatStatus(ChatStatusDto chatStatusDto) {
		Map<String, Object> mapChat = new HashMap<>();
		switch (chatStatusDto.getChatStatus()) {
		case "delete":
			mapChat.put("action", "delete-chat");
			mapChat.put("data", chatStatusDto.getChat());
			break;
		case "revoke":
			mapChat.put("action", "revoke-chat");
			mapChat.put("data", chatStatusDto.getChat());
			break;
		default:
			break;
		}
		websocketService.sendMessageChat(chatStatusDto.getTopicId(), mapChat);
	}

	public void processActionSystemPost(PostDto postDto) throws JsonProcessingException {
		UserDetail userReciver = postDto.getUserReciver();
		UserDetail userSender = postDto.getUserSender();
		String type = "post";
		NotifyDtoRequest notifyUnfiendOrAcceptFriend = generatorNotifyDtoForPost(userReciver, userSender,
				postDto, type);

		rabbitSenderService.convertAndSendNotify(notifyUnfiendOrAcceptFriend);
	}

	private NotifyDtoRequest generatorNotifyDtoForPost(UserDetail userReciver, UserDetail userSender, PostDto postDto,
			String type) throws JsonProcessingException {
		
		Map<String, Object> notifyValue = new HashMap<>();
		notifyValue.put("user", objectMapper.writeValueAsString(userReciver));
		notifyValue.put("action", postDto.getAction());
		List<String> param = new ArrayList<>();
		if(postDto.getPostId() != null) {
			String notifyPostParam = String.format("post=%s", postDto.getPostId());
			param.add(notifyPostParam);
		}
		
		if(postDto.getCommentId() != null) {
			String notifyCommnetParam = String.format("comment=%s", postDto.getCommentId());
			param.add(notifyCommnetParam);
		}
		
		NotifyDtoRequest notifyDto = new NotifyDtoRequest();
		notifyDto.setUserId(userSender.getId());
		notifyDto.setParam(String.join("&", param));
		notifyDto.setValue(objectMapper.writeValueAsString(notifyValue));
		notifyDto.setType(type);

		return notifyDto;
	}

	public void processActionSystemSub(SubScriberDto subScriberDto) throws JsonProcessingException {
		UserDetail userReciver = subScriberDto.getUserReciver();
		UserDetail userSender = subScriberDto.getUserSender();
		String type = "subscriber";
		NotifyDtoRequest notifyUnfiendOrAcceptFriend = generatorNotifyDtoForSub(userReciver, userSender,
				subScriberDto ,type);

		rabbitSenderService.convertAndSendNotify(notifyUnfiendOrAcceptFriend);
	}

	private NotifyDtoRequest generatorNotifyDtoForSub(UserDetail userReciver, UserDetail userSender,
			SubScriberDto subScriberDto, String type) throws JsonProcessingException {
		Map<String, Object> notifyValue = new HashMap<>();
		notifyValue.put("user", objectMapper.writeValueAsString(userReciver));
		notifyValue.put("action", "subscriber");
		
		List<String> param = new ArrayList<>();
		if(subScriberDto.getUserId() != null) {
			String notifyPostParam = String.format("userId=%s", subScriberDto.getUserId());
			param.add(notifyPostParam);
		}
		NotifyDtoRequest notifyDto = new NotifyDtoRequest();
		notifyDto.setUserId(userSender.getId());
		notifyDto.setParam(String.join("&", param));
		notifyDto.setValue(objectMapper.writeValueAsString(notifyValue));
		notifyDto.setType(type);
		
		return notifyDto;
	}

}
