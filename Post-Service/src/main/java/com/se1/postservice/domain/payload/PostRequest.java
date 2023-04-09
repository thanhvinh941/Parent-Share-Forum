package com.se1.postservice.domain.payload;

import java.util.List;

import lombok.Data;

@Data
public class PostRequest {
	private String title;
	private String context;
	private String hashTag;
	private Integer topicTagId;
	private List<String> imageList;
}
