package com.sinog2c.service.impl.dbmsnew;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sinog2c.dao.api.dbmsnew.DbmsDatasTableNewMapper;
import com.sinog2c.model.dbmsnew.DbmsDatasTableNew;
import com.sinog2c.model.dbmsnew.DbmsDatasTableNewKey;
import com.sinog2c.service.api.dbmsnew.DbmsDatasTableNewService;
import com.sinog2c.service.impl.base.ServiceImplBase;

@Service("dbmsDatasTableNewService")  
public class DbmsDatasTableNewServiceImpl extends ServiceImplBase implements DbmsDatasTableNewService {

	@Autowired
	private DbmsDatasTableNewMapper dbmsDatasTableNewMapper;
	
	@Override
	public int deleteByPrimaryKey(DbmsDatasTableNewKey key) {
		int num = dbmsDatasTableNewMapper.deleteByPrimaryKey(key);
		return num;
	}

	@Override
	public int insert(DbmsDatasTableNew record) {
		int num = dbmsDatasTableNewMapper.insert(record);
		return num;
	}

	@Override
	public int insertSelective(DbmsDatasTableNew record) {
		int num = dbmsDatasTableNewMapper.insertSelective(record);
		return num;
	}

	@Override
	public DbmsDatasTableNew selectByPrimaryKey(DbmsDatasTableNewKey key) {
		DbmsDatasTableNew dbmsDatasTableNew = dbmsDatasTableNewMapper.selectByPrimaryKey(key);
		return dbmsDatasTableNew;
	}

	@Override
	public int updateByPrimaryKeySelective(DbmsDatasTableNew record) {
		int num = dbmsDatasTableNewMapper.updateByPrimaryKeySelective(record);
		return num;
	}

	@Override
	public int updateByPrimaryKey(DbmsDatasTableNew record) {
		int num = dbmsDatasTableNewMapper.updateByPrimaryKey(record);
		return num;
	}
	
	@Override
	public List<DbmsDatasTableNew> listByCondition(Map<String, Object> map) {
		List<DbmsDatasTableNew> result = dbmsDatasTableNewMapper.listByCondition(map);
		return result;
	}
	
	@Override
	public List<Map> getShcemeTables(Map<String,Object> map) {
		return dbmsDatasTableNewMapper.getShcemeTables(map);
	}

	@Override
	public List<Map> getSchemetablecolumn(Map<String,Object> map) {
		return dbmsDatasTableNewMapper.getSchemetablecolumn(map);
	}
	
	@Override
	public int updateSchemeTablecolumn(Map map) {
		return dbmsDatasTableNewMapper.updateSchemeTablecolumn(map);
	}
	
	@Override
	public int saveSchemetable(Map map) {
		return dbmsDatasTableNewMapper.saveSchemetable(map);
	}
}
