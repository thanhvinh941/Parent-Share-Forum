package com.se1.userservice.domain.rabbitMQ;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.se1.userservice.config.MqConfig;
import com.se1.userservice.domain.common.SCMConstant;
import com.se1.userservice.domain.db.write.WUserMapper;
import com.se1.userservice.domain.payload.request.UpdateUserStatusRequest;
import com.se1.userservice.domain.payload.response.RabbitRequest;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class UserListener {

	@Autowired
	private ObjectMapper objectMapper;
	
	private WUserMapper wUserMapper;
	
	@RabbitListener(queues = MqConfig.USER_QUEUE)
	public void listener(RabbitRequest rabbitRequest) throws JsonProcessingException {
		log.info("USER_QUEUE listener message:  {}", objectMapper.writeValueAsString(rabbitRequest));
		if(rabbitRequest.getAction().equals(SCMConstant.USER_UPDATE_STATUS)) {
			UpdateUserStatusRequest request = new UpdateUserStatusRequest();
			BeanUtils.copyProperties(rabbitRequest.getData(), request);
			
			wUserMapper.updateUserStatus(request.getId(), request.getStatus());
		}
	}
}
