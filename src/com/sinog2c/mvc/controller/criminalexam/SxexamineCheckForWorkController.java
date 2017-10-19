package com.sinog2c.mvc.controller.criminalexam;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import java.util.Calendar;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import com.sinog2c.model.criminalexam.TbyzCheckForWork;
import com.alibaba.fastjson.JSONArray;
import com.sinog2c.dao.api.flow.UvFlowMapper;
import com.sinog2c.model.system.SystemUser;
import com.sinog2c.mvc.message.JSONMessage;
import com.sinog2c.service.api.flow.FlowBaseOtherService;
import com.sinog2c.util.common.JsonUtil;
import com.sinog2c.util.common.MapUtil;
import com.sinog2c.util.common.StringNumberUtil;
import com.sinog2c.mvc.controller.base.ControllerBase;
import com.sinog2c.service.api.criminalexam.SxexamineCheckForWorkService;

@Controller
@RequestMapping("sxexamineCheckForWork")
public class SxexamineCheckForWorkController extends ControllerBase {
	@Resource
	private SxexamineCheckForWorkService sxexamineCheckForWorkService;

	@Autowired
	private UvFlowMapper uvflowmapper;
	@Autowired
	private FlowBaseOtherService flowBaseOtherService;

	@RequestMapping(value = "/sxexamineWorkSet.page")
	public ModelAndView sxexamineWorkSet(HttpServletRequest request) {
		Date today = new Date();
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM");
		String yearDate = df.format(today);
		request.setAttribute("ecwdate", yearDate);
		
		return new ModelAndView("examine/criminalexam/workSet");
	}

	@RequestMapping(value = "/sxexamineWorkSetData.json")
	@ResponseBody
	public Object sxexamineWorkSetData(HttpServletRequest request) {
		
		SystemUser  user = getLoginUser(request);
		String key=request.getParameter("key");
		String yeardate = request.getParameter("yeardate");
		if(StringNumberUtil.isEmpty(yeardate)){
			Date now = new Date();
			yeardate = new SimpleDateFormat("yyyy-MM").format(now);
		}
		//分页
		int pageIndex =(Integer)(request.getParameter("pageIndex")==null? "":Integer.parseInt(request.getParameter("pageIndex")));
		int pageSize = (Integer)(request.getParameter("pageSize")==null? "":Integer.parseInt(request.getParameter("pageSize")));
		int start = pageIndex * pageSize + 1;
		int end = start + pageSize -1;
		
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("key", key);
		map.put("start", start);
		map.put("end", end);
		map.put("yeardate", yeardate);
		map.put("orgid", user.getOrgid());
		
		int count = this.sxexamineCheckForWorkService.getWorkCount(map);
		List<Map> data =  this.sxexamineCheckForWorkService.getListMap(map);
		JSONMessage message = new JSONMessage();
		message.setTotal(count);
		message.setData(data);
		return message;

	}


	@RequestMapping("/openMonthArrovalPage.page")
	public ModelAndView openMonthArrovalPage(HttpServletRequest request) {

		String flowid = request.getParameter("flowid");
		String flowdraftid = request.getParameter("flowdraftid");

		Map<String, String> map = new HashMap<String, String>();
		map.put("flowid", flowid);
		map.put("flowdraftid", flowdraftid);

		JSONArray json = new JSONArray();
		String content = flowBaseOtherService.getDocconentByFlowid(map);
		json.add(content);
		request.setAttribute("formcontent", json.toString());
		return new ModelAndView("examine/criminalexam/showMonthApproval");
	}

	/**
	 * 跳转考核考勤月报表页面
	 */
	@RequestMapping("/toAssessAndAttend.page")
	public ModelAndView toAssessAndAttend(HttpServletRequest request) {
		String orgid = getLoginUser(request).getOrgid();
		String ecwdate = request.getParameter("ecwdate"); 
		Date today = new Date();
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM");
		if(!StringNumberUtil.isNullOrEmpty(ecwdate)){
			try {
				today = df.parse(ecwdate);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		String yearDate = df.format(today);
		request.setAttribute("ecwdate", yearDate);
		Map map = sxexamineCheckForWorkService.getList(orgid, yearDate);
		return new ModelAndView("examine/criminalexam/sxexaminecheckforwork","map",map);
	}
	@RequestMapping("/workSetAddOrUpdate.action")
	@ResponseBody
	public String workSetAddOrUpdate(HttpServletRequest request){
		SystemUser  user = getLoginUser(request);
		String crimid=request.getParameter("crimid");//罪犯id
		String wtype=request.getParameter("wtype");//考勤类型
		String editdate=request.getParameter("editdate");//考勤日期
		int maxDay = 0;
		try {
			maxDay = this.getLastDayOfMonth(editdate);
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		String crimids[]=crimid.split(",");
		Map<String,String> map=new HashMap<String,String>();
		String str="";
		for(int i=0;i<crimids.length;i++){
			
			map.put("crimid",crimids[i]);
			map.put("wtype",wtype);
			map.put("batchop", editdate);
			map.put("maxDay", String.valueOf(maxDay));
			map.put("batchdate", editdate);
			map.put("departid", user.getOrganization().getOrgid());
			map.put("yeardate", editdate);
			map.put("opid", user.getUserid());
			List<TbyzCheckForWork> list = sxexamineCheckForWorkService.workSetSelect(map);
			try{
				if(list.size()>0){
					str=sxexamineCheckForWorkService.workSetDanGeUpdate(map);
				}else{
					str=sxexamineCheckForWorkService.workSetDanGeAdd(map);
				}
				map.clear();
			}catch(Exception e){
				e.printStackTrace();
			}
			
		}
		return str;
	}
	@RequestMapping("/workAddOrUpdate.action")
	@ResponseBody
	public String workAddOrUpdate(HttpServletRequest request){
		SystemUser  user = getLoginUser(request);
		String crimid=request.getParameter("crimid");//罪犯id
		String wtype=request.getParameter("wtype");//考勤类型
		String batchop=request.getParameter("batchop");//获取天
		String yeardate=request.getParameter("yeardate");//考勤日期
		Map<String,String> map=new HashMap<String,String>();
		map.put("wtype",wtype);
		map.put("batchop", batchop);
		map.put("departid", user.getOrganization().getOrgid());
		map.put("opid", user.getUserid());
		map.put(Integer.valueOf(batchop)<10? "day0"+String.valueOf(batchop):"day"+batchop, batchop);
		String[] crimids = crimid.split(",");
		crimids=crimid.split(",");
		String[] batchdates = yeardate.split(",");
		String str="";
			for(int i=0;i<crimids.length;i++){
				map.put("crimid",crimids[i]);
				map.put("yeardate", batchdates[i]);
				map.put("batchdate",batchdates[i]);
				try {
					if(Integer.valueOf(batchop) <= this.getLastDayOfMonth(batchdates[i])){ //设置日期应当小于等于当月天数
						List<TbyzCheckForWork> list = sxexamineCheckForWorkService.workSetSelect(map);
						try{
							if(list.size()>0){
								str=sxexamineCheckForWorkService.workAddOrUpdate(map);
							}else{
								str=sxexamineCheckForWorkService.insertSelective(map);
							}
						}catch(Exception e){
							e.printStackTrace();
						}
					}
				} catch (NumberFormatException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
		
		return str;
	}
	@RequestMapping("/workSetSelect.action")
	@ResponseBody
	public List<TbyzCheckForWork> workSetSelect(HttpServletRequest request){
		String batchdate=request.getParameter("batchdate");
		String key=request.getParameter("crimname");
		Map<String,String> map=new HashMap<String,String>();
		map.put("batchdate",batchdate);
		map.put("key", key);
		return sxexamineCheckForWorkService.workSetSelect(map);
	}
	/**  
	 * 获取某年某月的最后一天  
	 *   
	 * @param year  
	 *            年  
	 * @param month  
	 *            月  
	 * @return 最后一天  
	 * @throws ParseException 
	 */  
	private int getLastDayOfMonth(String editdate) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
		Date date = sdf.parse(editdate);
		String strDate = sdf.format(date);
		String strs[] = strDate.split("-");
		int year = Integer.valueOf(strs[0]);
		int month = Integer.valueOf(strs[1]);
		
		if (month == 1 || month == 3 || month == 5 || month == 7 || month == 8  
				|| month == 10 || month == 12) {   
			return 31;   
		}   
		if (month == 4 || month == 6 || month == 9 || month == 11) {   
			return 30;   
		}   
		if (month == 2) {   
			if ((year % 4 == 0 && year % 100 != 0) || (year % 400 == 0)) {   
				return 29;   
			} else {   
				return 28;   
			}   
		}   
		return 0;   
	}

	@RequestMapping("/danSheZhiUpdate.action")
	@ResponseBody
	public String danSheZhiUpdate(HttpServletRequest request){
		SystemUser  user = getLoginUser(request);
		String data = request.getParameter("data");
		String wtype=request.getParameter("wtype");//考勤类型
		String batchop=request.getParameter("batchop");//获取天
//		String yeardate=request.getParameter("yeardate");//年月
		String str="";
		Map<String,Object> map = (HashMap<String,Object>) JsonUtil.Decode(data);
		map.put("departid", user.getOrgid());
		map.put("opid", user.getUserid());
		map.put("wtype", wtype);
		map.put(batchop, wtype);//设置哪一天的工作类型
//		map.put("yeardate", yeardate);//年月
		List<TbyzCheckForWork> listRow = sxexamineCheckForWorkService.workSetSelect(map);
		try{
			if(listRow.size()>0){
				str=String.valueOf(sxexamineCheckForWorkService.updateSelective(map));
			}else{
				str=sxexamineCheckForWorkService.insertSelective(map);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return str;
	}
	@RequestMapping("/ajaxSaveDataAll.action")
	@ResponseBody
	public String saveDataAll(HttpServletRequest request){
		String ecwdate = request.getParameter("ecwdate");
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM");
		SystemUser  user = getLoginUser(request);
		Date today = new Date();
		if(!StringNumberUtil.isNullOrEmpty(ecwdate)){
			try {
				 today = df.parse(ecwdate);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		String yearDate = df.format(today);
		HashMap map = new HashMap();
		map.put("yeardate",yearDate);
		map.put("departid", user.getOrganization().getOrgid());
		String data = request.getParameter("data");
		String strs[] = data.split(",");
		if(strs.length>0){
			for(int i=0; i<strs.length; i++){//获取单条数据
				String str = strs[i];
				String strs1[] = str.split("@");
				for(int t=0;t<strs1.length;t++){//获取单条数据中的具体数据
					String value = strs1[t];
					String keyValue[] = value.split(":");
					if(keyValue.length>1){
						map.put(keyValue[0], keyValue[1]);
						
					}else if(keyValue.length<2){
						map.put(keyValue[0], "");
					}
               
				}
				int operator = sxexamineCheckForWorkService.selectByCrimid(map);
				int flag = 0;
				if(operator==0){
					flag = sxexamineCheckForWorkService.insertWorkData(map);
				}else{
					
					flag = sxexamineCheckForWorkService.updateWorkData(map);
					
				}
		}
	}
		return "";
  }
}
