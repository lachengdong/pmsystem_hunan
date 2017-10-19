package com.sinog2c.service.impl.system;

import static com.sinog2c.util.common.StringNumberUtil.parseInt;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sinog2c.dao.api.system.TbcourtconnectioninfoMapper;
import com.sinog2c.model.system.PrintScheme;
import com.sinog2c.model.system.SystemUser;
import com.sinog2c.model.system.Tbcourtconnectioninfo;
import com.sinog2c.mvc.message.JSONMessage;
import com.sinog2c.service.api.system.CourtVideoSessionService;
import com.sinog2c.util.common.JsonUtil;
import com.sinog2c.util.common.StringNumberUtil;
@Service
public class CourtVideoSessionServiceImpl implements CourtVideoSessionService {

	@Autowired
	private TbcourtconnectioninfoMapper tbcourtconnectioninfoMapper;

	@Override
	public Object getVideoSessionList(HttpServletRequest request) {
		Object uobj = request.getSession().getAttribute("session_user_key");
		SystemUser user = null;
		if(uobj instanceof SystemUser){
			user = (SystemUser)uobj;
		}
		// 页码, 0 开始
		String pageIndexStr = request.getParameter("pageIndex");
		// 每页显示条数
		String pageSizeStr = request.getParameter("pageSize");
		String sortField = request.getParameter("sortField");
		String sortOrder = request.getParameter("sortOrder");
		int pageIndex = parseInt(pageIndexStr, 0);
		int pageSize  = parseInt(pageSizeStr, 20);
		
		int total = 0;
		List<Map<String,String>> list = null;
		// 计算页码
		int start = pageIndex * pageSize + 1;
		int end = start + pageSize -1;
		Map<String,Object> paramMap = new HashMap<String, Object>();
		paramMap.put("start", start);
		paramMap.put("end", end);
		paramMap.put("sortField", sortField);
		paramMap.put("sortOrder", sortOrder);
		paramMap.put("orgid", user.getDepartid());
		total = tbcourtconnectioninfoMapper.getVideoSessionListCount(paramMap);
		list =  tbcourtconnectioninfoMapper.getVideoSessionList(paramMap);
		JSONMessage message = JSONMessage.newMessage();
		message.setTotal(total);
		message.setData(list);
		return message;
	}

	@Override
	public Object getVideoSessionDepartments(HttpServletRequest request) {
		Object uobj = request.getSession().getAttribute("session_user_key");
		SystemUser user = null;
		if(uobj instanceof SystemUser){
			user = (SystemUser)uobj;
		}
		String key = request.getParameter("key");
		// 页码, 0 开始
		String pageIndexStr = request.getParameter("pageIndex");
		// 每页显示条数
		String pageSizeStr = request.getParameter("pageSize");
		String sortField = request.getParameter("sortField");
		String sortOrder = request.getParameter("sortOrder");
		int pageIndex = parseInt(pageIndexStr, 0);
		int pageSize  = parseInt(pageSizeStr, 20);
		
		int total = 0;
		List<Map<String,String>> list = null;
		// 计算页码
		int start = pageIndex * pageSize + 1;
		int end = start + pageSize -1;
		Map<String,Object> paramMap = new HashMap<String, Object>();
		paramMap.put("start", start);
		paramMap.put("end", end);
		paramMap.put("sortField", sortField);
		paramMap.put("sortOrder", sortOrder);
		paramMap.put("orgid", user.getDepartid());
		paramMap.put("key", key);
		total = tbcourtconnectioninfoMapper.getVideoSessionDepartmentsCount(paramMap);
		list =  tbcourtconnectioninfoMapper.getVideoSessionDepartments(paramMap);
		JSONMessage message = JSONMessage.newMessage();
		message.setTotal(total);
		message.setData(list);
		return message;
	}

	@Override
	public Object updateVideoSession(HttpServletRequest request) {
		Tbcourtconnectioninfo coinfo = new Tbcourtconnectioninfo();
		Object uobj = request.getSession().getAttribute("session_user_key");
		SystemUser user = null;
		if(uobj instanceof SystemUser){
			user = (SystemUser)uobj;
		}
		String data = request.getParameter("data");
		Object obj = null;
		List<Map> list = (List) JsonUtil.Decode(data);
		if(list!=null && list.size()>0){
			Map map = list.get(0);
			coinfo.setCid(Long.parseLong(map.get("cid").toString()));
			coinfo.setCorgid(user.getDepartid());
			coinfo.setTorgid(map.get("torgid").toString());
			coinfo.setCname(map.get("cname").toString());
			coinfo.setCip(map.get("cip").toString());
			coinfo.setCport(map.get("cport").toString());
			coinfo.setCusername(map.get("cusername").toString());
			coinfo.setCpassword(map.get("cpassword").toString());
			coinfo.setOpid(user.getUserid());
			coinfo.setOptime(new Date());
			obj = tbcourtconnectioninfoMapper.updateByPrimaryKeySelective(coinfo);
		}
		return obj;
	}

	@Override
	public Object insertVideoSession(HttpServletRequest request) {
		Tbcourtconnectioninfo coinfo = new Tbcourtconnectioninfo();
		String data = request.getParameter("data");
		Object obj = null;
		List<Map> list = (List) JsonUtil.Decode(data);
		Object uobj = request.getSession().getAttribute("session_user_key");
		SystemUser user = null;
		if(uobj instanceof SystemUser){
			user = (SystemUser)uobj;
		}
		if(list!=null && list.size()>0){
			Map map = list.get(0);
			coinfo.setCorgid(user.getDepartid());
			coinfo.setTorgid(map.get("torgid").toString());
			coinfo.setCname(map.get("cname").toString());
			coinfo.setCip(map.get("cip").toString());
			coinfo.setCport(map.get("cport").toString());
			coinfo.setCusername(map.get("cusername").toString());
			coinfo.setCpassword(map.get("cpassword").toString());
			coinfo.setOpid(user.getUserid());
			coinfo.setOptime(new Date());
			obj = tbcourtconnectioninfoMapper.insertSelective(coinfo);
		}
		return obj;
	}

	@Override
	public Object getVideoSessionById(HttpServletRequest request) {
		String cid = request.getParameter("id");
		Tbcourtconnectioninfo coinfo = null;
		if(StringNumberUtil.notEmpty(cid)){
			coinfo = tbcourtconnectioninfoMapper.selectByPrimaryKey(Long.parseLong(cid));
		}
		return coinfo;
	}

	@Override
	public Object deleteVideoSessionById(HttpServletRequest request) {
		String cid = request.getParameter("id");
		if(StringNumberUtil.notEmpty(cid)){
			tbcourtconnectioninfoMapper.deleteByPrimaryKey(Long.parseLong(cid));
		}
		return null;
	}

	@Override
	public Object deleteBatchVideoSessionByIds(HttpServletRequest request) {
		String cids = request.getParameter("ids");
		if(StringNumberUtil.notEmpty(cids)){
			String[] cidArr = cids.split(",");
			for(String cid : cidArr){
				tbcourtconnectioninfoMapper.deleteByPrimaryKey(Long.parseLong(cid));
			}
		}
		return null;
	}

	@Override
	public Object getVideoPlayDepartments(HttpServletRequest request) {
		Object uobj = request.getSession().getAttribute("session_user_key");
		SystemUser user = null;
		if(uobj instanceof SystemUser){
			user = (SystemUser)uobj;
		}
		String key = request.getParameter("key");
		
		List<Map<String,String>> list = null;
		Map<String,Object> paramMap = new HashMap<String, Object>();
		paramMap.put("orgid", user.getDepartid());
		list =  tbcourtconnectioninfoMapper.getVideoPlayDepartments(paramMap);
		JSONMessage message = JSONMessage.newMessage();
		message.setData(list);
		return message.getData();
	}
}
