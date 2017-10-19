package com.sinog2c.service.api.meeting;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.sinog2c.model.meeting.TbprisonerMeeting;
import com.sinog2c.model.system.SystemUser;
public interface TbprisonerMeetingService {

	List<TbprisonerMeeting> getMeetingList(String sdid, String key,
			String sortField, String sortOrder, int pageIndex, int pageSize);

	int getCount(String sdid, String key);

	int savemeet(SystemUser user,HttpServletRequest request);

	TbprisonerMeeting getOneMeet(String sdid, String mkey);
	
	List<Map<String,Object>> getMeetTreeList(Map<String,Object> map,String type);
	
	List<Map> selectMeetingDetail(Map<String,Object> map);
	
	List<Map> selectPublicityDetail(Map<String,Object> map);

}
