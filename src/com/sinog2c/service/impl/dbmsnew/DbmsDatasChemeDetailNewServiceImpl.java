package com.sinog2c.service.impl.dbmsnew;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sinog2c.dao.api.dbmsnew.DbmsDatasChemeDetailNewMapper;
import com.sinog2c.model.dbmsnew.DbmsDatasChemeDetailNew;
import com.sinog2c.model.dbmsnew.DbmsDatasChemeDetailNewKey;
import com.sinog2c.service.api.dbmsnew.DbmsDatasChemeDetailNewService;
import com.sinog2c.service.impl.base.ServiceImplBase;

@Service("dbmsDatasChemeDetailNewService")  
public class DbmsDatasChemeDetailNewServiceImpl extends ServiceImplBase implements DbmsDatasChemeDetailNewService {

	@Autowired
	private DbmsDatasChemeDetailNewMapper dbmsDatasChemeDetailNewMapper;
	
	@Override
	public int deleteByPrimaryKey(DbmsDatasChemeDetailNewKey key) {
		int num = dbmsDatasChemeDetailNewMapper.deleteByPrimaryKey(key);
		return num;
	}

	@Override
	public int insert(DbmsDatasChemeDetailNew record) {
		int num = dbmsDatasChemeDetailNewMapper.insert(record);
		return num;
	}

	@Override
	public int insertSelective(DbmsDatasChemeDetailNew record) {
		int num = dbmsDatasChemeDetailNewMapper.insertSelective(record);
		return num;
	}

	@Override
	public DbmsDatasChemeDetailNew selectByPrimaryKey(
			DbmsDatasChemeDetailNewKey key) {
		DbmsDatasChemeDetailNew dbmsDatasChemeDetailNew = dbmsDatasChemeDetailNewMapper.selectByPrimaryKey(key);
		return dbmsDatasChemeDetailNew;
	}

	@Override
	public int updateByPrimaryKeySelective(DbmsDatasChemeDetailNew record) {
		int num = dbmsDatasChemeDetailNewMapper.updateByPrimaryKeySelective(record);
		return num;
	}

	@Override
	public int updateByPrimaryKey(DbmsDatasChemeDetailNew record) {
		int num = dbmsDatasChemeDetailNewMapper.updateByPrimaryKey(record);
		return num;
	}
	
	@Override
	public List<DbmsDatasChemeDetailNew> listByCondition(Map<String, Object> map) {

		List<DbmsDatasChemeDetailNew> result = dbmsDatasChemeDetailNewMapper.listByCondition(map);
		return result;
	}

	@Override
	public List<DbmsDatasChemeDetailNew> getcolumnByddcid(@Param("ddcid") String ddcid) {
		return dbmsDatasChemeDetailNewMapper.getcolumnByddcid(ddcid);
	}
}
