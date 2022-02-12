package com.agentapi.api.gateway.infrastructure.service;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import com.agentapi.api.core.domain.User;
import com.agentapi.api.core.domain.UserRepository;
import com.agentapi.api.gateway.infrastructure.security.JwtTokenProvider;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Service
@AllArgsConstructor
public class SecurityService implements SecureUserService {

    private UserRepository userRepository;

    private JwtTokenProvider jwtTokenProviderService;
    private AuthenticationManager authenticationManager;

    @Override
    public LoginResponse login(String login, String password) {
    	
		User user = userRepository.findUserByUsernameOrEmail(login);
    	
		try {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getEmail(), password));

		LoginResponse loginResponse = new LoginResponse();
		loginResponse.setAccessToken(jwtTokenProviderService.createToken(user.getEmail(), user.getRole()));

		return loginResponse;
		} catch(Exception e) {
			e.printStackTrace();
			return null;
		}
    }
}
