package com.sinog2c.service.impl.meeting;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.gkzx.common.GkzxCommon;
import com.sinog2c.dao.api.meeting.TbprisonerMeetingMapper;
import com.sinog2c.dao.api.system.TbsysDocumentMapper;
import com.sinog2c.model.meeting.TbprisonerMeeting;
import com.sinog2c.model.system.SystemUser;
import com.sinog2c.service.api.meeting.TbprisonerMeetingService;
import com.sinog2c.util.common.MapUtil;
import com.sinog2c.util.common.StringNumberUtil;

@Service("tbprisonerMeetingService")
public class TbprisonerMeetingServiceImpl implements TbprisonerMeetingService {
	@Resource
	private TbprisonerMeetingMapper tbprisonerMeetingMapper;
	@Resource
	private TbsysDocumentMapper tbsysDocumentMapper;

	@Override
	public List<TbprisonerMeeting> getMeetingList(String sdid, String key,
			String sortField, String sortOrder, int pageIndex, int pageSize) {
		int start = pageIndex * pageSize + 1;
		int end = start + pageSize - 1;
		return tbprisonerMeetingMapper.getMeetingList(sdid, key, sortField,
				sortOrder, start, end);
	}

	@Override
	public int getCount(String sdid, String key) {
		return tbprisonerMeetingMapper.getCount(sdid, key);
	}

	@Override
	public int savemeet(SystemUser user, HttpServletRequest request) {
		int rows = 0;
		Date date = new Date();
		String introduction = "";
		Map<String, Object> map = new HashMap<String, Object>();
		String operator = request.getParameter("operator");
		String tempid = request.getParameter("tempid") == null ? "" : request
				.getParameter("tempid");
		String content = request.getParameter("content") == null ? "" : request
				.getParameter("content");
		String data = request.getParameter("noteinfo") == null ? "" : request
				.getParameter("noteinfo");
		if (data != null) {
			ArrayList<Object> list = (ArrayList<Object>) JSON.parseArray(data,
					Object.class);
			if (list != null && list.size() > 0) {
				map = (Map<String, Object>) list.get(0);
				String mtype = (String) map.get("mname");
				// 会议类型(0减刑假释,1保外,2监区,3科室,4评审会,5监狱长)
				if (mtype != null) {
					if (mtype.contains("减刑、假释会议记录")) {
						map.put("mtype", 0);
						introduction = user.getOrganization().getName()
								+ "减刑、假释会议记录";
					} else if (mtype.contains("保外会议记录")) {
						map.put("mtype", 1);
						introduction = user.getOrganization().getName()
								+ "保外会议记录";
					} else if (mtype.contains("监区集体评议记录")) {
						map.put("mtype", 2);
						introduction = user.getOrganization().getName()
								+ "监区集体评议记录";
					} else if (mtype.contains("科室会议")) {
						map.put("mtype", 3);
						introduction = user.getOrganization().getName()
								+ "科室会议";
					} else if (mtype.contains("监狱减刑、假释、暂予监外执行评审会会议记录")) {
						map.put("mtype", 4);
						introduction = user.getOrganization().getName()
								+ "监狱减刑、假释、暂予监外执行评审会会议记录";
					} else if (mtype.contains("监狱长办公会会议记录")) {
						map.put("mtype", 5);
						introduction = user.getOrganization().getName()
								+ "监狱长办公会会议记录";
					} else {
						map.put("mtype", 6);
						introduction = user.getOrganization().getName()
								+ "其他会议";
					}
				}
			}
		}
		map.put("tempid", tempid);
		map.put("departid", user.getOrganization().getOrgid());
		map.put("introduction", introduction);
		map.put("content", content);
		map.put("opid", user.getUserid());
		map.put("optime", date);
		map.put("sdid", user.getOrganization().getOrgid());
		if (GkzxCommon.NEW.equals(operator)) {
			rows = tbprisonerMeetingMapper.insertByMap(map);
			rows = tbsysDocumentMapper.insertByMap(map);
		} else if (GkzxCommon.EDIT.equals(operator)) {
			rows = tbprisonerMeetingMapper.updateByMap(map);
			rows = tbsysDocumentMapper.updateByMap(map);
		}
		return rows;
	}

	@Override
	public TbprisonerMeeting getOneMeet(String sdid, String mkey) {
		return tbprisonerMeetingMapper.getOneMeet(sdid, mkey);
	}
	
	@Override
	public List<Map<String,Object>> getMeetTreeList(Map<String,Object> map,String type){
		if(StringNumberUtil.notEmpty(type)&&"bwjy".equals(type)){
			return MapUtil.convertKeyList2LowerCase(tbprisonerMeetingMapper.getBWMeetTreeList(map));
		}
		return MapUtil.convertKeyList2LowerCase(tbprisonerMeetingMapper.getMeetTreeList(map));
	}
	
	

	@Override
	public List<Map> selectMeetingDetail(Map<String,Object> map){
		List<Map> listmap = new ArrayList<Map>();
		List<Map> list = tbprisonerMeetingMapper.selectMeetingDetail(map);
		if(list!=null && list.size()>0){
			for(Object obj:list){
				Map tempmap = (Map)obj;
				tempmap =MapUtil.turnKeyToLowerCase(tempmap);
				listmap.add(tempmap);
			}
		}
		return listmap;
	}
	
	
	
	@Override
	public List<Map> selectPublicityDetail(Map<String,Object> map){
		List<Map> listmap = new ArrayList<Map>();
		List<Map> list = tbprisonerMeetingMapper.selectPublicityDetail(map);
		if(list!=null && list.size()>0){
			for(Object obj:list){
				Map tempmap = (Map)obj;
				tempmap =MapUtil.turnKeyToLowerCase(tempmap);
				listmap.add(tempmap);
			}
		}
		return listmap;
	}
	
}
