package com.agentapi.api.core.domain;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
	
	private String id;
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
	  private String password;
	  private UserRole role;
	  
	  public UserProfile mapToUserProfile() {
		    return new UserProfile(
		        this.firstName,
		        this.lastName,
		        this.birthDate,
		        this.city,
		        this.country,
		        this.avatar,
		        this.company,
		        this.jobPosition,
		        this.mobile,
		        this.username,
		        this.email,
		        this.role.name()
		    );
		  }

}
