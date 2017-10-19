package com.sinog2c.mvc.controller.penaltyPerform.inPrisonManagement;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.gkzx.common.GkzxCommon;
import com.gkzx.common.LogCommon;
import com.sinog2c.model.system.SystemLog;
import com.sinog2c.model.system.SystemOrganization;
import com.sinog2c.model.system.SystemUser;
import com.sinog2c.model.system.TbsysDocument;
import com.sinog2c.model.system.TbsysTemplate;
import com.sinog2c.mvc.controller.base.ControllerBase;
import com.sinog2c.service.api.system.SystemLogService;
import com.sinog2c.service.api.system.SystemModelService;
import com.sinog2c.service.api.system.SystemOrganizationService;
import com.sinog2c.service.api.system.TbsysDocumentService;
import com.sinog2c.util.common.DateUtil;
import com.sinog2c.util.common.StringNumberUtil;

/**
 * @author kexz
 *收监日志
 * 2014-7-17
 */
@Controller
@RequestMapping("/inTheLog")
public class InTheLogController extends ControllerBase {
	@Resource
	private SystemModelService systemModelService;
	@Resource
	private SystemOrganizationService systemOrganizationService;
	@Autowired
	private TbsysDocumentService tbsysDocumentService;
	@Resource
	public SystemLogService logService;
	/**
	 * 跳转收监日志列表页
	 * 
	 * @author hzl
	 * @param request
	 * @return ModelAndView
	 */
	@RequestMapping("/toInTheLog.page")
	public ModelAndView toInTheLog(HttpServletRequest request){
		returnResourceMap(request);
		return  new ModelAndView("penaltyPerform/inPrisonManagement/inTheLog");
	}
	/**
	 * 方法描述：列表页面
	 * 
	 * @author 
	 */
	@RequestMapping("/getInTheLogList.json")
	@ResponseBody
	public HashMap<String, Object> getInTheLogList(HttpServletRequest request) {
		SystemUser user = getLoginUser(request);	
		//获取当前登录的用户
		String deptid=user.getDepartid();
		HashMap<String, Object> result = new HashMap<String, Object>();
		String tempid = request.getParameter("tempid");
		String key = request.getParameter("key")==null?"":request.getParameter("key"); 
		try {
			key = java.net.URLDecoder.decode(key , "UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		String sortField = request.getParameter("sortField");
		String sortOrder = request.getParameter("sortOrder");
		int pageIndex = Integer.valueOf(request.getParameter("pageIndex"));
		int pageSize = Integer.valueOf(request.getParameter("pageSize"));
		List<TbsysDocument> list = tbsysDocumentService.getTbsysDocumentList(pageIndex, pageSize, key, tempid, "",deptid, sortField, sortOrder);
		int count = tbsysDocumentService.getCount(key, tempid, "", deptid);
		result.put("total", count);
		result.put("data", list);
		return result;
	}
	
	/**
	 * 跳转收监日志新增页面
	 * 
	 * @author liuxin
	 * @param request
	 * @return ModelAndView
	 */
	@RequestMapping("/inTheLogAdd.page")
	public ModelAndView toInLogAdd(HttpServletRequest request){
		JSONArray docconent = new JSONArray();
		String tempid = request.getParameter("tempid");
		request.setAttribute("tempid", tempid);
		//获取当前登录的用户
		SystemUser user = getLoginUser(request);
		String deptid=user.getDepartid();
		TbsysTemplate template = systemModelService.getTemplateAndDepartid(tempid, deptid);
		//通过部门id去找所在单位名称
		SystemOrganization org = systemOrganizationService.getByOrganizationId(deptid);
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("sdid",org.getName());
		map.put("detaindate", DateUtil.dateFormatForAip(new Date()));
		map.put("detainpolice", user.getName());
		if (template != null) {
			docconent.add(template.getContent());
		}
		//转json
		request.setAttribute("formDatajson", new JSONObject(map).toString());
		request.setAttribute("formcontent", docconent.toString());
		returnResourceMap(request);
		return  new ModelAndView("penaltyPerform/inPrisonManagement/inTheLogAdd");
	}
	/**
	 * 修改/保存大字段
	 * @author liuxin
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/saveInTheLog.json")
	@ResponseBody
	public String saveInlog(HttpServletRequest request){
		int countnum = 0;//保存结果：0、失败，1、成功
		short status = 0;
		Date date = new Date(); 
		String introduction = "";
		SystemLog log = new SystemLog();
		SystemUser user = getLoginUser(request);
		TbsysDocument document = new TbsysDocument();
		String operator = request.getParameter("operator");
		String tempid = request.getParameter("tempid")==null?"":request.getParameter("tempid");
		String docid = request.getParameter("docid")==null?"":request.getParameter("docid");
		String content = request.getParameter("content")==null?"":request.getParameter("content");
		String noteinfo = request.getParameter("noteinfo")==null?"":request.getParameter("noteinfo");
		if (noteinfo != null) {
			ArrayList<Object> list = (ArrayList<Object>) JSON.parseArray(noteinfo, Object.class);
			if(list!=null && list.size()>0){
				Map<String,String> map = (Map<String, String>)list.get(0);
				introduction = map.get("sdid") + map.get("detaindate")+LogCommon.SHOUJIANRIZHI+":";
				if(StringNumberUtil.isNullOrEmpty(map.get("detainnum")) && StringNumberUtil.isNullOrEmpty(map.get("detainzannonum"))
						&& StringNumberUtil.isNullOrEmpty(map.get("detainnonum"))){
					introduction += "暂无相关人数统计";
				}else{
					if(!map.get("detainnum").equalsIgnoreCase("")){
						introduction += "收监" + map.get("detainnum")+ "人  ";
					}
					if(!map.get("detainzannonum").equalsIgnoreCase("")){
						introduction += "不得收监" + map.get("detainzannonum")+ "人  ";
					}
					if(!map.get("detainnonum").equalsIgnoreCase("")){
						introduction += "不予收监" + map.get("detainnonum")+ "人  " ;
					}
				}
			}
		}
		if(GkzxCommon.NEW.equals(operator)){
			document.setDepartid(user.getOrgid());
			document.setTempid(tempid);
			document.setIntroduction(introduction);
			document.setContent(content);
			document.setOpid(user.getUserid());
			document.setOptime(date);
			log.setLogtype(LogCommon.SHOUJIANRIZHI+LogCommon.OPERATE);
			log.setOpaction(LogCommon.ADD);
			log.setOpcontent(LogCommon.SHOUJIANRIZHI+LogCommon.ADD);
			log.setOrgid(user.getUserid());
			log.setRemark(LogCommon.SHOUJIANRIZHI+LogCommon.ADD);
			countnum = tbsysDocumentService.saveTbsysDocument(document);
		}else if(GkzxCommon.EDIT.equals(operator)){
			document.setDocid(Integer.parseInt(docid));
			document.setIntroduction(introduction);
			document.setContent(content);
			document.setOpid(user.getUserid());
			document.setOptime(date);
			log.setLogtype(LogCommon.SHOUJIANRIZHI+LogCommon.OPERATE);
			log.setOpaction(LogCommon.EDIT);
			log.setOpcontent(LogCommon.SHOUJIANRIZHI+LogCommon.EDIT);
			log.setOrgid(user.getUserid());
			log.setRemark(LogCommon.SHOUJIANRIZHI+LogCommon.EDIT);
			countnum = tbsysDocumentService.updateTbsysDocument(document);
		}
		if(countnum == 1) status = 1;//如果新增或修改成功，日志记录成功
		log.setStatus(status);
		try {
			logService.add(log, user);
		} catch (Exception e) {
			// 吃掉异常
		}
		return "";
	}
	
	/**
	 * 查看、修改收监日志时，根据文书Id查询出大字段回显
	 * @author 
	 */
	@RequestMapping(value = "/inTheLogLook.page")
	public ModelAndView editorlook(HttpServletRequest request) {
		returnResourceMap(request);
		JSONArray docconent = new JSONArray();
		String docid = request.getParameter("docid");
		request.setAttribute("docid", docid);
		TbsysDocument document = tbsysDocumentService.getTbsysDocument(docid, null, null,"");
		if (document != null) {
			docconent.add(document.getContent());
		}
		request.setAttribute("formcontent", docconent.toString());
		return new ModelAndView("penaltyPerform/inPrisonManagement/inTheLogAdd");
	}
	
	/**
	 * 根据文书Id删除数据
	 * @author liuchaoyang
	 * 2014-7-24 17:11:45
	 */
	@RequestMapping(value = "/inTheLogDelete.json")
	@ResponseBody
	public int inLogDelete(HttpServletRequest request) {
		int result = 0;
		short status = 0;
		String docid = request.getParameter("docid");
		result = tbsysDocumentService.deleteTbsysDocument(docid);
		SystemUser user = getLoginUser(request);
		SystemLog log = new SystemLog();
		log.setLogtype(LogCommon.SHOUJIANRIZHI+LogCommon.OPERATE);
		log.setOpaction(LogCommon.DEL);
		log.setOpcontent(LogCommon.SHOUJIANRIZHI+LogCommon.DEL);
		log.setOrgid(user.getUserid());
		log.setRemark(LogCommon.SHOUJIANRIZHI+LogCommon.DEL);
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
