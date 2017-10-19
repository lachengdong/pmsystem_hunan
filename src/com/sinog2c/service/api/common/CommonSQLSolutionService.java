package com.sinog2c.service.api.common;

import java.sql.Connection;
import java.util.List;
import java.util.Map;

import com.sinog2c.model.system.SystemUser;

/**
 * 通用SQL方案服务接口
 */
public interface CommonSQLSolutionService {

	/**
	 * 保存
	 * @param params
	 * @param user
	 * @return
	 */
	public Map<String, Object> save(Map<String, Object> params, SystemUser user);
	/**
	 * 保存
	 * @param params
	 * @param user
	 * @return
	 */
	public Map<String, Object> save(Map<String, Object> params, SystemUser user, Connection conn);
	
	/**
	 * 批量保存
	 * @author YangZR	2016-08-13
	 * @param params
	 * @param user
	 * @return
	 */
	public Map<String, Object> batchSave(List<Map<String, Object>> paramList, SystemUser user);
	
	/**
	 * 批量保存
	 * @author YangZR	2016-08-13
	 * @param params
	 * @param user
	 * @return
	 */
	public Map<String, Object> batchSave(List<Map<String, Object>> paramList, SystemUser user, Connection conn);
	
	/**
	 * 删除
	 * @param params
	 * @param user
	 * @return
	 */
	public Map<String, Object> delete(Map<String, Object> params, SystemUser user);
	/**
	 * 删除
	 * @param params
	 * @param user
	 * @return
	 */
	public Map<String, Object> delete(Map<String, Object> params, SystemUser user, Connection conn);
	/**
	 * 查询
	 * @param params
	 * @param user
	 * @return
	 */
	public Map<String, Object> query(Map<String, Object> params, SystemUser user);
	/**
	 * 查询
	 * @param params
	 * @param user
	 * @return
	 */
	public Map<String, Object> query(Map<String, Object> params, SystemUser user, Connection conn);
	
	/**
	 * 流程的策略
	 * @param params
	 * @param user
	 * @return
	 */
	public Map<String, Object> flow(Map<String, Object> params, SystemUser user);
	
	/**
	 * 查询列表
	 * @param params
	 * @param user
	 * @return
	 */
	public Map<String, Object> list(Map<String, Object> params, SystemUser user);
	/**
	 * 查询列表
	 * @param params
	 * @param user
	 * @return
	 */
	public Map<String, Object> list(Map<String, Object> params, SystemUser user, Connection conn);
}
