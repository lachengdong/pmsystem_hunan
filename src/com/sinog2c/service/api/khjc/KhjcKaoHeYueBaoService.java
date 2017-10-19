package com.sinog2c.service.api.khjc;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sinog2c.model.flow.FlowArchivesCode;

public interface KhjcKaoHeYueBaoService {
	/**
	 * 获取考核月报表信息总数
	 * @param map
	 * yanqutai
	 * @return
	 */
	public int countForMonthCheck(HashMap<Object, Object> map);
	/**
	 * 获取考核月报表信息
	 * @param map
	 * yanqutai
	 * @return
	 */
	public List<HashMap<Object, Object>> getCriminalForMonthCheck(
			HashMap<Object, Object> map);
	
	public int insertByMap(Map<Object,Object> map);
	
	public int updateByMap(Map<Object,Object> map);
	
	public List<Map> searchScoreByCrimid(Map map);
}
