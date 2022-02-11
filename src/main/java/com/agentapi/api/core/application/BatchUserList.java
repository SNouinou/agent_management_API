package com.agentapi.api.core.application;

import java.util.List;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.agentapi.api.core.domain.BatchFeedback;
import com.agentapi.api.core.domain.User;
import com.agentapi.api.core.domain.UserRepository;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Component
public class BatchUserList {

	private UserRepository repository;
	
	private PasswordEncoder encoder;
	
	public BatchFeedback handle(BatchUserListInput input) {
		List<User> userList = input.getUsers();
		userList.stream().forEach(user->user.setPassword(encoder.encode(user.getPassword())));
		return repository.saveAll(userList);
	}
}
