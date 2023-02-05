package com.se1.authservice.security;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.se1.authservice.exception.ResourceNotFoundException;
import com.se1.authservice.model.User;
import com.se1.authservice.payload.ApiResponseEntity;
import com.se1.authservice.util.UserServiceRestTemplateClient;


@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    UserServiceRestTemplateClient client;
    
    @Override
    public UserDetails loadUserByUsername(String email)
            throws UsernameNotFoundException {
    	ApiResponseEntity<User> apiResponseEntityResult = (ApiResponseEntity<User>) client.findByEmail(email);
        
    	User user = apiResponseEntityResult.getData();
    	
    	if(user == null) {
    		new UsernameNotFoundException("User not found with email : " + email);
    	}

        return UserPrincipal.create(user);
    }

    public UserDetails loadUserById(Long id) {
    	ApiResponseEntity<User> apiResponseEntityResult = (ApiResponseEntity<User>) client.findById(id);
        
    	User user = apiResponseEntityResult.getData();
    	
    	if(user == null) {
    		new ResourceNotFoundException("User", "id", id);
    	}

        return UserPrincipal.create(user);
    }
}