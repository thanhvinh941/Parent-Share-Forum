package com.se1.postservice.domain.payload;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class CreateCommentResponse {
	private Long id;
	private String content;
	private Long userId;
	private Long postId;
	private CreateCommentResponse comemntParent;
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date createAt;
}
