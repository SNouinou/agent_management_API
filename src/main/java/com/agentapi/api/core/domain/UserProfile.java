package com.agentapi.api.core.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserProfile {
  private String firstName;
  private String lastName;
  private Date birthDate;
  private String city;
  private String country;
  private String avatar;
  private String company;
  private String jobPosition;
  private String mobile;
  private String username;
  private String email;
  private String role;
}
