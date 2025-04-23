package com.example.userservice.controller;

import com.example.userservice.dto.request.LoginUserRequestDTO;
import com.example.userservice.entity.User;
import com.example.userservice.service.UserService;
import com.example.userservice.util.JWTUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import com.example.userservice.dto.request.RegisterUserRequestDTO;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserService userService;

    @Autowired
    private JWTUtility jwtUtil;

    // Registration endpoint
    @PostMapping("/register")
    public String registerUser(@RequestBody RegisterUserRequestDTO registerUserRequestDTO) {
        return userService.registerUser(registerUserRequestDTO.getUsername(),
                registerUserRequestDTO.getPassword(), registerUserRequestDTO.getEmail()
        ,registerUserRequestDTO.getRoles());
    }

    // Login endpoint
    @PostMapping("/login")
    public String loginUser(@RequestBody LoginUserRequestDTO loginUserRequestDTO) {
        System.out.println("Login request received for username: " + loginUserRequestDTO.getUsername());
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginUserRequestDTO.getUsername(), loginUserRequestDTO.getPassword())
            );
            System.out.println("Authentication successful for username: " + loginUserRequestDTO.getUsername());
            String jwt = jwtUtil.generateToken(authentication);
            System.out.println("Generated JWT: " + jwt);
            return "Bearer " + jwt;
        } catch (Exception e) {
            System.out.println("Authentication failed for username: " + loginUserRequestDTO.getUsername());
            e.printStackTrace();
            return "Authentication failed";
        }
    }
}
