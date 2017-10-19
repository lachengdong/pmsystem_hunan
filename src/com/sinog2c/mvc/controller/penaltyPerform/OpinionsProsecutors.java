package com.sinog2c.mvc.controller.penaltyPerform;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.InternalResourceView;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sinog2c.model.prisoner.TbprisonerBaseinfo;
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
 * @author kexz
 *检察意见
 * 2014-7-17
 */
@Controller
public class OpinionsProsecutors extends ControllerBase {
	@Autowired
	private ChooseCriminalService chooseCriminalService;
	@Autowired
	private SystemModelService systemModelService;
	@Autowired
	private TbsysDocumentService tbsysDocumentService;
	@Autowired
	public SystemLogService logService;
	@Autowired
	private SystemOrganizationService systemOrganizationService;
	@Autowired
	private PrisonerService prisonerService;
	
	@RequestMapping("/opinionsProsecutors")
	public ModelAndView opinionsProsecutors(){
		return new ModelAndView(new InternalResourceView("WEB-INF/JSP/penaltyPerform/opinionsProsecutors.jsp"));
	}
	
	@RequestMapping("/getJianChaInfo")
	@ResponseBody
	public Object getJianChaInfo(HttpServletRequest request){
		Map<String, Object> resultmap = new HashMap<String,Object>();
		try{
			SystemUser user = getLoginUser(request);
			String key = request.getParameter("key");
			String startdate=request.getParameter("startdate")==null?"":request.getParameter("startdate");
			String enddate=request.getParameter("enddate")==null?"":request.getParameter("enddate");
			int pageIndex =(Integer)(request.getParameter("pageIndex")==null? "":Integer.parseInt(request.getParameter("pageIndex")));
			int pageSize = (Integer)(request.getParameter("pageSize")==null? "":Integer.parseInt(request.getParameter("pageSize")));
			String sortField = request.getParameter("sortField");
			String sortOrder = request.getParameter("sortOrder");
			int start = pageIndex * pageSize + 1;
			int end = start + pageSize -1;
			
			Map<String,Object> map = new HashMap<String,Object>();
	    	map.put("sortField", sortField);
	    	map.put("sortOrder", sortOrder);
	    	map.put("start", String.valueOf(start));
	    	map.put("end",String.valueOf(end));
			map.put("departid", user.getDepartid());
			map.put("orgid", user.getOrgid());
			map.put("key", key);
			map.put("startdate", startdate);
			map.put("enddate", enddate);
			List<Map> data=chooseCriminalService.getJianChaInfo(map);
			int count=chooseCriminalService.getJianChaInfoCount(map);
			resultmap.put("data", data);
			resultmap.put("total", count);
		}catch (Exception e) {
			e.printStackTrace();
		}
		return resultmap;
	}
	/*
	 * 监督意见页面
	 */
	@RequestMapping("/editJianDuYiJian")
	public ModelAndView editJianDuYiJian(HttpServletRequest request){
		returnResourceMap(request);//查询资源里的按钮
		JSONArray docconent = new JSONArray();
		SystemUser user = getLoginUser(request);
		String departid=user.getDepartid();
		String tempid = request.getParameter("templeid");
		String menuid=request.getParameter("menuid");
		String crimid=request.getParameter("crimid");
		String mbid=request.getParameter("mbid");
		
		TbsysDocument document = tbsysDocumentService.getTbsysDocument(null, tempid,crimid, null);
		TbprisonerBaseinfo baseinfo =  prisonerService.getBasicInfoByCrimid(crimid);
		Map<String,Object> map=new HashMap<String,Object>();
		if(null!=document){
			docconent.add(document.getContent());
			request.setAttribute("formcontent", docconent.toString());
		}else{
		TbsysTemplate template = systemModelService.getTemplateAndDepartid(tempid, departid);
		SystemOrganization org=systemOrganizationService.getByOrganizationId(departid);
		SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMdd");
		String text2 = String.valueOf(systemModelService.getRecommendationContent(mbid, user, request));
		text2=text2.replaceAll("\n", "");
		map.put("text1",org.getName());
		map.put("content",text2);
		map.put("text7",sdf.format(new Date()));
		map.put("jianyuname",org.getName() );
		map.put("name", org.getCity());
		map.put("name2", org.getCity());
		map.put("listyear", new SimpleDateFormat("yyyy").format(new Date()));
		if (template != null) {
			docconent.add(template.getContent());
		}
		//转json
		request.setAttribute("formcontent", docconent.toString());
		request.setAttribute("formDatajson", new JSONObject(map).toString());
		}
		request.setAttribute("menuid",menuid);
		request.setAttribute("tempid", tempid);
		request.setAttribute("name",baseinfo.getName());
		request.setAttribute("crimid",crimid);
		return new ModelAndView("outexecute/jianchajianduaippage");
	}
	
	
	@RequestMapping("/saveJianChaData")
	@ResponseBody
	public  String saveJianChaData(HttpServletRequest request){
		SystemUser user = getLoginUser(request);
		Date date = new Date(); 
		String templeid=request.getParameter("tempid");
		String filedata=request.getParameter("content");
		String crimid=request.getParameter("crimid");
		String docid=request.getParameter("docid");
		String name=request.getParameter("name");
		int count=tbsysDocumentService.getCount(null, templeid, crimid, null);
		TbsysDocument document = new TbsysDocument();
		document.setTempid(templeid);
		document.setIntroduction(crimid+"的检查监督建议");
		document.setCrimid(crimid);
		document.setContent(filedata);
		document.setOpid(user.getUserid());
		document.setOptime(date);
		document.setDepartid(user.getDepartid());
		document.setText1("");
		if(count>0){
			tbsysDocumentService.updateTbsysDocuments(document);
		}else{
			tbsysDocumentService.saveTbsysDocument(document);
		}
		return "success";
	}
}
