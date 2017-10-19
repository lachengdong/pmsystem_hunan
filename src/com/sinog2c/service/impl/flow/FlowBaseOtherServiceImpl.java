package com.sinog2c.service.impl.flow;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.gkzx.common.GkzxCommon;
import com.sinog2c.dao.api.flow.FlowArchivesSettingMapper;
import com.sinog2c.dao.api.flow.FlowBaseMapper;
import com.sinog2c.dao.api.flow.FlowBaseOtherMapper;
import com.sinog2c.dao.api.flow.FlowOtherFlowMapper;
import com.sinog2c.model.flow.FlowArchivesExist;
import com.sinog2c.model.flow.FlowArchivesSetting;
import com.sinog2c.model.flow.FlowBase;
import com.sinog2c.model.flow.FlowBaseOther;
import com.sinog2c.model.flow.FlowOtherFlow;
import com.sinog2c.model.system.SystemOrganization;
import com.sinog2c.model.system.SystemUser;
import com.sinog2c.service.api.common.CommonFormService;
import com.sinog2c.service.api.common.CommonSQLSolutionService;
import com.sinog2c.service.api.flow.FlowArchivesExistService;
import com.sinog2c.service.api.flow.FlowArchivesService;
import com.sinog2c.service.api.flow.FlowBaseOtherService;
import com.sinog2c.service.api.flow.FlowBaseService;
import com.sinog2c.service.impl.base.ServiceImplBase;
import com.sinog2c.util.common.MapUtil;
import com.sinog2c.util.common.StringNumberUtil;

@Service("flowBaseOtherService")
public class FlowBaseOtherServiceImpl extends ServiceImplBase implements FlowBaseOtherService{

	@Autowired
    private FlowBaseOtherMapper flowBaseOtherMapper;
	@Autowired
	private FlowOtherFlowMapper flowOtherFlowMapper;
	@Autowired
	private FlowBaseMapper flowBaseMapper;
	@Autowired
	private FlowArchivesSettingMapper flowArchivesSettingMapper;
	@Autowired
	private FlowBaseService flowBaseService;
	@Autowired(required=true)
	private CommonSQLSolutionService solutionService;
	@Autowired
	private CommonFormService commonFormService;
	@Autowired
	private FlowArchivesService flowArchivesService;
	@Resource
	private FlowArchivesExistService flowArchivesExistService;

	public FlowBaseOtherMapper getFlowBaseOtherMapper() {
		return flowBaseOtherMapper;
	}

	public void setFlowBaseOtherMapper(FlowBaseOtherMapper flowBaseOtherMapper) {
		this.flowBaseOtherMapper = flowBaseOtherMapper;
	}
	
	public FlowOtherFlowMapper getFlowOtherFlowMapper() {
		return flowOtherFlowMapper;
	}
	
	public void setFlowOtherFlowMapper(FlowOtherFlowMapper flowOtherFlowMapper) {
		this.flowOtherFlowMapper = flowOtherFlowMapper;
	}

	public int countAll() {
        return this.flowBaseOtherMapper.countAll();
    }
	
	public String getOtherId(String departid) {
		return this.flowBaseOtherMapper.getOtherId(departid);
	}

    public FlowBaseOther findById(String id) {
        return this.flowBaseOtherMapper.findById(id);
    }
    @Transactional
    public int insert(FlowBaseOther flowBaseOther) {
        return this.flowBaseOtherMapper.insert(flowBaseOther);
    }
    @Transactional
    public int delete(String id) {
    	return this.flowBaseOtherMapper.delete(id);
    }

    public List<FlowBaseOther> selectAll() {
        return this.flowBaseOtherMapper.selectAll();
    }
    @Transactional
    public int updateByCondition(HashMap<Object,Object> map){
		return this.flowBaseOtherMapper.updateByCondition(map);
    }

	@Override
	public FlowBaseOther findByFlowdraftId(String flowdraftid) {
		return this.flowBaseOtherMapper.findByFlowdraftId(flowdraftid);
	}
	
	/**
     * 根据模板ID查询数据
     */
    public FlowBaseOther getMeetingRecord(Map meetMap){
    	return flowBaseOtherMapper.getMeetingRecordByTempid(meetMap);
    }
   
    public FlowBaseOther getMeetingRecordById(String otherid){
    	if(StringNumberUtil.notEmpty(otherid)){
    		return flowBaseOtherMapper.findById(otherid);
    	}else{
    		return null;
    	}
    	
    }
    
    
    public String getDocconentByCondition(Map map){
    	return flowBaseOtherMapper.getDocconentByCondition(map);
    }
    
    @Override
    public String getDocconentByFlowdraftid(Map map){
    	return flowBaseOtherMapper.getDocconentByFlowdraftid(map);
    }
    
    @Override
    public FlowBaseOther getLastDocconentByFlowdraftid(Map map){
    	return flowBaseOtherMapper.getLastDocconentByFlowdraftid(map);
    }

	@Override
	public FlowBaseOther selectByCpdnumber(HashMap map) {
		return this.flowBaseOtherMapper.selectByCpdnumber(map);
	}
	
	@Override
	public List<Map> selectCpdnumbersByCpdnumber(Map map) {
		return MapUtil.turnKeyToLowerCaseOfList(flowBaseOtherMapper.selectCpdnumbersByCpdnumber(map));
	}

	@Override
	public int updateByCpdnumber(FlowBaseOther other) {
		String otherid = this.flowBaseOtherMapper.getOtherIdByOther(other);
		other.setOtherid(otherid);
		return this.flowBaseOtherMapper.updateMeetContextByOhterid(other);
	}
	@Override
	public int updateMeetContextByOhterid(FlowBaseOther other){
		return this.flowBaseOtherMapper.updateMeetContextByOhterid(other);
	}
	
	@Override
	public int updateMeetContextByOhterid_nx(FlowBaseOther other){
		return this.flowBaseOtherMapper.updateMeetContextByOhterid_nx(other);
	}
	
	@Override
	public String getDocconentByMap(Map map){
		return this.flowBaseOtherMapper.getDocconentByMap(map);
	}
	@Override
	public Map getFlowBaseOtherDataByMap(Map map){
		return MapUtil.turnKeyToLowerCase(flowBaseOtherMapper.getFlowBaseOtherDataByMap(map));
	}
	
	@Override
	public int updateByOtherid(Map map){
		return flowBaseOtherMapper.updateByOtherid(map);
	}
	
	@Override
	public String getDocconentByConditionTwo(Map map){
		return flowBaseOtherMapper.getDocconentByConditionTwo(map);
	}
	
	@Override
	public String getJXJSBigDataImpl(Map map){
		return flowBaseOtherMapper.getJXJSBigDataMapper(map);
	}
	
	public List<Map> getMaxProgress(Map map){
		return flowBaseOtherMapper.getMaxProgressMapper(map);
	}
	
	@Override
	public String getDocconentOfNewestFuYiData(Map map){
		return flowBaseOtherMapper.getDocconentOfNewestFuYiData(map);
	}
	
	@Override
	public String getOtheridByFlowdraftid(Map map){
		return flowBaseOtherMapper.getOtheridByFlowdraftid(map);
	}
	
	@Override
	public String getLastOtheridByFlowdraftid(Map map){
		return flowBaseOtherMapper.getLastOtheridByFlowdraftid(map);
	}
	
	@Override
	public String courtGetJailApproveDocByMap(Map map){
		return flowBaseOtherMapper.courtGetJailApproveDocByMap(map);
	}
	 /**
     * 根据Otherid查询数据
     */
	@Override
	public FlowBaseOther findByOtherid(String otherid){
		return flowBaseOtherMapper.findByOtherid(otherid);
	}

	@Override
	public List<Map> getJianChaData(Map map) {
		
		return flowBaseOtherMapper.getJianChaData(map);
	}

	@Override
	public int getJianChaDatas(Map map) {
		return flowBaseOtherMapper.getJianChaDatas(map);
	}

	@Override
	public List<Map> getJianYuName(String str) {
		return flowBaseOtherMapper.getJianYuName(str);
	}
	
	@Override
	public int countCriminalCourtHandleCaseList(Map map) {
		return flowBaseOtherMapper.countCriminalCourtHandleCaseList(map);
	}
	
	@Override
	public List<Map> getCriminalCourtHandleCaseList(Map map){
		return MapUtil.turnKeyToLowerCaseOfList(flowBaseOtherMapper.getCriminalCourtHandleCaseList(map));
	}
	
	@Override
	@Transactional
	@SuppressWarnings("all")
	public String saveSuggestion(SystemUser user,String bigdata,String tempid,String crimid,
			String flowdraftid,String flowid,String otherid,String caseNumber,String suggesttime){
		
		Map param = new HashMap();
		param.put("tempid", tempid);
		if(StringNumberUtil.notEmpty(flowdraftid)&&StringNumberUtil.isEmpty(otherid)){
			param.put("flowdraftid", (flowdraftid));
			otherid = flowBaseOtherMapper.getLastOtheridByFlowdraftid(param);
		}
		
		int row = 0;
		String lv = user.getOrganization().getUnitlevel();
		if(StringNumberUtil.notEmpty(otherid)){
			char[] c = bigdata.toCharArray();
			Map map = new HashMap();
			map.put("docconent", new String(c));
			map.put("otherid", (otherid));
			map.put("opid", user.getUserid());
			map.put("optime", new Date());
			map.put("text5", suggesttime);//上海要求可修改建议书显示时间
			//山西 监督意见书，需要把案件号保存到表中
			map.put("int1", caseNumber);
			row = flowBaseOtherMapper.updateByOtherid(map);
			if(row<=0){
				throw new RuntimeException();
			}
			
			//更新other关系表
			FlowOtherFlow flowOtherFlow = new FlowOtherFlow();
			flowOtherFlow.setOtherid(otherid);
			flowOtherFlow.setFlowdraftid(flowdraftid);
			if(StringNumberUtil.notEmpty(flowid)){
				flowOtherFlow.setFlowid(flowid);
			}
			flowOtherFlow.setOpid(user.getUserid());
			flowOtherFlow.setOptime(new Date());
			flowOtherFlow.setTempid(tempid);
			row = flowOtherFlowMapper.updateByCondition2(flowOtherFlow);
			if(row<=0){
				throw new RuntimeException();
			}else{
				if(lv!=null && ("6".equals(lv)||"7".equals(lv)) && tempid.startsWith("10161report")){//是法院的且保存为合议笔录的才更新
					FlowBase fb = new FlowBase();
					fb.setFlowdraftid(flowdraftid);
					fb.setText23("5");//已合议
					flowBaseMapper.updateSensitiveByFlowdraftid(fb);
				}
			}
			
		}else{
			FlowOtherFlow flowOtherFlow = new FlowOtherFlow();
			FlowBaseOther flowBaseOther = new FlowBaseOther();
			
			String otheridInt = flowBaseOtherMapper.getOtherId(user.getDepartid());
			flowBaseOther.setOtherid(otheridInt);
			flowBaseOther.setDocconent(bigdata);
			flowBaseOther.setSn(Integer.valueOf(GkzxCommon.ONE));
			flowBaseOther.setOpid(user.getUserid());
			flowBaseOther.setOptime(new Date());
			flowBaseOther.setText5(suggesttime);//上海要求可修改建议书显示时间
			//山西 监督意见书，需要把案件号保存到表中
			if (caseNumber != null && !"".equals(caseNumber)) {
				flowBaseOther.setInt1(Integer.parseInt(caseNumber));
			}
			row = flowBaseOtherMapper.insert(flowBaseOther);
			if(row<=0){
				throw new RuntimeException();
			}
			//保存流程与和流程相关的其他文档关系信息表
			flowOtherFlow.setOtherid(otheridInt);
			if(StringNumberUtil.notEmpty(flowid)){
				flowOtherFlow.setFlowid(flowid);
			}
			flowOtherFlow.setTempid(tempid);
			flowOtherFlow.setOpid(user.getUserid());
			
			flowOtherFlow.setFlowdraftid(flowdraftid);
			
			flowOtherFlow.setOptime(new Date());
			row = flowOtherFlowMapper.insert(flowOtherFlow);
			if(row<=0){
				throw new RuntimeException();
			}else{
				if(lv!=null && ("6".equals(lv)||"7".equals(lv)) && tempid.startsWith("10161report")){
					FlowBase fb = new FlowBase();
					fb.setFlowdraftid(flowdraftid);
					fb.setText23("5");//已合议
					flowBaseMapper.updateSensitiveByFlowdraftid(fb);
				}
			}
		}
		return String.valueOf(row);
		
	}
	
	@Override
	public String getDocconentByOtherid(Map map){
		return flowBaseOtherMapper.getDocconentByOtherid(map);
	}

	@Override
	public FlowBaseOther getCaseDocconentByApplyid(String tempid,String applyid,String flowdefid) {
		return flowBaseOtherMapper.getCaseDocconentByApplyid(tempid,applyid,flowdefid);
	}

	@Override
	public String getSuggestDocconent(Map map) {
		return flowBaseOtherMapper.getSuggestDocconent(map);
	}
	@Override
	public String getDocconent4other(Map map) {
		return flowBaseOtherMapper.getDocconent4other(map);
	}
	/**
	 * 方法描述：省局办案时查询监狱审核表内容
	 * @param map
	 * @author zhenghui
	 */
	@Override
	public String getDocconent5other(Map map) {
		return flowBaseOtherMapper.getDocconent5other(map);
	}
    @Override
	public Map getJCYCaseNumber(Map map) {
		// TODO Auto-generated method stub
		return flowBaseOtherMapper.getJCYCaseNumber(map);
	}
	@Override
	public String saveCourtFlowOther(HttpServletRequest request) {
		String tempid = request.getParameter("tempid");
		String flowdraftid = request.getParameter("flowdraftid");
		Map param = new HashMap();
		param.put("tempid", tempid);
		String otherid = null;
		int row = 0;
		if(StringNumberUtil.notEmpty(flowdraftid)){
			SystemUser user = null;
			Object obj = request.getSession().getAttribute("session_user_key");
			if(obj instanceof SystemUser){
				user = (SystemUser)obj;
			}
			Map fbMap = new HashMap();
			fbMap.put("flowdraftid", (flowdraftid));
			FlowBase fb = this.flowBaseMapper.getFlowBaseByFlowdraftid(fbMap);
			param.put("flowdraftid", (flowdraftid));
			otherid = flowBaseOtherMapper.getLastOtheridByFlowdraftid(param);
			String bigdata = request.getParameter("data");
			if(StringNumberUtil.notEmpty(otherid)){
				char[] c = bigdata.toCharArray();
				
				Map map = new HashMap();
				map.put("docconent", new String(c));
				map.put("otherid", (otherid));
				map.put("opid", user.getUserid());
				map.put("optime", new Date());
				row = flowBaseOtherMapper.updateByOtherid(map);
				if(row<=0){
					throw new RuntimeException();
				}
				
				//更新other关系表
				FlowOtherFlow flowOtherFlow = new FlowOtherFlow();
				flowOtherFlow.setOtherid(otherid);
				flowOtherFlow.setFlowdraftid(flowdraftid);
				if(StringNumberUtil.notEmpty(fb.getFlowid())){
					flowOtherFlow.setFlowid(fb.getFlowid());
				}
				flowOtherFlow.setOpid(user.getUserid());
				flowOtherFlow.setOptime(new Date());
				flowOtherFlow.setTempid(tempid);
				row = flowOtherFlowMapper.updateByCondition2(flowOtherFlow);
				if(row<=0){
					throw new RuntimeException();
				}
				
			}else{
				FlowOtherFlow flowOtherFlow = new FlowOtherFlow();
				FlowBaseOther flowBaseOther = new FlowBaseOther();
				
				String otheridInt = flowBaseOtherMapper.getOtherId(user.getDepartid());
				flowBaseOther.setOtherid(otheridInt);
				flowBaseOther.setDocconent(bigdata);
				flowBaseOther.setSn(Integer.valueOf(GkzxCommon.ONE));
				flowBaseOther.setOpid(user.getUserid());
				flowBaseOther.setOptime(new Date());
				row = flowBaseOtherMapper.insert(flowBaseOther);
				if(row<=0){
					throw new RuntimeException();
				}
				//保存流程与和流程相关的其他文档关系信息表
				flowOtherFlow.setOtherid(otheridInt);
				if(StringNumberUtil.notEmpty(fb.getFlowid())){
					flowOtherFlow.setFlowid(fb.getFlowid());
				}
				flowOtherFlow.setTempid(tempid);
				flowOtherFlow.setOpid(user.getUserid());
				
				flowOtherFlow.setFlowdraftid(flowdraftid);
				
				flowOtherFlow.setOptime(new Date());
				row = flowOtherFlowMapper.insert(flowOtherFlow);
				if(row<=0){
					throw new RuntimeException();
				}
			}
		}
		String flag ="success";
		if(row<=0){
			flag = "error";
		}
		return flag;
	}

	@Override
	public String getAnJianBigData(Map<String, String> paramMap) {
		return flowOtherFlowMapper.getAnJianBigData(paramMap);
	}

	@Override
	@Transactional
	public String deleFyData(Map map) {
		String result="success";
		try{
			flowBaseMapper.deleteBaseOtherCase(map);
			flowBaseMapper.deleteOtherFlowCase(map);
			flowBaseMapper.deleteFlow(map);
			flowBaseMapper.deleteFlowBaseCaseByanhao(map);
			flowBaseMapper.deleteFlowBaseDoc(map);//doc表
			flowBaseMapper.deleteCourtFlowBaseInfo(map);//法院额外信息表
			flowBaseMapper.deleteCourtCaseFullCourt(map);//合议庭分配表
			flowBaseMapper.deleteCourtBiluInfo(map);//开庭笔录的开庭设置信息
		}catch(Exception e){
			result="error";
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public String saveSuggestion_nx(SystemUser user, String bigdata,
			String tempid, String crimid, String flowdraftid, String flowid,
			String otherid, String caseNumber) {
		Map param = new HashMap();
		param.put("tempid", tempid);
		if(StringNumberUtil.notEmpty(flowdraftid)&&StringNumberUtil.isEmpty(otherid)){
			param.put("flowdraftid", (flowdraftid));
			otherid = flowBaseOtherMapper.getLastOtheridByFlowdraftid(param);
		}
		
		int row = 0;
		String lv = user.getOrganization().getUnitlevel();
		if(StringNumberUtil.notEmpty(otherid)){
			char[] c = bigdata.toCharArray();
			Map map = new HashMap();
			map.put("docconent", new String(c));
			map.put("otherid", (otherid));
			map.put("opid", user.getUserid());
			map.put("optime", new Date());
			//山西 监督意见书，需要把案件号保存到表中
			map.put("int1", caseNumber);
			row = flowBaseOtherMapper.updateByOtherid(map);
			if(row<=0){
				throw new RuntimeException();
			}
			
			//更新other关系表
			FlowOtherFlow flowOtherFlow = new FlowOtherFlow();
			flowOtherFlow.setOtherid(otherid);
			flowOtherFlow.setFlowdraftid(flowdraftid);
			if(StringNumberUtil.notEmpty(flowid)){
				flowOtherFlow.setFlowid(flowid);
			}
			flowOtherFlow.setOpid(user.getUserid());
			flowOtherFlow.setOptime(new Date());
			flowOtherFlow.setTempid(tempid);
			row = flowOtherFlowMapper.updateByCondition2(flowOtherFlow);
			if(row<=0){
				throw new RuntimeException();
			}else{
				if(lv!=null && ("6".equals(lv)||"7".equals(lv))){
					FlowBase fb = new FlowBase();
					fb.setFlowdraftid(flowdraftid);
					fb.setText23("5");//已合议
					flowBaseMapper.updateSensitiveByFlowdraftid(fb);
				}
			}
			
		}else{
			FlowOtherFlow flowOtherFlow = new FlowOtherFlow();
			FlowBaseOther flowBaseOther = new FlowBaseOther();
			
			String otheridInt = flowBaseOtherMapper.getOtherId(user.getDepartid());
			flowBaseOther.setOtherid(otheridInt);
			flowBaseOther.setDocconent(bigdata);
			flowBaseOther.setSn(Integer.valueOf(GkzxCommon.ONE));
			flowBaseOther.setOpid(user.getUserid());
			flowBaseOther.setOptime(new Date());
			//山西 监督意见书，需要把案件号保存到表中
			if (caseNumber != null && !"".equals(caseNumber)) {
				flowBaseOther.setInt1(Integer.parseInt(caseNumber));
			}
			row = flowBaseOtherMapper.insert(flowBaseOther);
			if(row<=0){
				throw new RuntimeException();
			}
			//保存流程与和流程相关的其他文档关系信息表
			flowOtherFlow.setOtherid(otheridInt);
			if(StringNumberUtil.notEmpty(flowid)){
				flowOtherFlow.setFlowid(flowid);
			}
			flowOtherFlow.setTempid(tempid);
			flowOtherFlow.setOpid(user.getUserid());
			
			flowOtherFlow.setFlowdraftid(flowdraftid);
			
			flowOtherFlow.setOptime(new Date());
			row = flowOtherFlowMapper.insert(flowOtherFlow);
			if(row<=0){
				throw new RuntimeException();
			}else{
				otherid = ""+otheridInt;
				if(lv!=null && ("6".equals(lv)||"7".equals(lv))){
					FlowBase fb = new FlowBase();
					fb.setFlowdraftid(flowdraftid);
					fb.setText23("5");//已合议
					flowBaseMapper.updateSensitiveByFlowdraftid(fb);
				}
			}
		}
		return String.valueOf(otherid);
	}

	@Override
	public String getAnJianText(Map<String, String> paramMap) {
		return flowOtherFlowMapper.getAnJianText(paramMap);
	}

	@Override
	public FlowBaseOther selectByCpdnumber_nx(HashMap map) {
		return this.flowBaseOtherMapper.selectByCpdnumber_nx(map);
	}

	@Override
	public String getDocconentByFlowid(Map map) {
		return flowBaseOtherMapper.getDocconentByFlowid(map);
	}

	@Override
	public void updateSignatureProcess(Map paramMap) {
		flowBaseOtherMapper.updateSignatureProcess(paramMap);
	}
	
	/**
	 * @author YangZR
	 * @Date 2015-05-12
	 * @param paramap：包含departid, filetype checkclob=1, flowdraftids
	 * @param user
	 * @return
	 */
	@Override
	public Map<String,Object> checkCaseFilingInfo(Map<String,Object> paramap, SystemUser user){
		Map<String,Object> returnmap = new HashMap<String,Object>();
		StringBuffer notFitInfosSb = new StringBuffer(); //不符合归档的信息返回
		List<String> fitFlowdraftid = new ArrayList<String>();//符合归档的草稿id保留
		
		//查询需要
		String flowdraftids = paramap.get("flowdraftids").toString();
		if(StringNumberUtil.notEmpty(flowdraftids)){
			flowdraftids = flowdraftids.trim();
			
			List<FlowArchivesSetting> list = flowArchivesSettingMapper.selectByDepartidAndFileType(paramap);
			List<String> flowdraftidList = StringNumberUtil.formatString2List(flowdraftids, ",");
			for(String flowdraftid : flowdraftidList){
				paramap.put("flowdraftid", flowdraftid);
				FlowBase fb = flowBaseService.getFlowBaseByFlowdraftid(paramap);
				paramap.put("flowdefid", fb.getFlowdefid());
				paramap.put("applyid", fb.getApplyid());
				String applyname = fb.getApplyname();
				StringBuffer notFitInfoSb = new StringBuffer();
				//
				for(FlowArchivesSetting fas : list){
					paramap.put("tempid", fas.getTempid());
					paramap.put("solutionid", fas.getSolutionid());
					
					Map<String,Object> resultMap = solutionService.query(paramap, user);
					JSONArray docconent = commonFormService.parseFormDataOfSolution(resultMap);
					if(null == docconent || docconent.size()<=0){
						notFitInfoSb.append(applyname).append("的").append(fas.getTempname()).append("不存在").append("，");
					}
				}
				
				if(StringNumberUtil.notEmpty(notFitInfoSb)){
					notFitInfosSb.append(notFitInfoSb);
				}else{
					fitFlowdraftid.add(flowdraftid);
				}
			}
			
			returnmap.put("notFitInfos", notFitInfosSb.toString());
			returnmap.put("flowdraftids", fitFlowdraftid);
			
		}
		
		return returnmap;
		
	}

	@Override
	public List<Map<String, Object>> getCaseFilingAssembleData(Map<String, Object> paramap, SystemUser user){
		List<Map<String, Object>> returnList = new ArrayList<Map<String, Object>>();
		
		FlowBase fb = flowBaseService.getFlowBaseByFlowdraftid(paramap);
		paramap.put("flowdefid", fb.getFlowdefid());
		paramap.put("applyid", fb.getApplyid());
		paramap.put("applyname", fb.getApplyname());
		
		List<FlowArchivesSetting> list = flowArchivesSettingMapper.selectByDepartidAndFileType(paramap);
		
		for(FlowArchivesSetting fas : list){
			paramap.put("tempid", fas.getTempid());
			paramap.put("tempname", fas.getTempname());
			paramap.put("solutionid", fas.getSolutionid());
			paramap.put("codeid", fas.getCodeid());
			paramap.put("savetype", fas.getSavetype());
			paramap.put("checkclob", fas.getCheckclob());
			paramap.put("isassemble", fas.getIsassemble());
			paramap.put("filetype", fas.getFiletype());
			paramap.put("departid", fas.getDepartid());
			
			Map<String,Object> resultMap = solutionService.query(paramap, user);
			Map<String,Object> form = (Map<String,Object>)resultMap.get("form");
			Map<String,Object> map = (Map<String,Object>)resultMap.get("root");
			if(null != form){
				Map<String, Object> returnMap = new HashMap<String, Object>();
				returnMap.put("aipFileStr", form.get("doc_content"));
				if(null!=map){
					map = StringNumberUtil.dealMapForForm(map);
					returnMap.put("formdata", new JSONObject(map));
				}
				returnMap.putAll(paramap);
				
				returnList.add(returnMap);
			}
			
		}
		return returnList;
	}
	
	
	@Override
	@Transactional
	public Object caseFileWithData(Map<String,Object> paramap, List<Map<String,Object>> assembleDataList, SystemUser user){
		List<Map<String, Object>> caseFileDataList = new ArrayList<Map<String, Object>>();
		
		FlowBase fb = flowBaseService.getFlowBaseByFlowdraftid(paramap);
		paramap.put("flowdefid", fb.getFlowdefid());
		paramap.put("departid", user.getDepartid());
		paramap.put("applyid", fb.getApplyid());
		paramap.put("applyname", fb.getApplyname());
		
		List<FlowArchivesSetting> list = flowArchivesSettingMapper.selectByDepartidAndFileType(paramap);
		
		for(FlowArchivesSetting fas : list){
			paramap.put("tempid", fas.getTempid());
			paramap.put("tempname", fas.getTempname());
			paramap.put("solutionid", fas.getSolutionid());
			paramap.put("codeid", fas.getCodeid());
			paramap.put("savetype", fas.getSavetype());
			paramap.put("checkclob", fas.getCheckclob());
			paramap.put("isassemble", fas.getIsassemble());
			paramap.put("filetype", fas.getFiletype());
			paramap.put("departid", fas.getDepartid());
			
			Map<String,Object> resultMap = solutionService.query(paramap, user);
			Map<String,Object> form = (Map<String,Object>)resultMap.get("form");
//			Map<String,Object> map = (Map<String,Object>)resultMap.get("root");
			if(null != form){
				Map<String, Object> caseFileDataMap = new HashMap<String, Object>();
				caseFileDataMap.put("aipFileStr", form.get("doc_content"));
//				if(null!=map){
//					returnMap.put("formdata", new JSONObject(map));
//				}
				caseFileDataMap.putAll(paramap);
				flowArchivesService.fileCaseData(caseFileDataMap, user);
//				caseFileDataList.add(caseFileDataMap);
			}
		}
		if(null != assembleDataList && assembleDataList.size()>0){
			for(Map assembleData : assembleDataList){
				flowArchivesService.fileCaseData(assembleData, user);
			}
//			caseFileDataList.addAll(assembleDataList);
		}
		
		//更新
		FlowArchivesExist flowArchivesExist = new FlowArchivesExist();
		
		//插入
		flowArchivesExist.setFlowdraftid(paramap.get("flowdraftid").toString());
		flowArchivesExist.setState(1);
		flowArchivesExist.setStarttime(new Date());
		flowArchivesExist.setEndtime(new Date());
		flowArchivesExistService.insertSelective(flowArchivesExist);
		
		return null;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Map identificationOfDisease(Map map) {
		return MapUtil.convertKey2LowerCase(flowOtherFlowMapper.identificationOfDisease(map));
	}

	
	/**
	 * 自动案件归档
	 */
	@Override
	@Transactional
	public void autoExecuteCaseFile(){
		Map<String,Object> paramap = new HashMap<String,Object>();
		paramap.put("int1", 1);
		//查询流程,获取流程flowdraftid的集合
		List<Map<String,Object>> flowdraftidMap = MapUtil.convertKeyList2LowerCase(flowBaseMapper.getUnDealFlowdraftids(paramap));
		
		if(null != flowdraftidMap && flowdraftidMap.size() >0){
			
			SystemUser user = new SystemUser();
			user.setOpid("-1");
			user.setUserid("-1");
			
			for(Map<String,Object> map : flowdraftidMap){
				Object flowdraftidObj = map.get("flowdraftid");
				if(null != flowdraftidObj){
					//
					String departid = map.get("departid").toString();
					user.setDepartid(departid);
					SystemOrganization org = new SystemOrganization();
					org.setOrgid(departid);
					user.setOrganization(org);
					
					String flowdraftid = flowdraftidObj.toString();
					paramap.put("flowdraftid", flowdraftid);
					
					caseFileWithData(paramap, null, user);
					
				}
			}
			
		}
		
	}

	@Override
	public String getDocconent(Map map) {
		return flowArchivesService.getDocconent(map);
	}
	
}