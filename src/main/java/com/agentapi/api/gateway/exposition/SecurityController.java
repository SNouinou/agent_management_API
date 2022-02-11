package com.agentapi.api.gateway.exposition;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public ResponseEntity<LoginResponse> login(HttpServletRequest requestHeader, @RequestBody LoginRequest request) throws RuntimeException {

        LoginResponse loginResponse = securityService.login(request.getUsername(), request.getPassword());
        if(loginResponse == null){
            throw new RuntimeException("Login failed.");
        }else{
            return new ResponseEntity<>(loginResponse, HttpStatus.OK);
        }
    }

}
