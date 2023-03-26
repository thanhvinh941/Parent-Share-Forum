package com.se1.userservice.domain.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
@Entity
@Table(name = "contacts", uniqueConstraints = {
        @UniqueConstraint(columnNames = { "userReciverId" , "userSenderId"})
})
public class Contact {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

    @Column(nullable = false)
	private Long userReciverId;

    @Column(nullable = false)
	private Long userSenderId;
	
    @Column(nullable = false)
	private int status; // 0 : Not Friend, 1 : Request Friend, 2 : Friend
	
    @Column(nullable = false)
    private String topicId;
    
	@Column(nullable = false)
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date createAt;
}
