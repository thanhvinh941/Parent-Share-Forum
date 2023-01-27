package com.se1.postservice.domain.entity;

import java.sql.Timestamp;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class Like {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer likeId;
	
	private Integer postId;
	
	private Integer userId;
	
	private Byte validFlg;
	
	private Timestamp likedAt;
	
}
