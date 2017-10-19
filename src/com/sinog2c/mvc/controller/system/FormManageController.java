package com.sinog2c.mvc.controller.system;

import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.gkzx.common.GkzxCommon;
import com.sinog2c.model.system.SignatureScheme;
import com.sinog2c.model.system.SystemDictionary;
import com.sinog2c.model.system.SystemDictionaryTemplate;
import com.sinog2c.model.system.SystemLog;
import com.sinog2c.model.system.SystemOrganization;
import com.sinog2c.model.system.SystemTemplate;
import com.sinog2c.model.system.SystemUser;
import com.sinog2c.mvc.controller.base.ControllerBase;
import com.sinog2c.mvc.message.JSONMessage;
import com.sinog2c.service.api.common.CommonSQLSolutionService;
import com.sinog2c.service.api.system.SignatureSchemeService;
import com.sinog2c.service.api.system.SystemDictionaryService;
import com.sinog2c.service.api.system.SystemDictionaryTemplateService;
import com.sinog2c.service.api.system.SystemLogService;
import com.sinog2c.service.api.system.SystemOrganizationService;
import com.sinog2c.service.api.system.SystemTemplateService;
import com.sinog2c.util.common.MapUtil;
import com.sinog2c.util.common.StringNumberUtil;

/**
 * 日志相关
 */
@Controller
@RequestMapping("/form")
public class FormManageController extends ControllerBase{
	@Autowired(required=true)
	private CommonSQLSolutionService solutionService;
	@Resource
	public SystemLogService logService;
	@Resource
	public SystemOrganizationService orgService;
	@Autowired
	public SystemTemplateService systemTemplateService;
	@Autowired
	public SystemDictionaryService systemDictionaryService;
	@Resource
	public SystemDictionaryTemplateService systemDictionaryTemplateService;
	@Resource
	public SignatureSchemeService signatureSchemeService;
	
	/**
	 * 进入表单管理信息页面
	 */
	@RequestMapping("/toformmanage.action")
	public ModelAndView toFormManage(HttpServletRequest request, HttpServletResponse response) {
		
		ModelAndView mav = new ModelAndView("system/aipmanage/toformmanage");

		return mav;
	}
	
	/**
	 * 进入表单区域锁定页面
	 */
	@RequestMapping("/tolockmanage.action")
	public ModelAndView tolockmanage(HttpServletRequest request, HttpServletResponse response) {
		
		ModelAndView mav = new ModelAndView("system/aipmanage/tolockmanage");

		return mav;
	}
	/**
	 * 进入表单管理编辑
	 */
	@RequestMapping("/editformmanage.action")
	public ModelAndView editFormManage(HttpServletRequest request, HttpServletResponse response) {
		String returnval = "";
		String departid = request.getParameter("departid");
		request.setAttribute("departid", departid);
		String tempid = request.getParameter("tempid");
		request.setAttribute("tempid", tempid);
		String type = request.getParameter("type");
		if(GkzxCommon.NEW.equalsIgnoreCase(type)){
			returnval = "system/aipmanage/addformmanage";
		}else returnval = "system/aipmanage/editformmanage";
			
		//addformmanage.jsp
		ModelAndView mav = new ModelAndView(returnval);

		return mav;
	}
	/**
	 * 进入表单管理编辑
	 */
	@RequestMapping(value = "/getformmanageinfo.action")
	@ResponseBody
	public void getFormManageInfo(HttpServletRequest request, HttpServletResponse response) {
		//用户对象
		SystemTemplate systemTemplate = new SystemTemplate();
		//定义变量
		String result = "";
		String tempid = request.getParameter("tempid");
		String departid = request.getParameter("departid");
		try{
			systemTemplate = systemTemplateService.selectByTempid(tempid,departid);
			HashMap<String, Object> resultmap = new HashMap<String, Object>();
			resultmap.put("tempid", systemTemplate.getTempid());
			resultmap.put("tempname", systemTemplate.getTempname());
			resultmap.put("temptype", systemTemplate.getTemptype());
			resultmap.put("generaltype", systemTemplate.getGeneraltype());
			resultmap.put("functionname", systemTemplate.getFunctionname());
			resultmap.put("content", systemTemplate.getContent());
			result = new JSONObject(resultmap).toString();
			response.setContentType("text/plain;charset=utf-8"); 
			PrintWriter writer = response.getWriter();
			writer.write(result);
			writer.flush();
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	/**
	 * 显示表单管理信息
	 */
	@RequestMapping(value = "/showformmanage.action")
	@ResponseBody
	public void showFormManage(HttpServletRequest request, HttpServletResponse response) {
		//用户对象
		SystemTemplate systemTemplate = new SystemTemplate();
		//定义变量
		String result = "";
		SystemUser user = getLoginUser(request);
		ArrayList<Object> data = new ArrayList<Object>();
		Map<String, Object> resultmap = new HashMap<String,Object>();
		
	
    	try{
    		//取得参数
    		String key = request.getParameter("key")==null?"":request.getParameter("key");
    		
    		//分页
    		int pageIndex =(Integer)(request.getParameter("pageIndex")==null? "":Integer.parseInt(request.getParameter("pageIndex")));
    		int pageSize = (Integer)(request.getParameter("pageSize")==null? "":Integer.parseInt(request.getParameter("pageSize")));        
    		//字段排序
    		String sortField = request.getParameter("sortField");
    		String sortOrder = request.getParameter("sortOrder");
    		int start = pageIndex * pageSize + 1;
    		int end = start + pageSize -1;
    		//获取当前菜单ID 对应的自定义流程ID 
    		Map<String,Object> map = new HashMap<String,Object>();
    		map.put("departid", user.getDepartid());
    		map.put("type", "2");
        	map.put("key", key);
        	map.put("sortField", sortField);
        	map.put("sortOrder", sortOrder);
        	map.put("start", String.valueOf(start));
        	map.put("end",String.valueOf(end));
        	
        	//获取列表对应条数
        	int count = systemTemplateService.countAllByCondition(map);
        	
        	//获取列表显示集合
        	List<SystemTemplate> list= systemTemplateService.selectAllByCondition(map);
    		if(list!=null && list.size()>0){
    			for(int i=0;i<list.size();i++){
    				systemTemplate = list.get(i);
    				HashMap<String,Object> tempmap = new HashMap<String,Object>();
    				tempmap.put("tempid", systemTemplate.getTempid());
    				tempmap.put("tempname", systemTemplate.getTempname());
    				tempmap.put("findid", systemTemplate.getFindid());
    				tempmap.put("temptype", systemTemplate.getTemptype());
    				tempmap.put("generaltype", systemTemplate.getGeneraltype());
    				tempmap.put("functionname", systemTemplate.getFunctionname());
    				tempmap.put("departid", systemTemplate.getDepartid());
    				tempmap.put("uneditedfields", systemTemplate.getUneditedfields());
    				data.add(tempmap);
    			}
    		}
    		resultmap.put("data", data);
			resultmap.put("total", count);
			
        	result = new JSONObject(resultmap).toString();
    		response.setContentType("text/plain;charset=utf-8"); 
    		PrintWriter writer = response.getWriter();
    		writer.write(result);
    		writer.flush();
    	}catch(Exception e){
			e.printStackTrace();
		}
	}
	/**
	 * 添加表单管理信息
	 * 
	 * @return
	 */
	@RequestMapping(value = "/addreportmanage.action")
	@ResponseBody
	public Object addReportManage(HttpServletRequest request) {
		//用户对象
		SystemLog log = new SystemLog();
		SystemUser user = getLoginUser(request);
		JSONMessage message = JSONMessage.newMessage();
		SystemTemplate systemTemplate = new SystemTemplate();
		
		//参数
		int rows = 0;
		String departid = "";
		Date now = new Date();
		String rbl = request.getParameter("rbl");//通用类型
		String resid = request.getParameter("resid");//按钮ID
		String data = request.getParameter("data");//json 数据
		String content = request.getParameter("content");//模板大字段内容
		String unitlevel = user.getOrganization().getUnitlevel();//部门级别
		
		try{
			if(data!=null){
		        ArrayList<Object> list = (ArrayList<Object>) JSON.parseArray(data, Object.class);

				if(list!=null && list.size()>0){
					Map<?, ?> map = (Map<?, ?>)list.get(0);
					Short type = map.get("type")== null ?0:Short.valueOf((String)map.get("type"));//模板类型
					
					if(GkzxCommon.ONE.equals(rbl)){//监狱特有
						departid = user.getDepartid();
					}else if(GkzxCommon.TWO.equals(rbl)){//省局通用
						//获取省局单位ID
						if(GkzxCommon.TWO.equals(unitlevel)){
							departid = user.getDepartid();
						}else{
							SystemOrganization  org = orgService.getByOrganizationId(user.getDepartid());
							if(org!=null){
								departid = org.getPorgid();
							}
						}
					}else if(GkzxCommon.THREE.equals(rbl)){//全国通用
						departid = GkzxCommon.ZERO;
					}
					
					systemTemplate.setTempid((String)map.get("tempid"));
					systemTemplate.setFunctionname((String)map.get("functionname"));
					systemTemplate.setTempname((String)map.get("tempname"));
					systemTemplate.setContent(content);
					systemTemplate.setOpid(user.getUserid());
					systemTemplate.setOptime(now);
					systemTemplate.setType(Short.valueOf(type));
					systemTemplate.setGeneraltype(rbl);
					systemTemplate.setDelflag(Short.valueOf(GkzxCommon.ZERO));
					systemTemplate.setDepartid(departid);
					rows = systemTemplateService.insert(systemTemplate);
					if( 1== rows){
						message.setInfo("添加成功!");
						message.setSuccess();
					} else {
						message.setInfo("操作失败!");
					}
					// 日志
					log.setLogtype("表单信息新增操作");
					log.setOpaction("新增");
					log.setOpcontent("表单信息新增,resid="+resid);
					log.setOrgid("templet");
					log.setRemark("添加成功!");
					log.setStatus((short)message.getStatus());
					logService.add(log, user);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return  message;
	}
	/**
	 * 判断表单ID是否存在
	 * 
	 * @return
	 */
	@RequestMapping(value = "/isexisttempid.json")
	@ResponseBody
	public Object isExistTempid(HttpServletRequest request) {
		//用户对象
		SystemUser user = getLoginUser(request);
		
		//参数
		int rows = 0;
		int resultval = -1;
		String departid = user.getDepartid();//部门id
		String tempid = request.getParameter("tempid");//模板id
		String generaltype = request.getParameter("rbl");//通用类型
		if(generaltype!=null && !"".equals(generaltype)) {
			if(generaltype.equals(GkzxCommon.THREE)) {
				departid = GkzxCommon.ZERO;
			} else {
				if(GkzxCommon.TWO.equals(user.getOrganization().getUnitlevel())){
					departid = user.getDepartid();
				}else{
					SystemOrganization  org = orgService.getByOrganizationId(user.getDepartid());
					if(org!=null){
						departid = org.getPorgid();
					}
				}				
			}
		}
		try{
			if(tempid!=null){
				rows = systemTemplateService.validateTempidByDepartid(tempid,departid);
				if(rows>0){
					resultval = 0;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return  resultval;
	}
	/**
	 * 修改表单管理信息
	 * 
	 * @return
	 */
	@RequestMapping(value = "/updatereportmanage.action")
	@ResponseBody
	public Object updateReportManage(HttpServletRequest request) {
		//用户对象
		SystemLog log = new SystemLog();
		SystemUser user = getLoginUser(request);
		SystemTemplate templet = new SystemTemplate();
		JSONMessage message = JSONMessage.newMessage();
		
		//参数
		int rows = 0;
		String resid = request.getParameter("resid");
		String content = request.getParameter("content");
		String data = request.getParameter("data");//json 数据
		try{
			if(data!=null){
		        ArrayList<Object> list = (ArrayList<Object>) JSON.parseArray(data, Object.class);
				if(list!=null && list.size()>0){
					Map map = (Map)list.get(0);
					String tempid = (String)map.get("tempid");
					String departid = request.getParameter("departid");
					//Short type = map.get("type")== null ?0:Short.valueOf((String)map.get("type"));//模板类型
					String functionname = (String)map.get("functionname");
					String tempname = (String)map.get("tempname");
					//查找对象
					templet = systemTemplateService.selectByTempid(tempid,departid);
					templet.setContent(content);
					templet.setFunctionname(functionname);
					templet.setTempname(tempname);
					rows = systemTemplateService.update(templet);
				}
			}
			if( 1== rows){
				message.setInfo("更新成功!");
				message.setSuccess();
			} else {
				message.setInfo("操作失败!");
			}
			// 日志
			log.setLogtype("表单信息更新操作");
			log.setOpaction("更新");
			log.setOpcontent("表单信息更新,resid="+resid);
			log.setOrgid("templet");
			log.setRemark("更新成功!");
			log.setStatus((short)message.getStatus());
			logService.add(log, user);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return  message;
	}
	/**
	 * 修改表单不可编辑区域信息（区域锁定）
	 * 
	 * @return
	 */
	@RequestMapping(value = "/updatelockareamanage.action")
	@ResponseBody
	public Object updateLockAreaManage(HttpServletRequest request) {
		//用户对象
		SystemLog log = new SystemLog();
		SystemUser user = getLoginUser(request);
		SystemTemplate templet = new SystemTemplate();
		JSONMessage message = JSONMessage.newMessage();
		
		//参数
		int rows = 0;
		String data = request.getParameter("data");//json 数据
		try{
			if(data!=null){
		        ArrayList<Object> list = (ArrayList<Object>) JSON.parseArray(data, Object.class);
				if(list!=null && list.size()>0){
					Map map = (Map)list.get(0);
					String tempid = (String)map.get("tempid");
					String departid = (String)map.get("departid");
					String uneditedfields = (String)map.get("uneditedfields");
					String editedfields = (String)map.get("editedfields");
					//查找对象
					templet = systemTemplateService.selectByTempid(tempid,departid);
					templet.setUneditedfields(uneditedfields);
					templet.setRemark(editedfields);
					rows = systemTemplateService.updateUneditedfields(templet);
				}
			}
			if( 1== rows){
				message.setInfo("更新成功!");
				message.setSuccess();
			} else {
				message.setInfo("操作失败!");
			}
			// 日志
			log.setLogtype("表单信息更新操作");
			log.setOpcontent("修改表单不可编辑区域信息（区域锁定）");
			log.setOpaction("更新");
			log.setOrgid("templet");
			log.setRemark("更新成功!");
			log.setStatus((short)message.getStatus());
			logService.add(log, user);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return  message;
	}
	/**
	 * 删除表单管理信息
	 * 
	 * @return
	 */
	@RequestMapping(value = "/removereportmanage.action")
	@ResponseBody
	public Object removeReportManage(HttpServletRequest request) {
		//用户对象
		SystemLog log = new SystemLog();
		SystemUser user = getLoginUser(request);
		JSONMessage message = JSONMessage.newMessage();
		//参数
		int rows = 0;
		String resid = request.getParameter("resid");
		String tempid = request.getParameter("tempid");//模板ID
		String departid = request.getParameter("departid");
		try{
			rows = systemTemplateService.deleteByTempid(tempid,departid);
			if( 1== rows){
				message.setInfo("删除成功!");
				message.setSuccess();
			} else {
				message.setInfo("操作失败!");
			}
			// 日志
			log.setLogtype("表单信息删除操作");
			log.setOpaction("删除");
			log.setOpcontent("表单信息删除,resid="+resid);
			log.setOrgid("templet");
			log.setRemark("删除成功!");
			log.setStatus((short)message.getStatus());
			logService.add(log, user);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return  message;
	}
	/**
	 * 进入模板元素管理页面
	 */
	@RequestMapping("/totempletmanage.action")
	public ModelAndView toTempletManage(HttpServletRequest request, HttpServletResponse response) {
		String tempid = request.getParameter("tempid");
		request.setAttribute("tempid",tempid);
		ModelAndView mav = new ModelAndView("system/aipmanage/totempletmanage");
		return mav;
	}
	/**
	 * 显示模板信息
	 */
	@RequestMapping(value = "/showtempletmanage.action")
	@ResponseBody
	public void showTempletManage(HttpServletRequest request, HttpServletResponse response) {
		//用户对象
		SystemUser user = getLoginUser(request);
		SystemDictionaryTemplate template = new SystemDictionaryTemplate();
		
		//定义变量
		String result = "";
		SimpleDateFormat sdf = new SimpleDateFormat(GkzxCommon.DATETIMEFORMAT);
		ArrayList<Object> data = new ArrayList<Object>();
		Map<String, Object> resultmap = new HashMap<String,Object>();
	
    	try{
    		//取得参数
    		String tempid = request.getParameter("tempid");//模板ID
    		String key = request.getParameter("key")==null?"":request.getParameter("key");
    		
    		//分页
    		int pageIndex =(Integer)(request.getParameter("pageIndex")==null? "":Integer.parseInt(request.getParameter("pageIndex")));
    		int pageSize = (Integer)(request.getParameter("pageSize")==null? "":Integer.parseInt(request.getParameter("pageSize")));        
    		//字段排序
    		String sortField = request.getParameter("sortField");
    		String sortOrder = request.getParameter("sortOrder");
    		int start = pageIndex * pageSize + 1;
    		int end = start + pageSize -1;
    		//获取当前菜单ID 对应的自定义流程ID 
    		Map<String,Object> map = new HashMap<String,Object>();
    		map.put("tempid", tempid);
        	map.put("key", key);
        	map.put("sortField", sortField);
        	map.put("sortOrder", sortOrder);
        	map.put("start", String.valueOf(start));
        	map.put("end",String.valueOf(end));
        	
        	//获取列表对应条数
        	int count = systemDictionaryTemplateService.countAllByCondition(map);
        	
        	//获取列表显示集合
        	List<SystemDictionaryTemplate> list= systemDictionaryTemplateService.selectAllByCondition(map);
    		if(list!=null && list.size()>0){
    			for(int i=0;i<list.size();i++){
    				template = list.get(i);
    				HashMap<String,Object> tempmap = new HashMap<String,Object>();
    				tempmap.put("tempid", template.getTempid());
    				tempmap.put("ename", template.getEname());
    				tempmap.put("ecolname", template.getEcolname());
    				tempmap.put("optime", sdf.format(template.getOptime()));
    				data.add(tempmap);
    			}
    		}
    		resultmap.put("data", data);
			resultmap.put("total", count);
			
        	result = new JSONObject(resultmap).toString();
    		response.setContentType("text/plain;charset=utf-8"); 
    		PrintWriter writer = response.getWriter();
    		writer.write(result);
    		writer.flush();
    	}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	@RequestMapping(value = "/getSystemDictionaryTemplate.json")
	@ResponseBody
	public Object getSystemDictionaryTemplate(HttpServletRequest request){
		
		//用户对象
//		SystemUser user = getLoginUser(request);
		//定义变量
//		List<Object> data = new ArrayList<Object>();
	
		//取得参数
		String tempid = request.getParameter("tempid");//模板ID
		if(StringNumberUtil.isEmpty(tempid)){
			return null;
		}
		
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("tempid", tempid);
    	
    	Object result = systemDictionaryTemplateService.getSystemDictionaryTemplate(map);
		return result;
	}
	
	@RequestMapping(value = "/getSelectTemplateNode.json")
	@ResponseBody
	public Object getSelectTemplateNode(HttpServletRequest request){
		//取得参数
		String signid = request.getParameter("signid");//模板ID
		if(StringNumberUtil.isEmpty(signid)){
			return null;
		}
		
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("signid", Integer.parseInt(signid));
		
		SignatureScheme signatureScheme = signatureSchemeService.getById(Integer.parseInt(signid));
		return signatureScheme.getProtectnode();
	}
	
	/**
	 * 进入新增模板元素页面
	 */
	@RequestMapping("/edittemplet.action")
	public ModelAndView editTemplet(HttpServletRequest request, HttpServletResponse response) {
		String tempid = request.getParameter("tempid");
		request.setAttribute("tempid",tempid);
		
		ModelAndView mav = new ModelAndView("system/aipmanage/edittemplet");

		return mav;
	}
	/**
	 * 显示字典信息
	 */
	@RequestMapping(value = "/showdictionary.action")
	@ResponseBody
	public void showDictionary(HttpServletRequest request, HttpServletResponse response) {
		//用户对象
		SystemUser user = getLoginUser(request);
		SystemDictionary template = new SystemDictionary();
		
		//定义变量
		String result = "";
		SimpleDateFormat sdf = new SimpleDateFormat(GkzxCommon.DATETIMEFORMAT);
		ArrayList<Object> data = new ArrayList<Object>();
		Map<String, Object> resultmap = new HashMap<String,Object>();
	
    	try{
    		//取得参数
    		String tempid = request.getParameter("tempid");//模板ID
    		String key = request.getParameter("key")==null?"":request.getParameter("key");
    		
    		//分页
    		int pageIndex =(Integer)(request.getParameter("pageIndex")==null? "":Integer.parseInt(request.getParameter("pageIndex")));
    		int pageSize = (Integer)(request.getParameter("pageSize")==null? "":Integer.parseInt(request.getParameter("pageSize")));        
    		//字段排序
    		String sortField = request.getParameter("sortField");
    		String sortOrder = request.getParameter("sortOrder");
    		int start = pageIndex * pageSize + 1;
    		int end = start + pageSize -1;
    		//获取当前菜单ID 对应的自定义流程ID 
    		Map<String,Object> map = new HashMap<String,Object>();
    		map.put("tempid", tempid);
        	map.put("key", key);
        	map.put("sortField", sortField);
        	map.put("sortOrder", sortOrder);
        	map.put("start", String.valueOf(start));
        	map.put("end",String.valueOf(end));
        	
        	//获取列表对应条数
        	int count = systemDictionaryService.countAllByCondition(map);
        	
        	//获取列表显示集合
        	List<SystemDictionary> list= systemDictionaryService.selectAllByCondition(map);
    		if(list!=null && list.size()>0){
    			for(int i=0;i<list.size();i++){
    				template = list.get(i);
    				HashMap<String,Object> tempmap = new HashMap<String,Object>();
    				tempmap.put("ename", template.getEname());
    				tempmap.put("cname", template.getCname());
    				tempmap.put("ecolname", template.getEcolname());
    				tempmap.put("ccolname", template.getCcolname());
    				tempmap.put("optime", sdf.format(template.getOptime()));
    				data.add(tempmap);
    			}
    		}
    		resultmap.put("data", data);
			resultmap.put("total", count);
			
        	result = new JSONObject(resultmap).toString();
    		response.setContentType("text/plain;charset=utf-8"); 
    		PrintWriter writer = response.getWriter();
    		writer.write(result);
    		writer.flush();
    	}catch(Exception e){
			e.printStackTrace();
		}
	}
	/**
	 * 添加模板管理信息
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/addtemplatemanage.action")
	@ResponseBody
	public Object addTemplateManage(HttpServletRequest request) {
		//用户对象
		SystemLog log = new SystemLog();
		SystemUser user = getLoginUser(request);
		JSONMessage message = JSONMessage.newMessage();
		SystemDictionaryTemplate template = new SystemDictionaryTemplate();
		
		//参数
		int rows = 0;
		Date now = new Date();
		String resid = request.getParameter("resid");//按钮ID
		String tempid = request.getParameter("tempid");//模板ID
		String enames = request.getParameter("ename");//表名
		String ecolnames = request.getParameter("ecolname");//列名
		
		try{
			if(ecolnames!=null){
				String[] enamearr = enames.split(",");
				String[] ecolnamearr = ecolnames.split(",");
				if(enamearr!=null && enamearr.length>0){
					for(int i=0;i<enamearr.length;i++){
						String ename = enamearr[i];
						String ecolname = ecolnamearr[i];
						template.setEname(ename);
						template.setTempid(tempid);
						template.setEcolname(ecolname);
						template.setOptime(new Date());
						rows = systemDictionaryTemplateService.insert(template);
						if( 1== rows){
							message.setInfo("添加成功!");
							message.setSuccess();
						} else {
							message.setInfo("操作失败!");
						}
						// 日志
						log.setLogtype("模板信息新增操作");
						log.setOpaction("新增");
						log.setOpcontent("模板信息新增,resid="+resid);
						log.setOrgid("dictionarytemplate");
						log.setRemark("添加成功!");
						log.setStatus((short)message.getStatus());
						logService.add(log, user);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return  message;
	}
	/**
	 * 删除模板元素信息
	 * 
	 * @return
	 */
	@RequestMapping(value = "/removetempletmanage.action")
	@ResponseBody
	public Object removeTempletManage(HttpServletRequest request) {
		//用户对象
		SystemLog log = new SystemLog();
		SystemUser user = getLoginUser(request);
		JSONMessage message = JSONMessage.newMessage();
		//参数
		int rows = 0;
		String resid = request.getParameter("resid");
		String tempid = request.getParameter("tempid");//模板ID
		String ecolname = request.getParameter("ecolname");//列名称
		String ename = request.getParameter("ename");//对应的表名称
		try{
			rows = systemDictionaryTemplateService.deleteByEcolname(tempid,ecolname,ename);
			if( 1== rows){
				message.setInfo("删除成功!");
				message.setSuccess();
			} else {
				message.setInfo("操作失败!");
			}
			// 日志
			log.setLogtype("模板信息删除操作");
			log.setOpaction("删除");
			log.setOpcontent("模板信息删除,resid="+resid);
			log.setOrgid("dictionarytemplate");
			log.setRemark("删除成功!");
			log.setStatus((short)message.getStatus());
			logService.add(log, user);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return  message;
	}
	
	/**
	 * 进入模板元素 弹出页面
	 * @return
	 */
	
	@RequestMapping("/getlist.action")
	public ModelAndView testGetList(HttpServletRequest request) {
		//用户对象
		SystemDictionary template = new SystemDictionary();
		
		//参数
		Date now = new Date();
		List<String> diclist = new ArrayList<String>();
		String tempid = request.getParameter("tempid");//模板ID
		List<SystemDictionary> list = systemDictionaryService.selectAllByTempid(tempid);
		if(list!=null && list.size()>0){
			for(SystemDictionary dic:list){
				diclist.add(dic.getEcolname()+","+dic.getCcolname());
			}
		}
		request.setAttribute("diclist", diclist);
		ModelAndView mav = new ModelAndView("system/aipmanage/set_filed");

		return mav;
	}
	@RequestMapping("/editClobPage.action")
	public ModelAndView editClobPage(HttpServletRequest request, HttpServletResponse response) {
		return new ModelAndView("system/aipmanage/editClobPage");
	}
	/**
	 * 根据查询方案查询大字段并将修改后的数据保存
	 * @author LiuChaoyang 2016年1月21日
	 * 
	 * @param type 判断是查询列表还是详细数据
	 * @return  result
	 */
	@RequestMapping(value = "/editClobJson.json")
	@ResponseBody
	public Object editClobJson(HttpServletRequest request){
		Map<String, Object> result = new HashMap();
	    SystemUser user = getLoginUser(request);
	    String data = request.getParameter("data");
	    String type = request.getParameter("type");
	    Map<String, Object> params = MapUtil.parseJSONArray2Map(data);
	    if(type.equals("list")){
	    	result = solutionService.list(params, user);
	    }else if(type.equals("root")){
	    	result = solutionService.query(params, user);
	    }else if(type.equals("save")){
	    	result = solutionService.save(params, user);
	    }
	    return result;
	  }
}
