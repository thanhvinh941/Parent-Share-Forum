package com.se1.postservice.domain.db.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class PostDto {
	private Long id;
	private Long userId;
	private String title;
	private String summary;
	private Integer status;
	private String context;
	private String imageList;
	private String hashTag;
	private Integer topicTagId;
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date createAt;
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date updateAt;
	private Integer likeCount;
	private Integer dislikeCount;
	private Integer commentCount;
	private Integer shareCount;
}
