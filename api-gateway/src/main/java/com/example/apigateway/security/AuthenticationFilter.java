package com.example.apigateway.security;

import com.example.apigateway.config.AuthConfig;
import com.example.apigateway.util.JwtUtil;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.List;

@Component
public class AuthenticationFilter implements GlobalFilter, Ordered {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private AuthConfig authConfig;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        ServerHttpRequest request = exchange.getRequest();

        //Allow registration and login endpoints without token
        if(isPublicEndpoint(request)){
            return chain.filter(exchange);
        }

        // Check if Authorization header is present
        if (!request.getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
           throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Missing Authorization Header");
        }

        String authHeader = request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
        System.out.println("Header: " + authHeader);
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid Authorization Header");
        }

        String token = authHeader.substring(7);
        System.out.println("Token: " + token);
        if (!jwtUtil.isTokenValid(token)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid JWT Token");
        }

//        Claims claims = jwtUtil.extractAllClaims(token);
        String userName = jwtUtil.getUsername(token);
        List<String> roles = jwtUtil.extractRoles(token);

        // Inject claims into headers
        ServerHttpRequest modifiedRequest = exchange.getRequest()
                .mutate()
                .header("X-User-Name", userName)
                .header("X-User-Roles", String.join(",", roles))
                .build();

        return chain.filter(exchange.mutate().request(modifiedRequest).build());
    }

    private boolean isPublicEndpoint(ServerHttpRequest request) {
        String path = request.getURI().getPath();
        return authConfig.getPublicPaths()
                .stream()
                .anyMatch(path::startsWith);
    }

    @Override
    public int getOrder() {
        return -1;
    }

}