package com.sinog2c.dao.api.system;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.sinog2c.model.system.SystemOrganization;

@Repository
public interface SystemOrganizationMapper {
	
    int insert(SystemOrganization org);

    int insertSelective(SystemOrganization org);
    
	public int insertByMap(Map<String, Object> map);
    //
    public int insertToHistory(SystemOrganization org);
    
    public int delete(SystemOrganization org);

    public int update(SystemOrganization org);

	public int updateByMap(Map<String, Object> map);

    //
	public int countAll();
    /**
     * 查询所有
     * @return
     */
    public List<SystemOrganization> selectAll();
    
    public List<SystemOrganization> listByPorgid(@Param("porgid")String porgid,@Param("unitlevel")String unitlevel);
    
    public List<SystemOrganization> listByLevel(@Param("unitlevel")String unitlevel);
    
    public List<Map<String,Object>> getOrgMapByLevel(@Param("unitlevel")String unitlevel, @Param("porgid")String porgid);
    
    // 可能后期还需要修改
    // 按页面显示
    public List<SystemOrganization> getAllByPage(
			@Param("start")
    		int start, 
			@Param("end")
    		int end);
    //
    public SystemOrganization getByOrgId(
			@Param("orgid")
    		String orgid
    		);
    
    public SystemOrganization getProvinceCode(@Param("orgid") String orgid);
    //
    public SystemOrganization getByUserid(
			@Param("userid")
    		String userid
    		);
    public List<HashMap> getOrgWithUserByOrgId(HashMap map);
    
    public List<Map> getOrganizationByuserid(Map map);
    
    List<SystemOrganization> getByPorgId(String porgid);
    
    List<SystemOrganization> getSonPrisonByPorgId(String porgid);
    List<SystemOrganization> getGrandSonPrisonByPorgId(String porgid);
    
    SystemOrganization getPrisonarealevelByPorgId(String porgid);
	List<SystemOrganization> selectJianQuByPorgid(@Param("porgid")String porgid,@Param("key")String key);
	List<HashMap<String,Object>> getExportdepart(@Param("departid")String departid);
    
	List<Map> getDepartById(Map map);
		
	List<Map> getCrimidStatus(String str);
	
	String getDep(Map map);
	
	String getNameByid(String str);
	
	List<SystemOrganization> getDeptInfo(String str);
	
	int getNumByid(Map map);

	int getNumByDepartid(@Param("userdepartid") String userdepartid,@Param("porgid") String porgid);
	
	SystemOrganization getOrginfoByOrgid(String orgid);
	
	List<Map> getSjDeptInfo(Map map);
	
	List<SystemOrganization> listByLevelByMap(Map map);
	
	SystemOrganization getDepartidByName(Map map);
	
	SystemOrganization getOrginfoByOrgnameAndDepartid(Map map);
	
	List<Map> getCodeListByCodeType(Map map);
	//start add by blue_lv 2015-07-12
	List<SystemOrganization> getAllOrginInfo();
	List<SystemOrganization> getAllOrginfoAndmember();
	List<Map> getOrginfoByFlowdraftid(Map map);
	//end add by blue_lv 2015-07-12
	
	public List<Map> getOrganizationByPorgid(Map map);
}