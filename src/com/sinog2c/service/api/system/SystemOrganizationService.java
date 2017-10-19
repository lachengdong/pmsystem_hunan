package com.sinog2c.service.api.system;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sinog2c.model.system.SystemOrganization;
import com.sinog2c.model.system.SystemUser;

public interface SystemOrganizationService {

	/**
	 * 使用sequence自增ID方式新增
	 * @param org 机构对象
	 * @param operator 当前用户
	 * @return
	 */
	public int add(SystemOrganization org, SystemUser operator);
	/**
	 * 新增系统机构,不使用序列自增机制,需要指定好ID号
	 * @param org 机构对象
	 * @param operator 当前用户
	 * @return
	 */
	public int insertWithId(SystemOrganization org, SystemUser operator);
	/**
	 * 根据map进行更新
	 * @param map
	 * @param operator
	 * @return
	 */
	public int insertByMap(Map<String, Object> map, SystemUser operator);

	/**
	 * 修改机构信息
	 * @param org 机构对象
	 * @param operator 当前用户
	 * @return
	 */
	public int update(SystemOrganization org, SystemUser operator);
	/**
	 * 由hashmap更新
	 * @param org
	 * @param operator
	 * @return
	 */
	public int updateByMap(Map<String, Object> map, SystemUser operator);

	/**
	 * 删除机构,逻辑上进行删除
	 * @param orgid
	 * @return
	 */
	public int delete(String orgid, SystemUser operator);

	/**
	 * 取得所有未删除的机构列表
	 * @return
	 */
	public List<SystemOrganization> selectAll();

	/**
	 * 根据页面取得数据
	 * @param pageIndex 页码,0开始
	 * @param pageSize 每页数量
	 * @return
	 */
    public List<SystemOrganization> getAllByPage(int pageIndex,	int pageSize);

	/**
	 * 根据 pid取得递归子元素机构列表
	 * 
	 * @param porgid
	 * @return
	 */
	public List<SystemOrganization> listByOrganizationPid(String porgid,String unitlevel);

    /**
     * 根据
     * @param unitlevel
     * @return
     */
    public List<SystemOrganization> listByLevel(String unitlevel);
    
    /**
     * 根据
     * @param unitlevel
     * @return
     */
    public List<Map<String,Object>> listMapByLevel(String unitlevel,String porgid);
	/**
	 * 取得所有未删除的机构数量
	 * @return
	 */
	public int countAll();

	/**
	 * 根据ID取得
	 * @param orgid
	 * @return
	 */
	public SystemOrganization getByOrganizationId(String orgid);
	
	public List<HashMap> getOrgWithUserByOrgId(HashMap map);
	public List<Map> getOrganizationByuserid(Map map);
	/**
	 * 根据父级id 查询 所有直属子部门
	 * @param porgid
	 * @return
	 */
	List<SystemOrganization> getByPorgId(String porgid);

	/**
	 * select * from tbsys_orginfo where porgid in
	 * (select orgid from tbsys_orginfo where porgid='1200');
	 * @param porgid
	 * @return
	 */
	List<SystemOrganization> getSonPrisonByPorgId(String porgid);
	/**
	 *    select * from tbsys_orginfo where porgid in 
	 *    (select orgid from tbsys_orginfo where porgid in 
	 *    (select orgid from tbsys_orginfo where porgid=#{porgid}))

	 * @param porgid
	 * @return
	 */
	 List<SystemOrganization> getGrandSonPrisonByPorgId(String porgid);
	 /**
	  * 根据用户所在单位获取入监监区
	  */
	 SystemOrganization getPrisonarealevelByPorgId(String porgid);
	 /**
	  * 根据用户所在单位获取监区
	  */
	 List<SystemOrganization> selectJianQuByPorgid(String porgid,String key);
	 
	 /**
	  * 根据用户部门级别获取相应级别部门信息
	  * @param map
	  * @return
	  */
	 List<Map> getDepartById(Map map);

	 /**
	  * 根据用户id获取用户所在部门的部门级别
	  * @param str
	  * @return
	  */
	 String getDep(Map  map);
	 
	 List<Map> getCrimidStatus(String str);
	 List<Map> getCodeListByCodeType(Map map);
	 List<HashMap<String,Object>>  getExportdepart(String departid);
	 SystemOrganization getProvinceCode(String orgid);

	 String getNameByid(String str);
	 
	 List<SystemOrganization> getDeptInfo(String str);
	 
	 /**
	  * 方法描述：省局 下属单位和部门
	  * @author：mushuhong
	  * @version:2015年2月3日17:01:25
	  * @param map
	  * @return
	  */
	 List<Map> getSjDeptInfo(String orgid,SystemUser user);
	 
	 int getNumByid(Map map);
	public int getNumByDepartid(String userdepartid,String porgid);
	
	public SystemOrganization  getOrginfoByOrgid(String orgid);
	
	public String getcodeValue(SystemUser user);
	
	public SystemOrganization getDepartidByName(Map map);
	//start add by blue_lv 2015-07-12
	/**
	 * 获取所用部门信息
	 * @return
	 */
	public List<SystemOrganization> getAllOrginInfo();
	
	/**
	 * 获取所用部门以及部门下成员
	 * @return
	 */
	public List<SystemOrganization> getAllOrginfoAndmember();
	//end add by blue_lv 2015-07-12
	
	/**
	 * 获取部门信息BYflowdraftid
	 */
	public List<Map> getOrginfoByFlowdraftid(Map<String, Object> map);
	
	public SystemOrganization getOrginfoByOrgnameAndDepartid(Map<String,Object> map);
	
	public List<SystemOrganization> getJailidByProvinceid(Map<String,Object> paramap);
	
	public List<Map> getOrganizationByPorgid(Map map);
	
}