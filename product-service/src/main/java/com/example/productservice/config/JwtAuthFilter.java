package com.example.productservice.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        try {
            String username = request.getHeader("X-User-Name");
            String rolesHeader = request.getHeader("X-User-Roles"); // e.g. "ADMIN,USER"
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



