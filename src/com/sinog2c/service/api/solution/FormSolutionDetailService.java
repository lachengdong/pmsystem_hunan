package com.sinog2c.service.api.solution;

import java.util.List;
import java.util.Map;

import com.sinog2c.model.solution.FormSolutionDetail;
import com.sinog2c.model.system.SystemUser;

public interface FormSolutionDetailService{
	
	public List<FormSolutionDetail> getTreeDataOfSolutionDetail(Map<String,Object> map);
	
	public FormSolutionDetail getSingleSolutionDetail(Map<String,Object> map);
	
	public int saveSolutionDetail(Map<String,Object> map,SystemUser su);
	
	public int deleteSolutionDetail(String detailid,SystemUser su);

}
