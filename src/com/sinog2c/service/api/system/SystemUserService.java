package com.sinog2c.service.api.system;

import java.util.List;
import java.util.Map;

import com.sinog2c.model.system.SystemOrganization;
import com.sinog2c.model.system.SystemUser;
import com.sinog2c.model.system.UserOrgWrapper;
import com.sinog2c.model.user.UserCertificate;

public interface SystemUserService {
    
	/**
	 * 新增用户
	 * @param user 需要新增的用户对象
	 * @param operator 操作员
	 * @return
	 */
    public int addUser(SystemUser user, SystemUser operator);
    
    /**
     * 更新自己的信息
     * @param user 用户对象
     * @param operator 操作人员
     * @return
     */
    public int updateSelfInfo(SystemUser user, SystemUser operator);
    /**
     * 更新用户的信息
     * @param user 用户对象
     * @param operator 操作人员
     * @return
     */
    public int updateUserInfo(SystemUser user, SystemUser operator);
    /**
     * 更新用户部门
     * @param userOrgWrapper
     * @param operator
     * @return
     */
    public boolean updateUserOrg(UserOrgWrapper userOrgWrapper, SystemUser operator);

	/**
	 * 删除记录到历史记录
	 * @param user 用户对象,必须有userid
	 * @return
	 */
	public int deleteToHistory(SystemUser user, SystemUser operator);
	
	
    /*
    public int update(SystemUser user);
   
    public int delete(String userName);
   
    public List<SystemUser> selectAll();
   
    */

    /**
     * 绑定用户证书
     */
    public int bindUserCert(UserCertificate cert, SystemUser user);
    
    public SystemUser getByUserId(String userId);
    /**
     * 根据用户ID获取Cert,原则上只能获取1个
     * @param userid
     * @return
     */
    public List<UserCertificate> listCertByUserId(String userid);
    /**
     * 根据用户ID获取Cert
     * @param userid
     * @return
     */
    public UserCertificate getCertByUserId(String userid);
    /**
     * 根据SN获取
     * @param certsn
     * @return
     */
    public UserCertificate getCertByCertsn(String certsn);
    /**
     * 根据certsn获取用户
     * @param certsn
     * @return
     */
    public SystemUser getUserByCert(String certsn, String certdata);
    
    public int countAll();
   
    public List<SystemUser> getAllByPage(int pageIndex, int pageSize);
	
    public int countBySearch(String key);
   
    public List<SystemUser> searchByPage(String key, int pageIndex, int pageSize);


    /**
     * 根据 org 获取所有子部门用户 数量
     * @param org
     * @return
     */
	public int countByOrg(SystemOrganization org);
	/**
	 * 根据 org 获取所有子部门用户
	 * @param org
	 * @param pageIndex 从0开始
	 * @param pageSize
	 * @return
	 */
	public List<SystemUser> getPageByOrg(SystemOrganization org, int pageIndex, int pageSize);

	public int countByOrgKey(SystemOrganization org, String key);
	/**
	 * 根据 org 搜索
	 * @param org
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	public List<SystemUser> getPageByOrgKey(SystemOrganization org, String key, int pageIndex, int pageSize);

	public int countByOrgs(List<SystemOrganization> orgs);
	/**
	 * 根据 orgs 获取
	 * @param orgs
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	public List<SystemUser> getPageByOrgs(List<SystemOrganization> orgs, int pageIndex, int pageSize);
    
	public List<SystemUser> getUsersByOrgid(Map<String,Object> map);
	
	public List<Map> getUsersByDepartid(Map map);
	
	public List<Map> getCurrDepartUsersByDepartid(Map map);

	public Object getNextShenPiRenList(String departid, String userid);
	
	//start add by blue_lv 2015-07-14
	public com.sinog2c.model.JSONMessage<SystemUser> getPageByOrgidAndKey2(
			Map<String, Object> parseParamMapForPagination);	
	public int updateByPrimaryKeySelective(SystemUser updateUser);
	//end add by blue_lv 2015-07-14

	public List<Map<String, Object>> getKeYuanList(Map<String, Object> map);
	//关机
	public Object getUserPassword(Map<String, Object> paramMap);
	//签收
	public Object saveOrUpdateQianShou(Map<String, Object> paramMap);
	
	public List<Map<String,Object>> getDepartNameList(Map map);

	public int jiechukey(String certno); 
}