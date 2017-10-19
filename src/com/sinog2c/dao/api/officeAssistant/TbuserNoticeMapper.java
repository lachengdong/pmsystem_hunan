package com.sinog2c.dao.api.officeAssistant;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import com.sinog2c.model.commutationParole.TbxfCommuteParoleBatch;
import com.sinog2c.model.flow.UvFlow;
import com.sinog2c.model.officeAssistant.TbuserNotice;

/**
 * 用户通知信息表的数据库操作
 * 
 * @author shily 2014-7-9 15:32:34
 */
@Component("tbuserNoticeMapper")
public interface TbuserNoticeMapper {
	/**
	 * 新增数据
	 * 
	 * @author shily 2014-7-9 15:32:34
	 */
	//int insert(TbuserNotice record);

	List<UvFlow> queryAllflowe(String floweid);
	/**
	 * 根据str查看所有的tbusernotice
	 */
	List<TbuserNotice> queryNotice(@Param("crimid")String str);
	/**
	 * 新增数据
	 * 
	 * @author shily 2014-7-9 15:32:34
	 */
	int insertMessage(TbuserNotice record);

	int insertMessageNew(TbuserNotice record);
	/**
	 * 新增数据（只处理非空字段）
	 * 
	 * @author shily 2014-7-9 15:32:34
	 */
	int insertSelective(TbuserNotice record);

	/**
	 * 删除单条数据
	 * 
	 * @author shily 2014-7-9 15:32:34
	 */
	int deleteNoticeByid(int noticeid);

	/**
	 * 删除多条数据
	 * 
	 * @author shily 2014-7-9 15:32:34
	 */
	int delectIdList(List idlist);

	/**
	 * 修改数据
	 * 
	 * @author shily 2014-7-9 15:32:34
	 */
	int update(TbuserNotice record);

	/**
	 * 修改数据
	 * 
	 * @author shily 2014-7-9 15:32:34
	 */
	int updateMseeage();

	/**
	 * 查询数据
	 * 
	 * @author shily 2014-7-9 15:32:34
	 */
	List<TbuserNotice> selectAll(int type);
	
	/**
	 * 查询授权管理数据
	 */
	List<TbuserNotice> selectGrantList(@Param("type") int type,@Param("userid") String userid,@Param("key") String key,
			@Param("start") int start,@Param("end") int end ,@Param("sortField") String sortField,@Param("sortOrder") String sortOrder);
	
	int selectGrantCount(@Param("type") int type,@Param("userid") String userid,@Param("key") String key);

	/**
	 * 方法描述：查询被授授权管理数据
	 */
	List<TbuserNotice> selectGrantList_bs(@Param("type") int type,@Param("userid") String userid,@Param("key") String key,
			@Param("start") int start,@Param("end") int end ,@Param("sortField") String sortField,@Param("sortOrder") String sortOrder);
	
	int selectGrantCount_bs(@Param("type") int type,@Param("userid") String userid,@Param("key") String key);

	/**
	 * 根据消息id查询消息详情
	 * 
	 * @author shily 2014-7-9 15:32:34
	 */
	TbuserNotice selectNoticeByNoticeId(int noticeid);

	/**
	 * 查询符合条件的数据总数
	 * 
	 * @author huzl 2014-7-9 15:32:34
	 */
	int selectCount(@Param("type") int type, @Param("opid") String opid,
			@Param("title") String title);

	/**
	 * 查询分页显示数据
	 * 
	 * @author huzl 2014-7-9 15:32:34
	 */
	List<TbuserNotice> selectDataById(@Param("type") int type,@Param("opid") String opid, @Param("title") String title,
			@Param("start") int start, @Param("end") int end,@Param("sortField") String sortField,@Param("sortOrder") String sortOrder);
	/**
	 * 获取本周日程列表	
	 * @return
	 */
	List<TbuserNotice> getCurrentWeekDataList(@Param("type") int type, @Param("startDate") Date startDate, @Param("endDate") Date endDate, @Param("opid") String opid);
	
	/**
	 * 获取某日日程列表
	 * @param type
	 * @param currentDate
	 * @param opid
	 * @return
	 */
	List<TbuserNotice> getCurrentDateDataList(@Param("type") int type, @Param("currentDate") Date currentDate, @Param("opid") String opid);
	
	/**
	 * 获取本月日程列表
	 * @return
	 */
	List<TbuserNotice> getCurrentMonthDataList(@Param("type") int type, @Param("startDate") Date startDate, @Param("endDate") Date endDate, @Param("opid") String opid);
	
	Map getNoticePk();
	
	List<TbuserNotice> getAllNotice(@Param("messagetype") int messagetype, @Param("state") int state, @Param("userid") String userid);
	
	List<TbuserNotice> getNextNotice(HashMap<String,String> map);
	
	List<TbuserNotice> getUserNotice(Map<String, Object> map);
	
	List<TbuserNotice> getAllEvent(Map<String, Object> map);
	
	void setEventStatus(Map<String, Object> map);
	
	void setEvent(Map<String, Object> map);
	
	List<TbxfCommuteParoleBatch> getBath(Map<String, Object> map);
	
	void insertDataToUserNotice(TbuserNotice notice);
	
	List<String> getAllbatchFromUserNotice();
	
	List<TbuserNotice> getNoticeList();
	
	int selectNoticeid();
	
	void delectNotice(String str);
	
	int getCount(Map map);
	
	List<Map> checkXZUserBSGrant(Map map);
	
	List<Map> checkXZUserGrantids(Map map);
	
	List<Map> queryMinGrantByNoticeids(Map map);
}