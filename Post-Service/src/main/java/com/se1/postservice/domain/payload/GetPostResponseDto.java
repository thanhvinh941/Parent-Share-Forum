package com.se1.postservice.domain.payload;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class GetPostResponseDto {

	private long id;
	private String title;
	private String summary;
	private String context;
	private String hashTag;
	private List<String> imageList;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
	private Date createAt;
	private Integer likeCount;
	private Integer disLikeCount;
	private Integer commentCount;
	private Integer shareCount;
	
	private TopicTag topicTag;
	private User user;
	@Data
	public static class TopicTag {
		private Integer id;
		private String color;
		private String tagName;
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
