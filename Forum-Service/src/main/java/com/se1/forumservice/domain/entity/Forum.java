package com.se1.forumservice.domain.entity;

import java.util.Date;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
@JsonSerialize
public class Forum {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer forumId;

	private String forumName;
	
	private String forumSlug;
	
	private Integer forumParent;
	
	private String forumParentName;
	
	private Byte validFlg;
	
	private Date createAt;
	
	private Date updateAt;
}

