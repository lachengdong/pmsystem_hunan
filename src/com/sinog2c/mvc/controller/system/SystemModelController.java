package com.sinog2c.mvc.controller.system;

import java.sql.Clob;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.InternalResourceView;
import com.gkzx.common.GkzxCommon;
import com.gkzx.util.property.GetProperty;
import com.sinog2c.model.system.SystemLog;
import com.sinog2c.model.system.SystemUser;
import com.sinog2c.model.system.TbsysTemplate;
import com.sinog2c.mvc.controller.base.ControllerBase;
import com.sinog2c.service.api.system.SystemLogService;
import com.sinog2c.service.api.system.SystemModelService;
import com.sinog2c.service.api.system.SystemOrganizationService;
import com.sinog2c.util.common.StringNumberUtil;

/**
 * 系统模版
 * @author liuchaoyang
 * 2014-7-21  14:46:45
 */
@Controller
public class SystemModelController extends ControllerBase {
	@Autowired
	private SystemModelService systemModelService;
	@Resource
	public SystemLogService logService;
	@Resource
	SystemOrganizationService systemOrganizationService;
	
	/**
	 * 跳转到列表页
	 * @author liuchaoyang
	 * 2014-7-22  11:06:45
	 */
	@RequestMapping(value = "/systemModel")
	public ModelAndView systemModel(HttpServletRequest request) {
		 ModelAndView view = null;
		 SystemUser user = getLoginUser(request);// 获取当前登录的用户
		 String lv = user.getOrganization().getUnitlevel();
		 if(lv!=null && ("6".equals(lv) || "7".equals(lv))){
			 view = new ModelAndView(new InternalResourceView("/WEB-INF/JSP/system/courtSystemModel.jsp"));
		 }else{
			 view = new ModelAndView(new InternalResourceView("/WEB-INF/JSP/system/systemModel.jsp"));
		 }
		return view;
	}
	/**
	 * 方法描述：获取数据列表
	 */
	@RequestMapping(value = "/getTemplateList")
	@ResponseBody
	public HashMap<String, Object> getTemplateList(
			HttpServletRequest request) {
		HashMap<String, Object> result = new HashMap<String, Object>();
		String type = request.getParameter("type");
		String key = request.getParameter("key");
		String sortField = request.getParameter("sortField");
		String sortOrder = request.getParameter("sortOrder");
		int pageIndex = Integer.valueOf(request.getParameter("pageIndex"));
		int pageSize = Integer.valueOf(request.getParameter("pageSize"));
		SystemUser user = getLoginUser(request);// 获取当前登录的用户
		Map map = new HashMap();
		map.put("start", pageIndex * pageSize + 1);
		map.put("end", (pageIndex +1)* pageSize);
		map.put("key", key);
		map.put("departid", user.getDepartid());
		map.put("sortField", sortField);
		map.put("sortOrder", sortOrder);
		map.put("type", type);
		// 查询出所有数据集合
		List<TbsysTemplate> list = systemModelService.getTemplateList(map);
		// 查询数据总数
		int count = systemModelService.getCount(map);
		result.put("total", count);
		result.put("data", list);
		return result;
	}
	/**
	 * 新增、修改模版保存方法
	 * @author liuchaoyang
	 * 2014-7-22  11:06:45
	 */
	@RequestMapping(value = "/saveModelDetail")
	@ResponseBody
	public int saveModelDetail(HttpServletRequest request){
		int countnum = 0;//保存结果：0、失败，1、成功
		short status = 0;
		Date date = new Date(); 
		Short type = 1;//模版类型：1、系统模版，2、表单
		SystemLog log = new SystemLog();
		SystemUser user = getLoginUser(request);// 获取当前登录的用户
		String sjdepid  = "";
		String lv = user.getOrganization().getUnitlevel();
		if(lv!=null && ("6".equals(lv) || "7".equals(lv))){//法院用户
			sjdepid = user.getDepartid();
		}else{
			//省局部门id
			sjdepid = systemOrganizationService.getProvinceCode(user.getOrgid()).getOrgid();
		}
		String departid = user.getDepartid();
		TbsysTemplate template = new TbsysTemplate();
		String operator = request.getParameter("operator");//判断新增、修改
		String tempid = request.getParameter("tempid")==null?"":request.getParameter("tempid");
//		String tempid1 = request.getParameter("tempid1")==null?"":request.getParameter("tempid1");
//		String requestdepartid = request.getParameter("requestdepartid")==null?"":request.getParameter("requestdepartid");
//		String generaltype1 = request.getParameter("generaltype1")==null?"":request.getParameter("generaltype1");
		String tempname = request.getParameter("tempname")==null?"":request.getParameter("tempname");
		String functionname = request.getParameter("functionname")==null?"":request.getParameter("functionname");
		String findid = request.getParameter("findid")==null?"":request.getParameter("findid");
		String content = request.getParameter("content")==null?"":request.getParameter("content");
		String temptype = request.getParameter("temptype")==null?"":request.getParameter("temptype");
		String generaltype = request.getParameter("generaltype")==null?"":request.getParameter("generaltype");
		String sn = request.getParameter("sn");
		Map<String,Object> map = new HashMap<String,Object>();
		if(GkzxCommon.TWO.equals(generaltype)){//省局通用
				departid = sjdepid;
		}else if(GkzxCommon.THREE.equals(generaltype)){//全国通用
			departid = GkzxCommon.ZERO;
		}
		Map<String,Object> map1 = new HashMap<String,Object>();
		map1.put("tempid",tempid);
		map1.put("departid",departid);
		TbsysTemplate tbsysTemplate =systemModelService.getTemplateByTempAndDept(map1);
		if(GkzxCommon.NEW.equals(operator)&&tbsysTemplate!=null){
			return 0;
		}
		if(GkzxCommon.NEW.equals(operator)){
			log.setLogtype("系统模版操作");
			log.setOpaction("新增");
			log.setOpcontent("系统模版新增");
			log.setOrgid(user.getUserid());
			log.setRemark("系统模版新增事件");
			template.setTempid(tempid);
			template.setTempname(tempname);
			template.setFunctionname(functionname);
			template.setContent(content);
			template.setType(type);
			template.setDepartid(departid);
			template.setTemptype(temptype);
			template.setGeneraltype(generaltype);
			if(sn!=null){
				template.setSn(Integer.parseInt(sn));
			}
			if(!StringNumberUtil.isNullOrEmpty(findid))
			template.setFindid(Integer.parseInt(findid));
			template.setOptime(date);
			template.setOpid(user.getUserid());
			countnum = systemModelService.saveTemplateDetail(template);
		}else if(GkzxCommon.EDIT.equals(operator)){
			log.setLogtype("系统模版操作");
			log.setOpaction("修改");
			log.setOpcontent("系统模版修改");
			log.setOrgid(user.getUserid());
			log.setRemark("系统模版修改事件");
			map.put("tempid", tempid);
			map.put("departid", departid);
			map.put("tempname", tempname);
			map.put("functionname", functionname);
			map.put("content", content);
			map.put("temptype", temptype);
			map.put("generaltype", generaltype);
			if(sn!=null){
				map.put("sn", sn);
			}
			if(!StringNumberUtil.isNullOrEmpty(findid))
			map.put("findid", Integer.parseInt(findid));
			map.put("optime", date);
			map.put("opid", user.getUserid());
			Properties jypro = new GetProperty().bornProp(GkzxCommon.DATABASETYPE, null);
			boolean flag = true;
			if(jypro!=null){
				String provincecode = jypro.getProperty("provincecode");
				if("fy".equals(provincecode)){//法院保存模版 要先判断是公有还是私有
					TbsysTemplate te = systemModelService.getCourtTemplateById(map);
					String gtype = te.getGeneraltype();
					if("5".equals(gtype)&&StringNumberUtil.isEmpty(sn)){//私有的保存在TBSYS_COURT_USER_TEMPLATE 这个表中
						flag = false;
						String updateorinsert = systemModelService.getCourtUserTemplateById(user.getUserid(),tempid);
						if(updateorinsert!=null && "insert".equals(updateorinsert)){
							systemModelService.insertCourtUserTemplate(map);
						}else if("update".equals(updateorinsert)){
							systemModelService.updateCourtUserTemplateById(map);
						}
					}
				}
			}
			if(flag){
				countnum = systemModelService.updateTemplateDetail2(map);
			}
		}
		if(countnum == 1) status = 1;//如果新增或修改成功，日志记录成功
		log.setStatus(status);
		try {
			logService.add(log, user);
		} catch (Exception e) {
			// 吃掉异常
		}
		return countnum;
	}
	/**
	 * 查看、修改模版时回显数据
	 * @author liuchaoyang
	 * 2014-7-22  11:06:45
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/getModelDetail")
	@ResponseBody
	public HashMap getModelDetail(HttpServletRequest request) {
		HashMap map = new HashMap();
		try{
			String tempid = request.getParameter("tempid");
			SystemUser user = getLoginUser(request);
			TbsysTemplate template = systemModelService.getTemplateAndDepartid(tempid,user.getDepartid());
			if (template != null) {
				//法院用
				Properties jypro = new GetProperty().bornProp(GkzxCommon.DATABASETYPE, null);
				if(jypro!=null){
					String provincecode = jypro.getProperty("provincecode");
					if("fy".equals(provincecode)){//法院模版打开 判断是私有共有 私有去取TBSYS_COURT_USER_TEMPLATE表中的数据。
						String gt = template.getGeneraltype();
						if(gt!=null && "5".equals(gt)){//私有
							Map<String,Object> map2 = systemModelService.getCourtUserTemplateTextById(tempid,user.getUserid());
							if(map2!=null){
								Clob clob =(Clob)map2.get("TMTEXT");
								if (clob != null) {
									String tmtext = clob.getSubString(1, (int)clob.length());
									if(tmtext!=null){
										template.setContent(tmtext);
									}
								}
							}
						}
					}
				}//--
				map.put("tempid", template.getTempid());
				map.put("functionname", template.getFunctionname());
				map.put("tempname", template.getTempname());
				map.put("content", template.getContent());
				map.put("findid", template.getFindid());
				map.put("temptype", template.getTemptype());
				map.put("generaltype", template.getGeneraltype());
				map.put("sn", template.getSn());
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}
	
	/**
	 * 删除数据
	 * @author liuchaoyang
	 * 2014-7-22  11:06:45
	 */
	@RequestMapping(value = "/deleteModelDetail")
	@ResponseBody
	public int deleteModelDetail(HttpServletRequest request) {
		int result = 0;
		short status = 0;
		//省局部门id
		SystemUser user = getLoginUser(request);
		String tempid = request.getParameter("tempid");
		String departid = request.getParameter("departid");
		result = systemModelService.deleteTemplateDetail(tempid,departid);
		SystemLog log = new SystemLog();
		log.setLogtype("系统模版操作");
		log.setOpaction("删除");
		log.setOpcontent("系统模版删除");
		log.setOrgid(user.getUserid());
		log.setRemark("系统模版删除事件");
		if(result == 1) status = 1;//如果新增或修改成功，日志记录成功
		log.setStatus(status);
		try {
			logService.add(log, user);
		} catch (Exception e) {
			// 吃掉异常
		}
		return result;
	}
	
	@RequestMapping(value = "/getSystemTemplateStr")
	@ResponseBody
	public Object getSystemTemplateStr(HttpServletRequest request) {
		String tempid = request.getParameter("tempid");
		if(StringNumberUtil.notEmpty(tempid)){
			SystemUser user = getLoginUser(request);
			TbsysTemplate template = systemModelService.getTemplateAndDepartid(tempid,user.getDepartid());
			if(null!=template){
				return template.getContent();
			}
			return "";
		}
		
		return "";
	}
	/**
	 * 获取模板类型
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/ajaxGettemptype")
	@ResponseBody
	public List<HashMap> ajaxGettemptype(HttpServletRequest request){
		String codetype = request.getParameter("codetype");
		List<HashMap> list = systemModelService.ajaxGettemptype(codetype);
		return list;
	}	
	/**
	 * 获取模板id
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/ajaxGetTempid.json")
	@ResponseBody
	public int ajaxGetTempid(HttpServletRequest request){
		int tempid = systemModelService.ajaxGetTempid();
		return tempid;
	}	
	
	/**
	 * 获取法院的系统模版 和目前监狱使用方式差别较大，分离！
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/getCourtSystemModelList")
	@ResponseBody
	public HashMap<String, Object> getCourtSystemModelList(HttpServletRequest request) {
		HashMap<String, Object> result = new HashMap<String, Object>();
		String type = request.getParameter("type");
		String key = request.getParameter("key");
		String sortField = request.getParameter("sortField");
		String sortOrder = request.getParameter("sortOrder");
		int pageIndex = Integer.valueOf(request.getParameter("pageIndex"));
		int pageSize = Integer.valueOf(request.getParameter("pageSize"));
		SystemUser user = getLoginUser(request);// 获取当前登录的用户
		Map map = new HashMap();
		map.put("start", pageIndex * pageSize + 1);
		map.put("end", (pageIndex +1)* pageSize);
		map.put("key", key);
		map.put("departid", user.getDepartid());
		map.put("sortField", sortField);
		map.put("sortOrder", sortOrder);
		map.put("type", type);
		// 查询出所有数据集合
		List<Map<String,Object>> list = systemModelService.getCourtSystemModelList(map);
		// 查询数据总数
		int count = systemModelService.getCourtSystemModelCount(map);
		result.put("total", count);
		result.put("data", list);
		return result;
	}
	
	/**
	 * 删除法院系统模版数据
	 */
	@RequestMapping(value = "/deleteCourtModelDetail")
	@ResponseBody
	public int deleteCourtModelDetail(HttpServletRequest request) {
		int result = 0;
		short status = 0;
		//省局部门id
		SystemUser user = getLoginUser(request);
		String tempid = request.getParameter("tempid");
		String departid = request.getParameter("departid");
		result = systemModelService.deleteCourtModelDetail(tempid,departid);
		SystemLog log = new SystemLog();
		log.setLogtype("系统模版操作");
		log.setOpaction("删除");
		log.setOpcontent("系统模版删除");
		log.setOrgid(user.getUserid());
		log.setRemark("系统模版删除事件");
		if(result == 1) status = 1;//如果新增或修改成功，日志记录成功
		log.setStatus(status);
		try {
			logService.add(log, user);
		} catch (Exception e) {
			// 吃掉异常
		}
		return result;
	}
}
