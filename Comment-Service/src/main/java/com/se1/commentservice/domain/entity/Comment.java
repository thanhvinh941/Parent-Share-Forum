package com.se1.commentservice.domain.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
@Entity
public class Comment {
 
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false)
	private String content;
	
	@Column(nullable = false)
	private Long userId;
	
	@Column(nullable = false)
	private Long postId;
	
	@Column(nullable = false)
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date createAt;

	private Long comemntParentId;
	
	@Column(nullable = false)
	private boolean delFlg;
	
}
