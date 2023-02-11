package com.se1.postservice.domain.entity;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import lombok.Data;

@Data
@Entity
public class Post {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(nullable = false)
	private Integer memberId;
	
	@Column(nullable = false)
	private String memberName;
	
	@Column(nullable = false)
	private String title;
	
	private String metaTitle;
	
	@Column(nullable = false)
	private String slug;

	@Column(nullable = false)
	private String summary;
		
	@Column(nullable = false)
	private Byte validFlag;
	
	@Lob
	@Column(nullable = false)
	private String context;

	@Column(nullable = false)
	private Long likeCount;
	
	private String hashTag;
	
	private List<Integer> tagIds;
	
	@Column(nullable = false)
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date createAt;

	@Column(nullable = false)
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date updateAt;
	
}
