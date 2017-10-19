package com.sinog2c.mvc.controller.dbmsnew.old;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sinog2c.model.system.SystemUser;
import com.sinog2c.mvc.controller.base.ControllerBase;
import com.sinog2c.service.api.dbmsnew.DbmsDatabaseNewService;

/**
 * 项目名称：pmsys
 * DBMS 数据导入导出 相关页面的映射关系
 * @author luanxuefeng 2014-8-12 12:32:59
 * @author renfufei 2014-08-26
 */
@Controller
@RequestMapping("/dbms")
public class DBMSPageController extends ControllerBase {

	//@Resource
	//private DbmsDatabaseNewService dbmsDatabaseNewService;
	

	/**
	 * 数据库管理
	 * @author zgl 2014-8-15 9:32:59
	 */
	@RequestMapping(value = "/getConnectionMessageList.page")
	public String toDatabaseMamagePage() {
		return "dbmsnew/databaseconnectionmessagelist";
	}
	/**
	 * 数据库管理——增加
	 */	
	@RequestMapping(value = "/addConnectionMessage.page")
	public String toDatabaseManageAddPage() {
		return "dbmsnew/adddatabaseconnectionmessage";
	}
	/**
	 * 数据库管理——批量删除
	 */	
//	@RequestMapping(value = "/deleteConnectionMessageList.page")
//	public String toDatabaseManageBatchDeletePage() {
//		return "dbmsnew/getConnectionMessageList";
//	}
	/**
	 * 数据库管理——列表页
	 */	
	@RequestMapping(value = "/showDatabaseInfo.action")
	@ResponseBody
	public HashMap<String, Object> showDatabaseInfo(HttpServletRequest request) {
		SystemUser user = getLoginUser(request);//获取当前登录的用户
		String departid=user.getDepartid();//根据用户Id获取所在部门Id
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("sdid", departid);
		List<Map> databaseList = null;//dbmsDatabaseNewService.getConnectionMessageForPage(map);
		HashMap<String, Object> mapList = new HashMap<String, Object>();
		mapList.put("data", databaseList);
		return mapList;
	}
	/**
	 * 方案管理
	 * @author zgl 2014-8-15 9:32:59
	 */
	@RequestMapping(value = "/showSchemeList.page")
	public String toSchemaMamagePage() {
		return "dbmsnew/databaSchemeList";
	}
	/**
	 * 代码管理
	 */	
	@RequestMapping(value = "/getCodeManagesList.page")
	public String toCodeMamagePage() {
		return "dbmsnew/codechememanages";
	}
	
	/************************************************************
	 * 跳转数据管理tabs主列表页
	 * @author zgl 2014-8-15 9:32:59
	 * 以下为数据管理Controller
	 ************************************************************/
	@RequestMapping(value = "/toDataManageTabPage.action")
	public String toDataManageTabPage() {
		return "dbmsnew/dbmsNewDataInterchange/showDataInfo";
	}
	/**
	 * 数据导出
	 */	
	@RequestMapping(value = "/toDataExportPage.page")
	public String toDataExportPage() {
		return "dbmsnew/dbmsNewExport/exportDatabaseXML";
	}
	/**
	 * 数据导入
	 */	
	@RequestMapping(value = "/toDataImportPage.page")
	public String toDataImportPage() {
		return "dbmsnew/dbmsNewImport/dataImport";
	}
	/**
	 * 数据交换
	 */	
	@RequestMapping(value = "/toDataSwapPage.page")
	public String toDataSwapPage() {
		return "dbmsnew/dbmsNewDataInterchange/dataInterchangeIndex";
	}

}
