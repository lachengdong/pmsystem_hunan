package com.sinog2c.service.api.system;

import java.util.List;
import java.util.Map;

import com.sinog2c.model.system.SystemConfigBean;
import com.sinog2c.model.system.SystemUser;

/**
 * 系统配置服务接口
 */
public interface SystemConfigService {

	/**
	 * 使用sequence自增ID方式新增
	 * @param bean 新增的对象
	 * @param operator 当前用户
	 * @return
	 */
	public int add(SystemConfigBean bean, SystemUser operator);
	/**
	 * 更新
	 * @param bean
	 * @param operator
	 * @return
	 */
	public int update(SystemConfigBean bean, SystemUser operator);
	/**
	 * 根据Map更新
	 * @param paraMap
	 * @param operator
	 * @return
	 */
	public int updateByMap(Map<String, Object> paraMap, SystemUser operator);
	/**
	 * 删除
	 * @param bean
	 * @param operator
	 * @return
	 */
	public int delete(SystemConfigBean bean, SystemUser operator);
    /**
     * 根据ID获取
     * @param id
     * @return
     */
	public SystemConfigBean getById(int id);
	/**
	 * 获取所有
	 * @return
	 */
	public int countAll();
	
	/**
	 * 获取所有
	 * @return
	 */
	public List<SystemConfigBean>  listAll();

	/**
	 * 获取所有,分页
	 * @param pageIndex
	 * @param pageSize
	 * @param sortField
	 * @param sortOrder
	 * @return
	 */
    public List<SystemConfigBean> listAllByPage(int pageIndex, int pageSize, String sortField, String sortOrder);

    /**
     * 搜索,获取数量
     * @param key
     * @return
     */
    public int countBySearch(String key);
    /**
     * 搜索, 分页
     * @param key
     * @param pageIndex
     * @param pageSize
     * @param sortField
     * @param sortOrder
     * @return
     */
    public List<SystemConfigBean> search(String key, int pageIndex, int pageSize, String sortField, String sortOrder);
    /**
     * 根据条件查询总个数
     * @param paramMap
     * @param user
     * @return
     */
    public int countByCondition(Map<String, Object> paramMap, SystemUser user);
    /**
     * 根据条件查询参数列表
     * @param paramMap
     * @param user
     * @return
     */
    public List<SystemConfigBean> listByCondition(Map<String, Object> paramMap, SystemUser user);
    
    /**
     * 查询监狱配置参数列表
     * @param user
     * @return
     */
    public List<SystemConfigBean> listJYConfig(SystemUser user);
    /**
     * 查询单个监狱配置参数
     * @param name
     * @param user
     * @return
     */
    public SystemConfigBean getJYConfig(String name, SystemUser user);
    
    /**
     * 查询单个监狱配置参数
     * @param name
     * @param user
     * @return
     */
    public SystemConfigBean getConfigByMap(Map<String,Object> map);

    /**
     * 查询系统配置参数列表
     * @param user
     * @return
     */
    public List<SystemConfigBean> listSYSConfig();
    /**
     * 查询单个系统配置参数
     * @param name
     * @param user
     * @return
     */
    public SystemConfigBean getSYSConfig(String name);
}
