package com.sinog2c.service.api.commutationParole.sealfile;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.sinog2c.model.system.SystemUser;
import com.sinog2c.model.system.TbsysCode;

/**
 * 预览打印
 * @author liuyi
 *
 */
public interface SealFileService {
	/**
	 * 获取签章文件列表
	 * @param request
	 * @return
	 * 2014-09-03 11:39:13
	 */
	public  Object  getSealFileList(HttpServletRequest request,SystemUser user);
	
	/**
	 * 方法描述：查询出签章方案
	 * @author 
	 * @version: 2014年9月10日11:44:52
	 */
	public List<Map> getSignScheme(HttpServletRequest request,SystemUser user);
	/**
	 * 方法描述：更新签章进程
	 * @auhtor
	 * @version 2014-1-11 11:19:11
	 */
	public int updateSignProgressToFlowBase(HttpServletRequest request);
}
