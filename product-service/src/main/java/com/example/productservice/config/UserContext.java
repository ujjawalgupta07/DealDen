package com.example.productservice.config;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@Scope(value = WebApplicationContext.SCOPE_REQUEST,
        proxyMode = ScopedProxyMode.TARGET_CLASS)
public class UserContext {

    private final HttpServletRequest request;

    @Autowired
    public UserContext(HttpServletRequest request) {
        this.request = request;
    }

    public String getUsername() {
        return request.getHeader("X-User-Name");
    }

    public Set<String> getRoles() {
        String rolesHeader = request.getHeader("X-User-Roles");
        if (rolesHeader == null) return Set.of();
        return Arrays.stream(rolesHeader.split(","))
                .map(String::trim)
                .collect(Collectors.toSet());
    }

    public boolean hasRole(String role) {
        return getRoles().contains(role);
    }

    public boolean hasAnyRole(String... roles) {
        Set<String> userRoles = getRoles();
        return Arrays.stream(roles).anyMatch(userRoles::contains);
    }
}
