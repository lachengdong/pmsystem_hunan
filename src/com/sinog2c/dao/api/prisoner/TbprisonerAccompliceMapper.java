package com.sinog2c.dao.api.prisoner;

import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Param;
import com.sinog2c.model.prisoner.TbprisonerAccomplice;
import com.sinog2c.model.prisoner.TbprisonerAccompliceKey;

public interface TbprisonerAccompliceMapper {
    int deleteByPrimaryKey(TbprisonerAccompliceKey key);

    int insertSelective(TbprisonerAccomplice record);

    TbprisonerAccomplice selectByPrimaryKey(TbprisonerAccompliceKey key);

    int updateByPrimaryKeySelective(TbprisonerAccomplice record);
    
    List<TbprisonerAccomplice> selectAccompliceByCrimid(String crimid);
    
 	public List<Map> getCrimeBaseInfoByCaseNo(@Param("crimid")String crimid,@Param("caseorgshort")String caseorgshort,@Param("caseno")String caseno);

}