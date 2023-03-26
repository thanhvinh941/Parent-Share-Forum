package com.se1.systemservice.domain.payload;

import lombok.Data;

@Data
public class CommentRequest {
	private Long userId;
	private Long postId;
	private String content;
}
