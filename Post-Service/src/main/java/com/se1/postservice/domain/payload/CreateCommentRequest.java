package com.se1.postservice.domain.payload;

import lombok.Data;

@Data
public class CreateCommentRequest {
	private String content;
	private Long postId;
	private Long comemntParentId;
}
