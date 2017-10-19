package com.sinog2c.service.api.system;

import java.util.List;
import java.util.Map;

public interface TbViewMaintainService {

	Map selectByCondition(Map map);
	
	List<Map> selectByProvinceid(Map map);
	
}
