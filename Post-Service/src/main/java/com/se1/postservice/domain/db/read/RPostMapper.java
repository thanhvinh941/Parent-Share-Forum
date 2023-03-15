package com.se1.postservice.domain.db.read;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface RPostMapper {

	Object findUserPostByPostId(Long postId, Long userId);
}
