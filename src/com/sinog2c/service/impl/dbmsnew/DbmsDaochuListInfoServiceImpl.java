package com.sinog2c.service.impl.dbmsnew;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sinog2c.dao.api.dbmsnew.DbmsDaochuListInfoMapper;
import com.sinog2c.model.dbmsnew.DbmsDaochuListInfo;
import com.sinog2c.service.api.dbmsnew.DbmsDaochuListInfoService;
import com.sinog2c.service.impl.base.ServiceImplBase;
import com.sinog2c.util.common.FileUtil;
import com.sinog2c.util.common.MapUtil;

@Service("dbmsDaochuListInfoService") 
public class DbmsDaochuListInfoServiceImpl extends ServiceImplBase implements DbmsDaochuListInfoService {

	@Autowired
	private DbmsDaochuListInfoMapper dbmsDaochuListInfoMapper;
	
	@Override
	public int deleteByPrimaryKey(String fileid) {
		int num = dbmsDaochuListInfoMapper.deleteByPrimaryKey(fileid);
		return num;
	}
	
	@Override
	public int deleteDataExport(String fileid,String filepath){
		
		int num = dbmsDaochuListInfoMapper.deleteByPrimaryKey(fileid);
		FileUtil.deleteFile(filepath);
		
		return num;
	}

	@Override
	public int insert(DbmsDaochuListInfo record) {
		int num = dbmsDaochuListInfoMapper.insert(record);
		return num;
	}

	@Override
	public int insertSelective(DbmsDaochuListInfo record) {
		int num = dbmsDaochuListInfoMapper.insertSelective(record);
		return num;
	}

	@Override
	public DbmsDaochuListInfo selectByPrimaryKey(String fileid) {
		DbmsDaochuListInfo daochuListInfo = dbmsDaochuListInfoMapper.selectByPrimaryKey(fileid);
		return daochuListInfo;
	}

	@Override
	public int updateByPrimaryKeySelective(DbmsDaochuListInfo record) {
		int num = dbmsDaochuListInfoMapper.updateByPrimaryKeySelective(record);
		return num;
	}

	@Override
	public int updateByPrimaryKey(DbmsDaochuListInfo record) {
		int num = dbmsDaochuListInfoMapper.updateByPrimaryKey(record);
		return num;
	}

	@Override
	public List<Map<String, Object>> listMapByPage(Map<String, Object> map) {
		List<Map<String, Object>> list = dbmsDaochuListInfoMapper.listMapByPage(map);
		list = MapUtil.convertKeyList2LowerCase(list);
		return list;
	}
	
	@Override
	public int countAll() {
		//
		int result = dbmsDaochuListInfoMapper.countAll();
		//
		return result;
	}
	
	@Override
	public List<DbmsDaochuListInfo> listAll() {
		//
		List<DbmsDaochuListInfo> result = dbmsDaochuListInfoMapper.listAll();
		//
		return result;
	}
	
	@Override
	public int countByCondition(Map<String, Object> map) {
		//
		int result = dbmsDaochuListInfoMapper.countByCondition(map);
		//
		return result;
	}
	
	@Override
	public List<DbmsDaochuListInfo> listByCondition(Map<String, Object> map) {
		//
		map = processMapPage(map);
		//
		List<DbmsDaochuListInfo> result = dbmsDaochuListInfoMapper.listByCondition(map);
		//
		return result;
	}
	
	@Override
	@Transactional
	public int updateByMap(Map<String, Object> map) {
		//
		int result = dbmsDaochuListInfoMapper.updateByMap(map);
		//
		return result;
	}
	
	@Override
	@Transactional
	public int insertByMap(Map<String, Object> map) {
		//
		int result = dbmsDaochuListInfoMapper.insertByMap(map);
		//
		return result;
	}

	@Override
	public int countZipDbmsDaochuListInfoData() {
		return dbmsDaochuListInfoMapper.countZipDbmsDaochuListInfoData();
	}

	@Override
	public List<Map<String, Object>> getZipDbmsDaochuListInfoData(Map<String, Object> map) {
		return MapUtil.convertKeyList2LowerCase(dbmsDaochuListInfoMapper.getZipDbmsDaochuListInfoData(map));
	}

	@Override
	public int countUnZipDbmsDaochuListInfoData() {
		return dbmsDaochuListInfoMapper.countUnZipDbmsDaochuListInfoData();
	}

	@Override
	public List<Map<String, Object>> getUnZipDbmsDaochuListInfoData(Map<String, Object> map) {
		return MapUtil.convertKeyList2LowerCase(dbmsDaochuListInfoMapper.getUnZipDbmsDaochuListInfoData(map));
	}
	
}
