package com.sinog2c.service.impl.dbmsnew;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sinog2c.dao.api.dbmsnew.DbmsDatabaseNewMapper;
import com.sinog2c.model.dbmsnew.DbmsDatabaseNew;
import com.sinog2c.model.system.SystemUser;
import com.sinog2c.service.api.dbmsnew.DbmsDatabaseNewService;
import com.sinog2c.service.impl.base.ServiceImplBase;
import com.sinog2c.util.common.MapUtil;
/**  
 * 项目名称：pmsys
 * 此类描述的是：    数据库管理SERVICE
 */
@Service("dataBaseConnectionMessageService")
public class DbmsDatabaseNewServiceImpl extends ServiceImplBase implements DbmsDatabaseNewService {

	@Autowired
	private DbmsDatabaseNewMapper dbmsDatabaseNewMapper;

	@Override
	public DbmsDatabaseNew selectByPrimaryKey(String ddcid) {
		DbmsDatabaseNew listMap = dbmsDatabaseNewMapper.selectByPrimaryKey(ddcid);
		//listMap = MapUtil.convertKeyList2LowerCase(listMap);
		return listMap;
	}

	@Override
	public int deleteByPrimaryKey(String ddid) {
		int num = dbmsDatabaseNewMapper.deleteByPrimaryKey(ddid);
		return num;
	}

	@Override
	public int insert(DbmsDatabaseNew record) {
		int num = dbmsDatabaseNewMapper.insert(record);
		return num;
	}

	@Override
	public int insertSelective(DbmsDatabaseNew record) {
		int num = dbmsDatabaseNewMapper.insertSelective(record);
		return num;
	}

	@Override
	public int updateByPrimaryKeySelective(DbmsDatabaseNew record) {
		int num = dbmsDatabaseNewMapper.updateByPrimaryKeySelective(record);
		return num;
	}

	@Override
	public int updateByPrimaryKey(DbmsDatabaseNew record) {
		int num = dbmsDatabaseNewMapper.updateByPrimaryKey(record);
		return num;
	}
	
	@Override
	public int allCount(Map<String, Object> map) {
		int num = dbmsDatabaseNewMapper.allCount(map); 
		return num;
	}

	@Override
	public List<Map<String, Object>> listDBConnMapByPage(
			Map<String, Object> map) {
		List<Map<String, Object>> list = dbmsDatabaseNewMapper.listDBConnMapByPage(map);
		list = MapUtil.convertKeyList2LowerCase(list);
		return list;
	}
	
	@Override
	public int countAll(SystemUser user) {
		if(null == user){
			return 0;
		}
		// 封装 Map数据
		Map<String, Object> map = setPrisonidToMap(null, user, "sdid");
		//
		int result = dbmsDatabaseNewMapper.countAll(map);
		//
		return result;
	}
	
	@Override
	public List<DbmsDatabaseNew> listAll(SystemUser user) {
		if(null == user){
			return null;
		}
		// 封装 Map数据
		Map<String, Object> map = setPrisonidToMap(null, user, "sdid");
		//
		List<DbmsDatabaseNew> result = dbmsDatabaseNewMapper.listAll(map);
		//
		return result;
	}
	
	//
	protected Map<String, Object> setPrisonidToMap(Map<String, Object> map, SystemUser user, String idKey) {
		//
		Map<String, Object> newMap = new HashMap<String, Object>();
		if(null == user){
			return newMap;
		}
		if(null == map){
			map = newMap;
		}
		if(null == idKey || idKey.length() < 1){
			idKey = "sdid";
		}
		//
		String sdid = (String) map.get(idKey);
		//
		if(null == sdid || sdid.trim().length() < 1){
			sdid = user.getPrisonid();
			if(null == sdid || sdid.length() < 1){
				sdid = user.getOrgid();
			}
		}
		//
		newMap.putAll(map);
		// 设置Map默认值
		newMap.put(idKey, sdid);
		//
		return newMap;
	}
	
	@Override
	public int countByCondition(Map<String, Object> map, SystemUser user) {
		if(null == map || null == user){
			return 0;
		}
		// 封装 Map数据
		map = setPrisonidToMap(map, user, "sdid");
		//
		int result = dbmsDatabaseNewMapper.countByCondition(map);
		//
		return result;
	}
	
	@Override
	public List<DbmsDatabaseNew> listByCondition(Map<String, Object> map, SystemUser user) {
		if(null == map || null == user){
			return null;
		}
		// 封装 Map数据
		map = setPrisonidToMap(map, user, "sdid");
		map = processMapPage(map);
		//
		List<DbmsDatabaseNew> result = dbmsDatabaseNewMapper.listByCondition(map);
		//
		return result;
	}
	
	@Override
	public int updateByMap(Map<String, Object> map, SystemUser user) {
		//
		int result = dbmsDatabaseNewMapper.updateByMap(map);
		//
		return result;
	}
	
	@Override
	public int insertByMap(Map<String, Object> map, SystemUser user) {
		//
		// 封装 Map数据
		map = setPrisonidToMap(map, user, "sdid");
		int result = dbmsDatabaseNewMapper.insertByMap(map);
		//
		return result;
	}

	@Override
	public List<Map> listByOrgid(Map<String, Object> map) {
		List<Map> result = new ArrayList<Map>();
		List<Map> list = dbmsDatabaseNewMapper.listByOrgid(map);
		if(list!=null){
			for(Object obj:list){
				Map tempmap = (Map)obj;
				tempmap =MapUtil.turnKeyToLowerCase(tempmap);
				result.add(tempmap);
			}
		}
		return result;
	}

	//行文交换增加数据
	@Override
	public int insertByOrgid(Map<String, Object> map) {
		int result = dbmsDatabaseNewMapper.insertByOrgid(map);
		return result;
	}

	@Override
	public int listByOrgidCount(Map<String, Object> map) {
		return dbmsDatabaseNewMapper.listByOrgidCount(map);
	}

	@Override
	public int updateByExchangeWritingMap(Map<String, Object> map) {
		return dbmsDatabaseNewMapper.updateByExchangeWritingMap(map);
	}

	@Override
	public DbmsDatabaseNew selectFromDatabaseByDdcid(String ddcid) {
		return dbmsDatabaseNewMapper.selectFromDatabaseByDdcid(ddcid);
	}
}
