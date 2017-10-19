package com.sinog2c.mvc.controller.outexecute;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.InternalResourceView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.gkzx.common.LogCommon;
import com.sinog2c.model.system.SystemLog;
import com.sinog2c.model.system.SystemOrganization;
import com.sinog2c.model.system.SystemUser;
import com.sinog2c.model.system.TbsysDocument;
import com.sinog2c.model.system.TbsysTemplate;
import com.sinog2c.mvc.controller.base.ControllerBase;
import com.sinog2c.service.api.penaltyPerform.ChooseCriminalService;
import com.sinog2c.service.api.system.SystemLogService;
import com.sinog2c.service.api.system.SystemModelService;
import com.sinog2c.service.api.system.SystemOrganizationService;
import com.sinog2c.service.api.system.TbsysDocumentService;
import com.sinog2c.util.common.DateUtil;
import com.sinog2c.util.common.StringNumberUtil;

/**
 * 保外就医通知书回执
 * 
 * @author wangxf
 * 
 */
@Controller
public class ReturnOutDoctorInform extends ControllerBase {
	@Autowired
	private SystemModelService systemModelService;
	@Autowired
	private SystemOrganizationService systemOrganizationService;
	@Autowired
	private TbsysDocumentService tbsysDocumentService;
	@Resource
	public SystemLogService logService;
	@Autowired
	private ChooseCriminalService chooseCriminalService;

	/**
	 * 罪犯选择页面
	 * @param request
	 * @return
	 */
	@RequestMapping("/returnOutDoctorInformChoose")
	public  ModelAndView returnOutDoctorInformChoose(HttpServletRequest request){
		returnResourceMap(request);
		return new ModelAndView(new InternalResourceView("WEB-INF/JSP/chooseCriminal/returnOutDoctorInformChoose.jsp"));
	}
	
	
//	/**
//	 * 方法描述：获取罪犯处理列表
//	 * @author kexz
//	 * 2014-7-30 20:37:45
//	 */  
//	@RequestMapping(value = "/getReturnOutDoctorList")
//	@ResponseBody
//	public Object getReturnOutDoctorList(HttpServletRequest request){
//		Map<String, Object> resultmap = new HashMap<String,Object>();
//		List<TbprisonerBaseinfo> data = new ArrayList<TbprisonerBaseinfo>();
//		try {
//			String key = request.getParameter("key")==null?"":request.getParameter("key");//取得参数
//			String tempid = request.getParameter("tempid")==null?"":request.getParameter("tempid");//取得模板编号
//			int pageIndex =(Integer)(request.getParameter("pageIndex")==null? "":Integer.parseInt(request.getParameter("pageIndex")));
//			int pageSize = (Integer)(request.getParameter("pageSize")==null? "":Integer.parseInt(request.getParameter("pageSize")));     
//			String sortField = request.getParameter("sortField");
//			String sortOrder = request.getParameter("sortOrder");
//			int start = pageIndex * pageSize + 1;
//			int end = start + pageSize -1;
//			Map<String,Object> map = new HashMap<String,Object>();
//			map.put("tempid", tempid);
//			map.put("key", key);
//			map.put("orgid", getLoginUser(request).getOrgid());
//	    	map.put("sortField", sortField);
//	    	map.put("sortOrder", sortOrder);
//	    	map.put("start", String.valueOf(start));
//	    	map.put("end",String.valueOf(end));
//	    	int count = chooseCriminalService.countAllByCondition(map);//根据map传参获取总条数
//	    	data= chooseCriminalService.getBasicInfoList(map);//根据map传参获取罪犯列表
//	    	resultmap.put("data", data);
//			resultmap.put("total", count);
//		}catch (Exception e) {
//		}
//		return resultmap;
//	}
	
	/**
	 * 表单添加页面
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/returnOutDoctorInform")
	public ModelAndView returnOutDoctorInform(HttpServletRequest request) {
		returnResourceMap(request);
		String crimid = request.getParameter("crimid");
		if(crimid==null || "".equals(crimid)){
			String id = request.getParameter("id");
			request.setAttribute("id", id);
			String[] ids = id.split(",");
			crimid = ids[0];
		}
		JSONArray docconent = new JSONArray();
		String tempid = "SZ_BWJY_TZSHZ";
		request.setAttribute("tempid", tempid);
		String menuid = request.getParameter("menuid");
		TbsysDocument document = tbsysDocumentService.getTbsysDocument(null, tempid, crimid, null);
		if (document != null) {
			docconent.add(document.getContent());
			request.setAttribute("docid", document.getDocid());
		}else{
			SystemUser user = getLoginUser(request);
			SystemOrganization org = systemOrganizationService.getByOrganizationId(user.getDepartid());
			TbsysTemplate template = systemModelService.getTemplateAndDepartid(tempid, user.getDepartid());
			if (template != null) {
				docconent.add(template.getContent());
			}
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("text1", DateUtil.dateFormatForAip(new Date()));
			map.put("text2", org.getFullname());
			request.setAttribute("formDatajson", new JSONObject(map).toString());
		}
		request.setAttribute("menuid", menuid);
		request.setAttribute("crimid", crimid);
		request.setAttribute("formcontent", docconent.toString());
		return new ModelAndView(new InternalResourceView("WEB-INF/JSP/aip/returnOutDoctorInform.jsp"));
		
	}

	@RequestMapping(value = "/saveOutDoctorInform")
	@ResponseBody
	public int saveOutDoctorInform(HttpServletRequest request) {
		int countnum = 0;// 保存结果：0、失败，1、成功
		short status = 0;
		Date date = new Date();
		SystemLog log = new SystemLog();
		SystemUser user = getLoginUser(request);
		TbsysDocument document = new TbsysDocument();
		String tempid = request.getParameter("tempid") == null ? "" : request.getParameter("tempid");
		String docid = request.getParameter("docid") == null ? "" : request.getParameter("docid");
		String content = request.getParameter("content") == null ? "" : request.getParameter("content");
		if (StringNumberUtil.isNullOrEmpty(docid)) {
			String crimid = request.getParameter("crimid");
			document.setCrimid(crimid);
			document.setDepartid(user.getDepartid());
			document.setTempid(tempid);
			document.setIntroduction(crimid + LogCommon.TONGZHISHUHUIZHI);
			document.setContent(content);
			document.setOpid(user.getUserid());
			document.setOptime(date);
			log.setLogtype(LogCommon.TONGZHISHUHUIZHI+LogCommon.OPERATE);
			log.setOpaction(LogCommon.ADD);
			log.setOpcontent(LogCommon.TONGZHISHUHUIZHI+LogCommon.ADD);
			log.setOrgid(user.getUserid());
			log.setRemark(LogCommon.TONGZHISHUHUIZHI+LogCommon.ADD);
			countnum = tbsysDocumentService.saveTbsysDocument(document);
		} else{
			String crimid = request.getParameter("crimid");
			document.setDocid(Integer.parseInt(docid));
			document.setIntroduction(crimid + LogCommon.TONGZHISHUHUIZHI);
			document.setContent(content);
			document.setOpid(user.getUserid());
			document.setOptime(date);
			log.setLogtype(LogCommon.TONGZHISHUHUIZHI+LogCommon.OPERATE);
			log.setOpaction(LogCommon.EDIT);
			log.setOpcontent(LogCommon.TONGZHISHUHUIZHI+LogCommon.EDIT);
			log.setOrgid(user.getUserid());
			log.setRemark(LogCommon.TONGZHISHUHUIZHI+LogCommon.EDIT);
			countnum = tbsysDocumentService.updateTbsysDocument(document);
		}
		if (countnum == 1)
			status = 1;// 如果新增或修改成功，日志记录成功
		log.setStatus(status);
		try {
			logService.add(log, user);
		} catch (Exception e) {
			// 吃掉异常
		}
		return countnum;
	}
}
