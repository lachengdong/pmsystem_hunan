package com.sinog2c.dao.api.officeAssistant;

import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Component;

import com.sinog2c.model.officeAssistant.TbuserUserNotice;

/**
 * 用户与通知关系表的相关数据库操作
 * 
 * @author shily 2014-7-9 15:33:23
 */
@Component("tbuserUserNoticeMapper")
public interface TbuserUserNoticeMapper {
	/**
	 * 新增数据
	 * 
	 * @author shily 2014-7-9 15:33:23
	 */
	int insert(TbuserUserNotice record);

	/**
	 * 新增数据（只处理非空字段）
	 * 
	 * @author shily 2014-7-9 15:33:23
	 */
	int insertSelective(TbuserUserNotice record);
	
	List<TbuserUserNotice> getAllNotice(HashMap<String,String> map);
	
	int updateNoticeByusernotice(HashMap<String,String> map);
}