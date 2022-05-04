package com.agentapi.api;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.json.JSONObject;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.agentapi.api.core.domain.User;
import com.agentapi.api.core.domain.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest(properties= {"spring.jpa.hibernate.ddl-auto=create"})
@AutoConfigureMockMvc
@TestMethodOrder(OrderAnnotation.class)
class ConsultUserTest {
	
	@Autowired
	MockMvc mockmvc;
	
	final Integer BATCH_SIZE = 10;
	
	@BeforeAll
	static void prepareDbData(@Autowired PasswordEncoder encoder,@Autowired UserRepository userRepository) throws Exception {
		
		File file = new File(ConsultUserTest.class.getResource("/users_list.json").toURI());
		
		ObjectMapper objectMapper = new ObjectMapper();
		User[] users = objectMapper.readValue(file, User[].class);
		
		List<User> userList = new ArrayList<User>();
		Collections.addAll(userList, users);
		userList.stream().forEach(user->user.setPassword(encoder.encode(user.getPassword())));
		userRepository.saveAll(userList);
		
	}
	
	@Test
	void userAuthentificateTest() throws Exception {
	    		
	    String string = "{"
	    		+ "\"username\": \"hermine.kuhn@yahoo.com\","
	    		+ "\"password\": \"1g8pkzm5m80a\""
	    		+ "}";
		
		mockmvc.perform(MockMvcRequestBuilders.post("/api/auth").contentType(MediaType.APPLICATION_JSON).content(string))
			.andDo(MockMvcResultHandlers.print())
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.jsonPath("$.accessToken").exists());
	}
	
	@Test
	void userViewUserTest() throws Exception {
				
	    String string = "{"
	    		+ "\"username\": \"hermine.kuhn@yahoo.com\","
	    		+ "\"password\": \"1g8pkzm5m80a\""
	    		+ "}";
		
		MvcResult result = mockmvc.perform(
				MockMvcRequestBuilders.post("/api/auth")
					.contentType(MediaType.APPLICATION_JSON)
					.content(string)
				).andReturn();
		
		String response = result.getResponse().getContentAsString();
		String token = "Bearer " + new JSONObject(response).getString("accessToken");
		
		mockmvc.perform( MockMvcRequestBuilders.get("/api/users/me")
				.header("Authorization", token))
			.andDo(MockMvcResultHandlers.print())
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(jsonPath("$.firstName").value("Adrian"))
			.andExpect(jsonPath("$.lastName").value("Dietrich"));
	}
	
	@Test
	void userViewAdminProfileTest() throws Exception {
				
	    String string = "{"
	    		+ "\"username\": \"hermine.kuhn@yahoo.com\","
	    		+ "\"password\": \"1g8pkzm5m80a\""
	    		+ "}";
		
		MvcResult result = mockmvc.perform(
				MockMvcRequestBuilders.post("/api/auth")
					.contentType(MediaType.APPLICATION_JSON)
					.content(string)
				).andReturn();
		
		String response = result.getResponse().getContentAsString();
		String token = "Bearer " + new JSONObject(response).getString("accessToken");
		
		mockmvc.perform( MockMvcRequestBuilders.get("/api/users/ruth.quigley")
				.header("Authorization", token))
			.andDo(MockMvcResultHandlers.print())
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(jsonPath("$.firstName").value("Lloyd"))
			.andExpect(jsonPath("$.lastName").value("Nolan"));
	}
	
	@Test
	void userViewUserProfileTest() throws Exception {
				
	    String string = "{"
	    		+ "\"username\": \"leila.weimann\","
	    		+ "\"password\": \"8xo8x77uh45t\""
	    		+ "}";
		
		MvcResult result = mockmvc.perform(
				MockMvcRequestBuilders.post("/api/auth")
					.contentType(MediaType.APPLICATION_JSON)
					.content(string)
				).andReturn();
		
		String response = result.getResponse().getContentAsString();
		String token = "Bearer " + new JSONObject(response).getString("accessToken");
		
		mockmvc.perform( MockMvcRequestBuilders.get("/api/users/ruth.quigley")
				.header("Authorization", token))
			.andDo(MockMvcResultHandlers.print())
			.andExpect(MockMvcResultMatchers.status().is(401));
	}

}
