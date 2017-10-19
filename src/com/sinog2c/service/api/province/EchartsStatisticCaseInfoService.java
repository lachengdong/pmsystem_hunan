package com.sinog2c.service.api.province;

import java.util.List;
import java.util.Map;

import com.sinog2c.model.system.TbsysCode;

public interface EchartsStatisticCaseInfoService {
	public String getProvinceCode();
	public List<TbsysCode>getCodeInfoListByParentCode(String parentCode);
	public List<Map<String,Object>>getProvinceCaseCount(Map<String, Object> paraMap);
	public List<Map<String,Object>>getSanLeiCaseCount(Map<String, Object> paraMap);
	public List<Map<String,Object>>getCaseTypeCount(Map<String, Object> paraMap);
	public List<Integer>getJXJSCount(Map<String, Object> paraMap);
	public List<Integer> getAYCount(Map<String,Object> paraMap);//案由
	public Map<String,Object>getSanLeiGridData(Map<String, Object> paraMap);
	public Map<String,Object>getCaseTypeGridData(Map<String, Object> paraMap);
	public Map<String,Object>getAnYouGridData(Map<String, Object> paraMap);
	public Map<String,Object>getJXJSGridData(Map<String, Object> paraMap);
	public Map<String ,Object>getJailGridData(Map<String, Object> paraMap);
	
}
