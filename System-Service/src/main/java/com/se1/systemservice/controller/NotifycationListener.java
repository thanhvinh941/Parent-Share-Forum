package com.se1.systemservice.controller;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.se1.systemservice.config.MqConfig;
import com.se1.systemservice.payload.NotifycationDto;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class NotifycationListener {

	@Autowired
	private ObjectMapper objectMapper;
	
	@RabbitListener(queues = MqConfig.NOTIFY_QUEUE)
	public void listener(NotifycationDto notifycationDto) throws JsonProcessingException {
		log.info("Notification-Queue listener message:  {}", objectMapper.writeValueAsString(notifycationDto));
	}
}
