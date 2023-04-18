package com.se1.chatservice.payload;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class ContactDto {
	private Long id;
	private int status; // 0 : Not Friend, 1 : Request Friend, 2 : Friend
	private User userFriend;
    private String topicContactId;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date createAt;
    
    @Data
    public static class User {
    	private Long id;
    	private String name;
    	private String email;
    	private String imageUrl;
    	private Boolean isExpert;
    	private Double rating;
    	private Byte status; // 1: online, 2 offline
    	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
        private Date lastTime;
    }
}
