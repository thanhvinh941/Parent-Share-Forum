package com.se1.postservice.domain.payload;

import lombok.Data;

@Data
public class UpdateTopicTagRequest {

	private Integer id;
	private String tagName;
	private String color;
	private Byte delFlg;
}
