package com.example.modernecommerce.modernecommerce.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;

/**
 * JwtProvider class provides functionality to generate JWTs based on user authentication
 * and extract the user's email address from a JWT.This is a common utility class used in applications
 * that rely on JWT-based authentication for user identification and authorization.
 * The class is marked as a Spring service, making it available for use throughout the application.
 */
@Service
public class JwtProvider {

    SecretKey key = Keys.hmacShaKeyFor(JwtConstant.SECRET_KEY.getBytes());

    public String generateToken(Authentication auth){
        String jwt= Jwts.builder()
                .setIssuedAt(new Date())
                .setExpiration(new Date(new Date().getTime()+846000000))
                .claim("email",auth.getName())
                .signWith(key).compact();

        return jwt;
    }
    
    public String getEmailFromToken(String jwt){

        jwt = jwt.substring(7);
        Claims claims = Jwts.parserBuilder().setSigningKey(key).build()
                .parseClaimsJws(jwt)
                .getBody();
        String email = String.valueOf(claims.get("email"));

        return email;
    }

}
