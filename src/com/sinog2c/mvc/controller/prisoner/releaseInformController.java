package com.sinog2c.mvc.controller.prisoner;

import java.text.DateFormat;
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
import org.springframework.web.servlet.view.InternalResourceView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.gkzx.common.LogCommon;
import com.sinog2c.model.prisoner.TbprisonerBaseinfo;
import com.sinog2c.model.system.SystemLog;
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
import com.sinog2c.util.common.StringNumberUtil;
/**
 *出监管理>假释释放> 释放告知书
 * @author 
 *
 */
@Controller
public class releaseInformController extends ControllerBase {
	@Autowired
	private SystemModelService systemModelService;
	@Autowired
	private SystemOrganizationService systemOrganizationService;
	@Autowired
	private TbsysDocumentService tbsysDocumentService;
	@Resource
	public SystemLogService logService;
	@Autowired
	private PrisonerService prisonerService;
	@Autowired
	private ChooseCriminalService chooseCriminalService;
	
	@RequestMapping(value = "/releaseInformchoose")
	public ModelAndView releaseInformchoose(HttpServletRequest request) {
		returnResourceMap(request);
		return new ModelAndView(new InternalResourceView(
		"/WEB-INF/JSP/chooseCriminal/releaseInformChoose.jsp"));
	}
//	/**
//	 * 获取罪犯列表
//	 * @author liyufeng
//	 * 2014-7-30 20:37:45
//	 */
//	@RequestMapping(value = "/getreleaseInformList")
//	@ResponseBody
//	public Object getreleaseInformList(HttpServletRequest request,
//			HttpServletResponse response){
//		Map<String, Object> resultmap = new HashMap<String,Object>();
//		List<Map<String,Object>> data = new ArrayList<Map<String,Object>>();
//		try {
//			String key = request.getParameter("key")==null?"":request.getParameter("key");
//			String tempid = request.getParameter("tempid")==null?"":request.getParameter("tempid");//取得模板编号
//			int pageIndex =(Integer)(request.getParameter("pageIndex")==null? "":Integer.parseInt(request.getParameter("pageIndex")));
//			int pageSize = (Integer)(request.getParameter("pageSize")==null? "":Integer.parseInt(request.getParameter("pageSize")));  
//			String sortField = request.getParameter("sortField");
//			String sortOrder = request.getParameter("sortOrder");
//			int start = pageIndex * pageSize + 1;
//			int end = start + pageSize -1;
//			Map<String,Object> map = new HashMap<String,Object>();
//			map.put("tempid",tempid);
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
//			
//		}catch (Exception e) {
//		}
//		return resultmap;
//	}
	
	/*
	 * 表单添加页面
	 */
	@RequestMapping("/releaseInform")
	public  ModelAndView releaseInform(HttpServletRequest request){
		returnResourceMap(request);
		String crimid="";
		
		String indexFlagStr = request.getParameter("indexFlag");
		int indexFlag = StringNumberUtil.notEmpty(indexFlagStr) ? Integer.parseInt(indexFlagStr) : 0;
		request.setAttribute("indexFlag", indexFlag);
		
		String id = request.getParameter("id");//获取罪犯编号字符串，解析成数组
		request.setAttribute("id", id);
		String[] ids = id.split(",");
		crimid = ids[indexFlag];//罪犯编号就从数组里取出第一个罪犯编号
		
		String menuid=request.getParameter("menuid");
		String tempid = "SDXF_ZFSF_JSGZS";
		JSONArray docconent = new JSONArray();
		TbsysDocument document = tbsysDocumentService.getTbsysDocument(null,tempid, crimid,null);
		if (document != null) {
			docconent.add(document.getContent());
			request.setAttribute("docid", document.getDocid());
		}else{
			SystemUser user = getLoginUser(request);
			DateFormat format = new SimpleDateFormat("yyyy");
			String departid = user.getDepartid();
			TbprisonerBaseinfo info = prisonerService.getBasicInfoByCrimid(crimid);//查询犯人基本信息
			String xuhao = tbsysDocumentService.getXuHao(null, tempid, null, departid);
			TbsysTemplate template = systemModelService.getTemplateAndDepartid(tempid, departid);
			if (template != null) {
				docconent.add(template.getContent());
			}
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("text1", "假释");
			map.put("text10",xuhao);
			map.put("cbiname", info.getName());
			map.put("text6", user.getOrganization().getShortname());
			map.put("text7", format.format(new Date()));
			request.setAttribute("formDatajson", new JSONObject(map).toString());
		}
		request.setAttribute("tempid", tempid);
		request.setAttribute("crimid", crimid);
		request.setAttribute("menuid", menuid);
		request.setAttribute("formcontent", docconent.toString());
		return new ModelAndView(new InternalResourceView("/WEB-INF/JSP/aip/releaseInformAdd.jsp"));
	} 
	
	@RequestMapping(value = "/savereleaseInform")
	@ResponseBody
	public int savereleaseInform(HttpServletRequest request){
		int countnum = 0;//保存结果：0、失败，1、成功
		short status = 0;
		Date date = new Date(); 
		SystemLog log = new SystemLog();
		SystemUser user = getLoginUser(request);
		TbsysDocument document = new TbsysDocument();
		String tempid = request.getParameter("tempid")==null?"":request.getParameter("tempid");
		String docid = request.getParameter("docid")==null?"":request.getParameter("docid");
		String introduction = LogCommon.JIASHIGAOZHISHU;
		String content = request.getParameter("content")==null?"":request.getParameter("content");
	
		if(docid==null || "".equals(docid)){
			String crimid = request.getParameter("crimid");
			document.setCrimid(crimid);
			document.setDepartid(user.getDepartid());
			document.setTempid(tempid);
			document.setIntroduction("编号"+crimid+introduction);
			document.setContent(content);
			document.setOpid(user.getUserid());
			document.setOptime(date);
			log.setLogtype(introduction+LogCommon.OPERATE);
			log.setOpaction(LogCommon.ADD);
			log.setOpcontent(introduction+LogCommon.ADD);
			log.setOrgid(user.getUserid());
			log.setRemark(introduction+LogCommon.ADD+LogCommon.EVENT);
			countnum = tbsysDocumentService.saveTbsysDocument(document);
		}else {
			document.setDocid(Integer.parseInt(docid));
			document.setContent(content);
			document.setOpid(user.getUserid());
			document.setOptime(date);
			log.setLogtype(introduction+LogCommon.OPERATE);
			log.setOpaction(LogCommon.EDIT);
			log.setOpcontent(introduction+LogCommon.EDIT);
			log.setOrgid(user.getUserid());
			log.setRemark(introduction+LogCommon.EDIT+LogCommon.EVENT);
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
	
}

