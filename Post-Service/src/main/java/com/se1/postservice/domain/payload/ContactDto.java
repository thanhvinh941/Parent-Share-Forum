package com.se1.postservice.domain.payload;

import java.util.Date;

import lombok.Data;

@Data
public class ContactDto {
	private Long id;
	private UserDetail userFriend;
	private int status; // 0 : Not Friend, 1 : Request Friend, 2 : Friend
    private String topicContactId;
    private Date createAt;
}
