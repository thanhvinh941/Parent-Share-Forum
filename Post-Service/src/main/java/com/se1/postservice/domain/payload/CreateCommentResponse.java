package com.se1.postservice.domain.payload;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class CreateCommentResponse {
	private Long id;
	private String content;
	private User userId;
	private Long postId;
	private CreateCommentResponse comemntParent;
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
