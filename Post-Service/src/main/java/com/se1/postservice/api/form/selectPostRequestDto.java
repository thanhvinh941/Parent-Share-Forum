package com.se1.postservice.api.form;

import lombok.Data;

@Data
public class selectPostRequestDto {
	private String postId;
	private Integer memberId;
	private String title;
	private String metaTitle;
	private String summary;
	private Byte publishedFlg;
	private String context;
}
