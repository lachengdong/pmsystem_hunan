package com.sinog2c.service.api.system;

import java.util.List;
import java.util.Map;

import com.sinog2c.model.system.Propertypunishmentperform;

public interface PropertypunishmentperformService {
	List findByProperty(Map map);
	List findByPropertyDetail(Map map);
	int findByPropertyCount(Map map);
	int findByPropertyDetailCount(Map map);
	int insert(Propertypunishmentperform record);
	int findMaxid();
	int updateByPrimaryKey(Propertypunishmentperform propertypunishmentperform);
	int deleteByPrimaryKey(String propertyid);
	 String findSumPro(Map map);
}
