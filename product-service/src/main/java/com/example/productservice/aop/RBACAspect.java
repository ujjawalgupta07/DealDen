package com.example.productservice.aop;

import com.example.productservice.aop.annotations.*;
import com.example.productservice.config.PublicEndpointConfig;
import com.example.productservice.config.UserContext;
import com.example.productservice.exception.AccessDeniedException;
import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Aspect
@Component
public class RBACAspect {

    private final UserContext userContext;
    private final PublicEndpointConfig publicEndpointConfig;

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
        return request.getRequestURI();
    }

    // This method checks the role dynamically based on the annotation
    private void validateRole(String role) {
        if (!userContext.hasRole(role)) {
            throw new AccessDeniedException("Access denied: Required role = " + role);
        }
    }

    // This method checks if the user has one of the required roles
    private void validateAnyRole(String[] roles) {
        if (!userContext.hasAnyRole(roles)) {
            throw new AccessDeniedException("Access denied: Required one of roles = " + String.join(", ", roles));
        }
    }

    @Before("@annotation(annotation)")
    public void checkIsAdmin(JoinPoint joinPoint, IsAdmin annotation) {
        String uri = getRequestUri();
        if (shouldSkipAuth(uri)) return;
        validateRole("ROLE_ADMIN");
    }

    @Before("@annotation(annotation)")
    public void checkIsVendor(JoinPoint joinPoint, IsVendor annotation) {
        String uri = getRequestUri();
        if (shouldSkipAuth(uri)) return;
        validateRole("ROLE_VENDOR");
    }

    @Before("@annotation(annotation)")
    public void checkIsUser(JoinPoint joinPoint, IsUser annotation) {
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
 