package com.se1.userservice.domain.payload;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class ContactDtoForChat {
	private Long id;
	private User userFriend;
	private int status; // 0 : Not Friend, 1 : Request Friend, 2 : Friend
    private String topicContactId;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date createAt;
    private List<Chat> chats;
    private boolean isBlocked;
    private boolean isBlock;
    
    @Data
    public static class Chat{
    	private Long id;
    	private User user;
    	private String content;
    	private int status;
    	private String topicId;
    	private Chat chatParent;

        @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    	private Date createAt;
    	private Long userDeleteId;
    }
    
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
