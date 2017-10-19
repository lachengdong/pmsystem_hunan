package com.sinog2c.dao.api.commutationParole;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.sinog2c.model.commutationParole.TbcourtCaseAttentionChoice;

public interface TbcourtCaseAttentionChoiceMapper {
    int deleteByPrimaryKey(Integer cacid);

    int insert(TbcourtCaseAttentionChoice record);

    int insertSelective(TbcourtCaseAttentionChoice record);

    TbcourtCaseAttentionChoice selectByPrimaryKey(Integer cacid);

    int updateByPrimaryKeySelective(TbcourtCaseAttentionChoice record);

    int updateByPrimaryKey(TbcourtCaseAttentionChoice record);
    
    int getCount(@Param("key")String key);
	
	List<HashMap> getCaseAttentionChoiceList(@Param("key")String key, @Param("sortField")String sortField, @Param("sortOrder")String sortOrder, @Param("start")int start, @Param("end")int end );
	
	List<HashMap<String, String>> ajaxGetCodeData(@Param("codeType")String codeType);
	
	List<HashMap<String, String>> ajaxGetDepartData(@Param("unitLevel")String unitLevel,@Param("topOrgid")String topOrgid);
	
	String getNameByCodeId(@Param("codeType")String codeType, @Param("codeId")String codeId);
	
	String getNameByOrgId(@Param("orgId")String orgId);
	
	String getNameBySenId(@Param("senId")String senId);
}