package com.agentapi.api.core.application;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class FetchUserListInput {
	
	private Integer page;
	private Integer size;
	private String username;
}
