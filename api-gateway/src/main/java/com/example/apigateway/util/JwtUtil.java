package com.example.apigateway.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class JwtUtil {

    @Value("${jwt.secret.key}")
    private String SECRET_KEY;

    private Key key;

    @PostConstruct
    public void init() {
        this.key = Keys.hmacShaKeyFor(Base64.getEncoder().encode(SECRET_KEY.getBytes()));
    }

    public Claims extractAllClaims(String token) {
        System.out.println("Extracting claims from token: " + token);
        return Jwts.parser()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public String getUsername(String token) {
        return extractAllClaims(token).getSubject();
    }

    public String getUserRole(String token) {
        return extractAllClaims(token).get("role", String.class);
    }

    public List<String> extractRoles(String token) {
        Claims claims = extractAllClaims(token);
        Object rolesObj = claims.get("roles");

        if (rolesObj instanceof String) {
            return Arrays.stream(((String) rolesObj).split(","))
                    .map(String::trim)
                    .collect(Collectors.toList());
        }

        if (rolesObj instanceof Collection) {
            return ((Collection<?>) rolesObj).stream()
                    .map(Object::toString)
                    .collect(Collectors.toList());
        }

        return Collections.emptyList();
    }

    public boolean isTokenValid(String token) {
        System.out.println("Validating Token: " + token);
        try {
            extractAllClaims(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}

