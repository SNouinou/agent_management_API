package com.agentapi.api.gateway.exposition;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.agentapi.api.core.application.BatchUserList;
import com.agentapi.api.core.application.BatchUserListInput;
import com.agentapi.api.core.application.GenerateUsers;
import com.agentapi.api.core.application.GenerateUsersInput;
import com.agentapi.api.core.domain.BatchFeedback;
import com.agentapi.api.core.domain.User;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("api/users")
@AllArgsConstructor
public class UserController {
	
	private GenerateUsers generateUsers;
	
	private BatchUserList batchUserList;
	
	@GetMapping(value="generate")
	public ResponseEntity<List<User>> generateUsers(@RequestParam Integer count){
		
		List<User> fakeList = generateUsers.handle(new GenerateUsersInput(count));
		
		return ResponseEntity.ok()
		        .contentType(MediaType.APPLICATION_JSON)
		        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"users_list.json\"")
		        .body(fakeList);

	}
	
	@PostMapping(value="batch", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
	public BatchFeedback batchUserList(@RequestParam("file") MultipartFile userFile) throws IOException {
		
		InputStream jsonArray = userFile.getInputStream();

		ObjectMapper objectMapper = new ObjectMapper();

		User[] users = objectMapper.readValue(jsonArray, User[].class);
		
		return batchUserList.handle(new BatchUserListInput(Arrays.asList(users)));
		
	}

}
