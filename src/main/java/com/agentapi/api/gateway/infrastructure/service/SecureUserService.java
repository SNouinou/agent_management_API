package com.agentapi.api.gateway.infrastructure.service;

public interface SecureUserService {
	
    LoginResponse login(String username, String password);

}