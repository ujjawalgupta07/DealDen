package com.example.productservice.aop;

import com.example.productservice.aop.annotations.HasAnyRole;
import com.example.productservice.config.UserContext;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.nio.file.AccessDeniedException;
import java.util.Arrays;
import java.util.Set;

@Aspect
@Component
public class RBACAspect {

    @Autowired
    private UserContext userContext;

    @Before("@annotation(com.example.productservice.aop.annotations.IsAdmin)")
    public void checkAdmin() throws AccessDeniedException {
        if (!userContext.hasRole("ADMIN")) {
            throw new AccessDeniedException("Access denied: ADMIN role required.");
        }
    }

    @Before("@annotation(com.example.productservice.aop.annotations.IsVendor)")
    public void checkVendor() throws AccessDeniedException {
        if (!userContext.hasRole("VENDOR")) {
            throw new AccessDeniedException("Access denied: VENDOR role required.");
        }
    }

    @Before("@annotation(com.example.productservice.aop.annotations.IsUser)")
    public void checkUser() throws AccessDeniedException {
        if (!userContext.hasRole("USER")) {
            throw new AccessDeniedException("Access denied: USER role required.");
        }
    }

    @Before("@annotation(hasAnyRole)")
    public void checkHasAnyRole(HasAnyRole hasAnyRole) throws AccessDeniedException {
        if (!userContext.hasAnyRole(hasAnyRole.value())) {
            throw new AccessDeniedException("Access denied: One of roles " + Arrays.toString(hasAnyRole.value()) + " required.");
        }
    }
}
