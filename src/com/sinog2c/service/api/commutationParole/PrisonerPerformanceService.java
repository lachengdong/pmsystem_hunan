package com.sinog2c.service.api.commutationParole;

import java.util.HashMap;
import java.util.List;


public interface PrisonerPerformanceService {
	
	public int getCount(HashMap map);
	
	public List<HashMap> getPrisonerPerformanceList(HashMap map);

	
}
