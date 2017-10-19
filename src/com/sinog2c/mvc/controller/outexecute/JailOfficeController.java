package com.sinog2c.mvc.controller.outexecute;

import java.net.URLDecoder;
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

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.gkzx.common.LogCommon;
import com.sinog2c.model.prisoner.TbprisonerBaseinfo;
import com.sinog2c.model.system.SystemLog;
import com.sinog2c.model.system.SystemOrganization;
import com.sinog2c.model.system.SystemUser;
import com.sinog2c.model.system.TbsysDocument;
import com.sinog2c.model.system.TbsysTemplate;
import com.sinog2c.mvc.controller.base.ControllerBase;
import com.sinog2c.service.api.penaltyPerform.ChooseCriminalService;
import com.sinog2c.service.api.prisoner.PrisonerService;
import com.sinog2c.service.api.system.SystemLogService;
import com.sinog2c.service.api.system.SystemModelService;
import com.sinog2c.service.api.system.SystemOrganizationService;
import com.sinog2c.service.api.system.TbsysDocumentService;

/**
 * 类描述：审查报告(报处室)
 * @author wangxf
 *
 */

@Controller
public class JailOfficeController extends ControllerBase{
	@Autowired
	private SystemModelService systemModelService;
	@Autowired
	private TbsysDocumentService tbsysDocumentService;
	@Resource
	public SystemLogService logService;
	@Autowired
	private SystemOrganizationService systemOrganizationService;
	@Autowired
	private PrisonerService prisonerService;
	@Autowired
	private ChooseCriminalService chooseCriminalService;

	/**
	 * @author kexz
	 *跳转审查报告(报处室)罪犯列表页面
	 * 2014-8-15
	 */
	@RequestMapping(value = "jailReviewOfficeList")
	public ModelAndView jailReviewOfficeList(HttpServletRequest request) {
		returnResourceMap(request);
		return new ModelAndView("chooseCriminal/jailReviewOfficeChoose");
	}
	
//	/**
//	 * 方法描述：列表页面(报处室)
//	 * 2014-8-12
//	 * @author kexz
//	 */
//	@RequestMapping(value="/getjailReviewOfficeChoose")
//	@ResponseBody
//	public HashMap<String, Object> getjailReviewOfficeChoose(HttpServletRequest request) {
//		HashMap<String, Object> resultmap = new HashMap<String, Object>();
//		List<Map<String,Object>> data = new ArrayList<Map<String,Object>>();
//		try {
//			String key = request.getParameter("key") == null ? "" : request.getParameter("key");
//			key = URLDecoder.decode(key,"UTF-8");
//			int pageIndex = (Integer) (request.getParameter("pageIndex") == null ?"": Integer.parseInt(request.getParameter("pageIndex")));
//			int pageSize = (Integer) (request.getParameter("pageSize") == null ? "": Integer.parseInt(request.getParameter("pageSize")));
//			String sortField = request.getParameter("sortField");
//			String sortOrder = request.getParameter("sortOrder");
//			int start = pageIndex * pageSize + 1;
//			int end = start + pageSize - 1;
//			Map<String, Object> map = new HashMap<String, Object>();
//			map.put("key", key);
//			map.put("orgid", getLoginUser(request).getOrgid());
//			map.put("sortField", sortField);
//			map.put("sortOrder", sortOrder);
//			map.put("start", String.valueOf(start));
//			map.put("end", String.valueOf(end));
//			int count = chooseCriminalService.countAllByCondition(map);
//			data = chooseCriminalService.getBasicInfoList(map);
//			resultmap.put("data", data);
//			resultmap.put("total", count);
//
//		} catch (Exception e) {
//		}
//		return resultmap;
//	}

	/**
	 * @author kexz
	 *文书简介lie'bia(报处室)
	 * 2014-8-15
	 */
	@RequestMapping(value = "/tojailReviewOfficeList")
	public ModelAndView tojailReviewOfficeList(HttpServletRequest request) {
		String name = request.getParameter("name")==null?"":request.getParameter("name");
		String crimid=request.getParameter("crimid");
		String idname=request.getParameter("idname");
		String id=request.getParameter("id");
		String menuid=request.getParameter("menuid");
		if(crimid==null || "".equals(crimid)){
			request.setAttribute("id", id);
			String[] ids = id == null ? null:id.split(",");
			String[] idnames = idname == null?null:idname.split(",");
			crimid = ids ==null?"":ids[0];
			name = idnames == null?"":idnames[0];
		}
		request.setAttribute("idname", idname);
		request.setAttribute("crimid", crimid);
		request.setAttribute("name", name);
		request.setAttribute("menuid", menuid);
	    return new ModelAndView("outexecute/tojailReviewOfficeList");
	}

	/**
	 * 方法描述：列表页面(报处室)
	 * 2014-8-12
	 * @author kexz
	 */
	@RequestMapping(value="/getjailReviewOfficeList")
	@ResponseBody
	public HashMap<String, Object> getjailReviewOfficeList(
			HttpServletRequest request) {
		HashMap<String, Object> result = new HashMap<String, Object>();
		String tempid = request.getParameter("tempid");
		String crimid=request.getParameter("crimid");
		String key = request.getParameter("key");
		SystemUser user = getLoginUser(request);
		//根据根据登录用户去获取对应部门id
		String deptid=user.getDepartid();
		String sortField = request.getParameter("sortField");
		String sortOrder = request.getParameter("sortOrder");
		int pageIndex = Integer.valueOf(request.getParameter("pageIndex"));
		int pageSize = Integer.valueOf(request.getParameter("pageSize"));
		List<TbsysDocument> list = tbsysDocumentService.getTbsysDocumentList(pageIndex, pageSize, key, tempid,crimid,deptid, sortField, sortOrder);
		int count = tbsysDocumentService.getCount(key, tempid,crimid, user.getDepartid());
		result.put("total", count);
		result.put("data", list);
		return result;
	}

	/**
	 * @author kexz
	 *(处室)
	 * 2014-8-12
	 */
	@RequestMapping(value="/tojailReviewOfficeAdd")
	public ModelAndView tojailReviewOfficeAdd(HttpServletRequest request){
		returnResourceMap(request);//查询资源里的按钮
		JSONArray docconent = new JSONArray();
		SystemUser user = getLoginUser(request);
		String departid=user.getDepartid();
		String tempid = request.getParameter("tempid");
		String menuid=request.getParameter("menuid");
		String crimid=request.getParameter("crimid");
		TbprisonerBaseinfo baseinfo =  prisonerService.getBasicInfoByCrimid(crimid);
		TbsysTemplate template = systemModelService.getTemplateAndDepartid(tempid, departid);
		SystemOrganization org=systemOrganizationService.getByOrganizationId(departid);
		Map<String,Object> map=new HashMap<String,Object>();
		SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMdd");
		String text2 = String.valueOf(systemModelService.getRecommendationContent("9688", user, request));
		text2=text2.replaceAll("\n", "");
		map.put("text1",org.getName());
		map.put("text2",text2);
		map.put("text7",sdf.format(new Date()));
		if (template != null) {
			docconent.add(template.getContent());
		}
		//转json
		request.setAttribute("menuid",menuid);
		request.setAttribute("tempid", tempid);
		request.setAttribute("name",baseinfo.getName());
		request.setAttribute("crimid",crimid);
		request.setAttribute("formDatajson", new JSONObject(map).toString());
		request.setAttribute("formcontent", docconent.toString());
		return new ModelAndView("aip/tojailReviewOfficeAdd");
	}

	/**
	 * 查看、修改时，根据文书Id查询出大字段回显(处室)
	 * @author kexz 
	 * 2014-8-12
	 */
	@RequestMapping(value="/editjailReviewOffice")
	public ModelAndView editjailReviewOffice(HttpServletRequest request) {
		returnResourceMap(request);//查询资源里的按钮
		JSONArray docconent = new JSONArray();
		String menuid=request.getParameter("menuid");
		String docid = request.getParameter("docid");//大字段主键
		request.setAttribute("docid", docid);
		TbsysDocument document = tbsysDocumentService.getTbsysDocument(docid, null,null, null);
		if (document != null) {
			docconent.add(document.getContent());
		}
		request.setAttribute("menuid",menuid);
		request.setAttribute("formcontent", docconent.toString());
		return new ModelAndView("aip/tojailReviewOfficeAdd");
	}

	/**
	 * 根据文书Id删除数据(处室)
	 * @author kexz
	 * 2014-8-12
	 */
	@RequestMapping(value="deletejailReviewOffice")
	@ResponseBody
	public int deletejailReviewOffice(HttpServletRequest request) {
		int result = 0;
		short status = 0;
		String docid = request.getParameter("docid");
        result = tbsysDocumentService.deleteTbsysDocument(docid);
		SystemUser user = getLoginUser(request);
		SystemLog log = new SystemLog();
		log.setLogtype(LogCommon.JWZXSCBG_CHUSHI+LogCommon.OPERATE);
		log.setOpaction(LogCommon.DEL);
		log.setOpcontent(LogCommon.JWZXSCBG_CHUSHI+LogCommon.DEL);
		log.setOrgid(user.getUserid());
		log.setRemark(LogCommon.JWZXSCBG_CHUSHI+LogCommon.DEL);
		log.setStatus(status);
		if(result == 1) status = 1;//如果新增或修改成功，日志记录成功
		log.setStatus(status);
		try {
			logService.add(log, user);
		} catch (Exception e) {
			// 吃掉异常
		}
		return result;
	}	

	/**
	 * 修改/保存大字段(处室)
	 * @author liuchaoyang
	 * 2014-7-24 17:11:45
	 */
	@RequestMapping(value="savejailReviewOffice")
	@ResponseBody
	public int savejailReviewOffice(HttpServletRequest request){
		int countnum = 0;//保存结果：0、失败，1、成功
		short status = 0;
		Date date = new Date(); 
		SystemLog log = new SystemLog();
		SystemUser user = getLoginUser(request);
		TbsysDocument document = new TbsysDocument();
		String operator = request.getParameter("temp");
		String tempid = request.getParameter("tempid")==null?"":request.getParameter("tempid");
		String docid = request.getParameter("docid")==null?"":request.getParameter("docid");
		String crimid = request.getParameter("crimid")==null?"":request.getParameter("crimid");
		//根据罪犯id获取罪犯信息
		TbprisonerBaseinfo baseinfo =  prisonerService.getBasicInfoByCrimid(crimid);
		String content = request.getParameter("content")==null?"":request.getParameter("content");
		if("new".equals(operator)){
			document.setCrimid(crimid);
			document.setDepartid(user.getDepartid());
			document.setTempid(tempid);
			document.setIntroduction("罪犯"+baseinfo.getName()+LogCommon.JWZXSCBG_CHUSHI);
			document.setContent(content);
			document.setOpid(user.getUserid());
			document.setOptime(date);
			log.setLogtype(LogCommon.JWZXSCBG_CHUSHI+LogCommon.OPERATE);
			log.setOpaction(LogCommon.ADD);
			log.setOpcontent(LogCommon.JWZXSCBG_CHUSHI+LogCommon.ADD);
			log.setOrgid(user.getUserid());
			log.setRemark(LogCommon.JWZXSCBG_CHUSHI+LogCommon.ADD);
			countnum = tbsysDocumentService.saveTbsysDocument(document);
		}else if("edit".equals(operator)){
			document.setDocid(Integer.parseInt(docid));
			document.setContent(content);
			document.setOpid(user.getUserid());
			document.setOptime(date);
			log.setLogtype(LogCommon.JWZXSCBG_CHUSHI+LogCommon.OPERATE);
			log.setOpaction(LogCommon.EDIT);
			log.setOpcontent(LogCommon.JWZXSCBG_CHUSHI+LogCommon.EDIT);
			log.setOrgid(user.getUserid());
			log.setRemark(LogCommon.JWZXSCBG_CHUSHI+LogCommon.EDIT);
			countnum = tbsysDocumentService.updateTbsysDocument(document);
		}
		log.setStatus(status);
		if(countnum == 1) status = 1;//如果新增或修改成功，日志记录成功
		log.setStatus(status);
		try {
			logService.add(log, user);
		} catch (Exception e) {
			// 吃掉异常
		}
		return countnum;
	}


}
