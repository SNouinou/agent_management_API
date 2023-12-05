package com.agentapi.api.gateway.infrastructure.service;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import com.agentapi.api.core.domain.UserRepository;
import com.agentapi.api.core.domain.UserRole;
import com.agentapi.api.gateway.infrastructure.security.JwtTokenProvider;
import com.agentapi.api.gateway.infrastructure.security.SecurityRole;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class SecurityService implements SecureUserService {

    private JwtTokenProvider jwtTokenProviderService;
    private AuthenticationManager authenticationManager;

    @Override
    public LoginResponse login(String login, String password) throws AuthenticationException {
    	
		try {
		Authentication auth = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(login, password));

		LoginResponse loginResponse = new LoginResponse();
		loginResponse.setAccessToken(jwtTokenProviderService.createToken(((User)auth.getPrincipal()).getUsername(),
				(SecurityRole)auth.getAuthorities().toArray()[0]));

		return loginResponse;
		} catch(AuthenticationException e) {
			throw e;
		} catch(RuntimeException e) {
			throw e;
		}
    }
}
