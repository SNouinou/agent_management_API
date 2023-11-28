package com.agentapi.api.core.domain;

import java.util.List;

import org.springframework.data.domain.Page;

public interface UserRepository {
	BatchFeedback saveAll(List<User> userList);
	User findUserByUsernameOrEmail(String login);
	List<User> findAll();
	Page<User> getPage(int page, int size, String username);
	Boolean deleteById(String userId) throws Exception;
}
