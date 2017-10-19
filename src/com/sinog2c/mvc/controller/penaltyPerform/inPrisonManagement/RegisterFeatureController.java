package com.sinog2c.mvc.controller.penaltyPerform.inPrisonManagement;

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
import com.sinog2c.util.common.DateUtil;
import com.sinog2c.util.common.StringNumberUtil;

/**
 * 身体特征登记
 * @author wangxf
 *
 */
@Controller
@RequestMapping("/registerFeature")
public class RegisterFeatureController extends ControllerBase {
	@Autowired
	private ChooseCriminalService chooseCriminalService;
	@Autowired
	private SystemModelService systemModelService;
	@Resource
	protected FlowBaseService flowBaseService;
	//罪犯基本信息表总接口
	@Autowired
	private PrisonerService prisoner;
	//罪犯基本信息表
	private TbprisonerBaseinfo baseInfo;
	//罪犯处罚信息表
	private TbprisonerBaseCrime baseCrime;
	
	/**
	 * 罪犯选择页面
	 * @param request
	 * @return
	 */
	@RequestMapping("/registerFeatureChoose.page")
	public  ModelAndView registerFeaturechoose(HttpServletRequest request){
		returnResourceMap(request);
		return new ModelAndView("penaltyPerform/inPrisonManagement/registerFeaturechoose");
	}
	/**
	 * 获取罪犯列表
	 * @author liuchaoyang
	 * 2014-7-30 20:37:45
	 */  
	@RequestMapping(value = "/getRegisterFeatureList.json")
	@ResponseBody
	public Object getRegisterFeatureList(HttpServletRequest request,
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
	    	map.put("flowdefid", "other_zftzdjsp");
	    	data= chooseCriminalService.getBasicInfoListWithFlow(map);//根据map传参获取罪犯列表
	    	resultmap.put("data", data);
			resultmap.put("total", count);
		}catch (Exception e) {
		}
		return resultmap;
	}
	
	@RequestMapping("/registerFeatureAdd.page")
	public  ModelAndView registerFeature(HttpServletRequest request){
		SystemUser user = getLoginUser(request);
		String departid=user.getDepartid();
		String crimid = request.getParameter("crimid");
		String idnumber = request.getParameter("idnumber")==null?"":request.getParameter("idnumber");//流程编号
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
		if(idnumber != null && !"".equals(idnumber)){
			String docConent =  flowBaseService.getDocConentByFlowdraftId(idnumber);
			docconent.add(docConent);
			request.setAttribute("flowdraftid",idnumber);
		}else{
			String tempid="SJ_CRIMINALCHARACTER";
			request.setAttribute("tempid", tempid);
			String menuid = request.getParameter("menuid");
			request.setAttribute("menuid", menuid);
			TbsysTemplate template = systemModelService.getTemplateAndDepartid(tempid, departid);
			if (template != null) {
				docconent.add(template.getContent());
			}
			baseInfo=prisoner.getBasicInfoByCrimid(crimid);
			baseCrime=prisoner.getCrimeByCrimid(crimid);
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("cbiname" ,baseInfo.getName());
			map.put("age", DateUtil.getAge(baseInfo.getBirthday()));//计算年龄
			map.put("text3patch", DateUtil.dateFormatForAip(new Date()));
			map.put("criminalid", crimid);
			map.put("text9", user.getName());
			
			String xingqi = baseCrime.getPunishmentyear()+","+baseCrime.getPunishmentmonth()+","+baseCrime.getPunishmentday();
			map.put("zhuxing", StringNumberUtil.getXingqi(baseCrime.getPunishmenttype(), xingqi));//主刑
			request.setAttribute("formDatajson", new JSONObject(map).toString());
		}
		returnResourceMap(request);
		request.setAttribute("flowdefid", "other_zftzdjsp");
		request.setAttribute("orgid", baseCrime.getOrgid1());
		request.setAttribute("applyid", crimid);
		request.setAttribute("applyname", baseInfo.getName());
		request.setAttribute("formcontent", docconent.toString());
		return new ModelAndView("penaltyPerform/inPrisonManagement/registerFeatureAdd");
	}
}
