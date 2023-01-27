package com.se1.forumservice.domain.db.write;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface WForumMapper {

	Integer updateForum(@Param("set") String set, @Param("forumId") Integer forumId);

	Integer uDeleteForum(@Param("forumId") Integer forumId);
}
