package com.se1.userservice.service;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import com.se1.userservice.config.MqConfig;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RabbitSenderService {

	private final RabbitTemplate rabbitTemplate;
	
	public void convertAndSendNotifycation(Object request) {
		rabbitTemplate.convertAndSend(MqConfig.Notification_EXCHANGE, MqConfig.Notification_ROUTING_KEY, request);

	}
	
}
