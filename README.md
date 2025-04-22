# JWT Authentication with Spring Boot

A comprehensive implementation of JWT-based authentication in a Spring Boot microservice-style architecture.


---

## Overview

This project implements stateless JWT authentication for securing REST APIs, a modern alternative to session-based auth — offering scalability, simplicity, and robustness for distributed systems.


---

## Project Architecture

+------------------------+
| Client Application |
| (Postman / Frontend) |
+-----------+------------+
|
[HTTP Request]
|
+-----------v------------+
| Spring Boot |
| +-------------------+ |
| | JwtAuthFilter |-- Validates JWT token
| +-------------------+ |
| | SecurityConfig |-- Configures secure paths, filters, password encoding
| +-------------------+ |
| | AuthController |-- Handles /login & /register
| +-------------------+ |
| | JwtUtil |-- Generates & validates tokens
| +-------------------+ |
| | UserEntity |-- Implements UserDetails
| +-------------------+ |
| | RoleEntity |-- User role management
| +-------------------+ |
| | UserRepository |-- CRUD for UserEntity
| +-------------------+ |
| | CustomUserDetailsService |-- Loads user data for Spring Security
+------------------------+


---

## Flow Summary

### 1. Registration

User posts credentials to /register.

Password is encrypted via BCryptPasswordEncoder.

User and Role entities are persisted.



### 2. Login

User posts username & password to /login.

AuthenticationManager validates the credentials.

If valid, JwtUtil generates and returns a signed JWT token.



### 3. Authenticated Requests

Client sends the JWT as: Authorization: Bearer <token>.

JwtAuthFilter intercepts requests, validates the token, sets SecurityContext.

The request proceeds as an authenticated user.





---

## Key Classes Explained

### UserEntity

Represents the user data model.

Implements UserDetails to integrate directly with Spring Security.

Holds state: username, password, roles, isAccountNonExpired, isEnabled etc.


### RoleEntity

Simple entity representing roles (ROLE_USER, ROLE_ADMIN).

Supports Many-to-Many mapping with users for flexibility.


### UserRepository / RoleRepository

Spring Data JPA interfaces for CRUD operations on UserEntity and RoleEntity.


### JwtUtil

Generates, parses, and validates JWT tokens.

Extracts Claims — pieces of information like username, roles, issued date, expiration.


### JwtAuthFilter

Custom Spring OncePerRequestFilter.

Extracts the token from headers, validates it, and sets the Spring Security context.


### SecurityConfig

Configures URL-based security.

Registers JwtAuthFilter into the Spring Security filter chain.

Declares password encoder and authentication manager.



---

## Claims in JWT

Claims are data embedded inside the JWT payload.

sub - Subject / Username

roles - User roles

iat - Issued at time

exp - Expiration time




---

### Logout and Token Blacklisting

Stateless logout: Client removes the token.

Server-side invalidation: Use a Redis-backed Token Blacklist.

Forced Logout: Add user's current token to blacklist when they log out manually or are deactivated.



---

### Refresh Tokens

Issue both Access Token (short-lived) and Refresh Token (long-lived).

When Access Token expires, client uses Refresh Token to request a new Access Token.

Prevents frequent logins while maintaining security.



---

### SecurityConfig Behind The Scenes

Secures URL patterns.

Enables Spring Security's authentication manager.

Registers your password encoder (BCrypt).

Integrates your JwtAuthFilter into Spring's internal filter flow.



---

###  Explanations

- Why JWT over sessions? 
-> Stateless, scalable, suitable for microservices and distributed systems.

- Why implement UserDetails? Allows Spring Security to treat your UserEntity as an authentication principal.

- What are JWT claims? Self-contained user and session data embedded in the token.

- How to handle forced logout in JWT? Using token blacklists stored in Redis or databases.

- How to add refresh tokens? Use a secure /refresh-token endpoint and pair the short-lived Access Token with a long-lived Refresh Token.



---

### Flow Diagram

>




---

### Conclusion

This JWT implementation is scalable, secure and production-ready for modern Spring Boot applications. You can extend it with:

Refresh tokens

Redis blacklisting

Multi-role RBAC systems

OAuth2 or SSO support


