package com.se1.userservice.domain.db.read;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.se1.userservice.domain.model.ChatBlock;

@Mapper
public interface RChatBlockMapper {

	ChatBlock selectByConditionStr(@Param("conditionStr") String conditionStr, 
			@Param("order") String order, @Param("limit") Integer limit, @Param("offset")  Integer offset);

}
