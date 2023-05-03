package com.se1.userservice.domain.db.write;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface WUserMapper {

	void updateUserStatus(@Param("userId") Long userId,@Param("status") int status);
	
	void updateEmailStatus(@Param("userId") Long userId);

}
