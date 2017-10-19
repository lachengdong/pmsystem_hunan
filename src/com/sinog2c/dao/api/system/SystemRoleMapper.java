package com.sinog2c.dao.api.system;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.sinog2c.model.system.SystemRole;

public interface SystemRoleMapper {
    int insert(SystemRole record);

    int insertSelective(SystemRole record);
    
	public int insertByMap(Map<String, Object> map);
    //
    public int insertToHistory(SystemRole role);
    
    public int delete(SystemRole role);

    public int delres(SystemRole role);
    public int update(SystemRole role);

	public int updateByMap(Map<String, Object> map);

    //
    int countAll(Map<String, Object> map);
    /**
     * 查询所有
     * @return
     */
    public List<SystemRole> selectAll();
    
    public List<SystemRole> selectAllByDepartid(Map<String, Object> map);
    
    // 可能后期还需要修改
    // 按页面显示
    public List<SystemRole> getAllByPage(Map<String, Object> map);
    //
    public SystemRole getByRoleId(
			@Param("roleid")
    		String roleid
    		);
}