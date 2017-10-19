package com.sinog2c.mvc.controller.khjc;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Calendar;
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

import sun.text.normalizer.IntTrie;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sinog2c.model.flow.FlowBase;
import com.sinog2c.model.system.SystemLog;
import com.sinog2c.model.system.SystemUser;
import com.sinog2c.mvc.controller.base.ControllerBase;
import com.sinog2c.mvc.message.JSONMessage;
import com.sinog2c.service.api.flow.FlowBaseService;
import com.sinog2c.service.api.khjc.KhjcKaoHeYueBaoService;
import com.sinog2c.service.api.system.SystemLogService;
import com.sinog2c.util.report.RMEngine;
/**
 * 考核奖惩相关
 * @author yanqutai
 */
@Controller
@RequestMapping("/check")
public class khjcKaoHeYueBaoController extends ControllerBase {
	@Resource
	private KhjcKaoHeYueBaoService khjcKaoHeYueBaoService;
	@Resource
	public SystemLogService logService;
	@Resource
	public FlowBaseService flowBaseService;
	/**
	 * 跳转考核奖惩罪犯专项奖分页面
	 * @author yanqutai
	 * @param request
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/monthcheck.page")
	public ModelAndView khjcZhuanXiangJiangFenPage(HttpServletRequest request){
		returnResourceMap(request);
		String name = request.getParameter("name")==null?"":request.getParameter("name");
		String tempid = request.getParameter("tempid")==null?"":request.getParameter("tempid");
		String idname = request.getParameter("idname")==null?"":request.getParameter("idname");
		String crimid = request.getParameter("crimid")==null?"":request.getParameter("crimid");
		String menuid = request.getParameter("menuid")==null?"":request.getParameter("menuid");
		String fathermenuid = request.getParameter("fathermenuid")==null?"":request.getParameter("fathermenuid");
		Calendar c = Calendar.getInstance();
		int year = c.get(c.YEAR);//得到年
		int month = c.get(Calendar.MONTH);//得到年
		request.setAttribute("caseyear", year);
		request.setAttribute("casemonth", month+1);
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
		request.setAttribute("tempid",tempid);
		request.setAttribute("fathermenuid", fathermenuid);
		return new ModelAndView("khjc/check/monthCheck");
	}
	/**
	 * 获取罪犯列表
	 * 
	 * @author liuxin
	 * @param request
	 * @return resultmap
	 */
	@RequestMapping(value="getCriminalForMonthCheck.json")
	@ResponseBody
	public Object getCriminalForMonthCheck(HttpServletRequest request,
			HttpServletResponse response){
		Map<String, Object> resultmap = new HashMap<String,Object>();
		ArrayList<Object> data = new ArrayList<Object>();
		try {
			// 用户对象
			SystemUser user = getLoginUser(request);
			//取得参数
			//分页
			int pageIndex =(Integer)(request.getParameter("pageIndex")==null? "":Integer.parseInt(request.getParameter("pageIndex")));
			int pageSize = (Integer)(request.getParameter("pageSize")==null? "":Integer.parseInt(request.getParameter("pageSize")));        
			//字段排序
			String sortField = request.getParameter("sortField");
			String sortOrder = request.getParameter("sortOrder");
			int start = pageIndex * pageSize + 1;
			int end = start + pageSize -1;
			HashMap<Object,Object> map = new HashMap<Object,Object>();
			
	    	map.put("sortField", sortField);
	    	map.put("sortOrder", sortOrder);
	    	map.put("start", String.valueOf(start));
	    	map.put("end",String.valueOf(end));
	    	map.put("orgid", user.getOrgid());
	    	map.put("biddate", "201505");
	    	map.put("lastbiddate", "201504");
	    	//根据map传参获取总条数
	    	int count = khjcKaoHeYueBaoService.countForMonthCheck(map);
	    	//根据map传参获取罪犯列表
	    	List<HashMap<Object, Object>> list = khjcKaoHeYueBaoService.getCriminalForMonthCheck(map);
	    	//获取总条数
			if(list!=null && list.size()>0){
				for(int i=0;i<list.size();i++){
					HashMap<Object,Object> listmap = list.get(i);
					HashMap<Object,Object> tempmap = new HashMap<Object,Object>();
					for(Object obj:listmap.keySet()){
						tempmap.put(((String)obj).toLowerCase(), listmap.get(obj));
					}
					data.add(tempmap);
				}
			}

	    	resultmap.put("data", data);
			resultmap.put("total", count);
		}catch (Exception e) {
		}
		return resultmap;
	}
	/**
	 * 保存月考核数据
	 * 
	 * @author zhenghui
	 * @param request
	 * @return message
	 */
	@RequestMapping(value="ajaxBySave.json")
	@ResponseBody
	public Object ajaxBySave(HttpServletRequest request){
		//用户对象
		int rows = -1;
		SystemLog log = new SystemLog();
		SystemUser user = getLoginUser(request);
		JSONMessage message = JSONMessage.newMessage();
		//获取数据
		String operatetype = request.getParameter("operatetype");//状态更新或修改
		String caseyear=request.getParameter("caseyear")==null?"":request.getParameter("caseyear");
		String casemonth=request.getParameter("casemonth")==null?"":request.getParameter("casemonth");
		if(Integer.valueOf(casemonth) < 10){
			casemonth = "0"+casemonth;
		}
		String data = request.getParameter("data");//json 数据
		if(data!=null){
	        ArrayList<Object> list = (ArrayList<Object>) JSON.parseArray(data, Object.class);
			if(list!=null && list.size()>0){
				for(int i=0;i<list.size();i++){
					Map map = (Map)list.get(i);
					String biddate = (String)(map.get("biddate") == null?"":map.get("biddate"));
					if(biddate!=null && !"".equals(biddate)){
						rows = khjcKaoHeYueBaoService.updateByMap(map);
					}else{
						biddate = caseyear+casemonth;
						map.put("biddate",biddate);
						rows = khjcKaoHeYueBaoService.insertByMap(map);
					}
					if( 1== rows){
						message.setInfo("更新成功!");
						message.setSuccess();
					} else {
						message.setInfo("操作失败!");
					}
					// 日志
					log.setLogtype("monthchek");
					log.setOpaction("ajaxBySave.json");
					log.setOpcontent("月考核信息新增/更新,resid=");
					log.setOrgid(user.getOrganization().getOrgid());
					log.setRemark("更新成功!");
					log.setStatus((short)message.getStatus());
					logService.add(log, user);
				}
			}
		}
		return message;
	}
	/**
	 * 查询当前罪犯连续12个月的累积分和专项奖分（山东）
	 * @param request
	 * @return
	 */
	@RequestMapping(value="ajaxSearchScoreByCrimid.json")
	@ResponseBody
	public Object ajaxSearchScoreByCrimid(HttpServletRequest request){
		Map<String, String> message = new HashMap<String, String>(); 
		//获取数据
		String date=request.getParameter("date")==null?"":request.getParameter("date");
		String crimid = request.getParameter("crimid")==null?"":request.getParameter("crimid");
		
		if(!"".equals(date) && !"".equals(crimid)){
			//页面传输的date格式为xxxx年xx月xx日，进行处理
			String[] str1 = date.split("月");
			date = str1[0].replace("年","");
			Map<String,String> map = new HashMap<String, String>();
			map.put("crimid", crimid);
			map.put("date", date);
			List<Map> list = khjcKaoHeYueBaoService.searchScoreByCrimid(map);
			if(list.size() > 0){
				Map<String,String> map2 = list.get(0);
				message = map2;
			}
		}
		
		return message;
	}
	
	
	/**
	 * * @author zhenghui
	 * @param request
	 * 进提交月报表页面
	 */
	@RequestMapping("/toMonthReport.page")
	public ModelAndView toMonthReport(HttpServletRequest request, HttpServletResponse response) {
		returnResourceMap(request);
		//用户对象
		SystemUser user = getLoginUser(request);
		request.setAttribute("nodeid", request.getParameter("nodeid"));
		request.setAttribute("identity", request.getParameter("identity"));
		request.setAttribute("flowdraftid", request.getParameter("flowdraftid"));
		RMEngine engine=this.systemResourceService.queryQualificationDataRmEngine("", user, request);
		request.setAttribute("mydata", engine.dedaoReportData());
		ModelAndView mav = new ModelAndView("khjc/check/monthreport");
		return mav;
	}
	/**
	 * * @author zhenghui
	 * @param request
	 * 进月报表信息查看页面
	 */
	@RequestMapping("/toMonthReportView.page")
	public ModelAndView toMonthReportView(HttpServletRequest request, HttpServletResponse response) {
		returnResourceMap(request);
		//用户对象
		SystemUser user = getLoginUser(request);
		Calendar c = Calendar.getInstance();
		int year = c.get(c.YEAR);//得到年
		int month = c.get(Calendar.MONTH);//得到年
		request.setAttribute("caseyear", year);
		request.setAttribute("casemonth", month+1);
		ModelAndView mav = new ModelAndView("khjc/check/monthreportview");
		return mav;
	}
	/**
	 * 月考核数据查询
	 * 
	 * @author zhenghui
	 * @param request
	 * @return resultjson
	 */
	@RequestMapping(value="ajaxGetMonthReport.json")
	@ResponseBody
	public Object ajaxGetMonthReport(HttpServletRequest request){
		Object json = "";
		//用户对象
		SystemUser user = getLoginUser(request);
		RMEngine engine=this.systemResourceService.queryQualificationDataRmEngine("", user, request);
		json = engine.dedaoReportData();
		return engine.dedaoReportData();
	}
	/**
	 * 方法描述：列表页面
	 * 
	 * @author 
	 */
	@RequestMapping("/ajaxGetApprovalList.json")
	@ResponseBody
	public HashMap<String, Object> ajaxGetApprovalList(HttpServletRequest request) throws Exception{
		String key = request.getParameter("key")==null? "":request.getParameter("key");
		String flowdefid = request.getParameter("flowdefid")==null? "":request.getParameter("flowdefid");
		key = URLDecoder.decode(key,"UTF-8");
		SystemUser user = getLoginUser(request);
		HashMap<String, Object> result = new HashMap<String, Object>();
		//获取当前登录的用户
		String deptId=user.getDepartid();
		//分页
		int pageIndex =(Integer)(request.getParameter("pageIndex")==null? "":Integer.parseInt(request.getParameter("pageIndex")));
		int pageSize = (Integer)(request.getParameter("pageSize")==null? "":Integer.parseInt(request.getParameter("pageSize")));        
		//字段排序
		String sortField = request.getParameter("sortField");
		String sortOrder = request.getParameter("sortOrder");
		int start = pageIndex * pageSize + 1;
		int end = start + pageSize -1;

		Map<String,Object> map = new HashMap<String,Object>();
		map.put("departid", deptId);
		map.put("key", key);
		map.put("flowdefid", flowdefid);
    	map.put("sortField", sortField);
    	map.put("sortOrder", sortOrder);
    	map.put("start", String.valueOf(start));
    	map.put("end",String.valueOf(end));
    	List<FlowBase> list = flowBaseService.getBaseListNotInforming(map);
		int count = flowBaseService.getBaseListNotInformingCount(map);
		result.put("total", count);
		result.put("data", list);
		return result;		
	}
}
