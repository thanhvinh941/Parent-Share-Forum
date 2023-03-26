package com.se1.postservice.domain.payload;

import lombok.Data;

@Data
public class CreateTopicTagRequest {
	private String tagName;
	private String color;
}
