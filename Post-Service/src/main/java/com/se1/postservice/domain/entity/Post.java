package com.se1.postservice.domain.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
@Entity
public class Post {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(nullable = false)
	private Integer userId;
	
	@Column(nullable = false)
	private String userName;
	
	@Column(nullable = false)
	private String title;
	
	private String metaTitle;
	
	@Column(nullable = false)
	private String slug;

	@Column(nullable = false)
	private String summary;
		
	@Column(nullable = false)
	private Boolean validFlag;
	
	@Lob
	@Column(nullable = false)
	private String context;

	@Column(nullable = false)
	private long likeCount;
	
	@ElementCollection
	private List<String> imageList;
	
	private String hashTag;
	
	@Column(nullable = false)
	private Integer topicTagId;
	
	@Column(nullable = false)
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date createAt;

	@Column(nullable = false)
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date updateAt;
	
}
