package com.sinog2c.mvc.controller.flow;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
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
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.InternalResourceView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sinog2c.model.commutationParole.TbxfMergeReference;
import com.sinog2c.model.flow.FlowDeliver;
import com.sinog2c.model.flow.TbflowSetup;
import com.sinog2c.model.system.SystemLog;
import com.sinog2c.model.system.SystemUser;
import com.sinog2c.mvc.message.JSONMessage;
import com.sinog2c.service.api.flow.FlowManageService;
import com.sinog2c.service.api.system.SystemLogService;
import com.sinog2c.service.api.system.SystemOrganizationService;
import com.sinog2c.service.api.system.SystemUserService;
import com.sinog2c.util.common.JsonUtil;
import com.sinog2c.util.common.StringNumberUtil;

/**
 * 
 * @author wangxf
 *
 */
@Controller
public class FlowManageController  extends FlowControllerBase {
	
	@Autowired
	private FlowManageService flowManageService;
	@Resource
	public SystemLogService logService;
	@Autowired
	public SystemOrganizationService organizationservice;
	@Autowired
	public SystemUserService systemUserService;
	
	/**
	 * 跳转流程管理页面
	 * @return
	 */
	@RequestMapping(value = "/flowManage")
	public ModelAndView FileManage() {
		return new ModelAndView("system/liucheng");//原流程页面flowManage
	}
	
	/**
	 * 行文交换
	 * @return
	 */
	@RequestMapping("/xingWenJiaoHuan")
	public ModelAndView XingWenJiaoHuan(HttpServletRequest request){
		returnResourceMap(request);
		return new ModelAndView("system/xingwenjiaohuan");
	}
	/**
	 * 获取行文数据
	 * @param request
	 * @return
	 */
	@RequestMapping("/getXingWenData")
	@ResponseBody
	public Object getXingWenData(HttpServletRequest request){
		SystemUser  user = getLoginUser(request);
		String departid=user.getDepartid();
		String key=request.getParameter("key");
		//分页
		int pageIndex =(Integer)(request.getParameter("pageIndex")==null? "":Integer.parseInt(request.getParameter("pageIndex")));
		int pageSize = (Integer)(request.getParameter("pageSize")==null? "":Integer.parseInt(request.getParameter("pageSize")));        
		//字段排序
		String sortField = request.getParameter("sortField");
		String sortOrder = request.getParameter("sortOrder");
		int start = pageIndex * pageSize + 1;
		int end = start + pageSize -1;
		Map<String,Object> map=new HashMap<String,Object>();
		List<Map> list=new ArrayList<Map>();
		map.put("departid", departid);
		map.put("start", start);
		map.put("end", end);
		map.put("sortField", sortField);
		map.put("sortOrder", sortOrder);
		map.put("key", key);
		list=flowManageService.getXingwenData(map);
		int total=flowManageService.getXingwenCount(map);
		JSONMessage message=new JSONMessage();
		message.setData(list);
		message.setTotal(total);
		return  message;
	}
	/**
	 * 获取行文单位
	 * @return
	 */
	@RequestMapping("/getXingwenDepart")
	@ResponseBody
	public Object getXingwenDepart(HttpServletRequest request){
		List<Map> data=new ArrayList<Map>();
		data=flowManageService.getXingWenDepart();
		return data;
	}
	/**
	 * 复制行文
	 * @param request
	 * @return
	 */
	@RequestMapping("/copyXingWen")
	@ResponseBody
	public Object copyXingWen(HttpServletRequest request){
		String fromid=request.getParameter("fromid");
		String toid=request.getParameter("toid");
		int num=0;
		Map map=new HashMap();
		map.put("fromid", fromid);
		map.put("porgid", toid);
		num=flowManageService.selectXingWenCount(map);
		if(num>0){
			flowManageService.deleXingWen(map);
		}
		String sql="insert into TBDATA_EXCHANGEWRITING(porgid,orgid,classification,subclass,status,Text1,text2,"+
                   "text3,remark) select replace(a.porgid,'"+fromid+"','"+toid+"') as porgid,"+
                   " a.orgid,a.classification,a.subclass,a.status,a.text1,a.text2,a.text3,a.remark"+
                   " from TBDATA_EXCHANGEWRITING a where a.porgid="+fromid;

		map.put("sql", sql);
		String temp=flowManageService.CopyXingWen(map);
		return temp;
	}
	/**
	 * 获取流程树
	 * @return
	 */
	@RequestMapping(value = "/ajaxGetTree")
	@ResponseBody
	public List<HashMap> getTree() {
		List<HashMap> list = flowManageService.selectTree("0");
		if (null != list && list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				HashMap map = (HashMap) list.get(i);
					map.put("expanded", false);
					map.put("isLeaf", false);
					map.put("asyncLoad", false);
				}
			}
		return list;
	}

	/**
	 * 点击树节点返回相应内容
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/ajaxGetFlowTreeData")
	@ResponseBody
	public List<FlowDeliver> findByflowdefid(HttpServletRequest request){
		String id=request.getParameter("id");
		String sortField = request.getParameter("sortField");
		String sortOrder = request.getParameter("sortOrder");
		
		
		Map<String, String> map=new HashMap<String, String>();
		SystemUser su = getLoginUser(request);
		map.put("id", id);
		map.put("departid", su.getDepartid());
		map.put("sortField", sortField);
		map.put("sortOrder", sortOrder);
		List<FlowDeliver> list=new ArrayList<FlowDeliver>();
		list=flowManageService.findByflowdefid(map);
		return list;
	}
	/*
	 * 单个流程明细页面
	 */
	@RequestMapping(value="/openFlowPage")
	public ModelAndView openFlowPage(HttpServletRequest request){
		String flowid=request.getParameter("flowid");
		request.setAttribute("flowid", flowid);
		return new ModelAndView(new InternalResourceView("/WEB-INF/JSP/system/flowdetail.jsp"));
	}
	/**
	 * 跳转至新增页面
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/openFlowInsert")
	public ModelAndView openFlowInsert(HttpServletRequest request){
		return new ModelAndView(new InternalResourceView("/WEB-INF/JSP/system/addFlow.jsp"));
	}
	
	/*
	 * 根据单位id获取本单位流程信息
	 */
	@RequestMapping("/getFlowByDepartid")
	@ResponseBody
	public Object getFlowByDepartid(HttpServletRequest request){
		SystemUser  user = getLoginUser(request);
		String departid=user.getDepartid();
		String key=request.getParameter("key");
		//分页
		int pageIndex =(Integer)(request.getParameter("pageIndex")==null? "":Integer.parseInt(request.getParameter("pageIndex")));
		int pageSize = (Integer)(request.getParameter("pageSize")==null? "":Integer.parseInt(request.getParameter("pageSize")));        
		//字段排序
		String sortField = request.getParameter("sortField");
		String sortOrder = request.getParameter("sortOrder");
		int start = pageIndex * pageSize + 1;
		int end = start + pageSize -1;
		Map<String,Object> map=new HashMap<String,Object>();
		List<FlowDeliver> list=new ArrayList<FlowDeliver>();
		map.put("snodeid", "0");
		map.put("departid", departid);
		map.put("start", start);
		map.put("end", end);
		map.put("sortField", sortField);
		map.put("sortOrder", sortOrder);
		map.put("key", key);
		list=flowManageService.findFlowByDepartid(map);
		int total=flowManageService.flowManageServicenum(map);
		JSONMessage message=new JSONMessage();
		message.setData(list);
		message.setTotal(total);
		return  message;
	}
	@RequestMapping("/copyFlow")
	@ResponseBody
	public String copyFlow(HttpServletRequest request){
		String result="error";
		String fromid = request.getParameter("fromid");
		String toid = request.getParameter("toid");
		Map map=new HashMap();
		map.put("departid", toid);
		map.put("fromid", fromid);
		map.put("toid", toid);
		int temp=flowManageService.findDepartidCount(map);
		if(temp>0){
			result="success";
		int count1=flowManageService.flowManageServicenum(map);
		int count2=flowManageService.getFlowconfByDepartid(map);
		if(count1==0){
			flowManageService.beginCopy1(map);
		}
		if(count2==0){
			String sql="insert into tbsys_flowconf (departid, flowdefid, flowshort, iscreate, remark, optime)" +
					" select replace(t.departid, '"+fromid+"', '"+toid+"') as departid,t.flowdefid, t.flowshort, t.iscreate," +
					" t.remark,t.optime from tbsys_flowconf t where t.departid = '"+fromid+"'";
			map.put("sql", sql);
			flowManageService.beginCopy2(map);
		}
		}
		
		return result;
	}
	
	/**
	 * 根据流程id获取流程信息
	 */
	@RequestMapping("/getFlowById")
	@ResponseBody
	public Object getFlowById(HttpServletRequest request){
		String flowid=request.getParameter("flowid");
		SystemUser  user = getLoginUser(request);
		request.setAttribute("flowid", flowid);
		
		//分页
		int pageIndex =(Integer)(request.getParameter("pageIndex")==null? "":Integer.parseInt(request.getParameter("pageIndex")));
		int pageSize = (Integer)(request.getParameter("pageSize")==null? "":Integer.parseInt(request.getParameter("pageSize")));        
		//字段排序
		String sortField = request.getParameter("sortField");
		String sortOrder = request.getParameter("sortOrder");
		int start = pageIndex * pageSize + 1;
		int end = start + pageSize -1;
		Map<String,Object> map=new HashMap<String,Object>();
		List<FlowDeliver> list=new ArrayList<FlowDeliver>();
		JSONMessage message=new JSONMessage();
		map.put("departid", user.getDepartid());
		map.put("flowid", flowid);
		map.put("sortField", sortField);
		map.put("sortOrder", sortOrder);
		map.put("start", start);
		map.put("end", end);
		list=flowManageService.getFlowById(map);
		int count=flowManageService.getFlowByIdCount(map);
		message.setData(list);
		message.setTotal(count);
		return message;
		
	}
	
	/**
	 *保存修改后的流程信息 
	 */
	@RequestMapping("/saveChangedFlow")
	@ResponseBody
	public int saveChangedFlow(HttpServletRequest request){
		String data=request.getParameter("data");
		SystemUser user=getLoginUser(request);
		String flowid=request.getParameter("flowid");
		int count=0;
		if(null!=data&&!"".equals(data)){
			List<Map> list=(List<Map>)JsonUtil.Decode(data);
				for(int i=0;i<list.size();i++){
					Map map=list.get(i);
					Map tm=new HashMap();
					String resid=(String)map.get("resid");
					String flowdefid=(String)map.get("flowdefid");
					tm.put("resid", resid);
					tm.put("flowdefid", flowdefid);
					tm.put("departid", user.getDepartid());
					count=flowManageService.getDeliverCount(tm);
					tm.clear();
					if(count>1){
						break;
					}
					String snodeid=(String)map.get("snodeid");
					String dnodeid=(String)map.get("dnodeid");
					String explain=(String)map.get("explain");
					int state=Integer.valueOf(String.valueOf(map.get("state"))).intValue();
					String remark=(String)map.get("remark");
					String departid=(String)map.get("departid");
					String opid=(String)map.get("opid");
					String text1=(String)map.get("text1");
					String text2=(String)map.get("text2");
					String text3=(String)map.get("text3");
					
					Map<String,Object> temp=new HashMap<String,Object>();
					temp.put("resid", resid);
					temp.put("snodeid", snodeid);
					temp.put("dnodeid", dnodeid);
					temp.put("explain", explain);
					temp.put("state", state);
					temp.put("flowdefid", flowdefid);
					temp.put("remark", remark);
					temp.put("opid", opid);
					temp.put("text1", text1);
					temp.put("text2", text2);
					temp.put("text3", text3);
					temp.put("departid", departid);
					
					temp.put("userdepartid", user.getDepartid());
					temp.put("flowid", flowid);
					flowManageService.updateFlowInfo(temp);
		}
	}
		return count;
}
	/**
	 * createSequences执行存储过程
	 */
	@RequestMapping("/createSequences")
	@ResponseBody
	public Object createSequences(){
		flowManageService.createSequences();
		return "success";
	}
	
	/**
	 * org表维护页面跳转
	 * @param request
	 * @return
	 */
	@RequestMapping("/openOrgAction")
	public ModelAndView openOrgAction(HttpServletRequest request){
		return new ModelAndView(new InternalResourceView("/WEB-INF/JSP/system/orgorg.jsp"));
	}
	
	/**
	 * 新增流程页面
	 * @param request
	 * @return
	 */
	@RequestMapping(value = {"/addNewFlowPage", "/addFlowAction"})
	public ModelAndView addNewFlowPage(HttpServletRequest request){
		String flowid=request.getParameter("flowid");
		request.setAttribute("int1", "1");
		request.setAttribute("flowid", flowid);
		if(!StringNumberUtil.isNullOrEmpty(flowid)){
			FlowDeliver deliver = flowManageService.setValue(flowid);
			request.setAttribute("remark", deliver.getRemark());
			request.setAttribute("int1", deliver.getInt1());
			request.setAttribute("jiancheng", deliver.getExplain());
		}
		return new ModelAndView(new InternalResourceView("/WEB-INF/JSP/system/addnewflowpage.jsp"));
	}
	
	
	/**
	 * 流程删除removeFlow
	 */
	@RequestMapping("/removeFlow")
	@ResponseBody
	public void removeFlow(HttpServletRequest request){
		String resids=request.getParameter("resid");
		String flowid=request.getParameter("flowid");
		SystemUser user=getLoginUser(request);
		if(null!=resids&&!"".equals(resids)){
			String []str;
			str=resids.split(",");
			for(int i=0;i<str.length;i++){
				Map map=new HashMap();
				map.put("departid", user.getDepartid());
				map.put("resid",str[i]);
				map.put("flowid", flowid);
				flowManageService.removeFlow(map);
			}
		}
	}
	
	/**
	 * 生成TBFLOW_ORG_ORG表数据
	 * 部门能处理别的部门事务表gs`   
	 */
	@RequestMapping("/createOrgOrg")
	@Transactional
	@ResponseBody
	public String createOrgOrg(HttpServletRequest request){
		String fromid=request.getParameter("fromid");
		SystemUser user=getLoginUser(request);
		String fromid2=request.getParameter("fromid2");
		String toid=request.getParameter("toid");
		/*if(null!=fromid && !"".equals(fromid)){
			flowManageService.delteOrgByid(fromid);
		}
		if(null!=fromid2&&!"".equals(fromid2)){
			flowManageService.delteOrgByid(fromid2);
		}*/
		if(null!=toid&&!"".equals(toid)){
			String []str;
			String departname="",tempname="";
			str=toid.split(",");
			Map map=new HashMap();
			if(null!=fromid&&!"".equals(fromid)){
				departname=organizationservice.getNameByid(fromid);
				for(int i=0;i<str.length;i++){
					Map tempmap=new HashMap();
					tempmap.put("str1", fromid);
					tempmap.put("str2", str[i]);
					int num=organizationservice.getNumByid(tempmap);
					if(num>0){
						continue;  
					}
					map.put("dorgid", str[i]);
					tempname=organizationservice.getNameByid(str[i]);
					map.put("orgid", fromid);
					map.put("optime", new Date());
					map.put("opid", user.getUserid());
					map.put("remark",departname+"->"+tempname);
					flowManageService.createOrgOrg(map);
				}
			}
			
			if(null!=fromid2&&!"".equals(fromid2)){
				departname=organizationservice.getNameByid(fromid2);
				for(int i=0;i<str.length;i++){
					map.put("dorgid", str[i]);
					tempname=organizationservice.getNameByid(str[i]);
					map.put("orgid", fromid2);
					map.put("optime", new Date());
					map.put("opid", user.getUserid());
					map.put("remark", departname+"->"+tempname);
					flowManageService.createOrgOrg(map);
				}
			}
			
		}
		
		return "1";
	}
	/**
	 * getOrgInfo 
	 */
	@RequestMapping("/getOrgInfo")
	@ResponseBody
	public Object getOrgInfo(HttpServletRequest request,HttpServletResponse response){
		SystemUser user=getLoginUser(request);
		String departid=user.getDepartid();
		//分页
		int pageIndex =(Integer)(request.getParameter("pageIndex")==null? "":Integer.parseInt(request.getParameter("pageIndex")));
		int pageSize = (Integer)(request.getParameter("pageSize")==null? "":Integer.parseInt(request.getParameter("pageSize")));        
		//字段排序
		String sortField = request.getParameter("sortField");
		String sortOrder = request.getParameter("sortOrder");
		String key=request.getParameter("key");
		int start = pageIndex * pageSize + 1;
		int end = start + pageSize -1;
		Map map=new HashMap();
		map.put("start",start);
		map.put("end", end);
		map.put("departid", departid);
		map.put("key", key);
		List<Map> list=flowManageService.getOrgInfo(map);
		int count=flowManageService.getOrgInfoCount(map);
		JSONMessage message=new JSONMessage();
		message.setData(list);
		message.setTotal(count);
		Map<String, Object> resultmap = new HashMap<String, Object>();
		List<Object> listtemp=new ArrayList<Object>();
		for(int i=0;i<list.size();i++){
			Map<String,Object> maps=new HashMap<String,Object>();
			maps.put("dorgid", list.get(i).get("DORGID"));
			maps.put("orgid", list.get(i).get("ORGID"));
			maps.put("remark", list.get(i).get("REMARK"));
			maps.put("opid", list.get(i).get("OPID"));
			maps.put("optime", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(list.get(i).get("OPTIME")));
			listtemp.add(maps);
		}
		resultmap.put("data", listtemp);
		resultmap.put("total", count);
		PrintWriter writer;
		String result="";
		try {
			result = new JSONObject(resultmap).toString();
			response.setContentType("text/plain;charset=utf-8"); 
			writer = response.getWriter();
			writer.write(result);
			writer.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
		
	}
	
	/**
	 * tbflow_org_org表关系删除
	 */
	@RequestMapping("/removeOrgOrg")
	@ResponseBody
	public Object removeOrgOrg(HttpServletRequest request){
		String orgid=request.getParameter("orgid");
		String dorgid=request.getParameter("dorgid");
		String []orgids;
		String []dorgids;
		if(null!=orgid&&null!=dorgid){
			orgids=orgid.split(",");
			dorgids=dorgid.split(",");
			for(int i=0;i<orgids.length;i++){
				Map map=new HashMap();
				map.put("orgid", orgids[i]);
				map.put("dorgid", dorgids[i]);
				flowManageService.removeOrgOrg(map);
			}
		}
		return "1";
	}
	/**
	 * 新增流程
	 * @param request
	 */
	@RequestMapping("addFlowPage")
	@ResponseBody
	public int addFlowPage(HttpServletRequest request){
		SystemUser user=getLoginUser(request);
		String data=request.getParameter("data");
		int count=0,count2=1;
		Map<String,Object> tempmap=new HashMap<String,Object>();
		String temp="";
		if(data!=null){
			ArrayList<Object> list = (ArrayList<Object>) JSON.parseArray(data, Object.class);
			FlowDeliver flowDeliver=new FlowDeliver();
			for(int i=0;i<list.size();i++){
				Map map=(Map)list.get(i);
				if(i==0){
						tempmap.put("flowdefid", map.get("flowid"));
						tempmap.put("departid", user.getOrganization().getOrgid());
					count2=flowManageService.findCountFlowid(tempmap);
					if(count2==0){
						tempmap.put("iscreate",1 );
						tempmap.put("flowshort", map.get("jiancheng"));
						flowManageService.addFlowToFlowconf(tempmap);
					}
				}
				flowDeliver.setResid((String) map.get("resid"));
				flowDeliver.setExplain((String)map.get("explain"));
				flowDeliver.setSnodeid((String)map.get("snodeid"));
				flowDeliver.setRemark((String)map.get("remark"));
				flowDeliver.setDnodeid((String)map.get("dnodeid"));
				temp=(String)map.get("flowid");
				flowDeliver.setFlowdefid((String)map.get("flowid"));
				flowDeliver.setOpid(user.getUserid());
				flowDeliver.setOptime(new Date());
				flowDeliver.setState((short)0);
				flowDeliver.setDepartid(user.getDepartid());
				flowDeliver.setText1((String)map.get("text1"));
				flowDeliver.setText2((String)map.get("text2"));
				flowDeliver.setText3((String)map.get("text3"));
				flowDeliver.setInt1((Integer.parseInt((String)map.get("int1"))));
				count=flowManageService.insert(flowDeliver);
				
			}
		}
		JSONMessage message = JSONMessage.newMessage();
		if(!(count > 0)){
			message.setInfo("添加失败");
		}else{
			message.setInfo("添加成功！");
			message.setStatus(1);
		}
		SystemLog log = new SystemLog();//日志
		log.setLogtype("流程管理操作");
		log.setOpaction("新增");
		log.setOpcontent("新增流程:" + temp);
		log.setOrgid(user.getUserid());
		log.setRemark("流程新增操作");
		log.setStatus((short)message.getStatus());
		try {
			logService.add(log, user);
		} catch (Exception e) {
			// 吃掉异常
		}
		return count;
	}
	
	
	
	
	/**
	 * 跳转流程配置页面
	 * @return
	 */
	@RequestMapping(value = "/flowSeUpManage")
	public ModelAndView flowSeUpManage() {
		return new ModelAndView("system/flow/flowSeUpList");
	}
	
	/*
	 * 根据单位id获取本单位流程配置信息
	 */
	@RequestMapping("/getChengBanPersons.json")
	@ResponseBody
	public Object getChengBanPersons(HttpServletRequest request){
		SystemUser  user = getLoginUser(request);
		String flowdefid = request.getParameter("flowdefid");
		String nodeid = request.getParameter("nodeid");
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("departid", user.getDepartid());
		map.put("flowdefid", flowdefid);
		map.put("nodeid", nodeid);
		
		List<Map<String,Object>> list=flowManageService.getChengBanPersons(map);
		return list;
	}
	
	
	/*
	 * 根据单位id获取本单位流程配置信息
	 */
	@RequestMapping("/getNextApprovePersons.json")
	@ResponseBody
	public Object getNextApprovePersons(HttpServletRequest request){
		SystemUser  user = getLoginUser(request);
		String flowdefid = request.getParameter("flowdefid");
		String nodeid = request.getParameter("nodeid");
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("departid", user.getDepartid());
		map.put("flowdefid", flowdefid);
		map.put("userid", user.getUserid());
		map.put("nodeid", nodeid);
		
		List<Map<String,Object>> list=flowManageService.getNextApprovePersons(map);
		return list;
	}
	
	/*
	 * 单个流程明细页面
	 */
	@RequestMapping(value="/openFlowSetUpPage")
	public ModelAndView openFlowSetUpPage(HttpServletRequest request){
		String flowdefid=request.getParameter("flowdefid");
		String groupid=request.getParameter("groupid");
		request.setAttribute("groupid", groupid);
		request.setAttribute("flowdefid", flowdefid);
		return new ModelAndView("system/flow/flowSetUpDetail");
	}
	
	/**
	 * 描述：审批人配制页面
	 * @author YangZR	2015-07-16
	 */
	@RequestMapping(value="/toOperateFlowPersonConfigPage.page")
	public ModelAndView toOperateFlowPersonConfigPage(HttpServletRequest request){
		Map<String,Object> map = this.parseParamMap(request);
		this.addMap2Attribute(map, request);
		return new ModelAndView("system/operateFlowPersonConfigPage");
	}
	
	
	@RequestMapping(value = { "/ajaxGetFlowDeliverType.json" })
	@ResponseBody
	public Object ajaxGetFlowDeliverType(HttpServletRequest request) {
		Map<String,Object> paramap = new HashMap<String,Object>();
		String flowdefid = request.getParameter("flowdefid");
		paramap.put("flowdefid", flowdefid);
		return flowManageService.getFlowDeliverType(paramap);
		
	}
	
	@RequestMapping(value = { "/ajaxFlowDeliverNodeName.json" })
	@ResponseBody
	public Object ajaxFlowDeliverNodeName(HttpServletRequest request) {
		String flowdefid =request.getParameter("flowdefid");
		String nodeid =request.getParameter("nodeid");
		if(StringNumberUtil.isEmpty(flowdefid) || StringNumberUtil.isEmpty(nodeid)){
			return null;
		}
		SystemUser user = this.getLoginUser(request);
		Map<String,Object> paramap = new HashMap<String,Object>();
		paramap.put("flowdefid", flowdefid);
		paramap.put("nodeid", nodeid);
		paramap.put("departid", user.getDepartid());
		
		return flowManageService.getFlowDeliverNodeName(paramap);
		
	}
	
	@RequestMapping(value = { "/ajaxGetFlowDeliverNodeid.json" })
	@ResponseBody
	public Object ajaxGetFlowDeliverNodeid(HttpServletRequest request) {
		SystemUser user = this.getLoginUser(request);
		Map<String,Object> paramap = new HashMap<String,Object>();
		String flowdefid = request.getParameter("flowdefid");
		paramap.put("flowdefid", flowdefid);
		paramap.put("departid", user.getDepartid());
		return flowManageService.ajaxGetFlowDeliverNodeid(paramap);
		
	}
	
	@RequestMapping(value = { "/ajaxGetCurrApprovePerson.json" })
	@ResponseBody
	public Object ajaxGetCurrApprovePerson(HttpServletRequest request) {
		String userid =request.getParameter("userid");
		if(StringNumberUtil.isEmpty(userid)){
			return null;
		}
		return flowManageService.getCurrApprovePerson(userid);
		
	}
	
	@RequestMapping(value = { "/ajaxGetDuserApprovePersons.json" })
	@ResponseBody
	public Object ajaxGetDuserApprovePersons(HttpServletRequest request) {
		String userids =request.getParameter("userids");
		if(StringNumberUtil.isEmpty(userids)){
			return null;
		}
		Map<String,Object> paramap = new HashMap<String,Object>();
		userids = StringNumberUtil.formatString(userids, ",");
		paramap.put("userids", userids);
		return flowManageService.getDuserApprovePersons(paramap);
		
	}
	
	@RequestMapping(value = { "/ajaxGetCurrDepartUsers.json" })
	@ResponseBody
	public Object ajaxGetCurrDepartUsers(HttpServletRequest request){
		SystemUser user = this.getLoginUser(request);
		Map<String,Object> paramap = new HashMap<String,Object>();
		paramap.put("departid", user.getDepartid());
		return systemUserService.getCurrDepartUsersByDepartid(paramap);
	}
	
	
	
	/**
	 * 描述：保存审批人配制表
	 * @author YangZR	2015-07-17
	 * @param request
	 * @return
	 */
	@RequestMapping(value = { "/saveFlowPersonConfig.json" })
	@ResponseBody
	public Object saveFlowPersonConfig(HttpServletRequest request){
		
//		String result = "success";
//		short status = 1;
//		result = "error";
//		status = 0;
		
		SystemUser user = getLoginUser(request);
		String json = request.getParameter("data");
		List list = (ArrayList)JsonUtil.Decode(json);
		if(null!=list&&list.size()>0){
			return flowManageService.operateFlowPersonConfig(list, user);
		}
		return null;
	}
	
	/**
	 * 描述：查询审批人配制表
	 * @author YangZR	2015-07-17
	 * @param request
	 * @return
	 */
	@RequestMapping(value = { "/ajaxGetFlowPersonConfigs.json" })
	@ResponseBody
	public Object ajaxGetFlowPersonConfigs(HttpServletRequest request){
		SystemUser user = this.getLoginUser(request);
		String flowdefid = request.getParameter("flowdefid");
		Map<String,Object> paramap = new HashMap<String,Object>();
		paramap.put("flowdefid", flowdefid);
		paramap.put("departid", user.getDepartid());
		
		//分页
		int pageIndex =(Integer)(request.getParameter("pageIndex")==null? "":Integer.parseInt(request.getParameter("pageIndex")));
		int pageSize = (Integer)(request.getParameter("pageSize")==null? "":Integer.parseInt(request.getParameter("pageSize")));        
		//字段排序
		String sortField = request.getParameter("sortField");
		String sortOrder = request.getParameter("sortOrder");
		int start = pageIndex * pageSize + 1;
		int end = start + pageSize -1;
		
		paramap.put("start", start);
		paramap.put("end", end);
		paramap.put("sortField", sortField);
		paramap.put("sortOrder", sortOrder);
		//
		List<Map<String,Object>> list=new ArrayList<Map<String,Object>>();
		list=flowManageService.getFlowPersonConfigs(paramap);
		int total=flowManageService.countFlowPersonConfigs(paramap);
		//
		JSONMessage message=new JSONMessage();
		message.setData(list);
		message.setTotal(total);
		return  message;
		
	}
	
}
