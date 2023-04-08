package com.se1.postservice.domain.db.read;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.se1.postservice.domain.db.dto.PostDto;

@Mapper
public interface RPostMapper {

	Object findPost(String query);
	
	List<PostDto> findAllByIdWithCondition(@Param("userIds") String userIds, @Param("offset") int offset);
}
