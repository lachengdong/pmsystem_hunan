package com.sinog2c.service.api.dbmsnew;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.sinog2c.model.system.SystemUser;
import com.sinog2c.mvc.message.JSONMessage;

public interface DataTransferService {
	
	public DataTransferService getNewInstance();
	
	/**
	 * 数据导出
	 */
	public Map<String,Object> dataExport(HttpServletRequest request,Map<String,Object> paramap, SystemUser user);
	
	/**
	 *=描述：导入数据
	 *@author YangZR	2015-05-29
	 *@param	filePath : 要导入数据的目录<br/>
	 *			user	 : 当前用户
	 */
	public boolean dataImport(HttpServletRequest request,String filePath,SystemUser user);
	
	/**
	 * 数据交换
	 */
	public JSONMessage dataExchange(String type, Map<String,Object> paramap, SystemUser user, String remoteDepartid, String crimids, String isArch);
	
	
	/**
	 * @Description: 部门电子档案复制到一个临时目录中。（主要用于cwrsync工具进行跨网段传输）
	 * @param	criminalidList中每一个Map: {crimid:610555555555, departid:6105}
	 * @return void  
	 * @throws
	 * @author 杨祖榕
	 * @date 2016年5月19日  上午10:15:26
	 * @Version 3.1
	 */
	public void preExportArchive(List<Map<String,Object>> criminalidList);
	

}
