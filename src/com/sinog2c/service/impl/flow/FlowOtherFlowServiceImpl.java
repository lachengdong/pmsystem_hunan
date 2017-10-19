package com.sinog2c.service.impl.flow;

import java.sql.Clob;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gkzx.common.GkzxCommon;
import com.sinog2c.dao.api.commutationParole.TbxfCommuteParoleBatchMapper;
import com.sinog2c.dao.api.flow.FlowBaseOtherMapper;
import com.sinog2c.dao.api.flow.FlowOtherFlowMapper;
import com.sinog2c.dao.api.system.SystemTemplateMapper;
import com.sinog2c.model.flow.FlowBaseOther;
import com.sinog2c.model.flow.FlowOtherFlow;
import com.sinog2c.model.system.SystemUser;
import com.sinog2c.model.system.TbsysTemplate;
import com.sinog2c.service.api.flow.FlowOtherFlowService;
import com.sinog2c.service.api.system.SystemModelService;
import com.sinog2c.service.api.system.SystemResourceService;
import com.sinog2c.service.impl.base.ServiceImplBase;
import com.sinog2c.service.impl.system.SystemResourceServiceImpl;
import com.sinog2c.util.common.JsonUtil;
import com.sinog2c.util.common.MapUtil;
import com.sinog2c.util.common.StringNumberUtil;

@Service("flowOtherFlowService")
public class FlowOtherFlowServiceImpl extends ServiceImplBase implements FlowOtherFlowService{

	@Autowired
    private FlowOtherFlowMapper flowOtherFlowMapper;
	public FlowOtherFlowMapper getFlowOtherFlowMapper() {
		return flowOtherFlowMapper;
	}
	public void setFlowOtherFlowMapper(FlowOtherFlowMapper flowOtherFlowMapper) {
		this.flowOtherFlowMapper = flowOtherFlowMapper;
	}
	@Autowired
	private SystemModelService systemModelService;
	
	public SystemModelService getSystemModelService() {
		return systemModelService;
	}
	public void setSystemModelService(SystemModelService systemModelService) {
		this.systemModelService = systemModelService;
	}
	@Autowired
    private TbxfCommuteParoleBatchMapper tbxfCommuteParoleBatchMapper;
	public TbxfCommuteParoleBatchMapper getTbxfCommuteParoleBatchMapper() {
		return tbxfCommuteParoleBatchMapper;
	}
	public void setTbxfCommuteParoleBatchMapper(TbxfCommuteParoleBatchMapper tbxfCommuteParoleBatchMapper) {
		this.tbxfCommuteParoleBatchMapper = tbxfCommuteParoleBatchMapper;
	} 
	
	@Autowired
    private SystemTemplateMapper systemTemplateMapper;
	public SystemTemplateMapper getSystemTemplateMapper() {
		return systemTemplateMapper;
	}
	public void setSystemTemplateMapper(SystemTemplateMapper systemTemplateMapper) {
		this.systemTemplateMapper = systemTemplateMapper;
	}

	@Autowired
    private FlowBaseOtherMapper flowBaseOtherMapper;
	public FlowBaseOtherMapper getFlowBaseOtherMapper() {
		return flowBaseOtherMapper;
	}
	public void setFlowBaseOtherMapper(FlowBaseOtherMapper flowBaseOtherMapper) {
		this.flowBaseOtherMapper = flowBaseOtherMapper;
	}

	public int countAll() {
        return this.flowOtherFlowMapper.countAll();
    }

    public FlowOtherFlow findById(String id) {
        return this.flowOtherFlowMapper.findById(id);
    }
    @Transactional
    public int insert(FlowOtherFlow flowOtherFlow) {
        return this.flowOtherFlowMapper.insert(flowOtherFlow);
    }

    public List<FlowOtherFlow> selectAll() {
        return this.flowOtherFlowMapper.selectAll();
    }
    /**
	*保存会议记录模版大字段、报表
	*/
    @SuppressWarnings("unchecked")
	@Transactional
    public String saveOrUpdateMeetingRecord(HashMap map,SystemUser user){
    	Timestamp ts = new Timestamp(System.currentTimeMillis()); 
    	String tempid = map.get("tempid")==null?"":map.get("tempid").toString();
    	String flowid = map.get("flowid")==null?"":map.get("flowid").toString();
    	String status = map.get("status")==null?"":map.get("status").toString();
    	String otherid = map.get("otherid")==null?"":map.get("otherid").toString();
    	String departid = map.get("departid")==null?"":map.get("departid").toString();
    	String annexcontent = map.get("annexcontent")==null?"":map.get("annexcontent").toString();
    	String bworjx = map.get("bworjx")==null?"":map.get("bworjx").toString();
    	
    	String curyear = map.get("curyear")==null?"":map.get("curyear").toString();
		String batch = map.get("batch")==null?"":map.get("batch").toString();
		TbsysTemplate template = systemModelService.getTemplateAndDepartid(tempid,user.getDepartid());
		String meetingDesc = curyear+"年第"+batch+"批次"+template.getTempname();//会议描述
		String contenttype = map.get("contenttype")==null?"":map.get("contenttype").toString();
		
    	String opid = map.get("opid")==null?"":map.get("opid").toString();
    	String orgid = map.get("orgid")==null?"":map.get("orgid").toString();
    	
    	String flowdraftids = map.get("flowdraftids")==null?"":map.get("flowdraftids").toString();
		String[] arr = flowdraftids.split(",");
    	//如果arr长度小于0，保存报表,tempid的值替换为文本大字段的otherid
//    	if(!StringNumberUtil.notEmpty(flowdraftids)){
//    		tempid = otherid;
//    		meetingDesc = template.getTempname()+"报表";//描述
//    	}
//    	String sql = "select SEQ_BASE_OTHER_ID.nextval from dual";
//		int id = flowOtherFlowMapper.getSequenceNextValue(sql);
		String id = flowBaseOtherMapper.getOtherId(user.getDepartid());
    	if(StringNumberUtil.notEmpty(status)&& GkzxCommon.NEW.equals(status)){
    		if(StringNumberUtil.notEmpty(flowdraftids)){
    			//循环插入 会议记录 对应的罪犯信息 内容
    			for(int i=0;i < arr.length;i++){
        			FlowOtherFlow fof = new FlowOtherFlow();
            		fof.setOtherid(id);
            		fof.setFlowdraftid(arr[i]);
            		fof.setFlowid(flowid);
            		fof.setTempid(tempid);
            		fof.setOpid(opid);
            		fof.setOptime(ts);
            		flowOtherFlowMapper.insert(fof);
        		}
    		}
    		//TBFLOW_BASE_OTHER存一条数据，与TBFLOW_OTHER_FLOW是"一对多关系"
    		FlowBaseOther fbo = new FlowBaseOther();
    		fbo.setOtherid(id);
    		fbo.setDocconent(annexcontent);
    		fbo.setSn(0);
    		fbo.setText1(curyear);
    		fbo.setText2(batch);
    		if(!StringNumberUtil.notEmpty(flowdraftids)) fbo.setText3(tempid);
    		if(contenttype!=null && !"".equals(contenttype)) {
    			fbo.setText3(contenttype);
    		} else {
    			fbo.setText3(GkzxCommon.MEETCONTENT);//会议记录标示
    		}
    		fbo.setText4(meetingDesc);//会议描述
    		fbo.setText5(template.getTempname());//会议类型
    		fbo.setText6(orgid);//部门编号
    		fbo.setInt1(1);//备用字段INT1如果为1 则为会议记录类型
    		fbo.setOptime(ts);
    		fbo.setOpid(opid);
    		flowBaseOtherMapper.insert(fbo);
    		otherid = id;//本次Id作为返回值返回
    	}else if(StringNumberUtil.notEmpty(status)&& GkzxCommon.EDIT.equals(status)) {
			FlowBaseOther fOther = new FlowBaseOther();
			fOther.setOtherid(otherid);
			fOther.setDocconent(annexcontent);
			int rows = flowBaseOtherMapper.updateMeetContextByOhterid(fOther);
		    id = otherid;//修改 返回当前 的otherid 
		}else if(StringNumberUtil.notEmpty(status)&& GkzxCommon.BIGTEXT.equals(status)&&StringNumberUtil.notEmpty(otherid)){
    		//不能 覆盖之前的数据
    		meetingDesc = user.getOrganization().getName()+""+GkzxCommon.MEETING;
    		String meetTitle = "";
    		//此处用汉字"监外执行"四个字 进行判断不太合适吧!：mushuhong
    		if(template.getTempname().contains("监外执行")){
    			 meetTitle = ""+curyear+GkzxCommon.LISTYEAR+batch+GkzxCommon.JIANWAI+meetingDesc;
    		}else{
    			 //根据bworjx参数的值进行判断 是保外的标题还是减刑的标题
    			 //bworjx=0代表保外，bworjx可以赋值不同的值 判断是减刑还是保外 @author :mushuhong
    			 if ("0".equals(bworjx)) {
    				 meetTitle = ""+curyear+GkzxCommon.LISTYEAR+batch+GkzxCommon.JIANWAI+meetingDesc;
				 }else {
					 meetTitle = ""+curyear+GkzxCommon.LISTYEAR+batch+GkzxCommon.LISTTIME+meetingDesc;
				 }
    		}
    		
    		FlowBaseOther fbo = new FlowBaseOther();
    		fbo.setText1(curyear);//年度
    		fbo.setText2(batch);//批次
    		fbo.setText5(meetingDesc);//(**部门)会议名称
    		fbo.setOtherid(id);//生成主键
    		fbo.setDocconent(annexcontent);//大字段
    		fbo.setOpid(opid);//用户名
    		fbo.setText6(orgid);//当前登录人的部门编号
    		fbo.setText3(GkzxCommon.MEETDOC);//标示 会议记录 大字段
    		fbo.setText4(meetTitle);
    		fbo.setInt2(otherid);
    		//获取 当前部门，当前批次 一共保存几份会议记录
    		Map curMap = new HashMap();
    		curMap.put("curyear", curyear);
    		curMap.put("batch", batch);
    		curMap.put("orgid", orgid);
    		curMap.put("text3", GkzxCommon.MEETDOC);
    		String sn = flowBaseOtherMapper.getMeetSn(curMap);
    		//如果 第一条那么 sn==null  sn=1
    		if (sn == null) {
				sn= "1";
			}
    		fbo.setSn(Integer.parseInt(sn));
    		flowBaseOtherMapper.saveMeetingContentBigText(fbo);
    	}
    	return id;
    }
    
    @Override
    public int  allCountByMapCondition(Map map){
    	return flowBaseOtherMapper.allCountByMapCondition(map);
    }
    @Override
	public List<Map> selectDataByMapCondition(Map map){
    	return MapUtil.turnKeyToLowerCaseOfList(flowBaseOtherMapper.selectDataByMapCondition(map));
    }
    
	@Override
    public int updateByCondition2(FlowOtherFlow fof){
    	return flowOtherFlowMapper.updateByCondition2(fof);
    }
	@Override
	public FlowOtherFlow findById2(Map<String, Object> map) {
		return flowOtherFlowMapper.findById2(map);
	}
	@Override
	public int juegeMeetWhetherExistDoc(String otherid) {
		List<Map> listMap = flowBaseOtherMapper.juegeMeetWhetherExistDoc(otherid);
		int count = 0;
		try {
			count=listMap.size();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return count;
	}
    public List<Map> priviewMeetDoc(String otherid){
    	
    	return flowBaseOtherMapper.priviewMeetDocMaper(otherid);
    }
	@Override
	public List<Map> getAllMeetByOrgid(Map map) {
		
		return flowBaseOtherMapper.getAllMeetByOrgid(map);
	}
	@Override
	public int isExistJys(String flowdraftid,String tempid) {
		return flowOtherFlowMapper.isExistJys(flowdraftid,tempid);
	}
	
	@Override
	@SuppressWarnings("all")
	public int judgeExistMeetByOrgid(HttpServletRequest request,SystemUser user) {
		int result = 0;
		try {
			Map map = new HashMap();
			String tempid = request.getParameter("tempid");
			map.put("orgid", user.getOrgid());
			map.put("tempid", tempid);
			map.put("sysdep", user.getDepartid());
			
			List<Map> list = flowOtherFlowMapper.judgeExistMeetByOrgid(map);
			result = list.size();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	@Override
	@SuppressWarnings("all")
	public Map<String,Object> judgeExistMeetRecordByUser(Map<String,Object> paramap){
		Map<String,Object> returnMap = new HashMap<String,Object>();
		int num = 0;
		List<Map<String,Object>> list = MapUtil.convertKeyList2LowerCase(flowOtherFlowMapper.judgeExistMeetRecordByUser(paramap));
		if(null != list && list.size()>0){
			num = list.size();
			Object flowdraftid = list.get(0).get("flowdraftid");
			returnMap.put("oldFlowdraftid", flowdraftid);
		}
		returnMap.put("num", num);
		return returnMap;
	}
	
	@Override
	@SuppressWarnings("all")
	public Map getMeetContent(HttpServletRequest request,SystemUser user) {
		Map map = new HashMap();
		//得到选择的罪犯的编号
		String crimids = request.getParameter("crimids")== null?"":request.getParameter("crimids");
		String tempid = request.getParameter("tempid");
		//资源id
		String resid = request.getParameter("menuid");
		SystemResourceService system = new SystemResourceServiceImpl();
		try {
			//如果选择罪犯 那么需要 组织新的会议记录
			if(!StringNumberUtil.isNullOrEmpty(crimids)){
				map.put("tempid", tempid);//模板类型
				map.put("crimids", crimids);//罪犯编号
				map.put("userid", user.getUserid());//用户编号
				map.put("departid", user.getDepartid());//单位编号
				map.put("resid", resid);
				//查询模板的内容
				List<Map> list = flowOtherFlowMapper.getMeetContent(map);
				//查询出 查询方案
				List<Map> replaceList = flowOtherFlowMapper.getMeetPlanSqlByResid(map);
				//取出 每一条 sql进行执行
				List<List<Map>> listMapData = new ArrayList<List<Map>>();
				//循环sql
				if (replaceList.size() > 0) {
					for (int i = 0; i < replaceList.size(); i++) {
						Map mapSql = replaceList.get(i);
						//得到 单条 sql语句
						Clob clob = (Clob) (mapSql.get("SQLTEXT")== null?"":mapSql.get("SQLTEXT"));
						try {
							String planSql = clob.getSubString(1,(int)clob.length());
							if (!StringNumberUtil.isNullOrEmpty(planSql)) {
								planSql = system.whereSql(user, planSql,request);
								mapSql.put("sql", planSql);
								List<Map> listData = flowOtherFlowMapper.getMeetReportDataByPlanSql(mapSql);
								listMapData.add(MapUtil.turnKeyToLowerCaseOfList(listData));
							}
						} catch (SQLException e) {
							e.printStackTrace();
						}
					}
				}
				if (list.size() > 0) {
					//参数用来 接收 结果内容
					map = list.get(0);
					Clob clob = (Clob)map.get("CONTENT");
					String content = clob.getSubString(1, (int)clob.length());
					//此处需要 对应 模板内容进行替换 
					map.clear();
					
					/**
					 * 会议记录内容，无非就是多条记录和单条记录
					 * 单条记录：直接循环map把模板中的 变量替换掉即可
					 * 多条记录：循环结果然后赋值给一个变量
					 * 转化为处理一条记录和单条记录
					 */
					if (listMapData.size() > 0) {
						for (int i = 0; i < listMapData.size(); i++) {
							List<Map> listMap = listMapData.get(i);
							//如果 结果是单条记录，那么直接循环map进行替换即可
							if (listMap.size() == 1) {
								Map map2 = listMap.get(0);
								Iterator iterator = map2.keySet().iterator();
								while(iterator.hasNext()){
									String key = iterator.next().toString();
									//替换内容
									content=content.replace("["+key+"]", map2.get(key).toString());
								}
							//如果是多条记录，那么先组织数据进行替换
							}else {
								StringBuffer buffer = new StringBuffer("");
								String key = "";
								for (int j = 0; j < listMap.size(); j++) {
									Map map3 = listMap.get(j);
									Iterator iterator = map3.keySet().iterator();
									while(iterator.hasNext()){
										key = iterator.next().toString();
										//进行数据的组织 当组织完成以后进行替换内容
										buffer.append(map3.get(key)+"\n");
									}
								}
								content=content.replace("["+key+"]", buffer);
							}
							
						}
					}
					List<Map<String, String>> tempList = new ArrayList<Map<String, String>>();
					Pattern pattern = Pattern.compile("\\[(.*?)\\]");// 获取中括号之间的内容
					Matcher matcher = pattern.matcher(content);
					while (matcher.find()) {
						Map<String, String> map1 = new HashMap<String, String>();
						map1.put("fieldname", matcher.group(1));
						tempList.add(map1);
					}
					for (int i = 0; i < tempList.size(); i++) {
						String con = "\\[" + ((Map) tempList.get(i)).get("fieldname")
								+ "\\]";
						content = content.replaceAll(con, " ");
					}
					map.put("annexcontent", content);
					map.put("otherid", 0);
				}
			}else{
				map.put("tempid", tempid);
				map.put("crimids", crimids);
				map.put("sysdep", user.getDepartid());
				map.put("orgid", user.getOrgid());
			    //未选择罪犯，那么就显示上一次保存的会议记录
				List<Map> list = flowOtherFlowMapper.judgeExistMeetByOrgid(map);
				Clob clob = (Clob) list.get(0).get("DOCCONENT");
				Object docconent = clob.getSubString(1, (int)clob.length());
				map.put("annexcontent", docconent);
				map.put("otherid", list.get(0).get("OTHERID"));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}
	@Override
	@SuppressWarnings("all")
	public Map queryMaxBatchMapper(SystemUser user,HttpServletRequest request) {
		Map map = new HashMap();
		try {
			map.put("sysdep", user.getDepartid());
			List<Map> list = flowOtherFlowMapper.queryMaxBatchMapper(map);
			if(list.size()  >0){
				request.setAttribute("curyear", list.get(0).get("CURYEAR"));
				request.setAttribute("batch", list.get(0).get("BATCH"));
				map.put("curyear", list.get(0).get("CURYEAR"));
				map.put("batch", list.get(0).get("BATCH"));
				map.put("batchid", list.get(0).get("BATCHID"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}
	@Override
	@SuppressWarnings("all")
	public String saveMeetContents(Map map) {
		int result1 = 0;
		int result2 = 0;
		String value = "0";//新增以后 返回id的值
		try {
			if ("new".equals(map.get("state"))) {
				//每次新增都需要获取一个新的otherid 
//				String sql = "select SEQ_BASE_OTHER_ID.nextval from dual";
//				int id = flowOtherFlowMapper.getSequenceNextValue(sql);
				String id = flowBaseOtherMapper.getOtherId((String)map.get("departid"));
				map.put("otherid", id);
				//新增 肯定选择罪犯，那么 toString() 不会出现空指针
				String flowdraftid = (String)map.get("flowdraftids");
				String[] flowdraftids = flowdraftid.split(",");
				for (int i = 0; i < flowdraftids.length; i++) {
					map.put("flowdraftid", flowdraftids[i]);
					result1 = flowOtherFlowMapper.saveMeetContentsOtherFlow(map);
				}
				result2 = flowOtherFlowMapper.saveMeetContentsBaseOther(map);
				value = id;
			}else if("edit".equals(map.get("state"))){
				
				result2 = flowOtherFlowMapper.updateMeetContentBase(map);
				value = map.get("otherid").toString();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return value;
	}

    /**
     * 根据罪犯编号和批次id 查询出对应的 考核内容
     * @version 2014年11月7日13:25:15
     */
	@SuppressWarnings("all")
	public List<Map> getPrisonErperManceImpl(SystemUser user,Map map,HttpServletRequest request) {
		List<Map> listMaps = new ArrayList<Map>();
		try {
			String crimid = request.getParameter("crimid");//罪犯编号
			
			//把参数封装集合中
			map.put("crimid", crimid);
			
			listMaps = flowOtherFlowMapper.getPrisonErperManceImplMapper(map);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return listMaps;
	}
	/**
	 * 方法描述：更新或者是插入罪犯对应的考核情况
	 * @version 2014年11月7日14:16:21
	 */
	@SuppressWarnings("all")
	public int caoZuoPrisonErperMance(SystemUser user,
			HttpServletRequest request) {
		int count = 0;
        try {
        	String state = request.getParameter("state");//操作状态
			String opid = user.getUserid();//当前用户
			String departid = user.getDepartid();//单位id
			String crimid = request.getParameter("crimid");//罪犯编号
			Map map= this.queryMaxBatchMapper(user, request);//批次
			String json = request.getParameter("data");
			List<Map> list = (List) JsonUtil.Decode(json);
			if (list.size() >0 ) {
				Map listMap = list.get(0);
				Iterator iterator = listMap.keySet().iterator();
				while (iterator.hasNext()) {
					Object key = iterator.next();
					Object value = listMap.get(key);
					if (value == null || "".equals(value)) {
						map.put(key, "0");
					}else{
						map.put(key, value);
					}
				}
			}
			map.put("opid", opid);
			map.put("crimid", crimid);
			map.put("departid", departid);
			if ("new".equals(state)) {
				count=flowOtherFlowMapper.insertPrisonErperManceImplMapper(map);
			}else if("edit".equals(state)){
				count=flowOtherFlowMapper.updatePrisonErperManceImplMapper(map);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return count;
	}
	@Override
	public int deleteMeetingImpl(HttpServletRequest request) {
		String otherid = request.getParameter("mkey");
		
		Map map = new HashMap();
		map.put("otherid", otherid);
		
		return this.flowOtherFlowMapper.deleteMeetingImplMapper(map);
	}
	@Override
	public List<Map> getAllMeetByOrgid_nx(Map map) {
		
		return flowBaseOtherMapper.getAllMeetByOrgid_nx(map);
	}
	@Override
	public List<Map> selectDataByMapCondition_nx(Map map) {
		
		return MapUtil.turnKeyToLowerCaseOfList(flowBaseOtherMapper.selectDataByMapCondition_nx(map));
	}
	
	@Override
	public String filterFlowdraftidOfMakedPage(Map<String,Object> paramap){
		String flowdraftidsObj = paramap.get("flowdraftids").toString();
		List<String> flowdraftidList = StringNumberUtil.formatString2List(flowdraftidsObj, ",");
		//
		paramap.put("flowdraftids", StringNumberUtil.formatString(flowdraftidsObj,","));
		List<Map<String,Object>> list = MapUtil.convertKeyList2LowerCase(flowBaseOtherMapper.filterFlowdraftidOfMakedPage(paramap));
		
		List<String> fitFlowdraftidList = new ArrayList<String>();
		if(null!=list && list.size()>0){
			for(Map map : list){
				String flowdraftid = map.get("flowdraftid").toString();
				fitFlowdraftidList.add(flowdraftid);
			}
		}
		flowdraftidList.removeAll(fitFlowdraftidList);
		
		return StringNumberUtil.formatList2String(flowdraftidList, ",");
	}
	
	
	
	
}