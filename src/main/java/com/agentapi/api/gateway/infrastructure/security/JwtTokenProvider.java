package com.agentapi.api.gateway.infrastructure.security;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.core.Authentication;

import com.agentapi.api.core.domain.UserRole;

public interface JwtTokenProvider {
    String createToken(String username, SecurityRole role);
    Authentication validateUserAndGetAuthentication(String token);
    String getUsername(String token);
    String parseToken(HttpServletRequest req);
    boolean validateToken(String token);

}
