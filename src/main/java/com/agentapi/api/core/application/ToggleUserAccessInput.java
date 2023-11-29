package com.agentapi.api.core.application;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ToggleUserAccessInput {
	
	private String id;
	private Boolean value;
}
