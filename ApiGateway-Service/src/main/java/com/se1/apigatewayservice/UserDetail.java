package com.se1.apigatewayservice;

public class UserDetail {
	
	private Long id;
	private String username;
	private String imageUrl;
	
	public UserDetail(Long id, String username, String imageUrl) {
		super();
		this.id = id;
		this.username = username;
		this.imageUrl = imageUrl;
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getImageUrl() {
		return imageUrl;
	}
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

}
