package com.sinog2c.service.api.prisoner;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

import com.sinog2c.model.prisoner.TbPrisonerCaiChanXing;

public interface TbPrisonerCaiChanXingService {

	int deleteByPrimaryKey(String crimid);

    int insert(TbPrisonerCaiChanXing record);

    int insertSelective(TbPrisonerCaiChanXing record);

    int insertSelective(Map record);
    
    TbPrisonerCaiChanXing selectByPrimaryKey(String crimid);

    int updateByPrimaryKeySelective(Map map);

    int updateByPrimaryKey(TbPrisonerCaiChanXing record);
    
    List<Map>  getCaiChanByCrimid(Map map);
    
    int countCaiChanByCrimid(Map map);
    
    int deleteByCrimidAndZhiDate(Map map);
    
    TbPrisonerCaiChanXing getByCrimidAndDate(Map map);
}
