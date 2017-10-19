package com.sinog2c.service.impl.system;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sinog2c.dao.api.system.RolePermissionsWrapperMapper;
import com.sinog2c.dao.api.system.SystemPermissionsMapper;
import com.sinog2c.model.system.RolePermissionsWrapper;
import com.sinog2c.model.system.SystemPermissions;
import com.sinog2c.model.system.SystemUser;
import com.sinog2c.service.api.system.SystemPermissionsService;
import com.sinog2c.service.impl.base.ServiceImplBase;
import com.sinog2c.util.common.StringNumberUtil;

@Service("systemPermissionsService")
public class SystemPermissionsServiceImpl extends ServiceImplBase implements SystemPermissionsService {

	@Autowired
	private SystemPermissionsMapper permissionsMapper = null;
	@Autowired
	private RolePermissionsWrapperMapper rolePermissionsWrapperMapper=null;

	public SystemPermissionsMapper getPermissionsMapper() {
		return permissionsMapper;
	}
	public void setPermissionsMapper(SystemPermissionsMapper permissionsMapper) {
		this.permissionsMapper = permissionsMapper;
	}
	public RolePermissionsWrapperMapper getRolePermissionsWrapperMapper() {
		return rolePermissionsWrapperMapper;
	}
	public void setRolePermissionsWrapperMapper(RolePermissionsWrapperMapper rolePermissionsWrapperMapper) {
		this.rolePermissionsWrapperMapper = rolePermissionsWrapperMapper;
	}

	@Override
	@Transactional
	public int add(SystemPermissions permissions, SystemUser operator) {

		if (null == permissions) {
			return 0;
		}
		int rows = permissionsMapper.insertSelective(permissions);
		return rows;
	}

	@Override
	@Transactional
	public int insertWithId(SystemPermissions permissions, SystemUser operator) {
		if (null == permissions) {
			return 0;
		}
		int rows = permissionsMapper.insertSelective(permissions);
		return rows;
	}

	@Override
	@Transactional
	public int insertByMap(Map<String, Object> map, SystemUser operator) {
		if (null == map) {
			return 0;
		}
		if (null != operator) {
			// map.put("opid", operator.getUserid());//
		}
		Object spid = map.get("spid");

		if (null == spid) { // map必须进行数据校验
			return 0;
		}
		//
		Object sn = map.get("sn");
		sn = StringNumberUtil.parseShort("" + sn, 9999);
		// map.put("sn", sn);
		//
		//
		int rows = permissionsMapper.insertByMap(map);
		return rows;
	}

	@Override
	@Transactional
	public int update(SystemPermissions permissions, SystemUser operator) {
		if (null == permissions) {
			return 0;
		}
		int permissionsult = permissionsMapper.update(permissions);
		return permissionsult;
	}

	@Override
	@Transactional
	public int updateByMap(Map<String, Object> map, SystemUser operator) {
		if (null == map) {
			return 0;
		}
		int rows = permissionsMapper.updateByMap(map);
		return rows;
	}

	@Override
	@Transactional
	public int delete(String spid, SystemUser operator) {
		if (null == spid) {
			return 0;
		}
		// 权限和日志,都可以在这里做...
		// 考虑到删除业务使用量不大, 不必考虑封装数据库SQL
		// 提取旧元素, 开始事务, 插入历史表, 删除现有记录, 提交事务
		//
		SystemPermissions permissions = getByPermissionsId(spid);
		if (null == permissions) {
			return 0;
		}
		// 如果不是叶子节点, 并且有子元素, 则需要拒绝删除 // 或者提供批量删除的接口
		// String spid = permissions.getResid();

		// 开始事务
		// ...
		// 插入历史表
		// int hisrows = permissionsMapper.insertToHistory(permissions);
		// if(1 == 0 || "".length() > 5){
		// 删除权限表的记录
		int permissionsrows = permissionsMapper.delete(permissions);
		if (1 == permissionsrows) {
			// 记录日志
			// ...
			// 提交事务
			return 1;
		} else {
			// throw new RuntimeException("删除记录失败!spid="+spid);
		}
		// }
		//
		return 0;
	}

	@Override
	public SystemPermissions getByPermissionsId(String spid) {
		if (null == spid) {
			return null;
		}
		SystemPermissions permissions = permissionsMapper.getBySpid(spid);
		//
		return permissions;
	}

	@Override
	public int countAll() {
		return permissionsMapper.countAll();
	}

	@Override
	public int getNextId() {
		return permissionsMapper.getNextId();
	}

	@Override
	public List<SystemPermissions> listByPermissionsPid(String spparent) {
		if (null == spparent) {
			return null;
		}
		return null;
	}

	@Override
	public List<SystemPermissions> selectAll() {
		List<SystemPermissions> list = permissionsMapper.selectAll();
		return list;
	}

	@Override
	public List<SystemPermissions> getAllByPage(int pageIndex, int pageSize) {
		// 计算页码
		int start = pageIndex * pageSize + 1;
		int end = start + pageSize - 1;

		List<SystemPermissions> list = permissionsMapper.getAllByPage(start, end);
		return list;
	}
	
	@Override
	public List<SystemPermissions> listByRoleid(String roleid) {
		
		// 先获取所有权限信息.
		List<SystemPermissions> allPermissions = permissionsMapper.selectAll();
		
		// 然后执行过滤
		// TODO 按部门过滤...
		List<SystemPermissions> rolesPermissions = allPermissions;
		// ... 
		// 获取角色的所有关联信息
		List<RolePermissionsWrapper> wrappers = rolePermissionsWrapperMapper.getAllByRoleid(roleid);
		//
		// ...
		
		// 迭代,设置. 优化算法,外层迭代大的集合,内层迭代小的集合。
		Iterator<SystemPermissions> iteratorP = rolesPermissions.iterator();
		while (iteratorP.hasNext()) {
			SystemPermissions permissions = (SystemPermissions) iteratorP.next();
			if(null == permissions){
				continue;
			}
			String spid = permissions.getSpid();
			// 根据spid,判断是否有对应权限记录
			if (hasPermission(wrappers, spid)) {
				// 增加checked信息
				permissions.setChecked(true);
				//System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!已有权限:spid="+spid);
			} else {
				//System.out.println("不存在:spid="+spid);
			}
		}
		//
		return rolesPermissions;
	}
	
	
	/**
	 * 更新角色的权限信息
	 */
	@Override
	@Transactional
	public boolean updateByRole(String roleid, List<RolePermissionsWrapper> newpermissions) {
		//
		if(null == roleid || null==newpermissions){
			return false;
		}
		
		// 获取角色的所有老的权限
		List<RolePermissionsWrapper> oldwrappers = rolePermissionsWrapperMapper.getAllByRoleid(roleid);
		
		// 1. 遍历找出新增权限
		List<RolePermissionsWrapper> addedwrappers = inAnotInB(newpermissions, oldwrappers);
		// 1.1提交的所有的新的权限
		Iterator<RolePermissionsWrapper> iteratorN = addedwrappers.iterator();
		while (iteratorN.hasNext()) {
			RolePermissionsWrapper nWrapper = (RolePermissionsWrapper) iteratorN.next();
			// 1.1.1新增新的权限
			rolePermissionsWrapperMapper.insertSelective(nWrapper);
			// 
			//System.out.println("新增权限记录,spid="+ nWrapper.getSpid());
		}

		// 2.遍历找出删除的权限
		List<RolePermissionsWrapper> removedwrappers = inAnotInB(oldwrappers, newpermissions);
		// 2.1将没有的权限删了
		Iterator<RolePermissionsWrapper> iteratorD = removedwrappers.iterator();
		while (iteratorD.hasNext()) {
			RolePermissionsWrapper delWrapper = (RolePermissionsWrapper) iteratorD.next();
			// 2.1.1删除没有的权限
			rolePermissionsWrapperMapper.delete(delWrapper);
			//System.out.println("删除权限记录,spid="+ delWrapper.getSpid());
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
	private List<RolePermissionsWrapper> inAnotInB(List<RolePermissionsWrapper> a, List<RolePermissionsWrapper> b){
		//
		if (null == a || null == b) {
			return null;
		}
		//
		List<RolePermissionsWrapper> inAnotInB = new ArrayList<RolePermissionsWrapper>();
		//
		Iterator<RolePermissionsWrapper> iteratorA = a.iterator();
		//
		while (iteratorA.hasNext()) {
			RolePermissionsWrapper wrapperA = (RolePermissionsWrapper) iteratorA.next();
			//
			if(null == wrapperA){
				continue;
			}
			//
			String spid = wrapperA.getSpid();
			if (hasPermission(b, spid)) {
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
	 * @param permid
	 * @return
	 */
	private boolean hasPermission(List<RolePermissionsWrapper> wrappers, String permid){
		if(null == wrappers || null == permid){
			return false;
		}
		//
		Iterator<RolePermissionsWrapper> iteratorW = wrappers.iterator();
		while (iteratorW.hasNext()) {
			RolePermissionsWrapper rolePermissionsWrapper = (RolePermissionsWrapper) iteratorW.next();
			if(null != rolePermissionsWrapper){
				String spid = rolePermissionsWrapper.getSpid();
				// 具有记录
				if(permid.equalsIgnoreCase(spid)){
					return true;
				}
			}
		}
		//
		return false;
	}

}
