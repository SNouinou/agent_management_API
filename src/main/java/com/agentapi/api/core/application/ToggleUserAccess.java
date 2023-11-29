package com.agentapi.api.core.application;

import org.springframework.stereotype.Component;

import com.agentapi.api.core.domain.UserRepository;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Component
public class ToggleUserAccess {

	private UserRepository repository;

	public Boolean handle(ToggleUserAccessInput toggleUserAccessInput) throws Exception {
		String userId = toggleUserAccessInput.getId();
		Boolean value = toggleUserAccessInput.getValue();
		return repository.toggleUserAccessById(userId,value);
	}
}
