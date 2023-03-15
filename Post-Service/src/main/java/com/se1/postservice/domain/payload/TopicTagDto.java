package com.se1.postservice.domain.payload;

import lombok.Data;

@Data
public class TopicTagDto {

	private Integer id;
	private String tagName;
	private String color;
}
