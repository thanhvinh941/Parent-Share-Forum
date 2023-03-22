package com.se1.chatservice.service;

import java.util.Date;

import org.springframework.stereotype.Service;

import com.se1.chatservice.domain.db.write.WChatMapper;
import com.se1.chatservice.model.Chat;
import com.se1.chatservice.payload.CreateChatRequest;
import com.se1.chatservice.payload.UpdateChatRequest;
import com.se1.chatservice.repository.ChatRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class ChatService {

	private final ChatRepository chatRepository;
	private final WChatMapper chatMapper;

	public void processCreate(CreateChatRequest chatRequest) {
		log.info("processCreate ");
		Chat chat = new Chat();
		chat.setChatParent(chatRequest.getChatParentId());
		chat.setUserId(chatRequest.getUserId());
		chat.setCreateAt(new Date());
		chat.setContent(chatRequest.getContent());
		chat.setStatus(1);
		chat.setTopicId(chatRequest.getTopicId());
		chatRepository.save(chat);
	}

	public void processChangeStatus(UpdateChatRequest chatRequest) {
		log.info("processChangeStatus ");
		int action = 0;
		Long userId = null;
		String content = "";
		switch (chatRequest.getAction()) {
		case "delete":
			action = 0;
			userId = chatRequest.getUserId();
			break;
		case "revoke":
			action = 2;
			content = "Tin nhắn đã được thu hồi";
		default:
			break;
		}

		chatMapper.updateStatus(chatRequest.getId(), action, content, userId);
	}

}
