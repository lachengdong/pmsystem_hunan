package com.sinog2c.dao.api.system;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.sinog2c.model.system.UserRoleWrapper;

@Repository
public interface UserRoleWrapperMapper {
    int insert(UserRoleWrapper record);

    int insertSelective(UserRoleWrapper record);
    
    int delete(UserRoleWrapper record);
    
    public List<UserRoleWrapper> getAllByUserid(String userid);
    
    public List<UserRoleWrapper> getAllByUserids(Map<String,String> map);

    //
	public int countAll();
    //
    public List<UserRoleWrapper> getAllByPage(
			@Param("start")
    		int start, 
			@Param("end")
    		int end);
    
}