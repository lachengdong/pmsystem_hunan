package com.sinog2c.service.impl.khjc;

import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.sinog2c.dao.api.khjc.TkhRewardpunishruleMapper;
import com.sinog2c.model.khjc.TkhRewardpunishrule;
import com.sinog2c.service.api.khjc.RewardPunishPointsService;
import com.sinog2c.util.common.MapUtil;

@Service("rewardPunishPointsService")
public class RewardPunishPointsServiceImpl implements RewardPunishPointsService{
	@Autowired
	private TkhRewardpunishruleMapper tkhRewardpunishruleMapper;
	
	public List<Map<String,Object>> ajaxRewardPunishRules(Map<String,Object> map) {
		return MapUtil.convertKeyList2LowerCase(tkhRewardpunishruleMapper.ajaxRewardPunishRules(map));
	}
	
	public Map<String,Object> ajaxRewardPunishRulesDetail(Map<String,Object> map) {
		return MapUtil.convertKey2LowerCase(tkhRewardpunishruleMapper.ajaxRewardPunishRulesDetail(map));
	}
	
	public int getRewardPunishRuleCount(Map<String,Object> map) {
		return tkhRewardpunishruleMapper.getRewardPunishRuleCount(map);
	}
	
	public List<Map<String,Object>> getRewardPunishRuleList(Map<String,Object> map) {
		return tkhRewardpunishruleMapper.getRewardPunishRuleList(map);
	}
	
	public int insertRewardPunishRule(TkhRewardpunishrule record) {
		return tkhRewardpunishruleMapper.insertSelective(record);
	}
	
	public void updateIsleafVal(TkhRewardpunishrule record) {
		tkhRewardpunishruleMapper.updateByPrimaryKeySelective(record);
	}
	
	public TkhRewardpunishrule getRewardPunishRuleByPrimaryKey(String ruleid) {
		return tkhRewardpunishruleMapper.selectByPrimaryKey(ruleid);
	}
}
