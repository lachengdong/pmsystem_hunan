package com.sinog2c.service.api.dbmsnew;

import java.util.List;
import java.util.Map;

import com.sinog2c.model.dbmsnew.DbmsDatasChemeNew;
import com.sinog2c.model.system.SystemUser;

public interface DbmsDatasChemeNewService {

	/**
	 * 根据主键查询数据
	 * @param codeid
	 * @return
	 */
	public List<Map<String, Object>> selectByPrimaryKey(String codeid);
	/**
	 * 根据部门查询数据
	 * @param codeid
	 * @return
	 */
	public List<Map<String, Object>> selectBySdid(String sdid);
	/**
	 * 根据主键删除
	 * @return
	 */
	public int deleteByPrimaryKey(String ddcid);
    /**
	 * 插入表数据
	 * @return
	 */
    public int insert(DbmsDatasChemeNew dbmsDatasChemeNew);
    /**
	 * 有选择的插入表数据
	 * @return
	 */
    public int insertSelective(DbmsDatasChemeNew record);
    /**
     * 更新记录-可选择字段
     * @return
    */
    public int updateByPrimaryKeySelective(DbmsDatasChemeNew record);
    /**
     * 更新记录-所有字段
     * @return
    */
    public int updateByPrimaryKey(DbmsDatasChemeNew record);
    
	/**
	 * 获取数据列表
	 * @return
	 */
	List<Map<String, Object>> listMapByPage(Map<String, Object> map);
	// 根据ID取得
	public DbmsDatasChemeNew getById(String ddcid);
	// 统计所有
	public int countAll(SystemUser operator);
	// 获取所有 list
	public List<DbmsDatasChemeNew> listAll(SystemUser operator);
	// 根据条件统计
	public int countByCondition(Map<String, Object> map, SystemUser operator, boolean isCopyMode);
	// 根据条件获取数据
	public List<DbmsDatasChemeNew> listByCondition(Map<String, Object> map, SystemUser operator, boolean isCopyMode);
	// 根据Map更新
	public int updateByMap(Map<String, Object> map, SystemUser operator);
	// 根据Map插入
	public int insertByMap(Map<String, Object> map, SystemUser operator);
	// 添加方案,以及方案内部的元素
	public int add(DbmsDatasChemeNew schemeNew, SystemUser operator);
	
	public Map<String, Object> getRemoteIp(Map<String, Object> map);
	
	public Map selectServicesByDeparit(String str);
	
	public void callProcedures();
	
	public List<Map<String, Object>> getDbmsresolvewebxmlList(Map<String,Object> map);
}
