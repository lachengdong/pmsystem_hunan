package com.sinog2c.service.api.khjc;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.sinog2c.model.system.SystemUser;
import com.sinog2c.mvc.message.JSONMessage;

public interface KhjcPublicService {
	//批量操作(提交，退回，拒绝)
	public String piLiangCaoZuo(HttpServletRequest request);
	
	//公共的保存方法
	public Object saveBaseDoc(Map<String,Object> map,SystemUser user);
	
	public Map<String,Object> isPapersMaked(Map<String,Object> map,SystemUser user);
	
	public JSONMessage saveProvinceCommuteLiAnData(Map<String,String> map,SystemUser user);
	
	public JSONMessage saveProvinceOutExecuteLiAnData(Map<String,String> map,SystemUser user);
	
	public JSONMessage saveFormData(String tempid,String docconent,String flowdefid,String flowdraftid,SystemUser user);
	
}
