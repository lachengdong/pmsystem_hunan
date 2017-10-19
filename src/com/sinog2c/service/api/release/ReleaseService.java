package com.sinog2c.service.api.release;

import java.util.List;
import java.util.Map;


public interface ReleaseService {
	
	List<Map> ajaxCodeShuJu(Map map);
	
	int saveOutPrison(Map map);
	
	List<Map> getOutPrison(String crimid);
	

}
