package com.sinog2c.service.impl.system;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sinog2c.dao.api.system.SystemDictionaryMapper;
import com.sinog2c.model.system.SystemDictionary;
import com.sinog2c.model.system.SystemDictionaryTemplate;
import com.sinog2c.service.api.system.SystemDictionaryService;
import com.sinog2c.service.impl.base.ServiceImplBase;


/**
 * @describe:操作幅度参照信息的service的实现类
 * @author YangZR
 * @date 2014-7-25 上午09:30:18
 */
@Service("systemDictionaryService")
public class SystemDictionaryServiceImpl extends ServiceImplBase implements SystemDictionaryService{
	@Autowired
	private SystemDictionaryMapper systemDictionaryMapper;
	
	public SystemDictionaryMapper getSystemDictionaryMapper() {
		return systemDictionaryMapper;
	}

	public void setSystemDictionaryMapper(
			SystemDictionaryMapper systemDictionaryMapper) {
		this.systemDictionaryMapper = systemDictionaryMapper;
	}

	@Override
	public int countAllByCondition(Map<String, Object> map) {
		return this.systemDictionaryMapper.countAllByCondition(map);
	}

	@Override
	public List<SystemDictionary> selectAllByCondition(
			Map<String, Object> map) {
		return this.systemDictionaryMapper.selectAllByCondition(map);
	}

	@Override
	public List<SystemDictionary> selectAllByTempid(String tempid) {
		return this.systemDictionaryMapper.selectAllByTempid(tempid);
	}
	
}
