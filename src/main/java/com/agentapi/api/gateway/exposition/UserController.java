package com.agentapi.api.gateway.exposition;

import java.util.Date;
import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.agentapi.api.core.application.GenerateUsers;
import com.agentapi.api.core.application.GenerateUsersInput;
import com.agentapi.api.core.domain.User;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("api/users")
@AllArgsConstructor
public class UserController {
	
	private GenerateUsers generateUsers;
	
	@GetMapping(value="generate")
	public ResponseEntity<List<User>> generateUsers(@RequestParam Integer count){
		
		List<User> fakeList = generateUsers.handle(new GenerateUsersInput(count));
		
		return ResponseEntity.ok()
		        .contentType(MediaType.APPLICATION_JSON)
		        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"users_list.json\"")
		        .body(fakeList);

	}
}
