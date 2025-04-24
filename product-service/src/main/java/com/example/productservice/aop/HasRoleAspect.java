package com.example.productservice.aop;

import com.example.productservice.aop.annotations.HasAnyRole;
import com.example.productservice.config.UserContext;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.nio.file.AccessDeniedException;
import java.util.Arrays;

@Aspect
@Component
public class HasRoleAspect {

    @Autowired
    private UserContext userContext;

    @Around("@annotation(hasRole)")
    public Object validateRole(ProceedingJoinPoint joinPoint, HasAnyRole hasRole) throws Throwable {
        String requiredRole = Arrays.toString(hasRole.value());
        if (userContext.hasRole(requiredRole)) {
            return joinPoint.proceed();
        }
        throw new AccessDeniedException("User does not have role: " + requiredRole);
    }
}