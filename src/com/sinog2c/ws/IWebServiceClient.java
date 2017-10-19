package com.sinog2c.ws;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.sinog2c.model.dbmsnew.DbmsNewDataExportBean;
import com.sinog2c.model.system.SystemUser;

public interface IWebServiceClient {
	
//	public Object wsCaseDataExchange(String ip, String port,String ddcid,String condition);
	
//	public Object wsCaseDataExchange_sx(Map map);
	
//	public Object wsCaseDataExchange_sxsj(Map map);
	
//	public Object wsClientSendSignal(String departid, Map<String,Object> senderData);
	
	/**
	 * 描述：狱务公开根据数据方案同步
	 * @param url  例如：http://localhost:8080/pmsystem/ws/pmsystem?wsdl
	 * @param paramMap 查询方案需要的参数集
	 * */
	public Object wsYwgkByDataScheme(String url,String ddcid,Map<String,Object> paramMap);
	
	/**
	 *描述： 狱务公开根据存储过程同步
	 *@param url 例如：http://localhost:8080/pmsystem/ws/pmsystem?wsdl
	 *@param 
	 * */
	public Object wsYwgkByProcedure(String url,Map<String,Object> callSql);
	
	
	public Object webServiceSender(SystemUser user, Map<String,Object> paramap,HttpServletRequest request);
	
	/**
	 * 根据方案id,ddcid获取webservice监听方案，找到监听的ip port
	 * 数据库连接的数据库名称为项目路径，projectPath，数据库用户名称为webservice方法
	 * 根据方法返回的值解析为xml文件保存下来，然后解析xml文件根据配置的webservice解析方案自动存入到表里
	 * @param bean
	 * @param user
	 * @return
	 */
	public Object executeWebXmlResolve(DbmsNewDataExportBean bean,SystemUser user);
	
	public String testWebXmlResolve(DbmsNewDataExportBean bean,SystemUser user);

}
