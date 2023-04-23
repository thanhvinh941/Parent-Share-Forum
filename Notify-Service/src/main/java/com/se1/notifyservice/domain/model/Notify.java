package com.se1.notifyservice.domain.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
@Entity
@Table(name = "notify")
public class Notify {

	 @Id
	 @GeneratedValue(strategy = GenerationType.IDENTITY)
	 private Long id;

	 @Column(nullable = false)
	 private Long userId;
	 
	 @Column(nullable = false)
	 private String param;
	 
	 @Column(nullable = false)
	 private String type;
	 
	 @Lob
	 @Column(nullable = false)
	 private String value;
	 
	 @Column(nullable = false)
	 private int status;
	 
	 @Column(nullable = false)
	 @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	 private Date createAt;
	 
	 @Column(nullable = false)
	 private Byte delFlg;
}
