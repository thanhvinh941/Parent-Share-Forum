package com.se1.postservice.domain.entity;

import java.sql.Timestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Data;

@Data
@Entity
public class Post {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer postId;
	
	private Integer memberId;
	
	private String postTitle;
	
	private String postMetaTitle;
	
	private String postSlug;
	
	private String postSummary;
		
	private Byte validFlag;
	
	private Timestamp createdAt;

	@Temporal(TemporalType.TIMESTAMP)
	private Timestamp updateAt;
	
	@Column(length = 60*60*60*60)
	private String context;
}
