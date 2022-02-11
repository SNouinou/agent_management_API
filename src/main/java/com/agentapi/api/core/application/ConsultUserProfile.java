package com.agentapi.api.core.application;

import org.springframework.stereotype.Component;

import com.agentapi.api.core.domain.User;
import com.agentapi.api.core.domain.UserProfile;
import com.agentapi.api.core.domain.UserRepository;
import com.agentapi.api.gateway.infrastructure.data.UserEntity;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Component
public class ConsultUserProfile {
	
  private UserRepository userRepository;

  public UserProfile handle(String username) {
    User user = userRepository.findUserByUsernameOrEmail(username);
    if (user != null) {
      return userRepository.findUserByUsernameOrEmail(username).mapToUserProfile();
    } else {
      return null;
    }
  }
}
