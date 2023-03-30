package com.se1.userservice.domain.rabbitMQ.payload;

import java.util.Date;

import com.se1.userservice.domain.payload.UserDetail;

import lombok.Data;

@Data
public class ContactResponse {
	private Long id;
	private UserDetail userReciver;
	private UserDetail userSender;
	private int status; // 0 : Not Friend, 1 : Request Friend, 2 : Friend
    private String topicId;
    private Date createAt;
}
