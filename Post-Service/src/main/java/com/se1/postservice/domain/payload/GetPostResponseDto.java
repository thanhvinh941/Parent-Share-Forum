package com.se1.postservice.domain.payload;

import java.util.List;

import lombok.Data;

@Data
public class GetPostResponseDto {

	private Integer id;
	private String title;
	private String metaTitle;
	private String slug;
	private String summary;
	private String context;
	private long likeCount;
	private String hashTag;
	private List<String> imageList;
	private TopicTag topicTag;
	private User user;
	
	@Data
	public class TopicTag {
		private Integer id;
		private String name;
	}
	
	@Data
	public class User {
		private Integer id;
		private String name;
		private String email;
		private String imageUrl;
		private Boolean isExpert;
		private Long licenceId;
		private Double ratingCount;
		private String topicId;
	}
	
}
