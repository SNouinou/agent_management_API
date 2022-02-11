package com.agentapi.api.core.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BatchFeedback {
	
	  private Integer totalEntries;
	  private Integer successes;
	  private Integer failures;
}
