package com.sinog2c.dao.api.system;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.sinog2c.model.system.RoleResourceWrapper;

public interface RoleResourceWrapperMapper {
    int insert(RoleResourceWrapper record);

    int insertSelective(RoleResourceWrapper record);
    
    int delete(RoleResourceWrapper record);
    
    public List<RoleResourceWrapper> getAllByRoleid(String srid);

    //
	public int countAll();
    //
    public List<RoleResourceWrapper> getAllByPage(
			@Param("start")
    		int start, 
			@Param("end")
    		int end);
}