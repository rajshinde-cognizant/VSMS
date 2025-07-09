package com.example.invoice_service.config;

import java.security.Key;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
 
@Component
public class JwtUtil {

    private final String SECRET = "bA7pR9NqD4sF1gT0mY2cVwXzLhKe8JrB";
private final Key key = Keys.hmacShaKeyFor(SECRET.getBytes());

    public String extractEmail(String token) {
        return Jwts.parserBuilder().setSigningKey(key).build()
                .parseClaimsJws(token).getBody().getSubject();
    }

    public String extractRole(String token) {
        return Jwts.parserBuilder().setSigningKey(key).build()
                .parseClaimsJws(token).getBody().get("role", String.class);
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true; // Token is valid
        } catch (JwtException | IllegalArgumentException e) {
            // Token is invalid
            return false;
        }
    }
}

