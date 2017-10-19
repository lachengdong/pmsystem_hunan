package com.sinog2c.service.api.khjc;

import java.util.List;
import java.util.Map;

public interface RewardMonthlySummaryService {
	List<Map<String,Object>> getRewardMonthlySummaryList(Map<String,Object> map);
	int getRewardMonthlySummaryCount(Map<String,Object> map);
}
