package com.sinog2c.mvc.controller.system;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.gkzx.common.GkzxCommon;
import com.gkzx.util.property.GetProperty;
import com.sinog2c.model.officeAssistant.TbuserNotice;
import com.sinog2c.model.system.SystemOrganization;
import com.sinog2c.model.system.SystemResource;
import com.sinog2c.model.system.SystemUser;
import com.sinog2c.mvc.controller.base.ControllerBase;
import com.sinog2c.service.api.flow.FlowDeliverService;
import com.sinog2c.service.api.officeAssistant.UserNoticeService;
import com.sinog2c.service.api.system.SystemCodeService;
import com.sinog2c.service.api.system.SystemMainService;
import com.sinog2c.service.api.system.SystemOrganizationService;
import com.sinog2c.service.api.system.SystemUserService;
import com.sinog2c.util.common.DateUtil;
import com.sinog2c.util.common.StringNumberUtil;

/**
 * Main相关控制器,类似于 Struts中的Action<br/>
 * 非线程安全, 单例
 */
@Controller
public class SystemMainController extends ControllerBase{
	
	@Resource
	private UserNoticeService userNoticeService;
	@Resource
	private SystemCodeService systemCodeService;
	@Resource
	private SystemMainService systemMainService;
	@Resource
	private SystemOrganizationService systemorganizationservice;
	@Resource
	private SystemUserService systemUserService;
	@Autowired
	private FlowDeliverService flowDeliverService;
	
	
	
	/**
	 * 主页面
	 * 
	 * @return
	 */
	@RequestMapping(value = { "/main.action", "/home" })
	public ModelAndView home(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mav = new ModelAndView("main/sz_main");
		
		SystemUser systemUser = (SystemUser) getSessionAttribute(request, ControllerBase.SESSION_USER_KEY); 
		if(null == systemUser){
			// 失败,应该已经被拦截器拦截了
		}else{
			Properties jypro = new GetProperty().bornProp(GkzxCommon.DATABASETYPE, null);
			if(jypro!=null){
				String provincecode = jypro.getProperty("provincecode");
				if("fy".equals(provincecode)){//法院登录页面
					mav = new ModelAndView("main/court_main");
				}
				if("6100".equals(provincecode)){//陕西登录页面
					if(!StringNumberUtil.isNullOrEmpty(systemUser)){
						String jailname = systemorganizationservice.getOrginfoByOrgid(systemUser.getDepartid()).getName();
						request.setAttribute("departname", jailname);
					}
					mav = new ModelAndView("main/sx_main");
				}
			}
		}
		
		//用户通知相关
		List<TbuserNotice> noticeContent =  new ArrayList<TbuserNotice>();
		TbuserNotice tbuserNotice = null;
		noticeContent =  userNoticeService.getAlluserNotice(3, 0, systemUser.getUserid());
		if(noticeContent.size()>0) {
			tbuserNotice = noticeContent.get(0);
		}
		
		// 添加属性、JSP页面上使用
		mav.addObject("user", systemUser);
		//mav.addObject("noticeList", noticeList);
		mav.addObject("tbuserNotice", tbuserNotice);
		//request.setAttribute("user", systemUser);  // mav 已经自动设置到 request属性中了。
		
		// start modify by blue_lv 2015-07-15
		// 进入触摸屏界面
		if (systemUser.getText1() != null
				&& systemUser.getText1().equalsIgnoreCase("app")) {
			mav = new ModelAndView("main/app_main");
		}
		
		// end modify by blue_lv 2015-07-15
		
		// 添加属性、JSP页面上使用
		mav.addObject("user", systemUser);
		// mav.addObject("noticeList", noticeList);
//		mav.addObject("tbuserNotice", tbuserNotice);
		// request.setAttribute("user", systemUser); // mav 已经自动设置到 request属性中了。

		// start modify by blue_lv 2015-07-15
		String path = request.getContextPath();
		String basePath = request.getScheme() + "://" + request.getServerName()
				+ ":" + request.getServerPort() + path + "/";

		SystemOrganization org = systemUser.getOrganization();
		String orgNameStr = "";
		if (null != org) {
			orgNameStr = org.getName();
		}

		mav.addObject("path", path);
		mav.addObject("basePath", basePath);
		mav.addObject("orgNameStr", orgNameStr);
		// end modify by blue_lv 2015-07-15
		return mav;
	}
	/**
	 * 桌面
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/desktop")
	public ModelAndView desktop(HttpServletRequest request, HttpServletResponse response) {
		Properties jyconfig = new GetProperty().bornProp(GkzxCommon.DATABASETYPE, null);
		String ischeckseal = (jyconfig.getProperty("ischeckseal")== null?"":jyconfig.getProperty("ischeckseal"));
		ModelAndView mav = null;
		request.setAttribute("ischeckseal", ischeckseal);
		if(jyconfig!=null){
			String provincecode = jyconfig.getProperty("provincecode").trim();
			if("fy".equals(provincecode)){//法院desktop
				mav = new ModelAndView("main/desktop_court");
			}else if("3700".equals(provincecode)){
				mav = new ModelAndView("main/desktop_SD");//分离山东的代办桌面
			}else if("GK".equals(provincecode)){
				mav = new ModelAndView("main/desktop_OA");//分离OA的代办桌面
			}else{
				mav = new ModelAndView("main/newDeskTop");
//				mav = new ModelAndView("main/desktop");
			}
		}
		return mav;
	}
	/**
	 * 桌面
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/publicMainAction")
	public ModelAndView publicMainAction(HttpServletRequest request, HttpServletResponse response) {
		// 权宜
		ModelAndView mav = new ModelAndView("res/resourcecrud");

		return mav;
	}
	
	/**
	 * 模拟请求返回结果, TODO 需要修正
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = { "/getMessage" })
	@ResponseBody
	public Object getMessage(HttpServletRequest request, HttpServletResponse response) {

		List<String> list = new ArrayList<String>();
		return list;
	}
	
	/**
	 * 模拟请求返回结果, TODO 需要修正
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = { "/getDateUPDownArea" })
	@ResponseBody
	public Object getDateUPDownArea(HttpServletRequest request, HttpServletResponse response) {

		Date date = new Date();
		String nowYear = DateUtil.dateFormat(date,"yyyy");
		int year = StringNumberUtil.getIntByString(nowYear.trim());
		List<Map<String, Integer>> list = new ArrayList<Map<String, Integer>>();
		for(int i=year-1;i<= year+1;i++){
			Map<String, Integer> map = new HashMap<String, Integer>();
			map.put("id", i);
			map.put("text", i);
			list.add(map);
		}
		return list;
	}
	
	
	/**
	 * 获取当前监区中的分监区长用户
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = { "/getCurrDepartPersons.json" })
	@ResponseBody
	public Object getCurrDepartPersons(HttpServletRequest request) {
		SystemUser user = this.getLoginUser(request);
		Map<String,Object> paramap = new HashMap<String,Object>();
		paramap.put("orgid", user.getOrgid());
		paramap.put("isfjquser", 1);
		List<SystemUser> userList = systemUserService.getUsersByOrgid(paramap);
		
		List<Map<String,String>> list = new ArrayList<Map<String,String>>();
		if(null != userList && userList.size()>0){
			for(SystemUser su : userList){
				if(su.getUserid().equals(user.getUserid())){
					continue;
				}
				//
				Map<String, String> map = new HashMap<String, String>();
				map.put("id", su.getUserid());
				map.put("text", su.getName());
				list.add(map);
			}
		}
		return list;
	}
	
	/**
	 * @describe 已办事项的案件类型
	 * @author YangZR
	 * @return
	 */
	@RequestMapping( "/getDoneCaseType.json" )
	@ResponseBody
	public Object getDoneCaseType(HttpServletRequest request) {
		
		SystemUser user = this.getLoginUser(request);
		Map map = new HashMap();
		map.put("userid", user.getUserid());
		map.put("departid", user.getDepartid());
		return systemMainService.getDoneCaseType(map);
		
	}
	
	/**
	 * 模拟请求返回结果, TODO 需要修正
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = { "/getCaseTypes" })
	@ResponseBody
	public Object getCaseTypes(HttpServletRequest request, HttpServletResponse response) {

		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		Map<String, String> map1 = new HashMap<String, String>();
		map1.put("id", "1");
		map1.put("text", "减字");
		list.add(map1);
		
		Map<String, String> map2 = new HashMap<String, String>();
		map2.put("id", "2");
		map2.put("text", "假字");
		list.add(map2);
		
		return list;
	}

	/**
	 * 模拟请求返回结果, TODO 需要修正
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = { "/getUserCount" })
	@ResponseBody
	public void getUserCount(HttpServletRequest request, HttpServletResponse response) {
		//
		int userCount = getOnlineUserCount();
		try {
			response.setContentType("text/plain;charset=utf-8"); 
			PrintWriter writer = response.getWriter();
			
			writer.write(""+userCount);
			writer.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 模拟请求返回结果, TODO 需要修正
	 * @param request
	 * @param response
	 * @return
	 */
		/*
	 * 用户所有事件列表
	 */
	@RequestMapping(value = { "/getEventList" })
	@ResponseBody
	public void getEventList(HttpServletRequest request, HttpServletResponse response) {

		String result="";
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, Object> resultmap = new HashMap<String, Object>();
		ArrayList<Object> data = new ArrayList<Object>();
		SystemUser user=(SystemUser)getSessionAttribute(request, ControllerBase.SESSION_USER_KEY); 
		map.put("userid",user.getUserid());
		map.put("departid", user.getDepartid());
		List<TbuserNotice> list=userNoticeService.getUserNotice(map);
		TbuserNotice tn=new TbuserNotice();
		for(int i=0;i<list.size();i++){
			tn=list.get(i);
			HashMap<String,Object> tempmap = new HashMap<String,Object>();
			tempmap.put("title", tn.getTitle());
			tempmap.put("context", tn.getContent());
			tempmap.put("noticeid", tn.getNoticeid());
			tempmap.put("username", tn.getUsername());
			tempmap.put("opid", tn.getOpid());
			tempmap.put("state", tn.getState());
			data.add(tempmap);
		}
		resultmap.put("data", data);
		PrintWriter writer;
		try {
			result = new JSONObject(resultmap).toString();
			response.setContentType("text/plain;charset=utf-8"); 
			writer = response.getWriter();
			writer.write(result);
			writer.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	
	
	/**
	 * 获取罪犯状态
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = { "/ajaxCodeShuJu" })
	@ResponseBody
	public Object ajaxCodeShuJu(HttpServletRequest request, HttpServletResponse response) {
		String codetype=request.getParameter("sctid");
			List<Map> list=systemorganizationservice.getCrimidStatus(codetype);
			List<Map> list2 = new ArrayList<Map>();
			for(int i=0;i<list.size();i++){
				Map maps=list.get(i);
				Map m=new HashMap();
				m.put("name", maps.get("NAME"));
				m.put("codeid", maps.get("CODEID"));
				list2.add(m);
			}
			return list2;
	}
	
	/**
	 * 用于公共页面的下拉框配置查询
	 * （yanqutai）
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = { "/ajaxCodeShuJuForPublicListPage" })
	@ResponseBody
	public Object ajaxCodeShuJuForPublicListPage(HttpServletRequest request, HttpServletResponse response) {
			SystemUser user=getLoginUser(request);	
			String codetype=request.getParameter("sctid");
			
			Properties jypro = new GetProperty().bornProp(GkzxCommon.DATABASETYPE, null);
			String provincecode = jypro.getProperty("provincecode");
			 
			Map map = new HashMap<String, String>();
			map.put("codetype", codetype);
			//先从当前用户的监狱查找
			map.put("departid", user.getDepartid());
			List<Map> list=systemorganizationservice.getCodeListByCodeType(map);
			//当前用户监狱查找不到则从当前省份查找
			if(list.size() < 1){
				map.put("departid", provincecode);
				list=systemorganizationservice.getCodeListByCodeType(map);
			}
			//当前省份查找不到则从全国中查找  0
			if(list.size() < 1){
				map.put("departid", "0");
				list=systemorganizationservice.getCodeListByCodeType(map);
			}
			
			List<Map> list2 = new ArrayList<Map>();
			for(int i=0;i<list.size();i++){
				Map maps=list.get(i);
				Map m=new HashMap();
				m.put("name", maps.get("NAME"));
				m.put("codeid", maps.get("CODEID"));
				list2.add(m);
			}
			return list2;
	}
	
	/**
	 * 模拟请求返回结果, TODO 需要修正
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = { "/ajaxListByMap" })
	@ResponseBody
	public Object ajaxListByMap(HttpServletRequest request, HttpServletResponse response) {
		SystemUser user = getLoginUser(request);
		Properties jypro = new GetProperty().bornProp(GkzxCommon.DATABASETYPE, null);
		String codetype = request.getParameter("codetype");
		String codeids = request.getParameter("codeids");
		codeids = StringNumberUtil.formatString(codeids, ",");
		Map<String, String> paraMap = new HashMap<String, String>();
		paraMap.put("codetype", codetype);
		paraMap.put("codeids", codeids);
		//配置文件中的省份代码
		paraMap.put("orgid", jypro.getProperty("provincecode"));
		return systemCodeService.listByMap(paraMap);
		
	}
	
	
	/**
	 * 根据用户所处部门级别获取部门信息（选择监区）
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = { "/getDepartList" })
	@ResponseBody
	public List<Map> getDepartList(HttpServletRequest request,
			HttpServletResponse response) {
		SystemUser user = getLoginUser(request);
		String type = request.getParameter("type");
		String removeself = request.getParameter("removeself")==null?"":request.getParameter("removeself");
		Map<String, String> maptemp = new HashMap<String, String>();
		maptemp.put("userid", user.getUserid());
		maptemp.put("departid", user.getOrganization().getOrgid());
		// 用户所在部门的级别
		String unitlevel = user.getOrganization().getUnitlevel();
		Map<String, String> map = new HashMap<String, String>();
		if (null != type && "all".equals(type)) {
			unitlevel = "3";
		}
		map.put("progid", user.getDepartid());
		map.put("unitlevel", unitlevel);
		if ("4".equals(unitlevel) || "11".equals(unitlevel) || "12".equals(unitlevel)) {
			map.put("progid", user.getOrgid());
		}
		List<Map> list = systemorganizationservice.getDepartById(map);
		SystemUser obj = (SystemUser) request.getSession().getAttribute(
				SESSION_USER_KEY);
		SystemOrganization sysorg = obj.getOrganization();
		if(!"".equals(removeself)){
			if (!list.isEmpty() && list != null) {
				for (int i = 0; i < list.size(); i++) {
					Object jianqu = list.get(i);
					Map map1 = list.get(i);
					Set keys = list.get(i).keySet();
					for (int j = 0; j < keys.size(); j++) {
						String jianq = (String) map1.get(keys.iterator().next());
						if (sysorg.getFullname().equals(jianq)) {
							list.remove(jianqu);
						}
					}
				}
			}
		}
		return list;
	}
	
	
	/**
	 * 模拟请求返回结果, TODO 需要修正
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = { "/shortcutMenu" })
	@ResponseBody
	public void shortcutMenu(HttpServletRequest request, HttpServletResponse response) {
		//
		try {
			response.setContentType("text/plain;charset=utf-8"); 
			PrintWriter writer = response.getWriter();
			String shortcutMenuStr = "[{\"smdiscribe\":\"表单管理\",\"smparentid\":\"16706\",\"smid\":\"16708\",\"smurl\":\"getReportList.action?1=1&systempletid=systempletid\"},{\"smdiscribe\":\"授权管理\",\"smparentid\":\"10005\",\"smid\":\"10010\",\"smurl\":\"publicMainAction.action?1=1\"},{\"smdiscribe\":\"功能管理\",\"smparentid\":\"6530\",\"smid\":\"200\",\"smurl\":\"publicMainAction.action?1=1\"}]";
			writer.write("" + shortcutMenuStr);
			writer.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 模拟请求返回结果, TODO 需要修正
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = { "/ajaxMainGetShuJu" })
	@ResponseBody
	public void ajaxMainGetShuJu(HttpServletRequest request, HttpServletResponse response) {
		//
		try {
			response.setContentType("text/plain;charset=utf-8"); 
			PrintWriter writer = response.getWriter();
			String ajaxMainGetShuJuStr = "{\"total\":7,\"data\":[{\"zhujianstr\":\"4463017728\",\"updatetime\":\"2014-05-23\",\"remark\":null,\"annexcontent\":null,\"antype\":\"SDXF_BASE_RJDJB\",\"gkzxtempid\":null,\"smid\":\"8328\",\"xuhao\":1,\"id\":\"4463017728\",\"sddiscribe\":\"七监区\",\"updatemender\":\"超级管理员\",\"annexname\":\"肖勇入监登记表\",\"signaturecourse\":null,\"approveid\":\"206656\"},{\"zhujianstr\":\"4463015397\",\"updatetime\":\"2014-06-27\",\"remark\":null,\"annexcontent\":null,\"antype\":\"SDXF_BASE_RJDJB\",\"gkzxtempid\":null,\"smid\":\"8328\",\"xuhao\":1,\"id\":\"4463015397\",\"sddiscribe\":\"三监区\",\"updatemender\":\"赵深\",\"annexname\":\"王凯入监登记表\",\"signaturecourse\":null,\"approveid\":\"223412\"},{\"zhujianstr\":\"4463015029\",\"updatetime\":\"2014-06-27\",\"remark\":null,\"annexcontent\":null,\"antype\":\"SDXF_BASE_RJDJB\",\"gkzxtempid\":null,\"smid\":\"8328\",\"xuhao\":1,\"id\":\"4463015029\",\"sddiscribe\":\"三监区\",\"updatemender\":\"赵深\",\"annexname\":\"阳红入监登记表\",\"signaturecourse\":null,\"approveid\":\"223153\"},{\"zhujianstr\":\"4406050281\",\"updatetime\":\"2014-06-20\",\"remark\":null,\"annexcontent\":null,\"antype\":\"SDXF_BASE_RJDJB\",\"gkzxtempid\":null,\"smid\":\"8328\",\"xuhao\":1,\"id\":\"4406050281\",\"sddiscribe\":\"四监区\",\"updatemender\":\"超级管理员\",\"annexname\":\"黄顺平入监登记表\",\"signaturecourse\":null,\"approveid\":\"215430\"},{\"zhujianstr\":\"4463012312\",\"updatetime\":\"2014-06-19\",\"remark\":null,\"annexcontent\":null,\"antype\":\"SDXF_BASE_RJDJB\",\"gkzxtempid\":null,\"smid\":\"8328\",\"xuhao\":1,\"id\":\"4463012312\",\"sddiscribe\":\"一监区\",\"updatemender\":\"管教测试\",\"annexname\":\"袁焰强入监登记表\",\"signaturecourse\":null,\"approveid\":\"207770\"},{\"zhujianstr\":\"4406050284gkzx_gkzx1gkzx_gkzxXFZX_CJQD\",\"updatetime\":\"2014-06-23\",\"remark\":null,\"annexcontent\":null,\"antype\":\"XFZX_CJQD\",\"gkzxtempid\":null,\"smid\":\"9398\",\"xuhao\":1,\"id\":\"4406050284\",\"sddiscribe\":\"三监区\",\"updatemender\":\"超级管理员\",\"annexname\":\"陈瑞洪罪犯出监鉴定表\",\"signaturecourse\":null,\"approveid\":\"218341\"},{\"zhujianstr\":\"44630239697\",\"updatetime\":\"2014-06-26\",\"remark\":null,\"annexcontent\":null,\"antype\":\"SDXF_BASE_RJDJB\",\"gkzxtempid\":null,\"smid\":\"8328\",\"xuhao\":1,\"id\":\"44630239697\",\"sddiscribe\":\"一监区\",\"updatemender\":\"管教测试\",\"annexname\":\"圣斗士入监登记表\",\"signaturecourse\":null,\"approveid\":\"221377\"}]}";
			writer.write("" + ajaxMainGetShuJuStr);
			writer.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
//	/**
//	 * 跳转待办事项审批页面
//	 * @author YangZR 	2015-02-28
//	 * @param request
//	 * @return ModelAndView
//	 */
//	@RequestMapping(value = "/toPublicTodoListPage")
//	public ModelAndView toPublicTodoListPage(HttpServletRequest request){
//		returnResourceMap(request);
//		String flowdefid = request.getParameter("flowdefid");
//		String tempid = request.getParameter("tempid");
//		String notinnodeid = request.getParameter("notinnodeid");
//		request.setAttribute("flowdefid", flowdefid);
//		request.setAttribute("tempid", tempid);
//		request.setAttribute("notinnodeid", notinnodeid);
//		return new ModelAndView("system/publicTodoListPage");
//	}
	
	/**
	 * @Describe 查询待办事项中的列表数据
	 * @authorYangZR		2015-02-27
	 */
	@RequestMapping(value="getPublicTodoDataList.action")
	@ResponseBody
	public HashMap<String, Object> getPublicTodoDataList(HttpServletRequest request) {
		
		SystemUser user = getLoginUser(request);
		Object buttonstr = "";
		
		HashMap<String, Object> result = new HashMap<String, Object>();
		Map paraMap = new HashMap();
		
		String applyid = request.getParameter("applyid");
		String flowdefid = request.getParameter("flowdefid");
		String jianqu = request.getParameter("jianqu");
		String notinnodeid = request.getParameter("notinnodeid");
		String key = request.getParameter("key")==null?"":request.getParameter("key");//检索关键字
		
		paraMap.put("applyid", applyid);
		paraMap.put("flowdefid", flowdefid);
		paraMap.put("jianqu", jianqu);
		paraMap.put("notinnodeid", notinnodeid);
		paraMap.put("key", key);
		Properties jypro = new GetProperty().bornProp(GkzxCommon.DATABASETYPE, null);
		String distributeflow = jypro.getProperty("distributeflow");//需分案的流程
		
		if(user!=null){
    		String orgid = user.getOrganization().getOrgid();
    		String departid = user.getDepartid();
    		paraMap.put("orgid", orgid);
			paraMap.put("departid", departid);
			//增加条件，处理本单位没有流程默认省局流程的情况(yanqutai)
			if (jypro != null) {
				paraMap.put("provincecode", jypro.getProperty("provincecode"));
			}
			paraMap.put("userid", user.getUserid());
			//获取该用户拥有的按钮资源id
			if(StringNumberUtil.notEmpty(flowdefid)){
				buttonstr = this.returnButtonResourceByUser(user, flowdefid);
			}else{
				buttonstr = this.returnButtonResourceByUser(user,null,null);
			}
	    	paraMap.put("connsql", buttonstr);
    	}
		
		int pageIndex = Integer.valueOf(request.getParameter("pageIndex")==null?"1":request.getParameter("pageIndex"));
		int pageSize = Integer.valueOf(request.getParameter("pageSize")==null?"20":request.getParameter("pageSize"));
		String sortField = request.getParameter("sortField");//排序字段
		String sortOrder = request.getParameter("sortOrder");//排序方式
		int start = pageIndex * pageSize + 1;
		int end = start + pageSize - 1;
		
		//有配分案的流程，进行分案处理
		if(StringNumberUtil.notEmpty(flowdefid) && StringNumberUtil.notEmpty(distributeflow)){
			if(distributeflow.indexOf(flowdefid) >-1){
				this.isFjqUser(paraMap, user);//map --> key=isfjquser  value=1 则为分监区用户
			}
		}
		
		paraMap.put("start", start);
		paraMap.put("end", end);
		paraMap.put("sortField", sortField);
		paraMap.put("sortOrder", sortOrder);
		
		List<Map> list = systemMainService.getBaseDocByCondition(paraMap);
		int count = systemMainService.countBaseDocByCondition(paraMap);
		result.put("total", count);
		result.put("data", list);
		return result;
	}
	
	
	/**
	 * 跳转公共罪犯选择页面
	 * @param request
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/criminalChoosePage")
	public ModelAndView criminalChoosePage(HttpServletRequest request){
		SystemUser user = this.getLoginUser(request);
		returnResourceMap(request);
		
		String flowdefid = request.getParameter("flowdefid");
		String tempid = request.getParameter("tempid");
		String menuid = getParameterString(request, "menuid", "");
		
		String currnodeid = getFlowCurrnodeidOfDataGrid(menuid, flowdefid, user);
		request.setAttribute("currnodeid", currnodeid);
		
		if(StringNumberUtil.isEmpty(flowdefid)){
			flowdefid = GkzxCommon.DEFAULT_FLOWDEFID;//默认系统流程
		}
		request.setAttribute("flowdefid",flowdefid);
		request.setAttribute("tempid",tempid);
		return new ModelAndView("system/criminalChoosePage");
	}
	
	
	private String getFlowCurrnodeidOfDataGrid(String resid, String flowdefid, SystemUser user){
		Map paraMap = new HashMap<String,Object>();
		paraMap.put("presid", resid);
		paraMap.put("flowdefid", flowdefid);
		paraMap.put("departid", user.getDepartid());
		paraMap.put("userid", user.getUserid());
		String currnodeid = flowDeliverService.getFlowCurrnodeidOfDataGrid(paraMap);
		if(StringNumberUtil.isEmpty(currnodeid)){
			currnodeid = GkzxCommon.RESULT_ZERO;
		}
		return currnodeid;
	}
	
	// start add by blue_lv 2015-07-15
	/**
	 * 进入应用界面
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = { "/app_main.action" })
	public ModelAndView app_main(HttpServletRequest request,
			@RequestParam(value = "theme", required = true) String theme) {
		System.out.println("开始处理 ,进入应用界面切换：" + System.currentTimeMillis());
		String path = request.getContextPath();
		String basePath = request.getScheme() + "://" + request.getServerName()
				+ ":" + request.getServerPort() + path + "/";
		
		ModelAndView mav = new ModelAndView("main/app_main");
		if(theme.equalsIgnoreCase("classic"))
		{
			mav = new ModelAndView("main/sz_main");			
		}		
		
		SystemUser systemUser = (SystemUser) getSessionAttribute(request,
				ControllerBase.SESSION_USER_KEY);
		// 用户通知相关
		List<TbuserNotice> noticeContent = new ArrayList<TbuserNotice>();
		TbuserNotice tbuserNotice = new TbuserNotice();

		noticeContent = userNoticeService.getAlluserNotice(3, 0,
				systemUser.getUserid());

		if (noticeContent.size() > 0) {
			tbuserNotice = noticeContent.get(0);
		}
		//更新用户界面设置
		SystemUser updateUser=new SystemUser();
		updateUser.setUserid(systemUser.getUserid());
		updateUser.setText1(theme);
		this.systemUserService.updateByPrimaryKeySelective(updateUser);
		systemUser.setText1(theme);
		
		SystemOrganization org = systemUser.getOrganization();
		String orgNameStr="";
		if (null != org) {
			orgNameStr = org.getName();
		}
		
		
		// 添加属性、JSP页面上使用
		mav.addObject("user", systemUser);
		mav.addObject("tbuserNotice", tbuserNotice);
		mav.addObject("path", path);
		mav.addObject("basePath", basePath);
		mav.addObject("orgNameStr", orgNameStr);
		System.out.println("结束处理,完成应用界面切换：" + System.currentTimeMillis());
		return mav;
	}
	
	/**
	 * 进入触摸屏应用界面
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	@RequestMapping("/app_desktop")
	public ModelAndView app_desktop(HttpServletRequest request,
			HttpServletResponse response) throws UnsupportedEncodingException {
		
		ModelAndView mav = new ModelAndView("main/app_desktop");
		String path = request.getContextPath();
		String basePath = request.getScheme() + "://" + request.getServerName()
				+ ":" + request.getServerPort() + path + "/";

		SystemUser user = getLoginUser(request);
		List<SystemResource> menus = systemResourceService.getMenuByUser(user);
		List<SystemResource> temp = new ArrayList<SystemResource>();
		for (SystemResource res : menus) {
			SystemResource restemp = new SystemResource();
			restemp.setResid(res.getResid());
			restemp.setPresid(res.getPresid());
			restemp.setName(res.getName().replace("\'", "\\'"));
			if (res.getSrurl() != null)
				restemp.setSrurl(URLEncoder.encode(res.getSrurl(),"utf-8"));
			restemp.setFormid(res.getFormid());
			restemp.setIsmenu(res.getIsmenu());
			restemp.setQuerysql(res.getQuerysql());
			restemp.setShowico(res.getShowico());
			temp.add(restemp);
		}
		mav.addObject("path", path);
		mav.addObject("basePath", basePath);
		mav.addObject("userMenus", JSON.toJSONString(temp));
		mav.addObject("app_ids",user.getText4());
		
		return mav;
	}
	
	/**
	 * 更新用户应用桌面菜单设置
	 * @param request
	 * @param app_ids
	 * @return
	 */
	@RequestMapping(value = "/updateUserAppTheme.action", method = RequestMethod.POST)
	@ResponseBody
	public int updateUserAppTheme(HttpServletRequest request,@RequestParam(value = "app_ids", required = true) String app_ids) {
		int result=-1;
		SystemUser user = ControllerBase.getLoginUser(request);		
		SystemUser updateUser=new SystemUser();
		updateUser.setUserid(user.getUserid());
		updateUser.setText4(app_ids);
		result= this.systemUserService.updateByPrimaryKeySelective(updateUser);
		user.setText4(app_ids);
		return result;
	}
	
	// end add by blue_lv 2015-07-15
}
