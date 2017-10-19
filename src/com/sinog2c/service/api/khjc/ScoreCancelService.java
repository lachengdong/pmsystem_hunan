package com.sinog2c.service.api.khjc;

import java.util.List;
import java.util.Map;


public interface ScoreCancelService {
	
	int countAllByCondition(Map<String,Object> map);

	List<Map<String,Object>> getBasicInfoList(Map<String,Object> map);

	int countCondition(Map<String, Object> map);

	List<Map<String, Object>> getInfoList(Map<String, Object> map);

	int getCancelReason(Map<String,Object> map);

	String getCancelReasonView(Map<String, Object> map);

}
