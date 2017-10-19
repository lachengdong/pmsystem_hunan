package com.sinog2c.service.api.system;

import java.util.List;
import java.util.Map;

import com.sinog2c.model.system.RolePermissionsWrapper;
import com.sinog2c.model.system.SystemPermissions;
import com.sinog2c.model.system.SystemUser;

public interface SystemPermissionsService  {

	/**
	 * 使用sequence自增ID方式新增
	 * @param permissions 权限对象
	 * @param operator 当前用户
	 * @return
	 */
	public int add(SystemPermissions permissions, SystemUser operator);
	/**
	 * 新增系统权限,不使用序列自增机制,需要指定好ID号
	 * @param permissions 权限对象
	 * @param operator 当前用户
	 * @return
	 */
	public int insertWithId(SystemPermissions permissions, SystemUser operator);
	/**
	 * 根据map进行更新
	 * @param map
	 * @param operator
	 * @return
	 */
	public int insertByMap(Map<String, Object> map, SystemUser operator);

	/**
	 * 修改权限信息
	 * @param permissions 权限对象
	 * @param operator 当前用户
	 * @return
	 */
	public int update(SystemPermissions permissions, SystemUser operator);
	/**
	 * 由hashmap更新
	 * @param map
	 * @param operator
	 * @return
	 */
	public int updateByMap(Map<String, Object> map, SystemUser operator);

	/**
	 * 删除权限,逻辑上进行删除
	 * @param spid
	 * @return
	 */
	public int delete(String spid, SystemUser operator);

	/**
	 * 取得所有未删除的列表
	 * @return
	 */
	public List<SystemPermissions> selectAll();

	/**
	 * 根据页面取得数据
	 * @param pageIndex 页码,0开始
	 * @param pageSize 每页数量
	 * @return
	 */
    public List<SystemPermissions> getAllByPage(int pageIndex,	int pageSize);

	/**
	 * 根据 pid取得直接子元素列表
	 * 
	 * @param spparent
	 * @return
	 */
	public List<SystemPermissions> listByPermissionsPid(String spparent);

	/**
	 * 取得所有未删除的数量
	 * @return
	 */
	public int countAll();

    /**
     * 获取自增长的 resid
     * @return
     */
    public int getNextId();
	/**
	 * 根据ID取得
	 * @param spid
	 * @return
	 */
	public SystemPermissions getByPermissionsId(String spid);
	

	/**
	 * 根据角色获取权限列表
	 * @param spparent
	 * @return
	 */
	public List<SystemPermissions> listByRoleid(String roleid);
	
	/**
	 * 更新用户的权限
	 * @param roleid
	 * @param newpermissions
	 * @return 执行是否成功
	 */
	public boolean updateByRole(String roleid, List<RolePermissionsWrapper> newpermissions);

}