package com.example.productservice.config;

import com.example.productservice.controller.CategoryController;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class JwtAuthFilter extends OncePerRequestFilter {

    private static final Logger LOGGER = LoggerFactory.getLogger(CategoryController.class.getName());

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        try {
            String username = request.getHeader("X-User-Name");
            String rolesHeader = request.getHeader("X-User-Roles");
            LOGGER.info("Inside Filter --->");
            LOGGER.info("Username: {}", username);
            LOGGER.info("Roles: {}", rolesHeader);
            List<String> roles = rolesHeader != null
                    ? Arrays.asList(rolesHeader.split(","))
                    : Collections.emptyList();

            UserContext.setUser(username, roles);
            filterChain.doFilter(request, response);
        } finally {
            UserContext.clear(); // Important to avoid memory leak
        }
    }
}



