package com.se1.systemservice.domain.payload;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class Contact {
	private Long id;
	private Long userReciverId;
	private Long userSenderId;
	private int status; // 0 : Not Friend, 1 : Request Friend, 2 : Friend
    private String topicId;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date createAt;
}
