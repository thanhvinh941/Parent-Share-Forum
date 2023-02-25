package com.se1.postservice.domain.payload;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
public class GetPostResponseDto {

	private long id;
	private String title;
	private String metaTitle;
	private String slug;
	private String summary;
	private String context;
	private long likeCount;
	private long disLikeCount;
	private long commentCount;
	private long shareCount;
	private String hashTag;
	private List<String> imageList;
	private TopicTag topicTag;
	private User user;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
	private Date publishAt;
	private Comment comment;
	
	@Data
	public class TopicTag {
		private long id;
		private String name;
	}

	@Data
	public class Comment {
		private long id;
		private String text;
		private long likeCount;
		private long disLikeCount;
		private long commentChildCount;
		@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
		private Date createAt;
		private User User;
	}

	@Data
	public class User {
		private long id;
		private String name;
		private String email;
		private String imageUrl;
		private Boolean isExpert;
		private Double ratingCount;
		private String topicId;
	}
}
