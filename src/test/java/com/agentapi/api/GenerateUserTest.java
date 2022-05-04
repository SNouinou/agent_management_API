package com.agentapi.api;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest(properties= {"spring.jpa.hibernate.ddl-auto=create"})
@AutoConfigureMockMvc
@TestMethodOrder(OrderAnnotation.class)
class GenerateUserTest {
	
	@Autowired
	MockMvc mockmvc;
	
	final Integer COUNT = 10;

	@Test
	void userGenerateTest() throws Exception {
		
		mockmvc.perform( MockMvcRequestBuilders.get("/api/users/generate").param("count", COUNT.toString()))
			.andDo(MockMvcResultHandlers.print())
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
			.andExpect(MockMvcResultMatchers.header().string("content-disposition", "attachment; filename=\"users_list.json\""))
			.andExpect(jsonPath("$").isArray())
			.andExpect(jsonPath("$.*", hasSize(COUNT)));
		
	}

}
