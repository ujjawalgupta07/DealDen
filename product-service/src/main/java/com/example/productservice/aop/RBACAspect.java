package com.example.productservice.aop;

import com.example.productservice.aop.annotations.*;
import com.example.productservice.config.PublicEndpointConfig;
import com.example.productservice.config.UserContext;
import com.example.productservice.controller.CategoryController;
import com.example.productservice.exception.AccessDeniedException;
import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Aspect
@Component
public class RBACAspect {

    private final UserContext userContext;
    private final PublicEndpointConfig publicEndpointConfig;
    private static final Logger LOGGER = LoggerFactory.getLogger(CategoryController.class.getName());


    public RBACAspect(UserContext userContext, PublicEndpointConfig publicEndpointConfig) {
        this.userContext = userContext;
        this.publicEndpointConfig = publicEndpointConfig;
    }

    private boolean shouldSkipAuth(String requestUri) {
        return publicEndpointConfig.isPublic(requestUri);
    }

    private String getRequestUri() {
        ServletRequestAttributes attrs = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        HttpServletRequest request = attrs.getRequest();
        LOGGER.info("Request URI :: {}", request.getRequestURI());
        return request.getRequestURI();
    }

    // This method checks the role dynamically based on the annotation
    private void validateRole(String role) {
        LOGGER.info("Validating role :: {}", role);
        if (!userContext.hasRole(role)) {
            throw new AccessDeniedException("Access denied: Required role = " + role);
        }
    }

    // This method checks if the user has one of the required roles
    private void validateAnyRole(String[] roles) {
        LOGGER.info("Validating any role :: {}", String.join(", ", roles));
        if (!userContext.hasAnyRole(roles)) {
            throw new AccessDeniedException("Access denied: Required one of roles = " + String.join(", ", roles));
        }
    }

    @Before("@annotation(com.example.productservice.aop.annotations.IsAdmin)")
    public void checkIsAdmin(JoinPoint joinPoint) {
        String uri = getRequestUri();
        if (shouldSkipAuth(uri)) return;
        validateRole("ROLE_ADMIN");
    }

    @Before("@annotation(com.example.productservice.aop.annotations.IsVendor)")
    public void checkIsVendor(JoinPoint joinPoint) {
        String uri = getRequestUri();
        if (shouldSkipAuth(uri)) return;
        validateRole("ROLE_VENDOR");
    }

    @Before("@annotation(com.example.productservice.aop.annotations.IsUser)")
    public void checkIsUser(JoinPoint joinPoint) {
        String uri = getRequestUri();
        if (shouldSkipAuth(uri)) return;
        validateRole("ROLE_USER");
    }

    @Before("@annotation(annotation)")
    public void checkHasRole(JoinPoint joinPoint, HasRole annotation) {
        String uri = getRequestUri();
        if (shouldSkipAuth(uri)) return;
        validateRole(annotation.value());
    }

    @Before("@annotation(annotation)")
    public void checkHasAnyRole(JoinPoint joinPoint, HasAnyRole annotation) {
        String uri = getRequestUri();
        if (shouldSkipAuth(uri)) return;
        validateAnyRole(annotation.value());
    }
}
 