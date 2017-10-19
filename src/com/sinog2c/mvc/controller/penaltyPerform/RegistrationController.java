package com.sinog2c.mvc.controller.penaltyPerform;

import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Calendar;
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
import com.gkzx.common.GkzxCommon;
import com.sinog2c.model.prisoner.TbprisonerBaseCrime;
import com.sinog2c.model.prisoner.TbprisonerBaseinfo;
import com.sinog2c.model.system.SystemUser;
import com.sinog2c.model.system.TbsysTemplate;
import com.sinog2c.mvc.controller.base.ControllerBase;
import com.sinog2c.service.api.flow.FlowBaseService;
import com.sinog2c.service.api.penaltyPerform.ChooseCriminalService;
import com.sinog2c.service.api.prisoner.PrisonerService;
import com.sinog2c.service.api.system.SystemCodeService;
import com.sinog2c.service.api.system.SystemModelService;
import com.sinog2c.service.api.system.SystemOrganizationService;
import com.sinog2c.service.api.system.TbsysDocumentService;
import com.sinog2c.util.common.DateUtil;
import com.sinog2c.util.common.StringNumberUtil;

/**
 * @author kexz
 * 申控检坦>登记
 * 2014-7-17
 */
@Controller 
public class RegistrationController extends ControllerBase{
	@Resource
	protected FlowBaseService flowBaseService;
	@Autowired
	private SystemModelService systemModelService;
	@Autowired
	private ChooseCriminalService chooseCriminalService;
	@Resource
	private PrisonerService prisonerService;
	@Autowired
	private SystemCodeService systemCodeService;
	@Autowired
	private TbsysDocumentService tbsysDocumentService;
	@Autowired
	private SystemOrganizationService systemOrganizationService;
	
	/**
	 * 选择罪犯页面
	 * @param request
	 * @return
	 */
	@RequestMapping("/registrationChoose")
	public ModelAndView registrationChoose(HttpServletRequest request){
		 returnResourceMap(request);
		return new ModelAndView(new InternalResourceView("WEB-INF/JSP/chooseCriminal/registrationChoose.jsp"));
	}
	
//	/**
//	 * 获取罪犯获取数据
//	 * @param request
//	 * @param response
//	 * @return
//	 */
//	@RequestMapping(value = "/getregistrationChoose")
//	@ResponseBody
//	public Object getregistrationChoose(HttpServletRequest request,HttpServletResponse response){
//		SystemUser user = getLoginUser(request);
//		Map<String, Object> resultmap = new HashMap<String,Object>();
//		List<Map<String,Object>> data = new ArrayList<Map<String,Object>>();
//		try {
//			//取得参数
//			String key = request.getParameter("key")==null?"":request.getParameter("key");
//			key = URLDecoder.decode(key,"UTF-8");
//			//分页
//			int pageIndex =(Integer)(request.getParameter("pageIndex")==null? "":Integer.parseInt(request.getParameter("pageIndex")));
//			int pageSize = (Integer)(request.getParameter("pageSize")==null? "":Integer.parseInt(request.getParameter("pageSize")));        
//			//字段排序
//			String sortField = request.getParameter("sortField");
//			String sortOrder = request.getParameter("sortOrder");
//			int start = pageIndex * pageSize + 1;
//			int end = start + pageSize -1;
//			Map<String,Object> map = new HashMap<String,Object>();  
//			map.put("key", key);
//			map.put("flowdefid", "doc_zfskjtsp");
//			map.put("orgid", user.getOrgid());
//	    	map.put("sortField", sortField);
//	    	map.put("sortOrder", sortOrder);
//	    	map.put("start", String.valueOf(start));
//	    	map.put("end",String.valueOf(end));
//	    	int count = chooseCriminalService.countAllByCondition(map);
//	    	data= chooseCriminalService.getBasicInfoList(map);
//	    	resultmap.put("data", data);
//			resultmap.put("total", count);
//		}catch (Exception e) {
//		}
//		
//		return resultmap;
//	}
	
	/**
	 * 方法描述：列表页面（流程数据）
	 * 
	 * @author 
	 */
	@RequestMapping("/ajaxGetzfskjtList")
	@ResponseBody
	public HashMap<String, Object> ajaxGetzfskjtList(HttpServletRequest request) throws Exception{
		SystemUser user = getLoginUser(request);
		HashMap<String, Object> result = new HashMap<String, Object>();
		String applyid=request.getParameter("crimid")==null? "":request.getParameter("crimid");//申请人id
		String key = request.getParameter("key")==null?"":request.getParameter("key");
		key = URLDecoder.decode(key,"UTF-8");
		//分页
		int pageIndex =(Integer)(request.getParameter("pageIndex")==null? "":Integer.parseInt(request.getParameter("pageIndex")));
		int pageSize = (Integer)(request.getParameter("pageSize")==null? "":Integer.parseInt(request.getParameter("pageSize")));        
		//字段排序
		String sortField = request.getParameter("sortField");
		String sortOrder = request.getParameter("sortOrder");
		int start = pageIndex * pageSize + 1;
		int end = start + pageSize -1;

		Map<String,Object> map = new HashMap<String,Object>();
		map.put("key", key);
		if("4".equals(user.getOrganization().getUnitlevel())){
			map.put("jqid", user.getOrganization().getOrgid());//获取当前登录的用户
		}
		map.put("departId", user.getDepartid());//获取当前登录的用户
		map.put("applyid", applyid);
		map.put("flowdefid", "doc_zfskjtsp");
    	map.put("sortField", sortField);
    	map.put("sortOrder", sortOrder);
    	map.put("start", String.valueOf(start));
    	map.put("end",String.valueOf(end));   
		List<Map> list = flowBaseService.findByMapCondition(map);
		int count = flowBaseService.countAllByCondition(map);
		result.put("total", count);
		result.put("data", list);
		return result;		
	}
	
	/**
	 * 罪犯申控检坦列表页面
	 * @param request
	 * @return
	 */
	@RequestMapping("/registration")
	public ModelAndView registration(HttpServletRequest request){
		String name = request.getParameter("name")==null?"":request.getParameter("name");
		String idname = request.getParameter("idname")==null?"":request.getParameter("idname");
		String crimid = request.getParameter("crimid")==null?"":request.getParameter("crimid");
		String menuid = request.getParameter("menuid")==null?"":request.getParameter("menuid");
		if(crimid==null || "".equals(crimid)){//如果罪犯编号为空就表示批量处理
			String id = request.getParameter("id");//获取罪犯编号字符串，解析成数组
			request.setAttribute("id", id);
			String[] ids = id == null ? null:id.split(",");
			String[] idnames = idname == null?null:idname.split(",");
			crimid = ids ==null?"":ids[0];//罪犯编号就从数组里取出第一个罪犯编号
			name = idnames == null?"":idnames[0];
		}
		request.setAttribute("idname", idname);
		request.setAttribute("crimid", crimid);
		request.setAttribute("name", name);
		request.setAttribute("menuid", menuid);
		return new ModelAndView(new InternalResourceView("WEB-INF/JSP/penaltyPerform/registration.jsp"));
	}
	/**
	 * 表单添加页面
	 * @param request
	 * @return	添加申控检坦表单表单页面
	 * @author wangxf
	 * @throws Exception 
	 */
	@RequestMapping("/toregistrationAddPage")
	public ModelAndView toregistrationAddPage(HttpServletRequest request) throws Exception{
		JSONArray docconent = new JSONArray();
		Map<String, Object> map=new HashMap<String, Object>();
		String menuid=request.getParameter("menuid");
		String crimid = request.getParameter("crimid");
		String flowdraftid = request.getParameter("flowdraftid");//流程编号
		TbprisonerBaseinfo baseinfo =  prisonerService.getBasicInfoByCrimid(crimid);
		TbprisonerBaseCrime crime =prisonerService.getCrimeByCrimid(crimid);
		if(flowdraftid != null && !"".equals(flowdraftid)){
			String docConent =  flowBaseService.getDocConentByFlowdraftId(flowdraftid);
			docconent.add(docConent);
			request.setAttribute("flowdraftid",flowdraftid);
		}else{
			String tempid = request.getParameter("tempid");
			request.setAttribute("tempid", tempid);
			SystemUser user = getLoginUser(request);
			String departinfo = user.getOrganization().getFullname();
			TbsysTemplate template = systemModelService.getTemplateAndDepartid(tempid, user.getDepartid());
			if (template != null) {
				docconent.add(template.getContent());
			}
			map.put("cbiname",baseinfo.getName());
			
			String departid = user.getDepartid();
			String jailname = systemOrganizationService.getOrginfoByOrgid(departid).getName();
			
			map.put("jianyu",jailname);
			map.put("zi",user.getPrison().getShortname());
			map.put("nian",Calendar.getInstance().get(Calendar.YEAR));
			map.put("text12", user.getName());
			map.put("hao", tbsysDocumentService.getXuHao(Calendar.getInstance().get(Calendar.YEAR)+GkzxCommon.YEAR, tempid, null,user.getDepartid()));
			map.put("riqipatch2", departinfo);
			map.put("cfcfillindate", DateUtil.dateFormatForAip(new Date()));
		}
		if(crime!=null){
			if(!StringNumberUtil.isNullOrEmpty(crime.getOrgid2())){
				request.setAttribute("orgid",crime.getOrgid2());
			}else{
				request.setAttribute("orgid",crime.getOrgid1());
			}
		}
		Map<String, Object> selectmap = new HashMap<String, Object>();
		selectmap.put("cfcitem", systemCodeService.getcodeValue("GK023"));
		request.setAttribute("menuid", menuid);
		request.setAttribute("crimid", crimid);
		request.setAttribute("flowdefid", "doc_zfskjtsp");
		request.setAttribute("applyname",baseinfo.getName() );
		request.setAttribute("applyid",crimid );
		request.setAttribute("formcontent", docconent.toString());
		request.setAttribute("formDatajson", new JSONObject(map).toString());
		request.setAttribute("selectDatajson", new JSONObject(selectmap).toString());
		return new ModelAndView(new InternalResourceView("WEB-INF/JSP/aip/registrationAdd.jsp"));
	}
	
	//申控检坦快速查询
	@RequestMapping("/registrationFastQuery")
	public ModelAndView registrationFastQuery(){
		return new ModelAndView(new InternalResourceView("WEB-INF/JSP/penaltyPerform/registrationFastQuery.jsp"));
	}
	/**
	 * 
	 * 跳转到转递单表单页面
	 */
	@RequestMapping("/toregistrationListPage")
    public ModelAndView toregistrationListPage(HttpServletRequest request){
		JSONArray docconent = new JSONArray();
		SystemUser user = getLoginUser(request);
		Map<String, Object> map=new HashMap<String, Object>();
		String crimid=request.getParameter("crimid");
		String menuid=request.getParameter("menuid");
		String tempid = request.getParameter("tempid");
		TbprisonerBaseinfo baseinfo =  prisonerService.getBasicInfoByCrimid(crimid);
		TbprisonerBaseCrime crime =prisonerService.getCrimeByCrimid(crimid);
		map.put("cbiname", baseinfo.getName());
		map.put("cbigender", baseinfo.getGender());
		map.put("cbibirthday", DateUtil.dateFormatForAip(baseinfo.getBirthday()));
		map.put("anyouhuizong", crime.getMaincase());
		map.put("zhuxing", crime.getPunishmenttype());
		map.put("text1", Calendar.getInstance().get(Calendar.YEAR));
		
		String departid = user.getDepartid();
		String jailname = systemOrganizationService.getOrginfoByOrgid(departid).getName();
		
		map.put("text29",jailname);
		map.put("sdidshotname",user.getPrison().getShortname());
		map.put("text8",user.getPrison().getFullname());
		map.put("xuhao",tbsysDocumentService.getXuHao(Calendar.getInstance().get(Calendar.YEAR)+GkzxCommon.YEAR, tempid, null,user.getDepartid()));
		request.setAttribute("tempid", tempid);
		request.setAttribute("menuid",menuid);
		TbsysTemplate template = systemModelService.getTemplateAndDepartid(tempid, user.getDepartid());
		if (template != null) {
			docconent.add(template.getContent());
		}
		request.setAttribute("formcontent", docconent.toString());
		request.setAttribute("formDatajson", new JSONObject(map).toString());
		return new ModelAndView(new InternalResourceView("WEB-INF/JSP/aip/registrationAdd.jsp"));
	}

}
