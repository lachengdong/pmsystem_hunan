package com.sinog2c.service.api.commutationParole;

import java.util.List;
import java.util.Map;

public interface SentenceAlterationHisService {

	int countSentenceChangesOfCrimid(Map map);
	
	List<Map>  getSentenceChangesOfCrimid(Map map);
	
	int countJiFenBuLuOfCrimid(Map<String,Object> map);
	
	List<Map<String,Object>>  getJiFenBuLuOfCrimid(Map<String,Object> map);
	
	Map<String,Object> getSingleSentenceChangeOfCrim(Map params);
}
