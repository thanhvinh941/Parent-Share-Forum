package com.se1.notifyservice.domain.service;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.se1.notifyservice.config.MqConfig;
import com.se1.notifyservice.config.SCMConstant;
import com.se1.notifyservice.domain.db.write.WNotifyMapper;
import com.se1.notifyservice.domain.dto.NotifyDto;
import com.se1.notifyservice.domain.model.Notify;
import com.se1.notifyservice.domain.payload.ApiResponseEntity;
import com.se1.notifyservice.domain.payload.NotifyResponse;
import com.se1.notifyservice.domain.payload.NotifyResponse.User;
import com.se1.notifyservice.domain.payload.UserDetail;
import com.se1.notifyservice.domain.rabbitMQ.dto.NotifyDtoRequest;
import com.se1.notifyservice.domain.rabbitMQ.dto.RabbitRequest;
import com.se1.notifyservice.domain.repository.NotifyRepository;
import com.se1.notifyservice.domain.restclient.UserServiceRestTemplateClient;

@Service
public class NotifyListenerService {

	@Autowired
	private WNotifyMapper mapper;
	
	@Autowired
	private NotifyRepository notifyRepository;
	
	@Autowired
	private RabbitTemplate rabbitTemplate;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@Autowired
	private UserServiceRestTemplateClient restTemplateClient;
	
	public void processNotify(String type, NotifyDtoRequest notifyDtoRequest) throws JsonProcessingException {
		NotifyDto notifyDto = new NotifyDto();
		BeanUtils.copyProperties(notifyDtoRequest, notifyDto);
		notifyDto.setType(type);
		
		Long notifyId = mapper.insertNotify(notifyDto);
		Notify notify = notifyRepository.findById(notifyId).get();
		
		NotifyResponse notifyResponse = new NotifyResponse();
		BeanUtils.copyProperties(notify, notifyResponse);
		NotifyResponse.User user = new User();
		ApiResponseEntity apiResponseEntityResult = (ApiResponseEntity) restTemplateClient.findById(notify.getUserId());
		if (apiResponseEntityResult.getStatus() == 1) {
			String apiResultStr = objectMapper.writeValueAsString(apiResponseEntityResult.getData());
			user = objectMapper.readValue(apiResultStr, NotifyResponse.User.class);
		}
		notifyResponse.setUser(user);
		
		RabbitRequest rabbitRequest = new RabbitRequest();
		rabbitRequest.setAction(SCMConstant.SYSTEM_NOTIFY);
		rabbitRequest.setData(notifyResponse);
		rabbitTemplate.convertAndSend(MqConfig.SYSTEM_EXCHANGE ,MqConfig.SYSTEM_ROUTING_KEY, rabbitRequest);
	}

}
