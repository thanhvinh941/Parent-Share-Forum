package com.se1.systemservice.domain.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.se1.systemservice.config.SCMConstant;
import com.se1.systemservice.domain.payload.dto.ContactDto;
import com.se1.systemservice.domain.payload.dto.NotifyDto;
import com.se1.systemservice.domain.payload.dto.UserDetail;
import com.se1.systemservice.domain.rabbitMQ.dto.ChatDto;
import com.se1.systemservice.domain.rabbitMQ.dto.ChatStatusDto;
import com.se1.systemservice.domain.rabbitMQ.dto.NotifyDtoRequest;

@Service
public class SystemListenerService {

	@Autowired
	private WebsocketService websocketService;

	@Autowired
	private RabbitSenderService rabbitSenderService;

	@Autowired
	private ObjectMapper objectMapper;

	public void processActionSystemContact(ContactDto data) throws JsonProcessingException {
		int status = data.getStatus();
		UserDetail userReciver = data.getUserReciver();
		UserDetail userSender = data.getUserSender();
		String type = "contact";
		switch (status) {
		case 1: // Request Friend
			NotifyDtoRequest notifyRequestFriend = generatorNotifyDto(userSender, userReciver, data.getTopicId(),
					status, type);

			rabbitSenderService.convertAndSendNotify(notifyRequestFriend);
			break;
		case 0: // Unfiend
		case 2: // Accept Friend
			NotifyDtoRequest notifyUnfiendOrAcceptFriend = generatorNotifyDto(userReciver, userSender,
					data.getTopicId(), status, type);

			rabbitSenderService.convertAndSendNotify(notifyUnfiendOrAcceptFriend);
			break;
		default:
			break;
		}

	}

	private NotifyDtoRequest generatorNotifyDto(UserDetail userFrom, UserDetail userTo, String TopicId, int status,
			String type) throws JsonProcessingException {
		Map<String, Object> notifyValue = new HashMap<>();
		notifyValue.put("userSender", objectMapper.writeValueAsString(userFrom));
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

	public void processActionSystemChat(ChatDto chatDto) {
		Map<String, Object> mapChat = new HashMap<>();
		mapChat.put("action", "");
		mapChat.put("data", chatDto);
		websocketService.sendMessageChat(chatDto.getTopicId(), mapChat);
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
}
