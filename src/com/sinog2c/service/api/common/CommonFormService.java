package com.sinog2c.service.api.common;

import java.util.Map;

import com.alibaba.fastjson.JSONArray;
import com.sinog2c.model.system.SystemUser;
import com.sinog2c.mvc.message.JSONMessage;


/**
 * 通用公共表单服务接口
 */
public interface CommonFormService{
	
	public JSONMessage commonRemoveDate(Map<String,Object> map,SystemUser user);
	
	public JSONArray parseFormDataOfSolution(Map resultMap);
	
	public JSONMessage updateDate(Map<String,Object> map,SystemUser user);
	
	public Map<String,Object> assembleFormData(JSONArray docconent,Map<String,Object> paramMap,SystemUser user,String tempid);
	
}
