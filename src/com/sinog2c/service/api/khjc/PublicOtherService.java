package com.sinog2c.service.api.khjc;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

//保存和删除时对主表的相关操作
public interface PublicOtherService {
	//会议记录表
	
	//刑法执行工作记录表
	public String saveZfzxgzjl(Map map,HttpServletRequest request);
	public String updateZfzxgzjl(Map map,HttpServletRequest request);
	public String deleteZfzxgzjlByID(String id);
	//刑罚执行授权记录表
	public String saveZfzxsqjl(Map map,HttpServletRequest request);
}
