package com.se1.notifyservice.domain.service;

import java.util.Date;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.se1.notifyservice.config.MqConfig;
import com.se1.notifyservice.config.SCMConstant;
import com.se1.notifyservice.domain.model.Notify;
import com.se1.notifyservice.domain.payload.ApiResponseEntity;
import com.se1.notifyservice.domain.payload.NotifyResponse;
import com.se1.notifyservice.domain.payload.NotifyResponse.User;
import com.se1.notifyservice.domain.rabbitMQ.dto.NotifyDtoRequest;
import com.se1.notifyservice.domain.rabbitMQ.dto.RabbitRequest;
import com.se1.notifyservice.domain.repository.NotifyRepository;
import com.se1.notifyservice.domain.restclient.UserServiceRestTemplateClient;

@Service
public class NotifyListenerService {

	@Autowired
	private NotifyRepository notifyRepository;
	
	@Autowired
	private RabbitTemplate rabbitTemplate;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@Autowired
	private UserServiceRestTemplateClient restTemplateClient;
	
	public void processNotify(NotifyDtoRequest notifyDtoRequest) throws JsonProcessingException {
		Notify notify = new Notify();
		BeanUtils.copyProperties(notifyDtoRequest, notify);
		notify.setDelFlg(new Byte("0"));
		notify.setCreateAt(new Date());
		Notify notifySave = notifyRepository.save(notify);
		
		NotifyResponse notifyResponse = new NotifyResponse();
		BeanUtils.copyProperties(notifySave, notifyResponse);
		NotifyResponse.User user = new User();
		ApiResponseEntity apiResponseEntityResult = (ApiResponseEntity) restTemplateClient.findById(notifySave.getUserId());
		if (apiResponseEntityResult.getStatus() == 1) {
			String apiResultStr = objectMapper.writeValueAsString(apiResponseEntityResult.getData());
			user = objectMapper.readValue(apiResultStr, NotifyResponse.User.class);
		}
		if(user != null) {
			notifyResponse.setUser(user);
			RabbitRequest rabbitRequest = new RabbitRequest();
			rabbitRequest.setAction(SCMConstant.SYSTEM_NOTIFY);
			rabbitRequest.setData(notifyResponse);
			rabbitTemplate.convertAndSend(MqConfig.SYSTEM_EXCHANGE ,MqConfig.SYSTEM_ROUTING_KEY, rabbitRequest);
		}
	
	}

}
