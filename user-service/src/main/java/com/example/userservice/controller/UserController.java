package com.example.userservice.controller;

import com.example.userservice.dto.response.UserDetailsResponseDTO;
import com.example.userservice.entity.User;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    @GetMapping("/me")
    public ResponseEntity<UserDetailsResponseDTO> getCurrentUserDetails(Authentication authentication) {
        User user = (User) authentication.getPrincipal();

        UserDetailsResponseDTO dto = new UserDetailsResponseDTO(
                user.getUsername(),
                user.getEmail(),
                user.getAuthorities()
        );

        return ResponseEntity.ok(dto);
    }
}
