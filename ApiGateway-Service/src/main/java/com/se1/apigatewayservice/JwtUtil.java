package com.se1.apigatewayservice;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

@Component
public class JwtUtil {

	@Autowired
	AuthServiceRestTemplateClient authServiceRestTemplateClient;
	
	@Value("${jwt.secret}")
	private String secret;

    public Claims getAllClaimsFromToken(String token) {
    	return Jwts.parserBuilder().setSigningKey(secret).build().parseClaimsJws(token).getBody();
	}

    private boolean isTokenExpired(String token) {
    	return this.getAllClaimsFromToken(token).getExpiration().before(new Date());
	}

    public boolean isInvalid(String token) {
    	return authServiceRestTemplateClient.isTokenValid(token);
	}
}
