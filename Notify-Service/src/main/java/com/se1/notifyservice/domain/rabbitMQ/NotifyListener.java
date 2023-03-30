package com.se1.notifyservice.domain.rabbitMQ;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.se1.notifyservice.config.MqConfig;
import com.se1.notifyservice.config.SCMConstant;
import com.se1.notifyservice.domain.rabbitMQ.dto.NotifyDtoRequest;
import com.se1.notifyservice.domain.rabbitMQ.dto.RabbitRequest;
import com.se1.notifyservice.domain.service.NotifyListenerService;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class NotifyListener {

	@Autowired
	private ObjectMapper objectMapper;
	
	@Autowired
	private NotifyListenerService notifyListenerService;
	
	@RabbitListener(queues = MqConfig.NOTIFY_QUEUE)
	public void listener(RabbitRequest rabbitRequest) throws JsonProcessingException {
		log.info("SYSTEM_QUEUE listener message:  {}", objectMapper.writeValueAsString(rabbitRequest));
		
		String action = rabbitRequest.getAction();
		NotifyDtoRequest notifyDto = new NotifyDtoRequest();
		String type = "";
		switch (action) {
		case SCMConstant.NOTIFY_CONTACT:
			BeanUtils.copyProperties(rabbitRequest.getData(), notifyDto);
			type = SCMConstant.NOTIFY_CONTACT;
			break;

		
		default:
			break;
		}
		
		notifyListenerService.processNotify(type, notifyDto);
	}
}
