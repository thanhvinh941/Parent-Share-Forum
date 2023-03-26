package com.se1.systemservice.domain.rabbitMQ;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.se1.systemservice.config.MqConfig;
import com.se1.systemservice.config.SCMConstant;
import com.se1.systemservice.domain.payload.NotifycationDto;
import com.se1.systemservice.domain.payload.RabbitRequest;
import com.se1.systemservice.domain.payload.dto.ContactDto;
import com.se1.systemservice.domain.service.SystemListenerService;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class SystemListener {

	@Autowired
	private ObjectMapper objectMapper;
	
	@Autowired
	private SystemListenerService systemListenerService;
	
	@RabbitListener(queues = MqConfig.SYSTEM_QUEUE)
	public void listener(RabbitRequest rabbitRequest) throws JsonProcessingException {
		log.info("SYSTEM_QUEUE listener message:  {}", objectMapper.writeValueAsString(rabbitRequest));
		
		String action = rabbitRequest.getAction();
		switch (action) {
		case SCMConstant.SYSTEM_CONTACT:
			ContactDto contactDto = new ContactDto();
			BeanUtils.copyProperties(rabbitRequest.getData(), contactDto);
			systemListenerService.processActionSystemContact(contactDto);
			break;

		default:
			break;
		}
	}
}
