package com.se1.postservice.domain.db.read;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.se1.postservice.domain.db.dto.PostDto;

@Mapper
public interface RPostMapper {

	Object findPost(String query);
	
	List<PostDto> findAllPostByUserId(@Param("userIdList") List<Long> userIds, @Param("offset") int offset);

	List<PostDto> findAllPostByCondition(@Param("conditionList") Map<String, String> conditionList, @Param("offset") int offset);

}
