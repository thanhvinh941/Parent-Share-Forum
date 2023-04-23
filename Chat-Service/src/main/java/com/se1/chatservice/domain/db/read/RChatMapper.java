package com.se1.chatservice.domain.db.read;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.se1.chatservice.domain.db.dto.ChatDto;
import com.se1.chatservice.model.Chat;

@Mapper
public interface RChatMapper {

	List<ChatDto> getAllChat(@Param("topicId") String topicId,@Param("limit") Integer limit,@Param("offset") Integer offset, @Param("userDelete") Long userDelete);
}
