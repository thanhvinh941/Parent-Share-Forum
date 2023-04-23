package com.se1.userservice.domain.db.write;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface WUserMapper {

	void updateUserStatus(@Param("userId") Long userId,@Param("status") int status);
	
	void updateLicenceImageToUser(String licenceFileName, Long userId);

	void updateLicenceToUser(Long licenceId, Long userId);
}
