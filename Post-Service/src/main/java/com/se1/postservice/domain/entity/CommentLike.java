package com.se1.postservice.domain.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
@Entity
@Table(name="comment_like")
public class CommentLike {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(nullable = false)
	private Integer commentId;
	
	@Column(nullable = false)
	private Integer userId;
		
	@Column(nullable = false)
	private Byte status; // 1: like, 2: dislike
	
	@Column(nullable = false)
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date createAt;
	
}
