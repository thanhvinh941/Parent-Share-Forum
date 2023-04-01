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
	public void listener(NotifyDtoRequest notifyDtoRequest) throws JsonProcessingException {
		log.info("NOTIFY_QUEUE listener message:  {}", objectMapper.writeValueAsString(notifyDtoRequest));
		notifyListenerService.processNotify(notifyDtoRequest);
	}
}
