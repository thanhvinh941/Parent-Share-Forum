package com.se1.systemservice.domain.payload;

import lombok.Data;

@Data
public class UserContactDto {

	private Integer userId;
	private String contactTopicId;
	private String userInfoName;
	private boolean status;
	private boolean isChoose;
	
}
