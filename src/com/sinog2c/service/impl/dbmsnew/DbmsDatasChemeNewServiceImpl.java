package com.sinog2c.service.impl.dbmsnew;

import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sinog2c.dao.api.dbmsnew.DbmsDatasChemeDetailNewMapper;
import com.sinog2c.dao.api.dbmsnew.DbmsDatasChemeNewMapper;
import com.sinog2c.dao.api.dbmsnew.DbmsDatasTableNewMapper;
import com.sinog2c.dao.api.dbmsnew.TbsysServicesMapper;
import com.sinog2c.model.dbmsnew.DbmsDatasChemeDetailNew;
import com.sinog2c.model.dbmsnew.DbmsDatasChemeNew;
import com.sinog2c.model.dbmsnew.DbmsDatasTableNew;
import com.sinog2c.model.system.SystemUser;
import com.sinog2c.service.api.dbmsnew.DbmsDatasChemeNewService;
import com.sinog2c.service.impl.base.ServiceImplBase;
import com.sinog2c.util.common.MapUtil;

@Service("dbmsDatasChemeNewService")
public class DbmsDatasChemeNewServiceImpl extends ServiceImplBase implements DbmsDatasChemeNewService {

	@Autowired
	private DbmsDatasChemeNewMapper dbmsDatasChemeNewMapper;
	@Autowired
	private DbmsDatasTableNewMapper tableNewMapper;
	@Autowired
	private DbmsDatasChemeDetailNewMapper detailNewMapper;
	@Autowired
	private TbsysServicesMapper tbsysservicesmapper;
	@Override
	public List<Map<String, Object>> selectByPrimaryKey(String codeid) {
		List<Map<String, Object>> mapList = dbmsDatasChemeNewMapper.selectByPrimaryKey(codeid);
		mapList = MapUtil.convertKeyList2LowerCase(mapList);
		return mapList;
	}
	
	// 根据ID取得
	@Override
	public DbmsDatasChemeNew getById(String ddcid){
		DbmsDatasChemeNew list = dbmsDatasChemeNewMapper.getById(ddcid);
		return list;
	}

	@Override
	public List<Map<String, Object>> selectBySdid(String sdid) {
		List<Map<String, Object>> mapList = dbmsDatasChemeNewMapper.selectBySdid(sdid);
		mapList = MapUtil.convertKeyList2LowerCase(mapList);
		return mapList;
	}

	@Override
	@Transactional
	public int deleteByPrimaryKey(String ddcid) {
		int deleteRows = dbmsDatasChemeNewMapper.deleteByPrimaryKey(ddcid);
		return deleteRows;
	}

	@Override
	@Transactional
	public int insert(DbmsDatasChemeNew record) {
		int insertRows = dbmsDatasChemeNewMapper.insert(record);
		return insertRows;
	}

	@Override
	@Transactional
	public int insertSelective(DbmsDatasChemeNew record) {
		int insertRows = dbmsDatasChemeNewMapper.insertSelective(record);
		return insertRows;
	}

	@Override
	@Transactional
	public int updateByPrimaryKeySelective(DbmsDatasChemeNew record) {
		int updateRows = dbmsDatasChemeNewMapper.updateByPrimaryKeySelective(record);
		return updateRows;
	}

	@Override
	@Transactional
	public int updateByPrimaryKey(DbmsDatasChemeNew record) {
		int updateRows = dbmsDatasChemeNewMapper.updateByPrimaryKeySelective(record);
		return updateRows;
	}

	@Override
	public List<Map<String, Object>> listMapByPage(Map<String, Object> map) {
		List<Map<String, Object>> list = dbmsDatasChemeNewMapper.listMapByPage(map);
		list = MapUtil.convertKeyList2LowerCase(list);
		return list;
	}
	
	@Override
	public int countAll(SystemUser operator) {
		//
		int result = dbmsDatasChemeNewMapper.countAll();
		//
		return result;
	}
	
	@Override
	public List<DbmsDatasChemeNew> listAll(SystemUser operator) {
		//
		List<DbmsDatasChemeNew> result = dbmsDatasChemeNewMapper.listAll();
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
		if(null == idKey || idKey.length() < 1){
			idKey = "sdid";
		}
		//
		String sdid = (String) map.get(idKey);
		if(null == sdid || sdid.trim().length() < 1){
			sdid = user.getPrisonid();
			if(null == sdid || sdid.length() < 1){
				sdid = user.getOrgid();
			}
		}
		//
		if(null != map){
			newMap.putAll(map);
		}
		// 设置Map默认值
		newMap.put(idKey, sdid);
		//
		return newMap;
	}
	@Override
	public int countByCondition(Map<String, Object> map, SystemUser operator, boolean isCopyMode) {
		//
		if (!isCopyMode){
			// 封装 Map数据
			map = setPrisonidToMap(map, operator, "sdid");
		}
		int result = dbmsDatasChemeNewMapper.countByCondition(map);
		//
		return result;
	}
	
	@Override
	public List<DbmsDatasChemeNew> listByCondition(Map<String, Object> map, SystemUser operator, boolean isCopyMode) {
		if (!isCopyMode){
			// 封装 Map数据
			map = processMapPage(map);
			// 处理用户
			map = setPrisonidToMap(map, operator, "sdid");
		} else {
			map.put("start", 0);
			map.put("end", 2000);
		}
		//列出所有的方案
		List<DbmsDatasChemeNew> result = dbmsDatasChemeNewMapper.listByCondition(map);
		//
		return result;
	}
	
	@Override
	@Transactional
	public int updateByMap(Map<String, Object> map, SystemUser operator) {
		//
		int result = dbmsDatasChemeNewMapper.updateByMap(map);
		//
		return result;
	}
	
	@Override
	@Transactional
	public int insertByMap(Map<String, Object> map, SystemUser operator) {
		//
		int result = dbmsDatasChemeNewMapper.insertByMap(map);
		//
		return result;
	}
	
	@Override
	@Transactional
	public int add(DbmsDatasChemeNew schemeNew, SystemUser operator) {
		if(null == schemeNew || null == operator){
			return -1;
		}
		//
		String userid = operator.getUserid();
		String sdid = schemeNew.getSdid();
		if(null == sdid || sdid.trim().length() < 1){
			sdid = operator.getPrisonid();
		}
		if(null == sdid || sdid.trim().length() < 1){
			sdid = operator.getOrgid();
		}
		// 
		String ddcid = schemeNew.getDdcid();
		//
		if(null == ddcid || ddcid.trim().length() < 1){
			return -1;
		}
		
		//
		Date current = new Date();
		// 对数据进行处理
		schemeNew.setUpdatemender(userid);
		schemeNew.setCreatemender(userid);
		schemeNew.setCreatetime(current);
		schemeNew.setUpdatetime(current);
		schemeNew.setSdid(sdid);
		//
		int result = dbmsDatasChemeNewMapper.insertSelective(schemeNew);
		if(result < 1){
			return result;
		}
		// 更新Table记录
		List<DbmsDatasTableNew> tableList = schemeNew.getTableList();
		if(null != tableList && !tableList.isEmpty()){
			// 遍历
			Iterator<DbmsDatasTableNew> iteratorT = tableList.iterator();
			//int i = 1;
			while (iteratorT.hasNext()) {
				DbmsDatasTableNew tableNew = (DbmsDatasTableNew) iteratorT.next();
				if(null == tableNew){
					continue;
				}
				// 处理数据
				tableNew.setCreatemender(userid);
				tableNew.setUpdatemender(userid);
				tableNew.setCreatetime(current);
				tableNew.setUpdatetime(current);
				//tableNew.setDdcid(ddcid);
				//
				int r2 = tableNewMapper.insertSelective(tableNew);
				r2 = r2+1; // no use
			}
		}
		// 方案对应的细节
		List<DbmsDatasChemeDetailNew> detailList = schemeNew.getDetailList();
		if(null != detailList && !detailList.isEmpty()){
			// 遍历
			Iterator<DbmsDatasChemeDetailNew> iteratorD = detailList.iterator();
			//int i = 1;
			while (iteratorD.hasNext()) {
				DbmsDatasChemeDetailNew detailNew = (DbmsDatasChemeDetailNew) iteratorD.next();
				if(null == detailNew){
					continue;
				}
				// 处理数据
				detailNew.setCreatemender(userid);
				detailNew.setUpdatemender(userid);
				detailNew.setCreatetime(current);
				detailNew.setUpdatetime(current);
				//tableNew.setDdcid(ddcid);
				//
				int r2 = detailNewMapper.insertSelective(detailNew);
				r2 = r2+1; // no use
			}
		}
		//
		return result;
	}
	
	@Override
	public Map<String, Object> getRemoteIp(Map<String, Object> map){
		return MapUtil.convertKey2LowerCase(dbmsDatasChemeNewMapper.getRemoteIp(map));
	}

	@Override
	public Map selectServicesByDeparit(String str) {
		return MapUtil.convertKey2LowerCase(tbsysservicesmapper.selectByDepartid(str));
	}

	@Override
	public void callProcedures() {
		tbsysservicesmapper.callProcedures();
	}

	@Override
	public List<Map<String, Object>> getDbmsresolvewebxmlList(Map<String,Object> map){
		List<Map<String, Object>> mapList = dbmsDatasChemeNewMapper.getDbmsresolvewebxmlList(map);
		mapList = MapUtil.convertKeyList2LowerCase(mapList);
		return mapList;
	}
	
	
}
