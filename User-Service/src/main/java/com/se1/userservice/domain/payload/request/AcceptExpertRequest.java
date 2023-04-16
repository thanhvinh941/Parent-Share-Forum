package com.se1.userservice.domain.payload.request;

import lombok.Data;

@Data
public class AcceptExpertRequest {
	private Long userId;
	private String licenceNo;
	private String licenceName;
	private String userName;
	private String userBirthday;
	private String userIdentifyNo;
	private String licenceAt;
	private String placeOfIssue;
	private String technicalDegree;
	private String scopeActivities;
	private String imageLicence;
}
