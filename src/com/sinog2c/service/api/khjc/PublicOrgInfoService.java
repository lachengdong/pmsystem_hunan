package com.sinog2c.service.api.khjc;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

//部门相关操作方法
public interface PublicOrgInfoService {
	//根据部门id获取部门名称
	public String getOrgName(String depid);
	
	//根据类型获取部门字符串
	public String getOrgStr(String type,HttpServletRequest request);
}
