package com.sinog2c.service.api.officeAssistant;

import java.util.Date;
import java.util.List;

import com.sinog2c.model.officeAssistant.TbuserNotice;

/**
 * 日程安排
 * 
 * @author huzl 2014-7-17 15:32:45
 */
public interface SchedulingService {
	/**
	 * 查询列表数据
	 * 
	 * @author huzl 2014-7-17 15:44:27
	 */
	public List<TbuserNotice> getData();

	/**
	 * 查询列表数据（分页、模糊查询）
	 * 
	 * @author huzl 2014-7-17 15:44:27
	 */
	public List<TbuserNotice> getDataById(int type, String opid, String title,
			int start, int end,String sortField,String sortOrder);

	/**
	 * 添加数据
	 * 
	 * @author huzl 2014-7-17 15:44:27
	 */
	public int addData(TbuserNotice t);

	/**
	 * 根据id查询消息详情
	 * 
	 * @author huzl 2014-7-17 15:44:27
	 */
	public TbuserNotice getDataByNoticeId(int noticeid);

	/**
	 * 修改数据
	 * 
	 * @author huzl 2014-7-17 15:44:27
	 */
	public int updateSchedule(TbuserNotice tbuserNotice);

	/**
	 * 删除数据
	 * 
	 * @author huzl 2014-7-17 15:44:27
	 */
	public int deleteNoticeByid(int noticeid);

	/**
	 * 查询符合条件的数据总数（分页用，总条数）
	 * 
	 * @author huzl 2014-7-17 15:44:27
	 */
	public int getScheduleCount(int type, String opid, String title);
	
	/**
	 * 获取本周日程列表
	 * @param type
	 * @param startDate
	 * @param endDate
	 * @param opid
	 * @return
	 */
	public List<TbuserNotice> getCurrentWeekDataList(int type, Date startDate, Date endDate, String opid);
	
	
	/**
	 * 获取指定日期日程
	 * 
	 * @param type
	 * @param currentDate
	 * @param opid
	 * @return
	 */
	public List<TbuserNotice> getCurrentDateDataList(int type, Date currentDate, String opid);
	
	/**
	 * 获取本月日程列表
	 * @param type
	 * @param startDate
	 * @param endDate
	 * @param opid
	 * @return
	 */
	public List<TbuserNotice> getCurrentMonthDataList(int type, Date startDate, Date endDate, String opid);
	

}
