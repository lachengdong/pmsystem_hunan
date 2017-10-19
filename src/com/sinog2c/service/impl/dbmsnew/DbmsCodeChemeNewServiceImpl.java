package com.sinog2c.service.impl.dbmsnew;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sinog2c.dao.api.dbmsnew.DbmsCodeChemeNewMapper;
import com.sinog2c.model.dbmsnew.DbmsCodeChemeNew;
import com.sinog2c.service.api.dbmsnew.DbmsCodeChemeNewService;
import com.sinog2c.service.impl.base.ServiceImplBase;
import com.sinog2c.util.common.MapUtil;

@Service("dbmsCodeChemeNewService")  
public class DbmsCodeChemeNewServiceImpl extends ServiceImplBase implements DbmsCodeChemeNewService{

	@Autowired
	private DbmsCodeChemeNewMapper dbmsCodeChemeNewMapper;
	
	@Override
	public List<Map<String, Object>> selectByPrimaryKey(DbmsCodeChemeNew key) {
		List<Map<String, Object>> listMap = dbmsCodeChemeNewMapper.selectByPrimaryKey(key);
		listMap = MapUtil.convertKeyList2LowerCase(listMap);
		return listMap;
	}

	@Override
	public int deleteByPrimaryKey(DbmsCodeChemeNew key) {
		int num = dbmsCodeChemeNewMapper.deleteByPrimaryKey(key);
		return num;
	}

	@Override
	public int insert(DbmsCodeChemeNew record) {
		int num = dbmsCodeChemeNewMapper.insert(record);
		return num;
	}

	@Override
	public int insertSelective(DbmsCodeChemeNew record) {
		int num = dbmsCodeChemeNewMapper.insertSelective(record);
		return num;
	}

	@Override
	public int updateByPrimaryKeySelective(DbmsCodeChemeNew record) {
		int num = dbmsCodeChemeNewMapper.updateByPrimaryKeySelective(record);
		return num;
	}

	@Override
	public int updateByPrimaryKey(DbmsCodeChemeNew record) {
		int num = dbmsCodeChemeNewMapper.updateByPrimaryKey(record);
		return num;
	}

	@Override
	public int countAll() {
		int num = dbmsCodeChemeNewMapper.countAll();
		return num;
	}

	@Override
	public int countByCondition(Map<String, Object> map) {
		int num = dbmsCodeChemeNewMapper.countByCondition(map);
		return num;
	}

	@Override
	public int insertByMap(Map<String, Object> map) {
		int num = dbmsCodeChemeNewMapper.insertByMap(map);
		return num;
	}

	@Override
	public List<DbmsCodeChemeNew> listAll() {
		List<DbmsCodeChemeNew> result = dbmsCodeChemeNewMapper.listAll();
		return result;
	}

	@Override
	public List<DbmsCodeChemeNew> listByCondition(Map<String, Object> map) {
		map = processMapPage(map);
		List<DbmsCodeChemeNew> result = dbmsCodeChemeNewMapper.listByCondition(map);
		return result;
	}
	@Override
	public List<DbmsCodeChemeNew> alllistcodescheme(Map<String, Object> map) {
		List<DbmsCodeChemeNew> result = dbmsCodeChemeNewMapper.listByCondition(map);
		return result;
	}

	@Override
	public int updateByMap(Map<String, Object> map) {
		int num = dbmsCodeChemeNewMapper.updateByMap(map);
		return num;
	}
	@Override
	public int getNextId() {
		int num = dbmsCodeChemeNewMapper.getNextId();
		return num;
	}

}
