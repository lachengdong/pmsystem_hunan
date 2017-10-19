package com.sinog2c.mvc.controller.penaltyPerform.inPrisonManagement;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

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
import com.sinog2c.service.api.system.SystemLogService;
import com.sinog2c.service.api.system.SystemModelService;
import com.sinog2c.service.api.system.SystemOrganizationService;
import com.sinog2c.util.common.DateUtil;

/**
 * @author kexz
 *身体检查
 * 2014-7-17
 */
@Controller
@RequestMapping("/Examination")
public class ExaminationBody extends ControllerBase{
	@Resource
	private ChooseCriminalService chooseCriminalService;
	@Resource
	private SystemModelService systemModelService;
	@Resource
	private PrisonerService prisonerService;
	@Resource
	private SystemOrganizationService systemOrganizationService;
	@Resource
	public SystemLogService logService;
	@Resource
	protected FlowBaseService flowBaseService;
	/**
	 * 跳转身体检查列表页面
	 * 
	 * @author liuxin
	 * @param request
	 * @return ModelAndView
	 */
	@RequestMapping("/examinationBody.page")
	public ModelAndView examinationBody(HttpServletRequest request){
		ModelAndView mav =  new ModelAndView("penaltyPerform/inPrisonManagement/examinationBodychoose");
		returnResourceMap(request);
		return mav;
	}
	
	/**
	 * 获取罪犯列表
	 * @author liuxin
	 * @param request
	 * @return resultmap
	 */
	@RequestMapping(value = "/getExaminationBodyBasicInfoList.json")
	@ResponseBody
	public Object getBasicInfoList(HttpServletRequest request,
			HttpServletResponse response){
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
			map.put("departId", getLoginUser(request).getOrgid());
			map.put("userid", getLoginUser(request).getUserid());
	    	map.put("sortField", sortField);
	    	map.put("sortOrder", sortOrder);    
	    	map.put("start", String.valueOf(start));   
	    	map.put("end",String.valueOf(end));
	    	map.put("userid", getLoginUser(request).getUserid());//给map传userid为了mapper中id为findWithFlow使用
	    	int count = chooseCriminalService.countAllByCondition(map);//根据map传参获取总条数
	    	map.put("flowdefid", "other_zfstjcsp");
	    	data= chooseCriminalService.getBasicInfoListWithFlow(map);//根据map传参获取罪犯列表
	    	resultmap.put("data", data);
			resultmap.put("total", count);
		}catch (Exception e) {
		}
		return resultmap;
	}
	
	/**
	 * 跳转身体检查列表表单页面
	 * 
	 * @author liuxin
	 * @param request
	 * @return ModelAndView
	 */
	@RequestMapping("/examinationBodyAdd.page")
	public ModelAndView examinationBodyAdd(HttpServletRequest request){
		SystemUser user = getLoginUser(request);
		String deptid=user.getDepartid();
		String idnumber = request.getParameter("idnumber")==null?"":request.getParameter("idnumber");//流程编号
		String crimid = request.getParameter("crimid");
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
		request.setAttribute("crimid", crimid);
		JSONArray docconent = new JSONArray();
		String tempid = request.getParameter("tempid");
		TbprisonerBaseinfo baseInfo =  prisonerService.getBasicInfoByCrimid(crimid);//根据罪犯id获取罪犯信息
		TbprisonerBaseCrime baseCrime  = prisonerService.getCrimeByCrimid(crimid);
		if(idnumber != null && !"".equals(idnumber)){
			String docConent =  flowBaseService.getDocConentByFlowdraftId(idnumber);
			docconent.add(docConent);
			request.setAttribute("flowdraftid",idnumber);
		}else{
			TbsysTemplate template = systemModelService.getTemplateAndDepartid(tempid, deptid);
			if (template != null) {
				docconent.add(template.getContent());
				//通过部门id去找所在单位名称
				SystemOrganization crimorg = systemOrganizationService.getByOrganizationId(baseCrime.getOrgid1());
				Map<String,Object> map = new HashMap<String,Object>();
				map.put("cbiname", baseInfo.getName());//姓名
				map.put("cbigendername", baseInfo.getGender());//性别 
				map.put("cbibirthday", DateUtil.dateFormatForAip(baseInfo.getBirthday()));//出生日期
				map.put("cbinationname", baseInfo.getNation());//民族 
				map.put("cbihomeaddress", baseInfo.getFamilyaddress());//家庭住址
				map.put("criminalid", crimid);//编号
				if(crimorg!=null)map.put("departid", crimorg.getName());//单位
				if(baseInfo != null){
					map.put("caiformermarriagename", baseInfo.getMaritalstatus());//婚否
				}
				if(baseCrime != null){
					map.put("anyouhuizong", baseCrime.getCauseaction());//罪名
					map.put("cjiimprisontypename", baseCrime.getPunishmenttype());//刑种
					map.put("zhuxing", baseCrime.getRemark() );//刑期
				}
				request.setAttribute("formDatajson", new JSONObject(map).toString());
			}
		}	
		request.setAttribute("flowdefid", "other_zfstjcsp");
		request.setAttribute("tempid", tempid);
		request.setAttribute("orgid", baseCrime.getOrgid1());
		request.setAttribute("applyid",crimid);
		request.setAttribute("applyname",baseInfo.getName());
		request.setAttribute("formcontent", docconent.toString());
		returnResourceMap(request);
		return new ModelAndView("penaltyPerform/inPrisonManagement/examinationBodyAdd");
	}
	
}
