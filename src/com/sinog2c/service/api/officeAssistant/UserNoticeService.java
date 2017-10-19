package com.sinog2c.service.api.officeAssistant;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sinog2c.model.commutationParole.TbxfCommuteParoleBatch;
import com.sinog2c.model.commutationParole.TbxfSentenceAlteration;
import com.sinog2c.model.officeAssistant.TbuserNotice;
import com.sinog2c.model.officeAssistant.TbuserUserNotice;
import com.sinog2c.model.system.SystemUser;

/**
 * 用户通知接口
 * 
 * @author shily 2014-7-9 15:37:24
 */
public interface UserNoticeService {
	/**
	 * 获取用户消息数据（1授权2日程3通知）
	 * 
	 * @author shily 2014-7-9 15:37:24
	 */
	public List<TbuserNotice> getDataForImpowerManage(int type);

	/**
	 * 获取用户
	 * 
	 * @author shily 2014-7-13 15:39:47
	 */
	public List<SystemUser> getAllUserForNotice();

	/**
	 * 保存消息
	 * 
	 * @author shily 2014-7-13 15:39:47
	 */
	public int saveMessage(TbuserNotice tbuserNotice);
	
	public int saveUserMessage(TbuserNotice tbuserNotice, TbuserUserNotice tbuserUserNotice);

	/**
	 * 删除消息
	 * 
	 * @author shily 2014-7-17 15:58:14
	 */
	public int deleteNoticeByid(int id);

	/**
	 * 删除多条消息
	 * 
	 * @author wangxf 2014-7-17 15:58:41
	 */
	public int delectIdList(List idlist);

	/**
	 * 修改消息
	 * 
	 * @author shily 2014-7-17 15:59:30
	 */
	public int updateMessage(TbuserNotice tbuserNotice);
	
	public String getNoticePk();
	
	public List<TbuserUserNotice> getAllNotice(HashMap<String,String> map);
	
	public List<TbuserNotice> getAlluserNotice(int messagetype, int state, String userid);
	
	public int updateNoticeByusernotice(HashMap<String,String> map);
	
	public List<TbuserNotice> getNextNotice(HashMap<String,String> map);
	
	public List<TbuserNotice> getUserNotice(Map<String, Object> map);
	
	public void setEventStatus(Map<String, Object> map);
	
	public List<TbuserNotice> getAllEvent(Map<String, Object> map);
	
	public void setEvent(Map<String, Object> map);
	
	public void delectIdList(Map<String, Object> map);
	
	/*
	 * 获取批次，新增批次时，插入用户事件表，提示用户
	 */
	public List<TbxfCommuteParoleBatch> getBath(Map<String, Object> map);
	
	public void insertDataToUserNotice(TbuserNotice notice);
	
	public List<TbuserNotice> getNoticeList();
	
	public void delectNotice(String str);
	
	public int getCount(Map map);
	
}
