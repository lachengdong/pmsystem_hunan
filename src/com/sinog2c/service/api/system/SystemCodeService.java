package com.sinog2c.service.api.system;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.sinog2c.model.system.SystemOrganization;
import com.sinog2c.model.system.SystemUser;
import com.sinog2c.model.system.TbsysCode;

public interface SystemCodeService {
	public  String getcodeValue(String codeType);
	
	List<TbsysCode> selectValueByCodeType(String codeType);
	
	public List<HashMap> ajaxGetcodeValueForOpinion(String codeType);
	public List<HashMap> ajaxGetcodeValueForOpinion1(String codeType);
	
	public List<Map> getCodeByCodeType(String codeType,String codeid,String pcodeid);
	
	public List<Map> getCodeByCode(HttpServletRequest request,SystemUser user);
	
	public String getCodeByOrgList(List<SystemOrganization> oList);
	public String getCodeValueByCodeTypeAndCodeId(Map map);
	
	public String getCodeValueByStrings(String codetype,String codeid);
	

    /**
     * 根据代码类别获取code列表<br/>
     * 代码按照： 代码类型(codetype),代码编号(codeid)来进行区分
     * @param codetype
     * @return
     */
    public List<TbsysCode> listByCodetype(String codetype);
    
    public List<Map> listByMap(Map map);
    
    public Map getDataByMap(Map map);
    
    public List<Map> selectValueByMap(Map map);

	// 统计所有
	public int countAll();
	// 获取所有 list
	public List<TbsysCode> listAll();
	// 根据条件统计
	public int countByCondition(Map<String, Object> map);
	// 根据条件获取数据
	public List<TbsysCode> listByCondition(Map<String, Object> map);
	
	public List<TbsysCode> listAllByCondition(Map<String, Object> map);

	// 根据Map更新
	public int updateByMap(Map<String, Object> map);
	// 根据Map插入
	public int insertByMap(Map<String, Object> map);
	/**
     * 根据罪犯编号查询建议减刑幅度情况
     * @param codetype
     * @return
     */
	public List<HashMap<Object, Object>> getAmplitudeData(String crimid);

	public Object selectValueByCodeTypeAndCodeid(Map map);
	
	public List<TbsysCode> getCodesByMap(Map map);
	
	public List<Map> getCaseTypeMap();  
	
	public List<Map> getAllSFSelectImpl(HttpServletRequest request,SystemUser user);
	
	/**
	 * 省市县通过下级codeid查询出上级的codeid
	 * @author：mushuhong
	 * @version:2016年1月18日10:19:42
	 */
	public Map getSJCodeidByXjCodeid(String codeid,String type);
	
	public List<Map> getSanLeiType(HttpServletRequest request);
}
