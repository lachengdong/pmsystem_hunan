package com.sinog2c.dao.api.province;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.sinog2c.model.system.SystemOrganization;

@Repository
public interface EchartsStatisticCaseInfoMapper {
	
	public List<SystemOrganization> getOrganiztionsByCityName(String provinceCode,String cityName);//根据城市名称获取所有监狱信息
	
	public List<SystemOrganization> getOrganiztionsByPageList(Map<String,Object>paraMap);//分页
	public int getOrganiztionsByPageCount(Map<String,Object>paraMap);
	
	public int getCaseCountByCityName(Map<String, Object> paraMap);//根据城市名称统计
	
	
	public int getSanLeiCaseCount(Map<String, Object> paraMap);//全省根据三类犯统计数量
	public int getJailSanLeiCaseCount(Map<String, Object> paraMap);//监狱根据三类犯统计数量
	
	
	public int getAYCount(Map<String,Object>paraMap);//全省根据案由统计
	public int getJailAYCount(Map<String,Object>paraMap);//监狱根据案由统计
	
	public int getCaseTypeCount(Map<String, Object> paraMap);//全省根据案件类型统计
	public int getJailCaseTypeCount(Map<String, Object> paraMap);//监狱根据案件类型统计
	
	public int getJXJSCount(Map<String, Object> paraMap);//全省根据减刑假释进行统计
	public int getJailJXJSCount(Map<String, Object> paraMap);//监狱根据减刑假释进行统计
	
	public List<Map<String,Object>>getSanLeiCaseList(Map<String,Object>paraMap);
	public List<Map<String,Object>>getJailSanLeiCaseList(Map<String,Object>paraMap);
	
	public List<Map<String,Object>>getAYCaseList(Map<String,Object>paraMap);
	public List<Map<String,Object>>getJailAYCaseList(Map<String,Object>paraMap);
	
	public List<Map<String,Object>>getCaseTypeList(Map<String,Object>paraMap);
	public List<Map<String,Object>>getJailCaseTypeList(Map<String,Object>paraMap);
	
	public List<Map<String,Object>>getJXJSList(Map<String,Object>paraMap);
	public List<Map<String,Object>>getJailJXJSList(Map<String,Object>paraMap);
	
	}
