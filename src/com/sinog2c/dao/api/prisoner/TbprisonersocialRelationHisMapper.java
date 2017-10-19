package com.sinog2c.dao.api.prisoner;


import com.sinog2c.model.prisoner.TbprisonersocialRelationHis;
/**
 * 罪犯社会关系历史
 * @author wangxf
 *
 */
public interface TbprisonersocialRelationHisMapper {
    int insert(TbprisonersocialRelationHis record);

    int insertSelective(TbprisonersocialRelationHis record);
  
    /**
	 * 根据罪犯id返回罪犯社会关系历史
	 * @param crimid
	 * @return
	 */
    TbprisonersocialRelationHis findBycrimid(String crimid);
}