package com.sinog2c.service.api.solution;

import java.util.List;
import java.util.Map;

import com.sinog2c.model.solution.FormParamMapping;
import com.sinog2c.model.system.SystemUser;

public interface FormParamMappingService {
	
	public List<FormParamMapping> getFormParamMappingDataList(Map<String,Object> map);
	
	public int countFormParamMappingDataList(Map<String,Object> map);
	
	public FormParamMapping getSingleParamMapping(Map<String,Object> map);
	
	public int saveParamMapping(Map<String,Object> map,SystemUser su);
	
	public int deleteParamMapping(Map<String,Object> map);
	
}
