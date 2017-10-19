package com.sinog2c.service.api.dbmsnew;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.sinog2c.model.dbmsnew.DbmsNewDataExportBean;
import com.sinog2c.model.dbmsnew.TaskBean;
import com.sinog2c.model.system.SystemUser;

public interface DbmsNewDataExportService {

	/**
	 * 对监狱的数据交换进行包装
	 */
	public void autoPrisonDataExchange();
	
	
	public void autoCaseDataExchange();
	
	/**
	 * 旧程序移植,避免多线程干扰
	 * @return
	 */
	public DbmsNewDataExportService getNewInstance();
	/**
	 * 执行监狱的数据交换
	 * @return
	 */
	//public String prisonDataExchange();
	public String prisonDataExchange(DbmsNewDataExportBean bean);
	/**
	 * 交换时记录进度
	 * @param bean
	 * @param taskBean
	 * @return
	 */
	public String prisonDataExchange(DbmsNewDataExportBean bean, TaskBean taskBean);
	/**
	 * 调用存储过程
	 * @param conn
	 * @param sdid
	 * @return
	 */
	public boolean callInterchangeProcedure(Connection conn, String sdid);
	//jianyushujujiaohuanAction
	
	/**
	 * 添加数据导出请求
	 */
	public boolean addDataExportRequest(DbmsNewDataExportBean bean, TaskBean taskBean);
	/**
	 * 添加数据导入请求
	 * @param bean
	 * @return
	 */
	public boolean addDataImportRequest(DbmsNewDataExportBean bean, TaskBean taskBean);
	
	
	public void caseDataExchange(Connection conn,PreparedStatement localps,ResultSet localrs,Map<String,Object> map);
	
	public void outPutArchives(List<String> criminalidList,Connection localconn,String strDdcId);
	
	public void newOutPutArchives(List<String> criminalidList,Connection localconn,String strDdcId, String remoteDepartid);
	
	/**
	 * 提交到法院
	 * @param bean
	 * @return
	 */
	public boolean dataSubmitToFrontMachine(HttpServletRequest request,String fileids);
	
	/**
	 * @Description: 获取前置机数据并组包
	 * @param
	 * @return boolean  
	 * @throws
	 * @author 杨祖榕
	 * @date 2016年6月3日  下午3:21:59
	 * @Version 3.1
	 */
	public String getPackageAndMergeZip(SystemUser user,HttpServletRequest request);
	
	public boolean localSaveFileData(DbmsNewDataExportBean bean, Map nodeMap);


	public boolean batchUnzip(HttpServletRequest request,String fileid);
	
	public void unzipFile(String filenameNoSuffix,String zipFilePath,String unzipdata) throws IOException;
	
}
