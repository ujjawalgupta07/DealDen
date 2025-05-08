package com.example.commons.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserContext {

    private static final ThreadLocal<UserDetails> userHolder = new ThreadLocal<>();
    private static final Logger LOGGER = LoggerFactory.getLogger(UserContext.class.getName());

    public static void setUser(String username, List<String> roles) {
        LOGGER.info("Setting user {} to roles {}", username, roles);
        userHolder.set(new UserDetails(username, roles));
    }

    public static String getUsername() {
        LOGGER.info("Get username from current thread");
        return userHolder.get() != null ? userHolder.get().getUsername() : null;
    }

    public static boolean hasRole(String role) {
        LOGGER.info("Checking if user {} has role {}", getUsername(), role);
        return userHolder.get() != null && userHolder.get().getRoles().contains(role);
    }

    public static boolean hasAnyRole(String... roles) {
        LOGGER.info("Checking if user {} has any role {}", getUsername(), roles);
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

