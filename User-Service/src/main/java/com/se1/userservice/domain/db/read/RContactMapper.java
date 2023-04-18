package com.se1.userservice.domain.db.read;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.se1.userservice.domain.db.dto.ContactDto;

@Mapper
public interface RContactMapper {

	List<ContactDto> findContactByUserIdAndTopicId(@Param("userId") Long userId, @Param("topicId") String topicId);
}
