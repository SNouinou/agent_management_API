package com.agentapi.api.core.application;

import java.util.List;

import com.agentapi.api.core.domain.User;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class FetchUserListInput {
	
	private Integer page;
}
