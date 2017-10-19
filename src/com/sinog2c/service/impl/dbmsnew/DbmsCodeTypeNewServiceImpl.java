package com.sinog2c.service.impl.dbmsnew;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sinog2c.dao.api.dbmsnew.DbmsCodeTypeNewMapper;
import com.sinog2c.model.dbmsnew.DbmsCodeChemeNew;
import com.sinog2c.model.dbmsnew.DbmsCodeTypeNew;
import com.sinog2c.service.api.dbmsnew.DbmsCodeTypeNewService;
import com.sinog2c.service.impl.base.ServiceImplBase;
import com.sinog2c.util.common.MapUtil;

@Service("dbmsCodeTypeNewService") 
public class DbmsCodeTypeNewServiceImpl extends ServiceImplBase implements DbmsCodeTypeNewService {

	@Autowired
	private DbmsCodeTypeNewMapper dbmsCodeTypeNewMapper;
	
	@Override
	public int deleteByPrimaryKey(String codetypeid) {
		int num = dbmsCodeTypeNewMapper.deleteByPrimaryKey(codetypeid);
		return num;
	}

	@Override
	public int insert(DbmsCodeTypeNew record) {
		int num = dbmsCodeTypeNewMapper.insert(record);
		return num;
	}

	@Override
	public int insertSelective(DbmsCodeTypeNew record) {
		int num = dbmsCodeTypeNewMapper.insertSelective(record);
		return num;
	}

	@Override
	public List<Map<String, Object>> selectByPrimaryKey(DbmsCodeChemeNew keyString) {
		List<Map<String, Object>> listMap = dbmsCodeTypeNewMapper.selectByPrimaryKey(keyString);
		listMap = MapUtil.convertKeyList2LowerCase(listMap);
		return listMap;
	}

	@Override
	public int updateByPrimaryKeySelective(DbmsCodeTypeNew record) {
		int num = dbmsCodeTypeNewMapper.updateByPrimaryKey(record);
		return num;
	}

	@Override
	public int updateByPrimaryKey(DbmsCodeTypeNew record) {
		int num = dbmsCodeTypeNewMapper.updateByPrimaryKeySelective(record);
		return num;
	}

	@Override
	public int countAll() {
		int num = dbmsCodeTypeNewMapper.countAll();
		return num;
	}

	@Override
	public int countByCondition(Map<String, Object> map) {
		int num = dbmsCodeTypeNewMapper.countByCondition(map);
		return num;
	}

	@Override
	public int insertByMap(Map<String, Object> map) {
		int num = dbmsCodeTypeNewMapper.insertByMap(map);
		return num;
	}

	@Override
	public List<DbmsCodeTypeNew> listAll() {
		List<DbmsCodeTypeNew> result = dbmsCodeTypeNewMapper.listAll();
		return result;
	}

	@Override
	public List<DbmsCodeTypeNew> listByCondition(Map<String, Object> map) {
		map = processMapPage(map);
		List<DbmsCodeTypeNew> result = dbmsCodeTypeNewMapper.listByCondition(map);
		return result;
	}

	@Override
	public List<Map<String, Object>> listCodeTypeMapByPage(Map<String, Object> map) {
		List<Map<String, Object>> result = dbmsCodeTypeNewMapper.listCodeTypeMapByPage(map);
		return result;
	}

	@Override
	public int updateByMap(Map<String, Object> map) {
		int num = dbmsCodeTypeNewMapper.updateByMap(map);
		return num;
	}

}
