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

  public UserProfile handle(String login) {
    User user = userRepository.findUserByUsernameOrEmail(login);
    if (user != null) {
      return userRepository.findUserByUsernameOrEmail(login).mapToUserProfile();
    } else {
      return null;
    }
  }
}
