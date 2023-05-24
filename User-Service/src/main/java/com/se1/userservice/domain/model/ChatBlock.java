package com.se1.userservice.domain.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table
public class ChatBlock {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	
	private Long userBlockId; // người chặn tin nhắn
	
	private Long userBlockedId; // người bị chặn tin nhắn
	
	private String topicId;
}
