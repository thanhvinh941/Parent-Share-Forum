package com.se1.commentservice.domain.dto;

import java.sql.Timestamp;
import java.util.List;

public class CommentResponse {
	private Integer commentId;
	private String commentContent;
	private Integer memberId;
	private Integer postId;
	private Timestamp commentAt;
	private List<CommentResponse> commentChild;
}
