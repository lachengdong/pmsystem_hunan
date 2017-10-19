package com.sinog2c.service.impl.common;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.gkzx.common.GkzxCommon;
import com.gkzx.util.property.GetProperty;
import com.sinog2c.dao.api.common.MeetingRecordFlowMapper;
import com.sinog2c.dao.api.commutationParole.TbxfCommuteParoleBatchMapper;
import com.sinog2c.dao.api.flow.UvFlowMapper;
import com.sinog2c.dao.api.system.UserRoleWrapperMapper;
import com.sinog2c.model.system.SystemUser;
import com.sinog2c.service.api.common.CommonSQLSolutionService;
import com.sinog2c.service.api.common.SpecialService;
import com.sinog2c.service.api.flow.UvFlowService;
import com.sinog2c.service.impl.base.ServiceImplBase;
import com.sinog2c.util.common.JsonUtil;
import com.sinog2c.util.common.MapUtil;
import com.sinog2c.util.common.StringNumberUtil;

/**
 * 公共配制无法操作的 特殊处理业务层
 */
@Service("specialService")
public class SpecialServiceImpl extends ServiceImplBase implements SpecialService{
	
	@Autowired
	private TbxfCommuteParoleBatchMapper tbxfCommuteParoleBatchMapper;
	@Autowired
	private UserRoleWrapperMapper userRoleWrapperMapper;
	@Autowired
    private UvFlowMapper uvFlowMapper;
	@Autowired
	private CommonSQLSolutionService solutionService;
	@Autowired
    private UvFlowService uvFlowService;
	
	
	private static HashMap<String, String> xmlLogMsg = new GetProperty().readXml(GkzxCommon.XMLLOGMSG, null);//获取Message
	private JsonUtil jsonTool;
	@Autowired
	private MeetingRecordFlowMapper meetingRecordFlowMapper ;
	/**
	 * 描述：分监区每罪犯的会议记录保存
	 * @author YangZR
	 * @Date 2015-05-06
	 */
	@Override
	public int jxjsFJQMeetRecord(Map<String,Object> parammap, SystemUser user){
		return meetRecord(parammap, user, 1, "jail");
	}
	
	/**
	 * 描述：医院诊断小组每罪犯的会议记录保存
	 */
	@Override
	public int bwjyYYZDXZMeetRecord(Map<String,Object> parammap, SystemUser user){
		return meetRecord(parammap, user, 0, "jail");
	}
	/**
	 * 描述：分监区每罪犯的会议记录保存
	 */
	@Override
	public int bwjyFJQMeetRecord(Map<String,Object> parammap, SystemUser user){
		return meetRecord(parammap, user, 1, "jail");
	}
	
	/**
	 * 描述：监区每罪犯的会议记录保存
	 * @author YangZR
	 * @Date 2015-05-06
	 */
	@Override
	public int jxjsJQMeetRecord(Map<String,Object> parammap, SystemUser user){
		return meetRecord(parammap, user, 2, "jail");
	}
	
	/**
	 * 描述：保外就医监区每罪犯的会议记录保存
	 * @author YangZR
	 * @Date 2015-05-06
	 */
	@Override
	public int bwjyJQMeetRecord(Map<String,Object> parammap, SystemUser user){
		return meetRecord(parammap, user, 2, "bwjy");
	}
	
	/**
	 * 描述：科室每罪犯的会议记录保存
	 * @author YangZR
	 * @Date 2015-07-22
	 */
	@Override
	public int jxjsKSMeetRecord(Map<String,Object> parammap, SystemUser user){
		return meetRecord(parammap, user, 3, "jail");
	}
	
	/**
	 * 描述：保外就医科室每罪犯的会议记录保存
	 * @author YangZR
	 * @Date 2015-07-22
	 */
	@Override
	public int bwjyKSMeetRecord(Map<String,Object> parammap, SystemUser user){
		return meetRecord(parammap, user, 3, "bwjy");
	}
	
	/**
	 * 描述：评审会每罪犯的会议记录保存
	 * @author YangZR
	 * @Date 2015-05-06
	 */
	@Override
	public int jxjsPSHMeetRecord(Map<String,Object> parammap, SystemUser user){
		return meetRecord(parammap, user, 4, "jail");
	}
	
	/**
	 * 描述：保外就医评审会每罪犯的会议记录保存
	 * @author YangZR
	 * @Date 2015-05-06
	 */
	@Override
	public int bwjyPSHMeetRecord(Map<String,Object> parammap, SystemUser user){
		return meetRecord(parammap, user, 4, "bwjy");
	}
	
	/**
	 * 描述：监狱长办公会每罪犯的会议记录保存
	 * @author YangZR
	 * @Date 2015-05-06
	 */
	@Override
	public int jxjsJYZMeetRecord(Map<String,Object> parammap, SystemUser user){
		return meetRecord(parammap, user, 5, "jail");
	}
	
	/**
	 * 描述：保外就医监狱长办公会每罪犯的会议记录保存
	 * @author YangZR
	 * @Date 2015-05-06
	 */
	@Override
	public int bwjyJYZMeetRecord(Map<String,Object> parammap, SystemUser user){
		return meetRecord(parammap, user, 5, "bwjy");
	}
	
	
	/**
	 * 描述：省局处合议每罪犯的会议记录保存
	 * 
	 * 
	 */
	@Override
	public int jxjsSJCHYMeetRecord(Map<String,Object> parammap, SystemUser user){
		return meetRecord(parammap, user, 7,"province");
	}
	
	/**
	 * 描述：省局处合议每罪犯的会议记录保存
	 * 
	 * 
	 */
	@Override
	public int bwjySJCHYMeetRecord(Map<String,Object> parammap, SystemUser user){
		return meetRecord(parammap, user, 7,"province");
	}
	
	/**
	 * 描述：省局评审会每罪犯的会议记录保存
	 * @author YangZR
	 * @Date 2015-05-06
	 */
	@Override
	public int jxjsSJPSHMeetRecord(Map<String,Object> parammap, SystemUser user){
		return meetRecord(parammap, user, 8,"province");
	}
	
	/**
	 * 描述：省局评审会每罪犯的会议记录保存
	 * @author YangZR
	 * @Date 2015-05-06
	 */
	@Override
	public int bwjySJPSHMeetRecord(Map<String,Object> parammap, SystemUser user){
		return meetRecord(parammap, user, 8,"province");
	}
	/**
	 * 描述：保外就医生卫科每罪犯的会议记录保存
	 * @author admin
	 * @Date 2015-10-19
	 */
	@Override
	public int bwjySWKMeetRecord(Map<String,Object> parammap, SystemUser user){
		return meetRecord(parammap, user, 9, "bwjy");
	}
	
	private int meetRecord(Map<String,Object> parammap, SystemUser user, int grade, String departType){
		String optype = parammap.get("optype")==null?"":parammap.get("optype").toString();
		if(StringNumberUtil.notEmpty(optype)&&"add".equals(optype)){
			String applyids = parammap.get("temp1")==null ? "" : parammap.get("temp1").toString();
			if(StringNumberUtil.notEmpty(applyids)){
				parammap.put("departid", user.getDepartid());
				if("jail".equals(departType)){
					parammap = getLastCommuteBatchInfo(parammap);
				}else if("bwjy".equals(departType) || "province".equals(departType)){
					parammap = getCurmonthInfo(parammap);
				}
				
				String[] applyidArr = applyids.split(",");
				for(String applyid : applyidArr){
					parammap.put("applyid", applyid);
					parammap.put("grade", grade);
					saveMeetRecordPerCriminal(parammap, user);
				}
				
			}
		}
		
		return 1;
	}
	private void saveMeetRecordPerCriminal(Map parammap, SystemUser user){
		String solutionid = "701323";//会议记录流水表对应的查询方案
		parammap.put("solutionid", solutionid);
		solutionService.save(parammap, user);
	}
	
	private Map getCurmonthInfo(Map parammap){
		
		Date date = new Date();
		String curyear = new SimpleDateFormat("yyyy").format(date);
		String batch = new SimpleDateFormat("MM").format(date);
		//
		parammap.put("curyear", curyear);
		parammap.put("batch", batch);
		return parammap;
	}
	
	private Map getLastCommuteBatchInfo(Map parammap){
		
		Map map = MapUtil.turnKeyToLowerCase(tbxfCommuteParoleBatchMapper.getCommuteParoleBatchInfo(parammap));
		parammap.put("batchid", map.get("batchid"));
		parammap.put("curyear", map.get("curyear"));
		parammap.put("batch", map.get("batch"));
		return parammap;
	}
	
	@Override
	public int saveFJQUserInFlow(Map<String,Object> paramMap, SystemUser user){
		//获取当前用户对应的分监区长用户
		
		//将分监区长用户放入tbflow_base表的FJQUSER中
		
		return 0;
	}
//狱政管理工作月报表
	public int addYZGLData(ArrayList tempData,Map<String,Object> map) {
		// TODO Auto-generated method stub	
		//tempData  获取表单节点数据 
		//TableField 获取表中所有字段 
		//sourceMap 保存数据
		int flag = 0;
		String new_flowdraftid=null;
		String operateType = map.get("operateType").toString();
		String flowdraftid = map.get("flowdraftid")==null?null:map.get("flowdraftid").toString();	
		if(operateType.equals("edit")) //修改时保存新生成的flowdraftid
		   new_flowdraftid = map.get("new_flowdraftid")==null?null:map.get("new_flowdraftid").toString();
		Map sourceMap = new HashMap();
		String TableField=xmlLogMsg.get(GkzxCommon.TBYZ_MAG_MTHREPORTER);
		//处理子表数据
		ArrayList rows = (ArrayList)jsonTool.Decode(TableField);
		ArrayList<Map<String,String>> detailData = new ArrayList<Map<String,String>>();
		if(tempData.size()>0){
			Map tempMap = (Map) tempData.get(0);
			
			String name = "FLOWDRAFTID" ;
			String nameValue = flowdraftid ;
			String name1 = "DEPARTID" ;
			String name2 = "ESCAPEWAY" ;
			String name3 = "ESCAPEPOPULATION" ;		
			String name4 = "INCIDENCEDEPT" ;			
			String name5 = "CASECATEGORY" ;
			String name6 = "CASEQUALITY";
			String name7 = "SUNQIPOPULATION" ;
			if(operateType.equals("edit")){
				nameValue = new_flowdraftid ;
			}
			for(int i = 0 ; i <=25 ; i++){
				String nametemp1 = name1+i ;
				if(!tempMap.get(nametemp1).equals("")&&tempMap.get(nametemp1)!=null){
					HashMap<String, String> bean = new HashMap<String, String>();
					bean.put(name1, tempMap.get(name1+i).toString());
					bean.put(name2, tempMap.get(name2+i).toString());
					bean.put(name3, tempMap.get(name3+i).toString());
					bean.put(name4, tempMap.get(name4+i).toString());
					bean.put(name5, tempMap.get(name5+i).toString());
					bean.put(name6, tempMap.get(name6+i).toString());
					bean.put(name7, tempMap.get(name7+i).toString());
					bean.put("FLOWDRAFTID",nameValue);
					bean.put("OLD_FLOWDRAFTID", flowdraftid);
					detailData.add(bean);
				}else{
					break ;
				}	
			}
			//处理主表数据
			for(int i=0; i<rows.size(); i++){
				HashMap row = (HashMap)rows.get(i);
				Iterator iter=row.entrySet().iterator();
				if(iter.hasNext()){
					Map.Entry entry=(Map.Entry)iter.next();
					String strKey=entry.getKey().toString();
					String strVal=entry.getValue().toString();
					String[] startEndColumns = strVal.split("\\|");
					if (startEndColumns[1].equals("VARCHAR2")) {
						sourceMap.put(startEndColumns[0],tempMap.get(strKey));
					} else if (startEndColumns[1].equals("NUMBER")) {
						sourceMap.put(startEndColumns[0],tempMap.get(strKey));
					} else if (startEndColumns[1].equals("DATE") && !StringUtils.isEmpty(tempMap.get(strKey))) {
							String ttt = tempMap.get(strKey).toString().replaceAll("年", "-").replace("月", "-").replace("日", "");
							sourceMap.put(startEndColumns[0],ttt);
					}
				}			
			}
			if(operateType.equals("save")){
				//insert main
				sourceMap.put("FLOWDRAFTID", flowdraftid);
				    sourceMap =MapUtil.turnKeyToLowerCase(sourceMap);
					flag=meetingRecordFlowMapper.addYZGLDataMain(sourceMap);
				//insert child
				if(detailData.size()>0){
					for(int i=0;i<detailData.size();i++){
					flag=meetingRecordFlowMapper.addYZGLDataChild(MapUtil.turnKeyToLowerCase(detailData.get(i)));
					}
				}	
			}
			if(operateType.equals("edit")){			
				//insert main
				sourceMap.put("FLOWDRAFTID", flowdraftid);
				sourceMap.put("NEW_FLOWDRAFTID", new_flowdraftid);			
				    sourceMap =MapUtil.turnKeyToLowerCase(sourceMap);
					flag=meetingRecordFlowMapper.updateYZGLDataMain(sourceMap);
					//delete old child
					sourceMap.clear();
					sourceMap.put("OLD_FLOWDRAFTID", flowdraftid);
					flag=meetingRecordFlowMapper.deleteOldYZGLDataChild(MapUtil.turnKeyToLowerCase(sourceMap));
					//insert child
				if(detailData.size()>0){
					for(int i=0;i<detailData.size();i++){
					flag=meetingRecordFlowMapper.addYZGLDataChild(MapUtil.turnKeyToLowerCase(detailData.get(i)));
					}	
				}			
			}		
		}
		return flag;
	}
	
//	/**
//	 * 外来人员入监
//	 * @param paramMap
//	 * @param user
//	 * @return 成功返回1，失败返回0
//	 */
//	@Override
//	public int dealForeignPersonRJ(Map<String,Object> paramMap, SystemUser user){
//		TbyzForeignPersonRj tbyzForeignPersonRj = assembleTbyzForeignPersonRj(paramMap, user);
//		int count = tbyzForeignPersonRjMapper.insertSelective(tbyzForeignPersonRj);
//		if(count == 1){
//			dealTbyzForeignPersonRjDetail(paramMap, user);
//			return count;
//		}else{
//			return 0;
//		}
//	}
	
//	private void dealTbyzForeignPersonRjDetail(Map<String,Object> paramMap, SystemUser user){
//		for(int i=0; i<10; i++){
//			if(StringNumberUtil.notEmpty(paramMap.get("wlryname"+i))){
//				TbyzForeignPersonRjDetail tbyzForeignPersonRjDetail = new TbyzForeignPersonRjDetail();
//				tbyzForeignPersonRjDetail.setFlowdraftid(paramMap.get("flowdraftid").toString());
//				tbyzForeignPersonRjDetail.setXuhao(paramMap.get("xuhao"+i).toString());
//				tbyzForeignPersonRjDetail.setPersonname(paramMap.get("wlryname"+i).toString());
//				if(StringNumberUtil.notEmpty(paramMap.get("wlrygender"+i))){
//					tbyzForeignPersonRjDetail.setGender(paramMap.get("wlrygender"+i).toString());
//				}
//				if(StringNumberUtil.notEmpty(paramMap.get("age"+i))){
//					tbyzForeignPersonRjDetail.setAge(paramMap.get("age"+i).toString());
//				}
//				if(StringNumberUtil.notEmpty(paramMap.get("depart"+i))){
//					tbyzForeignPersonRjDetail.setDepart(paramMap.get("depart"+i).toString());
//				}
//				if(StringNumberUtil.notEmpty(paramMap.get("career"+i))){
//					tbyzForeignPersonRjDetail.setCareer(paramMap.get("career"+i).toString());
//				}
//				if(StringNumberUtil.notEmpty(paramMap.get("id"+i))){
//					tbyzForeignPersonRjDetail.setPersonid(paramMap.get("id"+i).toString());
//				}
//				tbyzForeignPersonRjDetailMapper.insertSelective(tbyzForeignPersonRjDetail);
//			}
//		}
//	}
	
//	private TbyzForeignPersonRj assembleTbyzForeignPersonRj(Map<String,Object> paramMap, SystemUser user){
//		
//		TbyzForeignPersonRj tbyzForeignPersonRj = new TbyzForeignPersonRj();
//		if(StringNumberUtil.notEmpty(paramMap.get("flowdraftid"))){
//			tbyzForeignPersonRj.setFlowdraftid(paramMap.get("flowdraftid").toString());
//		}
//		if(StringNumberUtil.notEmpty(paramMap.get("year"))){
//			tbyzForeignPersonRj.setCaseyear(paramMap.get("year").toString());
//		}
//		
//		if(StringNumberUtil.notEmpty(paramMap.get("num"))){
//			tbyzForeignPersonRj.setCasenum(paramMap.get("num").toString());
//		}
//		
//		if(StringNumberUtil.notEmpty(paramMap.get("jinjiannum"))){
//			tbyzForeignPersonRj.setRjnum(paramMap.get("jinjiannum").toString());
//		}
//		
//		if(StringNumberUtil.notEmpty(paramMap.get("malenum"))){
//			tbyzForeignPersonRj.setMalenum(paramMap.get("malenum").toString());
//		}
//		
//		if(StringNumberUtil.notEmpty(paramMap.get("femalenum"))){
//			tbyzForeignPersonRj.setFemalenum(paramMap.get("femalenum").toString());
//		}
//		if(StringNumberUtil.notEmpty(paramMap.get("jinjianreason"))){
//			tbyzForeignPersonRj.setRjreason(paramMap.get("jinjianreason").toString());
//		}
//		if(StringNumberUtil.notEmpty(paramMap.get("workarea"))){
//			tbyzForeignPersonRj.setWorkarea(paramMap.get("workarea").toString());
//		}
//		if(StringNumberUtil.notEmpty(paramMap.get("jinjiandate1"))){
//			tbyzForeignPersonRj.setRjdate1(StringNumberUtil.formatChsDate2Datestr(paramMap.get("jinjiandate1").toString()));
//		}
//		if(StringNumberUtil.notEmpty(paramMap.get("hour1"))){
//			tbyzForeignPersonRj.setRjhour1(paramMap.get("hour1").toString());
//		}
//		if(StringNumberUtil.notEmpty(paramMap.get("minute1"))){
//			tbyzForeignPersonRj.setRjminute1(paramMap.get("minute1").toString());
//		}
//		if(StringNumberUtil.notEmpty(paramMap.get("jinjiandate2"))){
//			tbyzForeignPersonRj.setRjdate2(StringNumberUtil.formatChsDate2Datestr(paramMap.get("jinjiandate2").toString()));
//		}
//		if(StringNumberUtil.notEmpty(paramMap.get("hour2"))){
//			tbyzForeignPersonRj.setRjhour2(paramMap.get("hour2").toString());
//		}
//		if(StringNumberUtil.notEmpty(paramMap.get("minute2"))){
//			tbyzForeignPersonRj.setRjminute2(paramMap.get("minute2").toString());
//		}
//		if(StringNumberUtil.notEmpty(paramMap.get("gjopinion"))){
//			tbyzForeignPersonRj.setOpiniontext1(paramMap.get("gjopinion").toString());
//		}
//		if(StringNumberUtil.notEmpty(paramMap.get("jqzopinion"))){
//			tbyzForeignPersonRj.setOpiniontext2(paramMap.get("jqzopinion").toString());
//		}
//		if(StringNumberUtil.notEmpty(paramMap.get("yzkopinion"))){
//			tbyzForeignPersonRj.setOpiniontext3(paramMap.get("yzkopinion").toString());
//		}
//		if(StringNumberUtil.notEmpty(paramMap.get("jyzopinion"))){
//			tbyzForeignPersonRj.setOpiniontext4(paramMap.get("jyzopinion").toString());
//		}
//		if(StringNumberUtil.notEmpty(paramMap.get("date1"))){
//			tbyzForeignPersonRj.setDate1(StringNumberUtil.formatChsDate2Datestr(paramMap.get("date1").toString()));
//		}
//		if(StringNumberUtil.notEmpty(paramMap.get("date2"))){
//			tbyzForeignPersonRj.setDate2(StringNumberUtil.formatChsDate2Datestr(paramMap.get("date2").toString()));
//		}
//		if(StringNumberUtil.notEmpty(paramMap.get("date3"))){
//			tbyzForeignPersonRj.setDate3(StringNumberUtil.formatChsDate2Datestr(paramMap.get("date3").toString()));
//		}
//		if(StringNumberUtil.notEmpty(paramMap.get("date4"))){
//			tbyzForeignPersonRj.setDate4(StringNumberUtil.formatChsDate2Datestr(paramMap.get("date4").toString()));
//		}
//		tbyzForeignPersonRj.setDepartid(user.getDepartid());
//		
//		return tbyzForeignPersonRj;
//	}
	
	
//	/**
//	 * 罪犯离开监管区
//	 * @param paramMap
//	 * @param user
//	 * @return 成功返回1，失败返回0
//	 */
//	@Override
//	public int dealCriminalLeaveArea(Map<String,Object> paramMap, SystemUser user){
//		TbyzCriminalLeaveArea tbyzCriminalLeaveArea = assembleTbyzCriminalLeaveArea(paramMap, user);
//		int count = tbyzCriminalLeaveAreaMapper.insertSelective(tbyzCriminalLeaveArea);
//		if(count == 1){
//			dealTbyzCriminalLeaveArea(paramMap, user);
//			return count;
//		}else{
//			return 0;
//		}
//	}
//	
//	private TbyzCriminalLeaveArea assembleTbyzCriminalLeaveArea(Map<String,Object> paramMap, SystemUser user){
//		
//		TbyzCriminalLeaveArea tbyzCriminalLeaveArea = new TbyzCriminalLeaveArea();
//		if(StringNumberUtil.notEmpty(paramMap.get("flowdraftid"))){
//			tbyzCriminalLeaveArea.setFlowdraftid(paramMap.get("flowdraftid").toString());
//		}
//		if(StringNumberUtil.notEmpty(paramMap.get("year"))){
//			tbyzCriminalLeaveArea.setCaseyear(paramMap.get("year").toString());
//		}
//		if(StringNumberUtil.notEmpty(paramMap.get("num"))){
//			tbyzCriminalLeaveArea.setCasenum(paramMap.get("num").toString());
//		}
//		if(StringNumberUtil.notEmpty(paramMap.get("shiyou"))){
//			tbyzCriminalLeaveArea.setLvreason(paramMap.get("shiyou").toString());
//		}
//		if(StringNumberUtil.notEmpty(paramMap.get("days"))){
//			tbyzCriminalLeaveArea.setLvdays1(paramMap.get("days").toString());
//		}
//		if(StringNumberUtil.notEmpty(paramMap.get("shi"))){
//			tbyzCriminalLeaveArea.setLvhour1(paramMap.get("shi").toString());
//		}
//		if(StringNumberUtil.notEmpty(paramMap.get("fen"))){
//			tbyzCriminalLeaveArea.setLvminute1(paramMap.get("fen").toString());
//		}
//		if(StringNumberUtil.notEmpty(paramMap.get("days1"))){
//			tbyzCriminalLeaveArea.setLvdays2(paramMap.get("days1").toString());
//		}
//		if(StringNumberUtil.notEmpty(paramMap.get("shi1"))){
//			tbyzCriminalLeaveArea.setLvhour2(paramMap.get("shi1").toString());
//		}
//		if(StringNumberUtil.notEmpty(paramMap.get("fen1"))){
//			tbyzCriminalLeaveArea.setLvminute2(paramMap.get("fen1").toString());
//		}
//		if(StringNumberUtil.notEmpty(paramMap.get("policesman"))){
//			tbyzCriminalLeaveArea.setPolicesman(paramMap.get("policesman").toString());
//		}
//		if(StringNumberUtil.notEmpty(paramMap.get("jqopinion"))){
//			tbyzCriminalLeaveArea.setOpiniontext1(paramMap.get("jqopinion").toString());
//		}
//		if(StringNumberUtil.notEmpty(paramMap.get("date1"))){
//			tbyzCriminalLeaveArea.setDate1(StringNumberUtil.formatChsDate2Datestr(paramMap.get("date1").toString()));
//		}
//		if(StringNumberUtil.notEmpty(paramMap.get("yzkopinion"))){
//			tbyzCriminalLeaveArea.setOpiniontext2(paramMap.get("yzkopinion").toString());
//		}
//		if(StringNumberUtil.notEmpty(paramMap.get("date2"))){
//			tbyzCriminalLeaveArea.setDate2(StringNumberUtil.formatChsDate2Datestr(paramMap.get("date2").toString()));
//		}
//		if(StringNumberUtil.notEmpty(paramMap.get("fglopinion"))){
//			tbyzCriminalLeaveArea.setOpiniontext3(paramMap.get("fglopinion").toString());
//		}
//		if(StringNumberUtil.notEmpty(paramMap.get("date3"))){
//			tbyzCriminalLeaveArea.setDate3(StringNumberUtil.formatChsDate2Datestr(paramMap.get("date3").toString()));
//		}
//		if(StringNumberUtil.notEmpty(paramMap.get("jyzopinion"))){
//			tbyzCriminalLeaveArea.setOpiniontext4(paramMap.get("jyzopinion").toString());
//		}
//		if(StringNumberUtil.notEmpty(paramMap.get("date4"))){
//			tbyzCriminalLeaveArea.setDate4(StringNumberUtil.formatChsDate2Datestr(paramMap.get("date4").toString()));
//		}
//		tbyzCriminalLeaveArea.setDepartid(user.getDepartid());
//		return tbyzCriminalLeaveArea;
//	}
//	
//	private void dealTbyzCriminalLeaveArea(Map<String,Object> paramMap, SystemUser user){
//		for(int i=0; i<10; i++){
//			if(StringNumberUtil.notEmpty(paramMap.get("lkcrimid"+i))){
//				TbyzCriminalLvDetail tbyzCriminalLvDetail = new TbyzCriminalLvDetail();
//				tbyzCriminalLvDetail.setFlowdraftid(paramMap.get("flowdraftid").toString());
//				tbyzCriminalLvDetail.setXuhao(paramMap.get("xuhao"+i).toString());
//				tbyzCriminalLvDetail.setCrimid(paramMap.get("lkcrimid"+i).toString());
//				
//				if(StringNumberUtil.notEmpty(paramMap.get("name"+i))){
//					tbyzCriminalLvDetail.setName(paramMap.get("name"+i).toString());
//				}
//				if(StringNumberUtil.notEmpty(paramMap.get("charge"+i))){
//					tbyzCriminalLvDetail.setCharge(paramMap.get("charge"+i).toString());
//				}
//				if(StringNumberUtil.notEmpty(paramMap.get("yuanpan"+i))){
//					tbyzCriminalLvDetail.setYuanpan(paramMap.get("yuanpan"+i).toString());
//				}
//				if(StringNumberUtil.notEmpty(paramMap.get("yuxing"+i))){
//					tbyzCriminalLvDetail.setYuxing(paramMap.get("yuxing"+i).toString());
//				}
//				if(StringNumberUtil.notEmpty(paramMap.get("waichudidian"+i))){
//					tbyzCriminalLvDetail.setWaichudidian(paramMap.get("waichudidian"+i).toString());
//				}
//				
//				tbyzCriminalLvDetailMapper.insertSelective(tbyzCriminalLvDetail);
//			}
//		}
//	}
	
	
//	/**
//	 * 外来物品入监
//	 * @param paramMap
//	 * @param user
//	 * @return
//	 */
//	@Override
//	public int dealForeignGoodsInprison(Map<String,Object> paramMap, SystemUser user){
//		TbyzForeignGoodInprison tbyzForeignGoodInprison = assembleTbyzForeignGoodInprison(paramMap, user);
//		int count = tbyzForeignGoodInprisonMapper.insertSelective(tbyzForeignGoodInprison);
//		if(count == 1){
//			dealTbyzForeignGoodInprisonDetail(paramMap, user);
//			return count;
//		}else{
//			return 0;
//		}
//	}
	
//	private void dealTbyzForeignGoodInprisonDetail(Map<String,Object> paramMap, SystemUser user){
//		for(int i=0; i<10; i++){
//			if(StringNumberUtil.notEmpty(paramMap.get("goods"+i))){
//				TbyzForeignGoodInprisonDetail tbyzForeignGoodInprisonDetail = new TbyzForeignGoodInprisonDetail();
//				tbyzForeignGoodInprisonDetail.setFlowdraftid(paramMap.get("flowdraftid").toString());
//				tbyzForeignGoodInprisonDetail.setXuhao(paramMap.get("xuhao"+i).toString());
//				
//				tbyzForeignGoodInprisonDetail.setGoodname(paramMap.get("goods"+i).toString());
//				if(StringNumberUtil.notEmpty(paramMap.get("gdnum"+i))){
//					tbyzForeignGoodInprisonDetail.setGdnum(paramMap.get("gdnum"+i).toString());
//				}
//				if(StringNumberUtil.notEmpty(paramMap.get("depart"+i))){
//					tbyzForeignGoodInprisonDetail.setDepart(paramMap.get("depart"+i).toString());
//				}
//				if(StringNumberUtil.notEmpty(paramMap.get("tkperson"+i))){
//					tbyzForeignGoodInprisonDetail.setTkperson(paramMap.get("tkperson"+i).toString());
//				}
//				if(StringNumberUtil.notEmpty(paramMap.get("tkid"+i))){
//					tbyzForeignGoodInprisonDetail.setTkid(paramMap.get("tkid"+i).toString());
//				}
//				if(StringNumberUtil.notEmpty(paramMap.get("usename"+i))){
//					tbyzForeignGoodInprisonDetail.setUsername(paramMap.get("usename"+i).toString());
//				}
//				if(StringNumberUtil.notEmpty(paramMap.get("userid"+i))){
//					tbyzForeignGoodInprisonDetail.setUserid(paramMap.get("userid"+i).toString());
//				}
//				if(StringNumberUtil.notEmpty(paramMap.get("remark"+i))){
//					tbyzForeignGoodInprisonDetail.setRemark(paramMap.get("remark"+i).toString());
//				}
//				tbyzForeignGoodInprisonDetailMapper.insertSelective(tbyzForeignGoodInprisonDetail);
//			}
//		}
//	}
//	
//	private TbyzForeignGoodInprison assembleTbyzForeignGoodInprison(Map<String,Object> paramMap, SystemUser user){
//		
//		TbyzForeignGoodInprison tbyzForeignGoodInprison = new TbyzForeignGoodInprison();
//		if(StringNumberUtil.notEmpty(paramMap.get("flowdraftid"))){
//			tbyzForeignGoodInprison.setFlowdraftid(paramMap.get("flowdraftid").toString());
//		}
//		if(StringNumberUtil.notEmpty(paramMap.get("year"))){
//			tbyzForeignGoodInprison.setCaseyear(paramMap.get("year").toString());
//		}
//		
//		if(StringNumberUtil.notEmpty(paramMap.get("num"))){
//			tbyzForeignGoodInprison.setCasenum(paramMap.get("num").toString());
//		}
//		
//		if(StringNumberUtil.notEmpty(paramMap.get("jinjianreason"))){
//			tbyzForeignGoodInprison.setRjreason(paramMap.get("jinjianreason").toString());
//		}
//		if(StringNumberUtil.notEmpty(paramMap.get("jijiandate1"))){
//			tbyzForeignGoodInprison.setRjdate1(StringNumberUtil.formatChsDate2Datestr(paramMap.get("jijiandate1").toString()));
//		}
//		if(StringNumberUtil.notEmpty(paramMap.get("hour1"))){
//			tbyzForeignGoodInprison.setRjhour1(paramMap.get("hour1").toString());
//		}
//		if(StringNumberUtil.notEmpty(paramMap.get("minute1"))){
//			tbyzForeignGoodInprison.setRjminute1(paramMap.get("minute1").toString());
//		}
//		if(StringNumberUtil.notEmpty(paramMap.get("jijiandate2"))){
//			tbyzForeignGoodInprison.setRjdate2(StringNumberUtil.formatChsDate2Datestr(paramMap.get("jijiandate2").toString()));
//		}
//		if(StringNumberUtil.notEmpty(paramMap.get("hour2"))){
//			tbyzForeignGoodInprison.setRjhour2(paramMap.get("hour2").toString());
//		}
//		if(StringNumberUtil.notEmpty(paramMap.get("minute2"))){
//			tbyzForeignGoodInprison.setRjminute2(paramMap.get("minute2").toString());
//		}
//		if(StringNumberUtil.notEmpty(paramMap.get("gjopinion"))){
//			tbyzForeignGoodInprison.setOpiniontext1(paramMap.get("gjopinion").toString());
//		}
//		if(StringNumberUtil.notEmpty(paramMap.get("jqopinion"))){
//			tbyzForeignGoodInprison.setOpiniontext2(paramMap.get("jqopinion").toString());
//		}
//		if(StringNumberUtil.notEmpty(paramMap.get("yzkopinion"))){
//			tbyzForeignGoodInprison.setOpiniontext3(paramMap.get("yzkopinion").toString());
//		}
//		if(StringNumberUtil.notEmpty(paramMap.get("jyzopinion"))){
//			tbyzForeignGoodInprison.setOpiniontext4(paramMap.get("jyzopinion").toString());
//		}
//		if(StringNumberUtil.notEmpty(paramMap.get("date1"))){
//			tbyzForeignGoodInprison.setDate1(StringNumberUtil.formatChsDate2Datestr(paramMap.get("date1").toString()));
//		}
//		if(StringNumberUtil.notEmpty(paramMap.get("date2"))){
//			tbyzForeignGoodInprison.setDate2(StringNumberUtil.formatChsDate2Datestr(paramMap.get("date2").toString()));
//		}
//		if(StringNumberUtil.notEmpty(paramMap.get("date3"))){
//			tbyzForeignGoodInprison.setDate3(StringNumberUtil.formatChsDate2Datestr(paramMap.get("date3").toString()));
//		}
//		if(StringNumberUtil.notEmpty(paramMap.get("date4"))){
//			tbyzForeignGoodInprison.setDate4(StringNumberUtil.formatChsDate2Datestr(paramMap.get("date4").toString()));
//		}
//		tbyzForeignGoodInprison.setDepartid(user.getDepartid());
//		
//		return tbyzForeignGoodInprison;
//	}
	
//	/**
//	 *外来车辆入监
//	 * @param paramMap
//	 * @param user
//	 * @return
//	 */
//	@Override
//	public int dealForeignCarsInprison(Map<String,Object> paramMap, SystemUser user){
//		TbyzForeignCarInprison tbyzForeignCarInprison = assembleTbyzForeignCarInprison(paramMap, user);
//		int count = tbyzForeignCarInprisonMapper.insertSelective(tbyzForeignCarInprison);
//		return count;
//	}
//	
//	private TbyzForeignCarInprison assembleTbyzForeignCarInprison(Map<String,Object> paramMap, SystemUser user){
//		
//		TbyzForeignCarInprison tbyzForeignCarInprison = new TbyzForeignCarInprison();
//		if(StringNumberUtil.notEmpty(paramMap.get("flowdraftid"))){
//			tbyzForeignCarInprison.setFlowdraftid(paramMap.get("flowdraftid").toString());
//		}
//		if(StringNumberUtil.notEmpty(paramMap.get("year"))){
//			tbyzForeignCarInprison.setCaseyear(paramMap.get("year").toString());
//		}
//		if(StringNumberUtil.notEmpty(paramMap.get("num"))){
//			tbyzForeignCarInprison.setCasenum(paramMap.get("num").toString());
//		}
//		if(StringNumberUtil.notEmpty(paramMap.get("toplace"))){
//			tbyzForeignCarInprison.setToplace(paramMap.get("toplace").toString());
//		}
//		if(StringNumberUtil.notEmpty(paramMap.get("drivername"))){
//			tbyzForeignCarInprison.setDrivername(paramMap.get("drivername").toString());
//		}
//		if(StringNumberUtil.notEmpty(paramMap.get("carpersonnum"))){
//			tbyzForeignCarInprison.setCarpersonnum(paramMap.get("carpersonnum").toString());
//		}
//		if(StringNumberUtil.notEmpty(paramMap.get("malenum"))){
//			tbyzForeignCarInprison.setMalenum(paramMap.get("malenum").toString());
//		}
//		if(StringNumberUtil.notEmpty(paramMap.get("femalenum"))){
//			tbyzForeignCarInprison.setFemalenum(paramMap.get("femalenum").toString());
//		}
//		if(StringNumberUtil.notEmpty(paramMap.get("cartype"))){
//			tbyzForeignCarInprison.setCartype(paramMap.get("cartype").toString());
//		}
//		if(StringNumberUtil.notEmpty(paramMap.get("carno"))){
//			tbyzForeignCarInprison.setCarno(paramMap.get("carno").toString());
//		}
//		
//		if(StringNumberUtil.notEmpty(paramMap.get("remark"))){
//			tbyzForeignCarInprison.setRemark(paramMap.get("remark").toString());
//		}
//		if(StringNumberUtil.notEmpty(paramMap.get("jinjianreason"))){
//			tbyzForeignCarInprison.setRjreason(paramMap.get("jinjianreason").toString());
//		}
//		
//		if(StringNumberUtil.notEmpty(paramMap.get("jinjiandate1"))){
//			tbyzForeignCarInprison.setRjdays1(paramMap.get("jinjiandate1").toString());
//		}
//		if(StringNumberUtil.notEmpty(paramMap.get("hour1"))){
//			tbyzForeignCarInprison.setRjhour1(paramMap.get("hour1").toString());
//		}
//		if(StringNumberUtil.notEmpty(paramMap.get("minute1"))){
//			tbyzForeignCarInprison.setRjminute1(paramMap.get("minute1").toString());
//		}
//		if(StringNumberUtil.notEmpty(paramMap.get("jinjiandate2"))){
//			tbyzForeignCarInprison.setRjdays2(paramMap.get("jinjiandate2").toString());
//		}
//		if(StringNumberUtil.notEmpty(paramMap.get("hour2"))){
//			tbyzForeignCarInprison.setRjhour2(paramMap.get("hour2").toString());
//		}
//		if(StringNumberUtil.notEmpty(paramMap.get("minute2"))){
//			tbyzForeignCarInprison.setRjminute2(paramMap.get("minute2").toString());
//		}
//		if(StringNumberUtil.notEmpty(paramMap.get("gjopinion"))){
//			tbyzForeignCarInprison.setOpiniontext1(paramMap.get("gjopinion").toString());
//		}
//		if(StringNumberUtil.notEmpty(paramMap.get("jqopinion"))){
//			tbyzForeignCarInprison.setOpiniontext2(paramMap.get("jqopinion").toString());
//		}
//		if(StringNumberUtil.notEmpty(paramMap.get("yzkopinion"))){
//			tbyzForeignCarInprison.setOpiniontext3(paramMap.get("yzkopinion").toString());
//		}
//		if(StringNumberUtil.notEmpty(paramMap.get("jyzopinion"))){
//			tbyzForeignCarInprison.setOpiniontext4(paramMap.get("jyzopinion").toString());
//		}
//		if(StringNumberUtil.notEmpty(paramMap.get("date1"))){
//			tbyzForeignCarInprison.setDate1(StringNumberUtil.formatChsDate2Datestr(paramMap.get("date1").toString()));
//		}
//		if(StringNumberUtil.notEmpty(paramMap.get("date2"))){
//			tbyzForeignCarInprison.setDate2(StringNumberUtil.formatChsDate2Datestr(paramMap.get("date2").toString()));
//		}
//		if(StringNumberUtil.notEmpty(paramMap.get("date3"))){
//			tbyzForeignCarInprison.setDate3(StringNumberUtil.formatChsDate2Datestr(paramMap.get("date3").toString()));
//		}
//		if(StringNumberUtil.notEmpty(paramMap.get("date4"))){
//			tbyzForeignCarInprison.setDate4(StringNumberUtil.formatChsDate2Datestr(paramMap.get("date4").toString()));
//		}
//		tbyzForeignCarInprison.setDepartid(user.getDepartid());
//		
//		return tbyzForeignCarInprison;
//	}
	
//	/**
//	 * 服刑人员监区调动
//	 * @param paramMap
//	 * @param user
//	 * @return
//	 */
//	@Override
//	public int dealCriminalTransferApply(Map<String,Object> paramMap, SystemUser user){
//		handleCriminalTransferApplys(paramMap, user);
//		return 1;
//	}
//	
//	
//	private void handleCriminalTransferApplys(Map<String,Object> paramMap, SystemUser user){
//		String transferdata = paramMap.get("transferdata") == null ? "" : paramMap.get("transferdata").toString();
//		if(StringNumberUtil.notEmpty(transferdata)){
//			List<String> transferDataList = StringNumberUtil.formatString2List(transferdata, ";");
//			int count = 0;
//			for(String transferStr : transferDataList){
//				count ++;
//				List<String> transferList = StringNumberUtil.formatString2List(transferStr, "@");
//				TbyzCriminalTransferApply tbyzCriminalTransferApply = new TbyzCriminalTransferApply();
//				tbyzCriminalTransferApply.setFlowdraftid(paramMap.get("flowdraftid").toString());
//				tbyzCriminalTransferApply.setXuhao(String.valueOf(count));
//				tbyzCriminalTransferApply.setDepartid(user.getDepartid());
//				tbyzCriminalTransferApply.setCrimids(transferList.get(0));
//				tbyzCriminalTransferApply.setNames(transferList.get(1));
//				tbyzCriminalTransferApply.setNums(transferList.get(2));
//				tbyzCriminalTransferApply.setToorgid(transferList.get(3));
//				tbyzCriminalTransferApply.setToorgname(transferList.get(4));
//				tbyzCriminalTransferApply.setText1(paramMap.get("applyid").toString());
//				tbyzCriminalTransferApplyMapper.insertSelective(tbyzCriminalTransferApply);
//			}
//		}
//	}
	
	
//	/**
//	 * 罪犯狱政惩处审批处理
//	 * @param paramMap
//	 * @param user
//	 * @return
//	 */
//	public int dealConfinementApprove(Map<String,Object> paramMap, SystemUser user){
//		String flowdraftid = paramMap.get("flowdraftid").toString();
//		TbyzPunishment tbyzPunishment = tbyzPunishmentMapper.selectByPrimaryKey(flowdraftid);
//		if(null != tbyzPunishment){
//			tbyzPunishment = assembleTbyzPunishment(tbyzPunishment, paramMap, user);
//			return tbyzPunishmentMapper.updateByPrimaryKeySelective(tbyzPunishment);
//		}else{
//			tbyzPunishment = new TbyzPunishment();
//			tbyzPunishment.setFlowdraftid(flowdraftid);
//			tbyzPunishment = assembleTbyzPunishment(tbyzPunishment, paramMap, user);
//			return tbyzPunishmentMapper.insertSelective(tbyzPunishment);
//		}
//	}
	
//	private TbyzPunishment assembleTbyzPunishment(TbyzPunishment tbyzPunishment, Map<String,Object> paramMap, SystemUser user){
//		
//		if(StringNumberUtil.notEmpty(paramMap.get("crimid"))){
//			tbyzPunishment.setCrimid(paramMap.get("crimid").toString());
//		}
//		if(StringNumberUtil.notEmpty(paramMap.get("date1"))){
//			tbyzPunishment.setCbrq(StringNumberUtil.formatChsDate2Datestr(paramMap.get("date1").toString()));//呈报日期
//		}
//		if(StringNumberUtil.notEmpty(paramMap.get("date4"))){
//			tbyzPunishment.setPzrq(StringNumberUtil.formatChsDate2Datestr(paramMap.get("date4").toString()));//批准日期
//		}
//		if(StringNumberUtil.notEmpty(paramMap.get("cclb"))){
//			tbyzPunishment.setCclb(paramMap.get("cclb").toString());//惩处类别
//		}
//		if(StringNumberUtil.notEmpty(paramMap.get("applyreason"))){
//			tbyzPunishment.setCcyy(paramMap.get("applyreason").toString());//惩处原因
//		}
//		if(StringNumberUtil.notEmpty(paramMap.get("confinedays"))){
//			tbyzPunishment.setCcts(paramMap.get("confinedays").toString());//惩处天数
//		}
//		if(StringNumberUtil.notEmpty(paramMap.get("confinedate1"))){
//			tbyzPunishment.setCcqr(StringNumberUtil.formatChsDate2Datestr(paramMap.get("confinedate1").toString()));//惩处起日 呈报的惩处起始日期
//		}
//		if(StringNumberUtil.notEmpty(paramMap.get("confinedate2"))){
//			tbyzPunishment.setCczr(StringNumberUtil.formatChsDate2Datestr(paramMap.get("confinedate2").toString()));//惩处止日 呈报的惩处截止日期
//		}
//		if(StringNumberUtil.notEmpty(paramMap.get("applyreason"))){
//			tbyzPunishment.setBz(paramMap.get("applyreason").toString());//惩处原因详述
//		}
//		if(StringNumberUtil.notEmpty(paramMap.get("disarmdate"))){
//			tbyzPunishment.setJcrq(StringNumberUtil.formatChsDate2Datestr(paramMap.get("disarmdate").toString()));//惩处解除日期 惩处实际解除日期
//		}
//		if(StringNumberUtil.notEmpty(paramMap.get("confinementacting"))){
//			tbyzPunishment.setJcly(paramMap.get("confinementacting").toString());//解除理由/惩处期间表现 
//		}
//		tbyzPunishment.setDepartid(user.getDepartid());
//		return tbyzPunishment;
//		
//	}
	
	
//	/**
//	 * 高危犯审批处理
//	 * @param paramMap
//	 * @param user
//	 * @return
//	 */
//	public int dealDangerousApprove(Map<String,Object> paramMap, SystemUser user){
//		String flowdraftid = paramMap.get("flowdraftid").toString();
//		TbyzPunishment tbyzPunishment = tbyzPunishmentMapper.selectByPrimaryKey(flowdraftid);
//		if(null != tbyzPunishment){
//			tbyzPunishment = assembleTbyzPunishment4Dangerous(tbyzPunishment, paramMap, user);
//			return tbyzPunishmentMapper.updateByPrimaryKeySelective(tbyzPunishment);
//		}else{
//			tbyzPunishment = new TbyzPunishment();
//			tbyzPunishment.setFlowdraftid(flowdraftid);
//			tbyzPunishment = assembleTbyzPunishment4Dangerous(tbyzPunishment, paramMap, user);
//			return tbyzPunishmentMapper.insertSelective(tbyzPunishment);
//		}
//	}
	
	
	
//	private TbyzPunishment assembleTbyzPunishment4Dangerous(TbyzPunishment tbyzPunishment, Map<String,Object> paramMap, SystemUser user){
//		
//		if(StringNumberUtil.notEmpty(paramMap.get("crimid"))){
//			tbyzPunishment.setCrimid(paramMap.get("crimid").toString());
//		}
//		if(StringNumberUtil.notEmpty(paramMap.get("zhuanguantime"))){
//			tbyzPunishment.setCbrq(StringNumberUtil.formatChsDate2Datestr(paramMap.get("zhuanguantime").toString()));//呈报日期
//		}
//		if(StringNumberUtil.notEmpty(paramMap.get("text19"))){
//			tbyzPunishment.setPzrq(StringNumberUtil.formatChsDate2Datestr(paramMap.get("text19").toString()));//批准日期
//		}
//		if(StringNumberUtil.notEmpty(paramMap.get("cclb"))){
//			tbyzPunishment.setCclb(paramMap.get("cclb").toString());//惩处类别
//		}
//		if(StringNumberUtil.notEmpty(paramMap.get("zhuanguanreason"))){
//			tbyzPunishment.setCcyy(paramMap.get("zhuanguanreason").toString());//惩处原因
//		}
////		if(StringNumberUtil.notEmpty(paramMap.get("confinedays"))){
////			tbyzPunishment.setCcts(paramMap.get("confinedays").toString());//惩处天数
////		}
////		if(StringNumberUtil.notEmpty(paramMap.get("confinedate1"))){
////			tbyzPunishment.setCcqr(StringNumberUtil.formatChsDate2Datestr(paramMap.get("confinedate1").toString()));//惩处起日 呈报的惩处起始日期
////		}
////		if(StringNumberUtil.notEmpty(paramMap.get("confinedate2"))){
////			tbyzPunishment.setCczr(StringNumberUtil.formatChsDate2Datestr(paramMap.get("confinedate2").toString()));//惩处止日 呈报的惩处截止日期
////		}
//		if(StringNumberUtil.notEmpty(paramMap.get("zhuanguanreason"))){
//			tbyzPunishment.setBz(paramMap.get("zhuanguanreason").toString());//惩处原因详述
//		}
//		if(StringNumberUtil.notEmpty(paramMap.get("text20"))){
//			tbyzPunishment.setJcrq(StringNumberUtil.formatChsDate2Datestr(paramMap.get("text20").toString()));//惩处解除日期 惩处实际解除日期
//		}
////		if(StringNumberUtil.notEmpty(paramMap.get("confinementacting"))){
////			tbyzPunishment.setJcly(paramMap.get("confinementacting").toString());//解除理由/惩处期间表现 
////		}
//		tbyzPunishment.setDepartid(user.getDepartid());
//		return tbyzPunishment;
//		
//	}


	
//	/**
//	 * 优秀互监组状励审批的处理
//	 * @param paramMap
//	 * @param user
//	 * @return
//	 */
//	public int dealTbyzExcellentSupervision(Map<String,Object> paramMap, SystemUser user){
//		TbyzExcellentSupervision tbyzExcellentSupervision = assembleTbyzExcellentSupervision(paramMap, user);
//		int count = tbyzExcellentSupervisionMapper.insertSelective(tbyzExcellentSupervision);
//		if(count == 1){
//			dealTbyzExcellentSvDetail(paramMap, user);
//			return count;
//		}else{
//			return 0;
//		}
//	}
//	
//	private TbyzExcellentSupervision assembleTbyzExcellentSupervision(Map<String,Object> paramMap, SystemUser user){
//		
//		TbyzExcellentSupervision tbyzExcellentSupervision = new TbyzExcellentSupervision();
//		if(StringNumberUtil.notEmpty(paramMap.get("flowdraftid"))){
//			tbyzExcellentSupervision.setFlowdraftid(paramMap.get("flowdraftid").toString());
//		}
//		if(StringNumberUtil.notEmpty(paramMap.get("fajinjiaonaqingkuang"))){
//			tbyzExcellentSupervision.setGroupnum(Integer.parseInt(paramMap.get("fajinjiaonaqingkuang").toString()));
//		}
//		if(StringNumberUtil.notEmpty(paramMap.get("paymentpc"))){
//			tbyzExcellentSupervision.setMemsnum(Integer.parseInt(paramMap.get("paymentpc").toString()));
//		}
//		if(StringNumberUtil.notEmpty(paramMap.get("paymentcc"))){
//			tbyzExcellentSupervision.setApprovedate(StringNumberUtil.formatChsDate2Datestr(paramMap.get("paymentcc").toString()));
//		}
//		if(StringNumberUtil.notEmpty(paramMap.get("gaizaobiaoxian"))){
//			tbyzExcellentSupervision.setLeaderfen(new BigDecimal(Double.parseDouble(paramMap.get("gaizaobiaoxian").toString())));
//		}
//		if(StringNumberUtil.notEmpty(paramMap.get("casenum"))){
//			tbyzExcellentSupervision.setMemberfen(new BigDecimal(Double.parseDouble(paramMap.get("casenum").toString())));
//		}
//		if(StringNumberUtil.notEmpty(paramMap.get("text1"))){
//			tbyzExcellentSupervision.setOpiniontext1(paramMap.get("text1").toString());
//		}
//		if(StringNumberUtil.notEmpty(paramMap.get("text4"))){
//			tbyzExcellentSupervision.setDate1(StringNumberUtil.formatChsDate2Datestr(paramMap.get("text4").toString()));
//		}
//		if(StringNumberUtil.notEmpty(paramMap.get("text2"))){
//			tbyzExcellentSupervision.setOpiniontext2(paramMap.get("text2").toString());
//		}
//		if(StringNumberUtil.notEmpty(paramMap.get("text5"))){
//			tbyzExcellentSupervision.setDate2(StringNumberUtil.formatChsDate2Datestr(paramMap.get("text5").toString()));
//		}
//		if(StringNumberUtil.notEmpty(paramMap.get("text3"))){
//			tbyzExcellentSupervision.setOpiniontext3(paramMap.get("text3").toString());
//		}
//		if(StringNumberUtil.notEmpty(paramMap.get("text6"))){
//			tbyzExcellentSupervision.setDate3(StringNumberUtil.formatChsDate2Datestr(paramMap.get("text6").toString()));
//		}
//		tbyzExcellentSupervision.setDepartid(user.getDepartid());
//		
//		return tbyzExcellentSupervision;
//	}
//	
//	private void dealTbyzExcellentSvDetail(Map<String,Object> paramMap, SystemUser user){
//		for(int i=0; i<6; i++){
//			if(StringNumberUtil.notEmpty(paramMap.get("popup_zuzhang"+i))){
//				TbyzExcellentSvDetail tbyzExcellentSvDetail = new TbyzExcellentSvDetail();
//				tbyzExcellentSvDetail.setFlowdraftid(paramMap.get("flowdraftid").toString());
//				tbyzExcellentSvDetail.setXuhao(paramMap.get("xuhao"+i).toString());
//				tbyzExcellentSvDetail.setLeadername(paramMap.get("popup_zuzhang"+i).toString());
//				
//				if(StringNumberUtil.notEmpty(paramMap.get("id"+i))){
//					String idValue = paramMap.get("id"+i).toString();
////					String[] idArr = idValue.split("@");
//					tbyzExcellentSvDetail.setHujianid(idValue);
////					tbyzExcellentSvDetail.setHujiangroup(idArr[1]);
//				}
//				
//				
//				if(StringNumberUtil.notEmpty(paramMap.get("members"+i))){
//					tbyzExcellentSvDetail.setMembernames(paramMap.get("members"+i).toString());
//				}
//				if(StringNumberUtil.notEmpty(paramMap.get("job"+i))){
//					tbyzExcellentSvDetail.setJobs(paramMap.get("job"+i).toString());
//				}
//				if(StringNumberUtil.notEmpty(paramMap.get("leaderid"+i))){
//					tbyzExcellentSvDetail.setLeaderid(paramMap.get("leaderid"+i).toString());
//				}
//				if(StringNumberUtil.notEmpty(paramMap.get("membersid"+i))){
//					tbyzExcellentSvDetail.setMembersid(paramMap.get("membersid"+i).toString());
//				}
//				
//				tbyzExcellentSvDetailMapper.insertSelective(tbyzExcellentSvDetail);
//			}
//		}
//	}
	
	
	/**
	 * 流程走下一级时，将当前节点、下一级节点保存到TBFLOW_PERSONAPPROVE表中，
	 * 用于下一级节点退回到当前节点时，能够找到流程路线。
	 * @author	YangZR		2015-10-16
	 */
	public int saveCurrNodeidAndNextNodeid(Map<String,Object> paramMap, SystemUser user){
		String operateType = paramMap.get("operateType").toString().trim();
		if(StringNumberUtil.notEmpty(operateType) && ! "save".equals(operateType)){
			String state = "0,1"; //流程提交 state = 0， 退回 state = 2, 默认为提交
			if("back".equals(operateType)){
				state = "2";
			}else if("no".equals(operateType)){
				state = "-1";
			}
			
			paramMap.put("state", state);
			solutionSaveCurrNodeidAndNextNodeid(paramMap, user);
			
		}
		return 1;
		
	}
	
	private void solutionSaveCurrNodeidAndNextNodeid(Map parammap, SystemUser user){
		String solutionid = "950452";//保存下一个节点对应的查询方案
		parammap.put("solutionid", solutionid);
		solutionService.save(parammap, user);
	}
	

//	/**
//	 * 流程审批通过后，将批量奖扣分的数据（数据格式：crimid1@score1,crimid2@score2，数据存在表单节点ids里）存入数据库
//	 * @author	YangZR		2015-10-19
//	 */
//	public int dealBatchJiangFen(Map<String,Object> paramMap, SystemUser user){
//		String ids = paramMap.get("ids").toString().trim();
//		if(StringNumberUtil.notEmpty(ids)){
//			TbyzRewardPunishment tbyzRewardPunishment = new TbyzRewardPunishment();
//			tbyzRewardPunishment = assembleTbyzRewardPunishment(tbyzRewardPunishment, paramMap, user);
//			String[] id = ids.split(",");
//			for(int i=0; i<id.length; i++){
//				String crimid = id[i].split("@")[0];
//				String score = id[i].split("@")[1];
//				tbyzRewardPunishment.setCrimid(crimid);
//				tbyzRewardPunishment.setScore(score);
//				tbyzRewardPunishmentMapper.insertSelective(tbyzRewardPunishment);
//			}
//			//.
//		}
//		return 1;
//	}
//	
//	private TbyzRewardPunishment assembleTbyzRewardPunishment(TbyzRewardPunishment tbyzRewardPunishment, Map<String,Object> paramMap, SystemUser user){
//		
//		tbyzRewardPunishment.setFlowdraftid(paramMap.get("flowdraftid").toString());
//		tbyzRewardPunishment.setRptype(paramMap.get("rptype").toString());//批量奖扣分类型
//		tbyzRewardPunishment.setDepartid(user.getDepartid());
//		
//		if(StringNumberUtil.notEmpty(paramMap.get("jiangfenreason"))){
//			tbyzRewardPunishment.setRpreason(paramMap.get("jiangfenreason").toString());
//		}
//		if(StringNumberUtil.notEmpty(paramMap.get("jianquopinion"))){
//			tbyzRewardPunishment.setOpiniontext1(paramMap.get("jianquopinion").toString());
//		}
//		if(StringNumberUtil.notEmpty(paramMap.get("date1"))){
//			tbyzRewardPunishment.setDate1(StringNumberUtil.formatChsDate2Datestr(paramMap.get("date1").toString()));
//		}
//		if(StringNumberUtil.notEmpty(paramMap.get("keshiopinion"))){
//			tbyzRewardPunishment.setOpiniontext3(paramMap.get("keshiopinion").toString());
//		}
//		if(StringNumberUtil.notEmpty(paramMap.get("date2"))){
//			tbyzRewardPunishment.setDate3(StringNumberUtil.formatChsDate2Datestr(paramMap.get("date2").toString()));
//		}
//		if(StringNumberUtil.notEmpty(paramMap.get("jianyuopinion"))){
//			tbyzRewardPunishment.setOpiniontext5(paramMap.get("jianyuopinion").toString());
//		}
//		if(StringNumberUtil.notEmpty(paramMap.get("date3"))){
//			tbyzRewardPunishment.setDate5(StringNumberUtil.formatChsDate2Datestr(paramMap.get("date3").toString()));
//		}
//		if(StringNumberUtil.notEmpty(paramMap.get("tianbiaodate"))){
//			tbyzRewardPunishment.setFormmakedate(StringNumberUtil.formatChsDate2Datestr(paramMap.get("tianbiaodate").toString()));
//		}
//		
//		if(StringNumberUtil.notEmpty(paramMap.get("tempid"))){
//			tbyzRewardPunishment.setText1(paramMap.get("tempid").toString());
//		}
//		return tbyzRewardPunishment;
//	}
	
	/**
	 * 流程审批通过后，更新罪犯基本信息的分级处遇等级：更新表tbprisoner_base_crime的字段chargeclass，用查询方案实现
	 * @author	YangZR		2015-10-19
	 */
	public int dealCriminalFJCY(Map<String,Object> paramMap, SystemUser user){
		String solutionid = "950547";
		paramMap.put("solutionid", solutionid);
		solutionService.save(paramMap, user);
		return 1;
	}
	
	/**
	 * 减刑假释审批流程到省局，经办人办案时生成案件号
	 */
	public int createProvinceCaseNo(Map<String,Object> paramMap, SystemUser user){
		Integer result = -1;
		paramMap.put("flowdraftids", paramMap.get("flowdraftid"));
        List<Map> list = MapUtil.turnKeyToLowerCaseOfList(uvFlowMapper.getSJCaseLiAnData(paramMap));
        if(list.size()==0){
            //流程流转
        	paramMap.put("departid", user.getDepartid());
            result=uvFlowService.provinceLiAnForGD(paramMap);
        }else{
        	result = 1;
        }
		return result;
	}
}
