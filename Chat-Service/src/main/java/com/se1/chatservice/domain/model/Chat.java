package com.se1.chatservice.domain.model;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
@Table(name = "chats")
public class Chat {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	
	@Column(nullable = false)
	private Long userId;
	
	@Column(nullable = false)
	private String content;
	
	@Column(nullable = false)
	private int status;
	
	@Column(nullable = false)
	private String topicId;
	
	private Long chatParent;
	
	@Column(nullable = false)
	@JsonFormat(pattern="yyyy-MM-dd hh:mm:ss")
	private String createAt;
}
