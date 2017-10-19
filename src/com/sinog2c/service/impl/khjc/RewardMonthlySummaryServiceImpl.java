package com.sinog2c.service.impl.khjc;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sinog2c.dao.api.khjc.TkhMonthlysummaryMapper;
import com.sinog2c.service.api.khjc.RewardMonthlySummaryService;

@Service("rewardMonthlySummaryService")
public class RewardMonthlySummaryServiceImpl implements RewardMonthlySummaryService{
	@Autowired
	private TkhMonthlysummaryMapper tkhMonthlysummaryMapper;
	
	public List<Map<String,Object>> getRewardMonthlySummaryList(Map<String,Object> map) {
		return tkhMonthlysummaryMapper.getRewardMonthlySummaryList(map);
	}
	
	public int getRewardMonthlySummaryCount(Map<String,Object> map) {
		return tkhMonthlysummaryMapper.getRewardMonthlySummaryCount(map);
	}
}
