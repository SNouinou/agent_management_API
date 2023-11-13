package com.agentapi.api.core.application;

import java.util.List;

import org.springframework.stereotype.Component;

import com.agentapi.api.core.domain.User;
import com.agentapi.api.core.domain.UserRepository;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Component
public class FetchUserList {

	private UserRepository repository;
		
	public List<User> handle(FetchUserListInput input) {
		Integer page = input.getPage();
		return repository.findAll();
	}
}
