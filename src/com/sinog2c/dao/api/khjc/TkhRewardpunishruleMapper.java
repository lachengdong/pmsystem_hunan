package com.sinog2c.dao.api.khjc;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.sinog2c.model.khjc.TkhRewardpunishrule;

@Component("tkhRewardpunishruleMapper")
public interface TkhRewardpunishruleMapper {
    int deleteByPrimaryKey(String ruleid);

    int insert(TkhRewardpunishrule record);

    int insertSelective(TkhRewardpunishrule record);

    TkhRewardpunishrule selectByPrimaryKey(String ruleid);

    int updateByPrimaryKeySelective(TkhRewardpunishrule record);

    int updateByPrimaryKey(TkhRewardpunishrule record);
    
    List<Map<String,Object>> ajaxRewardPunishRules(Map<String,Object> map);
    
    Map<String,Object> ajaxRewardPunishRulesDetail(Map<String,Object> map);
    
    int getRewardPunishRuleCount(Map<String,Object> map);
    
    List<Map<String,Object>> getRewardPunishRuleList(Map<String,Object> map);
}