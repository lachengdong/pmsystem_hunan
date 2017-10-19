package com.sinog2c.mvc.controller.outexecute;

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
import com.sinog2c.model.prisoner.TbprisonerBaseCrime;
import com.sinog2c.model.prisoner.TbprisonerBaseinfo;
import com.sinog2c.model.system.SystemUser;
import com.sinog2c.model.system.TbsysTemplate;
import com.sinog2c.mvc.controller.base.ControllerBase;
import com.sinog2c.service.api.flow.FlowBaseService;
import com.sinog2c.service.api.penaltyPerform.ChooseCriminalService;
import com.sinog2c.service.api.prisoner.PrisonerService;
import com.sinog2c.service.api.system.SystemModelService;
import com.sinog2c.service.api.system.TbsysDocumentService;
import com.sinog2c.util.common.DateUtil;


/**
 * 法定不准出境人员通报备案通知书
 *
 */
@Controller
public class LegalNoExitPersonnelNotice extends ControllerBase{
	@Autowired
	private SystemModelService systemModelService;
	@Resource
	protected FlowBaseService flowBaseService;
	@Autowired
	private PrisonerService prisonerService;
	@Autowired
	private ChooseCriminalService chooseCriminalService;
	
	
	
	/**
	 *跳转 点击菜单中选项 （上海）法定不准出境人员通报备案通知书   跳转选择罪犯页面
	 * @param request
	 * @return
	 */
	@RequestMapping(value="legalNoExitPersonnelNotice")
	public ModelAndView legalNoExitPersonnelNotice(HttpServletRequest request){
		String menuid = request.getParameter("menuid");
		request.setAttribute("menuid",menuid);
		return new ModelAndView("chooseCriminal/legalNoExitPersonnelNotice");
	}
	
	/**
	 * 获取罪犯信息列表（上海法定不准出境人员通报备案通知书）
	 * @param request
	 * @return
	 */
	@RequestMapping("/getlegalNoExitPersonnelNotice")
	@ResponseBody
	public Object getCriminalFileTransferChoose(HttpServletRequest request) {
		Map<String, Object> resultmap = new HashMap<String,Object>();
		List<Map<String,Object>> data = new ArrayList<Map<String,Object>>();
		try {
			String key = request.getParameter("key")==null?"":request.getParameter("key");//取得参数
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
			map.put("departId", getLoginUser(request).getOrgid());
			map.put("sortField", sortField);
			map.put("sortOrder", sortOrder);
			map.put("start", String.valueOf(start));
			map.put("end",String.valueOf(end));
	    	map.put("flowdefid", "doc_bzcjtbbasp");
	    	int count = chooseCriminalService.countAllByCondition(map);//根据map传参获取总条数
	    	data= chooseCriminalService.getBasicInfoListWithFlow(map);//根据map传参获取罪犯列表
			resultmap.put("data", data);
			resultmap.put("total", count);
		}catch (Exception e) {
		}
		return resultmap;
	}
	/**
	 * 点击处理后跳转表单
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/addlegalNoExitPersonnelNotice")
	public ModelAndView showAddForm(HttpServletRequest request){
		String crimid = request.getParameter("crimid");
		String idnumber = request.getParameter("idnumber")==null?"":request.getParameter("idnumber");//流程草稿ID
		SystemUser user = getLoginUser(request);//获取当前登录的用户
		//如果罪犯编号为空就表示批量处理,将id解析成数组，取第一个作为复合编号（crimid+flowdraftid）
		if(crimid==null || "".equals(crimid)){
			String id = request.getParameter("id");
			request.setAttribute("id", id);
			String[] ids = id.split(",");
			crimid = ids[0];
			//将这个复合编号解析成数组，第一个是罪犯编号，第二个是流程草稿Id
			ids = crimid.split("@");
			crimid = ids[0];
			if(ids.length>1)idnumber = ids[1];
		}
		JSONArray docconent = new JSONArray();
		String menuid = request.getParameter("menuid");
		TbprisonerBaseinfo tbprisonerBaseinfo  = prisonerService.getBasicInfoByCrimid(crimid);
		TbprisonerBaseCrime crime = prisonerService.getCrimeByCrimid(crimid);//查询犯人犯罪处罚信息
		if(idnumber != null && !"".equals(idnumber)){
			String docConent =  flowBaseService.getDocConentByFlowdraftId(idnumber);
			docconent.add(docConent);
			request.setAttribute("flowdraftid", idnumber);
		}else{
			String departid=user.getDepartid();//根据用户Id获取所在部门Id
			String tempid = "SH_BZCJRYTBBATZS";
			TbsysTemplate template = systemModelService.getTemplateAndDepartid(tempid, departid);
			Map<String,Object> map = new HashMap<String,Object>();
			if(tbprisonerBaseinfo!=null){
				map.put("birthaddress",tbprisonerBaseinfo.getBirthaddress());//出生地
				map.put("addressdetail",tbprisonerBaseinfo.getAddressdetail());//现住址
				map.put("workaddressdetail",tbprisonerBaseinfo.getWorkaddressdetail());//工作单位
				map.put("name",tbprisonerBaseinfo.getName());//姓名
				map.put("text",tbprisonerBaseinfo.getUsedname());//曾用名
				map.put("nation",tbprisonerBaseinfo.getNation());//民族
				map.put("education",tbprisonerBaseinfo.getEducation());//文化程度
				map.put("maritalstatus",tbprisonerBaseinfo.getMaritalstatus());//婚姻状况
				map.put("idnumber",tbprisonerBaseinfo.getIdnumber());//身份证号
				map.put("registeraddressdetail",tbprisonerBaseinfo.getRegisteraddressdetail());//户口所在地
				map.put("gender",tbprisonerBaseinfo.getGender());//性别
				map.put("birthday",DateUtil.dateFormatForAip(tbprisonerBaseinfo.getBirthday()));//出生日期
				map.put("date",DateUtil.dateFormatForAip(new Date()));//填表时间（当前时间）
				map.put("crimid", crimid);
			}
			if (template != null) {
				docconent.add(template.getContent());
			}
			returnResourceMap(request);
			request.setAttribute("formDatajson", new JSONObject(map).toString());
			request.setAttribute("formcontent", docconent.toString());
		}
		request.setAttribute("menuid",menuid);
		request.setAttribute("orgid", crime.getOrgid1());
		request.setAttribute("applyid", crimid);
		request.setAttribute("applyname",tbprisonerBaseinfo.getName());
		request.setAttribute("flowdefid", "doc_bzcjtbbasp");
		//资源对象
		returnResourceMap(request); 
		request.setAttribute("formcontent", docconent.toString());
		return new ModelAndView("aip/addlegalNoExitPersonnelNotice");
	}

}
