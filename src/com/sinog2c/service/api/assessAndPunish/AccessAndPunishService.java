package com.sinog2c.service.api.assessAndPunish;

import java.util.List;
import java.util.Map;

public interface AccessAndPunishService {

	int getPunishInfoCountOfCrim(Map map);
	
	List<Map> getPunishInfoOfCrim(Map map);
	
}
