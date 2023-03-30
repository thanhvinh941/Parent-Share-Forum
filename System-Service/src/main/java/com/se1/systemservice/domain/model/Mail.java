package com.se1.systemservice.domain.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "mail")
public class Mail {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
	
	@Column(nullable = false)
	private String mailTemplate;
	
	@Lob
	@Column(nullable = false)
	private String body;
	
	@Column(nullable = false)
	private String Subject;
}
