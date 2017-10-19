package com.sinog2c.dao.api.assessAndPunish;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.sinog2c.model.assessAndPunish.TbyzRewards;

@Component("tbyzRewardsMapper")
public interface TbyzRewardsMapper {
    int insert(TbyzRewards record);

    int insertSelective(TbyzRewards record);
    
	public int getPunishInfoCountOfCrim(Map map);
	
	public List<Map> getPunishInfoOfCrim(Map map);
	
}