package com.example.productservice.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;
import java.util.List;

@Component
@Scope(value = WebApplicationContext.SCOPE_REQUEST,
        proxyMode = ScopedProxyMode.TARGET_CLASS)
public class UserContext {

    private static final ThreadLocal<UserDetails> userHolder = new ThreadLocal<>();

    public static void setUser(String username, List<String> roles) {
        userHolder.set(new UserDetails(username, roles));
    }

    public static String getUsername() {
        return userHolder.get() != null ? userHolder.get().getUsername() : null;
    }

    public static boolean hasRole(String role) {
        return userHolder.get() != null && userHolder.get().getRoles().contains(role);
    }

    public static boolean hasAnyRole(String... roles) {
        if (userHolder.get() == null) return false;
        for (String role : roles) {
            if (userHolder.get().getRoles().contains(role)) return true;
        }
        return false;
    }

    public static void clear() {
        userHolder.remove();
    }

    private static class UserDetails {
        private String username;
        private List<String> roles;

        public UserDetails() {
        }

        public UserDetails(String username, List<String> roles) {
            this.username = username;
            this.roles = roles;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public List<String> getRoles() {
            return roles;
        }

        public void setRoles(List<String> roles) {
            this.roles = roles;
        }
    }
}

