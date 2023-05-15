package com.se1.postservice.domain.payload;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class PostDto {
	private Integer id;
	private PostUser user;
	private String userName;
	private String title;
	private String metaTitle;
	private String slug;
	private String summary;
	private String context;
	private long likeCount;
	private String hashTag;
	private PostTopicTag topic;
	private List<String> imageList;

	@Data
	public class PostTopicTag {
		private Integer id;
		private String topicTagName;
		private String color;
	}

	@Data
	public static class PostUser {
		private Long id;
		private String email;
		private String name;
		private String imageUrl;
		private boolean isExpert;
		private Double rating;
		private Byte status;
		@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
		private Date lastTime = new Date();
	}
}
