package com.agentapi.api.core.application;

import org.springframework.stereotype.Component;

import com.agentapi.api.core.domain.UserRepository;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Component
public class DeleteUser {

	private UserRepository repository;

	public Boolean handle(DeleteUserInput deleteUserInput) throws Exception {
		String userId = deleteUserInput.getId();
		return repository.deleteById(userId);
	}
}
