package com.sinog2c.mvc.controller.commutationParole.threeTypesOfCrime;

import java.util.ArrayList;
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
import org.springframework.web.servlet.view.InternalResourceView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sinog2c.model.prisoner.TbprisonerBaseCrime;
import com.sinog2c.model.prisoner.TbprisonerBaseinfo;
import com.sinog2c.model.system.SystemOrganization;
import com.sinog2c.model.system.SystemUser;
import com.sinog2c.model.system.TbsysTemplate;
import com.sinog2c.mvc.controller.base.ControllerBase;
import com.sinog2c.service.api.flow.FlowBaseService;
import com.sinog2c.service.api.penaltyPerform.ChooseCriminalService;
import com.sinog2c.service.api.prisoner.PrisonerService;
import com.sinog2c.service.api.prisoner.TbxfSentencealterationService;
import com.sinog2c.service.api.system.SystemLogService;
import com.sinog2c.service.api.system.SystemModelService;
import com.sinog2c.service.api.system.SystemOrganizationService;
import com.sinog2c.util.common.DateUtil;

/**
 * @author kexz
 *报局审批
 * 2014-8-26
 */
@Controller
public class ThreemakeExaminationAndApproval extends ControllerBase {
	
	@Autowired
	private ChooseCriminalService chooseCriminalService;
	@Autowired
	private PrisonerService prisonerService;
	@Autowired
	private FlowBaseService flowBaseService;
	@Autowired
	private SystemModelService systemModelService;
	@Autowired
	private SystemOrganizationService systemOrganizationService;
	@Resource
	public SystemLogService logService;
	@Resource
	public TbxfSentencealterationService tbxfSentencealterationService;
	/**
	 * 方法描述：罪犯处理页面
	 */
	@RequestMapping("toThreemakeExaminationAndApprovalChoose")
	public ModelAndView toThreemakeExaminationAndApprovalChoose(HttpServletRequest request) {
		returnResourceMap(request);
		return new ModelAndView(new InternalResourceView("WEB-INF/JSP/commutationParole/threeTypesOfCrime/toThreemakeExaminationAndApprovalChoose.jsp"));
	}
	
	/**
	 *  方法描述：初始化加载罪犯列表
	 * 
	 */
	@RequestMapping("getThreemakeExaminationAndApprovalList")
	@ResponseBody
	public Object getThreemakeExaminationAndApprovalList(HttpServletRequest request){
		Map<String, Object> resultmap = new HashMap<String,Object>();
		List<Map<String,Object>> data = new ArrayList<Map<String,Object>>();
		try {
			String key = request.getParameter("key")==null?"":request.getParameter("key");
			int pageIndex =(Integer)(request.getParameter("pageIndex")==null? "":Integer.parseInt(request.getParameter("pageIndex")));
			int pageSize = (Integer)(request.getParameter("pageSize")==null? "":Integer.parseInt(request.getParameter("pageSize")));  
			String sortField = request.getParameter("sortField");
			String sortOrder = request.getParameter("sortOrder");
			int start = pageIndex * pageSize + 1;
			int end = start + pageSize -1;
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("key", key);
			map.put("userid", getLoginUser(request).getUserid());
			map.put("departId", getLoginUser(request).getOrgid());
	    	map.put("sortField", sortField);
	    	map.put("sortOrder", sortOrder);                     
	    	map.put("start", String.valueOf(start));
	    	map.put("end",String.valueOf(end));
	    	int count = chooseCriminalService.countAllByCondition(map);//根据map传参获取总条数
	    	map.put("flowdefid", "other_bjshzfrdsp");
	    	data= chooseCriminalService.getBasicInfoListWithFlow(map);//根据map传参获取罪犯列表
	    	resultmap.put("data", data);
			resultmap.put("total", count);
		}catch (Exception e) {
		}
		return resultmap;
	}
	
	
	/**
	 * 选择罪犯后处理的三类犯审批表单
	 * 
	 */
	@RequestMapping("toThreemakeExaminationAndApprovalPage")
	public ModelAndView toTabsPage(HttpServletRequest request) {
			String crimid = request.getParameter("crimid");
			String idnumber = request.getParameter("idnumber")==null?"":request.getParameter("idnumber");//流程草稿ID
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
			TbprisonerBaseinfo info = prisonerService.getBasicInfoByCrimid(crimid);//查询犯人基本信息
			TbprisonerBaseCrime crime =prisonerService.getCrimeByCrimid(crimid);//查询犯人犯罪处罚信息
			if(idnumber != null && !"".equals(idnumber)){
				String docConent =  flowBaseService.getDocConentByFlowdraftId(idnumber);
				docconent.add(docConent);
				request.setAttribute("flowdraftid",idnumber);
			}else{
				String tempid = "BJSHZFRDSPB";//天津三类犯审批 
				request.setAttribute("tempid", tempid);
				HashMap<String, Object> map = new HashMap<String, Object>();
				SystemUser user = getLoginUser(request);//获取当前登录的用户
				String departid=user.getDepartid();//根据用户Id获取所在部门Id
				TbsysTemplate template = systemModelService.getTemplateAndDepartid(tempid, departid);
				if (template != null) {
					docconent.add(template.getContent());
				}
				Map<String,Object> tbxf =tbxfSentencealterationService.selectTbxfByCrimid(crimid);//刑期变动
				SystemOrganization org=systemOrganizationService.getByOrganizationId(departid);//根据部门Id获取单位信息
				map.put("zfbh",crimid);
				map.put("xingming", info.getName());
				map.put("xingbie", info.getGender());
				map.put("csrq",DateUtil.dateFormatForAip(info.getBirthday()));
				map.put("mzui", info.getNation());
				map.put("whcd", info.getEducation());
				if(org!=null){
					map.put("dwei", org.getName());
				}
				if(crime!=null){
					map.put("zuiming", crime.getCauseaction());
					map.put("xz", crime.getPunishmenttype());
					map.put("xq", crime.getRemark());
					map.put("xqqz","自"+ DateUtil.dateFormat(crime.getSentencestime())+"至"+DateUtil.dateFormat(crime.getSentenceetime()));
					request.setAttribute("orgname1",crime.getOrgid1());
				}
 				if(tbxf!=null){
					map.put("xqbd",tbxf.get("SENTENCECHAGEINFO"));//刑期变动情况
				}
				request.setAttribute("tempid", tempid);
				request.setAttribute("crimid", crimid);
				request.setAttribute("formDatajson", new JSONObject(map).toString());
			}
			//资源对象
			returnResourceMap(request); 
			request.setAttribute("orgid", crime.getOrgid1());
			request.setAttribute("applyid", crimid);
			request.setAttribute("flowdefid", "other_bjshzfrdsp");
			request.setAttribute("applyname",info.getName());
			request.setAttribute("formcontent", docconent.toString());
		 return new ModelAndView(new InternalResourceView("WEB-INF/JSP/commutationParole/threeTypesOfCrime/toThreemakeExamination.jsp"));
	}
}
