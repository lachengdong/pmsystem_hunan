package com.sinog2c.dao.api.commutationParole;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.sinog2c.model.commutationParole.TbxfScreening;
import com.sinog2c.model.system.SystemUser;
import com.sinog2c.mvc.message.JSONMessage;

public interface TbxfScreeningMapper {
    int deleteByPrimaryKey(String crimid);

    int insert(TbxfScreening record);

    int insertSelective(TbxfScreening record);

    TbxfScreening selectByPrimaryKey(String crimid);
    
    int updateByPrimaryKeySelective(TbxfScreening record);

    int updateByPrimaryKey(TbxfScreening record);
    
    List<TbxfScreening> screeningList(@Param("departid")String departid);
    
    void deleteAll(@Param("departid")String departid);
    
    int getLiAnApproveCount(Map map);
	
    List<Map> getLiAnApproveList(Map map);
    
    int getLiAnCount(Map map);
	
    List<Map> getLiAnList(Map map);
    List<Map> getLiAnCrimList(Map map);
    int updateIsdeclare(List<String> list);
    
    int updateSpecialCrim(Map map);
    
    int updateLiAnBack(Map map);
    
    int updateDataAfterPrisonerLiAn(Map map);
    
    int updateDataAfterWithdraw(Map map);
    
    String getCrimiNameByCrimid(@Param("crimid")String crimid);
    
    int wastedata(@Param("crimid")String crimid);
    
    void deleteWastedata(@Param("departid")String departid);
    
    void insertNewdata(@Param("departid")String departid);
    
    /**
     * 得到案件性质列表
     * @return
     */
    List<Map> getAnJianXingZhiList(Map map);
    
    int getAnjianXingZhiCount(Map map);
    
    public int dealSpecialCase(Map<String, String> map);
    
    int getBaobeiLiAnCount(Map map);
	
    List<Map> getBaobeiLiAnList(Map map);
    
    Map getCrimidFlowData(String flowdraftid);
   
    int addSanLeiFanBaoBei(Map resourceMap);
    
    int updateSanLeiFanBaoBei(Map resourceMap);
    
    
    
    
    
    void updateStatus4Qualificate(@Param("departid")String departid);
    
    void callPSENTNCECHANG(@Param("departid")String departid);
    
    int countDepartQualifierTemp(Map<String,Object> map);
    
    List<Map<String,Object>> getQualifierCriminal(Map<String,Object> map);
    
    //获取三类犯、重要罪犯、无期、死缓案件资格判定1为是0为不是
    String decideOperateSymbol(String applyid);
}