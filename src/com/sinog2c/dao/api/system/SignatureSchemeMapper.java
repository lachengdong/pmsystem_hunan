package com.sinog2c.dao.api.system;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.sinog2c.model.system.SignatureScheme;

@Repository
public interface SignatureSchemeMapper {

	public int insertSelective(SignatureScheme scheme);
	
	public int insertByMap(Map<String, Object> paraMap);

	public int updateSelective(SignatureScheme scheme);

	public int updateByMap(Map<String, Object> paraMap);

	public int delete(@Param("signid")Integer signid,@Param("toid")String toid,@Param("tempid")String tempid);
    
    /**
     * 获取自增长的 ID
     * @return
     */
    public int getNextId();
    
	public SignatureScheme getById(Integer signid);
	
	public Map getMapById(Integer signid);
	
	public Map getMapById_nx(Integer signid);
	
	public int countAll(@Param("key")String key,@Param("orgid")String orgid,@Param("tempid")String tempid);
	
	public int getCountByDepartid(@Param("fromid")String fromid,@Param("tempid")String tempid);
	
	public List<Map> getSignByDepart();

	public void copySignByDepartid(@Param("fromid")String fromid,@Param("toid")String toid,@Param("tempid")String tempid);
	
	public List<SignatureScheme> listAllByPage(@Param("start")int start, @Param("end")int end, @Param("sortField")String sortField, 
			@Param("sortOrder")String sortOrder ,@Param("key")String key,@Param("orgid")String orgid,@Param("tempid")String tempid );
	public List<SignatureScheme> getListAll(HashMap map);
	public SignatureScheme selectselectSignatureScheme(String id);
	
	public List<Map> batchExportHandCaseFile(Map map);
	
	public List<Map> getSignSchemeType(String userid);
	
	public List<Map> getCrimInfoByOrgid1(Map whereMap);
	
	public int updateSignProgressToFlowBase(Map map);
	
	public List<SignatureScheme> getSignatureSchemeList(HashMap map);

	public List<SignatureScheme> getSignatureSchemeList_nx(HashMap map);
	
	public List<SignatureScheme> getCourtSignatureSchemeList(HashMap map);
	
	public Map signGetMaxCuryearMapper(Map map);
	
	public List<SignatureScheme> getSignatureSchemesByUser( Map<String, Object> map);
	
	public List<SignatureScheme> getSignatureSchemesByPsignid(Map<String, Object> map);
	
	public SignatureScheme getPreSignatureScheme(SignatureScheme signatureScheme);
	
	public SignatureScheme getSignatureSchemeByCondition(Map<String,Object> map);
	
	public SignatureScheme getSignatureSchemeByResid(String resid);
	
	public SignatureScheme getCurrnodeidSignatureScheme(Map<String,Object> map);
	
}