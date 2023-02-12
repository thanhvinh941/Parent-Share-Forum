package com.se1.userservice.model;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import lombok.Data;

@Data
@Entity
@Table(name = "licences", uniqueConstraints = {
        @UniqueConstraint(columnNames = "userIdentifyNo"),
        @UniqueConstraint(columnNames = "licenceNo")
})
public class Licence {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	
	@Column(nullable = false)
	private String licenceNo;
	
	@Column(nullable = false)
	private String licenceName;
	
	@Column(nullable = false)
	private String userName;
	
	@Column(nullable = false)
	private String userBirthday;
	
	@Column(nullable = false)
	private String userIdentifyNo;

	@Column(nullable = false)
	private String licenceAt;

	@Column(nullable = false)
	private String placeOfIssue;
	
	@Column(nullable = false)
	private String technicalDegree;

	@Column(nullable = false)
	private String scopeActivities;
}
