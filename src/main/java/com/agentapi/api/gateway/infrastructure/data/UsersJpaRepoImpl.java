package com.agentapi.api.gateway.infrastructure.data;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.persistence.EntityManager;

import org.hibernate.Filter;
import org.hibernate.Session;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.agentapi.api.core.domain.BatchFeedback;
import com.agentapi.api.core.domain.User;
import com.agentapi.api.core.domain.UserRepository;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Repository
//renamed due to circular dep issue
public class UsersJpaRepoImpl implements UserRepository {

	private UsersJpaRepository jpaRepository;

	private EntityManager entityManager;

	@Override
	public BatchFeedback saveAll(List<User> userList) {

		List<UserEntity> entities = userList.stream().map(user -> UserEntity.toEntity(user))
				.collect(Collectors.toList());

		List<UserEntity> savedEntries = jpaRepository.saveAll(entities);

		return new BatchFeedback(userList.size(), savedEntries.size(), userList.size() - savedEntries.size());
	}

	@Override
	public User findUserByUsernameOrEmail(String login) {

		User user = null;

		Session session = entityManager.unwrap(Session.class);
		Filter filter = session.enableFilter("deletedUserFilter");
		filter.setParameter("isDeleted", Boolean.FALSE);

		try {
			UserEntity userEntity = jpaRepository.findByUsernameOrEmail(login);
			if (userEntity != null) {
				user = userEntity.toDomain();
			}

		} catch (ClassCastException ex) {
			ex.printStackTrace();
		} finally {
			session.disableFilter("deletedUserFilter");
		}

		return user;
	}

	@Override
	public List<User> findAll() {

		List<User> userList = null;

		Session session = entityManager.unwrap(Session.class);
		Filter filter = session.enableFilter("deletedUserFilter");
		filter.setParameter("isDeleted", Boolean.FALSE);

		List<UserEntity> userEntityList = jpaRepository.findAll();

		try {
			Stream<User> userStream = userEntityList.stream().<User>map((x) -> {
				return x.toDomain();
			});

			userList = userStream.collect(Collectors.toList());

		} catch (ClassCastException ex) {
			ex.printStackTrace();
			userList = new ArrayList<User>();
		} finally {
			session.disableFilter("deletedUserFilter");
		}

		return userList;
	}

	@Override
	public Page<User> getPage(int page, int size, String username) {

		Page<User> usersPage = Page.<User>empty();

		Session session = entityManager.unwrap(Session.class);
		Filter filter = session.enableFilter("deletedUserFilter");
		filter.setParameter("isDeleted", Boolean.FALSE);

		try {

			Pageable pageReq = PageRequest.of(page - 1, size);

			Page<UserEntity> userEntityList;

			if (username != null) {
				userEntityList = jpaRepository.findByUsername(pageReq, username);
			} else {
				userEntityList = jpaRepository.findAll(pageReq);
			}

			usersPage = userEntityList.<User>map((x) -> {
				return x.toDomain();
			});
		} catch (ClassCastException ex) {
			ex.printStackTrace();
			usersPage = Page.<User>empty();
		} finally {
			session.disableFilter("deletedUserFilter");
		}

		return usersPage;
	}

	@Override
	public Boolean deleteById(String userId) throws Exception {
		Optional<UserEntity> userLookup = jpaRepository.findById(userId);
		if (userLookup.isEmpty()) {
			throw new Exception("Attempt to delete user that does not exist");
		}
		jpaRepository.deleteById(userId);
		return Boolean.TRUE;
	}

	@Override
	public Boolean toggleUserAccessById(String userId, Boolean value) throws Exception {
		Optional<UserEntity> userLookup = jpaRepository.findById(userId);
		if (userLookup.isEmpty()) {
			throw new Exception("Attempt to perform an action on a user that does not exist");
		}

		UserEntity update = userLookup.get();
		update.setEnabled(value);

		UserEntity user = jpaRepository.save(update);
		return user.isEnabled() == value;
	}

}
