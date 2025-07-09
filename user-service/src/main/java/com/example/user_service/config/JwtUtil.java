package com.example.user_service.config;

import java.util.List;

import io.jsonwebtoken.Jwts;

import org.springframework.stereotype.Component;

@Component
public class JwtUtil {
 
    private final String SECRET = "bA7pR9NqD4sF1gT0mY2cVwXzLhKe8JrB"; // Same as auth-service
 
    public String extractEmail(String token) {
        return Jwts.parser()
                .setSigningKey(SECRET.getBytes())
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }
 
    public String extractRole(String token) {
        return Jwts.parser()
                .setSigningKey(SECRET.getBytes())
                .parseClaimsJws(token)
                .getBody()
                .get("role", String.class);
    }
}
