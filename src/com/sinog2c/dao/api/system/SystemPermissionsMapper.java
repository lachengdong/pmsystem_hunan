package com.sinog2c.dao.api.system;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.sinog2c.model.system.SystemPermissions;

public interface SystemPermissionsMapper {
    int insert(SystemPermissions record);

    int insertSelective(SystemPermissions record);
    
	public int insertByMap(Map<String, Object> map);
    
    public int delete(SystemPermissions org);

    public int update(SystemPermissions org);

	public int updateByMap(Map<String, Object> map);

    /**
     * 所有数据的条数
     * @return
     */
    int countAll();
    /**
     * 获取自增ID
     * @return
     */
    int getNextId();
    /**
     * 查询所有
     * @return
     */
    public List<SystemPermissions> selectAll();
    
    // 可能后期还需要修改
    // 按页面显示
    public List<SystemPermissions> getAllByPage(
			@Param("start")
    		int start, 
			@Param("end")
    		int end);
    /**
     * 获取单个信息
     * @param spid
     * @return
     */
    public SystemPermissions getBySpid(
			@Param("spid")
    		String spid
    		);
}