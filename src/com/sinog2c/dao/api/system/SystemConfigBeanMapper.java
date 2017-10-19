package com.sinog2c.dao.api.system;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.sinog2c.model.system.SystemConfigBean;
import com.sinog2c.model.system.SystemUser;

/**
 * DAO层接口.
 */
// 必须指定 @Repository 注解
// 具体实现在 config/com.sinog2c.dao.oracle.xxx 等包中,XML文件
// <mapper namespace="本接口完全类名" ...
// interface中的所有方法声明,编译器强制转换为 public abstract 的
@Repository
public interface SystemConfigBeanMapper {
	
    int insert(SystemConfigBean config);

    int insertSelective(SystemConfigBean config);
    
    int insertByMap(Map<String, Object> paraMap);

    int deleteById(int id);

    int update(SystemConfigBean config);

    int updateSelective(SystemConfigBean config);
    
    int updateByMap(Map<String, Object> paraMap);
    
    SystemConfigBean getById(int id);
    
    /**
     * 查看数量
     */
    int countAll();

    List<SystemConfigBean>  listAll();
    /**
     * 所有数据分页
     */
    List<SystemConfigBean>  listAllByPage(
			@Param("start")
    		int start, 
			@Param("end")
    		int end,
			@Param("sortField")
    		String sortField,
			@Param("sortOrder")
    		String sortOrder
    		);
    /**
     * 根据关键词,计算数量
     * @param key 关键字,如果没有,请填入 ""
     */
    int countBySearch(
			@Param("key")
    		String key);
    /**
     * 搜索,分页
     */
    List<SystemConfigBean>  search(
			@Param("key")
    		String key,
			@Param("start")
    		int start, 
			@Param("end")
    		int end,
			@Param("sortField")
    		String sortField,
			@Param("sortOrder")
    		String sortOrder);
    
    /**
     * 根据条件查询总数
     * @param paramMap
     * @return
     */
    public int countByCondition(Map<String, Object> paramMap);
    /**
     * 根据条件查询列表
     * @param paramMap
     * @return
     */
    public List<SystemConfigBean> listByCondition(Map<String, Object> paramMap);
    
    public SystemConfigBean getConfigByMap(Map<String,Object> map);
}