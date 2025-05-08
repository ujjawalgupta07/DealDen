package com.example.commons.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import java.util.Arrays;
import java.util.List;

@Component
public class PublicEndpointConfig {

    @Value("${rbac.public-paths}")
    private final List<String> publicPaths;
    private final AntPathMatcher pathMatcher = new AntPathMatcher();
    private static final Logger LOGGER = LoggerFactory.getLogger(PublicEndpointConfig.class);

    public PublicEndpointConfig(@Value("${rbac.public-paths}") String publicPathsStr) {
        this.publicPaths = Arrays.stream(publicPathsStr.split(","))
                .map(String::trim)
                .toList();
    }

    public boolean isPublic(String requestUri) {
        for (String path : publicPaths) {
            if (pathMatcher.match(path, requestUri)) {
                LOGGER.info("Matched public path pattern: {}", path);
                return true;
            }
        }
        return false;
    }
}
