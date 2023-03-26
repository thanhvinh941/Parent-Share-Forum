package com.se1.postservice.domain.payload;

import lombok.Data;

@Data
public class RegisCommentRequest {
	private String content;
	private Long postId;
	private Long comemntParentId;
}
