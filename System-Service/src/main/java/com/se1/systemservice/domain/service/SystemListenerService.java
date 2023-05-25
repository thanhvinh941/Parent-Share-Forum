package com.se1.systemservice.domain.service;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;
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

	public void processActionSystemContact(ContactDto data) throws JsonProcessingException, UnsupportedEncodingException {
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
			String type) throws JsonProcessingException, UnsupportedEncodingException {
		Map<String, Object> notifyValue = new HashMap<>();
		notifyValue.put("user", new String(objectMapper.writeValueAsBytes(userFrom),"utf-8"));
		notifyValue.put("action", SCMConstant.getContactActionByStatus(status));
		String notifyContactParam = String.format("%s", TopicId);

		NotifyDtoRequest notifyDto = new NotifyDtoRequest();
		notifyDto.setUserId(userTo.getId());
		notifyDto.setParam(String.join("&", List.of(notifyContactParam)));
		notifyDto.setValue(new String(objectMapper.writeValueAsBytes(notifyValue),"utf-8"));
		notifyDto.setType(type);

		return notifyDto;
	}

	public void processActionSystemNotify(NotifyDto notifyDto) throws JsonProcessingException {
		websocketService.sendUser(notifyDto.getUser().getTopicId(), notifyDto);
	}

	public void processActionSystemChat(ChatDto chatDto) throws MalformedURLException, JsonProcessingException {
		String topicId = chatDto.getTopicId();
		Map<String, Object> mapChat = new HashMap<>();
		mapChat.put("status", 1);
		mapChat.put("error", null);
		mapChat.put("data", chatDto);
		websocketService.sendMessageChat(topicId, mapChat);
		
		ApiResponseEntity apiResponseEntity = (ApiResponseEntity) restTemplateClient.getContactByTopicId(topicId);
		ContactDto contact = objectMapper.convertValue(apiResponseEntity.getData(), ContactDto.class) ;
		UserDetail user1 = contact.getUserSender();
		UserDetail user2 = contact.getUserReciver();
		UserDetail userReciverNotify = new UserDetail();
		UserDetail userSendNotify = new UserDetail();
		if(chatDto.getUser().getId().equals(user1.getId())) {
			userReciverNotify = user2;
			userSendNotify = user1;
		}else {
			userReciverNotify = user1;
			userSendNotify = user2;
		}
		
		NotifyDto notifyDto = new NotifyDto();
		NotifyDto.User user = new NotifyDto.User();
		BeanUtils.copyProperties(userReciverNotify, user);
		Map<String, Object> notifyValue = new HashMap<>();
		notifyValue.put("user", objectMapper.writeValueAsString(userSendNotify));
		notifyValue.put("action", "new-message");
		notifyDto.setUser(user);
		notifyDto.setType("new-message");
		notifyDto.setValue(objectMapper.writeValueAsString(notifyValue));
		websocketService.sendUser(userReciverNotify.getTopicId(), notifyDto);
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

	public void processActionSystemPost(PostDto postDto) throws JsonProcessingException, UnsupportedEncodingException {
		UserDetail userReciver = postDto.getUserReciver();
		UserDetail userSender = postDto.getUserSender();
		String type = "post";
		NotifyDtoRequest notifyUnfiendOrAcceptFriend = generatorNotifyDtoForPost(userReciver, userSender,
				postDto, type);

		rabbitSenderService.convertAndSendNotify(notifyUnfiendOrAcceptFriend);
	}

	private NotifyDtoRequest generatorNotifyDtoForPost(UserDetail userReciver, UserDetail userSender, PostDto postDto,
			String type) throws JsonProcessingException, UnsupportedEncodingException {
		
		Map<String, Object> notifyValue = new HashMap<>();
		notifyValue.put("user", new String(objectMapper.writeValueAsBytes(userReciver), "utf-8"));
		notifyValue.put("action", postDto.getAction());
		List<String> param = new ArrayList<>();
		if(postDto.getPostId() != null) {
			String notifyPostParam = String.format("%s", postDto.getPostId());
			param.add(notifyPostParam);
		}
		
		NotifyDtoRequest notifyDto = new NotifyDtoRequest();
		notifyDto.setUserId(userSender.getId());
		notifyDto.setParam(String.join("&", param));
		notifyDto.setValue(new String(objectMapper.writeValueAsBytes(notifyValue), "utf-8"));
		notifyDto.setType(type);

		return notifyDto;
	}

	public void processActionSystemSub(SubScriberDto subScriberDto) throws JsonProcessingException, UnsupportedEncodingException {
		UserDetail userReciver = subScriberDto.getUserReciver();
		UserDetail userSender = subScriberDto.getUserSender();
		String type = "subscriber";
		NotifyDtoRequest notifyUnfiendOrAcceptFriend = generatorNotifyDtoForSub(userReciver, userSender,
				subScriberDto ,type);

		rabbitSenderService.convertAndSendNotify(notifyUnfiendOrAcceptFriend);
	}

	private NotifyDtoRequest generatorNotifyDtoForSub(UserDetail userReciver, UserDetail userSender,
			SubScriberDto subScriberDto, String type) throws JsonProcessingException, UnsupportedEncodingException {
		Map<String, Object> notifyValue = new HashMap<>();
		notifyValue.put("user", new String(objectMapper.writeValueAsBytes(userReciver), "utf-8"));
		notifyValue.put("action", "subscriber");
		
		List<String> param = new ArrayList<>();
		if(subScriberDto.getUserId() != null) {
			String notifyPostParam = String.format("%s", subScriberDto.getUserId());
			param.add(notifyPostParam);
		}
		NotifyDtoRequest notifyDto = new NotifyDtoRequest();
		notifyDto.setUserId(userSender.getId());
		notifyDto.setParam(String.join("&", param));
		notifyDto.setValue(new String(objectMapper.writeValueAsBytes(notifyValue), "utf-8"));
		notifyDto.setType(type);
		
		return notifyDto;
	}

}
