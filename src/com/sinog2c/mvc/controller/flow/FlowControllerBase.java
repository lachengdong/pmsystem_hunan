package com.sinog2c.mvc.controller.flow;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.InternalResourceView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.gkzx.common.GkzxCommon;
import com.gkzx.util.property.GetProperty;
import com.sinog2c.model.flow.FlowArchives;
import com.sinog2c.model.flow.FlowBaseDoc;
import com.sinog2c.model.flow.FlowBaseOther;
import com.sinog2c.model.flow.FlowDeliver;
import com.sinog2c.model.flow.FlowOtherFlow;
import com.sinog2c.model.flow.UvFlow;
import com.sinog2c.model.system.SystemLog;
import com.sinog2c.model.system.SystemOrganization;
import com.sinog2c.model.system.SystemResource;
import com.sinog2c.model.system.SystemUser;
import com.sinog2c.mvc.controller.base.ControllerBase;
import com.sinog2c.mvc.message.JSONMessage;
import com.sinog2c.service.api.flow.FlowArchivesService;
import com.sinog2c.service.api.flow.FlowArchivesViewService;
import com.sinog2c.service.api.flow.FlowBaseArchivesService;
import com.sinog2c.service.api.flow.FlowBaseDocService;
import com.sinog2c.service.api.flow.FlowBaseOtherService;
import com.sinog2c.service.api.flow.FlowBaseService;
import com.sinog2c.service.api.flow.FlowDeliverService;
import com.sinog2c.service.api.flow.FlowOtherFlowService;
import com.sinog2c.service.api.flow.FlowService;
import com.sinog2c.service.api.flow.UvFlowService;
import com.sinog2c.service.api.prisoner.TbxfSentencealterationService;
import com.sinog2c.service.api.system.SystemLogService;
import com.sinog2c.service.api.system.SystemOrganizationService;
import com.sinog2c.service.api.system.SystemResourceService;
import com.sinog2c.service.api.system.SystemTemplateService;
import com.sinog2c.service.api.system.SystemUserService;
import com.sinog2c.service.api.system.TbViewMaintainService;
import com.sinog2c.util.common.IPUtil;
import com.sinog2c.util.common.StringNumberUtil;

public abstract class FlowControllerBase extends ControllerBase {
	@Autowired
	protected FlowService flowService;
	@Resource
	protected UvFlowService uvFlowService;
	@Resource
	protected FlowBaseService flowBaseService;
	@Resource
	protected FlowBaseDocService flowBaseDocService;
	@Resource
	protected FlowDeliverService flowDeliverService;
	@Resource
	protected FlowArchivesService flowArchivesService;
	@Resource
	protected FlowBaseOtherService flowBaseOtherService;
	@Resource
	protected FlowOtherFlowService flowOtherFlowService;
	@Resource
	protected FlowArchivesViewService flowArchivesViewService;
	@Resource
	protected FlowBaseArchivesService flowBaseArchivesService;
	@Resource
	public SystemLogService logService;
	@Resource
	private SystemResourceService systemResourceService;
	@Resource
	private TbViewMaintainService tbViewMaintainService;
	@Resource
	private SystemOrganizationService systemOrganizationService;
	@Resource
	private TbxfSentencealterationService tbxfSentencealterationService;
	@Resource
	private SystemUserService systemUserService;
	@Resource
	private SystemTemplateService systemTemplateService;
	
	
	/**
	 * 待办加解锁
	 * 
	 * @return
	 */
	@RequestMapping(value = "/operationlock.action")
	@ResponseBody
	public Object operationLock(HttpServletRequest request,
			HttpServletResponse response) {
		//用户对象
		SystemLog log = new SystemLog();
		SystemUser user = getLoginUser(request);
		JSONMessage message = JSONMessage.newMessage();
		//定义变量
		int rows = 0;
		String islocked = GkzxCommon.ZERO;//加锁状态 0解锁
		
		//取得参数
		String type = request.getParameter("type");
		String flowdraftid = request.getParameter("flowdraftid");
		
		try{
			
			//设置要更新的锁状态 
			if(GkzxCommon.YES.equalsIgnoreCase(type)) islocked = GkzxCommon.ONE;
			rows = flowBaseService.updateById(islocked,flowdraftid,user.getUserid(),user.getName());
			
		}catch(Exception e){
			e.printStackTrace();
		}
		if( 1== rows){
			message.setInfo("加锁/解锁成功!");
			message.setSuccess();
		} else {
			message.setInfo("操作失败!");
		}
		// 日志
		log.setOpaction("加锁/解锁成功!");
		log.setOpcontent("新增档案查看信息");
		log.setLogtype("加锁/解锁操作");
		log.setOrgid("operationlock");
		log.setStatus((short)message.getStatus());
		logService.add(log, user);
		return message;
	}
	
	/**
	 * 待办加锁
	 * 
	 * @return
	 */
	@RequestMapping(value = "/ajaxLockCaseOfThisUser.json")
	@ResponseBody
	public Object ajaxLockCaseOfThisUser(HttpServletRequest request,HttpServletResponse response) {
		//定义变量
		int rows = 0;
		String islocked = GkzxCommon.ONE;//加锁状态  1:加锁， 0:解锁
		String flowdraftids = request.getParameter("flowdraftids");
		if(StringNumberUtil.isEmpty(flowdraftids)){
			return rows;
		}
		flowdraftids = StringNumberUtil.formatString(flowdraftids, ",");
		SystemUser user = getLoginUser(request);
		try{
			//设置要更新的锁状态 
			Map paraMap = new HashMap();
			paraMap.put("islocked", islocked);
			paraMap.put("flowdraftids", flowdraftids);
			paraMap.put("suid", user.getUserid());
			paraMap.put("opname", user.getName());
			rows = flowBaseService.updateByFlowdraftids(paraMap);
		}catch(Exception e){
			e.printStackTrace();
		}
		return rows;
	}
	/**
	 * 流程审批显示信息
	 * 
	 * @return
	 */
	@RequestMapping(value = {"/getapprovelist.action", "/getapprovelist.json"})
	@ResponseBody
	public void getApproveList(HttpServletRequest request,
			HttpServletResponse response) {
		
		// 用户对象
		UvFlow uvFlow = new UvFlow();
		SystemUser user = getLoginUser(request);
		SystemOrganization so = systemOrganizationService.getByOrganizationId(user.getDepartid());
		//定义变量
//		String orgid = "";
		String result = "";
		String state = "-1,2";//流转状态
		ArrayList<Object> data = new ArrayList<Object>();
		Object buttonstr = "";
		Map<String, Object> resultmap = new HashMap<String,Object>();
		
		try {
			//取得参数
			String key = request.getParameter("key")==null?"":request.getParameter("key");
			String flowdefid = request.getParameter("flowdefid")==null?"":request.getParameter("flowdefid");
			
			//分页
			int pageIndex =(Integer)(request.getParameter("pageIndex")==null? "":Integer.parseInt(request.getParameter("pageIndex")));
			int pageSize = (Integer)(request.getParameter("pageSize")==null? "":Integer.parseInt(request.getParameter("pageSize")));        
			//字段排序
			String sortField = request.getParameter("sortField");
			String sortOrder = request.getParameter("sortOrder");
			int start = pageIndex * pageSize + 1;
			int end = start + pageSize -1;
			
			//获取当前菜单ID 对应的自定义流程ID 
			Map<String,Object> map = new HashMap<String,Object>();
			if(StringNumberUtil.notEmpty(flowdefid)){
				map.put("flowdefid", flowdefid);
			}
			map.put("userid", user.getUserid());
			map.put("departid", user.getDepartid());
	    	map.put("state", state);
	    	map.put("isarch", GkzxCommon.ZERO);
	    	map.put("suid", user.getUserid());
	    	map.put("key", key);
	    	map.put("sortField", sortField);
	    	map.put("sortOrder", sortOrder);
	    	map.put("start", String.valueOf(start));
	    	map.put("end",String.valueOf(end));
	    	
	    	if(user!=null){
	    		String orgid = user.getOrganization().getOrgid();
//	    		orgid = user.getOrganization().getOrgid();
				map.put("orgid", orgid);
				
				//获取该用户拥有的按钮资源id
		    	buttonstr = this.returnButtonResourceByUser(user,null,null);
		    	map.put("connsql", buttonstr);
	    	}
	    	
	    	Map<String, String> paraMap = new HashMap<String, String>();
	    	paraMap.put("provinceid", so.getPorgid());
	    	List<Map> condiList= tbViewMaintainService.selectByProvinceid(paraMap);
	    	map.put("list", condiList);
	    	//获取总条数
	    	int count = 0;
	    	List<UvFlow> list= new ArrayList<UvFlow>();
	    	Properties jypro = new GetProperty().bornProp(GkzxCommon.DATABASETYPE, null);
			if(jypro!=null){
				String provincecode = jypro.getProperty("provincecode");
				if("fy".equals(provincecode)){//法院代办
					count = uvFlowService.countAllByConditionForCourt(map);
					list= uvFlowService.findByFlowdefidForCourt(map);
				}else{
					count = uvFlowService.countAllByCondition(map);
					list= uvFlowService.findByFlowdefid(map);
				}
			}
			if(list!=null && list.size()>0){
				for(int i=0;i<list.size();i++){
					uvFlow = list.get(i);
					HashMap<String,Object> tempmap = new HashMap<String,Object>();
					String flowid = uvFlow.getFlowid() == null ?"":uvFlow.getFlowid();
					tempmap.put("flowdraftid", uvFlow.getFlowdraftid());
					tempmap.put("flowid", flowid);
					tempmap.put("flowdefid", uvFlow.getFlowdefid());
					tempmap.put("applyid", uvFlow.getApplyid());
					tempmap.put("applyname", uvFlow.getApplyname());
					tempmap.put("conent", uvFlow.getConent());
					tempmap.put("snodeid", uvFlow.getNodeid());
					tempmap.put("endsummry", uvFlow.getEndsummry());
					tempmap.put("startsummry", uvFlow.getStartsummry());
					tempmap.put("commenttext", uvFlow.getCommenttext());
					tempmap.put("startdatetime", uvFlow.getStartdatetime());
					tempmap.put("enddatetime", uvFlow.getEnddatetime());
					tempmap.put("optime", uvFlow.getOptime());
					tempmap.put("opname", uvFlow.getOpname());
					tempmap.put("islocked", uvFlow.getIslocked());
					tempmap.put("orgid", uvFlow.getOrgid());
					tempmap.put("state", uvFlow.getState());
					tempmap.put("opid", uvFlow.getOpid());
					//判断是否加锁
					if(uvFlow.getOpid().equals(user.getUserid())){
						tempmap.put("isshow",GkzxCommon.ONE);
					}
					
//					//判断是否已审批
//					if(orgid.equals(uvFlow.getOrgid())){
//						tempmap.put("ispass", GkzxCommon.ONE);
//					}
					data.add(tempmap);
					//
				}
			}
			
			resultmap.put("data", data);
			resultmap.put("total", count);

			result = new JSONObject(resultmap).toString();
			response.setContentType("text/plain;charset=utf-8"); 
			PrintWriter writer = response.getWriter();
			writer.write(result);
			writer.flush();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	/**
	 * 流程审批显示信息
	 * 
	 * @return
	 */
	@RequestMapping(value = {"/getapprovelistForSD.action", "/getapprovelistForSD.json"})
	@ResponseBody
	public void getApproveListForSD(HttpServletRequest request,
			HttpServletResponse response) {
		
		// 用户对象
		UvFlow uvFlow = new UvFlow();
		SystemUser user = getLoginUser(request);
		SystemOrganization so = systemOrganizationService.getByOrganizationId(user.getDepartid());
		//定义变量
//		String orgid = "";
		String result = "";
		String state = "-1,2";//流转状态
		ArrayList<Object> data = new ArrayList<Object>();
		Object buttonstr = "";
		Map<String, Object> resultmap = new HashMap<String,Object>();
		
		try {
			//取得参数
			String key = request.getParameter("key")==null?"":request.getParameter("key");
			String flowdefid = request.getParameter("flowdefid")==null?"":request.getParameter("flowdefid");
			
			//分页
			int pageIndex =(Integer)(request.getParameter("pageIndex")==null? "":Integer.parseInt(request.getParameter("pageIndex")));
			int pageSize = (Integer)(request.getParameter("pageSize")==null? "":Integer.parseInt(request.getParameter("pageSize")));        
			//字段排序
			String sortField = request.getParameter("sortField");
			String sortOrder = request.getParameter("sortOrder");
			int start = pageIndex * pageSize + 1;
			int end = start + pageSize -1;
			
			//获取当前菜单ID 对应的自定义流程ID 
			Map<String,Object> map = new HashMap<String,Object>();
			if(StringNumberUtil.notEmpty(flowdefid)){
				map.put("flowdefid", flowdefid);
			}
			map.put("userid", user.getUserid());
			map.put("departid", user.getDepartid());
	    	map.put("state", state);
	    	map.put("isarch", GkzxCommon.ZERO);
	    	map.put("suid", user.getUserid());
	    	map.put("key", key);
	    	map.put("sortField", sortField);
	    	map.put("sortOrder", sortOrder);
	    	map.put("start", String.valueOf(start));
	    	map.put("end",String.valueOf(end));
	    	
	    	if(user!=null){
	    		String orgid = user.getOrganization().getOrgid();
//	    		orgid = user.getOrganization().getOrgid();
				map.put("orgid", orgid);
				
				//获取该用户拥有的按钮资源id
		    	buttonstr = this.returnButtonResourceByUser(user,null,null);
		    	map.put("connsql", buttonstr);
	    	}
	    	
	    	Map<String, String> paraMap = new HashMap<String, String>();
	    	paraMap.put("provinceid", so.getPorgid());
	    	List<Map> condiList= tbViewMaintainService.selectByProvinceid(paraMap);
	    	map.put("list", condiList);
	    	//获取总条数
	    	int count = 0;
	    	List<Map> list= new ArrayList<Map>();
	    	Properties jypro = new GetProperty().bornProp(GkzxCommon.DATABASETYPE, null);
			if(jypro!=null){
				count = uvFlowService.countAllByCondition(map);
				list= uvFlowService.findByFlowdefidForSD(map);
			}
			if(list!=null && list.size()>0){
				for(int i=0;i<list.size();i++){
					Map map1 = list.get(i);
					HashMap<String,Object> tempmap = new HashMap<String,Object>();
					tempmap.put("tempid",map1.get("TEMPID"));
					tempmap.put("tempname", map1.get("TEMPNAME"));
					tempmap.put("flowdefid", map1.get("FLOWDEFID"));
					tempmap.put("count", map1.get("COUNT"));
					tempmap.put("islocked", '0');
					tempmap.put("islocked", '0');
					data.add(tempmap);
				}
			}
			
			resultmap.put("data", data);
			resultmap.put("total", count);

			result = new JSONObject(resultmap).toString();
			response.setContentType("text/plain;charset=utf-8"); 
			PrintWriter writer = response.getWriter();
			writer.write(result);
			writer.flush();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	/**
	 * 获取待办事项信息
	 * @return
	 */
	@RequestMapping("getTodoListInfo.json")
	@ResponseBody
	public Object getTodoListInfo(HttpServletRequest request){
		
		SystemUser user = getLoginUser(request);
		List<Map<String,Object>> data = new ArrayList<Map<String,Object>>();
		
		Map<String,Object> paramap = this.parseParamMap(request);
//		paramap = this.parsePageInfoOfMap(paramap);
		
		String departid = user.getDepartid();
		paramap.put("orgid", user.getOrgid());
		paramap.put("userid", user.getUserid());
		paramap.put("departid", user.getDepartid());
		paramap.put("isfjq", user.getInt2());//是否是分监区用户
    	
    	Properties jypro = new GetProperty().bornProp(GkzxCommon.DATABASETYPE, null);
		String distributeflow = jypro.getProperty("distributeflow");//需分案的流程
		String provincecode = jypro.getProperty("provincecode");//当前省份ID
		
		if(departid.equals(provincecode)){//单位ID与provincecode一样，可视为顶级部门（顶级部门的部门过滤与一般的部门过滤有区别）
			paramap.put("departfilter", "yes");
		}else{
			paramap.put("orgfilter", "yes");
		}
		
    	//检查是否需要区分分监区   南宁监狱分监区与监区在同一个部门
    	String isFjquser = this.isFjqUser(null, user);//map --> key=isfjquser  value=1 则为分监区用户
    	
		//获取该用户拥有的按钮资源id
    	Object buttonstr = this.returnButtonResourceByUser(user,distributeflow,isFjquser);
    	paramap.put("connsql", buttonstr);
		//
    	data = uvFlowService.getTodoListInfo(paramap);
		//
		JSONMessage message = JSONMessage.newMessage();
		message.setSuccess();
		message.setData(data);
		return message;
		
	}
	
	/**
	 * 流程审批
	 * 
	 * @return
	 */
	@RequestMapping(value = "/approveview.action")
	public Object approveView(HttpServletRequest request,
			HttpServletResponse response) {
		//资源对象
		returnResourceByUser(request);
		// 用户对象
		FlowBaseDoc flowBaseDoc = new FlowBaseDoc();
		FlowArchives flowArchives = new FlowArchives();
		FlowBaseOther flowBaseOther = new FlowBaseOther();
		FlowOtherFlow flowOtherFlow = new FlowOtherFlow();
		Properties jyconfig = new GetProperty().bornProp(GkzxCommon.DATABASETYPE, null);
		String ningxia = jyconfig.getProperty(GkzxCommon.NINGXIA)==null?"":jyconfig.getProperty(GkzxCommon.NINGXIA);
		if (ningxia.contains(getLoginUser(request).getDepartid())) {
			request.setAttribute("ningxia", 1);
		}else{
			request.setAttribute("ningxia", 0);
		}
		
		//定义变量
		String returnval = "main/approve_view";
		JSONArray docconent=new JSONArray ();
		
		//取得参数
		String flowid = request.getParameter("flowid");
		String flowdefid = request.getParameter("flowdefid");
		String approve = request.getParameter("approve");
		String shenptype = request.getParameter("shenptype");
		
		
		if(flowdefid.contains("arch_")){//电子档案
			flowArchives = flowArchivesService.findByFlowid(flowid);
			if(flowArchives!=null){
				docconent.add(flowArchives.getDocconent());
			}
		}else if(flowdefid.contains("other_")){//需签章的大字段
			flowOtherFlow = flowOtherFlowService.findById(flowid);
			if(flowOtherFlow!=null){
				String otherid = flowOtherFlow.getOtherid();
				if(otherid!=null){
					flowBaseOther = flowBaseOtherService.findById(otherid);
					if(flowBaseOther!=null){
						docconent.add(flowBaseOther.getDocconent());
					}
				}
			}
		}else if(flowdefid.contains("doc_")){//不需要签章的大字段
			flowBaseDoc = flowBaseDocService.findById(flowid);
			if(flowBaseDoc!=null){
				docconent.add(flowBaseDoc.getDocconent());
			}
		}
		//表单显示用
		request.setAttribute("formcontent", docconent.toString());
		//页面判断是否走流程用
		request.setAttribute("approve", approve);
		request.setAttribute("shenptype", shenptype);
		ModelAndView mav = new ModelAndView(returnval);
		return mav;
	}
	/**
	 * 流程审批 流转操作
	 * @return
	 */
	@RequestMapping(value = "/flowapprove")
	@ResponseBody
	public String flowApprove(HttpServletRequest request,
			HttpServletResponse response) {
		
		// 用户对象
		SystemUser user = getLoginUser(request);
		Map<String, Object> paraMap = parseParamMap(request);//参数集合
		
		//定义变量
		String returnval = GkzxCommon.ZERO;
		
		//取得参数
		String data = (String)paraMap.get("data");//获取表单页面的数据
		String examineid = (String)paraMap.get("examineid");//指定审批人
		String orgid = (String)paraMap.get("orgid");//申请人的部门ID
		String applyid = (String)paraMap.get("applyid");//申请人ID
		String applyname = (String)paraMap.get("applyname");//申请人名称
		String operateType = (String)paraMap.get("operateType");//判断新增、修改
		String curyear = (String)paraMap.get("curyear");//案件年度
		String casenumber = (String)paraMap.get("casenumber");//案件号
		String tempid = (String)paraMap.get("tempid");//表单ID
		if(user!=null){
			if(StringNumberUtil.isNullOrEmpty(applyid)) applyid =user.getUserid();//申请人ID
			if(StringNumberUtil.isNullOrEmpty(applyname)) applyname =user.getName();//申请人名称
			if(StringNumberUtil.isNullOrEmpty(orgid)) orgid = user.getOrganization().getOrgid();
		}
		
		//流程流转
		Map<String,Object> tempmap = new HashMap<String,Object>();
		
		if("save".equals(operateType)){
			examineid=user.getUserid();//保存的话取当前人
			paraMap.put("examineid", examineid);
		}
		
		if(StringNumberUtil.notEmpty(curyear)&&StringNumberUtil.notEmpty(casenumber)){
			paraMap.put("casenum", curyear+casenumber);
		}
		tempmap.putAll(paraMap);
		String _ip = IPUtil.getClientIP(request);
		tempmap.put("_ip", _ip);
		returnval = (String)uvFlowService.flowApprove(data, user, tempmap);
		
		
		Properties jyconfig = new GetProperty().bornProp(GkzxCommon.DATABASETYPE, null);
		String provincecode = jyconfig.getProperty("provincecode");
		//湖南的入监登记表要求归档
		if("yes".equals(operateType) && "XFZX_ZFRJDJ".equals(tempid) && "4300".equals(provincecode)){
			flowBaseOtherService.autoExecuteCaseFile();
		}
		
		
		return returnval;
	}
//	/**
//	 * 狱务公开流程审批 流转操作
//	 * @return
//	 */
//	@RequestMapping(value = "/toYwgkflowPage.page")
//	public Object toYwgkflowPage(HttpServletRequest request,HttpServletResponse response) {
//		// 用户对象
//		SystemUser user = new SystemUser();
//		Map<String, Object> paraMap = parseParamMap(request);//参数集合
//		
//		//定义变量
//		String returnval = GkzxCommon.ZERO;
//		String pagename = "ywgk/front/znhdsuccess";
//		String error = "ywgk/front/error";
//		
//		//取得参数
//		String data = (String)paraMap.get("data");//获取表单页面的数据
////		String examineid = (String)paraMap.get("examineid");//指定审批人
//		String orgid = (String)paraMap.get("orgid");//申请人的部门ID
//		String applyid = (String)paraMap.get("crimid");//申请人ID
//		String moduleIds = (String)paraMap.get("moduleids");//模块ID
//		String tempid = (String)paraMap.get("tempid");
//		try{
//			if(moduleIds!=null && moduleIds.length()>0){
//				//查询犯人对应的部门ID
//				Map datamap = MapUtil.turnKeyToLowerCase(tbywgkBaseinfoService.findBaseInfoBycondition(paraMap));
////				TbxfSentenceAlteration sentence = tbxfSentencealterationService.selectByPrimaryKey(applyid);
//				//查询大字段
//				String[] arry = moduleIds.split(",");
//				for(String arr:arry){
//					String[] flowarry = arr.split("@");
//					if(flowarry!=null && flowarry.length>3){
//						//流程流转
//						Map<String,Object> tempmap = new HashMap<String,Object>();
//						tempmap.putAll(paraMap);
//						tempmap.put("applyid", applyid);
//						if(datamap!=null && datamap.size()>0){
//							orgid = StringNumberUtil.returnString2(datamap.get("orgid2"));
//							tempmap.put("orgid", orgid);
//							tempmap.put("departid", StringNumberUtil.returnString2(datamap.get("orgid")));
//							tempmap.put("applyname", StringNumberUtil.returnString2(datamap.get("name")));
//							tempmap.put("tempid", tempid);
//							tempmap.put("docconent", flowarry[3]);//
//							tempmap.put("mapkey1", flowarry[0]);//tbflow_base表存放模块ID
//							tempmap.put("resid", flowarry[1]);//关联资源resid
//							tempmap.put("flowdefid", flowarry[2]);//tbflow_base表  flowdefid
//							
//							//查询犯人对应的部门用户的管教 取第一个人TBSYS_USER_ORG 根据
//							SystemOrganization organization = systemOrganizationService.getByOrganizationId(orgid);
//							user.setDepartid(StringNumberUtil.returnString2(datamap.get("orgid")));
//							user.setUserid(applyid);
//							user.setName(StringNumberUtil.returnString2(datamap.get("name")));
//							user.setOrganization(organization);
//						}
//						returnval = (String)uvFlowService.flowApprove(data, user, tempmap);
//					}
//				}
//			}
//		}catch(Exception e){
//			e.printStackTrace();
//			pagename = error;
//		}
//		
//		request.setAttribute("paramMap", paraMap);
//		return new ModelAndView(pagename);
//	}
	
	
	/**
	 * 审批意见填写进入页面
	 * 
	 * @return
	 */
	@RequestMapping("/toapproveview.action")
	public Object toApproveView(HttpServletRequest request,HttpServletResponse response) {
		Map<String, Object> paraMap = parseParamMap(request);//参数集合
		String returnval = "main/select_window";
		ModelAndView mav = new ModelAndView(returnval,"yjmap",paraMap);
		return mav;
	}
	
	/**
	 * 已办事项
	 * @author kexz
	 * @return
	 */
	@RequestMapping(value="/Havetodoitem")
	public ModelAndView Havetodoitem(HttpServletRequest request) {
		returnResourceMap(request);
		return new ModelAndView(new InternalResourceView(
		"/WEB-INF/JSP/main/Havetodoitem.jsp"));
	}
	
	
	//已办事项列表
	@RequestMapping("/getHavetodoitemList")
	@ResponseBody
	public Object getHavetodoitemList(HttpServletRequest request,
			HttpServletResponse response){
		Map<String, Object> resultmap = new HashMap<String,Object>();
		List<UvFlow> data = new ArrayList<UvFlow>();	
		UvFlow uv=new UvFlow();
		try {
			String key = request.getParameter("key")==null?"":request.getParameter("key");
			String sptime1=request.getParameter("sptime1")==null?"":request.getParameter("sptime1");
			String sptime2=request.getParameter("sptime2")==null?"":request.getParameter("sptime2");
			String sptype=request.getParameter("sptype")==null?"":request.getParameter("sptype");
			int pageIndex =(Integer)(request.getParameter("pageIndex")==null? "":Integer.parseInt(request.getParameter("pageIndex")));
			int pageSize = (Integer)(request.getParameter("pageSize")==null? "":Integer.parseInt(request.getParameter("pageSize")));        
			String sortField = request.getParameter("sortField");
			String sortOrder = request.getParameter("sortOrder");
			int start = pageIndex * pageSize + 1;
			int end = start + pageSize -1;
			
			String opid = getLoginUser(request).getUserid();
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("key", key);
			map.put("sptime1",sptime1);
			map.put("sptime2", sptime2);
			map.put("sptype", sptype);
			map.put("departid", opid);
			map.put("end", end);
			map.put("start", start);
			map.put("sortField", sortField);
			map.put("sortOrder", sortOrder);
			
	    	int count = uvFlowService.selectCount1(map);
	    	data= uvFlowService.selecthavetodoitemByOpid(map);
	    	
	    	resultmap.put("data", data);
			resultmap.put("total", count);
			
		}catch (Exception e) {
		}
		return resultmap;
	}
	
	/**
	 * 根据资源用户查询子资源对应的
	 * 
	 * @return
	 */
	public void returnResourceByUser(HttpServletRequest request){
		Map<String,SystemResource> resmap = new HashMap<String,SystemResource>();
		List<String> toplist = new ArrayList<String>();
		Map<String,String> tempmap = new HashMap<String,String>();
		
		SystemUser user = getLoginUser(request);
		String flowdefid = request.getParameter("flowdefid");
		Map<String, String> map = new HashMap<String, String>();
		List<FlowDeliver> deliverlist = new ArrayList<FlowDeliver>();
		if(flowdefid!=null){
			map.put("id",flowdefid);
			map.put("departid", user.getDepartid());
			deliverlist = flowDeliverService.findByFlowdefid(map);
		}
		
		//资源对象
		List<SystemResource> reslist = systemResourceService.getAllResourceByUser(user);
		if(reslist!=null){
			for(SystemResource res:reslist){
				if(res.getIsmenu() == 0){
					resmap.put(res.getResid(), res);
					resmap.put(res.getPresid(), res);
				}
				//新查询方案 tab页资源
				if(res.getRestypeid() == 17){
					tempmap.put(res.getPresid(), res.getResid());
				}
			}
			if(deliverlist!=null && deliverlist.size()>0){
				for(FlowDeliver de:deliverlist){
					String resid = de.getResid();
					if(resmap.containsKey(resid)){
						SystemResource res = resmap.get(resid);
						String value = res.getResid()+"@"+res.getName()+"@"+res.getSrurl()+"@"+res.getShowico();
						//顶部按钮
						if(res.getRestypeid() == 12){
							toplist.add(value);
						}
					}
					//
					//新查询方案
					if(tempmap.containsKey(resid)){
						List<SystemResource> reslist2 = systemResourceService.listByMenuid(user, tempmap.get(resid), 12);
						for(SystemResource res2:reslist2){
							String value = res2.getResid()+"@"+res2.getName()+"@"+res2.getSrurl()+"@"+res2.getShowico();
							if(toplist.indexOf(value)==-1) toplist.add(value);
						}
					}
				}
			}
			
		}
		
		request.setAttribute("toplist", toplist);
	}
	/**
	 * 判断是否加锁
	 * 
	 * @return
	 */
	@RequestMapping("/ajaxReturnLockUser.json")
	@ResponseBody
	public Object ajaxReturnLockUser(HttpServletRequest request){
		//获取当前用户
		SystemUser user = getLoginUser(request);
		//参数
		Object resultval = -1;
		String flowdraftid = request.getParameter("flowdraftid");//流程草稿ID
		resultval = uvFlowService.ajaxReturnLockUser(flowdraftid,user);
		return resultval;
	}
	
	/**
	 * 判断是否加锁，如果没有被加锁则返回
	 * 
	 * @return
	 */
	@RequestMapping("/ajaxReturnLockUser2.json")
	@ResponseBody
	public Object ajaxReturnLockUser2(HttpServletRequest request){
		
		//获取当前用户
		SystemUser user = getLoginUser(request);
		//参数
		String flowdraftids = request.getParameter("flowdraftids");//流程草稿ID
		return uvFlowService.ajaxReturnLockUser2(flowdraftids,user);
		
	}
	
	/**
	 * 判断是否填写审批意见
	 * 
	 * @return
	 */
	@RequestMapping("/ajaxGetFlowOpinionColumn.json")
	@ResponseBody
	public Object ajaxGetFlowOpinionColumn(HttpServletRequest request){
		//参数
		Object resultval = -1;
		SystemUser user = getLoginUser(request);
		Map<String, Object> paraMap = parseParamMap(request);//参数集合
		Properties jyconfig = new GetProperty().bornProp(GkzxCommon.DATABASETYPE, null);
		String ispopbox = jyconfig.getProperty(GkzxCommon.ISPOPBOX)==null?"":jyconfig.getProperty(GkzxCommon.ISPOPBOX);
		paraMap.put("departid", user.getDepartid());
		resultval = uvFlowService.ajaxGetFlowOpinionColumn(paraMap);
		resultval = resultval+","+ispopbox;
		return resultval;
	}
	
	/**
	 * 方法描述：当操作拒绝案件的时候，把申报状态修改为为申报之前的状态
	 * @author：mushuhong
	 * @version：2015年1月23日10:38:58
	 */
	@RequestMapping(value="/updateIsdeclareByCrimid")
	public void updateIsdeclareByCrimid(HttpServletRequest request){
		this.flowBaseService.updateIsdeclareByCrimid(request, getLoginUser(request));
	}
	///add by blue -----------start
	/**
	 * 流程设计
	 * @author blue
	 * @return
	 */
	@RequestMapping(value="/flowDesign.action")
	public ModelAndView toflowDesign(HttpServletRequest request) {
		returnResourceMap(request);
		return new ModelAndView(new InternalResourceView(
		"/WEB-INF/JSP/system/flowDesign.jsp"));
	}
		
	///add by blue --------------------end
	public static void main(String[] args) {
		 UUID uuid = UUID.randomUUID();
	        System.out.println(uuid);
        System.out.println(uuid);
    }
}
