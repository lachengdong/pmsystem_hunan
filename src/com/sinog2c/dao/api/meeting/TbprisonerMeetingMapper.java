package com.sinog2c.dao.api.meeting;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.sinog2c.model.meeting.TbprisonerMeeting;
@SuppressWarnings("unchecked")
public interface TbprisonerMeetingMapper {
    int insert(TbprisonerMeeting record);

    int insertSelective(TbprisonerMeeting record);
    
	int insertByMap(Map map);
	
	int updateByMap(Map map);

    int updateByExampleSelective(TbprisonerMeeting record);

	List<TbprisonerMeeting> getMeetingList(@Param("sdid")String sdid, @Param("key")String key,
			 @Param("sortField")String sortField, @Param("sortOrder")String sortOrder, @Param("start")int start, @Param("end")int end);

	int getCount(@Param("sdid")String sdid,  @Param("key")String key);

	TbprisonerMeeting getOneMeet(@Param("sdid")String sdid,@Param("mkey")String mkey);
	
	List<Map<String,Object>> getMeetTreeList(Map<String,Object> map);
	
	List<Map<String,Object>> getBWMeetTreeList(Map<String,Object> map);
	
	List<Map> selectMeetingDetail(Map<String,Object> map);
	
	List<Map> selectPublicityDetail(Map<String,Object> map);
}