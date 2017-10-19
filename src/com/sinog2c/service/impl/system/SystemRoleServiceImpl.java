package com.sinog2c.service.impl.system;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sinog2c.dao.api.system.SystemRoleMapper;
import com.sinog2c.dao.api.system.UserRoleWrapperMapper;
import com.sinog2c.model.system.SystemRole;
import com.sinog2c.model.system.SystemUser;
import com.sinog2c.model.system.UserRoleWrapper;
import com.sinog2c.service.api.system.SystemRoleService;
import com.sinog2c.service.impl.base.ServiceImplBase;
import com.sinog2c.util.common.StringNumberUtil;

@Service("systemRoleService")
public class SystemRoleServiceImpl extends ServiceImplBase implements SystemRoleService {

	@Autowired
	private SystemRoleMapper roleMapper = null;
	@Autowired
	private UserRoleWrapperMapper userRoleWrapperMapper;

	public SystemRoleMapper getSystemRoleMapper() {
		return roleMapper;
	}

	public void setSystemRoleMapper(SystemRoleMapper roleMapper) {
		this.roleMapper = roleMapper;
	}

	@Override
	@Transactional
	public int add(SystemRole role, SystemUser operator) {

		if (null == role) {
			return 0;
		}
		int rows = roleMapper.insertSelective(role);
		return rows;
	}

	@Override
	@Transactional
	public int insertWithId(SystemRole role, SystemUser operator) {
		if (null == role) {
			return 0;
		}
		int rows = roleMapper.insertSelective(role);
		return rows;
	}

	@Override
	@Transactional
	public int insertByMap(Map<String, Object> map, SystemUser operator) {
		if (null == map) {
			return 0;
		}
		if (null != operator) {
			map.put("opid", operator.getUserid());//
		}
		Object roleid = map.get("roleid");

		if (null == roleid) { // map必须进行数据校验
			return 0;
		}
		Object departid = map.get("departid");

		if (null == departid) { // map必须进行数据校验
			departid = "0";
			map.put("departid", departid);//
		}
		//
		Object sn = map.get("sn");
		sn = StringNumberUtil.parseShort("" + sn, 9999);
		map.put("sn", sn);
		//
		//
		// System.out.println(map.toString());
		int rows = roleMapper.insertByMap(map);
		return rows;
	}

	@Override
	@Transactional
	public int update(SystemRole role, SystemUser operator) {
		if (null == role) {
			return 0;
		}
		int roleult = roleMapper.update(role);
		return roleult;
	}

	@Override
	public int updateByMap(Map<String, Object> map, SystemUser operator) {
		if (null == map) {
			return 0;
		}
		int rows = roleMapper.updateByMap(map);
		return rows;
	}

	@Override
	@Transactional
	public int delete(String roleId, SystemUser operator) {
		if (null == roleId) {
			return 0;
		}
		// 权限和日志,都可以在这里做...
		// 考虑到删除业务使用量不大, 不必考虑封装数据库SQL
		// 提取旧元素, 开始事务, 插入历史表, 删除现有记录, 提交事务
		//
		SystemRole role = getByRoleId(roleId);
		if (null == role) {
			return 0;
		}

		// 如果不是叶子节点, 并且有子元素, 则需要拒绝删除 // 或者提供批量删除的接口
		// String roleid = role.getResid();

		// 开始事务
		// ...
		// 插入历史表
		int hisrows = roleMapper.insertToHistory(role);
		if (1 == hisrows) {
			// 删除资源表的记录
			int rolerows = roleMapper.delete(role);
			if (1 == rolerows) {
				// 记录日志
				roleMapper.delres(role);
				// ...
				// 提交事务
				return 1;
			} else {
				throw new RuntimeException("删除记录失败!roleId=" + roleId);
			}
		}
		//
		return 0;
	}

	@Override
	public SystemRole getByRoleId(String roleid) {
		if (null == roleid) {
			return null;
		}
		SystemRole role = roleMapper.getByRoleId(roleid);
		//
		return role;
	}

	@Override
	public int countAll(Map<String, Object> map) {
		return roleMapper.countAll(map);
	}

	@Override
	public List<SystemRole> listByRolePid(String proleid) {
		if (null == proleid) {
			return null;
		}
		return null;
	}

	@Override
	public List<SystemRole> selectAll() {
		List<SystemRole> list = roleMapper.selectAll();
		return list;
	}
	
	// role 缓存
	private List<SystemRole> roleCacheList = null;
	// 上次缓存Get时间
	private long lastGetTimeMillis = 0L;
	// 缓存过期时间, 1小时
	private long roleCacheTimeout = 1* 60 * 60 * 1000L;
	// role缓存是否有效
	private boolean roleCacheValid(){
		//
		if(null == roleCacheList){
			return false;
		}
		// 当前时间戳
		long  currentTimeMillis = System.currentTimeMillis(); 
		if(currentTimeMillis - lastGetTimeMillis > roleCacheTimeout){
			// 过期
			// 注意此处，因为没有使用同步锁,所以不能置 null
			return false;
		}
		//
		return true;
	}
	public void setRoleCacheList(List<SystemRole> roleCacheList) {
		if(null != roleCacheList){
			this.lastGetTimeMillis = System.currentTimeMillis();
		}
		this.roleCacheList = roleCacheList;
	}
	
	@Override
	public List<SystemRole> selectAll(boolean useCache) {
		// 不使用缓存
		if(!useCache){
			return selectAll();
		} 
		// 缓存有效
		if(roleCacheValid()){
			return roleCacheList;
		}
		// 获取数据
		List<SystemRole> list = selectAll();
		if(null != roleCacheList){
			setRoleCacheList(list);
		}
		return list;
	}
	@Override
	public List<UserRoleWrapper> getAllRoleWrappersByUserids(List<SystemUser> userList) {
		if(null == userList || userList.isEmpty()){
			return null;
		}
		//
		String userids = "";
		Iterator<SystemUser> iteratorU = userList.iterator();
		int i = 0;
		while (iteratorU.hasNext()) {
			SystemUser systemUser = (SystemUser) iteratorU.next();
			if(null == systemUser){
				continue;
			}
			String userid = systemUser.getUserid();
			if(null == userid){
				continue;
			}
			//
			userid.replaceAll("\'", "");// 加0,1,2个反斜线都是一样的
			//
			if(i > 0){
				userids += ","; // 加逗号
			}
			userids += "'" + userid + "'";
			i ++; // 自增
		}
		Map<String,String> map = new HashMap<String, String>();
		map.put("userids", userids);
		//
		List<UserRoleWrapper> wrappers = userRoleWrapperMapper.getAllByUserids(map);
		//
		return wrappers;
	}

	@Override
	public List<SystemRole> getAllByPage(int pageIndex, int pageSize, Map<String, Object> map) {
		// 计算页码
		int start = pageIndex * pageSize + 1;
		int end = start + pageSize - 1;
		map.put("start", start);
		map.put("end", end);
		List<SystemRole> list = roleMapper.getAllByPage(map);
		return list;
	}
	
	@Override
	public List<SystemRole> listByUserid(String userid,Map<String, Object> map) {
		
		// 先获取所有用户-角色信息.
		//List<SystemRole> allRoles = roleMapper.selectAll();
		List<SystemRole> allRoles = roleMapper.selectAllByDepartid(map);
		// 然后执行过滤
		// TODO 按部门过滤...
		List<SystemRole> rolesRoles = allRoles;
		// ... 
		// 获取用户的所有关联信息
		List<UserRoleWrapper> wrappers =  userRoleWrapperMapper.getAllByUserid(userid);
		//
		// ...
		
		// 迭代,设置. 优化算法,外层迭代大的集合,内层迭代小的集合。
		Iterator<SystemRole> iteratorP = rolesRoles.iterator();
		while (iteratorP.hasNext()) {
			SystemRole role = (SystemRole) iteratorP.next();
			if(null == role){
				continue;
			}
			Integer roleid = role.getRoleid();
			// 根据roleid,判断是否有对应用户-角色记录
			if (hasRole(wrappers, roleid)) {
				// 增加checked信息
				role.setChecked(true);
				//System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!已有用户-角色:roleid="+roleid);
			} else {
				//System.out.println("不存在:roleid="+roleid);
			}
		}
		//
		return rolesRoles;
	}
	
	
	/**
	 * 更新用户的用户-角色信息
	 */
	@Override
	@Transactional
	public boolean updateByUser(String userid, List<UserRoleWrapper> newroles) {
		//
		if(null == userid || null==newroles){
			return false;
		}
		
		// 获取角色的所有老的用户-角色
		List<UserRoleWrapper> oldwrappers = userRoleWrapperMapper.getAllByUserid(userid);
		
		// 1. 遍历找出新增用户-角色
		List<UserRoleWrapper> addedwrappers = inAnotInB(newroles, oldwrappers);
		// 1.1提交的所有的新的用户-角色
		Iterator<UserRoleWrapper> iteratorN = addedwrappers.iterator();
		while (iteratorN.hasNext()) {
			UserRoleWrapper nWrapper = (UserRoleWrapper) iteratorN.next();
			// 1.1.1新增新的用户-角色
			userRoleWrapperMapper.insertSelective(nWrapper);
			// 
			//System.out.println("新增用户-角色记录,roleid="+ nWrapper.getRoleid());
		}

		// 2.遍历找出删除的用户-角色
		List<UserRoleWrapper> removedwrappers = inAnotInB(oldwrappers, newroles);
		// 2.1将没有的用户-角色删了 // TODO 考虑使用单条语句,删除
		Iterator<UserRoleWrapper> iteratorD = removedwrappers.iterator();
		while (iteratorD.hasNext()) {
			UserRoleWrapper delWrapper = (UserRoleWrapper) iteratorD.next();
			// 2.1.1删除没有的用户-角色
			userRoleWrapperMapper.delete(delWrapper);
			//System.out.println("删除用户-角色记录,roleid="+ delWrapper.getRoleid());
		}
		// 如果没出错,走到这里,那就是更新成功
		return true;
	}
	
	/**
	 * 返回在 A中,不在 B中的记录. 如果查相反的东西,请交换参数
	 * @param a
	 * @param b
	 * @return
	 */
	private List<UserRoleWrapper> inAnotInB(List<UserRoleWrapper> a, List<UserRoleWrapper> b){
		//
		if (null == a || null == b) {
			return null;
		}
		//
		List<UserRoleWrapper> inAnotInB = new ArrayList<UserRoleWrapper>();
		//
		Iterator<UserRoleWrapper> iteratorA = a.iterator();
		//
		while (iteratorA.hasNext()) {
			UserRoleWrapper wrapperA = (UserRoleWrapper) iteratorA.next();
			//
			if(null == wrapperA){
				continue;
			}
			//
			Integer roleid = wrapperA.getRoleid();
			if (hasRole(b, roleid)) {
				// 有记录就不管
			} else {
				// 没有就放到记录中
				inAnotInB.add(wrapperA);
			}			
		}
		
		//
		return inAnotInB;
	}
	

	/**
	 *  是否有对应记录
	 * @param wrappers
	 * @param roleid
	 * @return
	 */
	private boolean hasRole(List<UserRoleWrapper> wrappers, Integer roleid){
		if(null == wrappers || roleid < 1){
			return false;
		}
		//
		Iterator<UserRoleWrapper> iteratorW = wrappers.iterator();
		while (iteratorW.hasNext()) {
			UserRoleWrapper userRoleWrapper = (UserRoleWrapper) iteratorW.next();
			if(null != userRoleWrapper){
				int rid = userRoleWrapper.getRoleid();
				// 具有记录
				if(roleid == rid){
					return true;
				}
			}
		}
		//
		return false;
	}

	//start add by blue_lv 2015-07-14
	@Override
	public List<UserRoleWrapper> getRolesOfUser(String userid) {
		return userRoleWrapperMapper.getAllByUserid(userid);
	}
	//end add by blue_lv 2015-07-14
}
