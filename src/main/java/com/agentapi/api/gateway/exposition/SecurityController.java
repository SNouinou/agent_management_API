package com.agentapi.api.gateway.exposition;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.agentapi.api.gateway.infrastructure.service.LoginRequest;
import com.agentapi.api.gateway.infrastructure.service.LoginResponse;
import com.agentapi.api.gateway.infrastructure.service.SecurityService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("api/auth")
@AllArgsConstructor
public class SecurityController {
	
	private SecurityService securityService;
	
    @PostMapping(value = "")
    public ResponseEntity<LoginResponse> login(HttpServletRequest requestHeader, @RequestBody LoginRequest request) {
    	try {
        LoginResponse loginResponse = securityService.login(request.getUsername(), request.getPassword());
            return new ResponseEntity<>(loginResponse, HttpStatus.OK);
    	} catch(BadCredentialsException e) {
    		throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found.");
    	} catch(DisabledException e) {
    		throw new ResponseStatusException(HttpStatus.FORBIDDEN, "User does not have access.");
    	} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
		}
    }

}
