package com.agentapi.api.core.application;

import java.util.List;

import org.springframework.stereotype.Component;

import com.agentapi.api.core.domain.User;
import com.agentapi.api.core.domain.UserFaker;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Component
public class GenerateUsers {
	
	private UserFaker userFaker;
	
	public List<User> handle(GenerateUsersInput input) {
		return userFaker.fakeUsers(input.getCount());
	}
}
