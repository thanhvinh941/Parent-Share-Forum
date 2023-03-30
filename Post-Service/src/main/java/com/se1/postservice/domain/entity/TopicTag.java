package com.se1.postservice.domain.entity;

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
public class TopicTag {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(nullable = false)
	private String tagName;
	
	@Column(nullable = false)
	private String color;

	@Column(nullable = false)
	private Byte delFlg;
	
	@Column(nullable = false)
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date createAt;

	@Column(nullable = false)
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date updateAt;

	@Column(nullable = false)
	private String userCreate;
	
	@Column(nullable = false)
	private String userUpdate;
}
