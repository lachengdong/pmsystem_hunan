package com.sinog2c.service.api.solution;

import java.util.List;
import java.util.Map;

import com.sinog2c.model.solution.FormSqlGroup;
import com.sinog2c.model.system.SystemUser;

public interface FormSqlGroupService {
	
	public List<FormSqlGroup> getTreeDataOfFormSqlGroup(Map<String,Object> map);
	
	public FormSqlGroup getFormSqlGroupData(Map<String,Object> map);
	
	public int saveDetailSqlGroup(Map<String,Object> map,SystemUser su);
	
	public int deleteDetailSqlGroup(String sqlgroupid,SystemUser su);

}
