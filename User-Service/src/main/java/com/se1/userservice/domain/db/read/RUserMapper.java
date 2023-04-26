package com.se1.userservice.domain.db.read;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.se1.userservice.domain.model.User;

@Mapper
public interface RUserMapper {

	List<User> find(@Param("query") String query ,@Param("offset") Integer offset);

	List<User> findAll(@Param("conditions") List<String> mapRequest,
			@Param("offset") Integer offset);

}
