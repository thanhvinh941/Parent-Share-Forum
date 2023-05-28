package com.se1.userservice.domain.db.write;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface WUserMapper {

	void updateUserStatus(@Param("userId") Long userId,@Param("status") int status);
	
	void updateEmailStatus(@Param("userId") Long userId);

	void updateUser(@Param("recordStr") String recordStr,
			@Param("conditionStr") String conditionStr,
			@Param("order") String order, 
			@Param("limit") Integer limit, 
			@Param("offset") Integer offset);

	void insertUser(@Param("recordStr") String recordStr,
			@Param("conditionStr") String conditionStr,
			@Param("order") String order, 
			@Param("limit") Integer limit, 
			@Param("offset") Integer offset);

}
