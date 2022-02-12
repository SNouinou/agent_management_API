package com.agentapi.api.gateway.infrastructure.security;


import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
public class JwtTokenFilter extends OncePerRequestFilter {

    private JwtTokenProvider jwtTokenProviderService;

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        String token = jwtTokenProviderService.parseToken(httpServletRequest);
        try {
            if (token != null && jwtTokenProviderService.validateToken(token)) {
                Authentication auth = jwtTokenProviderService.validateUserAndGetAuthentication(token);
                SecurityContextHolder.getContext().setAuthentication(auth);
            }
        } catch (Exception ex) {
            SecurityContextHolder.clearContext();
            httpServletResponse.sendError(500, ex.getMessage());
            ex.printStackTrace();
            return;
        }

        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }

}