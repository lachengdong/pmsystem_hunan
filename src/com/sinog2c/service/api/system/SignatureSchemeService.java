package com.sinog2c.service.api.system;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.sinog2c.model.system.SignatureScheme;
import com.sinog2c.model.system.SystemUser;

/**
 * 签章方案服务
 */
public interface SignatureSchemeService {

    public int insert(SignatureScheme scheme, SystemUser operator);
    public int insertByMap(Map<String, Object> paraMap, SystemUser operator);
    
    public int update(SignatureScheme scheme, SystemUser operator);
    public int updateByMap(Map<String, Object> paraMap, SystemUser operator);

	public int delete(Integer signid,String toid,String tempid);
	
    public SignatureScheme getById(Integer signid);
    
    public Map getMapById(Integer signid);
    
    public Map getMapById_nx(Integer signid);
    

	/**
	 * 获取所有
	 * @return
	 */
	public int countAll(String key,String orgid,String tempid);
	/**
	 * 分页获取
	 * @param pageIndex
	 * @param pageSize
	 * @param sortField
	 * @param sortOrder
	 * @return
	 */
	public List<SignatureScheme> listAllByPage(int pageIndex, int pageSize, String sortField, String sortOrder,String key,String orgid,String tempid);
	
	/**
	 * 获取签章方案(原，山西可能再用，其它地区不用此方法)
	 */
	public List<SignatureScheme> getListAll(HashMap map);
	/**
	 * 获取签章方案(现有)
	 */
	public List<SignatureScheme> getSignatureSchemeList(HashMap map);
	/**
	 * 获取签章方案(现有)
	 * 宁夏分离 批量签章页面
	 */
	public List<SignatureScheme> getSignatureSchemeList_nx(HashMap map);
	/**
	 * 方法描述:获取最大的批次(批量签章的位置)
	 * @author:mushuhong
	 * @version 2015年1月6日14:46:10
	 */
	public Map signGetMaxCuryear(HttpServletRequest request,SystemUser user);
	public SignatureScheme selectSignatureScheme(String id);
    
	public List<Map> batchExportHandCaseFile(HttpServletRequest request,SystemUser user);
	
	public String savePDFDocToService(HttpServletRequest request);
	public List<SignatureScheme> getCourtSignatureSchemeList(HashMap map);
	public int getCountByDepartid(String fromid,String tempid);
	public List<Map> getSignByDepart();
	public void copySignByDepartid(String fromid,String toid,String tempid);
	
	public List<SignatureScheme> getSignatureSchemesByUser(Map<String, Object> map);
	
	public List<SignatureScheme> getSignatureSchemesByPsignid(Map<String, Object> map);
	
	public SignatureScheme getPreSignatureScheme(SignatureScheme signatureScheme);
	
	public SignatureScheme getSignatureSchemeByCondition(Map<String,Object> map);
	/**
	 * 获取进行此资源对应的流程操作时需要有多少个批注、签章
	 * @param resid
	 * @return
	 */
	public String getSignatureSchemeByResid(String resid);
}