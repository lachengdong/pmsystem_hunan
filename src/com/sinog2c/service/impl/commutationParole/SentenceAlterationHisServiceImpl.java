package com.sinog2c.service.impl.commutationParole;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.sinog2c.dao.api.commutationParole.SentenceAlterationHisMapper;
import com.sinog2c.service.api.commutationParole.SentenceAlterationHisService;
import com.sinog2c.util.common.MapUtil;

@Service("sentenceAlterationHisService")
public class SentenceAlterationHisServiceImpl implements SentenceAlterationHisService{
	
	@Resource
	SentenceAlterationHisMapper sentenceAlterationHisMapper;

	public int countSentenceChangesOfCrimid(Map map){
		return sentenceAlterationHisMapper.countSentenceChangesOfCrimid(map);
	}
	
	public List<Map>  getSentenceChangesOfCrimid(Map map){
		List<Map> result = sentenceAlterationHisMapper.getSentenceChangesOfCrimid(map);
		return MapUtil.turnKeyToLowerCaseOfList(result);
	}
	
	public Map<String,Object> getSingleSentenceChangeOfCrim(Map params){
		Map result = sentenceAlterationHisMapper.getSingleSentenceChangeOfCrim(params);
		return MapUtil.turnKeyToLowerCase(result);
	}
	
	
	public int countJiFenBuLuOfCrimid(Map<String,Object> map){
		return sentenceAlterationHisMapper.countJiFenBuLuOfCrimid(map);
	}
	
	
	public List<Map<String,Object>>  getJiFenBuLuOfCrimid(Map<String,Object> map){
		List<Map<String,Object>> result = sentenceAlterationHisMapper.getJiFenBuLuOfCrimid(map);
		return MapUtil.convertKeyList2LowerCase(result);
	}
}
