package com.sinog2c.service.impl.officeAssistant;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sinog2c.dao.api.officeAssistant.TbuserNoticeMapper;
import com.sinog2c.model.officeAssistant.TbuserNotice;
import com.sinog2c.service.api.officeAssistant.SchedulingService;
import com.sinog2c.service.impl.base.ServiceImplBase;

/**
 * 日程安排
 * 
 * @author huzl 2014-7-17 15:32:45
 */
@Service("schedulingService")
public class SchedulingServiceImpl extends ServiceImplBase implements SchedulingService {
	@Autowired
	private TbuserNoticeMapper tbuserNoticeMapper;

	/**
	 * 查询列表数据
	 * 
	 * @author huzl 2014-7-17 15:44:27
	 */
	@Override
	public List<TbuserNotice> getData() {
		return tbuserNoticeMapper.selectAll(2);
	}

	/**
	 * 根据id查询消息详情
	 * 
	 * @author huzl 2014-7-17 15:44:27
	 */
	@Override
	public TbuserNotice getDataByNoticeId(int noticeid) {
		return tbuserNoticeMapper.selectNoticeByNoticeId(noticeid);
	}

	/**
	 * 添加数据
	 * 
	 * @author huzl 2014-7-17 15:44:27
	 */
	@Override
	public int addData(TbuserNotice t) {
		return tbuserNoticeMapper.insertSelective(t);
	}

	/**
	 * 修改数据
	 * 
	 * @author huzl 2014-7-17 15:44:27
	 */
	@Override
	public int updateSchedule(TbuserNotice tbuserNotice) {
		return tbuserNoticeMapper.update(tbuserNotice);
	}

	/**
	 * 删除数据
	 * 
	 * @author huzl 2014-7-17 15:44:27
	 */
	@Override
	public int deleteNoticeByid(int noticeid) {
		return tbuserNoticeMapper.deleteNoticeByid(noticeid);
	}

	/**
	 * 查询列表数据（分页、模糊查询）
	 * 
	 * @author huzl 2014-7-17 15:44:27
	 */
	@Override
	public List<TbuserNotice> getDataById(int type, String opid, String title,
			int pageIndex, int pageSize ,String sortField,String sortOrder) {
		int start = pageIndex * pageSize + 1;
		int end = start + pageSize - 1;
		return tbuserNoticeMapper.selectDataById(type, opid, title, start, end,sortField, sortOrder);
	}

	/**
	 * 查询符合条件的数据总数（分页用，总条数）
	 * 
	 * @author huzl 2014-7-17 15:44:27
	 */
	@Override
	public int getScheduleCount(int type, String opid, String title) {
		return tbuserNoticeMapper.selectCount(type, opid, title);
	}

	@Override
	public List<TbuserNotice> getCurrentWeekDataList(int type, Date startDate,
			Date endDate, String opid) {
		// TODO Auto-generated method stub
		return tbuserNoticeMapper.getCurrentWeekDataList(type, startDate, endDate, opid);
	}

	@Override
	public List<TbuserNotice> getCurrentDateDataList(int type,
			Date currentDate, String opid) {
		// TODO Auto-generated method stub
		return tbuserNoticeMapper.getCurrentDateDataList(type, currentDate, opid);
	}

	@Override
	public List<TbuserNotice> getCurrentMonthDataList(int type, Date startDate,
			Date endDate, String opid) {
		// TODO Auto-generated method stub
		return tbuserNoticeMapper.getCurrentMonthDataList(type, startDate, endDate, opid);
	}

}
