package com.sinog2c.dao.api.system;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.sinog2c.model.system.SystemUser;

@Repository("systemUserMapper")
public interface SystemUserMapper {
	public int insert(SystemUser user);

	public int insertSelective(SystemUser user);
	// 根据ID拷贝到历史
	public int copyToHistory(SystemUser user);

	public int updateSelfInfo(SystemUser user);

	public int updateUserInfo(SystemUser user);
	// 只负责删除
	public int deleteOnly(SystemUser user);

	public SystemUser getByUserId(String userId);
	
	public List<Map<String,Object>> getApprovePersons(Map<String,Object> map);
	//
	public int countAll();
	
	public List<SystemUser> selectAll();

	public List<SystemUser> getAllByPage(@Param("start") int start, @Param("end") int end);

	public int countBySearch(@Param("key") String key);

	public List<SystemUser> searchByPage(@Param("key") String key, @Param("start") int start, @Param("end") int end);

	
	public int countByOrgid(@Param("orgid") String orgid);
	/**
	 * 根据 orgid 获取 直接用户
	 * @param orgid
	 * @return
	 */
	public List<SystemUser> getPageByOrgid(@Param("orgid") String orgid, @Param("start") int start, @Param("end") int end);

	public int countByOrgidAndKey(@Param("orgid") String orgid, @Param("key") String key);
	/**
	 * 根据 orgid 搜索
	 * @param orgid
	 * @return
	 */
	public List<SystemUser> getPageByOrgidAndKey(@Param("orgid") String orgid, @Param("key") String key, @Param("start") int start, @Param("end") int end);

	public int countByOrgids(@Param("orgids") String orgids);
	/**
	 * 根据 orgids 获取
	 * @param orgids 参数形式: 【'1','2','65'】
	 * @return
	 */
	public List<SystemUser> getPageByOrgids(@Param("orgids") String orgids, @Param("start") int start, @Param("end") int end);
	
	public List<SystemUser> getUsersByOrgid(Map<String,Object> map);
	
	public List<SystemUser> getUserInfoByOrgid(String orgid);
	
	public List<SystemUser> getUserIdByOrgids(String orgid);
	
	public List<Map> getUsersByDepartid(Map map);
	
	public List<Map> getCurrDepartUsersByDepartid(Map map);

	public List<String> getCurrentUserCourtRole(String userid);

	public List<SystemUser> getNextShenPiRenList(@Param("orgid") String orgid,@Param("temprole") String temprole);
	
	//start add by blue_lv 2015-07-14
	public List<SystemUser> getPageByOrgidAndKey2(Map<String,Object>map);
	public int getcountbyOrgidAndKey(Map<String,Object>map);	
	public int updateByPrimaryKeySelective(SystemUser record);
	//end add by blue_lv 2015-07-14

	public List<Map<String, Object>> getKeYuanList(Map<String, Object> map);
	
	//获取user
	public List<SystemUser> getUserPassword(String localhostip);
	
	//获取改变数据条数
	public int saveOrUpdateQianShou(Map<String, Object> map);
}