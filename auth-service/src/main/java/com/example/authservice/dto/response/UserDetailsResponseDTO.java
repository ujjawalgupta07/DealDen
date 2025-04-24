package com.example.authservice.dto.response;

import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class UserDetailsResponseDTO {

    private String username;
    private String email;
    private Collection<? extends GrantedAuthority> roles;

    public UserDetailsResponseDTO(String username, String email, Collection<? extends GrantedAuthority> roles) {
        this.username = username;
        this.email = email;
        this.roles = roles;
    }

    // Getters
    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public Collection<? extends GrantedAuthority> getRoles() {
        return roles;
    }
}