package com.se1.systemservice.domain.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.se1.systemservice.config.SCMConstant;
import com.se1.systemservice.domain.payload.dto.ContactDto;
import com.se1.systemservice.domain.payload.dto.NotifyDto;
import com.se1.systemservice.domain.payload.dto.UserDetail;
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
		switch (status) {
		case 1: // Request Friend
			NotifyDtoRequest notifyRequestFriend = generatorNotifyDto(userSender, userReciver, data.getTopicId(), status);
			
			rabbitSenderService.convertAndSendNotify(notifyRequestFriend);
//			websocketService.sendUser(userReciver.getTopicId(), data);
			break;
		case 0: // Unfiend
		case 2: // Accept Friend
			NotifyDtoRequest notifyUnfiendOrAcceptFriend = generatorNotifyDto(userReciver, userSender,data.getTopicId(), status);
			
			rabbitSenderService.convertAndSendNotify(notifyUnfiendOrAcceptFriend);
//			websocketService.sendUser(userSender.getTopicId(), data);
			break;
		default:
			break;
		}
		
	}

	private NotifyDtoRequest generatorNotifyDto(UserDetail userFrom, UserDetail userTo, String TopicId, int status) throws JsonProcessingException {
		String notifyUserValue = String.format("userSender=%s", objectMapper.writeValueAsString(userFrom));
		String notifyActionValue = String.format("action=%s", SCMConstant.getContactActionByStatus(status));
		String notifyContactParam = String.format("topicId=%s", TopicId);
		String notifyStatusParam = String.format("status=%d", status);
		
		NotifyDtoRequest notifyDto = new NotifyDtoRequest();
		notifyDto.setUserId(userTo.getId());
		notifyDto.setParam(String.join("&", List.of(notifyStatusParam, notifyContactParam)));
		notifyDto.setValue(String.join("&", List.of(notifyUserValue, notifyActionValue)));
		
		return notifyDto;
	}

	public void processActionSystemNotify(NotifyDto notifyDto) throws JsonProcessingException {
		websocketService.sendUser(notifyDto.getUser().getTopicId(), notifyDto);
	}
}
