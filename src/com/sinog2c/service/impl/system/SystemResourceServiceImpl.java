package com.sinog2c.service.impl.system;

import java.sql.Clob;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;
import com.sinog2c.dao.api.flow.FlowDeliverMapper;
import com.sinog2c.dao.api.system.ResourceTypeMapper;
import com.sinog2c.dao.api.system.RoleResourceWrapperMapper;
import com.sinog2c.dao.api.system.SystemResourceMapper;
import com.sinog2c.dao.api.system.TbsysTemplateMapper;
import com.sinog2c.model.flow.FlowDeliver;
import com.sinog2c.model.system.ResourceType;
import com.sinog2c.model.system.RoleResourceWrapper;
import com.sinog2c.model.system.SystemOrganization;
import com.sinog2c.model.system.SystemResource;
import com.sinog2c.model.system.SystemUser;
import com.sinog2c.service.api.common.CommonSQLSolutionService;
import com.sinog2c.service.api.common.CommonService;
import com.sinog2c.service.api.system.SystemModelService;
import com.sinog2c.service.api.system.SystemResourceService;
import com.sinog2c.service.impl.base.ServiceImplBase;
import com.sinog2c.util.common.JsonUtil;
import com.sinog2c.util.common.MapUtil;
import com.sinog2c.util.common.StringNumberUtil;
import com.sinog2c.util.report.RMEngine;

@Service("systemResourceService")
public class SystemResourceServiceImpl extends ServiceImplBase implements SystemResourceService {
	@Autowired
	private TbsysTemplateMapper tbsysTemplateMapper;
	@Autowired
	private SystemModelService systemModelService;
	@Autowired
	private ResourceTypeMapper resourcetypemapper;
	@Autowired(required=true)
	private CommonSQLSolutionService solutionService;
	@Autowired(required=true)
	private FlowDeliverMapper flowDeliverMapper;
	@Autowired(required=true)
	private CommonService commonService;
	
	/**
	 * 资源刷新时间.
	 */
	public static long REFRESH_RES_TIME_MILLIS = 1 * 30 * 60 * 1000;
	@Autowired
	private SystemResourceMapper systemResourceMapper = null;
	@Autowired
	private RoleResourceWrapperMapper roleResourceWrapperMapper = null;
	@Autowired
	private ResourceTypeMapper resourceTypeMapper = null;

	@Override
	@Transactional
	public int add(SystemResource res, SystemUser operator) {
		if (null == res) {
			return 0;
		}
		if (null != operator) {
			res.setOpid(operator.getUserid());
		}
		// int rows = systemResourceMapper.insert(res);
		int rows = systemResourceMapper.insertSelective(res);
		return rows;
	}

	@Override
	@Transactional
	public int insertWithId(SystemResource res, SystemUser operator) {
		if (null == res) {
			return 0;
		}
		if (null != operator) {
			res.setOpid(operator.getUserid());
		}
		int rows = systemResourceMapper.insertSelective(res);
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
		Object resid = map.get("resid");

		if (null == resid) { // map必须进行数据校验
			return 0;
		}
		//
		Object sn = map.get("sn");
		sn = StringNumberUtil.parseShort("" + sn, 9999);
		map.put("sn", sn);
		//
		Object ismenu = map.get("ismenu");
		ismenu = StringNumberUtil.parseShort("" + ismenu, 0);
		map.put("ismenu", ismenu);
		//
		//
		int rows = systemResourceMapper.insertByMap(map);
		return rows;
	}

	@Override
	@Transactional
	public int update(SystemResource res, SystemUser operator) {
		if (null == res) {
			return 0;
		}
		if (null != operator) {
			res.setOpid(operator.getUserid());
		}
		int result = systemResourceMapper.update(res);
		return result;
	}

	@Override
	@Transactional
	public int updateByMap(Map<String, Object> map, SystemUser operator) {
		if (null == map) {
			return 0;
		}
		if (null != operator) {
			map.put("opid", operator.getUserid());//
		}
		Object resid = map.get("resid");

		if (null == resid) { // map必须进行数据校验
			return 0;
		}
		//
		Object sn = map.get("sn");
		sn = StringNumberUtil.parseShort("" + sn, 9999);
		map.put("sn", sn);
		//
		Object ismenu = map.get("ismenu");
		ismenu = StringNumberUtil.parseShort("" + ismenu, 0);
		map.put("ismenu", ismenu);
		//
		int rows = systemResourceMapper.updateByMap(map);
		return rows;
	}

	public int deleteSingle(SystemResource resource, boolean force) {
		if (null == resource) {
			return 0;
		}
		// 插入历史表
		int hisrows = systemResourceMapper.insertToHistory(resource);
		if (1 == hisrows) {
			// 删除资源表的记录
			int resrows = systemResourceMapper.delete(resource);
			if (1 == resrows) {
				// 记录日志
				// ...
				// 提交事务
				return 1;
			} else {
				throw new RuntimeException("删除记录失败!resId=" + resource.getResid());
			}
		} else {
			return 0;
		}
	}

	@Override
	@Transactional
	public int delete(String resId, SystemUser operator) {
		if (null == resId) {
			return 0;
		}
		// 考虑到删除业务使用量不大, 不必考虑封装数据库SQL
		// 提取旧元素, 开始事务, 插入历史表, 删除现有记录, 提交事务
		//
		SystemResource resource = getByResourceId(resId);
		if (null == resource) {
			return 0;
		}
		// 如果不是叶子节点, 并且有子元素, 则需要拒绝删除 // 或者提供批量删除的接口
		// String resid = resource.getResid();
		// 迭代删除
		List<SystemResource> sublist = systemResourceMapper.listByResourcePid(resId);
		//
		Iterator<SystemResource> iteratorR = sublist.iterator();
		while (iteratorR.hasNext()) {
			SystemResource ressub = (SystemResource) iteratorR.next();
			deleteSingle(ressub, true);
		}
		// 删除
		// 开始事务
		// ...
		// 插入历史表
		int hisrows = systemResourceMapper.insertToHistory(resource);
		if (1 == hisrows) {
			// 删除资源表的记录
			int resrows = systemResourceMapper.delete(resource);
			if (1 == resrows) {
				// 记录日志
				// ...
				// 提交事务
				return 1;
			} else {
				throw new RuntimeException("删除记录失败!resId=" + resId);
			}
		}
		//
		return 0;
	}

	@Override
	public SystemResource getByResourceId(String resourceId) {
		if (null == resourceId) {
			return null;
		}
		SystemResource resource = systemResourceMapper.getByResourceId(resourceId);
		//
		return resource;
	}

	@Override
	public int countAll() {
		return systemResourceMapper.countAll();
	}

	@Override
	public int getNextId() {
		return systemResourceMapper.getNextId();
	}

	@Override
	public List<SystemResource> selectAll() {
		List<SystemResource> list = systemResourceMapper.selectAll();
		return list;
	}

	@Override
	public List<SystemResource> getAllByPage(int pageIndex, int pageSize) {
		// 计算页码
		int start = pageIndex * pageSize + 1;
		int end = start + pageSize - 1;

		List<SystemResource> list = systemResourceMapper.getAllByPage(start, end);
		return list;
	}

	/*
	 * 获取所有菜单
	 * 
	 * @return
	 */
	/*
	 * public List<SystemResource> getAllMenu(){ List<SystemResource> list =
	 * systemResourceMapper.getAllMenu(); return list; }
	 */
	@Override
	public List<SystemResource> getAllResourceByUser(SystemUser user) {
		//
		if (null == user) {
			return null;
		}
		// 要返回的结果
		List<SystemResource> result = null;
		//
		List<SystemResource> resources = user.getAllResources();
		Date refreshResTime = user.getRefreshResTime();
		// 有资源缓存,则直接返回
		// 最简单的缓存实现,不分层处理
		if (null != resources && false == resources.isEmpty()) {
			//
			if (null != refreshResTime) {
				long lastTimeMillis = refreshResTime.getTime();
				long currentTimeMillis = System.currentTimeMillis();
				// 尚未过期
				if (currentTimeMillis - lastTimeMillis < REFRESH_RES_TIME_MILLIS) {
					//System.err.println(LogUtil.currentMethodName() + " 利用缓存");
					result =  resources;
				} else {
					//System.err.println(LogUtil.currentMethodName() + " 缓存过期,需要重新获取用户资源");
				}
			}
		} else {
			//System.err.println(LogUtil.currentMethodName() + " 查询数据库。。。");
		}
		// 如果 result 为 null, 从数据库获取
		if(null == result){
			String userid = user.getUserid();
			resources = systemResourceMapper.getResourcesByUserid(userid, 0);
			if (null != resources) {
				// 缓存到用户
				user.setAllResources(resources);
				// 刷新时间
				user.setRefreshResTime(new Date());
			}
			result = resources;
		}
		// 遍历克隆对象; 返回的接口是 ArrayList
		if(null != result){
			ArrayList<SystemResource> clonedList = new ArrayList<SystemResource>();
			Iterator<SystemResource> iteratorResult = result.iterator();
			while (iteratorResult.hasNext()) {
				SystemResource res = (SystemResource) iteratorResult.next();
				if(null == res){
					continue;
				}
				try {
					SystemResource resClone = res.clone();
					clonedList.add(resClone);
				} catch (CloneNotSupportedException e) {
					e.printStackTrace();
				}
			}
			// 缓存后,每次使用,都是克隆后的数据
			result = clonedList;
		}
		
		//
		return result;
	}
	
	@Override
	public SystemResource getResourceByUserAndId(SystemUser user, String menuid) {
		SystemResource resource = null;
		//
		if (null == user || null == menuid || menuid.trim().isEmpty()) {
			return resource;
		}
		List<SystemResource> resList = getAllResourceByUser(user);
		//
		if(null == resList || resList.isEmpty()){
			return resource;
		}

		Iterator<SystemResource> iteratorR = resList.iterator();
		while (iteratorR.hasNext()) {
			SystemResource res = (SystemResource) iteratorR.next();
			if(null == res){
				continue;
			}
			//
			String resid = res.getResid();
			if(menuid.equals(resid)){
				resource = res;
				break;
			}
		}
		//
		return resource;
	}

	public List<SystemResource> filterByType(List<SystemResource> resources, int restypeid) {
		if (null == resources) {
			return null;
		}
		// 传入0,则返回所有
		if (0 == restypeid) {
			return resources;
		}
		//
		List<SystemResource> list = new ArrayList<SystemResource>();
		// 迭代选择
		Iterator<SystemResource> iteratorR = resources.iterator();
		while (iteratorR.hasNext()) {
			SystemResource res = (SystemResource) iteratorR.next();
			if (null == res) {
				continue;
			}
			int typeid = res.getRestypeid();
			if (typeid == restypeid) {
				list.add(res);
			}
		}
		//
		return list;
	}

	/**
	 * 
	 * @param resources
	 * @param presid
	 * @param deep
	 *            是否深度遍历,1为是,0为否
	 * @return
	 */
	public List<SystemResource> filterByPid(List<SystemResource> resources, String presid, int deep) {
		if (null == resources || null == presid) {
			return resources;
		}
		//
		List<SystemResource> sublist = new ArrayList<SystemResource>();
		// 至少遍历一次
		Iterator<SystemResource> iteratorR1 = resources.iterator();
		while (iteratorR1.hasNext()) {
			SystemResource res = (SystemResource) iteratorR1.next();
			if (null == res) {
				continue;
			}
			String pid = res.getPresid();
			if (presid.equals(pid)) {
				sublist.add(res);
			}
		}
		// 只可能走一条路径
		if (0 == deep) {
			// 非深度迭代
			return sublist;
		}
		// 继续深度迭代.
		List<SystemResource> deeplist = new ArrayList<SystemResource>();
		deeplist.addAll(sublist);

		Iterator<SystemResource> iteratorS = sublist.iterator();
		while (iteratorS.hasNext()) {
			SystemResource res = (SystemResource) iteratorS.next();
			// 获取资源ID，而不是父ID
			String pid = res.getResid();
			// 递归、查找子元素的子元素
			// 因为层级不可能很深,所以可以采用递归
			List<SystemResource> slist = filterByPid(resources, pid, deep);
			deeplist.addAll(slist);
		}

		//
		return deeplist;
	}

	// 根据 presid 和类型, 选择子元素
	public List<SystemResource> filterByPidAndType(List<SystemResource> resources, String presid, int restypeid) {
		if (null == resources) {
			return null;
		}
		// 先根据pid过滤, 不进行深度遍历
		//start modify bu blue_lv 2015-07-20
		List<SystemResource> resBypid = filterByPid(resources, presid, 1);//原来是0
		//end modify bu blue_lv 2015-07-20
		// 再根据 type过滤
		List<SystemResource> list = filterByType(resBypid, restypeid);
		//
		return list;
	}

	@Override
	public List<SystemResource> getMenuByUser(SystemUser user) {
		if (null == user) {
			return null;
		}
		// 
		// String userid = user.getUserid();
		// List<SystemResource> resources =
		// systemResourceMapper.getResourcesByUserid(userid,
		// ResourceType.TYPE_ID_MENU);

		List<SystemResource> resources = getAllResourceByUser(user);

		List<SystemResource> menus = filterByType(resources, ResourceType.TYPE_ID_MENU);
		//
		return menus;
	}

	@Override
	public List<SystemResource> getButtonInline(SystemUser user, String menuid) {
		if (null == user || null == menuid) {
			return null;
		}
		List<SystemResource> resources = getAllResourceByUser(user);
		int restypeid = ResourceType.TYPE_ID_BUTTON_INLINE;
		List<SystemResource> buttons = filterByPidAndType(resources, menuid, restypeid);
		// 
		/*
		 * String userid = user.getUserid(); List<SystemResource> resources =
		 * systemResourceMapper.getResourcesByUserid(userid,
		 * ResourceType.TYPE_ID_BUTTON_INLINE); // 过滤.. List<SystemResource>
		 * buttons = new ArrayList<SystemResource>();
		 * 
		 * Iterator<SystemResource> iteratorR = resources.iterator(); while
		 * (iteratorR.hasNext()) { SystemResource systemResource =
		 * (SystemResource) iteratorR.next(); if(null == systemResource){
		 * continue; } // String pid = systemResource.getPresid();
		 * if(menuid.equals(pid)){ buttons.add(systemResource); } }
		 */
		//
		return buttons;
	}

	@Override
	public List<SystemResource> getButtonTop(SystemUser user, String menuid) {
		if (null == user || null == menuid) {
			return null;
		}
		List<SystemResource> resources = getAllResourceByUser(user);
		int restypeid = ResourceType.TYPE_ID_BUTTON_INLINE;
		List<SystemResource> buttons = filterByPidAndType(resources, menuid, restypeid);
		/*
		 * // String userid = user.getUserid(); List<SystemResource> resources =
		 * systemResourceMapper.getResourcesByUserid(userid,
		 * ResourceType.TYPE_ID_BUTTON_TOP); // 过滤.. List<SystemResource>
		 * buttons = new ArrayList<SystemResource>();
		 * 
		 * Iterator<SystemResource> iteratorR = resources.iterator(); while
		 * (iteratorR.hasNext()) { SystemResource systemResource =
		 * (SystemResource) iteratorR.next(); if(null == systemResource){
		 * continue; } // String pid = systemResource.getPresid();
		 * if(menuid.equals(pid)){ buttons.add(systemResource); } }
		 */
		//
		return buttons;
	}

	@Override
	public List<SystemResource> listByMenuid(SystemUser user, String menuid) {
		if (null == user || null == menuid) {
			return null;
		}

		List<SystemResource> resources = getAllResourceByUser(user);
		// int restypeid= ResourceType.TYPE_ID_BUTTON_INLINE;
		List<SystemResource> list = filterByPid(resources, menuid, 0);

		return list;
		// return systemResourceMapper.listByResourcePid(menuid);
	}

	@Override
	public List<SystemResource> listByMenuid(SystemUser user, String menuid, int restypeid) {
		if (null == user || null == menuid) {
			return null;
		}

		List<SystemResource> resources = getAllResourceByUser(user);
		// int restypeid= ResourceType.TYPE_ID_BUTTON_INLINE;
		List<SystemResource> list = filterByPidAndType(resources, menuid, restypeid);

		return list;
		/*
		 * // String userid = user.getUserid(); List<SystemResource> resources =
		 * systemResourceMapper.getResourcesByUserid(userid, restypeid); // 过滤..
		 * List<SystemResource> buttons = new ArrayList<SystemResource>();
		 * 
		 * Iterator<SystemResource> iteratorR = resources.iterator(); while
		 * (iteratorR.hasNext()) { SystemResource systemResource =
		 * (SystemResource) iteratorR.next(); if(null == systemResource){
		 * continue; } // String pid = systemResource.getPresid();
		 * if(menuid.equals(pid)){ buttons.add(systemResource); } }
		 * 
		 * // return buttons;
		 */
	}

	@Override
	public List<SystemResource> listByMenuid(SystemUser user, String menuid, boolean forceNoUser) {

		if (null == menuid) {
			return null;
		}
		if (true == forceNoUser) {
			return systemResourceMapper.listByResourcePid(menuid);
		}
		if (null == user) {
			return null;
		}
		//
		List<SystemResource> reslist = listByMenuid(user, menuid);
		/*
		 * // String userid = user.getUserid(); // 先获取资源信息. List<SystemResource>
		 * userRes = systemResourceMapper.getResourcesByUserid(userid, 0);
		 * List<SystemResource> reslist = new ArrayList<SystemResource>();
		 * 
		 * Iterator<SystemResource> iteratorR = userRes.iterator(); while
		 * (iteratorR.hasNext()) { SystemResource systemResource =
		 * (SystemResource) iteratorR.next(); if(null == systemResource){
		 * continue; } // String pid = systemResource.getPresid();
		 * if(menuid.equals(pid)){ reslist.add(systemResource); } }
		 */
		//
		return reslist;
	}

	@Override
	public List<SystemResource> listByRoleid(String roleid) {

		// 先获取所有角色-资源信息.
		List<SystemResource> allResource = systemResourceMapper.selectAll();

		// 然后执行过滤
		// 按部门过滤...?
		List<SystemResource> rolesResources = allResource;
		// ...
		// 获取角色的所有关联信息
		List<RoleResourceWrapper> wrappers = roleResourceWrapperMapper.getAllByRoleid(roleid);
		//
		// ...

		// 迭代,设置. 优化算法,外层迭代大的集合,内层迭代小的集合。
		Iterator<SystemResource> iteratorP = rolesResources.iterator();
		while (iteratorP.hasNext()) {
			SystemResource resource = (SystemResource) iteratorP.next();
			if (null == resource) {
				continue;
			}
			String resid = resource.getResid();
			// 根据resid,判断是否有对应角色-资源记录
			if (hasResource(wrappers, resid)) {
				// 增加checked信息
				resource.setChecked(true);
				// System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!已有角色-资源:resid="+resid);
			} else {
				// System.out.println("不存在:resid="+resid);
			}
		}
		//
		return rolesResources;
	}

	/**
	 * 更新角色的角色-资源信息
	 */
	@Override
	@Transactional
	public boolean updateByRole(String roleid, List<RoleResourceWrapper> newresources) {
		//
		if (null == roleid || null == newresources) {
			return false;
		}

		// 获取角色的所有老的角色-资源
		List<RoleResourceWrapper> oldwrappers = roleResourceWrapperMapper.getAllByRoleid(roleid);

		// 1. 遍历找出新增角色-资源
		List<RoleResourceWrapper> addedwrappers = inAnotInB(newresources, oldwrappers);
		// 1.1提交的所有的新的角色-资源
		Iterator<RoleResourceWrapper> iteratorN = addedwrappers.iterator();
		while (iteratorN.hasNext()) {
			RoleResourceWrapper nWrapper = (RoleResourceWrapper) iteratorN.next();
			// 1.1.1新增新的角色-资源
			roleResourceWrapperMapper.insertSelective(nWrapper);
			// 
			// System.out.println("新增角色-资源记录,resid="+ nWrapper.getResid());
		}

		// 2.遍历找出删除的角色-资源
		List<RoleResourceWrapper> removedwrappers = inAnotInB(oldwrappers, newresources);
		// 2.1将没有的角色-资源删了
		Iterator<RoleResourceWrapper> iteratorD = removedwrappers.iterator();
		while (iteratorD.hasNext()) {
			RoleResourceWrapper delWrapper = (RoleResourceWrapper) iteratorD.next();
			// 2.1.1删除没有的角色-资源
			roleResourceWrapperMapper.delete(delWrapper);
			// System.out.println("删除角色-资源记录,resid="+ delWrapper.getResid());
		}
		// 如果没出错,走到这里,那就是更新成功
		return true;
	}

	/**
	 * 返回在 A中,不在 B中的记录. 如果查相反的东西,请交换参数
	 * 
	 * @param a
	 * @param b
	 * @return
	 */
	private List<RoleResourceWrapper> inAnotInB(List<RoleResourceWrapper> a, List<RoleResourceWrapper> b) {
		//
		if (null == a || null == b) {
			return null;
		}
		//
		List<RoleResourceWrapper> inAnotInB = new ArrayList<RoleResourceWrapper>();
		//
		Iterator<RoleResourceWrapper> iteratorA = a.iterator();
		//
		while (iteratorA.hasNext()) {
			RoleResourceWrapper wrapperA = (RoleResourceWrapper) iteratorA.next();
			//
			if (null == wrapperA) {
				continue;
			}
			//
			String resid = wrapperA.getResid();
			if (hasResource(b, resid)) {
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
	 * 是否有对应记录
	 * 
	 * @param wrappers
	 * @param permid
	 * @return
	 */
	private boolean hasResource(List<RoleResourceWrapper> wrappers, String permid) {
		if (null == wrappers || null == permid) {
			return false;
		}
		//
		Iterator<RoleResourceWrapper> iteratorW = wrappers.iterator();
		while (iteratorW.hasNext()) {
			RoleResourceWrapper roleResourceWrapper = (RoleResourceWrapper) iteratorW.next();
			if (null != roleResourceWrapper) {
				String resid = roleResourceWrapper.getResid();
				// 具有记录
				if (permid.equalsIgnoreCase(resid)) {
					return true;
				}
			}
		}
		//
		return false;
	}

	// listAllResourceType 的缓存
	private static List<ResourceType> types_cache = null;
	private static Date types_cache_time = null;

	@Override
	public List<ResourceType> listAllResourceType() {
		//
		if (null != types_cache) {
			//
			if (null != types_cache_time) {
				long lastTimeMillis = types_cache_time.getTime();
				long currentTimeMillis = System.currentTimeMillis();
				// 尚未过期
				if (currentTimeMillis - lastTimeMillis < REFRESH_RES_TIME_MILLIS) {
					// System.err.println(LogUtil.currentMethodName() +
					// " 利用缓存");
					return types_cache;
				} else {
					// System.err.println(LogUtil.currentMethodName() +
					// " 缓存过期,需要重新获取用户资源");
				}
			}
		}
		// 查询数据库
		List<ResourceType> types = resourceTypeMapper.listAll();
		// 缓存
		types_cache = types;
		types_cache_time = new Date();

		//
		return types;
	}
	
	@Override
	public List<SystemResource> listAllByPid(String pid) {
		//
		return null;
	}
	
	/**
	 * 方法描述：查询资格榜单的内容 显示在报表中
	 * @author mushuhong
	 * @version 2014年8月20日09:40:06
	 */
	@SuppressWarnings("all")
	public RMEngine queryQualificationDataRmEngine(String rid, SystemUser user,HttpServletRequest request){
		String menuid = request.getParameter("menuid");
		List<Map> listPlanSql = resourceTypeMapper.getPlanSqlByResid(menuid);
		
		List<List<Map>> listMapData = new ArrayList<List<Map>>();
		//循环sql
		if (listPlanSql.size() > 0) {
			for (int i = 0; i < listPlanSql.size(); i++) {
				Map mapSql = listPlanSql.get(i);
				//得到 单条 sql语句
				Clob clob = (Clob) (mapSql.get("SQLTEXT")== null?"":mapSql.get("SQLTEXT"));
				String planSql = "";
				try {
					planSql = clob.getSubString(1,(int)clob.length());
					if (!"".equals(planSql)) {
						//得到 单条sql语句的结果
						HashMap mapsql = new HashMap();
						planSql = this.whereSql(user, planSql,request);
						mapSql.put("sql", planSql);
						List<Map> listData = resourceTypeMapper.getReportDataByPlanSql(mapSql);
						//把key和value值都对应为 小写的字母
						List<Map> lowerListData = MapUtil.turnKeyToLowerCaseOfList(listData);
						
						//处理 批次年度(进入 对应的方法 可以看到说明)
						lowerListData = this.getReplaceBigBatch(lowerListData);
						
						listMapData.add(lowerListData);
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		
		//查询 需要显示字段的 数据类型 和长度(暂时不用)
		List<List<Map>> listMapType = new ArrayList<List<Map>>();
		return this.getRmEngineByRid(listMapData, listMapType, request);
	}
	/**
	 * 方法描述：把查询的结果放到报表中
	 * @author mushuhong
	 * @version 2014年8月20日09:51:03
	 * @param dataList  数据集合(出现多条sql的情况)
	 * @param listType  数据对应字段类型集合(出现多条sql的情况)
	 * @param request
	 * @return
	 */
	public RMEngine getRmEngineByRid(List<List<Map>> dataList,List<List<Map>> listType,HttpServletRequest request) {
		RMEngine engine = getEngin(dataList, listType, request);
		request.setAttribute("engine", engine);
		return engine;
	}
	
	
	@SuppressWarnings("all")
	private RMEngine getEngin(List<List<Map>> dataList,List<List<Map>> listType,HttpServletRequest request) {
		RMEngine engine = null;
		String  tbcode= "";//数据集名称
		String menuid = request.getParameter("menuid");//资源id
		try {
			engine = new RMEngine();
			String reportName = this.getReportNameBySn(menuid);
			if(engine!=null){
				//控件初始化 加载
				engine.Init();
				engine.ViewerVersion = "12.0.0.6";
				engine.ViewerFileName = "hwpostil.inf";
				//把查询的数据 放到报表中
				int i=0;
				for (List<Map> map : dataList) {
					tbcode = "tbcode";
					//动态组织数据集名
					tbcode = tbcode+""+i;
					engine.AddDataSet(tbcode,map,null);
					i++;
				}
				String path=request.getRealPath("").replace("\\", "/")+"/RMreportReport/";
				engine.SetReportFile(path+reportName+".rmf", 1);
				request.setAttribute("reportName", reportName);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return engine;
	}
	
	/**
	 *描述: 查询报表的数据集
	 *@author YangZR	2015-03-29
	 */
	@Override
	public RMEngine getReportEngin(Map<String,Object> paramMap, SystemUser user, Map<String,Object> listMap){
		RMEngine engine = null;
		try {
			engine = new RMEngine();
			//控件初始化 加载
			engine.Init();
			engine.ViewerVersion = "12.0.0.6";
			engine.ViewerFileName = "hwpostil.inf";
			
			//报表用系统模板
			if(StringNumberUtil.notEmpty(paramMap.get("tpsolutionid"))){
				String tpsolutionid = paramMap.get("tpsolutionid").toString();
				String[] tpsolutionidArr = tpsolutionid.split("@");
				if(3 == tpsolutionidArr.length){
					String solutionid = tpsolutionidArr[0];
					String key = tpsolutionidArr[1];
					String column = tpsolutionidArr[2];
					
					paramMap.put("solutionid", solutionid);
					
					String sysTemplate = commonService.assembleSysTemplateData(paramMap, user);
					Map<String,Object>  sysTemplateMap = new HashMap<String,Object>();
					sysTemplateMap.put(column, sysTemplate);
					List<Map> stMList = new ArrayList<Map>();
					stMList.add(sysTemplateMap);
					dealListData(stMList);
					engine.AddDataSet(key, stMList, null);
				}
			}
			
			Set<String> set = listMap.keySet();
			
			for(String key : set){
				Object listValue = listMap.get(key);
				//
				if(listValue instanceof List<?>){
					List<Map> dataList = (List<Map>)listValue;
					if(null!=dataList && dataList.size()>0){
						dealListData(dataList);
						engine.AddDataSet(key, dataList, null);
					}
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		//
		return engine;
	}
	
	private void dealListData(List<Map> list){
		if(null != list){
			for(Map<String,Object> map : list){
				Set<String> set = map.keySet();
				for(String key : set){
					if(StringNumberUtil.isEmpty(map.get(key))){
						map.put(key, "　");
					}
				}
			}
		}
	}
	
	
	//通过模板编号查询出对应的报表名称
	@SuppressWarnings("all")
	private String getReportNameBySn(String menuid){
		String rname = "";
		try {
			List<Map> list = resourceTypeMapper.getReportNameBySn(menuid);
			for (Map map : list) {
				rname = map.get("RNAME").toString();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rname;
	}
	
	@Override
	public String getReportNameByMenuid(String menuid){
		SystemResource resource = getByResourceId(menuid);
		if(null != resource){
			return resource.getText8();
		}
		return null;
	}
	
	/**
	 * 方法描述：根据查询方案中 不同需要替换的字段 内容 
	 * 进行替换即可
	 * @author mushuhong
	 * @version 2014年8月21日13:55:17
	 * @param 当前登录用户
	 * @param request
	 */
	public String whereSql(SystemUser user,String plansql,HttpServletRequest request){
		String whereSql = "";
		//禁止 加 重复的内置变量
		try {
			whereSql = plansql;
			if (plansql != null && !"".equals(plansql)) {
				String departid = user.getDepartid();//当前单位编号
				SystemOrganization so = user.getOrganization();
				String orgid = so.getOrgid();//当前部门编号
				String depart = so.getFullname();//单位全称
				String intermediatecourt = so.getIntermediatecourt();//中院
				String highcourt = so.getHighcourt();//高院
				String userid = user.getUserid();//当前用户编号
				String crimid = request.getParameter("crimid")==null?"":request.getParameter("crimid");//当前罪犯编号
				String crimids = request.getParameter("crimids")==null?"":request.getParameter("crimids");//当前罪犯编号
				String crimid2=(String)request.getAttribute("crimid");//罪犯编号
				String nowpunishmenttype = request.getParameter("nowpunishmenttype")==null?"":request.getParameter("nowpunishmenttype");//现刑种
				String anjianhao = request.getParameter("anjianhao")==null?"":request.getParameter("anjianhao");//案件号
				String mkey = request.getParameter("mkey");//会议记录 案件号
				String choosecrimid = request.getParameter("choosecrimid");//选择 多条罪犯数据替换
				String querycondition = request.getParameter("querycondition");
				String flowdefid = request.getParameter("flowdefid");//流程自定义id
				String otherid = request.getParameter("otherid");
				String casetype = request.getParameter("casetype")==null?"":request.getParameter("casetype");//案件类型
				String caseyear = request.getParameter("caseyear");//案件年
				String casemonth = request.getParameter("casemonth");//案件月
				String kaitingdate=request.getParameter("kaitingdate");//法院开庭时间
				String kaitingend=request.getParameter("kaitingend");//法院开庭结束时间
				String zhangtiedate=request.getParameter("zhangtiedate");//法院公告张贴时间
				String jianyuid=request.getParameter("jianyuname");//法院开庭监狱
				String flowdraftid = request.getParameter("flowdraftid");//流程草稿id
				casetype = java.net.URLDecoder.decode(casetype, "utf-8");
				String id = request.getParameter("orgid");
				String flowdraftids = request.getParameter("flowdraftids")==null?"0":request.getParameter("flowdraftids");//流程草稿id数组，唯一标识一条办案数据
				
				//减刑假释办案id
				if(plansql.contains("@paroleid")){
					String paroleid = request.getParameter("id");
					if(paroleid!=null){
						String[] ids = paroleid.split(",");
						String temps = ids[0];
						String[] temArr = temps.split("@");
						//罪犯编号就从数组里取出第一个罪犯编号
						whereSql = whereSql.replaceAll("@paroleid", "'"+temArr[0]+"'");
					}
				}
				// 根据单位编号查询()
				if (plansql.contains("@bumenid")) {
					if(id!=null){
						whereSql = whereSql.replaceAll("@bumenid", "'"+id+"'");
					}else{
						whereSql = whereSql.replaceAll("@bumenid", "SELECT dorgid  FROM TBFLOW_ORG_ORG org   WHERE org.orgid = '"+orgid+"'");
					}
				}
				//单位编号如天津(1209)
				if (plansql.contains("@sysdep")) {
					whereSql = whereSql.replaceAll("@sysdep", "'"+departid+"'");
				}
				//单位编号和@sysdep重复
				if (plansql.contains("@departid")) {
					whereSql = whereSql.replaceAll("@departid", "'"+departid+"'");
				}
				//单位全称
				if (plansql.contains("@depart")) {
					whereSql = whereSql.replaceAll("@depart", "'"+depart+"'");
				}
				if (plansql.contains("@dorgids")) {
					whereSql = whereSql.replaceAll("@dorgids", "(select t.dorgid from TBFLOW_ORG_ORG t where t.orgid='" + orgid + "')");
				}
				//intermediatecourt中院
				if (plansql.contains("@intermediatecourt")) {
					whereSql = whereSql.replaceAll("@intermediatecourt", "'"+intermediatecourt+"'");
				}
				//高院
				if (plansql.contains("@highcourt")) {
					whereSql = whereSql.replaceAll("@highcourt", "'"+highcourt+"'");
				}
				// 根据部门编号如(120901：一监区)
				if (plansql.contains("@orgid")) {
					whereSql = whereSql.replace("@orgid", "'"+orgid+"'");
				}
				//用户 进行 查询用户id(userid:nzgj)
				if (plansql.contains("@sysuser")) {
					whereSql = whereSql.replace("@sysuser", "'"+userid+"'");
				}
				//会议记录 TBFLOW_BASE_OTHER (主键)
				if (plansql.contains("@otherid")) {
					//注意在前台传值得时候 ，字符串不能再加双引号
					whereSql = whereSql.replace("@otherid", otherid.replace("\"", ""));
				}
				//根据罪犯编号组成的数组查询如crimid in ('1','2') ,crimids格式：编号1,编号2,编号3
				if(plansql.contains("@crimids")){
					crimids = crimids.replace(",","','");
					whereSql = whereSql.replace("@crimids","('"+crimids+"')");
				}
				//现刑种
				if(plansql.contains("@nowpunishmenttype")){
					whereSql = whereSql.replace("@nowpunishmenttype", nowpunishmenttype);
				}
				//根据罪犯编号 查询
				if(plansql.contains("@crimid")){
					if(null!=crimid&&!"".equals(crimid)){
						whereSql = whereSql.replace("@crimid", "'"+crimid+"'");
					}else{
						whereSql = whereSql.replace("@crimid", "'"+crimid2+"'");
					}
				}
				//流程定义id 
				if(plansql.contains("@flowdefid")){
					whereSql = whereSql.replace("@flowdefid", "'"+flowdefid+"'");
				}
				if(plansql.contains("@jailid")){
					whereSql = whereSql.replace("@jailid", "'"+jianyuid+"'");
				}
				//未知
				if(plansql.contains("@querycondition")){
					if(querycondition==null || "".equals(querycondition)) {
						querycondition=" ";
					}
					whereSql = whereSql.replace("@querycondition", querycondition);
				}
				//按照 案件号 进行查询（@anjianhao加上双引号，因为此处使用的in语句如果in后面括号中条件如果是一条数据那么就会出现问题，多条数据无问题）
				if (plansql.contains("@anjianhao")) {
					String anString = "(";
					String[] anjianhaos =  anjianhao.split(",");
                    if (anjianhaos.length >= 2) {
						for (int i = 0; i < anjianhaos.length; i++) {
							String[] anjians = anjianhaos[i].split("-");
							if (anjians.length == 1) {
								anString += "'"+caseyear+anjians[0]+"',"; 
							}else{
								int firstaj = Integer.parseInt(anjians[0]);
								int lastaj = Integer.parseInt(anjians[anjians.length-1]);
								for (int j = 0; j <= lastaj-firstaj; j++) {
									anString+="'"+caseyear+(firstaj+j)+"',";
								}
							}
						}
					}else{
						//如果只是 输入单个数字
						String[] anjians = anjianhaos[0].split("-");
						if (anjians.length == 1) {
							anString += "'"+caseyear+anjians[0]+"',"; 
						}else{
							int firstaj = Integer.parseInt(anjians[0]);
							int lastaj = Integer.parseInt(anjians[anjians.length-1]);
							for (int j = 0; j <= lastaj-firstaj; j++) {
								anString+="'"+caseyear+(firstaj+j)+"',";
							}
						}
					}
					// 数据 组织完整以后，把最后一个字符串","去掉
                    anString=anString.substring(0, anString.length()-1)+")";
                    whereSql = whereSql.replace("@anjianhao", anString);
                    if(whereSql.contains("@start")&&whereSql.contains("@end")){
                    	anString=anString.replace("(", "").replace(")", "");
                    	String []temp=anString.split(",");
                    	String first=temp[0];
                    	String last= temp[temp.length-1];
                    	first=first.replace(caseyear, "");
                    	last=last.replace(caseyear, "");
                    	whereSql=whereSql.replace("@start", first);
                    	whereSql=whereSql.replace("@end", last);
                    }
				}
				if (plansql.contains("@anjian@")) {
					String anString = "(";
					String[] anjianhaos =  anjianhao.split(",");
                    if (anjianhaos.length >= 2) {
						for (int i = 0; i < anjianhaos.length; i++) {
							String[] anjians = anjianhaos[i].split("-");
							if (anjians.length == 1) {
								anString +=anjians[0]+","; 
							}else{
								int firstaj = Integer.parseInt(anjians[0]);
								int lastaj = Integer.parseInt(anjians[anjians.length-1]);
								for (int j = 0; j <= lastaj-firstaj; j++) {
									anString+=(firstaj+j)+",";
								}
							}
						}
					}else{
						//如果只是 输入单个数字
						String[] anjians = anjianhaos[0].split("-");
						if (anjians.length == 1) {
							anString += anjians[0]+","; 
						}else{
							int firstaj = Integer.parseInt(anjians[0]);
							int lastaj = Integer.parseInt(anjians[anjians.length-1]);
							for (int j = 0; j <= lastaj-firstaj; j++) {
								anString+=(firstaj+j)+",";
							}
						}
					}
					// 数据 组织完整以后，把最后一个字符串","去掉
                    anString=anString.substring(0, anString.length()-1)+")";
                    whereSql = whereSql.replace("@anjian@", anString);
				}
				//案件类型(如：减字,初保字，刑执字等)
				if (plansql.contains("@casetype")) {
					whereSql = whereSql.replace("@casetype", "'"+casetype+"'");
				}
				//案件年
                if(plansql.contains("@caseyear")){
                	whereSql = whereSql.replace("@caseyear", caseyear);
                }
                //案件月
                if(plansql.contains("@casemonth")){
                	whereSql = whereSql.replace("@casemonth", casemonth);
                }
				//会议记录 主键 替换(不用)
				if (plansql.contains("@meetPkey")) {
					whereSql = whereSql.replace("@meetPkey", mkey);
				}
				//根据查询方案 id查询(模板id)
				if (plansql.contains("@tempid")) {
					String tempid = request.getParameter("tempid");
					whereSql = whereSql.replace("@tempid", "'"+tempid+"'");
				}
				//流程草稿ID
				if (plansql.contains("@flowdraftid")) {
					whereSql = whereSql.replace("@flowdraftid", ""+flowdraftid+"");
				}
				//获取当前减刑假释年度、批次(不用)
				if (plansql.contains("@curyear") || plansql.contains("@batch") || plansql.contains("@yearbatch")) {
					//245是查询方案中"查询年度、批次"的方案id，查询出Sql执行返回年度、批次
					String sql = tbsysTemplateMapper.getSqlText("245");
					sql = this.whereSql(user, sql, request);
					HashMap map = systemModelService.getDocumentContent(sql);
					map = (HashMap) MapUtil.turnKeyToLowerCase(map);
					String curyear = map.get("curyear").toString();//年度
					String batch = map.get("batch").toString();//批次
					
					whereSql = plansql.replace("@curyear", "'"+curyear+"'");
					whereSql = plansql.replace("@batch", "'"+batch+"'");
					whereSql = plansql.replace("@yearbatch", "'"+curyear+batch+"'");
				}
				//根据罪犯编号数组 (罪犯编号数组)
				if (whereSql.contains("@choosecrimid")) {
					whereSql=whereSql.replace("@choosecrimid", choosecrimid);
				}
				
				//法院公告
				if (whereSql.contains("@kaitingdate")) {
					whereSql=whereSql.replace("@kaitingdate", kaitingdate);
				}
				if (whereSql.contains("@kaitingend")) {
					whereSql=whereSql.replace("@kaitingend", kaitingend);
				}
				if (whereSql.contains("@zhangtiedate")) {
					whereSql=whereSql.replace("@zhangtiedate", zhangtiedate);
				}
				if (whereSql.contains("@jianyuid")) {
					whereSql=whereSql.replace("@jianyuid", jianyuid);
				}
				
				//流程草稿id 数组
				if (whereSql.contains("@flow_draftids")) {
					whereSql=whereSql.replace("@flow_draftids", flowdraftids);
				}
				
				if(whereSql.contains("@param")){ //任意参数 -- 提供10个 查询顺序  parameter -->attribute -- >session
					for(int i=1; i<=10; i++){
						String temp = "param";
						temp=temp+i;
						String param = request.getParameter(temp);//parameter
						if(StringNumberUtil.isEmpty(param)){//attribute
							Object attobj = request.getAttribute(temp);
							if(attobj!=null){
								param = attobj.toString();
							}
						}
						if(StringNumberUtil.notEmpty(param)){
							String replaceStr = "@"+temp;
							whereSql = whereSql.replaceAll(replaceStr, "'"+param+"'");
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return whereSql;
	}
	/**
	 * 方法描述:如果 字段 别名是 大写biglistyear 大写 biglisttime
	 * 否者查询字段内容 可以as成 任何字段
	 * @author mushuhong
	 * @version 2014年8月22日14:24:56
	 */
	@SuppressWarnings("all")
	public List<Map> getReplaceBigBatch(List<Map> listMaps){
		List<Map> resultList = listMaps;
		List list = new ArrayList();
		try {
			//循环 外层集合 
			for (int i = 0; i < resultList.size(); i++) {
				// 循环 map中的键 值 对
				Map map = resultList.get(i);
				Iterator iterator = map.keySet().iterator();
				while(iterator.hasNext()){
					Object key = iterator.next();
					Object value = map.get(key);
					if ("biglistyear".equals(key.toString().toLowerCase())) {
						value = MapUtil.minZbig(value.toString());
						map.put(key, value);
					}
					if ("biglisttime".equals(key.toString().toLowerCase())) {
						value = MapUtil.minZbig(value.toString());
						map.put(key, value);
					}
				}
				// 把替换过的从新组合的 map集合 ，添加到list集合中
				list.add(map);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public List<Map> getAllReportResources(HttpServletRequest request,SystemUser user) {
		List<Map> listMaps = new ArrayList<Map>();
		try {
			String userid = user.getUserid();
			listMaps = resourceTypeMapper.getAllReportResourcesMapper(userid);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return listMaps;
	}
	
	@Override
	public List<Map> getNextApproveUserInfoOfFlow(SystemUser user, Map map){
		//-------------------------------------------
		if (null == user) {
			return null;
		}
		// 要返回的结果
		List<Map> result = null;
		//
		List<Map> resources = user.getNextApprovePerson();
		Date refreshApproveTime = user.getRefreshApproveTime();
		// 有资源缓存,则直接返回
		// 最简单的缓存实现,不分层处理
		if (null != resources && false == resources.isEmpty()) {
			//
			if (null != refreshApproveTime) {
				long lastTimeMillis = refreshApproveTime.getTime();
				long currentTimeMillis = System.currentTimeMillis();
				// 尚未过期
				if (currentTimeMillis - lastTimeMillis < REFRESH_RES_TIME_MILLIS) {
					//System.err.println(LogUtil.currentMethodName() + " 利用缓存");
					result =  resources;
				} 
			}
		} 
		// 如果 result 为 null, 从数据库获取
		if(null == result){
			
			List<Map> list1 = MapUtil.turnKeyToLowerCaseOfList(systemResourceMapper.getResourcesByMap(map));
			if(null==list1||list1.size()<=0){
				return null;
			}
			String nodeids = ""; //dnodeid
			for(Map temMap : list1){
				String dnodeid = temMap.get("dnodeid").toString();
				if(StringNumberUtil.notEmpty(dnodeid)){
					nodeids += "'"+dnodeid+"',";
				}
			}
			nodeids = StringNumberUtil.removeLastStr(nodeids, ",");
			map.put("nodeids", nodeids);
			
			resources = MapUtil.turnKeyToLowerCaseOfList(systemResourceMapper.getUserInfoByMap(map));
			
			
			if (null != resources) {
				// 缓存到用户
				user.setNextApprovePerson(resources);
				// 刷新时间
				user.setRefreshApproveTime(new Date());
			}
			result = resources;
		}
		// 遍历克隆对象; 返回的接口是 ArrayList
		if(null != result){
			List<Map> clonedList = new ArrayList<Map>();
			for(Map temMap:result){
				Map resClone = new HashMap();
				if(null==temMap){
					continue;
				}
				resClone.put("userid", temMap.get("userid"));
				resClone.put("name", temMap.get("name"));
				clonedList.add(resClone);
			}
			
//			Iterator<Map> iteratorResult = result.iterator();
//			while (iteratorResult.hasNext()) {
//				Map res = iteratorResult.next();
//				if(null == res){
//					continue;
//				}
//				try {
//					Map resClone = new HashMap();
//					resClone.put("userid", value);
//					clonedList.add(resClone);
//				} catch (CloneNotSupportedException e) {
//					e.printStackTrace();
//				}
//			}
			// 缓存后,每次使用,都是克隆后的数据
			result = clonedList;
		}
		
		//
		return result;
	}
    
	@Override
	public List<Map> getResouceType(Map map) {
		return resourcetypemapper.getResouceType(map);
	}
	/*@Override
	 public List<ResourceType> getResouceType(Map map) {
		return resourcetypemapper.getResouceType(map);
	}*/


	@Override
	public int getCount(Map map) {
		return resourcetypemapper.getCount(map);
	}
	public Object insertResouceManage(HttpServletRequest request)
	{
		ResourceType record=new ResourceType();
		String data = request.getParameter("data");
		Object obj = null;
		List<Map> list = (List) JsonUtil.Decode(data);
		if(list!=null && list.size()>0){
			Map map = list.get(0);
			record.setRestypeid(Integer.parseInt((String)map.get("restypeid")));
			record.setName((String)map.get("name"));
			obj = resourcetypemapper.insert(record);
		}
		return obj;
	}
	
	public Object updateResouceManage(HttpServletRequest request)
	{
		ResourceType record= new ResourceType();
		String data = request.getParameter("data");
		Object obj = null;
		List<Map> list = (List) JsonUtil.Decode(data);
		if(list!=null && list.size()>0){
			Map map = list.get(0);
			record.setRestypeid(Integer.parseInt((String)map.get("restypeid")));
			record.setName((String)map.get("name"));
			obj = resourcetypemapper.updateByPrimaryKeySelective(record);
		}
		return obj;
	}
	public Object deleteResouceManage(HttpServletRequest request)
	{
		String restypeid= request.getParameter("restypeid");
		if(StringNumberUtil.notEmpty(restypeid)){
			resourcetypemapper.deleteByPrimaryKey(restypeid);
			
		
	     }
		return null;
	}

	/***
	 * 方法描述：监区减刑假释呈报榜
	 */
	@SuppressWarnings("all")
	public Object jqCaseTypeChengBaoBangPage(HttpServletRequest request,SystemUser user) {
		String departid = user.getDepartid();//单位id
		String orgid = user.getOrgid();//部门id
		String caseNumber = request.getParameter("casenumber");//案件号
		String pageIndex = request.getParameter("pageIndex")==null?"0":request.getParameter("pageIndex");//页码
		String pageSize = request.getParameter("pageSize")==null?"20":request.getParameter("pageSize");//每页显示多少条
		String menuid = request.getParameter("menuid");//菜单id
		Map objMap = new HashMap();
		try {
			int pageindex = Integer.parseInt(pageIndex);
			int pagesize = Integer.parseInt(pageSize);
			int start = pageindex;
			int end = (pageindex+1) * 20;
			Map map = new HashMap();//封装参数
			map.put("departid", departid);
			map.put("orgid", orgid);
			map.put("casenumber", caseNumber);
			map.put("start", start);
			map.put("end", end);
			int reportortemp = 1;
			
			int counts = 0;//总条数
			List<Map> mapList1 = new ArrayList<Map>();//数据data
			List<Map> listMaps = resourcetypemapper.listMapsTemplateByMenuid(menuid,reportortemp);
			if (listMaps != null) {
				for (Map map2 : listMaps) {
					Clob clob = (Clob)(map2.get("SQLTEXT")==null?"":map2.get("SQLTEXT"));
					String planSql = clob.getSubString(1, (int)clob.length());
					planSql = this.wherePlanSql(planSql,map);
					//根据 表中的排序，第一条sql查询是分页数据，第二条sql
					String count = map2.get("INT1")==null?"":map2.get("INT1").toString();
					//count返回值是 int类型
					if ("count".equals(count)) {
						counts = resourcetypemapper.jqCaseTypeChengBaoBangPageCount(planSql);
					}else{
						mapList1 = resourcetypemapper.jqCaseTypeChengBaoBangPageData(planSql);
					}
				}
			}
			objMap.put("total", counts);
			objMap.put("data", MapUtil.turnKeyToLowerCaseOfList(mapList1));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return objMap;
	}
    /**
     * 方法描述：参数替换
     * @author :mushuhong
     * @version ：2014年12月24日11:15:04
     */
	public String wherePlanSql(String whereSql,Map map){
		
		String planSql = whereSql;
		//部门编号
		if (planSql.contains("@orgid")) {
			planSql = planSql.replace("@orgid", map.get("orgid")==null?"":map.get("orgid").toString());
		}
		//单位编号
		if (planSql.contains("@departid")) {
			planSql = planSql.replace("@departid", map.get("departid")==null?"":map.get("departid").toString());
		}
		//当前页
		if (planSql.contains("@start")) {
			planSql = planSql.replace("@start", map.get("start")==null?"0":map.get("start").toString());
		}
		//每页显示多少条数据
		if (planSql.contains("@end")) {
			planSql = planSql.replace("@end", map.get("end")==null?"20":map.get("end").toString());
		}
		//罪犯编号 集合
		if (planSql.contains("@crimids")) {
			planSql = planSql.replace("@crimids", map.get("end")==null?"":map.get("crimids").toString());
		}
		//流程自定义id= flowdefid
		if (planSql.contains("@flowdefid")) {
			planSql = planSql.replace("@flowdefid", map.get("flowdefid")==null?"":map.get("flowdefid").toString());
		}
		//流程草稿id=flowdraftids
		if (planSql.contains("@flowdraftid")) {
			planSql = planSql.replace("@flowdraftid", map.get("flowdraftid")==null?"20":map.get("flowdraftid").toString());
		}
		return planSql;
	}
	@SuppressWarnings("all")
	public void getCaoZuoResource(HttpServletRequest request, SystemUser user) {
		//select * from TBFLOW_DELIVER  where flowdefid=#{flowdefid} and resid=#{resid} and departid=#{departid} 
		try {
			String userid = user.getUserid();//用户id
			String flowdefid = "other_jxjscpb";//流程自定义id
			String departid = user.getDepartid();//单位id
			Map map = new HashMap();
			map.put("userid", userid);
			map.put("flowdefid", flowdefid);
			map.put("departid", departid);
			map = resourcetypemapper.getCaoZuoResource(map);
			request.setAttribute("text1", map.get("TEXT1"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 方法描述：根据资源id查询出对应的列表方案
	 * @author mushuhong 
	 * @version 2014年12月24日08:36:20
	 */
	public void jqCaseTypeChengBaobiao(HttpServletRequest request,SystemUser user) {
		String menuid = request.getParameter("menuid");
		Map map = resourcetypemapper.jqCaseTypeChengBaobiao(menuid);
		
		String planSql = "";
		//对sql进行处理
		try {
			if (map != null) {
				Clob clob = (Clob)(map.get("SQLTEXT")==null?"":map.get("SQLTEXT"));
				planSql = clob.getSubString(1, (int)clob.length());
				map.clear();
				map = resourcetypemapper.jqCaseTypeChengBaobiaoSql(planSql);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		request.setAttribute("menuid",menuid);
		request.setAttribute("data", MapUtil.turnKeyToLowerCase(map));
	}

	/**
	 * 方法描述：导出xls表格
	 * @zuthor:mushuhong
	 * @version :2014年12月24日13:33:09
	 */
	@SuppressWarnings("all")
	public List<Map> exportXlsService(HttpServletRequest request, SystemUser user) {
		String crimids = request.getParameter("crimids");//罪犯编号
		String flowdefid = request.getParameter("flowdefid");//流程自定义id
		String flowdraftids = request.getParameter("flowdraftids");//流程草稿id
		String menuid = request.getParameter("menuid");//菜单id
		
		int reportortemp = 0;//sql状态
		List<Map> listMap = resourcetypemapper.listMapsTemplateByMenuid(menuid, reportortemp);
		List<Map> mapList = new ArrayList<Map>();
		Map map2 = new HashMap();
		try {
			map2.put("crimids", crimids);
			map2.put("flowdefid", flowdefid);
			map2.put("flowdraftid", flowdraftids);
			map2.put("orgid", user.getOrgid());
			if (listMap != null) {
				for (Map map : listMap) {
					if (map.get("SQLTEXT") != null) {
						Clob clob = (Clob)(map.get("SQLTEXT")==null?"":map.get("SQLTEXT"));
						String planSql = clob.getSubString(1, (int)clob.length());
						planSql = this.wherePlanSql(planSql, map2);
						map2.clear();//参数重复使用
						if (!"".equals(planSql)) {
							mapList = resourcetypemapper.exportXlsServiceMapper(planSql);
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mapList;
	}
	
	@Override
	@Transactional
	public int copyTo(String fromresid, String toresid, int onlysub,
			SystemUser operator) {
		if (null == operator) {
			return -1;
		}
		if(StringNumberUtil.isEmpty(fromresid) || StringNumberUtil.isEmpty(toresid) || fromresid.equals(toresid) ){
			return -1;
		}
		//
		SystemResource fromRes = systemResourceMapper.getByResourceId(fromresid);
		SystemResource toRes = systemResourceMapper.getByResourceId(toresid);
		// 非空才能拷贝
		if(null == fromRes || null == toRes){
			return -1;
		}
		// 获取需要拷贝的资源List
		List<SystemResource> fromList = systemResourceMapper.listAllByPid(fromresid);
		//
		int result = copyResource(fromList, fromRes, toRes, onlysub, operator);
		//
		return result;
	}
	

	// 拷贝资源
	private int copyResource(List<SystemResource> fromList,
			SystemResource fromRes, SystemResource toRes, int onlysub, SystemUser operator) {
		//
		if(1== onlysub && (null == fromList || fromList.isEmpty())){
			return 0;
		}
		//
		int n = 0;
		//
		List<SystemResource> resultList = setIdShip(fromList, fromRes, toRes, onlysub, operator);
		
		Iterator<SystemResource> iteratorR = resultList.iterator();
		while (iteratorR.hasNext()) {
			SystemResource systemResource = (SystemResource) iteratorR.next();
			// 如果报错,则根据外层事物控制, 执行自动回滚
			int i = systemResourceMapper.insertSelective(systemResource);
			n += i;
		}
		//
		return n;
	}
	
	// 递归设置所有需要拷贝的资源信息
	private List<SystemResource> setIdShip(List<SystemResource> fromList,
			SystemResource fromRes, SystemResource toRes, int onlysub, SystemUser operator){
		List<SystemResource> resultList = new ArrayList<SystemResource>();
		//
		SystemResource newParent = toRes;
		if(onlysub < 1){
			// 如果 fromRes 也参与拷贝
			// 拷贝并替换新的根节点
			SystemResource newFromRes = cloneNew(fromRes, toRes, operator);
			
			//
			resultList.add(newFromRes);
			newParent = newFromRes;
		}
		// 找子元素
		List<SystemResource> subList = findDirectSubRes(fromList, fromRes);

		Iterator<SystemResource> iteratorR = subList.iterator();
		while (iteratorR.hasNext()) {
			SystemResource oldsub = (SystemResource) iteratorR.next();
			//
			SystemResource newsub = cloneNew(oldsub, newParent, operator);
			resultList.add(newsub);
			// 迭代. 只能使用递归了
			//
			int onlysubN = 1;
			List<SystemResource> subSubList = setIdShip(fromList, oldsub, newsub, onlysubN, operator);
			//
			resultList.addAll(subSubList);
		}
		//
		return resultList ;
	}
	
	//
	private List<SystemResource> findDirectSubRes(List<SystemResource> fromList, SystemResource parentRes){
		List<SystemResource> subList = new ArrayList<SystemResource>();
		//
		if(null == fromList || null == parentRes){
			return subList;
		}
		//
		String parentId = parentRes.getResid();
		if(null == parentId){
			return subList;
		}
		//
		Iterator<SystemResource> iteratorR = fromList.iterator();
		while (iteratorR.hasNext()) {
			SystemResource res = (SystemResource) iteratorR.next();
			String presid = res.getPresid();
			if(parentId.equals(presid)){
				subList.add(res);
			}
		}
		//
		return subList;
	}
	
	
	//
	private SystemResource cloneNew(SystemResource oldRes, SystemResource newParent, SystemUser operator){
		SystemResource newRes = null;
		try {
			newRes = oldRes.clone();
			int random = new Random().nextInt();
			//
			int nextId = systemResourceMapper.getNextIdNoCache(random);
			//
			newRes.setResid(""+nextId);
			newRes.setPresid(newParent.getResid());
			newRes.setOptime(null);
			//
			if(null != operator){
				newRes.setOpid(operator.getUserid());
			}
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		
		//
		return newRes;
	}
	
	@Override
	public List<SystemResource> getResourcesByConditions(Map map){
		return systemResourceMapper.getResourcesByConditions(map);
	}
	
	@Override
	public Object returnButtonResourceByUser(SystemUser user,String flowdefid){
		
		//用户对象
		FlowDeliver flowDeliver = new FlowDeliver();
		
		//资源对象
		Object buttonstr = "1!=1";
		StringBuffer flowdefidsbf = new StringBuffer();
		Map<String,FlowDeliver> map = new HashMap<String,FlowDeliver>();
		
		//获取流程配置信息
		Map<String,String> paraMap = new HashMap<String,String>();
		paraMap.put("id", flowdefid);
		paraMap.put("departid", user.getDepartid());
		List<FlowDeliver> deliverlist = flowDeliverMapper.findByflowdefid(paraMap);
		for(FlowDeliver deliver:deliverlist){
			map.put(deliver.getResid(), deliver);
		}
		//获取资源
		List<SystemResource> reslist = getAllResourceByUser(user);
		if(reslist!=null){
			for(SystemResource res:reslist){
				Integer ismenu = res.getIsmenu();
				if(map.containsKey(res.getResid())){
					flowDeliver = map.get(res.getResid());
					//拥有的所有可审批的流程
					String flowdefidval = "(flowdefid = '"+flowDeliver.getFlowdefid()+"' and nodeid ='"+flowDeliver.getSnodeid()+"') or ";
					if(flowdefidsbf.indexOf(flowdefidval)== -1 ){
						flowdefidsbf.append(flowdefidval);
					}
				}
			}
		}
		if(flowdefidsbf!=null && flowdefidsbf.length()>0){
			buttonstr = flowdefidsbf.substring(0, flowdefidsbf.length()-3);
		}
		
		return buttonstr;
		
		
	}
	
	@Override
	public String returnUserResourceByCondition(SystemUser user,String flowdefid){
		
		String buttonstr = "";
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("userid", user.getUserid());
		map.put("flowdefid", flowdefid);
		map.put("departid", user.getDepartid());
		buttonstr = flowDeliverMapper.returnUserResourceByCondition(map);
		return buttonstr;
		
	}

	/**
	 * 方法描述：根据查询方案查询数据返回结果集
	 * @author 
	 * @version 2015年4月29日09:40:06
	 */
	@SuppressWarnings("all")
	public Map queryQualificationData(SystemUser user,HttpServletRequest request){
		Map listMap = new HashMap();
		String resid = request.getParameter("menuid");
		//通过资源编号，得到对应查询方案 对应所有的sql语句
		List<Map> listPlanSql = resourceTypeMapper.getPlanSqlByResidNew(resid);
		
		List<List<Map>> listMapData = new ArrayList<List<Map>>();
		//循环sql
		if (listPlanSql.size() > 0) {
			for (int i = 0; i < listPlanSql.size(); i++) {
				Map mapSql = listPlanSql.get(i);
				//得到 单条 sql语句
				Clob clob = (Clob) (mapSql.get("SQLTEXT")== null?"":mapSql.get("SQLTEXT"));
				String planSql = "";
				try {
					planSql = clob.getSubString(1,(int)clob.length());
					if (!"".equals(planSql)) {
						//得到 单条sql语句的结果
						HashMap mapsql = new HashMap();
						planSql = this.whereSql(user, planSql,request);
						mapSql.put("sql", planSql);
						List<Map> listData = resourceTypeMapper.getReportDataByPlanSql(mapSql);
						//把key和value值都对应为 小写的字母
						List<Map> lowerListData = MapUtil.turnKeyToLowerCaseOfList(listData);
						if(lowerListData!=null){
							for(Map map:lowerListData){
								if(map!=null) listMap.putAll(map);
							}
						}
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		//
		return listMap;
	}

	@Override
	public Map<String,String> ajaxSearchResourceByFlowdefid(String flowdefid,String resid, SystemUser user) {
		Map map = new HashMap<String,String>();
		String userid = user.getUserid();
		List<Map> reslist = systemResourceMapper.getResourcesByFlowdefid(flowdefid,resid,userid);
		if(reslist.size() > 0){
			map = reslist.get(0);
		}
		return map;
	}

	@Override
	public List<SystemResource> selectMenubyrestypeid(String string,
			int flowbutton) {
		return systemResourceMapper.selectMenubyrestypeid(string, flowbutton);
		}

	@SuppressWarnings("all")
	public List<Map> lockSignNode(SystemUser user,String flowdefid) {
		List<Map> listMaps = new ArrayList<Map>();
		listMaps = systemResourceMapper.lockSignNode(user.getUserid(),flowdefid,user.getDepartid());
		return listMaps;
	}
	
}
