package com.sinog2c.mvc.controller.penaltyPerform.inPrisonManagement;

import java.text.SimpleDateFormat;
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

/**
 * @author kexz
 *收监告知
 * 2014-7-17
 */
@Controller
@RequestMapping("/toInform")
public class ToInformController extends ControllerBase{
	@Autowired
	private SystemModelService systemModelService;
	@Autowired
	private SystemOrganizationService systemOrganizationService;
	@Autowired
	private TbsysDocumentService tbsysDocumentService;
	@Resource
	public SystemLogService logService;
	/**
	 * @author kexz
	 *显示收监告知列表页
	 * 2014-7-24
	 */
	@RequestMapping("/toInformList.page")
	public ModelAndView toInformList(HttpServletRequest request){
		returnResourceMap(request);//查询资源里的按钮
		return new ModelAndView("penaltyPerform/inPrisonManagement/toInformList");
	}
	
	/**
	 * 方法描述：列表页面
	 * 2014-7-29
	 * @author kexz
	 */
	@RequestMapping("/ajaxGetToInformList.json")
	@ResponseBody
	public HashMap<String, Object> ajaxGetToInformList(HttpServletRequest request) {
		HashMap<String, Object> result = new HashMap<String, Object>();
		String tempid = request.getParameter("tempid");
		String key = request.getParameter("key")==null?"":request.getParameter("key"); 
		
		SystemUser user = getLoginUser(request);
		//根据根据登录用户去获取对应部门id
		String deptid=user.getDepartid();
		String sortField = request.getParameter("sortField");
		String sortOrder = request.getParameter("sortOrder");
		int pageIndex = Integer.valueOf(request.getParameter("pageIndex"));
		int pageSize = Integer.valueOf(request.getParameter("pageSize"));
		List<TbsysDocument> list = tbsysDocumentService.getTbsysDocumentList(pageIndex, pageSize, key, tempid,null,deptid, sortField, sortOrder);
		int count = tbsysDocumentService.getCount(key, tempid,null, user.getDepartid());
		result.put("total", count);
		result.put("data", list);
		return result;
	}
	/**
	 * @author kexz
	 *显示收监告知新增表单
	 * 2014-7-29
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/toInformAdd.page")
	public ModelAndView toInformAdd(HttpServletRequest request){
		returnResourceMap(request);//查询资源里的按钮
		
		String menuid = request.getParameter("menuid");
		request.setAttribute("menuid", menuid);
		JSONArray docconent = new JSONArray();
		String tempid = request.getParameter("tempid");
		request.setAttribute("tempid", tempid);
		String year = new SimpleDateFormat("yyyy").format(new Date());//定义时间格式获取年号
		SystemUser user = getLoginUser(request);
		String departid=user.getDepartid();
		TbsysTemplate template = systemModelService.getTemplateAndDepartid(tempid, departid);
		SystemOrganization org=systemOrganizationService.getByOrganizationId(departid);
		if(template!=null){
			String xuhao = tbsysDocumentService.getXuHao(year+GkzxCommon.YEAR, tempid, null,departid);//获取序号
			HashMap map=new HashMap();
			map.put("depname",org.getFullname()+"检察室：");
			Date date=new Date();
			map.put("text10",org.getName()+template.getTempname());//拼接标题
			map.put("sdidshotname", org.getShortname());//取部门简称	
			map.put("cinyear",year);
			map.put("xuhao", xuhao);
			map.put("username",org.getName());
			map.put("cinfillindate",DateUtil.dateFormatForAip(date));
			
			docconent.add(template.getContent());
			request.setAttribute("tempid", tempid);
			request.setAttribute("formDatajson", new JSONObject(map).toString());
			}
			
		request.setAttribute("formcontent", docconent.toString());
		return new ModelAndView("penaltyPerform/inPrisonManagement/toInformAdd");
	}
	/**
	 * 修改/保存大字段
	 * @author kexz
	 * 2014-7-29
	 */
	@RequestMapping(value = "/saveToInform.json")
	@ResponseBody
	public int saveToInform(HttpServletRequest request){
		int countnum = 0;//保存结果：0、失败，1、成功
		short status = 0;
		Date date = new Date(); 
		SystemLog log = new SystemLog();
		SystemUser user = getLoginUser(request);
		TbsysDocument document = new TbsysDocument();
		String introduction = "";//文书简介
		String operator = request.getParameter("operator");
		//通过部门id去找组织机构的信息
		SystemOrganization org=systemOrganizationService.getByOrganizationId(user.getDepartid());
		String tempid = request.getParameter("tempid")==null?"":request.getParameter("tempid");
		String docid = request.getParameter("docid")==null?"":request.getParameter("docid");
		String content = request.getParameter("content")==null?"":request.getParameter("content");
		String noteinfo = request.getParameter("noteinfo")==null?"":request.getParameter("noteinfo");
		if (noteinfo != null) {
			ArrayList list = (ArrayList) JSON.parseArray(noteinfo, Object.class);
			if(list!=null && list.size()>0){
				Map<String,String> map = (Map)list.get(0);
				introduction = map.get("cinyear")+"年"+org.getName()+"第"+map.get("xuhao")+"号"+LogCommon.SHOUJIANGAOZHI;
			}
		}	
		if(GkzxCommon.NEW.equals(operator)){			
			document.setDepartid(user.getDepartid());
			document.setTempid(tempid);
			document.setIntroduction(introduction);
			document.setContent(content);
			document.setOpid(user.getUserid());
			document.setOptime(date);
			log.setOrgid(user.getOrgid());
			log.setOpid(user.getUserid());
			log.setOpaction(LogCommon.ADD);
			log.setLogtype(LogCommon.SHOUJIANGAOZHI+LogCommon.OPERATE);
			log.setOpcontent(LogCommon.SHOUJIANGAOZHI+LogCommon.ADD);
			log.setRemark(LogCommon.SHOUJIANGAOZHI+LogCommon.ADD);
			countnum = tbsysDocumentService.saveTbsysDocument(document);
		}else if(GkzxCommon.EDIT.equals(operator)){
			document.setDocid(Integer.parseInt(docid));
			document.setContent(content);
			document.setOpid(user.getUserid());
			document.setOptime(date);
			log.setOrgid(user.getOrgid());
			log.setOpid(user.getUserid());
			log.setOpaction(LogCommon.EDIT);
			log.setLogtype(LogCommon.SHOUJIANGAOZHI+LogCommon.OPERATE);
			log.setOpcontent(LogCommon.SHOUJIANGAOZHI+LogCommon.EDIT);
			log.setRemark(LogCommon.SHOUJIANGAOZHI+LogCommon.EDIT);
			countnum = tbsysDocumentService.updateTbsysDocument(document);
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
	 * 查看、修改收监告知时，根据文书Id查询出大字段回显
	 * @author kexz 
	 * 2014-7-29
	 */
	@RequestMapping(value = "/toInformEdit.page")
	public ModelAndView editorlook(HttpServletRequest request) {
		returnResourceMap(request);//查询资源里的按钮
		JSONArray docconent = new JSONArray();
		String docid = request.getParameter("docid");//大字段主键
		request.setAttribute("docid", docid);
		TbsysDocument document = tbsysDocumentService.getTbsysDocument(docid, null,null, null);
		if (document != null) {
			docconent.add(document.getContent());
		}
		request.setAttribute("formcontent", docconent.toString());
		return new ModelAndView("penaltyPerform/inPrisonManagement/toInformAdd");
	}
	
	/**
	 * 根据文书Id删除数据
	 * @author kexz
	 * 2014-7-29
	 */
	@RequestMapping(value = "/deleteToInform.json")
	@ResponseBody
	public int deleteInfo(HttpServletRequest request) {
		int result = 0;
		short status = 0;
		String docid = request.getParameter("docid");
        result = tbsysDocumentService.deleteTbsysDocument(docid);
		SystemUser user = getLoginUser(request);
		SystemLog log = new SystemLog();
		log.setLogtype(LogCommon.SHOUJIANGAOZHI+LogCommon.EDIT);
		log.setOpaction(LogCommon.DEL);
		log.setOpcontent(LogCommon.SHOUJIANGAOZHI+LogCommon.DEL);
		log.setOrgid(user.getUserid());
		log.setRemark(LogCommon.SHOUJIANGAOZHI+LogCommon.DEL);
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
