package com.sinog2c.dao.api.prisoner;


import com.sinog2c.model.prisoner.TbprisonerResumHis;
/**
 * 罪犯简历历史
 * @author wangxf
 *
 */
public interface TbprisonerResumHisMapper {
    int insert(TbprisonerResumHis record);

    int insertSelective(TbprisonerResumHis record);
    
    TbprisonerResumHis findBycrimid(String crimid);
}