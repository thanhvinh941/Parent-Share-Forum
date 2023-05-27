package com.se1.userservice.domain.service;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.se1.userservice.domain.db.read.RChatBlockMapper;
import com.se1.userservice.domain.model.ApiRequestEntity;
import com.se1.userservice.domain.model.ChatBlock;
import com.se1.userservice.domain.payload.request.ChatBlockRequest;
import com.se1.userservice.domain.repository.ChatBlockRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class ChatBlockService {

	private final RChatBlockMapper rChatBlockMapper;
	private final ChatBlockRepository chatBlockRepository;
	
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
	}

	public ChatBlock createChatBlock(ChatBlockRequest request, Long id) {
		ChatBlock chatBlockRequest = new ChatBlock();
		BeanUtils.copyProperties(request, chatBlockRequest);
		chatBlockRequest.setUserBlockId(id);
		
		try {
			return chatBlockRepository.save(chatBlockRequest);
		} catch (Exception e) {
			return null;
		}
	}

	public ChatBlock deleteChatBlock(ChatBlockRequest request, Long id) {
		StringBuffer conditionStr = new StringBuffer();
		conditionStr.append(String.format("topic_id = %s", request.getTopicId()));
		conditionStr.append(String.format(" AND user_blocked_id = %d", request.getUserBlockedId()));
		conditionStr.append(String.format(" AND user_block_id = %d", id));
		
		ChatBlock chatBlock = rChatBlockMapper.selectByConditionStr(conditionStr.toString(),
				null, null, null);
		if(chatBlock != null) {
			chatBlockRepository.deleteById(chatBlock.getId());
		}
		
		return chatBlock;
	};
	
	
}
