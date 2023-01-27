package com.se1.threadservice.domain.entity;

import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Data;

@Data
@Entity
public class Thread {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer threadId;
	
	private String threadTitle;
	
	private String threadMetaTitle;
	
	private String threadSlug;
	
	private String threadSummary;
	
	private Integer prefixId;
	
	private String prefixName;
	
	private Integer memberId;
	
	private Integer forumId;
	
	private Date createAt;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date updateAt;
}
