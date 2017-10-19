package com.sinog2c.mvc.controller.dbmsnew;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sinog2c.model.dbmsnew.DbmsDatabaseNew;
import com.sinog2c.model.system.SystemUser;
import com.sinog2c.mvc.controller.base.ControllerBase;
import com.sinog2c.mvc.message.JSONMessage;
import com.sinog2c.service.api.dbmsnew.DbmsDatabaseNewService;
import com.sinog2c.util.common.MapUtil;
import com.sinog2c.util.common.StringNumberUtil;
import com.sinog2c.util.dbms.DBMSMetaUtil;

/**
 * 数据交换使用的数据库连接配置信息管理
 */
@Controller
@RequestMapping("/dbms")
public class DBConnConfigController extends ControllerBase {

	@Resource
	private DbmsDatabaseNewService dbmsDatabaseNewService;
	
	/**
	 * 数据库管理,页面
	 */
	@RequestMapping(value = "/dbconnconfigmanage.page")
	public String databaseConnectionConfigManage() {
		return "dbms/dbconnconfigmanage";
	}
	/**
	 *数据交换行文，页面
	 */
	@RequestMapping(value = "/exchangetextmanage.page")
	public String exchangetextmanage() {
		return "dbms/exchangetextmanage";
	}
	@RequestMapping(value="/servicesManage.page")
	public String servicesManage(){
		return "dbms/servicesManage";
	}
	/**
	 * 数据交换行文——列表,json
	 */	
	@RequestMapping(value = "/ajax/listexchangetext.json")
	@ResponseBody
	public Object listExchangeText(HttpServletRequest request) {
		SystemUser user = getLoginUser(request);//获取当前登录的用户
		int pageIndex =(Integer)(request.getParameter("pageIndex")==null? "":Integer.parseInt(request.getParameter("pageIndex")));
		int pageSize = (Integer)(request.getParameter("pageSize")==null? "":Integer.parseInt(request.getParameter("pageSize")));  
		Map<String, Object> map = parseParamMap(request);
		int start = pageIndex * pageSize + 1;
		int end = start + pageSize -1;
		map.put("start", String.valueOf(start));
    	map.put("end",String.valueOf(end));
    	map.put("departid",user.getDepartid());
		List<Map> databaseList = dbmsDatabaseNewService.listByOrgid(map);
		int total = dbmsDatabaseNewService.listByOrgidCount(map);
		JSONMessage message = new JSONMessage();
		message.setData(databaseList);
		message.setTotal(total);
		return message;
	}
	/**
	 * 数据库管理——列表,json
	 */	
	@RequestMapping(value = "/ajax/listdbconnconfig.json")
	@ResponseBody
	public Object listDBConnConfig(HttpServletRequest request) {
		SystemUser user = getLoginUser(request);//获取当前登录的用户
		//String departid=user.getDepartid();//根据用户Id获取所在部门Id
		Map<String, Object> map = parseParamMap(request);
		//map.put("sdid", departid);
		int total = dbmsDatabaseNewService.countByCondition(map, user);
		List<DbmsDatabaseNew> databaseList = dbmsDatabaseNewService.listByCondition(map, user);
		
		JSONMessage message = new JSONMessage();
		message.setData(databaseList);
		message.setTotal(total);
		
		return message;
	}

	/**
	 * 列出所有数据库——列表,json
	 */	
	@RequestMapping(value = "/ajax/listalldb.json")
	@ResponseBody
	public Object listAllDB(HttpServletRequest request) {
		SystemUser user = getLoginUser(request);//获取当前登录的用户
		//String departid=user.getDepartid();//根据用户Id获取所在部门Id
		//Map<String, Object> map = parseParamMap(request);
		//map.put("sdid", departid);
		int total = dbmsDatabaseNewService.countAll(user);
		List<DbmsDatabaseNew> databaseList = dbmsDatabaseNewService.listAll(user);
		
		JSONMessage message = new JSONMessage();
		message.setData(databaseList);
		message.setTotal(total);
		
		return message;
	}

	@RequestMapping(value = "/ajax/savedbconnconfig.json")
	@ResponseBody
	public Object saveDatabaseConnConfig(HttpServletRequest request) {
		SystemUser user = getLoginUser(request);//获取当前登录的用户
		String userid = user.getUserid();
		//String sdid = user.getOrgid();
		//String departid=user.getDepartid();//根据用户Id获取所在部门Id
		Map<String, Object> map = parseParamMap(request);
		//
		Date current = new Date();
		int islocalnew = getParameterInt(request, "islocalnew", 0);
		int rows = 0;
		try{
			if(islocalnew > 0){
				map.put("delflg", 0);
				//map.put("sdid", sdid); // 监狱部门, 由业务层处理
				map.put("createmender", userid);
				map.put("createtime", current);
				map.put("updatemender", userid);
				map.put("updatetime", current);
				rows = dbmsDatabaseNewService.insertByMap(map, user);
			} else {
				map.put("updatemender", userid);
				map.put("updatetime", current);
				rows = dbmsDatabaseNewService.updateByMap(map, user);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		JSONMessage message = new JSONMessage();
		message.setStatus(rows);
		message.setInfo(rows>0?"操作成功33":"操作失败");
		
		return message;
	}
	
	/**
	 * 新增行文交换的数据保存
	 * 
	 */
	@RequestMapping(value = "/ajax/saveexchangetext.json")
	@ResponseBody
	public Object saveExchangeText(HttpServletRequest request) {
		
		Map<String, Object> map = parseParamMap(request);
		int rows = 0;
		JSONMessage message = new JSONMessage();
		int count  = dbmsDatabaseNewService.listByOrgidCount(map);
		if(count!=0){
			rows = 0;
			message.setStatus(rows);
			rows = dbmsDatabaseNewService.updateByExchangeWritingMap(map);
//			message.setInfo("保存失败，数据重复！");
		}else{
			rows = dbmsDatabaseNewService.insertByOrgid(map);
			message.setStatus(rows);
			message.setInfo(rows>0?"操作成功44":"操作失败");
		}
		return message;
	}
	

	@RequestMapping(value = "/ajax/testdbconfiglink.json")
	@ResponseBody
	public Object testdbconfiglink(HttpServletRequest request) {
		//Map<String, Object> map = parseParamMap(request);
		//
		boolean result = false;
		String info = "测试失败!";
		//
		//String ddid = getParameter(request, "ddid");
		//
		String ddip = getParameter(request, "ddip");
		String port = getParameter(request, "port");
		String databasename = getParameter(request, "databasename");
		String databaseuser = getParameter(request, "databaseuser");
		String databasepassword = getParameter(request, "databasepassword");
		String databasetype = getParameter(request, "databasetype");
		//
		if(false == StringNumberUtil.notEmpty(ddip)){
			info = "ddip参数必须上传!";
		} else if(false == StringNumberUtil.notEmpty(port)){
			info = "port参数必须上传!";
		} else if(false == StringNumberUtil.notEmpty(databasename)){
			info = "databasename参数不能为空!";
		} else if(false == StringNumberUtil.notEmpty(databaseuser)){
			info = "databaseuser参数不能为空!";
		} 
		else {
			// 直接查询
			result = DBMSMetaUtil.TryLink(databasetype, ddip, port, databasename, databaseuser, databasepassword);
			if(result){
				info = "测试成功!";
			}
		} 
//		else {
//			info = "databasepassword参数不能为空!";
//			// 后期修正,可以根据ID取出数据进行检测.
//			// TO DO
//		}
		
		JSONMessage message = new JSONMessage();
		message.setStatus(result?1:0);
		message.setInfo(info);
		
		return message;
	}
}
