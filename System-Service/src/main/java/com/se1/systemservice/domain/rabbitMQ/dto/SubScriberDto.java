package com.se1.systemservice.domain.rabbitMQ.dto;

import com.se1.systemservice.domain.payload.dto.UserDetail;

import lombok.Data;

@Data
public class SubScriberDto {
	private Long userId;
	private UserDetail userReciver;
	private UserDetail userSender;
}
