package com.se1.postservice.domain.db.write;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface WTopicTagMapper {

	Integer updateTopicTag(@Param("condition") String where);
}
