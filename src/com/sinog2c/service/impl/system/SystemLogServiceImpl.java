package com.sinog2c.service.impl.system;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gkzx.common.GkzxCommon;
import com.sinog2c.dao.api.system.SystemLogMapper;
import com.sinog2c.dao.api.system.SystemResourceMapper;
import com.sinog2c.model.system.SystemLog;
import com.sinog2c.model.system.SystemResource;
import com.sinog2c.model.system.SystemUser;
import com.sinog2c.mvc.message.JSONMessage;
import com.sinog2c.service.api.system.SystemLogService;
import com.sinog2c.service.impl.base.ServiceImplBase;
import com.sinog2c.util.common.IPUtil;
import com.sinog2c.util.common.StringNumberUtil;

@Service("systemLogService")
public class SystemLogServiceImpl extends ServiceImplBase implements SystemLogService {

	@Resource
	private SystemLogMapper systemLogMapper;
	@Autowired
	private SystemResourceMapper systemResourceMapper;
	
	@Override
	public int add(SystemLog log, SystemUser operator) {
		if(null == log){
			return -1;
		}
		//
		if(null != operator){
			String opid = operator.getUserid();
			String opname = operator.getName();
			log.setOpid(opid);
			log.setOpname(opname);
			log.setOrgid(operator.getOrgid()); // orgid
			log.setLoginmac("客户端IP:"+IPUtil.getLocalIP());
			log.setLoginip("客户端IP:" + IPUtil.getLocalIP());
		}
		log.setOptime(new Date());
		//
		try{
			int result = systemLogMapper.insertSelective(log);
			return result;
		} catch (Exception e) {
		}
		return 0;
	}
	@Override
	public int add(SystemLog log, SystemUser operator, JSONMessage message) {
		if(null == log){
			return -1;
		}
		//
		if (null != message) {
			log.setOpcontent(message.getInfo());
			log.setRemark(message.getInfo());
			log.setStatus(message.getStatus());
			log.setOrgid(operator.getOrgid());
		}
		//
		return add(log, operator);
	}

	@Override
	public SystemLog getByLogId(int logid) {
		SystemLog systemLog = systemLogMapper.getByLogId(logid);
		return systemLog;
	}

	@Override
	public int countBySearch(String logtype, String key,String starttime,String endtime) {
		int result = systemLogMapper.countBySearch(logtype, key, starttime, endtime);
		return result;
	}
	@Override
	public List<SystemLog> search(int pageIndex, int pageSize, String logtype, String key,
			String sortField, String sortOrder,String starttime,String endtime) {
		// 计算页码
		int start = pageIndex * pageSize + 1;
		int end = start + pageSize -1;
		List<SystemLog> result = systemLogMapper.search(start, end, logtype, key, sortField, sortOrder, starttime, endtime);
		return result;
	}
	@Override
	public int removelog(String starttime,String endtime) {
		int result = systemLogMapper.removelog(starttime, endtime);
		return result;
	}
	@Override
	public int addLog(Map<String,Object> params,SystemUser operator) {
		SystemLog log = new SystemLog();
		String resname = null;
		String remark = null;
		String status = "0";
		String logtype = GkzxCommon.OPERATE;
		if(params.get("operateType").equals(GkzxCommon.ADD)){
			logtype = GkzxCommon.BUTTON_NEW+logtype;
		}else if(params.get("operateType").equals(GkzxCommon.EDIT)){
			logtype = GkzxCommon.BUTTON_MODIFY+logtype;
		}else if(params.get("operateType").equals(GkzxCommon.DEL)){
			logtype = GkzxCommon.BUTTON_DEL+logtype;
		}if(params.get("operateType").equals(GkzxCommon.DISTRI)){
			logtype = GkzxCommon.BUTTON_DISTRI+logtype;
		}if(params.get("operateType").equals(GkzxCommon.REPLY)){
			logtype = GkzxCommon.BUTTON_REPLY+logtype;
		}
		SystemResource resource = systemResourceMapper.getByResourceId(params.get("menuid").toString());
		if(!StringNumberUtil.isNullOrEmpty(resource)){
			resname = resource.getName();
		}
		if(params.get("status").equals(1)){
			status = GkzxCommon.ONE;
			remark = logtype+GkzxCommon.RESULT_SUCCESS;
		}else if (params.get("status").equals(0)){
			status = GkzxCommon.ZERO;
			remark = logtype+GkzxCommon.RESULT_ERROR;
		}
		log.setOpaction(logtype);
		log.setLogtype(resname+logtype);
		log.setOpcontent(resname+logtype+GkzxCommon.SEMICOLON+"resid="+params.get("menuid").toString());
		log.setLoginmac("客户端IP:"+IPUtil.getLocalIP());
		log.setLoginip("客户端IP:" + IPUtil.getLocalIP());
		log.setStatus(Integer.parseInt(status));
		log.setRemark(remark);
		log.setOrgid(operator.getOrgid());
		log.setOptime(new Date());
		log.setOpid(operator.getUserid());
		log.setOpname(operator.getName());
		try{
			int result = systemLogMapper.insertSelective(log);
			return result;
		} catch (Exception e) {
		}
		return add(log, operator);
	}

}
