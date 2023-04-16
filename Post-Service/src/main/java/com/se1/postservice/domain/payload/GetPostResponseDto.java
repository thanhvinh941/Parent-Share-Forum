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
	private long likeCount;
	private long disLikeCount;
	private long commentCount;
	private long shareCount;
	
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
