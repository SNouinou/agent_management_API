package com.agentapi.api.gateway.infrastructure.fake;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.springframework.stereotype.Component;

import com.agentapi.api.core.domain.User;
import com.agentapi.api.core.domain.UserFaker;
import com.agentapi.api.core.domain.UserRole;
import com.github.javafaker.Faker;

import lombok.NoArgsConstructor;

@NoArgsConstructor
@Component
public class UserJavaFaker implements UserFaker {

	@Override
	public List<User> fakeUsers(int count) {
		
		List<User> fakeList = new ArrayList<User>(count);
		
		Faker faker = new Faker();
		User item;
		for(int i = 0; i<count; i++) {

			item = new User(
					faker.name().firstName(),
					faker.name().lastName(),
					faker.date().birthday(),
					faker.address().cityName(),
					faker.address().country(),
					faker.avatar().image(),
					faker.job().title(),
					faker.job().position(),
					faker.phoneNumber().cellPhone(),
					faker.name().username(),
					faker.internet().emailAddress(),
					faker.internet().password(),
					UserRole.values()[new Random().nextInt(UserRole.values().length)]
					);
			
			fakeList.add(item);
		}

		return fakeList;
	}
	
}
