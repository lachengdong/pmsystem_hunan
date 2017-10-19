package com.sinog2c.service.impl.assessAndPunish;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.sinog2c.dao.api.assessAndPunish.TbyzRewardsMapper;
import com.sinog2c.service.api.assessAndPunish.AccessAndPunishService;
import com.sinog2c.util.common.MapUtil;

@Service("accessAndPunishService")
public class AccessAndPunishServiceImpl implements AccessAndPunishService{
	
	
	@Resource
	private TbyzRewardsMapper tbyzRewardsMapper;
	
	@Override
	public int getPunishInfoCountOfCrim(Map map){
		return tbyzRewardsMapper.getPunishInfoCountOfCrim(map);
	}
	
	@Override
	public List<Map> getPunishInfoOfCrim(Map map){
		List<Map> result = tbyzRewardsMapper.getPunishInfoOfCrim(map);
		if(null==result) return null;
		return MapUtil.turnKeyToLowerCaseOfList(result);
	}
	
}
