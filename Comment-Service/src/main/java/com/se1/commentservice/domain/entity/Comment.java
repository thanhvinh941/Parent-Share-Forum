package com.se1.commentservice.domain.entity;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;

@Data
@Entity
public class Comment {
 
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer commentId;
	
	@Column(nullable = false)
	private String commentContent;
	
	@Column(nullable = false)
	private Integer memberId;
	
	@Column(nullable = false)
	private Integer postId;
	
	@Column(nullable = false)
	private Timestamp commentAt;
	
	@Column(nullable = false)
	private Integer commentParentId;
	
	@Column(nullable = false)
	private Byte validFlg;
	
}
