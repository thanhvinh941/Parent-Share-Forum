package com.se1.userservice.domain.payload.request;

import java.util.List;

import lombok.Data;

@Data
public class CreateUserRequest {

    private String name;
    private String email;
    private String imageUrlBase64;
    private String password;
    private String role;
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
