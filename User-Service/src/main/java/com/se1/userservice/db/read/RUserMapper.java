package com.se1.userservice.db.read;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.se1.userservice.model.User;

@Mapper
public interface RUserMapper {

	List<User> find(@Param("query") String query);

}
