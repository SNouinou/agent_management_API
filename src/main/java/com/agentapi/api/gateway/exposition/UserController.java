package com.agentapi.api.gateway.exposition;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import com.agentapi.api.core.application.BatchUserList;
import com.agentapi.api.core.application.BatchUserListInput;
import com.agentapi.api.core.application.ConsultUserProfile;
import com.agentapi.api.core.application.GenerateUsers;
import com.agentapi.api.core.application.GenerateUsersInput;
import com.agentapi.api.core.domain.BatchFeedback;
import com.agentapi.api.core.domain.User;
import com.agentapi.api.core.domain.UserProfile;
import com.agentapi.api.core.domain.UserRole;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("api/users")
@AllArgsConstructor
public class UserController {
	
	private GenerateUsers generateUsers;
	
	private BatchUserList batchUserList;
	
	private ConsultUserProfile consultUserProfile;
	
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
	
	  @GetMapping("{username}")
	  public ResponseEntity<UserProfile> getUserProfile(@PathVariable String username) {
	    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	    String authenticatedUserEmail = authentication.getPrincipal() instanceof UserDetails ? ((UserDetails)authentication.getPrincipal()).getUsername() : authentication.getPrincipal().toString() ;
	    UserProfile authenticatedUserProfile = consultUserProfile.handle(authenticatedUserEmail);
	    if (Objects.equals(username, "me") || Objects.equals(username, authenticatedUserProfile.getUsername())) {
	      return ResponseEntity.ok(authenticatedUserProfile);
	    } else {
	      if (authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals(UserRole.ADMIN.name()))) {
	        UserProfile userProfile = consultUserProfile.handle(username);
	        if (userProfile != null) {
	          return ResponseEntity.ok(userProfile);
	        } else {
	          throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
	        }
	      } else {
	        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Access Denied!");
	      }
	    }
	  }

}
