package com.se1.userservice.domain.service;

import org.springframework.stereotype.Service;

import com.se1.userservice.domain.db.read.RChatBlockMapper;
import com.se1.userservice.domain.model.ApiRequestEntity;
import com.se1.userservice.domain.model.ChatBlock;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class ChatBlockService {

	private final RChatBlockMapper rChatBlockMapper;
	
	public ChatBlock getChatBlock(ApiRequestEntity request) throws Exception {
		ChatBlock chatBlock = null;
		try {			
			chatBlock = rChatBlockMapper.selectByConditionStr(request.getConditionStr(),
					request.getOrder(), request.getLimit(), request.getOffet());
		} catch (Exception e) {
			log.error("Call Internal API ERROR getChatBlock ", e);
			throw new Exception(e.getMessage());
		}
		return chatBlock;
	};
}
