package com.se1.commentservice.domain.entity;

import java.sql.Timestamp;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class Comment {
 
	@Id
	private Integer commentId;
	
	private String commentContent;
	
	private Integer memberId;
	
	private Integer postId;
	
	private Timestamp commentAt;
	
	private Integer commentParentId;
	
	private Byte validFlg;
	
}
