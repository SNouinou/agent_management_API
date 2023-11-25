package com.agentapi.api.core.application;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import com.agentapi.api.core.domain.User;
import com.agentapi.api.core.domain.UserRepository;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Component
public class FetchUserList {

	private UserRepository repository;
		
	public Page<User> handle(FetchUserListInput input) {
		int page = input.getPage();
		int size = input.getSize();
		String username = input.getUsername();
		return repository.getPage(page, size, username);
	}
}
