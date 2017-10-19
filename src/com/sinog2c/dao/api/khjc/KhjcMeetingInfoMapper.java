package com.sinog2c.dao.api.khjc;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.sinog2c.model.khjc.KhjcMeetingInfo;
import com.sinog2c.model.khjc.KhjcTbflowBaseDoc;

public interface KhjcMeetingInfoMapper {
    int deleteByPrimaryKey(String docid);

    int insert(KhjcMeetingInfo record);

    int insertSelective(KhjcMeetingInfo record);

    KhjcMeetingInfo selectByPrimaryKey(String docid);

    int updateByPrimaryKeySelective(KhjcMeetingInfo record);

    int updateByPrimaryKeyWithBLOBs(KhjcMeetingInfo record);

    int updateByPrimaryKey(KhjcMeetingInfo record);

	/**
	 * 获取当前页面数据（分页）
	 * 
	 * @author yanqutai 
	 */
	List<KhjcMeetingInfo> getKhjcMeetingByType(@Param("start") int start,@Param("end") int end,@Param("key") String key,@Param("tempid") String tempid,
			@Param("sortField") String sortField,@Param("sortOrder") String sortOrder);

}