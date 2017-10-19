package com.sinog2c.service.impl.flow;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.gkzx.common.GkzxCommon;
import com.gkzx.util.property.GetProperty;
import com.sinog2c.dao.api.commutationParole.TbxfPunishmentRangMapper;
import com.sinog2c.dao.api.commutationParole.TbxfScreeningMapper;
import com.sinog2c.dao.api.commutationParole.TbxfSentenceAlterationMapper;
import com.sinog2c.dao.api.flow.FlowArchivesExistMapper;
import com.sinog2c.dao.api.flow.FlowArchivesMapper;
import com.sinog2c.dao.api.flow.FlowArchivesViewMapper;
import com.sinog2c.dao.api.flow.FlowBaseArchivesMapper;
import com.sinog2c.dao.api.flow.FlowBaseDocMapper;
import com.sinog2c.dao.api.flow.FlowBaseMapper;
import com.sinog2c.dao.api.flow.FlowBaseOtherMapper;
import com.sinog2c.dao.api.flow.FlowDeliverMapper;
import com.sinog2c.dao.api.flow.FlowMapper;
import com.sinog2c.dao.api.flow.FlowOtherFlowMapper;
import com.sinog2c.dao.api.flow.UvFlowMapper;
import com.sinog2c.dao.api.system.TbsysDocumentMapper;
import com.sinog2c.model.flow.Flow;
import com.sinog2c.model.flow.FlowArchives;
import com.sinog2c.model.flow.FlowArchivesView;
import com.sinog2c.model.flow.FlowBase;
import com.sinog2c.model.flow.FlowBaseArchives;
import com.sinog2c.model.flow.FlowBaseDoc;
import com.sinog2c.model.flow.FlowBaseOther;
import com.sinog2c.model.flow.FlowDeliver;
import com.sinog2c.model.flow.FlowOtherFlow;
import com.sinog2c.model.flow.UvFlow;
import com.sinog2c.model.officeAssistant.TbuserNotice;
import com.sinog2c.model.officeAssistant.TbuserUserNotice;
import com.sinog2c.model.system.SystemLog;
import com.sinog2c.model.system.SystemOrganization;
import com.sinog2c.model.system.SystemUser;
import com.sinog2c.model.system.TbsysDocument;
import com.sinog2c.model.system.TbsysTemplate;
import com.sinog2c.mvc.message.JSONMessage;
import com.sinog2c.service.api.common.CommonSQLSolutionService;
import com.sinog2c.service.api.dbmsnew.DataTransferService;
import com.sinog2c.service.api.flow.FlowBaseService;
import com.sinog2c.service.api.flow.FlowDeliverService;
import com.sinog2c.service.api.flow.UvFlowService;
import com.sinog2c.service.api.officeAssistant.UserNoticeService;
import com.sinog2c.service.api.system.SystemLogService;
import com.sinog2c.service.api.system.SystemModelService;
import com.sinog2c.service.api.system.SystemOrganizationService;
import com.sinog2c.service.impl.base.ServiceImplBase;
import com.sinog2c.thread.ArchivesCopyRunnable;
import com.sinog2c.util.common.DateUtil;
import com.sinog2c.util.common.FileUtil;
import com.sinog2c.util.common.GetAbsolutePath;
import com.sinog2c.util.common.MapUtil;
import com.sinog2c.util.common.StringNumberUtil;

@Service("uvFlowService")
public class UvFlowServiceImpl extends ServiceImplBase implements UvFlowService {

	@Autowired
    private UserNoticeService userNoticeService;
	@Autowired
	private UvFlowMapper uvFlowMapper;
	@Autowired
	private FlowMapper flowMapper;
	@Autowired
	private FlowBaseMapper flowBaseMapper;
	@Autowired
	private FlowDeliverMapper flowDeliverMapper;
	@Autowired
	private FlowBaseOtherMapper flowBaseOtherMapper;
	@Autowired
	private FlowOtherFlowMapper flowOtherFlowMapper;
	@Autowired
	private FlowArchivesMapper flowArchivesMapper;
	@Autowired
	private FlowBaseDocMapper flowBaseDocMapper;
	@Autowired
	private FlowArchivesExistMapper flowArchivesExistMapper;
	@Autowired
	private FlowBaseArchivesMapper flowBaseArchivesMapper;
	@Autowired
	private FlowArchivesViewMapper flowArchivesViewMapper;
	@Autowired
	public SystemLogService logService;
	@Autowired
	public TbxfScreeningMapper tbxfScreeningMapper;
	@Autowired
	public SystemOrganizationService systemOrganizationService;
	@Autowired
	public SystemModelService systemModelService;
	@Autowired
	public TbsysDocumentMapper tbsysDocumentMapper;
	@Autowired
	public TbxfSentenceAlterationMapper tbxfSentenceAlterationMapper;
	@Autowired
	public FlowBaseService flowBaseService;
	@Autowired
	private FlowDeliverService flowDeliverService;
	@Autowired
	private TbxfPunishmentRangMapper tbxfPunishmentRangMapper;
	@Autowired
	private CommonSQLSolutionService solutionService;
	@Autowired
	private DataTransferService dataTransferService;
	
	
	public List<UvFlow> findByFlowdefid(Map<String, Object> map) {
		return this.uvFlowMapper.findByFlowdefid(map);
	}
	public List<Map> findByFlowdefidForSD(Map<String, Object> map) {
		return this.uvFlowMapper.findByFlowdefidForSD(map);
	}

	@Override
	public int countByMapParams(Map<String, Object> map) {
		return this.uvFlowMapper.countByMapParams(map);
	}
	
	@Override
	public List<Map<String,Object>> getTodoListInfo(Map<String, Object> map){
		return MapUtil.convertKeyList2LowerCase(uvFlowMapper.getTodoListInfo(map));
	}
	
//	@Override
//	public int countTodoListInfo(Map<String, Object> map){
//		return this.uvFlowMapper.countTodoListInfo(map);
//	}

	@Override
	public List<UvFlow> findByMapParams(Map map) {
		return this.uvFlowMapper.findByMapParams(map);
	}

	public int countAllByCondition(Map<String, Object> map) {
		return this.uvFlowMapper.countAllByCondition(map);
	}

	public int countAllOfCommuteByCondition(Map map) {
		return uvFlowMapper.countAllOfCommuteByCondition(map);
	}

	public List<Map> findCommutatioByCondition(Map map) {
		return MapUtil.turnKeyToLowerCaseOfList(uvFlowMapper.findCommutatioByCondition(map));
	}

	public int countAllOfJinBanRenCommute(Map map) {
		return uvFlowMapper.countAllOfJinBanRenCommute(map);
	}

	public List<Map> findCommutatioOfJianBanRen(Map map) {
		return MapUtil.turnKeyToLowerCaseOfList(uvFlowMapper.findCommutatioOfJianBanRen(map));
	}

	public String getFlowidByFlowdraftid(Map map) {
		return uvFlowMapper.getFlowidByFlowdraftid(map);
	}

	// 已办事项
	public List<UvFlow> selecthavetodoitemByOpid(Map<String, Object> map) {
		return uvFlowMapper.selecthavetodoitemByOpid(map);
	}

	// 分页
	public int selectCount1(Map map) {
		return uvFlowMapper.selectCount1(map);
	}

	@Override
	public int countAllOfProvinceCommuteByCondition(Map map) {
		return uvFlowMapper.countAllOfProvinceCommuteByCondition(map);
	}
	
	@Override
	public List<Map> getCasenumsOfCommuteByCondition(Map map) {
		return MapUtil.turnKeyToLowerCaseOfList(uvFlowMapper.getCasenumsOfCommuteByCondition(map));
	}

	@Override
	public List<Map> findProvinceCommutatioByCondition(Map map) {
		return MapUtil.turnKeyToLowerCaseOfList(uvFlowMapper
				.findProvinceCommutatioByCondition(map));
	}

	@Override
	public List<Map> getCaseCheckList(Map map) {
		return MapUtil.turnKeyToLowerCaseOfList(uvFlowMapper.getCaseCheckList(map));
	}

	@Override
	public int getCountCaseCheckList(Map map) {
		return uvFlowMapper.getCountCaseCheckList(map);
	}

	@Override
	public Map getPrisonerParoleType(Map map) {
		return MapUtil.turnKeyToLowerCase(uvFlowMapper.getPrisonerParoleType(map));
	}

	@Override
	public String getFlowidByCrimid(Map map) {
		return uvFlowMapper.getFlowidByCrimid(map);
	}

	
//	/**
//	 * 操作流程信息
//	 * 
//	 * @return
//	 */
//	@Transactional
//	public Object operationFlow(SystemUser user, Map<String, Object> map) {
//
//		// 用户对象
//		UvFlow uvflow = null;
//		SystemLog log = new SystemLog();
//		JSONMessage message = JSONMessage.newMessage();
//
//		// 定义常量
//		int rows = 0;
//		short state = -2;
//		String returnval = GkzxCommon.ZERO;
//		Short dstate = -2;
//		String lastnodeid = "";
//		String detype = GkzxCommon.ONE;
//		Date now = new Date();
//		Map<String, Object> datamap = new HashMap<String, Object>();
//
//		// 取得参数
//		String type = "";// 判断新增、修改
//		String departid = StringNumberUtil.returnString2(user.getDepartid());// 组织ID
//		String resid = StringNumberUtil.returnString2(map.get("resid"));// 和流程相关按钮ID
//		String tempid = StringNumberUtil.returnString2(map.get("tempid"));// 表单ID
//		String flowid = StringNumberUtil.returnString2(map.get("flowid"));// 流程ID
//		String flowdefid = StringNumberUtil.returnString2(map.get("flowdefid"));// 流程自定义ID
//		String orgid = StringNumberUtil.returnString2(map.get("orgid"))==null||StringNumberUtil.returnString2(map.get("orgid")).equals("")?user.getOrganization().getOrgid():StringNumberUtil.returnString2(map.get("orgid"));// 用户部门ID
//		//String orgid = StringNumberUtil.returnString2(map.get("orgid"));// 用户部门ID
//		if("report".equals(tempid)&&StringNumberUtil.isNullOrEmpty(orgid)){
//			orgid=user.getOrganization().getOrgid();
//		}
//		String flowdraftid = StringNumberUtil.returnString2(map.get("flowdraftid"));// 流程草稿ID
//		String operateType = StringNumberUtil.returnString2(map.get("operateType"));// 判断新增、修改
//
//		Date applydatetime = now;
//		String dnodeid = GkzxCommon.ZERO;// 目的节点ID 源节点snodeid=0 为起始节点
//		String commenttext = StringNumberUtil.returnString2(map.get("commenttext"));// 审批意见
//		String conent = StringNumberUtil.returnString2(map.get("conent"));// 内容
//		String applyid = StringNumberUtil.returnString2(map.get("applyid"));// 申请人ID
//		String applyname = StringNumberUtil.returnString2(map.get("applyname"));// 申请人姓名
//		String docconent = StringNumberUtil.returnString2(map.get("docconent"));
//
//		try {
//			// 添加业务信息到日志
//			// 日志
//			log.setOpaction("流程操作用户信息!");
//			log.setOpcontent("新增用户信息" + user.getUserid() + ","+ user.getName());
//			log.setLogtype("operationFlow");
//			log.setOrgid(user.getOrganization().getOrgid());
//			log.setStatus((short) message.getStatus());
//			logService.add(log, user);
//
//			// 获取流程表对应信息
//			List<FlowDeliver> deliverlist = flowDeliverMapper.findByParam(flowdefid, departid);
//			if (!"".equals(flowdraftid)) {
//				// 获取上次的流水信息
//				uvflow = uvFlowMapper.getFlowByFlowdraftid(flowdraftid);
//				if (uvflow != null) {
//					orgid = uvflow.getOrgid();
//					flowid = uvflow.getFlowid();
//					lastnodeid = uvflow.getNodeid();
//					applyid = uvflow.getApplyid();
////					if(applyname.equals(uvflow.getApplyname()))
//					applyname = uvflow.getApplyname();
//					flowdefid = uvflow.getFlowdefid();
//					dstate = uvflow.getState().shortValueExact();
//				}
//				// 加锁
//				rows = flowBaseMapper.updateById(GkzxCommon.ZERO, flowdraftid,
//						user.getUserid(), user.getName());
//			}
//
//			// /放入数据到datamap 用于保存和更新
//			datamap.putAll(map);
//			datamap.put("resid", resid);
//			datamap.put("tempid", tempid);
//			datamap.put("flowid", flowid);
//			datamap.put("flowdefid", flowdefid);
//			datamap.put("flowdraftid", flowdraftid);
//			datamap.put("dnodeid", dnodeid);
//			datamap.put("flowdraftid", flowdraftid);
//			datamap.put("state", state);
//			datamap.put("commenttext", commenttext);
//			datamap.put("applydatetime", applydatetime);
//			datamap.put("conent", conent);
//			datamap.put("applyname", applyname);
//			datamap.put("orgid", orgid);
//			
//			// 操作流程
//			String flowtype = GkzxCommon.ZERO;
//			String updatetype = GkzxCommon.ZERO;
//			if ("save".equalsIgnoreCase(operateType)) {// 状态为保存时 save
//				state = -2;
//				datamap = this.updateMapData("state", String.valueOf(state),datamap);
//				if (flowdraftid == null || "".equals(flowdraftid)) {
//					type = GkzxCommon.NEW;
//					flowdraftid = flowMapper.getFlowid(orgid, departid,flowdefid, 0);// 获取流程草稿ID
//				} else {
//					type = GkzxCommon.EDIT;
//					detype = GkzxCommon.ZERO;
//					updatetype = GkzxCommon.ONE;
//					if (dstate == -2) flowtype = GkzxCommon.ONE;
//				}
//
//				// //
//			} else{// 状态为提交时
//				state = -1;
//				// 更新状态
//				if (!StringNumberUtil.isNullOrEmpty(flowdraftid)) rows = flowMapper.updateFlowState(GkzxCommon.ONE, flowdraftid);
//
//				if (StringNumberUtil.isNullOrEmpty(flowdraftid)) {
//					type = GkzxCommon.NEW;
//					// 获取流程ID
//					String tempflowval = flowMapper.getFlowid(orgid, departid,flowdefid, 1);
//					// 判断该单位是否存在流程
//					if (!StringNumberUtil.isEmpty(tempflowval) && tempflowval.contains(",")) { 
//						flowid = tempflowval.split(",")[0];
//						flowdraftid = tempflowval.split(",")[1];
//					}
//
//				} else {
//					type = GkzxCommon.EDIT;
//					updatetype = GkzxCommon.ONE;
//					// 获取流程ID
//					if (flowid == null || "".equals(flowid)) {
//						String tempflowval = flowMapper.getFlowid(orgid,departid, flowdefid, 1);
//						if (tempflowval.length() > 0) { // 判断该单位是否存在流程
//							String[] flowval = tempflowval.split(",");
//							flowid = flowval[0];
//						}
//					}
//				}
//
//			}
//			/////////////////////////////
//			// 新增时将流程草稿ID返回
//			returnval = flowdraftid;
//			// 更新datamap 字段对应的值
//			datamap = this.updateMapData("flowid", flowid, datamap);
//			datamap = this.updateMapData("flowdraftid", flowdraftid,datamap);
//			datamap = this.getDeliverMap(operateType, user,uvflow, datamap);
//			// flow
//			dealFlowData(flowtype, datamap, user);
//			// 更新flow_base
//			rows = dealFlowBaseData(updatetype, datamap, user);
//			// ////////////////////////////////////////////////
//
//			// 判断分类 审批签章类 、文本类
//			if (flowdefid != null) {
//				if (flowdefid.contains("other_")) {// 签章审批流转
//					this.operationFlowForSignature(flowid, flowdraftid,tempid, docconent, user, type);
//				} else if (flowdefid.contains("doc_")) {// 不同审批流转
//					this.operationFlowForOrdinary(flowid, flowdraftid,tempid, docconent, user, type);
//				} else if (flowdefid.contains("arch_")) {// 电子档案审批流转
//					this.operationFlowForArch(flowid,flowdefid,docconent,map, user);
//				}
//			}
//			if(rows>1) returnval = flowdraftid;
////			returnval =String.valueOf(rows);
//		} catch (Exception e) {
//			returnval = "-1";
//			e.printStackTrace();
//		}
//
//		return returnval;
//	}
	
	/**
	 * 操作流程信息
	 * 
	 * @return
	 */
	@Transactional
	@Override
	public String operationFlow(SystemUser user, Map<String, Object> map) {

		UvFlow uvflow = null;
		String returnval = GkzxCommon.ZERO;
		String flowdraftid = StringNumberUtil.getStrFromMap("flowdraftid", map);
		if(StringNumberUtil.notEmpty(flowdraftid)){
			uvflow = uvFlowMapper.getFlowByFlowdraftid(flowdraftid);
		}
		Map<String,Object> resultMap = operationFlowByParam(user, map, uvflow);
		//
		flowdraftid = StringNumberUtil.getStrFromMap("flowdraftid", resultMap);
		if(StringNumberUtil.notEmpty(flowdraftid)){
			returnval = flowdraftid;
		}
		return returnval;
	}
	
	/**
	 * 操作流程信息
	 * 
	 * @return
	 */
	public Map<String,Object> operationFlowByParam(SystemUser user, Map<String, Object> map, UvFlow uvflow) {
		Map<String, Object> returnMap = new HashMap<String, Object>();
		
		int rows = 0;
		Short dstate = -2;
		//
		String flowdraftid = StringNumberUtil.returnString2(map.get("flowdraftid"));//流程草稿ID
		String flowid = StringNumberUtil.returnString2(map.get("flowid"));// 流程ID
		String operateType = StringNumberUtil.returnString2(map.get("operateType"));// 判断新增、修改
		String newcompount = StringNumberUtil.returnString2(map.get("newcompount"));//是否是公共配制的流程 : 1为是
		//
		String type = "";//操作与流程有关表的标志：new 新增    edit 修改
		String opFlowType = GkzxCommon.ZERO;//0:插入新增，1：更新
		String opFlowBaseType = GkzxCommon.ZERO;//0:插入新增   1：更新
		//
		if(StringNumberUtil.isEmpty(flowdraftid)){
			type = GkzxCommon.NEW;
			flowdraftid = StringNumberUtil.getUUID();
			flowid = StringNumberUtil.getUUID();
		}else{
			type = GkzxCommon.EDIT;
			opFlowBaseType = GkzxCommon.ONE;
			if(null != uvflow){
				flowid = uvflow.getFlowid();
				dstate = uvflow.getState().shortValueExact();
			}
			
			if("save".equalsIgnoreCase(operateType)){
				if (dstate == -2 || dstate == 0){
					opFlowType = GkzxCommon.ONE;
				}
			}else{
				map.put("lastopname", user.getName());
				flowMapper.updateFlowState(GkzxCommon.ONE,flowdraftid);
			}
		}
		
		if(StringNumberUtil.isEmpty(flowid)){
			flowid = StringNumberUtil.getUUID();
		}
		map.put("flowid", flowid);
		map.put("flowdraftid", flowdraftid);
		//
		map = this.getDeliverMap(operateType, user,uvflow, map);
		Flow flow = dealFlowData(opFlowType, map, user); // flow
		map.put("flow", flow);
		rows = this.dealFlowBaseData(opFlowBaseType, map, user);// 更新flow_base
		
		// 判断分类 审批签章类 、文本类
		if ( StringNumberUtil.isEmpty(newcompount) || !"1".equals(newcompount) ) {
			dealDocconent(map,user,type);
		}
		
		//把需要返回的值放入map
		returnMap.put("flowdraftid", flowdraftid);
		returnMap.put("state", rows);
					
		return returnMap;
	}
	
	
	private void dealDocconent(Map<String,Object> map,SystemUser user, String type){
		String docconent = StringNumberUtil.getStrFromMap("docconent", map);
		String flowdefid = StringNumberUtil.getStrFromMap("flowdefid", map);
		String flowid = StringNumberUtil.getStrFromMap("flowid", map);
		String flowdraftid = StringNumberUtil.getStrFromMap("flowdraftid", map);
		String tempid = StringNumberUtil.getStrFromMap("tempid", map);
		
		if (flowdefid.contains("other_")) {// 签章审批流转
			this.operationFlowForSignature(flowid, flowdraftid,tempid, docconent, user, type);
		} else if (flowdefid.contains("doc_")) {// 不同审批流转
			this.operationFlowForOrdinary(flowid, flowdraftid,tempid, docconent, user, type);
		} else if (flowdefid.contains("arch_")) {// 电子档案审批流转
			this.operationFlowForArch(flowid,flowdefid,docconent,map, user);
		}
	}
	
	/*
	 * 获取当前流程表信息 
	 * 测试
	 */
	public Map<String,Object> getDeliverMap(String operateType,SystemUser user, 
			UvFlow uvflow, Map<String,Object> datamap) {
		// 变量
		Date now = new Date();
		Short state = -2;// 流程流转状态
		String flowlaunch = StringNumberUtil.getStrFromMap("flowlaunch", datamap);//flowlaunch=1表示流程发起，则表TBFLOW的state=0;
		if(StringNumberUtil.notEmpty(flowlaunch) && "1".equals(flowlaunch)){
			state = 0;
		}
		
		String opinion = "";
		String flowdefid = StringNumberUtil.returnString2(datamap.get("flowdefid"));
		String endsummry = "";
		String startsummry = "";
		String commenttext = "";
		String dnodeid = GkzxCommon.ZERO;// 目的节点
		String lastnodeid = GkzxCommon.ZERO;// 上次节点
		String resid = StringNumberUtil.returnString2(datamap.get("resid"));//资源ID
		String conent = StringNumberUtil.returnString2(datamap.get("conent"));
		String applyid = StringNumberUtil.returnString2(datamap.get("applyid"));
		String applyname = StringNumberUtil.returnString2(datamap.get("applyname"));
		
		String freeDnodeid = StringNumberUtil.returnString2(datamap.get("freeDnodeid"));//自流流转目地节点
		
		//流程配置表状态
		int _state = 0;
		if("end".equalsIgnoreCase(operateType)) _state=1; 
		if("back".equalsIgnoreCase(operateType)) _state=2; 
		if("no".equalsIgnoreCase(operateType)) _state=-1; 
		if("defer".equalsIgnoreCase(operateType)) _state=-1; 
		
		FlowDeliver flowDeliver = null;
		Map<String, Object> map = new HashMap<String, Object>();
		SimpleDateFormat sdf = new SimpleDateFormat(GkzxCommon.DATEPOINTFORMAT);
		if(null != uvflow){
			lastnodeid = uvflow.getNodeid();
			flowdefid = uvflow.getFlowdefid();
		}
		map.put("departid", user.getDepartid());
		map.put("flowdefid", flowdefid);
		map.put("snodeid", lastnodeid);
		
		if ("save".equalsIgnoreCase(operateType)||"yes".equalsIgnoreCase(operateType)) {
			map.put("instate", GkzxCommon.ZERO);
		}else{
			map.put("state", String.valueOf(_state));
		}
		
		//资源不为空时
		if(StringNumberUtil.notEmpty(resid)&& !"save".equalsIgnoreCase(operateType)){	
			flowDeliver = flowDeliverMapper.findByCondition(resid, user.getDepartid());
		}else {
			flowDeliver = flowDeliverMapper.findByParamMap(map);
		}
		
		if (null != flowDeliver) {
			flowdefid = flowDeliver.getFlowdefid();// 获取流程自定义ID
			String deliverstate = String.valueOf(flowDeliver.getState());// 获取节点对应状态
			String remark = flowDeliver.getRemark() == null ? "": flowDeliver.getRemark();
			Integer int1 = flowDeliver.getInt1();
			if ("save".equalsIgnoreCase(operateType)){// 目的节点
				dnodeid = lastnodeid;
			}else{
				if ("yes".equalsIgnoreCase(operateType)) {
					state = -1;
				}else if ("end".equalsIgnoreCase(operateType)) {
					state = 1;
				}else if ("back".equalsIgnoreCase(operateType)) {
					state = 2;
				}else if ("review".equalsIgnoreCase(operateType)) { // 退回复议
					state = 4;
					datamap.put("isreview", GkzxCommon.ONE);
				}else if ("no".equalsIgnoreCase(operateType)) {// 不同意(不予办理)
					state = 3;
				}else if ("defer".equalsIgnoreCase(operateType)) {//暂缓处理
					state = -4;
					datamap.put("defer", GkzxCommon.TWO);//YES为暂缓
				}
				dnodeid = flowDeliver.getDnodeid();// 目的节点
			}
			
			String describe = "";
			if (int1 == 0) {// 申请人为罪犯
				describe = GkzxCommon.DESCRIBE_ZF.replace("@", applyname);
			} else if (int1 == 1) {// 申请人为单位
				datamap = this.updateMapData("applyid", user.getOrganization().getOrgid(), datamap);
				datamap = this.updateMapData("applyname", user.getOrganization().getName(), datamap);
				describe = GkzxCommon.DESCRIBE_OTHER.replace("@", user.getOrganization().getName());
			} else if (int1 == 2) {// 申请人为用户
				describe = GkzxCommon.DESCRIBE_USER.replace("@", applyname);
			} else {
				describe = GkzxCommon.DESCRIBE_OTHER.replace("@", applyname);
			}
			describe += remark + "(" + sdf.format(now) + ")";
			String opinionname = flowDeliver.getText1() == null ? "": flowDeliver.getText1();// 获取审批意见框对应属性

			// 开始摘要
			startsummry = describe;
			// 获取审批意见
			opinion = datamap.get(opinionname) == null ? "": datamap.get(opinionname).toString();// 审批意见

			if (!"".equals(opinion)) {
				commenttext = opinion;
				datamap = this.updateMapData("commenttext", commenttext,datamap);
			}

			// 此处流转结束
			if (GkzxCommon.ONE.equals(deliverstate) || "-1".equals(deliverstate)) {
				// 流转状态 1为完成
				if (GkzxCommon.ONE.equals(deliverstate) && "yes".equalsIgnoreCase(operateType)) {
					state = Short.valueOf(GkzxCommon.ONE);
					endsummry = describe;
				}
				// startsummry = lastflow.getStartsummry();
			}else if(GkzxCommon.TWO.equals(deliverstate) && "back".equalsIgnoreCase(operateType)){
				Flow preFlow = flowMapper.findByFristId(uvflow.getFlowdraftid());//查询第一条记录的流程流水
	            String noticePk = userNoticeService.getNoticePk();
	            TbuserNotice notice = new TbuserNotice();
				notice.setNoticeid(Integer.valueOf(noticePk));
				notice.setContent(startsummry + "被退回！");
				notice.setTitle("案件退回提醒");
				notice.setMessagetype(3);
				notice.setOpid("sysauto");
				notice.setUsername(preFlow.getOpid());
				notice.setStarttime(new Date());
				notice.setEndtime(new Date());
				notice.setOptime(new Date());
				notice.setState(0);
				notice.setText1(uvflow.getFlowdraftid());
				notice.setText2(uvflow.getFlowdefid());
				notice.setText3(applyid);
	            TbuserUserNotice userNotice = new TbuserUserNotice();
	            userNotice.setNoticeid(Integer.valueOf(noticePk));
	            userNotice.setUserid(preFlow.getOpid());
	            userNotice.setOptime(new Date());
	            userNotice.setOpid(user.getUserid());
	            userNoticeService.saveUserMessage(notice,userNotice);
			}else if("1".equals(freeDnodeid)){ //freeDnodeid = 1  表示流程结束
				state = Short.valueOf(GkzxCommon.ONE);
				endsummry = describe;
			}
			// 审批内容
			if ("".equals(conent)) {
				conent = describe;
			}

			// 更新datamap 字段对应的值
			datamap = this.updateMapData("flowdefid", flowdefid, datamap);
			datamap = this.updateMapData("state", String.valueOf(state),datamap);
			datamap = this.updateMapData("startsummry", startsummry,datamap);
			datamap = this.updateMapData("endsummry", endsummry, datamap);
			datamap = this.updateMapData("conent", conent, datamap);
			if(StringNumberUtil.isEmpty(freeDnodeid)){
				datamap = this.updateMapData("dnodeid", dnodeid, datamap);
			}else{
				datamap.put("dnodeid", freeDnodeid);
			}
			
			
			//查询下一级节点的FlowDeliver
			Map<String,Object> paramap = new HashMap<String,Object>();
			paramap.put("flowdefid", flowdefid);
			paramap.put("departid", user.getDepartid());
			paramap.put("snodeid", datamap.get("dnodeid"));
			paramap.put("instate", GkzxCommon.ZERO);
			FlowDeliver nextFlowDeliver = flowDeliverMapper.findByParamMap(paramap);
			if(null != nextFlowDeliver){
				datamap.put("nextFlowDeliver", nextFlowDeliver);
			}
			
		}
		return datamap;
	}
	/**
	 * 更新map中某属性的值
	 */
	public Map<String, Object> updateMapData(String key, String value,Map<String,Object> datamap) {
		datamap.remove(key);
		datamap.put(key, value);
		return datamap;
	}

	/**
	 * 插入/更新flow表数据
	 * 
	 * @param type
	 * @return
	 */
	public Flow dealFlowData(String opFlowType, Map<String,Object> map, SystemUser user) {
		// 初始化对象
		int rows = 0;
		Date now = new Date();
		Flow flow = new Flow();

		// 获取参数
		String flowid = (String) (map.get("flowid") == null ? "" : map.get("flowid"));
		String dnodeid = (String) (map.get("dnodeid") == null ? "" : map.get("dnodeid"));
		String examineid = (String) (map.get("examineid") == null ? "" : map.get("examineid"));
		String flowdraftid = (String) (map.get("flowdraftid") == null ? "": map.get("flowdraftid"));
		String state = (String) (map.get("state") == null ? "0" : map.get("state").toString());
		String suid = user.getUserid();
		String suname = user.getName();
		String commenttext = (String) (map.get("commenttext") == null ? "": map.get("commenttext"));
		String startsummry = (String) (map.get("startsummry") == null ? "": map.get("startsummry"));
		String endsummry = (String) (map.get("endsummry") == null ? "" : map.get("endsummry"));
		String text1 = (String) (map.get("isreview") == null ? "" : map.get("isreview"));// 判断是否复议
		String text2=(String)map.get("opcillinstance")==null?"":(String)map.get("opcillinstance");//保外病残情况
		String text3=(String)map.get("opcillcheckresult")==null?"":(String)map.get("opcillcheckresult");//保外病残鉴定结论
		String defer = StringNumberUtil.returnString2(map.get("defer"));//非空为暂缓
		String backnoreason = StringNumberUtil.returnString2(map.get("backnoreason"));//退回或者拒绝理由
		
		String text4 = "";// 刑期年
		String text5 = "";// 刑期月
		String text6 = "";// 刑期日
		String jxjs_date = (String) (map.get("jxjs_date") == null ? "" : map.get("jxjs_date"));
		String jxjs_2 = (String) (map.get("jxjs_2") == null ? "" : map.get("jxjs_2"));
		String jxjs_3 = (String) (map.get("jxjs_3") == null ? "" : map.get("jxjs_3"));
		String jxjs_4 = (String) (map.get("jxjs_4") == null ? "" : map.get("jxjs_4"));
		if (StringNumberUtil.notEmpty(jxjs_date)) {
			String[] jxjs_arr = jxjs_date.split(",");
			if (jxjs_arr != null && jxjs_arr.length == 3) {
				text4 = jxjs_arr[0];
				text5 = jxjs_arr[1];
				text6 = jxjs_arr[2];
			}
		}else{
			text4 = jxjs_2;
			text5 = jxjs_3;
			text6 = jxjs_4;
		}

		String int1 = (String) (map.get("jxjs_1") == null ? "" : map.get("jxjs_1"));// 减刑假释、保外类型
		String int2 = (String) (map.get("jxjs_5") == null ? "" : map.get("jxjs_5"));// 剥政年限

		String ruleid = (String) (map.get("ruleid") == null ? "" : map.get("ruleid"));
		String pointsval = (String) (map.get("pointsval") == null ? "" : map.get("pointsval"));
		String pljkfcrimids = (String) (map.get("pljkfcrimids") == null ? "" : map.get("pljkfcrimids"));
		String rewardtype = (String) (map.get("rewardtype") == null ? "" : map.get("rewardtype"));
		String rewardcrimid = (String) (map.get("rewardcrimid") == null ? "" : map.get("rewardcrimid"));
		String pundate = (String) (map.get("pundate") == null ? "" : map.get("pundate"));
		String puntime = (String) (map.get("puntime") == null ? "" : map.get("puntime"));
		
		// 保存设置
		flow.setFlowid(flowid);
		flow.setNodeid(dnodeid);
		flow.setExamineid(examineid);
		flow.setFlowdraftid(flowdraftid);
		flow.setOrgid(user.getOrgid());
		flow.setState(Short.decode(state));
		flow.setOpid(suid);
		flow.setOpname(suname);
		flow.setCommenttext(commenttext);
		flow.setStartsummry(startsummry);
		flow.setEndsummry(endsummry);
		flow.setOptime(now);
        flow.setText7(backnoreason);
		//暂缓处理
		if(!StringNumberUtil.isNullOrEmpty(defer)){
			text2="defer";
		}
		if (!"".equals(int1)) {
			flow.setInt1(Integer.valueOf(int1));
		}

		if (!"".equals(int2)) {
			flow.setInt2(Integer.valueOf(int2));
		}

		if (!"".equals(text1)) {
			flow.setText1(text1);
		}
		if (!"".equals(text2)) {
			flow.setText2(text2);
		}
		if (!"".equals(text3)) {
			flow.setText3(text3);
		}

		if (!"".equals(text4)) {
			flow.setText4(text4);
		}

		if (!"".equals(text5)) {
			flow.setText5(text5);
		}

		if (!"".equals(text6)) {
			flow.setText6(text6);
		}
		
		if(!"".equals(ruleid)) {
			flow.setText2(ruleid);
		}
		
		if(!"".equals(pointsval)) {
			flow.setText3(pointsval);
		}

		if(!"".equals(pljkfcrimids)) {
			flow.setText1(pljkfcrimids);
		}
		
		if(!"".equals(rewardcrimid)) {
			flow.setText1(rewardcrimid);
		}
		
		if(!"".equals(rewardtype)) {
			flow.setText2(rewardtype);
		}

		if(!"".equals(pundate)) {
			flow.setText3(pundate);
		}

		if(!"".equals(puntime)) {
			flow.setInt1(Integer.valueOf(puntime));
		}
		String ip = map.get("_ip")==null?"":map.get("_ip").toString();
		String operateid = map.get("operateid")==null?"":map.get("operateid").toString();
		String operateType = map.get("operateType")==null?"":map.get("operateType").toString();
		
		flow.setOperateip(ip);
		flow.setOperateid(operateid);
		flow.setOperatetype(operateType);
		
		// opFlowType 0:插入新增，1：更新
		if (GkzxCommon.ZERO.equalsIgnoreCase(opFlowType)) {
			Flow preFlow = flowMapper.findById(flowdraftid);//查询前一条记录的流程流水
			Integer orderby = preFlow  == null ? 0 : preFlow.getOrderby() +1;
			String prenodeid = preFlow  == null ? null : preFlow.getNodeid();
			flow.setOrderby(orderby);
			flow.setPrenodeid(prenodeid);
			rows = flowMapper.insert(flow);
		}else if (GkzxCommon.ONE.equalsIgnoreCase(opFlowType)) {
			rows = flowMapper.updateByFlowdraftid(flow);
		}
		//
		if (1 != rows) {
			throw new RuntimeException("流程操作失败！");
		}
		return flow;
	}

	/**
	 * 插入/更新flow_base表数据
	 * 
	 * @param type
	 * @return
	 */
	public Integer dealFlowBaseData(String opFlowBaseType, Map<String,Object> map, SystemUser user) {
		// 初始化对象
		int rows = 0;
		Date now = new Date();
		FlowBase flowBase = new FlowBase();

		// 获取参数
		String sn = StringNumberUtil.isEmpty(map.get("sn")) ? "1": map.get("sn").toString();// 各个顶级部门处理流程序号
		
		String userid = user.getUserid();
		String username = user.getName();
		String lastopname = (String) (map.get("lastopname") == null ? "" : map.get("lastopname"));
		String flowid = (String) (map.get("flowid") == null ? "" : map.get("flowid"));// 流程ID
		String flowdefid = (String) (map.get("flowdefid") == null ? "" : map.get("flowdefid"));// 流程自定义ID
		String flowdraftid = (String) (map.get("flowdraftid") == null ? "": map.get("flowdraftid"));// 流程草稿ID
		String applyid = (String) (map.get("applyid") == null ? "" : map.get("applyid"));// 申请人ID
		String applyname = (String) (map.get("applyname") == null ? "" : map.get("applyname"));// 申请人姓名
		Date applydatetime = now;
		Date startdatetime = (Date) (map.get("startdatetime") == null ? null: map.get("startdatetime"));// 开始时间
		Date enddatetime = (Date) (map.get("enddatetime") == null ? null : map.get("enddatetime"));// 结束时间
		String conent = (String) (map.get("conent") == null ? "" : map.get("conent"));// 内容
		String operateType = StringNumberUtil.getStrFromMap("operateType", map);
		
		// 表单获取的减刑假释 、保外意见
		String text1 = (String) (map.get("fajinjiaonaqingkuang") == null ? "": map.get("fajinjiaonaqingkuang"));// 履行罚金
		String text2 = (String) (map.get("paymentzk") == null ? "" : map.get("paymentzk"));// 退回赃款
		String text3 = (String) (map.get("paymentpc") == null ? "" : map.get("paymentpc"));// 履行赔偿
		String text4 = (String) (map.get("paymentcc") == null ? "" : map.get("paymentcc"));// 履行没收财产
		String text5 = (String) (map.get("gaizaobiaoxian") == null ? "" : map.get("gaizaobiaoxian"));// 改造表现
		
		//案件号 在第一次新增的时候已经保存进去了，以后对表单操作的时候 宁夏不对案件号进行更新，否者
		String text6 = (String) (map.get("_casenumber") == null ? "" : map.get("_casenumber"));// 案件年+案件号
		String text7 = (String) (map.get("casetype") == null ? "" : map.get("casetype"));// 案件类型
		String text9 = (String) (map.get("worktype") == null ? "" : map.get("worktype"));// 工种
		String text10 = (String) (map.get("liandate") == null ? "" : map.get("liandate"));// 立案日期
		
		String jailcasechg = map.get("jailcasechg") == null ? "" : map.get("jailcasechg").toString().trim();// 监狱案件号的第字描述
		String provincecasechg = map.get("provincecasechg") == null ? "" : map.get("provincecasechg").toString().trim();// 省局案件号的第字描述
		
		String text11 = (String) (map.get("acceptdate") == null ? "" : map.get("acceptdate"));// 收案日期
		String text12 = (String) (map.get("fyfenandate") == null ? "" : map.get("fyfenandate"));// 分案日期
		String text13 = (String) (map.get("endcasedate") == null ? "" : map.get("endcasedate"));// 结案日期
		String text14 = (String) (map.get("xuanpandate") == null ? "" : map.get("xuanpandate"));// 拟宣判日期
		String text15 = (String) (map.get("discussiondate") == null ? "" : map.get("discussiondate"));// 合议日期(拟合议日期)
		String courttype = (String) (map.get("courttype") == null ? "" : map.get("courttype")); // 法院案件类刑// xzz：刑执字，xzbz：刑执备字，xzjz刑执监字，xzcz刑执查字
		String text17 = (String) (map.get("filedate") == null ? "" : map.get("filedate"));// 归档日期
		String text18 = (String) (map.get("bingcanqingkuang") == null ? "": map.get("bingcanqingkuang"));// 病残情况
		String text19 = (String) (map.get("jiandingyijian") == null ? "" : map.get("jiandingyijian"));// 病残诊断
		String text20 = "";
		if (flowdefid.equals("other_zfjyjxjssp")) {
			text20 = (String) (map.get("text18") == null ? "" : map.get("text18"));// 减刑假释审核表 监狱意见处时间
		} else if (flowdefid.equals("other_sjjxjssp")){
			text20 = (String) (map.get("text36") == null ? "" : map.get("text36"));// 减刑假释审核表 省局意见处时间
		}
		String text24 = (String) (map.get("shortname") == null ? "" : map.get("shortname"));// 单位简称(办案用)
		String int6 = (String) (map.get("batch") == null ? "" : map.get("batch"));// 减刑假释批次
		String text8 = StringNumberUtil.returnString2(map.get("mapkey8"));//mapkey8
		String text21 = StringNumberUtil.returnString2(map.get("mapkey21"));
		String text22 = StringNumberUtil.returnString2(map.get("mapkey22"));
		String text23 = StringNumberUtil.returnString2(map.get("mapkey23"));
		String text25 = StringNumberUtil.returnString2(map.get("mapkey25"));
		String text28 = StringNumberUtil.returnString2(map.get("mapkey28"));
		String text29 = StringNumberUtil.returnString2(map.get("mapkey29"));
		String text30 = StringNumberUtil.returnString2(map.get("mapkey30"));
		String text31 = StringNumberUtil.returnString2(map.get("mapkey31"));
		String defer = StringNumberUtil.returnString2(map.get("defer"));//非空为暂缓
		
		//mapkey设值
		if(!StringNumberUtil.isNullOrEmpty(map.get("mapkey1"))) text1= StringNumberUtil.returnString2(map.get("mapkey1"));
		if(!StringNumberUtil.isNullOrEmpty(map.get("mapkey2"))) text2= StringNumberUtil.returnString2(map.get("mapkey2"));
		if(!StringNumberUtil.isNullOrEmpty(map.get("mapkey3"))) text3= StringNumberUtil.returnString2(map.get("mapkey3"));
		if(!StringNumberUtil.isNullOrEmpty(map.get("mapkey4"))) text4= StringNumberUtil.returnString2(map.get("mapkey4"));
		if(!StringNumberUtil.isNullOrEmpty(map.get("mapkey5"))) text5= StringNumberUtil.returnString2(map.get("mapkey5"));
		if(!StringNumberUtil.isNullOrEmpty(map.get("mapkey6"))) text6= StringNumberUtil.returnString2(map.get("mapkey6"));
		if(!StringNumberUtil.isNullOrEmpty(map.get("mapkey7"))) text7= StringNumberUtil.returnString2(map.get("mapkey7"));
		if(!StringNumberUtil.isNullOrEmpty(map.get("mapkey9"))) text9= StringNumberUtil.returnString2(map.get("mapkey9"));
		if(!StringNumberUtil.isNullOrEmpty(map.get("mapkey10"))) text10= StringNumberUtil.returnString2(map.get("mapkey10"));
		if(!StringNumberUtil.isNullOrEmpty(map.get("mapkey11"))) text11= StringNumberUtil.returnString2(map.get("mapkey11"));
		if(!StringNumberUtil.isNullOrEmpty(map.get("mapkey12"))) text12= StringNumberUtil.returnString2(map.get("mapkey12"));
		if(!StringNumberUtil.isNullOrEmpty(map.get("mapkey13"))) text13= StringNumberUtil.returnString2(map.get("mapkey13"));
		if(!StringNumberUtil.isNullOrEmpty(map.get("mapkey14"))) text14= StringNumberUtil.returnString2(map.get("mapkey14"));
		if(!StringNumberUtil.isNullOrEmpty(map.get("mapkey15"))) text15= StringNumberUtil.returnString2(map.get("mapkey15"));
		if(!StringNumberUtil.isNullOrEmpty(map.get("mapkey16"))) courttype = StringNumberUtil.returnString2(map.get("mapkey16"));
		if(!StringNumberUtil.isNullOrEmpty(map.get("mapkey17"))) text17= StringNumberUtil.returnString2(map.get("mapkey17"));
		if(!StringNumberUtil.isNullOrEmpty(map.get("mapkey18"))) text18= StringNumberUtil.returnString2(map.get("mapkey18"));
		if(!StringNumberUtil.isNullOrEmpty(map.get("mapkey19"))) text19= StringNumberUtil.returnString2(map.get("mapkey19"));
		if(!StringNumberUtil.isNullOrEmpty(map.get("mapkey20"))) text20= StringNumberUtil.returnString2(map.get("mapkey20"));
		if(!StringNumberUtil.isNullOrEmpty(map.get("mapkey24"))) text24= StringNumberUtil.returnString2(map.get("mapkey24"));
		if(!StringNumberUtil.isNullOrEmpty(map.get("mapkey28"))) text28= StringNumberUtil.returnString2(map.get("mapkey28"));
		if(!StringNumberUtil.isNullOrEmpty(map.get("mapkey29"))) text29= StringNumberUtil.returnString2(map.get("mapkey29"));
		if(!StringNumberUtil.isNullOrEmpty(map.get("mapkey30"))) text30= StringNumberUtil.returnString2(map.get("mapkey30"));
		if(!StringNumberUtil.isNullOrEmpty(map.get("mapkey31"))) text31= StringNumberUtil.returnString2(map.get("mapkey31"));
		
		//保证人信息
		String baozhengrenname = (String)map.get("baozhengrenname")==null?"":(String)map.get("baozhengrenname");
		String baozhengrenzhuzhi = (String)map.get("baozhengrenzhuzhi")==null?"":(String)map.get("baozhengrenzhuzhi");
		String baozhengrendanwei = (String)map.get("baozhengrendanwei")==null?"":(String)map.get("baozhengrendanwei");
		String baozhengrenguanxi = (String)map.get("baozhengrenguanxi")==null?"":(String)map.get("baozhengrenguanxi");
		if(StringNumberUtil.isNullOrEmpty(text21)){text21=baozhengrenname;}
		if(StringNumberUtil.isNullOrEmpty(text5)){text5=baozhengrenzhuzhi;}
		if(StringNumberUtil.isNullOrEmpty(text18)){text18=baozhengrendanwei;}
		if(StringNumberUtil.isNullOrEmpty(text19)){text19=baozhengrenguanxi;}
		
		Properties jyconfig = new GetProperty().bornProp(GkzxCommon.DATABASETYPE, null);
		String provincecode = jyconfig.getProperty("provincecode");
		String provinceCode = jyconfig.getProperty(GkzxCommon.PROVINCECODE) == null ? "": jyconfig.getProperty(GkzxCommon.PROVINCECODE);

		// 保外日期
		String bystartdate = (String) (map.get("bystartdate") == null ? "": map.get("bystartdate"));// 保外开始日期
		String byenddate = (String) (map.get("byenddate") == null ? "" : map.get("byenddate"));// 保外结束日期
		if (!"".equals(bystartdate))
			text10 = bystartdate;
		if (!"".equals(byenddate))
			text13 = byenddate;

		// 从严从宽
		String congkuan = (String) (map.get("jxjs_7") == null ? "" : map.get("jxjs_7"));// 从宽
		String congyan = (String) (map.get("jxjs_9") == null ? "" : map.get("jxjs_9"));// 从严
		if (!"".equals(congkuan))
			text18 = congkuan;
		if (!"".equals(congyan))
			text19 = congyan;

		String int2 = "";// 刑期年
		String int3 = "";// 刑期月
		String int4 = "";// 刑期日

		// 计算后的刑期
		String jxjs_date = (String) (map.get("jxjs_date") == null ? "" : map.get("jxjs_date"));
		String jxjs_2 = (String) (map.get("jxjs_2") == null ? "" : map.get("jxjs_2"));
		String jxjs_3 = (String) (map.get("jxjs_3") == null ? "" : map.get("jxjs_3"));
		String jxjs_4 = (String) (map.get("jxjs_4") == null ? "" : map.get("jxjs_4"));
		if (StringNumberUtil.notEmpty(jxjs_date)) {
			String[] jxjs_arr = jxjs_date.split(",");
			if (jxjs_arr != null && jxjs_arr.length == 3) {
				int2 = jxjs_arr[0];// 刑期年
				int3 = jxjs_arr[1];// 刑期月
				int4 = jxjs_arr[2];// 刑期日
			}
		}else{
			int2 = jxjs_2;
			int3 = jxjs_3;
			int4 = jxjs_4;
		}

		String int1 = (String) (map.get("jxjs_1") == null ? "" : map.get("jxjs_1"));// 减刑假释、保外类型
		String int5 = (String) (StringNumberUtil.isEmpty(map.get("jxjs_5")) ? "0" : map.get("jxjs_5"));// 剥政年限
		String int7 = (String) (map.get("jxjs_6") == null ? "" : map.get("jxjs_6"));// 减免罚金
		
		//暂缓处理
		if(!StringNumberUtil.isNullOrEmpty(defer)){
			text22="defer";
			text23=defer;
		}

		// 保存设值
		flowBase.setSn(Integer.valueOf(sn));
		flowBase.setFlowid(flowid);
		flowBase.setFlowdefid(flowdefid);
		flowBase.setFlowdraftid(flowdraftid);
		flowBase.setEnddatetime(enddatetime);
		flowBase.setConent(conent);
		if("save".equals(operateType)){
			String flowlaunch = StringNumberUtil.getStrFromMap("flowlaunch", map);
			if(StringNumberUtil.notEmpty(flowlaunch) && "1".equals(flowlaunch)){//流程发起，不加锁
				flowBase.setIslocked(Short.valueOf(GkzxCommon.ZERO));
			}else{
				flowBase.setIslocked(Short.valueOf(GkzxCommon.ONE));
			}
		}else{
			flowBase.setIslocked(Short.valueOf(GkzxCommon.ZERO));
		}
		flowBase.setOpid(userid);
		flowBase.setOpname(username);
		flowBase.setLastOpname(lastopname);
		flowBase.setOptime(now);
		flowBase.setText1(text1);
		flowBase.setText2(text2);
		flowBase.setText3(text3);
		flowBase.setText4(text4);
		flowBase.setText5(text5);
		flowBase.setText6(text6);
		flowBase.setText8(text8);
		flowBase.setText9(text9);

		flowBase.setText10(text10);
		flowBase.setText11(text11);
		flowBase.setText12(text12);
		flowBase.setText13(text13);
		flowBase.setText14(text14);
		flowBase.setText15(text15);
		flowBase.setText16(courttype);
		flowBase.setText17(text17);
		flowBase.setText18(text18);
		flowBase.setText19(text19);
		flowBase.setText20(text20);
		flowBase.setText21(text21);
		flowBase.setText22(text22);
		flowBase.setText23(text23);
		flowBase.setText28(text28);
		flowBase.setText29(text29);
		flowBase.setText30(text30);
//		flowBase.setText31(text31);
		
		if (!StringNumberUtil.isNullOrEmpty(text24)) {
			flowBase.setText24(text24);
		}
		if (!StringNumberUtil.isNullOrEmpty(text25)) {
			flowBase.setText25(text25);
		}
		if (!"".equals(int1)) {
			flowBase.setInt1(Integer.valueOf(int1));
		}
		if (!"".equals(int2)) {
			flowBase.setInt2(Integer.valueOf(int2));
		}
		if (!"".equals(int3)) {
			flowBase.setInt3(Integer.valueOf(int3));
		}
		if (!"".equals(int4)) {
			flowBase.setInt4(Integer.valueOf(int4));
		}
		if (!"".equals(int5)) {
			flowBase.setInt5(Integer.valueOf(int5));
		}
		// 此处是批次
		if (!"".equals(int6)) {
			flowBase.setInt6(Integer.valueOf(int6));
		}
		// 此处是减免罚金
		if (!"".equals(int7)) {
			flowBase.setInt7(Double.valueOf(int7));
		}
		
		
		if (!StringNumberUtil.isNullOrEmpty(jailcasechg)) {
			flowBase.setJailcasechg(jailcasechg);
		}
		if (!StringNumberUtil.isNullOrEmpty(provincecasechg)) {
			flowBase.setProvincecasechg(provincecasechg);
		}
		
		
		Flow flow = map.get("flow")==null?null:(Flow)map.get("flow");
		FlowDeliver nextFlowDeliver = map.get("nextFlowDeliver")==null?null:(FlowDeliver)map.get("nextFlowDeliver");
		
		if(null != flow){
			flowBase.setFlowsn(flow.getFlowsn());
			flowBase.setCurrnodeid(flow.getNodeid());
			flowBase.setCommenttext(flow.getCommenttext());
			flowBase.setOperatetype(flow.getOperatetype());
			flowBase.setState(flow.getState());
			flowBase.setOperateip(flow.getOperateip());
			flowBase.setOperateid(flow.getOperateid());
		}
		
		if(null != nextFlowDeliver){
			flowBase.setResid(nextFlowDeliver.getResid());
			flowBase.setExplain(nextFlowDeliver.getExplain());
			flowBase.setCasedesc(nextFlowDeliver.getRemark());
			flowBase.setOpinionnode(nextFlowDeliver.getText1());
			flowBase.setEditnodes(nextFlowDeliver.getText2());
			flowBase.setFilterorg(nextFlowDeliver.getFilterorg());
			flowBase.setBranchtype(nextFlowDeliver.getBranchtype());
			flowBase.setBranchvalue(nextFlowDeliver.getBranchvalue());
			flowBase.setFlowstatus(nextFlowDeliver.getText3());
		}else{
			//如果流程没有下一级的话，相关字段要置空
			flowBase.setResid("");
			flowBase.setExplain("");
			flowBase.setCasedesc("");
			flowBase.setOpinionnode("");
			flowBase.setEditnodes("");
			flowBase.setFilterorg("");
			flowBase.setBranchtype("");
			flowBase.setBranchvalue("");
			flowBase.setFlowstatus("");
		}
		
		
		// opFlowBaseType 0:插入新增 1：更新
		if (GkzxCommon.ZERO.equalsIgnoreCase(opFlowBaseType)) {
			flowBase.setOrgid(user.getOrgid());
			flowBase.setDepartid(user.getDepartid());
			//
			flowBase.setApplyid(applyid);
			flowBase.setApplyname(applyname);
			flowBase.setApplydatetime(applydatetime);
			flowBase.setStartdatetime(startdatetime);
			flowBase.setText7(text7);
			rows = flowBaseMapper.insert(flowBase);

			if (1 != rows) {
				throw new RuntimeException("流程操作失败！");
			}
		} else if (GkzxCommon.ONE.equalsIgnoreCase(opFlowBaseType)) {
			flowBase.setApplyname(applyname);
//			if (!"".equals(flowdefid) && GkzxCommon.FLOW_DEFID_JY.equals(flowdefid) &&  !"4400".equals(provincecode.trim())) {
//				text7 = GkzxCommon.CASE_TYPE_JXJS;
//				if (GkzxCommon.ONE.equals(int1)) {// 假释
//					text7 = GkzxCommon.CASE_TYPE_JS;
//				}
//				flowBase.setText7(text7);
//			}
			//判断 如果是 宁夏，那么 后面再更新的时候，text6(casenum)的值不能进行修改
			//因为 只有这个地方才 确定是 更新还是新增，故需要在这个里面进行判断
			//更新的时候，广东不更新text6. 只允许立案（insert）和案件号整理可以更改text6。
			if (provinceCode.contains(GkzxCommon.GUANGDONG_PROVINCE)) {
				flowBase.setText6("");
			}
			
			rows = flowBaseMapper.updateSensitiveByFlowdraftid(flowBase);
			if (1 != rows) {
				throw new RuntimeException("流程操作失败！");
			}
		}

		return rows;
	}

	/**
	 * 审批盖章类 流程
	 * 
	 * @return
	 */
	public void operationFlowForSignature(String flowid,String flowdraftid, String tempid,
			 String docconent,SystemUser user, String type) {
		// 用户对象
		FlowOtherFlow flowOtherFlow = new FlowOtherFlow();
		FlowBaseOther flowBaseOther = new FlowBaseOther();
		// 取得参数
		int rows = 0;
		String userid = user.getUserid();
		
		// 更新other关系表 设置 值
		flowOtherFlow.setOptime(new Date());
		flowOtherFlow.setTempid(tempid);
		flowOtherFlow.setFlowid(flowid);
		flowOtherFlow.setFlowdraftid(flowdraftid);

		// 发起流程
		if (GkzxCommon.NEW.equals(type)) {// 新增
			String otherid = flowBaseOtherMapper.getOtherId(user.getDepartid());
			flowBaseOther.setOtherid(otherid);
			flowBaseOther.setDocconent(docconent);
			flowBaseOther.setSn(Integer.valueOf(GkzxCommon.ONE));
			flowBaseOther.setOpid(userid);
			flowBaseOther.setOptime(new Date());
			rows = flowBaseOtherMapper.insert(flowBaseOther);
			// 保存流程与和流程相关的其他文档关系信息表
			flowOtherFlow.setOtherid(otherid);
			flowOtherFlow.setOpid(userid);

			rows = flowOtherFlowMapper.insert(flowOtherFlow);

		} else {// 更新
			char[] c = docconent.toCharArray();
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("docconent", new String(c));
			map.put("flowdraftid", flowdraftid);
			if (StringNumberUtil.notEmpty(flowid)) {
				map.put("flowid", flowid);
			}
			map.put("tempid", tempid);
			map.put("opid", userid);
			String otherid = flowBaseOtherMapper.getOtheridByFlowdraftid(map);
			map.put("otherid", otherid);
			rows = flowBaseOtherMapper.updateByOtherid(map);
			rows = flowOtherFlowMapper.updateByCondition2(flowOtherFlow);
		}
		
		if (1 != rows) {
			throw new RuntimeException();
		}
	}

	public Map operationFlowBaseOther(String flowdraftid, String tempid,
			String docconent, SystemUser user, String type, String otheridStr, String modelid) {
		Map result = new HashMap();
		// 用户对象
		FlowOtherFlow flowOtherFlow = new FlowOtherFlow();
		FlowBaseOther flowBaseOther = new FlowBaseOther();

		// 取得参数
		int row = 0;
		Date now = new Date();
		String suid = user.getUserid();

		// 更新other关系表 设置 值
		flowOtherFlow.setOptime(now);
		flowOtherFlow.setTempid(tempid);
		flowOtherFlow.setFlowdraftid(flowdraftid);
		
		// 发起流程
		if (GkzxCommon.NEW.equals(type)) {// 新增
			String otherid = flowBaseOtherMapper.getOtherId(user.getDepartid());
			flowBaseOther.setOtherid(otherid);
			flowBaseOther.setDocconent(docconent);
			flowBaseOther.setSn(Integer.valueOf(GkzxCommon.ONE));
			flowBaseOther.setOpid(suid);
			flowBaseOther.setOptime(now);
			flowBaseOther.setText6(modelid);
			row = flowBaseOtherMapper.insert(flowBaseOther);
			if (row <= 0) {
				throw new RuntimeException();
			}
			// 保存流程与和流程相关的其他文档关系信息表
			flowOtherFlow.setOtherid(otherid);
			flowOtherFlow.setOpid(suid);

			row = flowOtherFlowMapper.insert(flowOtherFlow);
			if (row <= 0) {
				throw new RuntimeException();
			}
			result.put("otherid", String.valueOf(otherid));
		} else if (GkzxCommon.EDIT.equals(type)) {// 更新
			char[] c = docconent.toCharArray();
			HashMap map = new HashMap();
			map.put("docconent", new String(c));
			map.put("flowdraftid", flowdraftid);
			map.put("tempid", tempid);
			map.put("opid", suid);
			map.put("modelid", modelid);
			// rows = flowBaseOtherService.updateByCondition(map);
			if (StringNumberUtil.notEmpty(otheridStr)) {
				map.put("otherid", otheridStr);
				flowOtherFlow.setOtherid(otheridStr);
				result.put("otherid", otheridStr);
			} else {
				String otherid = flowBaseOtherMapper
						.getLastOtheridByFlowdraftid(map);
				map.put("otherid", otherid);
				flowOtherFlow.setOtherid(otherid);
				result.put("otherid", otherid);
			}
			row = flowBaseOtherMapper.updateByOtherid(map);
			if (row <= 0) {
				throw new RuntimeException();
			}

			// 更新other关系表
			row = flowOtherFlowMapper.updateByCondition2(flowOtherFlow);
			if (row <= 0) {
				throw new RuntimeException();
			}
		}
		result.put("result", "success");
		return result;
	}

	public void saveFlowBaseOther(SystemUser user, Map datamap, String type)
			throws Exception {
		FlowOtherFlow flowOtherFlow = new FlowOtherFlow();
		FlowBaseOther flowBaseOther = new FlowBaseOther();
		String tempid = datamap.get("tempid") == null ? "" : datamap.get("tempid").toString();
		String flowid = datamap.get("flowid") == null ? "" : datamap.get("flowid").toString();
		String flowdraftid = datamap.get("flowdraftid") == null ? "" : datamap.get("flowdraftid").toString();
		String docconent = datamap.get("docconent") == null ? "" : datamap.get("docconent").toString();

		// 取得参数
		int row = 0;
		Date now = new Date();
		String suid = user.getUserid();

		// 更新other关系表 设置 值
		flowOtherFlow.setOptime(now);
		flowOtherFlow.setTempid(tempid);
		flowOtherFlow.setFlowid(flowid);
		flowOtherFlow.setFlowdraftid(flowdraftid);

		// 发起流程
		if (GkzxCommon.NEW.equals(type)) {// 新增
			String otherid = flowBaseOtherMapper.getOtherId(user.getDepartid());
			flowBaseOther.setOtherid(otherid);
			flowBaseOther.setDocconent(docconent);
			flowBaseOther.setSn(Integer.valueOf(GkzxCommon.ONE));
			flowBaseOther.setOpid(suid);
			flowBaseOther.setOptime(now);
			row = flowBaseOtherMapper.insert(flowBaseOther);
			if (row != 1) {
				throw new RuntimeException();
			}
			// 保存流程与和流程相关的其他文档关系信息表
			flowOtherFlow.setOtherid(otherid);
			flowOtherFlow.setOpid(suid);

			row = flowOtherFlowMapper.insert(flowOtherFlow);
			if (row != 1) {
				throw new RuntimeException();
			}

		} else {// 更新
			char[] c = docconent.toCharArray();
			HashMap map = new HashMap();
			map.put("docconent", new String(c));
			map.put("flowdraftid", flowdraftid);
			if (StringNumberUtil.notEmpty(flowid)) {
				map.put("flowid", flowid);
			}
			map.put("tempid", tempid);
			map.put("opid", suid);
			// rows = flowBaseOtherService.updateByCondition(map);
			String otherid = flowBaseOtherMapper.getLastOtheridByFlowdraftid(map);
			map.put("otherid", otherid);
			flowOtherFlow.setOtherid(otherid);
			row = flowBaseOtherMapper.updateByOtherid(map);
			if (row != 1) {
				throw new RuntimeException();
			}
			// 更新other关系表
			row = flowOtherFlowMapper.updateByOtherid(flowOtherFlow);
			if (row != 1) {
				throw new RuntimeException();
			}
		}

	}

	/**
	 * 一般审批类 流程
	 * 
	 * @return
	 */
	public void operationFlowForOrdinary(String flowid,String flowdraftid, String tempid,
			String docconent,SystemUser user, String type) {
		//
		FlowBaseDoc flowBaseDoc = new FlowBaseDoc();
		// 取得参数
		int rows = 0;
		String userid = user.getUserid();
		String departid = user.getDepartid();
		flowBaseDoc.setDepartid(departid);

		// 设置值
		flowBaseDoc.setOpid(userid);
		flowBaseDoc.setOptime(new Date());
		flowBaseDoc.setFlowid(flowid);
		flowBaseDoc.setDocconent(docconent);
		flowBaseDoc.setFlowdraftid(flowdraftid);

		if (GkzxCommon.NEW.equals(type)) {//新增
			// 保存和流程相关的文档表
			flowBaseDoc.setOthercodeid(tempid);
			rows = flowBaseDocMapper.insert(flowBaseDoc);
		} else {// 更新
			rows = flowBaseDocMapper.updateByCondition(flowBaseDoc);
		}

		if (1 != rows) {
			throw new RuntimeException();
		}
	}

	/**
	 * 电子档案 流程
	 * 
	 * @return
	 */
	public void operationFlowForArch(String flowid,String flowdefid, String docconent,Map<String,Object> map,SystemUser user){
		// 取得参数
		int rows = 0;
		String dnodeid = map.get("dnodeid") == null ? "" : map.get("dnodeid").toString().trim();
		String applyid = map.get("applyid") == null ? "": map.get("applyid").toString().trim();// 申请人ID
		// 档案查看表
		String archiveid = map.get("archiveid") == null ? "": map.get("archiveid").toString().trim();// 归档ID
		String starttime = map.get("starttime") == null ? "": map.get("starttime").toString().trim();// 申请开始时间
		String endtime = map.get("endtime") == null ? "": map.get("endtime").toString().trim();// 申请结束时间

		// 用户对象
		FlowArchivesView flowArchivesView = new FlowArchivesView();
		SimpleDateFormat sdf = new SimpleDateFormat(GkzxCommon.DATETIMEFORMAT);// 格式

		if ("arch_zfdajdsp".equals(flowdefid)) {
			if ("N0002".equals(dnodeid)) {
				//保存罪犯电子档案信息 TBFLOW_ARCHIVES
				rows = saveFlowArchives(docconent,flowid,null, applyid,user);
				if (1 != rows) {
					throw new RuntimeException();
				}
			}
		} else if ("arch_zfdajysp".equals(flowdefid)) {
			if ("N0002".equals(dnodeid)) {
				// 保存档案查看表 TBFLOW_ARCHIVES_VIEW
				flowArchivesView.setFlowid(flowid);
				flowArchivesView.setApplyid(user.getUserid());
				flowArchivesView.setPersonid(applyid);
				flowArchivesView.setArchiveid(archiveid);
				try {
					flowArchivesView.setStarttime(sdf.parse(starttime));
					flowArchivesView.setEndtime(sdf.parse(endtime));
				} catch (ParseException e) {
					e.printStackTrace();
				}
				rows = flowArchivesViewMapper.insert(flowArchivesView);
				if (1 != rows){
					throw new RuntimeException();
				}
			} else {
				Map<String, Object> viewmap = new HashMap<String, Object>();
				viewmap.put("flowid", flowid);
				viewmap.put("applyid", user.getUserid());
				viewmap.put("starttime", "to_date('" + starttime+ "','yyyy-mm-dd hh24:mi:ss')");
				viewmap.put("endtime", "to_date('" + endtime + "','yyyy-mm-dd hh24:mi:ss')");
				if ("-1".equals(dnodeid)) {// dnodeid =-1 退回
					rows = flowArchivesViewMapper.delete(viewmap);
				} else {
					// 更新档案查看表 TBFLOW_ARCHIVES_VIEW
					rows = flowArchivesViewMapper.update(viewmap);
				}
				
				if (1 != rows) {
					throw new RuntimeException();
				}
			}
		}
		
	}
	
	/**描述：保存电子档案至目录
	 * @author YangZR 2015-03-01
	 * @param docconent
	 * @param flowid
	 * @param user
	 * @return
	 */
	@Override
	public int saveFlowArchives(String docconent,String flowid,String flowdraftid, String applyid,SystemUser user){
		int row = 0;
		try{
			String archiveid = "";
			String relativePath = "";
			//保存罪犯电子档案信息 TBFLOW_ARCHIVES
			FlowArchives flowArchives = flowArchivesMapper.findByFlowid(flowid);
			if(null!=flowArchives&&StringNumberUtil.notEmpty(flowArchives.getArchiveid())){
				archiveid = flowArchives.getArchiveid();
				flowArchives.setDepartid(user.getDepartid());
				flowArchives.setOpid(user.getUserid());
				flowArchives.setOptime(new Date());
				
				//保存到文件时int1设为1
				flowArchives.setInt1(1);
				//相对路径
				relativePath = GkzxCommon.SINGLESEPALINE + GkzxCommon.CRIMINALARCHIVES_PATH + File.separator + user.getDepartid() 
												+ File.separator + applyid + File.separator + archiveid + ".txt";
				//保存相对路径
				flowArchives.setDocconent(relativePath);
				
				flowArchives.setPersonid(applyid);
				row = flowArchivesMapper.update(flowArchives);
				
			}else{
				flowArchives = new FlowArchives();
				flowArchives.setFlowid(flowid);
				flowArchives.setDepartid(user.getDepartid());
				flowArchives.setOpid(user.getUserid());
				flowArchives.setOptime(new Date());
//				flowArchives.setSn("0");
				flowArchives.setSn(String.valueOf(tbxfPunishmentRangMapper.getPunid()));
				if(StringNumberUtil.notEmpty(flowdraftid)){
					flowArchives.setText1(flowdraftid);
				}
				archiveid = flowArchivesMapper.getArchiveid(user.getDepartid());
				flowArchives.setArchiveid(archiveid);
				//保存到文件时int1设为1
				flowArchives.setInt1(1);
				
				//相对路径
				relativePath = GkzxCommon.SINGLESEPALINE + GkzxCommon.CRIMINALARCHIVES_PATH + File.separator + user.getDepartid() 
												+ File.separator + applyid + File.separator + archiveid + ".txt";
				//保存相对路径
				flowArchives.setDocconent(relativePath);
				
				flowArchives.setPersonid(applyid);
				
				row = flowArchivesMapper.insert(flowArchives);
			}
			
			if(1 != row){
				throw new RuntimeException();
			}
			
			//绝对路径
			Properties jyconfig = new GetProperty().bornProp(GkzxCommon.DATABASETYPE, null);
			String strPath = jyconfig.getProperty(GkzxCommon.CRIMINALARCHIVES_ABSOLUTEPATH) == null ? "": jyconfig.getProperty(GkzxCommon.CRIMINALARCHIVES_ABSOLUTEPATH);
			String targetPath = GetAbsolutePath.getAbsolutePathAppend(strPath, relativePath);
			FileUtil.saveToFile(docconent, targetPath, GkzxCommon.encoding);
			
		}catch(Exception e){
			e.printStackTrace();
			throw new RuntimeException();
		}
		return row;
	}
	
	/**
	 * 更新档案至目录
	 * @throws Exception 
	 */
	private void saveCaseFileToHD(String docconent, String jailid, String personid, String archiveid) throws Exception{
		
		//相对路径
		String relativePath = GkzxCommon.SINGLESEPALINE + GkzxCommon.CRIMINALARCHIVES_PATH + File.separator + jailid 
										+ File.separator + personid + File.separator + archiveid + ".txt";
		
		//绝对路径
		Properties jyconfig = new GetProperty().bornProp(GkzxCommon.DATABASETYPE, null);
		String strPath = jyconfig.getProperty(GkzxCommon.CRIMINALARCHIVES_ABSOLUTEPATH) == null ? "": jyconfig.getProperty(GkzxCommon.CRIMINALARCHIVES_ABSOLUTEPATH);
		String targetPath = GetAbsolutePath.getAbsolutePathAppend(strPath, relativePath);
		FileUtil.saveToFile(docconent, targetPath, GkzxCommon.encoding);
		
	}

	/**
	 * 流程审批 流转操作
	 */
	@Transactional
	public Object flowApprove(String data, SystemUser user, Map<String, Object> datamap) {
		// 参数
		String returnval = GkzxCommon.ZERO;
		Map<String, String> aipmap = new HashMap<String, String>();

		try {
			// 从表单aip 获取数据data
			aipmap = getDataByAip(data);

			// 判断表单上是否放了 申请人ID
			if (aipmap.containsKey("applyid")) {
				datamap.remove("applyid");
				datamap.remove("applyname");
			}
			datamap.putAll(aipmap);

			returnval = (String) operationFlow(user, datamap);
			
		} catch (Exception e) {
			returnval = "-1";
			e.printStackTrace();
		}

		return returnval;
	}

	@Transactional
	public String saveCourtCaseReview(String data, SystemUser user, Map datamap) {
		// 参数
		Map<String, String> aipmap = new HashMap<String, String>();
		aipmap = getDataByAip(data);
		datamap.putAll(aipmap);
		try {
			Map dataTem = flowBaseService.getDataFromUvFlow(datamap);
			String flowid = "";
			String fuyiflag = "";
			if (null != dataTem) {
				flowid = dataTem.get("flowid") == null ? "" : dataTem.get("flowid").toString();
				fuyiflag = dataTem.get("fuyiflag") == null ? "" : dataTem.get("fuyiflag").toString();
			}
			// this.dealFlowData(GkzxCommon.ONE, datamap, user);
			// this.dealFlowBaseData(GkzxCommon.ONE, datamap, user);
			String type = "";
			if (StringNumberUtil.notEmpty(fuyiflag)&& "1".equals(fuyiflag.trim())) {
				type = "new";
				Flow flow = new Flow();
//				flow.setFlowdraftid((datamap.get("flowdraftid").toString()));
				flow.setFlowdraftid(datamap.get("flowdraftid").toString());
				flow.setText1("2");
				int count = flowMapper.updateByFlowdraftid(flow);
				if (count != 1) {
					throw new RuntimeException();
				}
			} else {
				type = "edit";
			}
			this.saveFlowBaseOther(user, datamap, type);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
		return "success";

	}

	/**
	 * 档案操作审批
	 */
	@Transactional
	public Object approveArchives(SystemUser user, Map<String, Object> datamap) {
		// 参数
		Object returnval = -1;
		try {
			returnval = operationFlow(user, datamap);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return returnval;
	}

	/**
	 * 档案 审批鉴定
	 */
	@Transactional
	public Object batchApprove(FlowBaseArchives flowBaseArchives,
			SystemUser user, Map<String, Object> datamap) {
		// 对象
		JSONMessage message = JSONMessage.newMessage();

		// 参数
		int rows = 0;
		Object returnval = 0;
		try {
			Map map = new HashMap();
			String strArchiveid = flowBaseArchives.getArchiveid();
			map.put("archiveid", strArchiveid);
			rows = flowBaseArchivesMapper.selectById(map);
			if (1 != rows) {
				rows = flowBaseArchivesMapper.insert(flowBaseArchives);
			}
			if (1 == rows) {
				message.setInfo("添加成功!");
				message.setSuccess();
			} else {
				message.setInfo("操作失败!");
			}
			// 日志
			SystemLog log = new SystemLog();
			log.setLogtype("电子档案操作");
			log.setOpaction("新增");
			log.setOpcontent("新增电子档案基本信息,resid=");
			log.setOrgid("flowBaseArchives");
			log.setRemark("添加成功!");
			log.setStatus((short) message.getStatus());
			logService.add(log, user);

			returnval = operationFlow(user, datamap);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return returnval;
	}

	/**
	 * 获取aip表单的显示数据
	 * 
	 * @return
	 */
	public Map<String, String> getDataByAip(String data) {
		// 集合
		Map<String, String> map = new HashMap<String, String>();
		if (data != null) {
			ArrayList list = (ArrayList) JSON.parseArray(data, Object.class);
			if (list != null && list.size() > 0) {
				Map tempmap = (Map) list.get(0);
				Set<String> set = tempmap.keySet();
				// 遍历map
				for (String obj : set) {
					String name = obj;// 属性名
					String value = (String) tempmap.get(obj);// 属性名对应得值
					map.put(name, value);
				}
			}
		}
		return map;
	}

	@Transactional
	public Object jiangQuLiAn(String data,SystemUser user, Map<String,Object> datamap){
		int isdeclare = 0;//  1已申报未立案  0 未申报  2已立案
		Object result = -1;
		
		//判断是否已立案
		isdeclare = flowBaseMapper.validateRecord(datamap);
		if(isdeclare == 1){//已申报未立案
			result = flowApprove(data,user,datamap);
			String crimid = datamap.get("applyid")==null?"":datamap.get("applyid").toString();
			String applyname = datamap.get("applyname")==null?"":datamap.get("applyname").toString();
			Map temMap = new HashMap();
			temMap.put("crimid", crimid);
			temMap.put("isdeclare", 2);
			int a =  tbxfScreeningMapper.updateDataAfterPrisonerLiAn(temMap);
			if(a<=0){
				throw new RuntimeException();
			}
			
			//如果为无期、死缓、报局审核的往tbsys_document插入省局的减刑审核模板
			//String nowpunishmenttype =  datamap.get("nowpunishmenttype")==null?"":datamap.get("nowpunishmenttype").toString();
			
			//operateProvinceTemplete(user,nowpunishmenttype,datamap);
		}else{
			result = GkzxCommon.ONE;
		}
		
		return result;

	}

	@Transactional
	public Object baoWaiJailLiAn(String data, SystemUser user,
			Map<String, Object> datamap) {

		Object result = -1;
		result = flowApprove(data, user, datamap);
		// Map temMap = new HashMap();
		// temMap.put("crimid", crimid);
		// temMap.put("isdeclare", 2);
		// int a = tbxfScreeningMapper.updateDataAfterPrisonerLiAn(temMap);
		// if(a<=0){
		// throw new RuntimeException();
		// }
		return result;
	}

	
	@Transactional
	public Object baoWaiJailLiAn123(String data, SystemUser user,
			Map<String, Object> datamap) {

		Object result = -1;
		result = flowApprove(data, user, datamap);
		// Map temMap = new HashMap();
		// temMap.put("crimid", crimid);
		// temMap.put("isdeclare", 2);
		// int a = tbxfScreeningMapper.updateDataAfterPrisonerLiAn(temMap);
		// if(a<=0){
		// throw new RuntimeException();
		// }
		return result;
	}
	public Object isHaveCaseLiAn(Map<String,Object> map) {
		
		String result = "";
		List<Map> list = MapUtil.turnKeyToLowerCaseOfList(uvFlowMapper.getCaseLiAnData(map));
		if (null == list || list.size() == 0) {
			result = "0";
		} else {
			for (Map<String, String> temMap : list) {
				result += temMap.get("applyname") + ",";
			}
			result = StringNumberUtil.removeLastStr(result, ",");
		}
		return result;
	}
	
	/**
	 * 描述：
	 */
	@Override
	public Object isSJHaveCaseLiAn(String flowdraftids) {
		JSONMessage message = JSONMessage.newMessage();
		String result = "";
		Map paramap = new HashMap();
		paramap.put("flowdraftids", flowdraftids);
		List<Map> list = MapUtil.turnKeyToLowerCaseOfList(uvFlowMapper.getSJCaseLiAnData(paramap));
		if (null == list || list.size() == 0){
			message.setSuccess();
		} else {
			for (Map<String, String> temMap : list) {
				result += temMap.get("applyname") + ",";
			}
			result = StringNumberUtil.removeLastStr(result, ",");
			message.setInfo(result);
		}
		return message;
	}

	@Transactional
	public int provinceLiAn(String data, SystemUser user,
			Map<String, Object> datamap) {

		int result = 1;
		Object obj = flowApprove(data, user, datamap);
//		result = obj == null ? -1 : Integer.valueOf(obj.toString());
		if (obj == null){
			result = -1;
		}

		/*
		 * if(result == 0){
		 * 
		 * String crimid =
		 * datamap.get("applyid")==null?"":datamap.get("applyid").toString();
		 * String applyname =
		 * datamap.get("applyname")==null?"":datamap.get("applyname"
		 * ).toString(); Map temMap = new HashMap(); temMap.put("crimid",
		 * crimid); temMap.put("isdeclare", 2); int a =
		 * tbxfScreeningMapper.updateDataAfterPrisonerLiAn(temMap); if(a<=0){
		 * throw new RuntimeException(); }
		 * 
		 * }
		 */

		return result;

	}
	
	@Transactional
	@Override
	public int provinceLiAnForGD(Map<String,Object> paramMap){
		
		int result = 1;
		
		String curyear = "";
		Date date = new Date();
		curyear = DateUtil.dateFormat(date, "yyyy");
		paramMap.put("curyear", curyear);
		
//		String casenumber = flowBaseService.getSJMaxCaseNumForGD(paramMap);//案件号
		
		Object flowdraftidObj = paramMap.get("flowdraftids");
		Object approveperson = paramMap.get("approveperson");
		if(StringNumberUtil.notEmpty(flowdraftidObj)){
			String flowdraftids = flowdraftidObj.toString().trim();
			List<String> flowdraftidList = StringNumberUtil.formatString2List(flowdraftids, ",");
			
			
//			int casenum = Integer.parseInt((String) casenumber);

			Map<String,Object> paramap = new HashMap<String,Object>();
			for(String flowdraftid : flowdraftidList){
				paramap.put("flowdraftid", flowdraftid);
				FlowBase fb = flowBaseMapper.getFlowBaseByFlowdraftid(paramap);
				if(null != approveperson){
					fb.setFjquser(approveperson.toString().trim());
				}
				if(StringNumberUtil.isEmpty(fb.getText11())){
					paramMap.put("casetype", fb.getText7());
					int casenum = getSJMaxCaseNumForHN(paramMap);
					fb.setText11(curyear + casenum);
				}
				if(StringNumberUtil.isEmpty(fb.getText26())){
					fb.setText26(fb.getText7());
				}
				flowBaseMapper.updateSensitiveByFlowdraftid(fb);
			}
		}
		
		return result;
	}
	
	
	
	private int getSJMaxCaseNumForHN(Map<String,Object> paramMap){
		
		String casenumber = flowBaseService.getSJMaxCaseNumForGD(paramMap);//案件号
		
		int casenum = Integer.parseInt((String) casenumber);
		
		return casenum;
	}

	@Transactional
	public String courtLiAn(String data, SystemUser user,
			Map<String, Object> datamap) {

		String result = "-1";
		Object obj = flowApprove(data, user, datamap);
		result = obj == null ? "-1" : obj.toString();

		/*
		 * if(result == 0){
		 * 
		 * String crimid =
		 * datamap.get("applyid")==null?"":datamap.get("applyid").toString();
		 * String applyname =
		 * datamap.get("applyname")==null?"":datamap.get("applyname"
		 * ).toString(); Map temMap = new HashMap(); temMap.put("crimid",
		 * crimid); temMap.put("isdeclare", 2); int a =
		 * tbxfScreeningMapper.updateDataAfterPrisonerLiAn(temMap); if(a<=0){
		 * throw new RuntimeException(); } }
		 */

		return result;

	}

	private int operateProvinceTemplete(SystemUser user,
			String nowpunishmenttype, Map<String, Object> datamap) {

		String crimid = datamap.get("applyid") == null ? "" : datamap.get("applyid").toString();
		// 判断该罪犯是否报局审核
		String state = tbxfSentenceAlterationMapper.getReportauditByCrimid(crimid);
		String applyname = datamap.get("applyname") == null ? "" : datamap.get("applyname").toString();
		if ("1".equals(state)|| (StringNumberUtil.notEmpty(nowpunishmenttype) && 
		(("9995".equals(nowpunishmenttype.trim())) || ("9996".equals(nowpunishmenttype.trim()))))) {
			int count = 0;
			String sjTempid = "JXJS_JXSH";
			String departid = user.getDepartid();
			SystemOrganization org = systemOrganizationService.getByOrganizationId(departid);
			String porgid = org.getPorgid();
			TbsysTemplate template = systemModelService.getTemplateAndDepartid(sjTempid, porgid);
			if (template != null) {
				TbsysDocument td = new TbsysDocument();
				td.setContent(template.getContent());
				td.setCrimid(crimid);
				td.setDepartid(departid);
				td.setOpid(user.getUserid());
				td.setTempid(sjTempid);
				td.setIntroduction(applyname + "省局减刑假释审核表模板");
				Map paramap = new HashMap();
				paramap.put("crimid", crimid);
				paramap.put("tempid", sjTempid);
				TbsysDocument preTd = tbsysDocumentMapper
						.getTbsysDocumentByMap2(paramap);
				if (null != preTd
						&& StringNumberUtil.notEmpty(preTd.getContent())) {
					td.setDocid(preTd.getDocid());
					count = tbsysDocumentMapper.updateTbsysDocument(td);
				} else {
					count = tbsysDocumentMapper.insertSelective(td);
				}
				if (count <= 0) {
					return -1;
				}
			}

		}

		return 0;
	}

//	@Transactional
//	public int batchJailLiAnFlowApprove(SystemUser user,Map<String,Object> datamap){
//		
//		String flowdefid = datamap.get("flowdefid")==null?"":datamap.get("flowdefid").toString();
//		String curyear = datamap.get("curyear")==null?"":datamap.get("curyear").toString();
//		String dataStr = datamap.get("dataStr")==null?"":datamap.get("dataStr").toString();
//		String resid = datamap.get("resid")==null?"":datamap.get("resid").toString();
//		String tempid = datamap.get("tempid")==null?"":datamap.get("tempid").toString();
//		String flowid = datamap.get("flowid")==null?"":datamap.get("flowid").toString();
//		String conent = datamap.get("conent")==null?"":datamap.get("conent").toString();
//		String docconent = datamap.get("docconent")==null?"":datamap.get("docconent").toString();
//		String operateType = datamap.get("operateType")==null?"":datamap.get("operateType").toString();
//		String flowdraftid = datamap.get("flowdraftid")==null?"":datamap.get("flowdraftid").toString();
//		String commenttext = datamap.get("commenttext")==null?"":datamap.get("commenttext").toString();
//		String casetype = datamap.get("casetype")==null?"":datamap.get("casetype").toString();
//		String jailcasechg = datamap.get("jailcasechg")==null?"":datamap.get("jailcasechg").toString();
//		String batch = datamap.get("batch")==null?"":datamap.get("batch").toString();
//		String shortname = datamap.get("shortname")==null?"":datamap.get("shortname").toString();
//		String commutetype = datamap.get("commutetype")==null?"":datamap.get("commutetype").toString();
//
//		Map temMap3 = new HashMap();
//		temMap3.put("departid", user.getDepartid());
//		temMap3.put("flowdefid", flowdefid);
//		temMap3.put("curyear", curyear);
//		if (StringNumberUtil.notEmpty(commutetype)) {
//			temMap3.put("commutetype", commutetype);
//		}
//		String[] datas = dataStr.split(",");
//		Properties jyconfig = new GetProperty().bornProp(GkzxCommon.DATABASETYPE, null);
//		String provincecode = jyconfig.getProperty("provincecode");
//		List<String> lianCrimsLst = new ArrayList<String>();
//		if (GkzxCommon.TIANJIN_PROVINCE.equals(provincecode)) {
//			String strCrimids = "'1'";
//			for (String temData : datas) {
//				String[] temDataArr = temData.split("@");
//				String applyid = temDataArr[0];// 申请人ID
//				strCrimids = strCrimids + ",'" + applyid + "'";
//			}
//			if (commutetype.equals(GkzxCommon.ZERO)||commutetype.equals(GkzxCommon.ONE))  {
//				Map temMap4 = new HashMap();
//				temMap4.put("crimids", strCrimids);
//				temMap4.put("commutetype", commutetype);
//				List<Map> crimMapsLst = MapUtil.turnKeyToLowerCaseOfList(tbxfScreeningMapper.getLiAnCrimList(temMap4));
//				
//				if (crimMapsLst != null) {
//					for (int m =0; m < crimMapsLst.size(); m++) {
//						lianCrimsLst.add(crimMapsLst.get(m).get("crimid").toString());
//					}
//				}
//			}
//		}
//		
//		String casenumber = datamap.get("casenumber")==null?"":datamap.get("casenumber").toString();
//		if("".equals(casenumber)){
//			casenumber = flowBaseService.getMaxCaseNum(temMap3);// 案件号
//		}
//
//		if (!StringNumberUtil.notEmpty(dataStr)) {
//			return -1;
//		}
//		
//
//		int casenumberNum = Integer.parseInt(casenumber);
//		Map temMap2 = new HashMap();
//		for (String temData : datas) {
//			String[] temDataArr = temData.split("@");
//			String applyid = temDataArr[0];// 申请人ID
//			String applyname = temDataArr[1];// 申请人名称
//			String orgid = temDataArr[2];// 申请人部门
//			String nowpunishmenttype = "";
//			if (temDataArr.length > 3) {
//				nowpunishmenttype = temDataArr[3];
//			}
//			// 从表单aip 获取数据data
//			
//			//减刑或假释立案条件不满足
//			if (GkzxCommon.TIANJIN_PROVINCE.equals(provincecode)) {
//				if (lianCrimsLst.indexOf(applyid) < 0) {
//					continue;
//				}
//			}
//			// 流程流转
//			Map<String, Object> tempmap = new HashMap<String, Object>();
//			tempmap.put("applyid", applyid);// 和流程相关按钮ID
//			tempmap.put("applyname", applyname);// 和流程相关按钮ID
//			tempmap.put("resid", resid);// 和流程相关按钮ID
//			tempmap.put("tempid", tempid);
//			tempmap.put("flowid", flowid);// 流程ID
//			tempmap.put("conent", conent);// 内容
//			tempmap.put("docconent", docconent);// 审批大字段
//			tempmap.put("flowdefid", flowdefid);// 流程ID
//			tempmap.put("operateType", operateType);
//			tempmap.put("flowdraftid", flowdraftid);// 流程草稿ID
//			tempmap.put("commenttext", commenttext);// 审批意见
//			tempmap.put("orgid", orgid);
//			tempmap.put("casetype", casetype);
//			tempmap.put("jailcasechg", jailcasechg);
//			tempmap.put("curyear", curyear);
//			tempmap.put("batch", batch);
//			tempmap.put("departid", user.getDepartid());
//			tempmap.put("shortname", shortname);
//			
//			tempmap.put("jxjs_1", commutetype);
//			tempmap.put("casenum", curyear + casenumberNum);
//			casenumberNum++;
//
//			//判断是否已立案
//			int count = flowBaseMapper.validateRecord(tempmap);
//			if(count == 1){
//				Object obj = operationFlow(user, tempmap);
//				int result = -1;
//				if(obj==null){
//					throw new RuntimeException();
//				}
//				
//				temMap2.put("crimid", applyid);
//				temMap2.put("isdeclare", 2);
//				tbxfScreeningMapper.updateDataAfterPrisonerLiAn(temMap2);
//				
//				result = operateProvinceTemplete(user,nowpunishmenttype,tempmap);
//				if(result == -1){
//					throw new RuntimeException();
//				}
//			}
//		}
//		return 0;
//	}
	
	
	@Override
	@Transactional
	public int batchJailLiAnFlowApprove(SystemUser user,Map<String,Object> datamap){
		
		int result = 1;//  1 立案成功	-1立案失败     
		
		datamap.put("departid", user.getDepartid());
		String flowdefid = datamap.get("flowdefid")==null?"":datamap.get("flowdefid").toString();
		String curyear = datamap.get("curyear")==null?"":datamap.get("curyear").toString();
		String dataStr = datamap.get("dataStr")==null?"":datamap.get("dataStr").toString();
		String commutetype = datamap.get("commutetype")==null?"":datamap.get("commutetype").toString();
		String casetype = datamap.get("casetype")==null?"减（有）":datamap.get("casetype").toString();//湖南默认是减有字
		
		if(StringNumberUtil.isEmpty(dataStr)) {
			return -1;
		}
		if(StringNumberUtil.notEmpty(commutetype)){
			datamap.put("jxjs_1", commutetype);
		}
		
		
		Map<String, Object> temMap = new HashMap<String, Object>();
		temMap.put("departid", user.getDepartid());
		temMap.put("flowdefid", flowdefid);
		temMap.put("curyear", curyear);
		temMap.put("casetype", casetype);//湖南的减刑案件号查询
		
		String casenumber = flowBaseService.getMaxCaseNum4HuNanJailCommute(temMap);// 湖南专用的案件号查询
		
		String[] datas = dataStr.split(",");
		
		List<Map<String,Object>> criminalList = new ArrayList<Map<String,Object>>();
		//
		int casenumberNum = Integer.parseInt(casenumber);
		Map<String,Object> temMap2 = new HashMap<String,Object>();
		for (String temData : datas) {
			Map<String,Object> paramap = new HashMap<String,Object>();
			paramap.putAll(datamap);
			
			String[] temDataArr = temData.split("@");
			String applyid = temDataArr[0];// 申请人ID
			String applyname = temDataArr[1];// 申请人名称
			String orgid = temDataArr[2];// 申请人部门
			String nowpunishmenttype = "";
			if (temDataArr.length > 3) {
				nowpunishmenttype = temDataArr[3];
			}
			
			
			String operatesymbol = tbxfScreeningMapper.decideOperateSymbol(applyid);
					
			//如果是无期、死缓等罪犯，要把其电子档案往临时目录里copy，然后用cwrsync往省局进行数据传输
			if(StringNumberUtil.notEmpty(operatesymbol) && ("1".equals(operatesymbol.trim())) ){
				Map<String,Object> map = new HashMap<String,Object>();
				map.put("crimid", applyid);
				map.put("departid", user.getDepartid());
				criminalList.add(map);
			}
			
			paramap.put("applyid", applyid);// 和流程相关按钮ID
			paramap.put("applyname", applyname);// 和流程相关按钮ID
			paramap.put("orgid", orgid);
			paramap.put("departid", user.getDepartid());
			paramap.put("_casenumber", curyear + casenumberNum);
			
			String flowlaunch = "1";//流程发起
			paramap.put("flowlaunch", flowlaunch);//流程发起时，tbflow表的state=0
			
			String obj = operationFlow(user, paramap);
			if(StringNumberUtil.isEmpty(obj) || GkzxCommon.ZERO.equals(obj)){
				throw new RuntimeException();
			}
			
			temMap2.put("crimid", applyid);
			temMap2.put("isdeclare", 2);
			tbxfScreeningMapper.updateDataAfterPrisonerLiAn(temMap2);
			
			casenumberNum ++;
		}
		
		//
		if(null != criminalList && ! criminalList.isEmpty()){
			//采用多线程实现
			new Thread(new ArchivesCopyRunnable(criminalList,dataTransferService)).start();
		}
		//
		return result;
	}

	@Transactional
	public int baoWaiJailBatchLiAn(SystemUser user, Map<Object, Object> datamap) {

		String flowdefid = datamap.get("flowdefid") == null ? "" : datamap.get("flowdefid").toString();
		String curyear = datamap.get("curyear") == null ? "" : datamap.get("curyear").toString();
		String dataStr = datamap.get("dataStr") == null ? "" : datamap.get("dataStr").toString();
		String resid = datamap.get("resid") == null ? "" : datamap.get("resid").toString();
		String tempid = datamap.get("tempid") == null ? "" : datamap.get("tempid").toString();
		String flowid = datamap.get("flowid") == null ? "" : datamap.get("flowid").toString();
		String conent = datamap.get("conent") == null ? "" : datamap.get("conent").toString();
		String docconent = datamap.get("docconent") == null ? "" : datamap.get("docconent").toString();
		String operateType = datamap.get("operateType") == null ? "" : datamap.get("operateType").toString();
		String flowdraftid = datamap.get("flowdraftid") == null ? "" : datamap.get("flowdraftid").toString();
		String commenttext = datamap.get("commenttext") == null ? "" : datamap.get("commenttext").toString();
		String casetype = datamap.get("casetype") == null ? "" : datamap.get("casetype").toString();
		String commutetype = datamap.get("commutetype") == null ? "" : datamap.get("commutetype").toString();

		Map temMap3 = new HashMap();
		temMap3.put("departid", user.getDepartid());
		temMap3.put("flowdefid", flowdefid);
		temMap3.put("curyear", curyear);
		if (StringNumberUtil.notEmpty(commutetype)) {
			temMap3.put("commutetype", commutetype);
		}
		String casenumber = flowBaseService.getMaxBaowaiCaseNum(temMap3);// 案件号

		if (!StringNumberUtil.notEmpty(dataStr)) {
			return -1;
		}
		String[] datas = dataStr.split(",");

		int casenumberNum = Integer.parseInt(casenumber);
		Map temMap2 = new HashMap();
		for (String temData : datas) {
			String[] temDataArr = temData.split("@");
			String applyid = temDataArr[0];// 申请人ID
			String applyname = temDataArr[1];// 申请人名称
			String orgid = temDataArr[2];// 申请人部门
			// 从表单aip 获取数据data

			// 流程流转
			Map<String, Object> tempmap = new HashMap<String, Object>();
			tempmap.put("applyid", applyid);// 和流程相关按钮ID
			tempmap.put("applyname", applyname);// 和流程相关按钮ID
			tempmap.put("resid", resid);// 和流程相关按钮ID
			tempmap.put("tempid", tempid);
			tempmap.put("flowid", flowid);// 流程ID
			tempmap.put("conent", conent);// 内容
			tempmap.put("docconent", docconent);// 审批大字段
			tempmap.put("flowdefid", flowdefid);// 流程ID
			tempmap.put("operateType", operateType);
			tempmap.put("flowdraftid", flowdraftid);// 流程草稿ID
			tempmap.put("commenttext", commenttext);// 审批意见
			tempmap.put("orgid", orgid);
			tempmap.put("casetype", casetype);
			tempmap.put("jxjs_1", commutetype);
			tempmap.put("casenum", curyear + casenumberNum);
			casenumberNum++;
			Object obj = operationFlow(user, tempmap);
			if (obj == null) {
				throw new RuntimeException();
			}

		}
		return 0;

	}

	@Transactional
	public int batchProvinceLiAnFlowApprove(SystemUser user,
			Map<Object, Object> datamap) {

		String flowdefid = datamap.get("flowdefid") == null ? "" : datamap.get("flowdefid").toString();
		String curyear = datamap.get("curyear") == null ? "" : datamap.get("curyear").toString();
		String dataStr = datamap.get("dataStr") == null ? "" : datamap.get("dataStr").toString();
		String resid = datamap.get("resid") == null ? "" : datamap.get("resid").toString();
		String tempid = datamap.get("tempid") == null ? "" : datamap.get("tempid").toString();
		String flowid = datamap.get("flowid") == null ? "" : datamap.get("flowid").toString();
		String conent = datamap.get("conent") == null ? "" : datamap.get("conent").toString();
		String docconent = datamap.get("docconent") == null ? "" : datamap.get("docconent").toString();
		String operateType = datamap.get("operateType") == null ? "" : datamap.get("operateType").toString();
		String flowdraftid = datamap.get("flowdraftid") == null ? "" : datamap.get("flowdraftid").toString();
		String commenttext = datamap.get("commenttext") == null ? "" : datamap.get("commenttext").toString();
		String casetype = datamap.get("casetype") == null ? "" : datamap.get("casetype").toString();
//		String commutetype = datamap.get("commutetype") == null ? "" : datamap.get("commutetype").toString();

//		Map temMap3 = new HashMap();
//		temMap3.put("departid", user.getDepartid());
//		temMap3.put("flowdefid", flowdefid);
//		temMap3.put("curyear", curyear);
//		if (StringNumberUtil.notEmpty(commutetype)) {
//			temMap3.put("commutetype", commutetype);
//		}
//		String casenumber = flowBaseService.getMaxCaseNum(temMap3);// 案件号

		if (!StringNumberUtil.notEmpty(dataStr)) {
			return -1;
		}
		String[] datas = dataStr.split(",");

//		int casenumberNum = Integer.parseInt(casenumber);
		Map temMap2 = new HashMap();
		for (String temData : datas) {
			String[] temDataArr = temData.split("@");
			String applyid = temDataArr[0];// 申请人ID
			String applyname = temDataArr[1];// 申请人名称
			String orgid = temDataArr[2];// 申请人部门
			String commutetype = temDataArr[3];//案件类刑
			if(StringNumberUtil.notEmpty(commutetype)&&"0".equals(commutetype.trim())){
				casetype = "减字";
			}else if(StringNumberUtil.notEmpty(commutetype)&&"1".equals(commutetype.trim())){
				casetype = "假字";
			}
			
			Map temMap3 = new HashMap();
			temMap3.put("departid", user.getDepartid());
			temMap3.put("flowdefid", flowdefid);
			temMap3.put("curyear", curyear);
			if (StringNumberUtil.notEmpty(commutetype)) {
				temMap3.put("commutetype", commutetype);
			}
			String casenumber = flowBaseService.getLastCaseNum(temMap3);// 案件号
			int casenumberNum = Integer.parseInt(casenumber);

			// 从表单aip 获取数据data

			// 流程流转
			Map<String, Object> tempmap = new HashMap<String, Object>();
			tempmap.put("applyid", applyid);// 和流程相关按钮ID
			tempmap.put("applyname", applyname);// 和流程相关按钮ID
			tempmap.put("resid", resid);// 和流程相关按钮ID
			tempmap.put("tempid", tempid);
			tempmap.put("flowid", flowid);// 流程ID
			tempmap.put("conent", conent);// 内容
//			if(StringNumberUtil.isEmpty(docconent)){
//				TbsysDocument tbsysDocument = tbsysDocumentMapper.getTbsysDocument(null,tempid,applyid,null);
//				docconent = tbsysDocument.getContent();
//			}
			tempmap.put("docconent", docconent);// 审批大字段
			tempmap.put("flowdefid", flowdefid);// 流程ID
			tempmap.put("operateType", operateType);
			tempmap.put("flowdraftid", flowdraftid);// 流程草稿ID
			tempmap.put("commenttext", commenttext);// 审批意见
			tempmap.put("orgid", orgid);
			tempmap.put("casetype", casetype);
			if (StringNumberUtil.notEmpty(commutetype)) {
				tempmap.put("jxjs_1", commutetype);
			}
			tempmap.put("casenum", curyear + casenumberNum);
			//casenumberNum++;

			Object obj = operationFlow(user, tempmap);
//			int result = obj == null ? -1 : Integer.valueOf(obj.toString());
			if (obj == null) {
				throw new RuntimeException();
			}

			// temMap2.put("crimid", applyid);
			// temMap2.put("isdeclare", 2);
			// tbxfScreeningMapper.updateDataAfterPrisonerLiAn(temMap2);

		}
		return 0;

	}

//	@Transactional
//	public int batchCourtLiAnFlowApprove(SystemUser user,
//			Map<Object, Object> datamap) {
//
//		String flowdefid = datamap.get("flowdefid") == null ? "" : datamap.get("flowdefid").toString();
//		String curyear = datamap.get("curyear") == null ? "" : datamap.get("curyear").toString();
//		String dataStr = datamap.get("dataStr") == null ? "" : datamap.get("dataStr").toString();
//		String resid = datamap.get("resid") == null ? "" : datamap.get("resid").toString();
//		String tempid = datamap.get("tempid") == null ? "" : datamap.get("tempid").toString();
//		String flowid = datamap.get("flowid") == null ? "" : datamap.get("flowid").toString();
//		String conent = datamap.get("conent") == null ? "" : datamap.get("conent").toString();
//		String docconent = datamap.get("docconent") == null ? "" : datamap.get("docconent").toString();
//		String operateType = datamap.get("operateType") == null ? "" : datamap.get("operateType").toString();
//		String flowdraftid = datamap.get("flowdraftid") == null ? "" : datamap.get("flowdraftid").toString();
//		String commenttext = datamap.get("commenttext") == null ? "" : datamap.get("commenttext").toString();
//		String casetype = datamap.get("casetype") == null ? "" : datamap.get("casetype").toString();
//		String courttype = datamap.get("courttype") == null ? "" : datamap.get("courttype").toString();
//		String acceptdate = datamap.get("acceptdate") == null ? "" : datamap.get("acceptdate").toString();
//		String liandate = datamap.get("liandate") == null ? "" : datamap.get("liandate").toString();
//		String pagecasenumber = datamap.get("casenumber") == null ? "": datamap.get("casenumber").toString();
//		// String commutetype =
//		// datamap.get("commutetype")==null?"":datamap.get("commutetype").toString();
//
//		Map temMap3 = new HashMap();
//		temMap3.put("departid", user.getDepartid());
//		temMap3.put("flowdefid", flowdefid);
//		temMap3.put("curyear", curyear);
//		// if(StringNumberUtil.notEmpty(commutetype)){
//		// temMap3.put("commutetype", commutetype);
//		// }
//		String casenumber = "";
//		if (StringNumberUtil.notEmpty(pagecasenumber)) {
//			casenumber = pagecasenumber;
//		} else {
//			casenumber = flowBaseService.getCourtMaxCaseNum(temMap3);// 案件号
//		}
//		if (!StringNumberUtil.notEmpty(dataStr)) {
//			return -1;
//		}
//		String[] datas = dataStr.split(",");
//
//		int casenumberNum = Integer.parseInt(casenumber);
//		Map temMap2 = new HashMap();
//		for (String temData : datas) {
//			String[] temDataArr = temData.split("@");
//			String applyid = temDataArr[0];// 申请人ID
//			String applyname = temDataArr[1];// 申请人名称
//			String orgid = temDataArr[2];// 申请人部门
//			// 从表单aip 获取数据data
//
//			// 流程流转
//			Map<String, Object> tempmap = new HashMap<String, Object>();
//			tempmap.put("applyid", applyid);// 和流程相关按钮ID
//			tempmap.put("applyname", applyname);// 和流程相关按钮ID
//			tempmap.put("resid", resid);// 和流程相关按钮ID
//			tempmap.put("tempid", tempid);
//			tempmap.put("flowid", flowid);// 流程ID
//			tempmap.put("conent", conent);// 内容
//			tempmap.put("docconent", docconent);// 审批大字段
//			tempmap.put("flowdefid", flowdefid);// 流程ID
//			tempmap.put("operateType", operateType);
//			tempmap.put("flowdraftid", flowdraftid);// 流程草稿ID
//			tempmap.put("commenttext", commenttext);// 审批意见
//			tempmap.put("orgid", orgid);
//			tempmap.put("casetype", casetype);
//			tempmap.put("courttype", courttype);
//			tempmap.put("acceptdate", acceptdate);
//			tempmap.put("liandate", liandate);
//			// if(StringNumberUtil.notEmpty(commutetype)){
//			// tempmap.put("jxjs_1", commutetype);
//			// }
//			tempmap.put("casenum", curyear + casenumberNum);
//			casenumberNum++;
//
//			Object obj = operationFlow(user, tempmap);
//			String result = obj == null ? "-1" : obj.toString();
//			if (result == "-1") {
//				throw new RuntimeException();
//			}
//
//			// temMap2.put("crimid", applyid);
//			// temMap2.put("isdeclare", 2);
//			// tbxfScreeningMapper.updateDataAfterPrisonerLiAn(temMap2);
//
//		}
//		return 0;
//
//	}

	@Override
	public int jailBaoWaiLiAnCount(Map map) {
		return uvFlowMapper.jailBaoWaiLiAnCount(map);
	}
	
	@Override
	public int jailBaoWaiLiAnCount123(Map map) {
		return uvFlowMapper.jailBaoWaiLiAnCount123(map);
	}
	
	@Override
	public int updateFjqUser(Map<String,Object> map) {
		return uvFlowMapper.updateFjqUser(map);
	}

	@Override
	public List<Map> jailBaoWaiLiAnList(Map map) {
		return MapUtil.turnKeyToLowerCaseOfList(uvFlowMapper.jailBaoWaiLiAnList(map));
	}
	
	@Override
	public List<Map> jailBaoWaiLiAnList123(Map map) {
		return MapUtil.turnKeyToLowerCaseOfList(uvFlowMapper.jailBaoWaiLiAnList123(map));
	}


	@Override
	public int countBaoWaiJianBanRenList(Map map) {
		return uvFlowMapper.countBaoWaiJianBanRenList(map);
	}

	@Override
	public List<Map> getBaoWaiJianBanRenList(Map map) {
		return MapUtil.turnKeyToLowerCaseOfList(uvFlowMapper
				.getBaoWaiJianBanRenList(map));
	}

	@Override
	public int countBaoWaiJailCaseList(Map map) {
		return uvFlowMapper.countBaoWaiJailCaseList(map);
	}

	@Override
	public List<Map> getBaoWaiJailCaseList(Map map) {
		return MapUtil.turnKeyToLowerCaseOfList(uvFlowMapper
				.getBaoWaiJailCaseList(map));
	}

	@Override
	public int countProvinceOutPrisonLiAnList(Map map) {
		return uvFlowMapper.countProvinceOutPrisonLiAnList(map);
	}

	@Override
	public List<Map> getProvinceOutPrisonLiAnList(Map map) {
		return MapUtil.turnKeyToLowerCaseOfList(uvFlowMapper.getProvinceOutPrisonLiAnList(map));
	}

	@Override
	public int countOutPrisonChuShiList(Map map) {
		return uvFlowMapper.countOutPrisonChuShiList(map);
	}

	@Override
	public List<Map> getOutPrisonChuShiList(Map map) {
		return MapUtil.turnKeyToLowerCaseOfList(uvFlowMapper
				.getOutPrisonChuShiList(map));
	}

	@Override
	public int countOutPrisonProvinceCaseList(Map map) {
		return uvFlowMapper.countOutPrisonProvinceCaseList(map);
	}

	@Override
	public List<Map> getOutPrisonProvinceCaseList(Map map) {
		return MapUtil.turnKeyToLowerCaseOfList(uvFlowMapper
				.getOutPrisonProvinceCaseList(map));
	}

	@Override
	public List<Map> getCourtCaseHandleList(Map map) {
		return MapUtil.turnKeyToLowerCaseOfList(uvFlowMapper
				.getCourtCaseHandleList(map));
	}

	@Override
	public int countCourtCaseHandleList(Map map) {
		return uvFlowMapper.countCourtCaseHandleList(map);
	}

	@Override
	public List<Map> getCourtCaseCheckDataList(Map map) {
		return MapUtil.turnKeyToLowerCaseOfList(uvFlowMapper
				.getCourtCaseCheckDataList(map));
	}

	@Override
	public String getCourtCasePercentCheckData(Map map) {
		List<Map> result = MapUtil.turnKeyToLowerCaseOfList(uvFlowMapper
				.getCourtCasePercentCheckData(map));
		int length = result.size();
		Double percent = 0.0;
		if (StringNumberUtil.notEmpty(map.get("percent"))) {
			percent = Double.parseDouble(map.get("percent").toString());
		}
		int count = (int) (length * (percent / 100));
		Integer[] indexArr = StringNumberUtil.getRandomData(0, length - 1,
				count);
		StringBuilder sb = new StringBuilder();
		for (Integer index : indexArr) {
			sb.append(result.get(index).get("flowdraftid")).append(",");
		}
		String flowdraftids = sb.toString();
		if (flowdraftids.endsWith(",")) {
			flowdraftids = flowdraftids.substring(0, flowdraftids.length() - 1);
		}
		return flowdraftids;
	}

	@Override
	public int countCourtCaseCheckDataList(Map map) {
		return uvFlowMapper.countCourtCaseCheckDataList(map);
	}

	@Override
	public List<Map> getCourtCaseBaoBeiDataList(Map map) {
		return MapUtil.turnKeyToLowerCaseOfList(uvFlowMapper
				.getCourtCaseBaoBeiDataList(map));
	}

	@Override
	public int countCourtCaseBaoBeiDataList(Map map) {
		return uvFlowMapper.countCourtCaseBaoBeiDataList(map);
	}

	@Override
	public List getAllDocumentIdByCasenums(Map map) {
		return uvFlowMapper.getAllDocumentIdByCasenums(map);
	}

	@Override
	public List<Map> getCriminalForCourtList(Map map) {
		return MapUtil.turnKeyToLowerCaseOfList(uvFlowMapper
				.getCriminalForCourtList(map));
	}

	@Override
	public int countCriminalForCourt(Map map) {
		return uvFlowMapper.countCriminalForCourt(map);
	}

	@Override
	public String getStateByFlowdraftid(Map map) {
		return uvFlowMapper.getStateByFlowdraftid(map);
	}
	
	@Override
	public String getNodeidByFlowdraftid(Map map) {
		return uvFlowMapper.getNodeidByFlowdraftid(map);
	}

	@Override
	public List<Map> getCollegialPanelListData(Map map) {
		return MapUtil.turnKeyToLowerCaseOfList(uvFlowMapper
				.getCollegialPanelListData(map));
	}

	@Override
	public int countCollegialPanelListData(Map map) {
		return uvFlowMapper.countCollegialPanelListData(map);
	}

	@Override
	public List<Map> getChangeCollegialPanelListData(Map map) {
		return MapUtil.turnKeyToLowerCaseOfList(uvFlowMapper
				.getChangeCollegialPanelListData(map));
	}

	@Override
	public int countChangeCollegialPanelListData(Map map) {
		return uvFlowMapper.countChangeCollegialPanelListData(map);
	}

	@Override
	public List<Map> getCourtCaseShengPiList(Map map) {
		return MapUtil.turnKeyToLowerCaseOfList(uvFlowMapper
				.getCourtCaseShengPiList(map));
	}

	@Override
	public int countCourtCaseShengPiList(Map map) {
		return uvFlowMapper.countCourtCaseShengPiList(map);
	}

	@Override
	public String getCourtCaseState(Map map) {
		return uvFlowMapper.getCourtCaseState(map);
	}

	@Override
	@Transactional
	public Map saveModelContent(Map<String, String> map, SystemUser user) {
		String flowdraftid = map.get("flowdraftid");
		String tempid = map.get("tempid");
		String docconent = map.get("annexcontent");
		String otherid = map.get("otherid");
		String modelid = map.get("modelid");
		String type = "";
		if (StringNumberUtil.notEmpty(otherid)) {
			type = "edit";
		} else {
			type = "new";
		}

		return operationFlowBaseOther(flowdraftid, tempid, docconent, user,
				type, otherid, modelid);
	}

	@Override
	@Transactional
	public int removearchives(String archiveid) {
		int rows = 0;
		if (archiveid != null && !"".equals(archiveid)) {
			String[] archiveids = archiveid.split(",");
			if (archiveids != null && archiveids.length > 0) {
				for (String m : archiveids) {
					// 删除批量归档（结案）用表
					rows = flowArchivesExistMapper.removeByArchid(m);
					// 删除电子档案基本表
					rows = flowBaseArchivesMapper.deleteById(m);
					//删除tbflow_base
					rows =  flowBaseArchivesMapper.deleteFlowBaseById(m);
					//删除流程信息
					rows =  flowBaseArchivesMapper.deleteFlowById(m);
					// 删除罪犯电子档案信息
					FlowArchives flowArchives = flowArchivesMapper.findByArchiveid(m);
					String sPath = GetAbsolutePath.getAbsolutePath(flowArchives.getDocconent());
					boolean flag = FileUtil.deleteFolder(sPath);
					rows = flowArchivesMapper.removeByArchid(m);
				}
			}
		}
		return rows;
	}

	@Override
	public String getCommenttextByApplyid(String applyid, String flowdefid) {
		return uvFlowMapper.getCommenttextByApplyid(applyid, flowdefid);
	}

	@Override
	public List<Map> getCourtCaseGuiDangList(Map map) {
		return MapUtil.turnKeyToLowerCaseOfList(uvFlowMapper
				.getCourtCaseGuiDangList(map));
	}

	@Override
	public int getCourtCaseGuiDangListCount(Map map) {
		return uvFlowMapper.getCourtCaseGuiDangListCount(map);
	}

	@Override
	public List<Map> getShujyData(Map map) {
		return MapUtil.turnKeyToLowerCaseOfList(uvFlowMapper.getShujyData(map));
	}

	@Override
	public int getShujyDataCount(Map map) {
		return uvFlowMapper.getShujyDataCount(map);
	}

	@Override
	public int updateCriminalDetainStatus(HttpServletRequest request,SystemUser user) {
		int row = 0;
		try {
			String crimid = request.getParameter("crimids");//罪犯编号集合
			String crimids[] = crimid.split(",");
			Map map = new HashMap();
			for (int i = 0; i < crimids.length; i++) {
				map.put("crimids", crimids[i]);
				map.put("detainstatus", GkzxCommon.DETAINSTATUS_BW);
				map.put("status", GkzxCommon.SENSTATUS);
				row = uvFlowMapper.updateCriminalDetainStatus(map);//修改基本信息表中在押状态
				row = uvFlowMapper.updateSenStatusDetainStatus(map);//修改刑期变动表中的状态
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}
	@Override
	public int countAllOfTuiAnByCondition(Map map) {
		return uvFlowMapper.countAllOfTuiAnByCondition(map);
	}

	@Override
	public List<Map> findTuiAnByCondition(Map map) {
		return MapUtil.turnKeyToLowerCaseOfList(uvFlowMapper
				.findTuiAnByCondition(map));
	}

	@Override
	public int countAllByConditionForCourt(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<UvFlow> findByFlowdefidForCourt(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	/**
	 * 判断是否加锁
	 * @param request
	 * @return
	 */
	public Object ajaxReturnLockUser(String flowdraftid,SystemUser user){
		Object resultval = -1;
		String suid = user.getUserid();
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("flowdraftid", flowdraftid);
		FlowBase flowBase = flowBaseService.getFlowBaseByFlowdraftid(paramMap);
		if(flowBase!=null){
			Short islocked = flowBase.getIslocked(); 
			String opname = flowBase.getOpname() == null ?"":flowBase.getOpname();
			String opid = flowBase.getOpid() == null?"":flowBase.getOpid();
			if(islocked == 1 && !suid.equals(opid)){
				resultval = opname;
			}
		}
		return resultval;
	}
	
	
	 /**
	 * 描述：判断是否加锁，
	 * @author YangZR 2015-03-03 
	 * @param flowdraftid
	 * @param user
	 * @return 返回JSONMessage，如果案件被其它用户加锁，则返回其它用户审批案件的相关信息，同时JSONMessage.status = 0,否则为1
	*/
	@Override
	public Object ajaxReturnLockUser2(String flowdraftids,SystemUser user){
		JSONMessage message = JSONMessage.newMessage();
		StringBuffer caseLockResultSb = new StringBuffer();
		
		String userid = user.getUserid();
		flowdraftids = StringNumberUtil.formatString(flowdraftids, ",");
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("flowdraftids", flowdraftids);
		
		Map<String,ArrayList<FlowBase>> caseLockMap = new HashMap<String,ArrayList<FlowBase>>();//用于存放被加锁、同时非当前用户能审批的案件
		List<FlowBase> flowBaseList = flowBaseService.getFlowBaseByFlowdraftid2(paramMap);
		if(null!=flowBaseList&&flowBaseList.size()>0){
			for(FlowBase fb:flowBaseList){
				if(fb!=null){
					Short islocked = fb.getIslocked(); 
					String opid = fb.getOpid() == null?"":fb.getOpid();
					//如果案件加锁，同时案件被其它用户审批，则做处理：将由相同审批人审批的案件归到同一个List当中
					if(islocked == 1 && !userid.equals(opid)){
						ArrayList<FlowBase> caseLockList = null;
						if(caseLockMap.containsKey(opid)){
							caseLockList = caseLockMap.get(opid);
						}else{
							caseLockList = new ArrayList<FlowBase>(); 
						}
						
						caseLockList.add(fb);
						if(null!=caseLockList && caseLockList.size()>0){
							caseLockMap.put(fb.getOpid(), caseLockList);
						}
						
					}
				}
			}
		}
		
		if(caseLockMap.size()>0){
			Set<String> set = caseLockMap.keySet();
			if(null!=set&&set.size()>0){
				for(String opid : set){
					String opname = "";//案件审批人
					
					StringBuffer caseApplynameSb = new StringBuffer();//审批人审批案件的申请人
					ArrayList<FlowBase> fbList = caseLockMap.get(opid);
					for(FlowBase fb : fbList){
						opname = fb.getOpname();
						caseApplynameSb.append(fb.getApplyname()).append(",");
					}
					
					//如果字符串以","结尾，则去掉","
					if(caseApplynameSb.lastIndexOf(",")==caseApplynameSb.length()-1){
						caseApplynameSb.substring(0, caseApplynameSb.length()-2);
					}
					
					//审批人审批的案件组织成字符串
					if(StringNumberUtil.notEmpty(opname)&&StringNumberUtil.notEmpty(caseApplynameSb.toString())){
						caseLockResultSb.append(caseApplynameSb.toString()).append("的案件正由").append(opname).append("审批！").append("");
					}
					
				}
			}
		}
		
		//返回值的操作
		if(StringNumberUtil.notEmpty(caseLockResultSb.toString())){
			message.setInfo(caseLockResultSb.toString());
		}else{
			message.setSuccess();
			message.setInfo("案件可以审批！");
		}
		
		return message;
	}
	
	/**
	 * 根据权限控制表单节点的编辑
	 * @param flowdraftid
	 * @param user
	 * @return
	 */
	public List<Object> ajaxNotEditAttributeList(String departid, String flowdefid, String resnodeid, String tempid){
		List<Object> list = new ArrayList<Object>();
		Map<String,String> map = new HashMap<String, String>();
		if(!StringNumberUtil.isEmpty(flowdefid)){//有流程的获取可编辑的节点
			map.put("departid", departid);
			map.put("id", flowdefid);
			map.put("snodeid", resnodeid);
			List<FlowDeliver> deliverlist = flowDeliverService.findByFlowdefid(map);
			//text1 有问题，控制不住 节点是否审批
			for(FlowDeliver deliver:deliverlist){
				String colname1 = deliver.getText1();//审批的意见节点
				String colname2 = deliver.getText2();
				
				//意见对应字段
				if(!StringNumberUtil.isNullOrEmpty(colname1) 
				&& list.indexOf(colname1)== -1) list.add(colname1);
				
				//流程审批时需要编辑的节点（除意见栏外）
				if(!StringNumberUtil.isNullOrEmpty(colname1)){
					if(colname2.contains(",")){
						String[] _arr = colname2.split(",");
						for(String co:_arr){
							if(!StringNumberUtil.isNullOrEmpty(co) && list.indexOf(co) == -1) list.add(co);
						}
					}else{
						if(list.indexOf(colname2)== -1) list.add(colname2);
					}
				}
			}
		}else{//无流程的获取不可编辑的节点
			String uneditedfields = "";
			TbsysTemplate template = systemModelService.getTemplateAndDepartid(tempid, departid);
			//审批的意见节点
			if (template != null) uneditedfields = template.getUneditedfields();
			if(!StringNumberUtil.isNullOrEmpty(uneditedfields)){
				if(uneditedfields.contains(",")){
					String[] _arr = uneditedfields.split(",");
					for(String co:_arr){
						if(!StringNumberUtil.isNullOrEmpty(co) && list.indexOf(co) == -1) list.add(co);
					}
				}else{
					if(list.indexOf(uneditedfields)== -1) list.add(uneditedfields);
				}
			}
		}
		return list;
	}
	public List<Map> selectFlowForScar(Map<String, Object> paraMap){
		List<Map> listmap = new ArrayList<Map>();
		List<Map> list = flowMapper.selectFlowForScar(paraMap);
		if(list!=null && list.size()>0){
			for(Object obj:list){
				Map tempmap = (Map)obj;
				tempmap =MapUtil.turnKeyToLowerCase(tempmap);
				listmap.add(tempmap);
			}
		}
		return listmap;
	}

	@Override
	@Transactional
	public Object ajaxGetFlowOpinionColumn(Map<String, Object> paraMap) {
		Object resultval = -1;
		int state = Integer.parseInt(GkzxCommon.ZERO);
		try{
			String operateType = StringNumberUtil.returnString2(paraMap.get("operateType"));
			if(operateType.equalsIgnoreCase("back")) state=2;
			if(operateType.equalsIgnoreCase("no")) state=-1;
			if(operateType.equalsIgnoreCase("end")) state=1;
			paraMap.put("state", String.valueOf(state));
			if(!operateType.equalsIgnoreCase("save")){
				FlowDeliver deliver = flowDeliverMapper.findByParamMap(paraMap);
				if(deliver!=null){
					resultval = StringNumberUtil.returnString2(deliver.getText1());
					if(StringNumberUtil.notEmpty(deliver.getText4())){
						resultval =resultval+"@"+StringNumberUtil.returnString2(deliver.getText4());
					}
					if(StringNumberUtil.isEmpty(resultval)) {
						resultval= -1;
					}
				}	
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return resultval;
	}
	@Override
	@Transactional
	public Object rollBack(Map<String, Object> paraMap) {
		Object rows = -1;
		int state = Integer.parseInt(GkzxCommon.ZERO);
		try{
			String ids = StringNumberUtil.returnString2(paraMap.get("ids"));
			if(StringNumberUtil.notEmpty(ids)){
				String[] arry = ids.split(",");
				for(String obj:arry){
					//删除拒绝的流水数据 tbflow表
					Flow flow = new Flow();
					rows = flowMapper.delete(obj);
					flow.setFlowdraftid(obj);
					flow.setState(Short.valueOf("-1"));
					//更新流水数据 tbflow表state状态
					rows = flowMapper.updateByFlowdraftid(flow);
					//更新流程基本表text25状态
					FlowBase base = new FlowBase();
					base.setFlowdraftid(obj);
					base.setText25(GkzxCommon.ZERO);
					rows = flowBaseMapper.updateSensitiveByFlowdraftid(base);
				}
			}
			
		}catch(Exception e){
			
		}
		return rows;
	}

	@Override
	public List<Map> getJianChaYuanopinionsData(Map map) {
		return MapUtil.turnKeyToLowerCaseOfList(uvFlowMapper.getJianChaYuanopinionsData(map));
	}

	@Override
	public int getJianChaYuanopinionsDataCount(Map map) {
		return uvFlowMapper.getJianChaYuanopinionsDataCount(map);
	}

	@Override
	public Map findflowByflowdefid(Map map) {
		return MapUtil.turnKeyToLowerCase(flowMapper.findflowByflowdefid(map));
	}

	@Override
	@Transactional
	public String saveFormBySolutionID(Map<String,Object> paramMap, SystemUser user){
		String returnvalue = "success";
		//将key为aipFileStr转为查询方案操作大字段的统一用的doc_content
		paramMap.put("doc_content", paramMap.get("aipFileStr"));
		paramMap.remove("aipFileStr");
		solutionService.save(paramMap, user);
		return returnvalue;
	}
	
	@Override
	public Map searchChuJianDateByFlowdraftID(Map<String, Object> map) {
		return uvFlowMapper.searchChuJianDateByFlowdraftID(map);
	}
	
	@Override
	public Object getUvFlowDataByParam(Map<String,Object> map){
		return uvFlowMapper.getUvFlowDataByParam(map);
	}
	
	@Override
	public UvFlow getFlowByFlowdraftid(String flowdraftid){
		return uvFlowMapper.getFlowByFlowdraftid(flowdraftid);
	}
	@Override
	public Map isLookTab(HttpServletRequest request) {
		String crimid = request.getParameter("applyid");
		Map maps = new HashMap();
		maps.put("crimid", crimid);
		return uvFlowMapper.isLookTab(maps);
	}
	
	@Override
	public Object submitBaseInfo(Map<String,Object> map) {
		
		JSONMessage message = JSONMessage.newMessage();
		message.setStatus(1);
		
		String ids = StringNumberUtil.getStrFromMap("ids", map);
		if(StringNumberUtil.notEmpty(ids)){
			
			Map<String,Object> paramap = new HashMap<String,Object>();
			StringBuilder sb = new StringBuilder("");
			paramap.put("flowdefid", map.get("flowdefid"));
			String[] idArr = ids.split(",");
			for(String id : idArr){
				String crimid = id.split("@")[0];
				String name = id.split("@")[1];
				paramap.put("crimid", crimid);
				
				Map<String,Object> object = MapUtil.convertKey2LowerCase(uvFlowMapper.submitBaseInfo(paramap));
				if(null == object || object.get("currnodeid").equals("-1")){
					sb.append(name).append("，");
				}
			}
			
			if(StringNumberUtil.notEmpty(sb.toString())){
				message.setStatus(0);
				message.setInfo(StringNumberUtil.removeLastStr(sb.toString(), "，"));
			}
		}
		return message;
		
//		Map<String,Object> object = uvFlowMapper.submitBaseInfo(map);
//		//如果流程表中查询不出来数据 那么 就给需要判断的字段赋值为0
//		if (object == null) {
//			object = new HashMap();
//			object.put("NODEID", 0);
//		}
//		return object.get("NODEID");
		
	}
	
	
	@Override
	public Object isDealJxjsCase(Map<String,Object> map) {
		
		
		Map<String,Object> object = MapUtil.convertKey2LowerCase(uvFlowMapper.isDealJxjsCase(map));
		//如果流程表中查询不出来数据 那么 就给需要判断的字段赋值为0
		if (object == null) {
			object = new HashMap();
			object.put("nodeid", 0);
		}
		return object.get("nodeid");
		
	}
	
	public Object submitSentchange(Map<String,Object> map){
		
		
		JSONMessage message = JSONMessage.newMessage();
		message.setStatus(1);
		
		String ids = StringNumberUtil.getStrFromMap("ids", map);
		if(StringNumberUtil.notEmpty(ids)){
			
			Map<String,Object> paramap = new HashMap<String,Object>();
			StringBuilder sb = new StringBuilder("");
			String[] idArr = ids.split(",");
			for(String id : idArr){
				String crimid = id.split("@")[0];
				String name = id.split("@")[1];
				paramap.put("crimid", crimid);
				
				Map<String,Object> object = MapUtil.convertKey2LowerCase(uvFlowMapper.submitSentchange(paramap));
				
				if(null != object){//
					int value = StringNumberUtil.parseInt(object.get("xf_data"), 0);
					if(value > 0){
						sb.append(name).append("有").append(value).append("份刑罚变动未提交").append("，");
					}
					
				}
			}
			
			if(StringNumberUtil.notEmpty(sb.toString())){
				message.setStatus(0);
				message.setInfo(StringNumberUtil.removeLastStr(sb.toString(), "，"));
			}
		}
		return message;
		
		
		
		
//		String value = "";
//		Map object = uvFlowMapper.submitSentchange(map);
//		int count1 = Integer.parseInt(object.get("COUNT1").toString());
//		int count2 = Integer.parseInt(object.get("COUNT2").toString());
//		
//		if (count1 == count2) {
//			value = "";
//		}else{
//			value = object.get("NODEID").toString();
//		}
//		
//		return value;
	}
	
	@Override
	public String getCurrnodeidByFlowdraftid(String flowdraftid){
		UvFlow uvflow = null;
		if(StringNumberUtil.notEmpty(flowdraftid)){
			uvflow = getFlowByFlowdraftid(flowdraftid);
		}
		String currnodeid = uvflow==null?"0":uvflow.getNodeid();//当前操作人的节点ID
		return currnodeid;
	}
	
	@Override
	public int isRepeatCaseNum(Map map) {
		return uvFlowMapper.isRepeatCaseNum(map);
	}
	@Override
	public void baoWaiCheHui(String crimid) {
		// TODO Auto-generated method stub
		uvFlowMapper.baoWaiCheHui(crimid);
	}
	
	@Override
	public void baoWaiCheHui1(String flowdraftid) {
		// TODO Auto-generated method stub
		uvFlowMapper.baoWaiCheHui1(flowdraftid);
	}
	@Override
	public Object getUvFlowDataByParam2(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return uvFlowMapper.getUvFlowDataByParam2(map);
	}
	
	
	
}