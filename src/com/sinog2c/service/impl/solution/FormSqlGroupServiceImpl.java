package com.sinog2c.service.impl.solution;

import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.sinog2c.dao.api.solution.FormSqlGroupMapper;
import com.sinog2c.dao.api.system.SystemLogMapper;
import com.sinog2c.model.solution.FormSqlGroup;
import com.sinog2c.model.system.SystemLog;
import com.sinog2c.model.system.SystemUser;
import com.sinog2c.service.api.solution.FormSqlGroupService;
import com.sinog2c.service.impl.base.ServiceImplBase;
import com.sinog2c.util.common.IPUtil;
import com.sinog2c.util.common.StringNumberUtil;

@Service("formSqlGroupService")
public class FormSqlGroupServiceImpl extends ServiceImplBase implements  FormSqlGroupService{
	@Autowired
	private SystemLogMapper systemLogMapper;
	@Autowired
	FormSqlGroupMapper formSqlGroupMapper;
	
	public List<FormSqlGroup> getTreeDataOfFormSqlGroup(Map<String,Object> map) {
		return formSqlGroupMapper.getFormSqlGroupData(map);
	}

	@Override
	public FormSqlGroup getFormSqlGroupData(Map<String,Object> map) {
		List<FormSqlGroup> result = formSqlGroupMapper.getFormSqlGroupData(map);
		return null!=result&&result.size()>0?result.get(0):null;
	}

	@Override
	@Transactional
	public int saveDetailSqlGroup(Map<String,Object> map, SystemUser su) {
		Object operateObj = map.get("operate");
		SystemLog log = new SystemLog();
        log.setOpid(su.getUserid());
        log.setOpname(su.getName());
        log.setLoginmac("客户端IP:"+IPUtil.getLocalIP());
        log.setLoginip("客户端IP:" + IPUtil.getLocalIP());
        log.setLogtype("查询方案操作");
        log.setOpaction("方案细节SQL组新增/修改操作");
        log.setOpcontent("方案细节SQL组更新,sqlgroupid="+map.get("sqlgroupid")+";sqlgroupname="+map.get("sqlgroupname"));
        log.setOrgid(su.getOrgid());
        log.setStatus(1);
        log.setRemark("操作成功!");
        systemLogMapper.insertSelective(log);
		if(StringNumberUtil.notEmpty(operateObj)){
			if("new".equals(operateObj.toString())){
				map.put("createmender", su.getUserid());
				return formSqlGroupMapper.insertByMap(map);
			}else if("edit".equals(operateObj.toString())){
				map.put("updatemender", su.getUserid());
				return formSqlGroupMapper.updateByMap(map);
			}
		}
		return 0;
	}

	@Override
	@Transactional
	public int deleteDetailSqlGroup(String sqlgroupid,SystemUser operator){
		SystemLog log = new SystemLog();
        log.setOpid(operator.getUserid());
        log.setOpname(operator.getName());
        log.setLoginmac("客户端IP:"+IPUtil.getLocalIP());
        log.setLoginip("客户端IP:" + IPUtil.getLocalIP());
        log.setLogtype("查询方案操作");
        log.setOpaction("方案细节SQL组删除操作");
        log.setOpcontent("方案细节SQL组删除,sqlgroupid="+sqlgroupid);
        log.setOrgid(operator.getOrgid());
        log.setStatus(1);
        log.setRemark("操作成功!");
        systemLogMapper.insertSelective(log);
		return formSqlGroupMapper.deleteById(sqlgroupid);
	}

}
