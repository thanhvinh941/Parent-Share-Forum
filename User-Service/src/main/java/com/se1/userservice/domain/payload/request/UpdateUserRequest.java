package com.se1.userservice.domain.payload.request;

import java.util.List;

import lombok.Data;

@Data
public class UpdateUserRequest {

	private Long id;
    private String name;
    private String imageUrlBase64;
    private String password;
    private ExpertInfo expertInfo;
    
    @Data
    public static class ExpertInfo {
    	private String phoneNumber;
    	private String jobTitle;
    	private String specialist;
    	private String workPlace;
    	private List<Description> description;
    }
    
    @Data
    public static class Description{
    	private String title;
    	private List<String> description;
    }
}
