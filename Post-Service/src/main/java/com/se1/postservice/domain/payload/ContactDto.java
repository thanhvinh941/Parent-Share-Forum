package com.se1.postservice.domain.payload;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class ContactDto {
	private Long id;
	private UserDetail userFriend;
	private int status; // 0 : Not Friend, 1 : Request Friend, 2 : Friend
    private String topicContactId;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date createAt;
}
