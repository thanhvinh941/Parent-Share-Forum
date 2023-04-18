package com.se1.userservice.domain.db.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class ContactDto {
	private Long id;
	private Long userReciverId;
	private Long userSenderId;
	private int status; // 0 : Not Friend, 1 : Request Friend, 2 : Friend
    private String topicId;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date createAt;
}
