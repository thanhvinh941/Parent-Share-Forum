package com.se1.systemservice.domain.payload.dto;

import java.util.Date;

import lombok.Data;

@Data
public class ContactDto {
	private Long id;
	private UserDetail userReciver;
	private UserDetail userSender;
	private int status; // 0 : Not Friend, 1 : Request Friend, 2 : Friend
    private String topicId;
    private Date createAt;
}
