package com.sinog2c.thread;

import java.util.HashMap;
import java.util.Map;

import com.alibaba.druid.support.json.JSONUtils;
import com.sinog2c.model.system.SystemOrganization;
import com.sinog2c.model.system.SystemUser;
import com.sinog2c.service.api.dbmsnew.DataTransferService;

public class DataExchangeRunnable implements Runnable{

	private String paramStr;
	private DataTransferService dataTransferService;
	
	public DataExchangeRunnable(){
	}
	
	public DataExchangeRunnable(String paramStr, DataTransferService dataTransferService){
		this.paramStr = paramStr;
		this.dataTransferService = dataTransferService;
	}
	
	
	
	@Override
	public void run() {
		
		Object obj = JSONUtils.parse(paramStr);
		if(obj instanceof Map<?,?>){
			Map<String,Object> paramap = (HashMap<String,Object>)obj;
			
			SystemUser user = new SystemUser();
			String departid = paramap.get("departid").toString();
			user.setDepartid(departid);
			user.setUserid("sys"); 
			SystemOrganization organization = new SystemOrganization();
			organization.setOrgid(departid);
			user.setOrganization(organization);
			user.setName("sys");
			
			String remoteDepartid = paramap.get("remoteDepartid").toString();//由webservice客户端的单位id
//			String isArch = paramap.get("isArch").toString(); // 是否传递电子档案; isArch = 1 表示传递
			String isArch = null;//电子档案不再需要从这里传递，而是通过cwrsync等文件同步工具实现传递
			String crimids = paramap.get("crimids").toString(); // 传递哪些罪犯的电子档案
			String type = "1";
			dataTransferService.dataExchange(type,paramap, user, remoteDepartid, crimids, isArch);
		}
		
	}
	
	
}
