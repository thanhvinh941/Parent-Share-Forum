package com.se1.postservice.domain.payload;

import java.util.List;

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
	public class PostUser{
		private Long id;
		private String name;
		private String imageUrl;
		private boolean isExpert;
		private Double rating;
		private Byte status;
	}
}
