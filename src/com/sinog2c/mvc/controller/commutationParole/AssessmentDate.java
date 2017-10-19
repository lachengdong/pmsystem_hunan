package com.sinog2c.mvc.controller.commutationParole;

import java.net.URLDecoder;
import java.sql.Clob;
import java.sql.SQLException;
import java.sql.Timestamp;
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

import com.alibaba.fastjson.JSONObject;
import com.sinog2c.model.commutationParole.TbdataSentchage;
import com.sinog2c.model.system.SystemLog;
import com.sinog2c.model.system.SystemUser;
import com.sinog2c.mvc.controller.base.ControllerBase;
import com.sinog2c.mvc.message.JSONMessage;
import com.sinog2c.service.api.commutationParole.TbdataSentchageService;
import com.sinog2c.service.api.system.SystemLogService;
import com.sinog2c.util.common.DateUtil;
import com.sinog2c.util.common.DateUtils;
import com.sinog2c.util.common.JsonUtil;
import com.sinog2c.util.common.StringNumberUtil;

/**
 * 
 * 考核之日补录
 * 
 */
@Controller
@RequestMapping("/assessmentDate")
public class AssessmentDate extends ControllerBase {
	@Resource
	private TbdataSentchageService tbdataSentchageService;
	
	/**
	 * 在线日志服务对象
	 */
	@Autowired
	protected SystemLogService logService; 
	
	@RequestMapping("/toListPage.page")
	public ModelAndView choosePage(HttpServletRequest request) throws Exception {
		returnResourceMap(request);
		return new ModelAndView("commutationParole/assessementDateList");
	}
	
	@RequestMapping("/toListPageFGX.page")
	public ModelAndView toListPage(HttpServletRequest request) throws Exception {
		returnResourceMap(request);
		return new ModelAndView("commutationParole/assessementDateListFGX");
	}

	/**
	 *方法描述：获取罪犯列表数据
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getList.json")
	@ResponseBody
	public Object getList(HttpServletRequest request)
			throws Exception {
		Map<String, Object> resultmap = new HashMap<String, Object>();
		List<TbdataSentchage> data = new ArrayList<TbdataSentchage>();
		try {
			String key = request.getParameter("key") == null ? "" : request.getParameter("key");
			key = URLDecoder.decode(key,"UTF-8");
			int pageIndex = (Integer) (request.getParameter("pageIndex") == null ? "": Integer.parseInt(request.getParameter("pageIndex")));
			int pageSize = (Integer) (request.getParameter("pageSize") == null ? "": Integer.parseInt(request.getParameter("pageSize")));
			String sortField = request.getParameter("sortField");
			String sortOrder = request.getParameter("sortOrder");
			int start = pageIndex * pageSize + 1;
			int end = start + pageSize - 1;
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("key", key);
			map.put("departId", getLoginUser(request).getOrgid());
			map.put("sortField", sortField);
			map.put("sortOrder", sortOrder);
			map.put("start", String.valueOf(start));
			map.put("end", String.valueOf(end));
			int count = tbdataSentchageService.selectDataListCount(map);// 根据map传参获取总条数
			data = tbdataSentchageService.selectDataList(map);
			resultmap.put("data", data);
			resultmap.put("total", count);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultmap;
	}
	
	/**
	 *方法描述：获取罪犯列表数据
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getSentenceChangeData.json")
	@ResponseBody
	public Object getSentenceChangeData(HttpServletRequest request){
		
		Map<String, Object> resultmap = new HashMap<String, Object>();
		List<Map<String,Object>> data = new ArrayList<Map<String,Object>>();
		try {
			String key = request.getParameter("key")==null?"":request.getParameter("key");
			key = URLDecoder.decode(key,"UTF-8");
			int pageIndex = (Integer) (request.getParameter("pageIndex") == null ? "": Integer.parseInt(request.getParameter("pageIndex")));
			int pageSize = (Integer) (request.getParameter("pageSize") == null ? "": Integer.parseInt(request.getParameter("pageSize")));
			String sortField = request.getParameter("sortField");
			String sortOrder = request.getParameter("sortOrder");
			int start = pageIndex * pageSize + 1;
			int end = start + pageSize - 1;
			
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("key", key);
			paramMap.put("orgid", getLoginUser(request).getOrgid());
			paramMap.put("sortField", sortField);
			paramMap.put("sortOrder", sortOrder);
			paramMap.put("start", String.valueOf(start));
			paramMap.put("end", String.valueOf(end));
			int count = tbdataSentchageService.countSentenceChangeData(paramMap);// 根据map传参获取总条数
			data = tbdataSentchageService.getSentenceChangeData(paramMap);
			resultmap.put("data", data);
			resultmap.put("total", count);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultmap;
	}

	@RequestMapping("/saveChanges")
	@ResponseBody
	public Object saveChanges(HttpServletRequest request) throws Exception {
		String result = request.getParameter("data");
		List list = new ArrayList();
		HashMap<String, Object> map = new HashMap<String, Object>();
		list = (List) JsonUtil.Decode(result);
		if (list != null && list.size() > 0) {
			for(int i=0;i<list.size();i++){
				Map tempmap = (Map)list.get(i);
				map.putAll(tempmap);
				map.put("REPORTTIME",DateUtil.dateFormatForAip(tempmap.get("REPORTTIME")));
				map.put("AWARDEND", DateUtil.dateFormatForAip(tempmap.get("AWARDEND")));
				tbdataSentchageService.updateKHZR(map);
				tbdataSentchageService.callREWARDSTARTProcedure();
			}
		}
		return null;
	}
	
	@RequestMapping("/saveSentenceChanges")
	@ResponseBody
	public Object saveSentenceChanges(HttpServletRequest request) throws Exception {
		String result = request.getParameter("data");
		List list = new ArrayList();
		Object dataObj = JsonUtil.Decode(result);
		if(dataObj instanceof List){
			list = (ArrayList)dataObj;
		}else if(dataObj instanceof Map){
			list.add(dataObj);
		}
		
		return tbdataSentchageService.updateSentenceChangeKHZRDate(list);
	}
	
	
	@RequestMapping("/lastCommutationDataList.page")
	public ModelAndView lastCommutationDataList(HttpServletRequest request) throws Exception {
		returnResourceMap(request);
		return new ModelAndView("commutationParole/hebei/lastCommutationDataList");
	}
	
	@RequestMapping("/getLastCommutationData.json")
	@ResponseBody
	public Object getLastCommutationData(HttpServletRequest request)
			throws Exception {
		Map<String, Object> resultmap = new HashMap<String, Object>();
		List<TbdataSentchage> data = new ArrayList<TbdataSentchage>();
		try {
			String key = request.getParameter("key") == null ? "" : request.getParameter("key");
			key = URLDecoder.decode(key,"UTF-8");
			int pageIndex = (Integer) (request.getParameter("pageIndex") == null ? "": Integer.parseInt(request.getParameter("pageIndex")));
			int pageSize = (Integer) (request.getParameter("pageSize") == null ? "": Integer.parseInt(request.getParameter("pageSize")));
			String sortField = request.getParameter("sortField");
			String sortOrder = request.getParameter("sortOrder");
			int start = pageIndex * pageSize + 1;
			int end = start + pageSize - 1;
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("key", key);
			map.put("orgid", getLoginUser(request).getOrgid());
			map.put("sortField", sortField);
			map.put("sortOrder", sortOrder);
			map.put("start", String.valueOf(start));
			map.put("end", String.valueOf(end));
			int count = tbdataSentchageService.getLastCommutationDataCount(map);// 根据map传参获取总条数
			data = tbdataSentchageService.getLastCommutationData(map);
			resultmap.put("data", data);
			resultmap.put("total", count);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultmap;
	}
	
	@RequestMapping("/saveReportDateChanges")
	@ResponseBody
	public Object saveReportDateChanges(HttpServletRequest request) throws Exception {
		String result = request.getParameter("data");
		List list = new ArrayList();
		HashMap<String, Object> map = new HashMap<String, Object>();
		list = (List) JsonUtil.Decode(result);
		if (list != null && list.size() > 0) {
			for(int i=0;i<list.size();i++){
				Map tempmap = (Map)list.get(i);
				map.putAll(tempmap);
				Object reporttime = DateUtil.dateFormatForAip(tempmap.get("REPORTTIME"));
				if(StringNumberUtil.isEmpty(reporttime)){
					continue;
				}
				
//				Object awardend = DateUtil.dateFormatForAip(tempmap.get("AWARDEND"));
//				if(StringNumberUtil.isEmpty(awardend)){
//					awardend = null;
//				}
				
				map.put("REPORTTIME",reporttime);
//				map.put("AWARDEND", awardend);
				tbdataSentchageService.updateKHZR(map);
			}
		}
		return null;
	}
	@RequestMapping("/setCrimeTypesMapping")
	@ResponseBody
	public Object setCrimeTypesMapping(HttpServletRequest request) throws Exception {
		String result = request.getParameter("data");
		String typeid = request.getParameter("typeid");
		List list = new ArrayList();
		HashMap<String, Object> map = new HashMap<String, Object>();
		list = (List) JsonUtil.Decode(result);
		if (list != null && list.size() > 0) {
			Map tempmap = (Map)list.get(0);
			String crimeTypesMapping = (String)tempmap.get("TYPEMAPPING");
			if (StringNumberUtil.isNullOrEmpty(crimeTypesMapping)){
				map.put("typeid", typeid);
				map.put("CRIMID",tempmap.get("CRIMID"));
				tbdataSentchageService.setCrimeTypesMapping(map);
			} else {
				if (crimeTypesMapping.indexOf(typeid)!=-1){
					map.put("typeid", typeid);
					map.put("CRIMID",tempmap.get("CRIMID"));
					tbdataSentchageService.deleteCrimeTypesMapping(map);
				} else {
					map.put("typeid", typeid);
					map.put("CRIMID",tempmap.get("CRIMID"));
					tbdataSentchageService.setCrimeTypesMapping(map);
				}
			}
		}
		return null;
	}
	
	/**
	 * 跳转列表页
	 * 
	 */
	@RequestMapping(value = "/toCrimMergeReferenceListPage")
	public ModelAndView toMergeReferenceListPage(HttpServletRequest request) {
		String result = request.getParameter("data");
		List list = new ArrayList();
		Map<String, Object> map = new HashMap<String, Object>();
		list = (List) JsonUtil.Decode(result);
		if (list != null && list.size() > 0) {
			Map tempmap = (Map)list.get(0);
			map.put("crimid",tempmap.get("CRIMID"));

		}
		ModelAndView mav = null;
		return new ModelAndView("commutationParole/crimQpbTeaListPage", "record", map);
	}
	
	/**
	 * 跳转列表页
	 * 
	 */
	@RequestMapping(value = "/toCrimConsumePage")
	public ModelAndView toCrimConsumePage(HttpServletRequest request) {
		String result = request.getParameter("data");
		List list = new ArrayList();
		Map<String, Object> map = new HashMap<String, Object>();
		list = (List) JsonUtil.Decode(result);
		if (list != null && list.size() > 0) {
			Map tempmap = (Map)list.get(0);
			map.put("crimid",tempmap.get("CRIMID"));

		}
		ModelAndView mav = null;
		return new ModelAndView("commutationParole/crimConsume", "record", map);
	}
	
	/**
	 * 列表数据
	 * @return
	 */
	@RequestMapping(value = "/ajaxGetConsumeList")
	@ResponseBody
	public Object ajaxGetConsumeList(HttpServletRequest request){
		int pageIndex = Integer.valueOf(request.getParameter("pageIndex"));
		int pageSize = Integer.valueOf(request.getParameter("pageSize"));
		String crimid = request.getParameter("crimid");
		int total = tbdataSentchageService.getConsumeCount(crimid);
		int start = pageIndex * pageSize + 1;
		int end = start + pageSize - 1;
		String sortField = "RQ";
		String sortOrder = "ASC";
		List<HashMap> list = tbdataSentchageService.getConsumeList(crimid, sortField, sortOrder, start, end);
		JSONMessage message = JSONMessage.newMessage();
		message.setTotal(total);
		message.setData(list);
		return message;
	}
	
	/**
	 * 列表数据
	 * @return
	 */
	@RequestMapping(value = "/ajaxGetCrimQpbTeaList")
	@ResponseBody
	public Object ajaxGetCrimQpbTeaList(HttpServletRequest request){
		int pageIndex = Integer.valueOf(request.getParameter("pageIndex"));
		int pageSize = Integer.valueOf(request.getParameter("pageSize"));
		String crimid = request.getParameter("crimid");
		int total = tbdataSentchageService.getCrimQpbTeaCount(crimid);
		int start = pageIndex * pageSize + 1;
		int end = start + pageSize - 1;
		String sortField = "REWARDID";
		String sortOrder = "ASC";
		List<HashMap> list = tbdataSentchageService.getCrimQpbTeaList(crimid, sortField, sortOrder, start, end);
		JSONMessage message = JSONMessage.newMessage();
		message.setTotal(total);
		message.setData(list);
		return message;
	}
	
	/**
	 * 保存
	 * @param request
	 * @return
	 */
	@RequestMapping(value = { "/saveCrimQpbTea" })
	@ResponseBody
	public String saveCrimQpbTea(HttpServletRequest request){
		String result = "success";
		short status = 1;
		SystemUser user = getLoginUser(request);
		String crimid = request.getParameter("crimid");
		String json = request.getParameter("data");
		ArrayList rows = (ArrayList)JsonUtil.Decode(json);
		if(null!=rows&&rows.size()>0){
			for(int i=0;i<rows.size();i++){
				HashMap row = (HashMap)rows.get(i);
				String id = row.get("ID")==null?"":row.get("ID").toString();
				String rewardid = row.get("REWARDID")==null?"":row.get("REWARDID").toString();
				String awardno = row.get("AWARDNO")==null?"":row.get("AWARDNO").toString();
				String rewarddate = DateUtil.dateFormatForAip(row.get("REWARDDATE"));
				String rewardname = row.get("REWARDNAME")==null?"":row.get("REWARDNAME").toString();
				Map map = new HashMap();
				map.put("zfbh", crimid);
				map.put("id", id);
				map.put("rewardid", rewardid);
				map.put("awardno", awardno);
				map.put("rewarddate", rewarddate);
				map.put("rewardname", rewardname);
				if("added".equals((String)row.get("_state"))){
					try {
						tbdataSentchageService.insertCrimQpbTea(map);
					} catch (Exception e) {
						e.printStackTrace();
						result = "error";
						status = 0;
					}
					SystemLog log = new SystemLog();
					log.setLogtype("奖励证添加操作");
					log.setOpaction("添加");
					log.setOpcontent("添加奖励证");
					log.setOrgid(user.getUserid());
					log.setRemark("添加奖励证");
					log.setStatus(status);
					try {
						logService.add(log,user);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}else if("removed".equals((String)row.get("_state"))){
					try {
						tbdataSentchageService.deleteCrimQpbTea(map);
					} catch (NumberFormatException e) {
						e.printStackTrace();
						result = "error";
						status = 0;
					}
					SystemLog log = new SystemLog();
					log.setLogtype("奖励证删除操作");
					log.setOpaction("删除");
					log.setOpcontent("删除奖励证,id= "+id);
					log.setOrgid(user.getUserid());
					log.setRemark("删除奖励证");
					log.setStatus(status);
					try {
						logService.add(log,user);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}
		return result;
	}
	
	
	@RequestMapping("/deleteCrimeTypesMapping")
	@ResponseBody
	public Object deleteCrimeTypesMapping(HttpServletRequest request) throws Exception {
		String result = request.getParameter("data");
		List list = new ArrayList();
		HashMap<String, Object> map = new HashMap<String, Object>();
		list = (List) JsonUtil.Decode(result);
		if (list != null && list.size() > 0) {
			Map tempmap = (Map)list.get(0);
			map.putAll(tempmap);
			map.put("CRIMID",tempmap.get("CRIMID"));
			tbdataSentchageService.deleteCrimeTypesMapping(map);
		}
		return null;
	}
	@RequestMapping("/viewscreeningexcuse")
	public Object viewScreeningExcuse(HttpServletRequest request) throws Exception {
		String crimid = request.getParameter("crimid");
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("crimid", crimid);
		Map<String,Object> criMap = tbdataSentchageService.viewScreeningExcuse(map);
		Clob clob = (Clob)criMap.get("EXCUSE");
		try {
			String excuse = clob.getSubString(1, (int)clob.length());
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("criminalinfo", excuse);
			request.setAttribute("criminalinfo", jsonObject.toString());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		ModelAndView mav = new ModelAndView("commutationParole/viewscreeningexcuse");
		return mav;
	}
	/**
	 *方法描述：获取罪犯列表数据(考核起日补录页面)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getSentenceChangedData.json")
	@ResponseBody
	public Object getSentenceChangedData(HttpServletRequest request){
		
		Map<String, Object> resultmap = new HashMap<String, Object>();
		List<Map<String,Object>> data = new ArrayList<Map<String,Object>>();
		try {
			String key = request.getParameter("key")==null?"":request.getParameter("key");
			key = URLDecoder.decode(key,"UTF-8");
			int pageIndex = (Integer) (request.getParameter("pageIndex") == null ? "": Integer.parseInt(request.getParameter("pageIndex")));
			int pageSize = (Integer) (request.getParameter("pageSize") == null ? "": Integer.parseInt(request.getParameter("pageSize")));
			String sortField = request.getParameter("sortField");
			String sortOrder = request.getParameter("sortOrder");
			int start = pageIndex * pageSize + 1;
			int end = start + pageSize - 1;
			
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("key", key);
			paramMap.put("orgid", getLoginUser(request).getOrgid());
			paramMap.put("sortField", sortField);
			paramMap.put("sortOrder", sortOrder);
			paramMap.put("start", String.valueOf(start));
			paramMap.put("end", String.valueOf(end));
			int count = tbdataSentchageService.countSentenceChangedData(paramMap);// 根据map传参获取总条数
			data = tbdataSentchageService.getSentenceChangedData(paramMap);
			resultmap.put("data", data);
			resultmap.put("total", count);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultmap;
	}

	@RequestMapping("/saveKaoHeZhi.action")
	@ResponseBody
	public Object saveKaoHeZhi(HttpServletRequest request){
		SystemUser user = getLoginUser(request);
		int result = 0;
		String data = request.getParameter("jsons") == null ? "" : request.getParameter("jsons");
		List list = new ArrayList();
		list = (List) JsonUtil.Decode(data);
		if (list != null && list.size() > 0) {
			for(int i=0;i<list.size();i++){
				Map tempmap = (Map)list.get(i);
				Date awardend1 = (Date)tempmap.get("awardend");
				String awardend = DateUtils.date2String(awardend1, "yyyyMMdd");
				String courtsanction1 = (String) tempmap.get("courtsanction");
				String courtsanction  = courtsanction1.replace("-", "");
				
				Map map = new HashMap<String,String>();
				map.put("courtsanction", courtsanction);
				map.put("orgid", user.getOrgid());
				//根据判裁日期和部门编号  查询本部门本次减刑的所有最烦编号
				List<String> jxlist = tbdataSentchageService.selectJxCrimid(map);
				if (jxlist.size()>0) {
					for (int j = 0; j < jxlist.size(); j++) {
						Map tmap = new HashMap<String,String>();
						tmap.put("awardend", awardend);
						tmap.put("courtsanction", courtsanction);
						tmap.put("crimid", jxlist.get(j));
						result = tbdataSentchageService.updateAwardends(tmap);
					}
				}
				
			}
		}
		return result;
	}
	
}
