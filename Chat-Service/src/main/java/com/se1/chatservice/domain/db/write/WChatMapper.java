package com.se1.chatservice.domain.db.write;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface WChatMapper {

	void updateStatus(@Param("id") Long id, @Param("content") String content, @Param("userDeleteId") Long userDeleteId);

}
