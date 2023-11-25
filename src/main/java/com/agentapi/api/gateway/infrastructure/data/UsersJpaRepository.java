package com.agentapi.api.gateway.infrastructure.data;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UsersJpaRepository extends JpaRepository<UserEntity, String> {
	  @Query("select u from UserEntity u where u.username = :login or u.email = :login")
	  UserEntity findByUsernameOrEmail(@Param("login") String login);

	  List<UserEntity> findAll();
	  Page<UserEntity> findAll(Pageable pageReq);
	  
	  @Query("select u from UserEntity u where u.username LIKE %:username%")
	  Page<UserEntity> findByUsername(Pageable pageReq,@Param("username") String username);
	  
}
