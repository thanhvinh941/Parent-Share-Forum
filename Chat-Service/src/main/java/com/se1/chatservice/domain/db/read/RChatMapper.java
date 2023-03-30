package com.se1.chatservice.domain.db.read;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.se1.chatservice.model.Chat;

@Mapper
public interface RChatMapper {

	List<Chat> getAllChat(String topic,int limit,int offset);
}
