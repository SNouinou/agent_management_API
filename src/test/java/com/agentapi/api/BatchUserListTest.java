package com.agentapi.api;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import java.io.File;
import java.io.FileInputStream;
import java.util.List;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.agentapi.api.core.domain.User;
import com.agentapi.api.core.domain.UserRepository;

@SpringBootTest(properties= {"spring.jpa.hibernate.ddl-auto=create"})
@AutoConfigureMockMvc
@TestMethodOrder(OrderAnnotation.class)
class BatchUserListTest {
	
	@Autowired
	MockMvc mockmvc;
	
	@Autowired
	UserRepository userRepository;
	
	final Integer BATCH_SIZE = 10;
	
	@Test
	void userBatchLoadTest() throws Exception {
		
		FileInputStream fileInputStream = new FileInputStream(new File(this.getClass().getResource("/users_list.json").toURI()));
		MockMultipartFile mockMultipartFileStream = new MockMultipartFile("file", fileInputStream);
		
		mockmvc.perform( MockMvcRequestBuilders.multipart("/api/users/batch").file(mockMultipartFileStream))
			.andDo(MockMvcResultHandlers.print())
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(jsonPath("$.successes").value(BATCH_SIZE.toString()))
			.andExpect(jsonPath("$.totalEntries").value(BATCH_SIZE.toString()))
			.andExpect(jsonPath("$.failures").value("0"));
		
		List<User> userList = userRepository.findAll();
		assertThat(userList).isNotEmpty();
	    assertThat(userList.size()).isEqualTo(BATCH_SIZE);
	}

}
