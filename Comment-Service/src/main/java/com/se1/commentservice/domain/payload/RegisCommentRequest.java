package com.se1.commentservice.domain.payload;

import lombok.Data;

@Data
public class RegisCommentRequest {
	private String content;
	private Long postId;
	private Long comemntParentId;
}
