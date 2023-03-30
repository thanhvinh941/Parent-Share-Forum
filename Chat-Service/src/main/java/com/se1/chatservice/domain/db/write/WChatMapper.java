package com.se1.chatservice.domain.db.write;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface WChatMapper {

	Long updateStatus(long id, int action, String content, Long userId);

}
