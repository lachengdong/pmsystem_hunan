package com.sinog2c.service.api.prisoner;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.sinog2c.model.prisoner.Tbxfoldilldisability;
import com.sinog2c.model.prisoner.TbxfoldilldisabilityKey;
import com.sinog2c.model.system.SystemUser;

/**
 * 老病残信息补录
 * @author wangxf
 *
 */
@Service("TbxfoldilldisabilityService")
public interface TbxfoldilldisabilityService {
	/**
	 *  根据key 删除单条
	 * @param key(根据罪犯id 老病残种类oidtypedetail  老病残种类明细oidtypedetail)
	 * @return
	 * @author wangxf
	 */
    int deleteByPrimaryKey(Map<String, Object> map);

    /**
     * 只更新非空字段
     * @param record
     * @return
     * @author wangxf
     */
    int insertSelective(Tbxfoldilldisability record);

    /**
     * 根据key查询
     * @param key
     * @return
     * @author wangxf
     */
    Tbxfoldilldisability selectByPrimaryKey(TbxfoldilldisabilityKey key);

    /**
     * 根据key更新全部字段
     * @param record
     * @return
     * @author wangxf
     */
    int updateByPrimaryKeySelective(Tbxfoldilldisability tbxfoldilldisability);
    /**
     * 根据罪犯id查询
     * @return
     * @author wangxf
     */
    List<Map<String, Object>> findByCrimid(Map<String,Object> map);
    
    int findByCrimidCount(Map<String,Object> map);
    
    int checkByPrimaryKey(Map<String, String> map);
    
    /**
     * 在新增或者修改老病残的同时同步老病残情况到tbprisoner_base_crime(OIDTYPE存为0,1)和tbxf_sentencealteration表(oidtypedetail)
     */
    Map<String,String> updateBaseCrimeAndSentenceAlteration(Map<String,String> map);
    Map<String,String> deleteBaseCrimeAndSentenceAlteration(Map<String,String> map);
    
    int tongbuBaseCrimeAndSentenceAlteration(String crimid);
    /**
     * 修改老病残补录状态：0无效，1有效，2撤销，同时保存撤销时间和撤销原因
     */
    int updateRevokeOldIllDisabilityStatus(Map<String,Object> map);
    
    /**
     * 判断老病残操作权限，根据罪犯在tbflow_base的减刑假释currnodeid流程节点和state状态判断，（只有流程节点在管教的时候才允许，其他只有查看权限）
     */
    List<Map<String, Object>> getcontrolsymbolByCrimid(String crimid);
    
}
