package com.sinog2c.dao.api.prisoner;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.sinog2c.model.prisoner.TbPrisonerCaiChanXing;

public interface TbPrisonerCaiChanXingMapper {
    int deleteByPrimaryKey(String id);

    int insert(TbPrisonerCaiChanXing record);

    int insertSelective(TbPrisonerCaiChanXing record);
    
    int insertSelective1(Map record);

    TbPrisonerCaiChanXing selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(Map map);

    int updateByPrimaryKey(TbPrisonerCaiChanXing record);
    
    List<Map>  getCaiChanByCrimid(Map map);
    
    int countCaiChanByCrimid(Map map);
    
    int deleteByCrimidAndZhiDate(Map map);
    
    TbPrisonerCaiChanXing getByCrimidAndDate(Map map);
    
    void callCaichanxingProcedure(Map<String, String> map);
    
    void callCaichanDeleteProcedure(Map map);
    
    int isByPrimaryKeySelective(Map map);


}