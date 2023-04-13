package com.se1.userservice.domain.db.write;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface WUserMapper {

	void updateUserStatus(Long userId, int status);
	
	void updateLicenceImageToUser(String licenceFileName, Long userId);

	void updateLicenceToUser(Long licenceId, Long userId);
}
