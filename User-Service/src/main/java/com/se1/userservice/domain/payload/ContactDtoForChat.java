package com.se1.userservice.domain.payload;

import java.util.Date;
import java.util.List;

import lombok.Data;

@Data
public class ContactDtoForChat {
	private Long id;
	private UserDetail userFriend;
	private int status; // 0 : Not Friend, 1 : Request Friend, 2 : Friend
    private String topicContactId;
    private Date createAt;
    private List<Chat> chats;
    
    @Data
    public static class Chat{
    	private Long id;
    	private User user;
    	private String content;
    	private int status;
    	private String topicId;
    	private Chat chatParent;
    	private Date createAt;
    	private Long userDeleteId;
    	
    	@Data
    	public static class User {
    		private Long id;
    		private String email;
    		private String name;
    		private String imageUrl;
    		private String role;
    		private Boolean isExpert;
    		private Double rating;
    		private int status;
    		private String topicId;
    	}
    }
}
