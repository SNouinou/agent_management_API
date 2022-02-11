package com.agentapi.api.core.domain;

import java.util.List;

public interface UserRepository {
	BatchFeedback saveAll(List<User> userList);
}
