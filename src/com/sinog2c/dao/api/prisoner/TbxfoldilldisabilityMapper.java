package com.sinog2c.dao.api.prisoner;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.sinog2c.model.prisoner.Tbxfoldilldisability;
import com.sinog2c.model.prisoner.TbxfoldilldisabilityKey;
import com.sinog2c.model.system.SystemUser;

public interface TbxfoldilldisabilityMapper {
	
    int deleteByPrimaryKey(Map<String, Object> map);

    int insertSelective(Tbxfoldilldisability record);

    Tbxfoldilldisability selectByPrimaryKey(TbxfoldilldisabilityKey key);

    int updateByPrimaryKeySelective(Tbxfoldilldisability tbxfoldilldisability);
    
    List<Map<String, Object>> findByCrimid(Map<String, Object> map);
    
    int findByCrimidCount(Map<String, Object> map);
    
    int checkByPrimaryKey(Map<String, String> map);

    int updatetbprisonerbasecrime(Map<String,String> map);//同步更新tbprisoner_base_crime(添加和修改)
    int updatetbxfsentencealteration(Map<String,String> map);//同步更新tbxf_sentencealteration(添加和修改)
    
    int updateOldIllDisabilityStatus(Map<String,Object> map);//更改状态保存撤销时间和撤消原因
    
    int tongbuBaseCrimeAndSentenceAlteration(Map<String,String> map);//同步更新tbxf_sentencealteration和tbprisoner_base_crime(新方法)
    
    Map<String,Object> getOldSickDisableInfoByCrimid(@Param("crimid")String crimid);
    
    int updateSentenceOldIllDisability(Map<String,Object> map);
    
    int updateBaseCrimeOldIllDisability(Map<String,Object> map);
    
    List<Map<String, Object>> getcontrolsymbolByCrimid(String crimid);
    
}