package com.se1.postservice.domain.service;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import com.se1.postservice.common.MqConfig;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RabbitSenderService {

	private final RabbitTemplate rabbitTemplate;
	
	public void convertAndSendSysTem(Object request) {
		rabbitTemplate.convertAndSend(MqConfig.SYSTEM_EXCHANGE, MqConfig.SYSTEM_ROUTING_KEY, request);
	}
}
