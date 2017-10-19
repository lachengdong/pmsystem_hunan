package com.sinog2c.mvc.controller.dbmsnew;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sinog2c.model.dbmsnew.DbmsCodeChemeNew;
import com.sinog2c.model.dbmsnew.DbmsCodeTypeNew;
import com.sinog2c.model.system.SystemUser;
import com.sinog2c.mvc.controller.base.ControllerBase;
import com.sinog2c.mvc.message.JSONMessage;
import com.sinog2c.service.api.dbmsnew.DbmsCodeChemeNewService;
import com.sinog2c.service.api.dbmsnew.DbmsCodeTypeNewService;
import com.sinog2c.util.common.StringNumberUtil;

/**
 * 数据交换使用的数据库连接配置信息管理
 */
@Controller
@RequestMapping("/dbms")
public class CodeSchemeController extends ControllerBase {

	@Autowired
	private DbmsCodeTypeNewService codeTypeNewService;
	@Autowired
	private DbmsCodeChemeNewService codeChemeNewService;
	
	/**
	 * 代码类型管理,页面
	 */
	@RequestMapping(value = "/codetypemanage.page")
	public String codeTypeManage() {
		return "dbms/codetypemanage";
	}
	/**
	 * 代码类型映射细节,页面
	 */
	@RequestMapping(value = "/showcodemappingbytype.page")
	public String showCodeMappingByType(HttpServletRequest request) {
		// 页面不处理,直接由JSON处理
		//
		return "dbms/codetypemapping";
	}
	/**
	 * 代码映射细节管理; 这个要使用吗?
	 * @return
	 */
	@RequestMapping(value = "/codemanage.page")
	public String codeSchemeManage() {
		return "dbms/codemanage";
	}
	/**
	 * 代码类型管理——列表
	 */
	@RequestMapping(value = "/ajax/listcodetype.json")
	@ResponseBody
	public Object ajaxlistcodetype(HttpServletRequest request) {
		Map<String, Object> map = parseParamMap(request);
		//map.put("sdid", departid);
		int total = codeTypeNewService.countByCondition(map);
		//map.put("sdid", departid);
		List<DbmsCodeTypeNew> codeTypeList = codeTypeNewService.listByCondition(map);
		
		//
		JSONMessage message = new JSONMessage();
		message.setData(codeTypeList);
		message.setTotal(total);
		
		return message;
	}
	/**
	 * 保存数据方案,返回值为 json
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/ajax/savecodetype.json")
	@ResponseBody
	public Object savecodetype(HttpServletRequest request) {
		SystemUser user = getLoginUser(request);//获取当前登录的用户
		String userid = user.getUserid();
		//String departid=user.getDepartid();//根据用户Id获取所在部门Id
		Map<String, Object> map = parseParamMap(request);
		//
		int islocalnew = getParameterInt(request, "islocalnew", 0);
		int rows = 0;
		try{
			if(islocalnew > 0){
				map.put("delflg", 0);
				map.put("createmender", userid);
				map.put("createtime", new Date());
				rows = codeTypeNewService.insertByMap(map);
			} else {
				map.put("updatemender", userid);
				map.put("updatetime", new Date());
				rows = codeTypeNewService.updateByMap(map);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		JSONMessage message = new JSONMessage();
		message.setStatus(rows);
		message.setInfo(rows>0?"操作成功22":"操作失败");
		
		return message;
	}
	
	/**
	 * 代码映射管理——列表
	 */
	@RequestMapping(value = "/ajax/listcodescheme.json")
	@ResponseBody
	public Object ajaxlistcodescheme(HttpServletRequest request) {
		Map<String, Object> map = parseParamMap(request);
		
		int total = codeChemeNewService.countByCondition(map);
		//map.put("sdid", departid);
		List<DbmsCodeChemeNew> codeTypeList = codeChemeNewService.listByCondition(map);
		
		JSONMessage message = new JSONMessage();
		message.setData(codeTypeList);
		message.setTotal(total);
		
		return message;
	}
	@RequestMapping(value = "/ajax/alllistcodescheme.json")
	@ResponseBody
	public Object alllistcodescheme(HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		String codetypeid = request.getParameter("codetypeid");		
		map.put("codetypeid", codetypeid);
		List<DbmsCodeChemeNew> codeTypeList = codeChemeNewService.alllistcodescheme(map);
		JSONMessage message = new JSONMessage();
		message.setData(codeTypeList);
		return message;
	}
	

	/**
	 * 下一个id,返回的是单个值映射关系的自增ID，不是方案ID
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/ajax/getnextcodeschemeid.json")
	@ResponseBody
	public Object getNextCodeschemeId(HttpServletRequest request, HttpServletResponse response) {
		
	
		// 调用业务方法
		int nextid = 0;
		
		try{
			nextid = codeChemeNewService.getNextId();
		} catch (Exception e) {
		}
		
		if(nextid < 1){
			try{
				nextid = codeChemeNewService.getNextId();
			} catch (Exception e) {
				e.printStackTrace();
			}
			// 提示成功
		}
		//
		JSONMessage message = JSONMessage.newMessage();
		if(nextid > 1){
			message.setSuccess();
			//
			//DecimalFormat format = new DecimalFormat("00000");
			message.addMeta("nextid", nextid);
		}
		// 
		
		//
		return message;
	}
	/**
	 * 保存单个映射代码记录,返回值为 json
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/ajax/savecodescheme.json")
	@ResponseBody
	public Object savecodescheme(HttpServletRequest request) {
		SystemUser user = getLoginUser(request);//获取当前登录的用户
		String userid = user.getUserid();
		//String departid=user.getDepartid();//根据用户Id获取所在部门Id
		Map<String, Object> map = parseParamMap(request);
		//
		int islocalnew = getParameterInt(request, "islocalnew", 0);
		int rows = 0;
		try{
			if(islocalnew > 0){
				map.put("delflg", 0);
				map.put("createmender", userid);
				map.put("createtime", new Date());
				rows = codeChemeNewService.insertByMap(map);
			} else {
				map.put("updatemender", userid);
				map.put("updatetime", new Date());
				rows = codeChemeNewService.updateByMap(map);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		JSONMessage message = new JSONMessage();
		message.setStatus(rows);
		message.setInfo(rows>0?"操作成功22":"操作失败");
		
		return message;
	}
}
