package com.se1.systemservice.model;

import java.util.Date;

import lombok.Data;

@Data
public class ChatDto {
	private Long userId;
	private String content;
	private Date createAt;
}
