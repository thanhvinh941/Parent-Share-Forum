package com.se1.userservice.domain.model;

import com.se1.userservice.domain.common.SCMConstant;

public enum AuthorizationItem {

	REGIST_TOPIC(1, SCMConstant.TOPIC_TAG_CONTROLLER),
	UPDATE_TOPIC(2, SCMConstant.TOPIC_TAG_CONTROLLER),
	UDELETE_TOPIC(3, SCMConstant.TOPIC_TAG_CONTROLLER),
	GET_TOPIC(4, SCMConstant.TOPIC_TAG_CONTROLLER),
	
	REGIST_USER(5, SCMConstant.USER_CONTROLLER),
	UPDATE_USER(6, SCMConstant.USER_CONTROLLER),
	UDELETE_USER(7, SCMConstant.USER_CONTROLLER),
	GET_USER(8, SCMConstant.USER_CONTROLLER),
	
	REGIST_POST(9, SCMConstant.GROUP_ROLE_CONTROLLER),
	UPDATE_POST(10, SCMConstant.GROUP_ROLE_CONTROLLER),
	UDELETE_POST(11, SCMConstant.GROUP_ROLE_CONTROLLER),
	GET_POST(12, SCMConstant.GROUP_ROLE_CONTROLLER),
	
	REGIST_GROUP_ROLE(13, SCMConstant.POST_CONTROLLER),
	UPDATE_GROUP_ROLE(14, SCMConstant.POST_CONTROLLER),
	UDELETE_GROUP_ROLE(15, SCMConstant.POST_CONTROLLER),
	GET_GROUP_ROLE(16, SCMConstant.POST_CONTROLLER),
	
	GET_LOG_USER(17, SCMConstant.LOG_USER_CONTROLLER),
	
	CSV_TOPIC(18,SCMConstant.TOPIC_TAG_CONTROLLER),
	CSV_USER(19,SCMConstant.TOPIC_TAG_CONTROLLER),
	CSV_POST(20,SCMConstant.TOPIC_TAG_CONTROLLER),
	CSV_GROUP_ROLE(21,SCMConstant.TOPIC_TAG_CONTROLLER),
	CSV_LOG_USER(22,SCMConstant.TOPIC_TAG_CONTROLLER);
	
	private Integer id;
	private String controller;
	
	AuthorizationItem(Integer id, String controller){
		this.id = id;
		this.controller = controller;
	}

	public Integer getId() {
		return id;
	}

	public String getController() {
		return controller;
	}
	
}
