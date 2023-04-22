package com.se1.userservice.domain.model;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
@Entity
@Table
public class GroupRole {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	
	@Column(nullable = false)
    private String name;

	@ElementCollection
    private List<String> roles;
    
    @Column(nullable = false)
    private Byte delFlg;
    
    @Column(nullable = false)
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date createAt;

    @Column(nullable = false)
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date updateAt;
}
