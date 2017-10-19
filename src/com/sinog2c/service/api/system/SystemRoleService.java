package com.sinog2c.service.api.system;

import java.util.List;
import java.util.Map;

import com.sinog2c.model.system.SystemRole;
import com.sinog2c.model.system.SystemUser;
import com.sinog2c.model.system.UserRoleWrapper;

public interface SystemRoleService {

	/**
	 * 使用sequence自增ID方式新增
	 * @param role 角色对象
	 * @param operator 当前用户
	 * @return
	 */
	public int add(SystemRole role, SystemUser operator);
	/**
	 * 新增系统角色,不使用序列自增机制,需要指定好ID号
	 * @param role 角色对象
	 * @param operator 当前用户
	 * @return
	 */
	public int insertWithId(SystemRole role, SystemUser operator);
	/**
	 * 根据map进行更新
	 * @param map
	 * @param operator
	 * @return
	 */
	public int insertByMap(Map<String, Object> map, SystemUser operator);

	/**
	 * 修改角色信息
	 * @param role 角色对象
	 * @param operator 当前用户
	 * @return
	 */
	public int update(SystemRole role, SystemUser operator);
	/**
	 * 由hashmap更新
	 * @param role
	 * @param operator
	 * @return
	 */
	public int updateByMap(Map<String, Object> map, SystemUser operator);

	/**
	 * 删除角色,逻辑上进行删除
	 * @param roleid
	 * @return
	 */
	public int delete(String roleid, SystemUser operator);

	/**
	 * 取得所有未删除的角色列表
	 * @return
	 */
	public List<SystemRole> selectAll();
	/**
	 * 取得所有未删除的角色列表
	 * @param useCache 是否使用缓存
	 * @return
	 */
	public List<SystemRole> selectAll(boolean useCache);
	/**
	 * 根据用户列表取得对应的角色匹配信息Wrapper记录
	 * @param userList
	 * @return
	 */
	public List<UserRoleWrapper> getAllRoleWrappersByUserids(List<SystemUser> userList);
	/**
	 * 根据页面取得数据
	 * @param pageIndex 页码,0开始
	 * @param pageSize 每页数量
	 * @return
	 */
    public List<SystemRole> getAllByPage(int pageIndex,	int pageSize, Map<String, Object> map);

	/**
	 * 根据 pid取得直接子元素角色列表
	 * 
	 * @param proleid
	 * @return
	 */
	public List<SystemRole> listByRolePid(String proleid);

	/**
	 * 取得所有未删除的角色数量
	 * @return
	 */
	public int countAll(Map<String, Object> map);

	/**
	 * 根据ID取得
	 * @param roleid
	 * @return
	 */
	public SystemRole getByRoleId(String roleid);


	/**
	 * 根据userid获取角色列表
	 * @param userid
	 * @return
	 */
	public List<SystemRole> listByUserid(String userid,Map<String, Object> map);
	
	
	/**
	 * 更新用户的角色
	 * @param userid
	 * @param newroles
	 * @return 执行是否成功
	 */
	public boolean updateByUser(String userid, List<UserRoleWrapper> newroles);

	//start add by blue_lv 2015-07-15
	/**
	 * 获取用户所具有的角色
	 * @param userid
	 * @return
	 */
	List<UserRoleWrapper> getRolesOfUser(String userid);
	//end add by blue_lv 2015-07-15
}