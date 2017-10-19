package com.sinog2c.dao.api.system;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.sinog2c.model.system.RolePermissionsWrapper;

@Repository
public interface RolePermissionsWrapperMapper {
	
    int insert(RolePermissionsWrapper record);

    int insertSelective(RolePermissionsWrapper record);
    
    int delete(RolePermissionsWrapper record);
    
    public List<RolePermissionsWrapper> getAllByRoleid(String srid);

    //
	public int countAll();
    //
    public List<RolePermissionsWrapper> getAllByPage(
			@Param("start")
    		int start, 
			@Param("end")
    		int end);
}