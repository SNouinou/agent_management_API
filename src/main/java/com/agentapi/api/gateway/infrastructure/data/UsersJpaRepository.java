package com.agentapi.api.gateway.infrastructure.data;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UsersJpaRepository extends JpaRepository<UserEntity, String> {

}
