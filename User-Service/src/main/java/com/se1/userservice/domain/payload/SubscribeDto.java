package com.se1.userservice.domain.payload;

import java.util.Date;

import lombok.Data;

@Data
public class SubscribeDto {
	private Long id;
	private UserDetail userExpertId;
	private Date createAt;
}
