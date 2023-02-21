package com.se1.commentservice.domain.dto;

import java.sql.Timestamp;

import lombok.Data;

@Data
public class CommentDto {

	private Integer commentId;
	private String commentContent;
	private Integer memberId;
	private Integer postId;
	private Timestamp commentAt;
	private Integer commentParentId;
	private Byte validFlg;
}
