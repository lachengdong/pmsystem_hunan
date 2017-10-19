package com.sinog2c.service.impl.khjc;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;

import com.sinog2c.dao.api.khjc.KhjccriminalmonthscoresdMapper;
import com.sinog2c.service.api.khjc.KhjcKaoHeYueBaoService;

@Service("khjcKaoHeYueBaoService")
public class KhjcKaoHeYueBaoServiceImpl implements KhjcKaoHeYueBaoService{
	@Resource
	private KhjccriminalmonthscoresdMapper khjccriminalmonthscoresdMapper;
	/**
	 * 获取考核月报表信息总数
	 * @param map
	 * yanqutai
	 * @return
	 */
	@Override
	public int countForMonthCheck(HashMap<Object, Object> map) {
		
		return khjccriminalmonthscoresdMapper.countForMonthCheck(map);
	}
	/**
	 * 获取考核月报表信息
	 * @param map
	 * yanqutai
	 * @return
	 */
	@Override
	public List<HashMap<Object, Object>> getCriminalForMonthCheck(
			HashMap<Object, Object> map) {
		return khjccriminalmonthscoresdMapper.getCriminalForMonthCheck(map);
	}
	@Override
	public int insertByMap(Map<Object, Object> map) {
		return khjccriminalmonthscoresdMapper.insertByMap(map);
	}
	@Override
	public int updateByMap(Map<Object, Object> map) {
		return khjccriminalmonthscoresdMapper.updateByMap(map);
	}
	
	@Override
	public List<Map> searchScoreByCrimid(Map map) {
		return khjccriminalmonthscoresdMapper.searchScoreByCrimid(map);
	}
}
