package com.sinog2c.service.impl.officeAssistant;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.sinog2c.dao.api.officeAssistant.TbuserNoticeMapper;
import com.sinog2c.dao.api.officeAssistant.TbuserUserNoticeMapper;
import com.sinog2c.dao.api.system.SystemUserMapper;
import com.sinog2c.model.commutationParole.TbxfCommuteParoleBatch;
import com.sinog2c.model.officeAssistant.TbuserNotice;
import com.sinog2c.model.officeAssistant.TbuserUserNotice;
import com.sinog2c.model.system.SystemUser;
import com.sinog2c.service.api.officeAssistant.UserNoticeService;
import com.sinog2c.service.impl.base.ServiceImplBase;

/**
 * 用户通知
 * 
 * @author Administrator
 * 
 */
@Service("userNoticeService")
public class UserNoticeServiceImpl extends ServiceImplBase implements UserNoticeService {

	@Autowired
	private TbuserNoticeMapper tbuserNoticeMapper;
	@Autowired
	private SystemUserMapper systemUserMapper;
	@Autowired
	private TbuserUserNoticeMapper tbuserUserNoticeMapper;

	/**
	 * 获取授权管理数据
	 * 
	 * @author shily 2014-7-9 15:37:24
	 */
	@Override
	public List<TbuserNotice> getDataForImpowerManage(int type) {
		return tbuserNoticeMapper.selectAll(type);
	}

	/**
	 * 获取用户
	 * 
	 * @author shily 2014-7-13 15:39:47
	 */
	@Override
	public List<SystemUser> getAllUserForNotice() {
		return systemUserMapper.selectAll();
	}

	/**
	 * 保存消息
	 * 
	 * @author shily 2014-7-13 15:39:47
	 */
	@Override
	public int saveMessage(TbuserNotice tbuserNotice) {
		return tbuserNoticeMapper.insertMessage(tbuserNotice);
	}

	@Override
	public int deleteNoticeByid(int id) {
		// TODO Auto-generated method stub
		return tbuserNoticeMapper.deleteNoticeByid(id);

	}

	/**
	 * 批量删除授权、消息
	 * 
	 * @author wangxf
	 */
	@Override
	public int delectIdList(List idlist) {
		// TODO Auto-generated method stub
		return tbuserNoticeMapper.delectIdList(idlist);
	}

	/**
	 * 修改授权
	 * 
	 * @author wangxf
	 */
	@Override
	public int updateMessage(TbuserNotice tbuserNotice) {
		// TODO Auto-generated method stub
		return tbuserNoticeMapper.updateMseeage();
	}

	@Override
	@Transactional
	public int saveUserMessage(TbuserNotice tbuserNotice,
			TbuserUserNotice tbuserUserNotice) {
		int result = tbuserNoticeMapper.insertMessageNew(tbuserNotice);
		String[] userArr = tbuserNotice.getUsername().split(",");
		for(int i=0; i<userArr.length; i++) {
			tbuserUserNotice.setUserid(userArr[i]);
			tbuserUserNoticeMapper.insertSelective(tbuserUserNotice);
		}
		return result;
	}

	@Override
	public String getNoticePk() {
		Map<String,Object> map = tbuserNoticeMapper.getNoticePk();
		return map.get("NOTICEID").toString();
	}
	
	@Override
	public List<TbuserUserNotice> getAllNotice(HashMap map) {
		return tbuserUserNoticeMapper.getAllNotice(map);
	}
	
	@Override
	public List<TbuserNotice> getAlluserNotice(int messagetype, int state, String userid) {
		return tbuserNoticeMapper.getAllNotice(messagetype, state, userid);
	}
	
	@Override
	public int updateNoticeByusernotice(HashMap<String,String> map) {
		return tbuserUserNoticeMapper.updateNoticeByusernotice(map);
	}
	
	@Override
	public List<TbuserNotice> getNextNotice(HashMap<String,String> map) {
		return tbuserNoticeMapper.getNextNotice(map);
	}

	@Override
	public List<TbuserNotice> getUserNotice(Map<String, Object> map) {
		return tbuserNoticeMapper.getUserNotice(map);
	}


	@Override
	public void setEventStatus(Map<String, Object> map) {
		 tbuserNoticeMapper.setEventStatus(map);
		
	}

	@Override
	public List<TbuserNotice> getAllEvent(Map<String, Object> map) {
		return tbuserNoticeMapper.getAllEvent(map);
	}

	@Override
	public void setEvent(Map<String, Object> map) {
		tbuserNoticeMapper.setEvent(map);
	}

	@Override
	public void delectIdList(Map<String, Object> map) {
		tbuserNoticeMapper.setEvent(map);
	}

	@Override
	public List<TbxfCommuteParoleBatch> getBath(Map<String, Object> map) {
		return tbuserNoticeMapper.getBath(map);
	}

	@Override
	public void insertDataToUserNotice(TbuserNotice notice) {
		tbuserNoticeMapper.insertDataToUserNotice(notice);
	}

	@Override
	public List<TbuserNotice> getNoticeList() {
		return tbuserNoticeMapper.getNoticeList();
	}

	@Override
	public void delectNotice(String str) {
		tbuserNoticeMapper.delectNotice(str);
		
	}

	@Override
	public int getCount(Map map) {
		return tbuserNoticeMapper.getCount(map);
	}

}
