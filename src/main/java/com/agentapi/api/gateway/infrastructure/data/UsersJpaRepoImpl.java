package com.agentapi.api.gateway.infrastructure.data;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

import com.agentapi.api.core.domain.BatchFeedback;
import com.agentapi.api.core.domain.User;
import com.agentapi.api.core.domain.UserRepository;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Repository
//renamed due to circular dep issue
public class UsersJpaRepoImpl implements UserRepository {

	private UsersJpaRepository jpaRepository;

	@Override
	public BatchFeedback saveAll(List<User> userList) {

		List<UserEntity> savedEntries = 
				jpaRepository.saveAll(
						userList.stream()
						.map(user -> UserEntity.toEntity(user))
						.collect(Collectors.toList())
						);
		
		return new BatchFeedback(
				userList.size(),
				savedEntries.size(),
				userList.size()-savedEntries.size()
				);
	}

	@Override
	public User findUserByUsernameOrEmail(String login) {
		
		UserEntity user = jpaRepository.findByUsernameOrEmail(login);
		
		if(user == null) {
			return null;
		} else {
			return user.toDomain();
		}
	}
	
	

}
