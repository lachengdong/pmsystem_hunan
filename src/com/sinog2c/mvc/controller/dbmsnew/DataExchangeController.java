package com.sinog2c.mvc.controller.dbmsnew;

import java.sql.Connection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sinog2c.model.dbmsnew.DbmsNewDataExportBean;
import com.sinog2c.model.dbmsnew.TaskBean;
import com.sinog2c.model.system.SystemUser;
import com.sinog2c.mvc.controller.base.ControllerBase;
import com.sinog2c.mvc.message.JSONMessage;
import com.sinog2c.service.api.dbmsnew.DbmsDatabaseNewService;
import com.sinog2c.service.api.dbmsnew.DbmsNewDataExportService;
import com.sinog2c.util.common.JdbcConnUtil;
import com.sinog2c.util.common.StringNumberUtil;

/**
 * 数据交换使用的数据库连接配置信息管理
 */
@Controller
@RequestMapping("/dbms")
public class DataExchangeController extends ControllerBase {

	@Autowired
	private DbmsDatabaseNewService dbmsDatabaseNewService;
	@Autowired
	private DbmsNewDataExportService dataExportService;
	
	/**
	 * 数据库交换,页面
	 */
	@RequestMapping(value = "/dataexchangemanage.page")
	public String dataexchangemanage() {
		return "dbms/dataexchangemanage";
	}
	/**
	 * 数据库交换——列表
	 */	
	@RequestMapping(value = "/ajax/listdataexchange.json")
	@ResponseBody
	public Object listdataexchange(HttpServletRequest request) {
		//SystemUser user = getLoginUser(request);//获取当前登录的用户
		//String departid=user.getDepartid();//根据用户Id获取所在部门Id
		HashMap<String, Object> map = new HashMap<String, Object>();
		//map.put("sdid", departid);
		List<Map<String, Object>> databaseList = dbmsDatabaseNewService.listDBConnMapByPage(map);
		
		//
		int total = 0;
		if(null != databaseList){
			total = databaseList.size();
		}
		JSONMessage message = new JSONMessage();
		message.setData(databaseList);
		message.setTotal(total);
		
		return message;
	}

	/**
	 * 数据导入——列表
	 */	
	@RequestMapping(value = "/ajax/queryprogress.json")
	@ResponseBody
	public Object queryProgressByUUID(HttpServletRequest request) {
		//SystemUser user = getLoginUser(request);//获取当前登录的用户
		//String departid=user.getDepartid();//根据用户Id获取所在部门Id
		Map<String, Object> map = parseParamMap(request);
		
		//
		String uuid = (String)map.get("queryuuid");
		//
		TaskBean taskBean = taskBeanMap.get(uuid);
		//
		JSONMessage message = new JSONMessage();
		message.setSuccess();
		
		if(null != taskBean){
			message.setSuccess();
			message.addMeta("taskbean", taskBean);
			// 如果已经处理成功,则清理掉对象
			// ...
		} else {
			// 空。。。可能是执行完毕 
			// 去数据库查询
			// ...
		}
		
		return message;
	}
	/**
	 * 手动交换
	 */	
	@RequestMapping(value = "/ajax/calldataexchange.json")
	@ResponseBody
	public Object addDataExport(HttpServletRequest request) {
		final SystemUser user = getLoginUser(request);//获取当前登录的用户
		//String departid=user.getDepartid();//根据用户Id获取所在部门Id
		//
		Map<String, Object> paraMap = parseParamMap(request);

		boolean validOK = true;
		String validError = "输入错误";
		
		JSONMessage message = new JSONMessage();
		String info = "执行失败";
		// ddcid
		String ddcid = (String)paraMap.get("ddcid");
		String manual = (String)paraMap.get("manual");
		String condition = (String)paraMap.get("condition");
		String insertonly = (String)paraMap.get("insertonly");
		// 查询UUID,KEY
		String queryuuid = (String)paraMap.get("queryuuid");
		final String isCallPrec = (String)paraMap.get("isCallPrec");//是否执行存储过程
		//String operatetype = GkzxCommon.FAYUAN;
		// 还有其他参数
		//
		if(StringNumberUtil.isEmpty(ddcid)){
			//
			if(StringNumberUtil.notEmpty(manual)){
				return manualDataExport(request); // 返回手动执行
			} else {
				//
				validOK = false;
				validError = "参数错误: ddcid";
			}
		}
		if(StringNumberUtil.notEmpty(queryuuid)){
			// 将 queryuuid 作为key,存放进度
			//validOK = false;
			//validError = "参数错误: ddcid";
		}
		//
		if(validOK){
			//
			//
			// 如果提交法院
			// bean.setOperatetype(operatetype);
			//

			// 每次生成一个
			final DbmsNewDataExportBean bean = new DbmsNewDataExportBean();
			bean.setDdcid(ddcid);
			bean.setInsertonly(insertonly);
			bean.setCondition(condition);
			bean.setHiddencon(null);
			//dataExport.setBean(bean);
			boolean success = true;//

			final TaskBean taskBean = new TaskBean();
			taskBeanMap.put(queryuuid, taskBean);
			//
			Thread thread = 
			new Thread(new Runnable() {
				@Override
				public void run() {
					executeDataInterchange(bean, taskBean, user, isCallPrec);
				}
			},"[执行数据交换线程, ddcid="+ddcid+"]");
			// 设置后台进程,不影响关闭
			thread.setDaemon(true);
			//
			thread.start();
			try {
			} catch (Exception e) {
				success = false;
				info = e.getMessage();
			}
			//
			if(success){
				info = "已成功添加数据交换作业";
				message.setSuccess();
			}
			//
			message.setInfo(info);
		} else {
			message.setInfo(validError);
		}
		//
		return message;
	}
	

	// 手动执行数据交换
	public Object manualDataExport(HttpServletRequest request) {
		//SystemUser user = getLoginUser(request);//获取当前登录的用户
		//String departid=user.getDepartid();//根据用户Id获取所在部门Id
		//
		Map<String, Object> paraMap = parseParamMap(request);

		boolean validOK = true;
		String validError = "输入错误";
		
		JSONMessage message = new JSONMessage();
		String info = "执行失败";
		// ddcid
		
		String manual = (String)paraMap.get("manual");
		//String condition = (String)paraMap.get("condition");
		//String insertonly = (String)paraMap.get("insertonly");
		// 查询UUID,KEY
		String queryuuid = (String)paraMap.get("queryuuid");
		//String operatetype = GkzxCommon.FAYUAN;
		// 还有其他参数
		//
		if(StringNumberUtil.isEmpty(manual)){
			validOK = false;
			validError = "参数错误: manual";
		}
		if(StringNumberUtil.notEmpty(queryuuid)){
			// 将 queryuuid 作为key,存放进度
			//validOK = false;
			//validError = "参数错误: ddcid";
		}
		//
		if(validOK){
			//
			//
			// 如果提交法院
			// bean.setOperatetype(operatetype);
			//
			final TaskBean taskBean = new TaskBean();
			taskBeanMap.put(queryuuid, taskBean);
			// 每次生成一个
			//dataExport.setBean(bean);
			boolean success = true;
			new Thread(new Runnable() {
				@Override
				public void run() {
					manualExecuteDataInterchange(taskBean);
				}
			},"[手动执行全部数据交换线程]").start();
			try {
			} catch (Exception e) {
				success = false;
				info = e.getMessage();
			}
			//
			if(success){
				info = "已成功添加数据交换作业";
				message.setSuccess();
			}
			//
			message.setInfo(info);
		} else {
			message.setInfo(validError);
		}
		//
		return message;
	}
	
	//
	private void executeDataInterchange(DbmsNewDataExportBean bean, TaskBean taskBean, SystemUser user, String isCallPrec){

		DbmsNewDataExportService dataExport = dataExportService.getNewInstance();
		
		String departid = user.getPrisonid();
		if(StringNumberUtil.isEmpty(departid)){
			departid = user.getOrgid();
		}
		try {
			//
			taskBean.setStatus(TaskBean.STATUS_INIT);
			//
			dataExport.prisonDataExchange(bean,taskBean);

			if(isCallPrec!=null && "true".equals(isCallPrec)){
				// 调用存储过程分发数据
				Connection conn =  JdbcConnUtil.getConn();
				try {
					log("开始调用数据交换存储过程。。。");
					taskBean.setStatus(TaskBean.STATUS_CALLING);
					boolean rest = dataExport.callInterchangeProcedure(conn, departid);
					if(rest){
						taskBean.setStatus(TaskBean.STATUS_SUCCESS);
					} else {
						taskBean.setStatus(TaskBean.STATUS_FAILURE);
					}
					log("数据交换存储过程执行" + (rest ? "成功" : "失败"));
				} catch (Exception e) {
					log("调用存储过程失败!");
					e.printStackTrace();
				} finally {
					JdbcConnUtil.close(conn);
					conn = null;
				}
			}
		} catch (Exception e) {
		}
	}
	// 
	private void manualExecuteDataInterchange(TaskBean taskBean){
		DbmsNewDataExportService dataExport = dataExportService.getNewInstance();
		try {
			dataExport.autoPrisonDataExchange();
		} catch (Exception e) {
		}
	};
}
