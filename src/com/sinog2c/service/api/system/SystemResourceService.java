package com.sinog2c.service.api.system;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.sinog2c.model.system.ResourceType;
import com.sinog2c.model.system.RoleResourceWrapper;
import com.sinog2c.model.system.SystemResource;
import com.sinog2c.model.system.SystemUser;
import com.sinog2c.util.report.RMEngine;

public interface SystemResourceService {

	/**
	 * 使用sequence自增ID方式新增
	 * @param res 资源对象
	 * @param operator 当前用户
	 * @return
	 */
	public int add(SystemResource res, SystemUser operator);
	/**
	 * 新增系统资源,不使用序列自增机制,需要指定好ID号
	 * @param res 资源对象
	 * @param operator 当前用户
	 * @return
	 */
	public int insertWithId(SystemResource res, SystemUser operator);
	/**
	 * 根据map进行更新
	 * @param map
	 * @param operator
	 * @return
	 */
	public int insertByMap(Map<String, Object> map, SystemUser operator);
	
	/**
	 * 方法描述：根据查询方案对应的资源ID返回对应的map数据集合
	 */
	public Map queryQualificationData(SystemUser user,HttpServletRequest request);

	/**
	 * 修改资源信息
	 * @param res 资源对象
	 * @param operator 当前用户
	 * @return
	 */
	public int update(SystemResource res, SystemUser operator);
	
	
	
	/**
	 * 由hashmap更新
	 * @param res
	 * @param operator
	 * @return
	 */
	public int updateByMap(Map<String, Object> map, SystemUser operator);

	/**
	 * 更新用户的权限
	 * @param roleid
	 * @param resources
	 * @return 执行是否成功
	 */
	public boolean updateByRole(String roleid, List<RoleResourceWrapper> newresources);
	/**
	 * 删除资源,逻辑上进行删除
	 * @param resName
	 * @return
	 */
	public int delete(String resID, SystemUser operator);
	

    /**
     * 获取自增长的 resid
     * @return
     */
    public int getNextId();

	/**
	 * 根据ID取得
	 * @param resourceId
	 * @return
	 */
	public SystemResource getByResourceId(String resourceId);

	/**
	 * 取得所有未删除的资源数量
	 * @return
	 */
	public int countAll();
	/**
	 * 取得所有未删除的资源列表
	 * @return
	 */
	public List<SystemResource> selectAll();

	/**
	 * 根据页面取得数据
	 * @param pageIndex 页码,0开始
	 * @param pageSize 每页数量
	 * @return
	 */
    public List<SystemResource> getAllByPage(int pageIndex,	int pageSize);

    /**
     * 获取所有菜单
     * @return
     */
    //public List<SystemResource> getAllMenu();
    
    /**
     * 根据用户权限,获取菜单
     * @param user
     * @return
     */
    public List<SystemResource> getMenuByUser(SystemUser user);
    /**
     * 根据用户权限,获取所有资源
     * @param user
     * @return
     */
    public List<SystemResource> getAllResourceByUser(SystemUser user);
    /**
     * 根据用户权限,获取单个资源
     * @param user
     * @param menuid
     * @return
     */
    public SystemResource getResourceByUserAndId(SystemUser user, String menuid);
	/**
	 * 根据 pid与用户取得直接子元素资源列表
	 * @param user 用户
	 * @param menuid
	 * @return
	 */
	public List<SystemResource> listByMenuid(SystemUser user, String menuid);
	/**
	 * 根据用户获取子资源类型ID
	 * @param user
	 * @param menuid
	 * @param restypeid
	 * @return
	 */
	public List<SystemResource> listByMenuid(SystemUser user, String menuid, int restypeid);
	/**
	 * 获取菜单内的顶部按钮
	 * @param user
	 * @param menuid
	 * @return
	 */
	public List<SystemResource> getButtonTop(SystemUser user, String menuid);
	/**
	 * 获取菜单内的列中按钮
	 * @param user
	 * @param menuid
	 * @return
	 */
	public List<SystemResource> getButtonInline(SystemUser user, String menuid);
	/**
	 * 为了限制上层调用,避免误用,如果需要获取所有,无关用户和权限,则使用强制
	 * @param menuid
	 * @param user
	 * @param forceNoUser 如果不想涉及授权,请使用 true
	 * @return
	 */
	public List<SystemResource> listByMenuid(SystemUser user, String menuid, boolean forceNoUser);

	/**
	 * 根据角色获取权限列表
	 * @param roleid
	 * @return
	 */
	public List<SystemResource> listByRoleid(String roleid);
	
	/**
	 * 列出所有ResourceType
	 * @return
	 */
	public List<ResourceType> listAllResourceType();
	/**
	 * 根据pid递归获取
	 * @param pid
	 * @return
	 */
	public List<SystemResource> listAllByPid(String pid);
	/**
	 * 方法描述：查询资格榜单的内容 显示在报表中
	 * @author mushuhong
	 * @version 2014年8月20日09:40:06
	 */
	public RMEngine queryQualificationDataRmEngine(String rid, SystemUser condition,HttpServletRequest request);
	
	public RMEngine getReportEngin(Map<String,Object> paramMap, SystemUser user, Map<String,Object> resultMap);
	
	/**
	 * 方法描述：根据 用户名 查询出各种条件
	 * @author mushuhong
	 * @version 2014年8月21日13:55:17
	 * @param 当前登录用户
	 * @param request
	 */
	public String whereSql(SystemUser user,String plansql,HttpServletRequest request);
	/**
	 * 方法描述：查询出所有的报表资源
	 * @author mushuhong
	 * @version 2014年9月17日11:00:29
	 */
	public List<Map> getAllReportResources(HttpServletRequest request,SystemUser user);
	
	public List<Map> getNextApproveUserInfoOfFlow(SystemUser user,Map map);
	
	public List<Map>  getResouceType(Map map);
	
	public int getCount(Map map);
	
	public Object insertResouceManage(HttpServletRequest request);
	public void getCaoZuoResource(HttpServletRequest request,SystemUser user);
	
	public Object updateResouceManage(HttpServletRequest request);
	
	public Object deleteResouceManage(HttpServletRequest request);
	
	public Map<String, String> ajaxSearchResourceByFlowdefid(String flowdraftid,String resid,SystemUser User);
	
	public Object jqCaseTypeChengBaoBangPage(HttpServletRequest request,SystemUser user);
	
	public void jqCaseTypeChengBaobiao(HttpServletRequest request,SystemUser user);
	
	public List<Map> exportXlsService(HttpServletRequest request,SystemUser user);
	/**
	 * 拷贝资源
	 * @param fromresid
	 * @param toresid
	 * @param onlysub
	 * @param operator
	 * @return
	 */
	public int copyTo(String fromresid, String toresid, int onlysub,
			SystemUser operator);
	
	public List<SystemResource> getResourcesByConditions(Map map);
	
	public String getReportNameByMenuid(String menuid);
	
	
	public Object returnButtonResourceByUser(SystemUser user,String flowdefid);
	
	public String returnUserResourceByCondition(SystemUser user,String flowdefid);
	
	RMEngine getRmEngineByRid(List<List<Map>> dataList,List<List<Map>> listType,HttpServletRequest request);
	
	public List<SystemResource> selectMenubyrestypeid(String string,int flowbutton);
	
	public List<Map> lockSignNode(SystemUser user,String flowdefid);
}