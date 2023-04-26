package com.se1.postservice.domain.db.read;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.se1.postservice.domain.db.dto.PostDto;

@Mapper
public interface RPostMapper {

	Object findPost(String query);
	
	List<PostDto> findAllPostByTitle(@Param("title") String title, @Param("offset") int offset, @Param("userId") Long userId);
	
	List<PostDto> findAllPostByHashTag(@Param("hashTag") String hashTag, @Param("offset") int offset, @Param("userId") Long userId);
	
	List<PostDto> findAllPostByUserId(@Param("userIds") String userIds, @Param("offset") int offset, @Param("userId") Long userId);

	List<PostDto> findAllPostByCondition(@Param("conditionList") Map<String, String> conditionList, @Param("offset") int offset, @Param("userId") Long userId);
	
	List<PostDto> findPostById(@Param("ids") String ids, @Param("userId") Long userId);

	List<PostDto> findPostMostLike(@Param("userId") Long userId);

	List<PostDto> findPostMostComment(@Param("userId") Long userId);

	List<PostDto> findPostMostView(@Param("userId") Long userId);
}
