package com.sinog2c.service.api.common;

import java.util.ArrayList;
import java.util.Map;

import com.sinog2c.model.system.SystemUser;


/**
 * 特殊服务接口
 */
public interface SpecialService{
	
	public int jxjsFJQMeetRecord(Map<String,Object> paramMap, SystemUser user);
	
	public int jxjsJQMeetRecord(Map<String,Object> paramMap, SystemUser user);
	
	public int jxjsKSMeetRecord(Map<String,Object> parammap, SystemUser user);
	
	public int jxjsPSHMeetRecord(Map<String,Object> paramMap, SystemUser user);
	
	public int jxjsJYZMeetRecord(Map<String,Object> paramMap, SystemUser user);
	
	public int saveFJQUserInFlow(Map<String,Object> paramMap, SystemUser user);
	
	public int jxjsSJCHYMeetRecord(Map<String,Object> parammap, SystemUser user);
	
	public int jxjsSJPSHMeetRecord(Map<String,Object> paramMap, SystemUser user);
	
	//保外
	public int bwjyFJQMeetRecord(Map<String,Object> parammap, SystemUser user);
	
	public int bwjyJQMeetRecord(Map<String,Object> parammap, SystemUser user);
	
	public int bwjyYYZDXZMeetRecord(Map<String,Object> parammap, SystemUser user);
	
	public int bwjySWKMeetRecord(Map<String,Object> parammap, SystemUser user);//生卫科
	
	public int bwjyKSMeetRecord(Map<String,Object> parammap, SystemUser user);
	
	public int bwjyPSHMeetRecord(Map<String,Object> parammap, SystemUser user);
	
	public int bwjyJYZMeetRecord(Map<String,Object> parammap, SystemUser user);
	
	public int bwjySJCHYMeetRecord(Map<String,Object> parammap, SystemUser user);
	
	public int bwjySJPSHMeetRecord(Map<String,Object> paramMap, SystemUser user);
	
	//狱政
	public int addYZGLData(ArrayList tempData,Map<String,Object> map);
	
//	/**
//	 * 外来人员入监
//	 * @param paramMap
//	 * @param user
//	 * @return
//	 */
//	public int dealForeignPersonRJ(Map<String,Object> paramMap, SystemUser user);
	
//	/**
//	 * 罪犯离开监管区
//	 * @param paramMap
//	 * @param user
//	 * @return
//	 */
//	public int dealCriminalLeaveArea(Map<String,Object> paramMap, SystemUser user);
	
//	/**
//	 * 外来物品入监
//	 * @param paramMap
//	 * @param user
//	 * @return
//	 */
//	public int dealForeignGoodsInprison(Map<String,Object> paramMap, SystemUser user);
	
//	/**
//	 * 外来车辆入监
//	 * @param paramMap
//	 * @param user
//	 * @return
//	 */
//	public int dealForeignCarsInprison(Map<String,Object> paramMap, SystemUser user);
	
//	/**
//	 * 服刑人员监区调动
//	 * @param paramMap
//	 * @param user
//	 * @return
//	 */
//	public int dealCriminalTransferApply(Map<String,Object> paramMap, SystemUser user);
	
//	/**
//	 * 罪犯狱政惩处审批处理
//	 * @param paramMap
//	 * @param user
//	 * @return
//	 */
//	public int dealConfinementApprove(Map<String,Object> paramMap, SystemUser user);
	
//	/**
//	 * 高危犯审批处理
//	 * @param paramMap
//	 * @param user
//	 * @return
//	 */
//	public int dealDangerousApprove(Map<String,Object> paramMap, SystemUser user);
	
//	/**
//	 * 优秀互监组状励审批的处理
//	 * @param paramMap
//	 * @param user
//	 * @return
//	 */
//	public int dealTbyzExcellentSupervision(Map<String,Object> paramMap, SystemUser user);
	
	/**
	 * 流程走下一级时，将当前节点、下一级节点保存到TBFLOW_PERSONAPPROVE表中，
	 * 用于下一级节点退回到当前节点时，能够找到流程路线。
	 * @author	YangZR		2015-10-16
	 */
	public int saveCurrNodeidAndNextNodeid(Map<String,Object> paramMap, SystemUser user);
	
//	/**
//	 * 流程审批通过后，将批量奖分的数据（数据格式：crimid1@score1,crimid2@score2，数据存在表单节点ids里）存入数据库
//	 * @author	YangZR		2015-10-19
//	 */
//	public int dealBatchJiangFen(Map<String,Object> paramMap, SystemUser user);
	
	/**
	 * 流程审批通过后，更新罪犯基本信息的分级处遇等级：更新表tbprisoner_base_crime的字段chargeclass，用查询方案实现
	 * @author	YangZR		2015-10-19
	 */
	public int dealCriminalFJCY(Map<String,Object> paramMap, SystemUser user);
	
	/**
	 * 减刑假释审批流程到省局，经办人办案时生成案件号
	 */
	public int createProvinceCaseNo(Map<String,Object> paramMap, SystemUser user);
	
}
