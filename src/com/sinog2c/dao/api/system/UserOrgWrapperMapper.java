package com.sinog2c.dao.api.system;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.sinog2c.model.system.UserOrgWrapper;

public interface UserOrgWrapperMapper {
    int insert(UserOrgWrapper record);

    int insertSelective(UserOrgWrapper record);
    
    int delete(UserOrgWrapper record);
    
    /**
     * 获取用户的部门信息
     * @param userid
     * @return
     */
    public List<UserOrgWrapper> getAllByUserid(String userid);
    /**
     * 根据 orgid 获取,用户及子单位用户
     * @param orgid
     * @return
     */
    public List<UserOrgWrapper> getAllByOrgid(String orgid);
    /**
     * 根据 orgids 获取
     * @param orgids 参数形式: 【'1','2','65'】
     * @return
     */
    public List<UserOrgWrapper> getAllByOrgids(String orgids);
    /**
     * 获取所有
     * @return
     */
    public List<UserOrgWrapper> getAll();
    //
	public int countAll();
    //
    public List<UserOrgWrapper> getAllByPage(
			@Param("start")
    		int start, 
			@Param("end")
    		int end);    
    public List<UserOrgWrapper> getIdsByDepartid(String departid);
    
    public int deleterole(UserOrgWrapper record);
    
    public List<Map<String,Object>> getUserByMap(Map map);
    
}