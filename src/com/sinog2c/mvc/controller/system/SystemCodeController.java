package com.sinog2c.mvc.controller.system;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.sinog2c.model.system.SystemUser;
import com.sinog2c.model.system.TbsysCode;
import com.sinog2c.mvc.controller.base.ControllerBase;
import com.sinog2c.mvc.message.JSONMessage;
import com.sinog2c.service.api.system.SystemCodeService;
import com.sinog2c.service.api.system.SystemLogService;
import com.sinog2c.util.common.JsonUtil;
import com.sinog2c.util.common.StringNumberUtil;

/**
 * 系统代码相关
 */
@Controller
@RequestMapping("/syscode")
public class SystemCodeController extends ControllerBase{

	@Autowired
	public SystemLogService logService;
	@Autowired
	public SystemCodeService codeService;

	/**
	 * 显示代码信息页面
	 */
	@RequestMapping("/manage.page")
	public ModelAndView manageUser(HttpServletRequest request, HttpServletResponse response) {
		//资源对象
		returnResourceMap(request);
		ModelAndView mav = new ModelAndView("system/syscode/manage");

		return mav;
	}
	
	/**
	 * 显示代码信息页面
	 */
	@RequestMapping("/toOperateSysConfigPage.page")
	public ModelAndView toOperateSysConfigPage(HttpServletRequest request, HttpServletResponse response) {
		//资源对象
//		returnResourceMap(request);
		Map<String, Object> map = parseParamMap(request);
		
		String name = request.getParameter("name");
    	String remark = request.getParameter("remark");
    	try {
    		if(StringNumberUtil.notEmpty(name)){
    			name = URLDecoder.decode(name,"UTF-8");
    			map.put("name", name);
    		}
    		if(StringNumberUtil.notEmpty(remark)){
    			remark = URLDecoder.decode(remark,"UTF-8");
    			map.put("remark", remark);
    		}
			
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		super.addMap2Attribute(map, request);
		
		ModelAndView mav = new ModelAndView("system/syscode/operateSysConfigPage");

		return mav;
	}

	@RequestMapping(value = "/ajax/list.json")
	@ResponseBody
	public Object listByPage(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
	
		//
		Map<String, Object> map = parseParamMap(request);

		//// 总数
		int total = codeService.countByCondition(map);
		// 分页得到的数据;
		List<TbsysCode> list = codeService.listByCondition(map);
		//
		JSONMessage message = JSONMessage.newMessage();
		//
		message.setTotal(total); 
		message.setSuccess();
		message.setData(list);
		
		//
		return message;
	}
	@RequestMapping(value = "/ajax/listAll.json")
	@ResponseBody
	public Object listAll(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {

		Map<String, Object> map = parseParamMap(request);

		// 分页得到的数据;
		List<TbsysCode> list = codeService.listAllByCondition(map);
		JSONMessage message = JSONMessage.newMessage();

		message.setSuccess();
		message.setData(list);
		
		//
		return message;
	}

	/**
	 * 保存,返回值为 json
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/ajax/savecode.json")
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
				rows = codeService.insertByMap(map);
			} else {
				map.put("updatemender", userid);
				map.put("updatetime", new Date());
				rows = codeService.updateByMap(map);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		JSONMessage message = new JSONMessage();
		message.setStatus(rows);
		message.setInfo(rows>0?"操作成功77":"操作失败");
		
		return message;
	}
	
	@RequestMapping(value = "/ajax/getSysCodeByType.json")
	@ResponseBody
	public List<TbsysCode> getSysCodeByType(HttpServletRequest request,
			@RequestParam("codetype") String codetype) {
		return this.codeService.listByCodetype(codetype);
	}
	
	
	@RequestMapping(value = "/toSysConfigDetailPage.page")
	public ModelAndView toSysConfigDetailPage(HttpServletRequest request){
//		SystemUser user = getLoginUser(request);
		Map map = this.parseParamMap(request);
		super.addMap2Attribute(map, request);
		//
//		ModelAndView mav  = new ModelAndView("examine/editSanrenhujianPage");
		ModelAndView mav  = new ModelAndView("system/syscode/sysConfigDetailPage");
		return mav;
	}
	
	
	@RequestMapping(value = "/getSyscodeDetailList.json")
	@ResponseBody
	public Object getSyscodeDetailList(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {

		Map<String, Object> map = parseParamMap(request);
		//// 总数
		int total = 0;// codeService.countSyscodeDetailList(map);
		// 分页得到的数据;
		List<Map<String, Object>> list =null; //codeService.getSyscodeDetailList(map);
		//
		JSONMessage message = JSONMessage.newMessage();
		//
		message.setTotal(total); 
		message.setSuccess();
		message.setData(list);
		return message;
		
	}
	
	@RequestMapping(value = "/saveSysconfigDetail.json")
	@ResponseBody
	public Object saveSysconfigDetail(HttpServletRequest request){
		SystemUser user = this.getLoginUser(request);
		List<Map<String,Object>> griddataList = null;
		
		String griddata = request.getParameter("griddata");
		if(null != griddata){
			griddataList = (List) JsonUtil.Decode(griddata);
		}
		
		return null;//codeService.saveSysconfigDetail(user, griddataList);
		
	}

}
