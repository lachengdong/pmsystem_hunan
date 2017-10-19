package com.sinog2c.service.api.system;

import java.util.List;
import java.util.Map;

import com.sinog2c.model.system.SystemUser;

public interface SystemMainService {
	
	List<Map> getBaseDocByCondition(Map map);
	
	int countBaseDocByCondition(Map map);
	
	List<Map> getDoneCaseType(Map map);
	
}
