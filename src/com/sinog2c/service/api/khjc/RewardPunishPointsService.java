package com.sinog2c.service.api.khjc;

import java.util.List;
import java.util.Map;

import com.sinog2c.model.khjc.TkhRewardpunishrule;
import com.sinog2c.model.system.TbsysLocalcode;

public interface RewardPunishPointsService {
	List<Map<String,Object>> ajaxRewardPunishRules(Map<String,Object> map);
	
	Map<String,Object> ajaxRewardPunishRulesDetail(Map<String,Object> map);
	
	int getRewardPunishRuleCount(Map<String,Object> map);
	
	List<Map<String,Object>> getRewardPunishRuleList(Map<String,Object> map);
	
	int insertRewardPunishRule(TkhRewardpunishrule record);
	
	void updateIsleafVal(TkhRewardpunishrule record);
	
	TkhRewardpunishrule getRewardPunishRuleByPrimaryKey(String ruleid);
		
}
