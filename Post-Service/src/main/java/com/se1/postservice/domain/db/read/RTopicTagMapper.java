package com.se1.postservice.domain.db.read;


import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.se1.postservice.domain.payload.dto.TopicTagResponse;

@Mapper
public interface RTopicTagMapper {

	List<TopicTagResponse> findAllByName(@Param("tagName") String tagName);
}
