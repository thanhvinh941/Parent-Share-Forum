package com.se1.postservice.domain.payload;

import java.util.List;

import lombok.Data;

@Data
public class PostDto {
	private Integer id;
	private Integer userId;
	private String userName;
	private String title;
	private String metaTitle;
	private String slug;
	private String summary;
	private String context;
	private long likeCount;
	private String hashTag;
	private PostTopicTagDto topicTag;
	private List<String> imageList;
	
	@Data
	public class PostTopicTagDto {
		private Integer id;
		private String topicTagName;
	}
}
