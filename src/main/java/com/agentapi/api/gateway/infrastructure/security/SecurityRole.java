package com.agentapi.api.gateway.infrastructure.security;

import org.springframework.security.core.GrantedAuthority;

import com.agentapi.api.core.domain.UserRole;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class SecurityRole implements GrantedAuthority {

	private UserRole userRole;
	
	@Override
	public String getAuthority() {
		return userRole.name();
	}

	
}
