package com.se1.apigatewayservice;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;

@Component
public class JwtUtil {

    private static final Logger logger = LoggerFactory.getLogger(JwtUtil.class);
	
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
    	return this.validateToken(token);
	}
    
    public boolean validateToken(String authToken) {
        try {
            Jws<Claims> jws = Jwts.parser().setSigningKey(secret).parseClaimsJws(authToken);
            return true;
        } catch (MalformedJwtException ex) {
            logger.error("Invalid JWT token");
        } catch (ExpiredJwtException ex) {
            logger.error("Expired JWT token");
        } catch (UnsupportedJwtException ex) {
            logger.error("Unsupported JWT token");
        } catch (IllegalArgumentException ex) {
            logger.error("JWT claims string is empty.");
        }
        return false;
    }
}
