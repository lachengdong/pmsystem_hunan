package com.sinog2c.mvc.controller.penaltyPerform;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.gkzx.common.GkzxCommon;
import com.sinog2c.model.flow.Flow;
import com.sinog2c.model.flow.FlowBaseOther;
import com.sinog2c.model.prisoner.TbprisonerBaseCrime;
import com.sinog2c.model.prisoner.TbprisonerBaseinfo;
import com.sinog2c.model.system.SystemUser;
import com.sinog2c.model.system.TbsysTemplate;
import com.sinog2c.mvc.controller.base.ControllerBase;
import com.sinog2c.service.api.flow.FlowBaseOtherService;
import com.sinog2c.service.api.flow.FlowBaseService;
import com.sinog2c.service.api.flow.FlowService;
import com.sinog2c.service.api.penaltyPerform.ChooseCriminalService;
import com.sinog2c.service.api.prisoner.PrisonerService;
import com.sinog2c.service.api.system.SystemModelService;
import com.sinog2c.util.common.DateUtil;
import com.sinog2c.util.common.StringNumberUtil;

/**
 * @author kexz 解回再审登记 2014-7-18
 */
@Controller
public class RegistrationTwo extends ControllerBase {
	@Resource
	private ChooseCriminalService chooseCriminalService;
	@Resource
	private SystemModelService systemModelService;
	@Resource
	private PrisonerService prisonerService;
	@Resource
	protected FlowBaseService flowBaseService;
	@Resource
	protected FlowBaseOtherService flowBaseOtherService;
	@Resource
	protected FlowService flowService;
	/**
	 * 跳转解回再审登记选择罪犯页面
	 * 
	 * @author liuxin
	 * @param request
	 * @return ModelAndView
	 */
	@RequestMapping("/registrationTwo")
	public ModelAndView sentenceChange(HttpServletRequest request) {
		returnResourceMap(request);
		return new ModelAndView("chooseCriminal/registrationTwoChoose");
	}
	
	/**
	 * 跳转解回再审登记选择罪犯页面
	 */
	@RequestMapping("/toProvinceRetrialRegister")
	public ModelAndView toProvinceRetrialRegister(HttpServletRequest request) {
		returnResourceMap(request);
		return new ModelAndView("chooseCriminal/provinceRetrialRegister");
	}

//	/**
//	 * 获取罪犯列表
//	 * 
//	 * @author liuxin
//	 * @param request
//	 * @return resultmap
//	 */
//	@RequestMapping(value = "/getRegistrationTwoBasicInfoList")
//	@ResponseBody
//	public Object getRegistrationTwoBasicInfoList(HttpServletRequest request,
//			HttpServletResponse response) {
//		Map<String, Object> resultmap = new HashMap<String, Object>();
//		List<Map<String,Object>> data = new ArrayList<Map<String,Object>>();
//		try {
//			String key = request.getParameter("key") == null ? "" : request.getParameter("key");// 取得参数
//			// 分页
//			int pageIndex = (Integer) (request.getParameter("pageIndex") == null ? "": Integer.parseInt(request.getParameter("pageIndex")));
//			int pageSize = (Integer) (request.getParameter("pageSize") == null ? "": Integer.parseInt(request.getParameter("pageSize")));
//			// 字段排序
//			String sortField = request.getParameter("sortField");
//			String sortOrder = request.getParameter("sortOrder");
//			int start = pageIndex * pageSize + 1;
//			int end = start + pageSize - 1;
//			Map<String, Object> map = new HashMap<String, Object>();
//			map.put("orgid", getLoginUser(request).getOrgid());
//			map.put("sortField", sortField);
//			map.put("sortOrder", sortOrder);
//			map.put("start", String.valueOf(start));
//			map.put("end", String.valueOf(end));
//			map.put("key", key);
//			int count = chooseCriminalService.countAllByCondition(map);// 根据map传参获取总条数
//			data = chooseCriminalService.getBasicInfoList(map);// 根据map传参获取罪犯列表
//			resultmap.put("data", data);
//			resultmap.put("total", count);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return resultmap;
//	}
	
	/**
	 * 获取罪犯列表
	 * 
	 */
	@RequestMapping(value = "/getProvinceRetrialRegisterBasicInfoList")
	@ResponseBody
	public Object getProvinceRetrialRegisterBasicInfoList(HttpServletRequest request) {
		Map<String, Object> resultmap = new HashMap<String, Object>();
		List<Map> data = new ArrayList<Map>();
		try {
			String key = request.getParameter("key");// 取得参数
			String jailid = request.getParameter("jailid");
			// 分页
			int pageIndex = (Integer) (request.getParameter("pageIndex") == null ? "": Integer.parseInt(request.getParameter("pageIndex")));
			int pageSize = (Integer) (request.getParameter("pageSize") == null ? "": Integer.parseInt(request.getParameter("pageSize")));
			// 字段排序
			String sortField = request.getParameter("sortField");
			String sortOrder = request.getParameter("sortOrder");
			int start = pageIndex * pageSize + 1;
			int end = start + pageSize - 1;
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("departid", getLoginUser(request).getOrgid());
			map.put("sortField", sortField);
			map.put("sortOrder", sortOrder);
			map.put("start", String.valueOf(start));
			map.put("end", String.valueOf(end));
			map.put("key", key);
			map.put("jailid", jailid);
			int count = chooseCriminalService.countProvinceAllByCondition(map);// 根据map传参获取总条数
			data = chooseCriminalService.getProvinceBasicInfoList(map);// 根据map传参获取罪犯列表
			resultmap.put("data", data);
			resultmap.put("total", count);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultmap;
	}

	/**
	 * 跳转解回再审登记页面
	 * 
	 * @author kexz
	 * @param request
	 * @return ModelAndView
	 */
	@RequestMapping("/registrationTwoList")
	public ModelAndView registrationTwo(HttpServletRequest request) {
		String name = request.getParameter("name")==null?"":request.getParameter("name");
		String idname = request.getParameter("idname")==null?"":request.getParameter("idname");
		String crimid = request.getParameter("crimid")==null?"":request.getParameter("crimid");
		String menuid = request.getParameter("menuid")==null?"":request.getParameter("menuid");
		if(StringNumberUtil.isNullOrEmpty(crimid)){//如果罪犯编号为空就表示批量处理
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
		return new ModelAndView("penaltyPerform/registrationTwo");
	}
	
	/**
	 * 跳转解回再审登记页面
	 * 
	 * @author kexz
	 * @param request
	 * @return ModelAndView
	 */
	@RequestMapping("/toProvinceSingleRetrialRegister")
	public ModelAndView toProvinceSingleRetrialRegister(HttpServletRequest request) {
		String name = request.getParameter("name")==null?"":request.getParameter("name");
		String idname = request.getParameter("idname")==null?"":request.getParameter("idname");
		String crimid = request.getParameter("crimid")==null?"":request.getParameter("crimid");
		String menuid = request.getParameter("menuid")==null?"":request.getParameter("menuid");
		if(StringNumberUtil.isNullOrEmpty(crimid)){//如果罪犯编号为空就表示批量处理
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
		return new ModelAndView("penaltyPerform/provinceRegistration");
	}

	/**
	 * 跳转解回再审登记新增、查看、修改页面
	 * 
	 * @author liuxin
	 * @param request
	 * @return ModelAndView
	 */
	@RequestMapping("/registrationTwoAdd")
	public ModelAndView registrationTwoAdd(HttpServletRequest request) {
		returnResourceMap(request);
		String crimid = request.getParameter("crimid");
		String tempid = request.getParameter("tempid");
		String menuid=request.getParameter("menuid");
		String flowdraftid = request.getParameter("flowdraftid");
		TbprisonerBaseCrime baseCrime = prisonerService.getCrimeByCrimid(crimid);
		TbprisonerBaseinfo baseinfo =  prisonerService.getBasicInfoByCrimid(crimid);
		JSONArray docconent = new JSONArray();
		if(!StringNumberUtil.isNullOrEmpty(flowdraftid)){
			FlowBaseOther baseOther = flowBaseOtherService.findByFlowdraftId(request.getParameter("flowdraftid"));
			if (baseOther != null) {
				docconent.add(baseOther.getDocconent());
			}
			request.setAttribute("flowdraftid", flowdraftid);
		}else{
			try {
				SystemUser user = getLoginUser(request);
				String deptid = user.getDepartid();
				TbsysTemplate template = systemModelService.getTemplateAndDepartid(tempid, deptid);
				if (template != null) {
					docconent.add(template.getContent());
				}
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("cbiname", baseinfo.getName());
				map.put("age", DateUtil.getAge(baseinfo.getBirthday()));
				map.put("cbigendername", baseinfo.getGender());
				if (baseCrime != null) {
					map.put("anyouhuizong", baseCrime.getCauseaction());
					map.put("zhuxing", baseCrime.getRemark());
					map.put("fujiaxing", baseCrime.getSanremark());
					try {
						Date startTime = baseCrime.getSentencestime();
						Date endTime = baseCrime.getSentenceetime();
						String xingqiqizhi = "自" + DateUtil.dateFormat(startTime,"yyyy年MM月dd日") + "起至"
								+ DateUtil.dateFormat(endTime,"yyyy年MM月dd日") + "止";
						if(startTime!=null){
							map.put("xingqiqizhi", xingqiqizhi);
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
					String guanyajianqu = baseCrime.getDetainprison()+baseCrime.getOrgname1();
					map.put("ctrnewcorpname", guanyajianqu);
				}
				request.setAttribute("formDatajson", new JSONObject(map).toString());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		returnResourceMap(request);
		request.setAttribute("crimid", crimid);
		request.setAttribute("menuid", menuid);
		request.setAttribute("tempid", tempid);
		request.setAttribute("applyid", crimid);
		request.setAttribute("name", baseinfo.getName());
		request.setAttribute("flowdefid", "other_zfjhzssp");
		request.setAttribute("applyname", baseinfo.getName());
		request.setAttribute("orgid", baseCrime.getOrgid1());
		request.setAttribute("formcontent", docconent.toString());
		return new ModelAndView("aip/registrationTwoAdd");
	}
	/**
	 * 根据罪犯编号获取解回再审列表
	 * @param request
	 * @return
	 */
	@RequestMapping("/ajaxGetzfjhzsspList")
	@ResponseBody
	public HashMap<String, Object> ajaxGetregistrationList(HttpServletRequest request) {
		SystemUser user = getLoginUser(request);
		HashMap<String, Object> result = new HashMap<String, Object>();
		//获取当前登录的用户
		String deptId=user.getDepartid();
		String flowdefid = "other_zfjhzssp";
		//申请人id
		String applyid=request.getParameter("crimid");
		//分页
		int pageIndex =(Integer)(request.getParameter("pageIndex")==null? "":Integer.parseInt(request.getParameter("pageIndex")));
		int pageSize = (Integer)(request.getParameter("pageSize")==null? "":Integer.parseInt(request.getParameter("pageSize")));        
		//字段排序
		String sortField = request.getParameter("sortField");
		String sortOrder = request.getParameter("sortOrder");
		int start = pageIndex * pageSize + 1;
		int end = start + pageSize -1;

		Map<String,Object> map = new HashMap<String,Object>();
		map.put("departId", deptId);
		map.put("applyid", applyid);
		map.put("flowdefid", flowdefid);
    	map.put("sortField", sortField);
    	map.put("sortOrder", sortOrder);
    	map.put("start", String.valueOf(start));
    	map.put("end",String.valueOf(end));
    	List<Map<String,Object>> list = flowBaseService.getJHZSData(map);
		int count = flowBaseService.getJHZSDataCount(map);
		result.put("total", count);
		result.put("data", list);
		return result;		
	}
	
	/**
	 * 根据罪犯编号获取解回再审列表
	 * @param request
	 * @return
	 */
	@RequestMapping("/ajaxGetProvinceJHZSspList")
	@ResponseBody
	public HashMap<String, Object> ajaxGetProvinceJHZSspList(HttpServletRequest request) {
		SystemUser user = getLoginUser(request);
		HashMap<String, Object> result = new HashMap<String, Object>();
		//获取当前登录的用户
		String departid=user.getDepartid();
		String flowdefid=request.getParameter("flowdefid");
		String applyid=request.getParameter("crimid");
		//分页
		int pageIndex =(Integer)(request.getParameter("pageIndex")==null? "":Integer.parseInt(request.getParameter("pageIndex")));
		int pageSize = (Integer)(request.getParameter("pageSize")==null? "":Integer.parseInt(request.getParameter("pageSize")));        
		//字段排序
		String sortField = request.getParameter("sortField");
		String sortOrder = request.getParameter("sortOrder");
		int start = pageIndex * pageSize + 1;
		int end = start + pageSize -1;
		
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("departid", departid);
		map.put("applyid", applyid);
		map.put("type", "1");
		map.put("flowdefid", flowdefid);
		map.put("sortField", sortField);
		map.put("sortOrder", sortOrder);
		map.put("start", String.valueOf(start));
		map.put("end",String.valueOf(end));
		List<Map> list = flowBaseService.getProvinceJHZSData(map);
		int count = flowBaseService.countProvinceJHZSData(map);
		result.put("total", count);
		result.put("data", list);
		return result;		
	}
	
	@RequestMapping("/jhzszz")
	public ModelAndView jhzszz(HttpServletRequest request) {
		String flowsn = request.getParameter("flowsn");
		request.setAttribute("flowsn", flowsn);
		ModelAndView mav = new ModelAndView(
				"penaltyPerform/addJHZSZZ");
		returnResourceMap(request);
		return mav;
	}
	@RequestMapping("updatezz")
	@ResponseBody
	public String updatezz(HttpServletRequest request){
		String flowsn = request.getParameter("flowsn")==null?GkzxCommon.ZERO:request.getParameter("flowsn");
		if(flowsn.equals(GkzxCommon.ZERO)){
			return GkzxCommon.RESULT_ERROR;
		}
		String text1 = request.getParameter("text1");
		String text2 = request.getParameter("text2");
		if(text2!=null){
			text2 = text2.replace("\"", "").substring(0, 10);
		}
		Flow flow = new Flow();
		flow.setText1(text1);
		flow.setText2(text2);
		flow.setFlowsn(flowsn);
		flowService.updateZZByFlowSn(flow);
		return GkzxCommon.RESULT_SUCCESS;
	}
}
