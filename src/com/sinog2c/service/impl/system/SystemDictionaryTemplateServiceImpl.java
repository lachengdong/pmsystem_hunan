package com.sinog2c.service.impl.system;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sinog2c.dao.api.system.SystemDictionaryTemplateMapper;
import com.sinog2c.model.system.SystemDictionaryTemplate;
import com.sinog2c.service.api.system.SystemDictionaryTemplateService;
import com.sinog2c.service.impl.base.ServiceImplBase;
import com.sinog2c.util.common.MapUtil;


/**
 * @describe:操作幅度参照信息的service的实现类
 * @author YangZR
 * @date 2014-7-25 上午09:30:18
 */
@Service("systemDictionaryTemplateService")
public class SystemDictionaryTemplateServiceImpl extends ServiceImplBase implements SystemDictionaryTemplateService{
	
	@Autowired
	private SystemDictionaryTemplateMapper systemDictionaryTemplateMapper;

	public SystemDictionaryTemplateMapper getSystemDictionaryTemplateMapper() {
		return systemDictionaryTemplateMapper;
	}

	public void setSystemDictionaryTemplateMapper(
			SystemDictionaryTemplateMapper systemDictionaryTemplateMapper) {
		this.systemDictionaryTemplateMapper = systemDictionaryTemplateMapper;
	}

	@Override
	public int insert(SystemDictionaryTemplate record) {
		return this.systemDictionaryTemplateMapper.insert(record);
	}

	@Override
	public int countAllByCondition(Map<String, Object> map) {
		return this.systemDictionaryTemplateMapper.countAllByCondition(map);
	}

	@Override
	public List<SystemDictionaryTemplate> selectAllByCondition(
			Map<String, Object> map) {
		return this.systemDictionaryTemplateMapper.selectAllByCondition(map);
	}
	
	@Override
	public List<Map<String,Object>> getSystemDictionaryTemplate(Map<String,Object> map){
		return MapUtil.convertKeyList2LowerCase(systemDictionaryTemplateMapper.getSystemDictionaryTemplate(map));
	}

	@Override
	public int deleteByEcolname(String tempid,String ecolname,String ename) {
		return this.systemDictionaryTemplateMapper.deleteByEcolname(tempid,ecolname,ename);
	}

	@Override
	public List<SystemDictionaryTemplate> selectAllByTempid(String tempid) {
		return this.systemDictionaryTemplateMapper.selectAllByTempid(tempid);
	}
	
}
