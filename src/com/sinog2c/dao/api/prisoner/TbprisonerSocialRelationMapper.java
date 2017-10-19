package com.sinog2c.dao.api.prisoner;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.sinog2c.model.prisoner.TbprisonerSocialRelation;

/**
 * 罪犯社会关系表
 * @author wangxf
 *
 */
public interface TbprisonerSocialRelationMapper {
	String getSrid();
	
    int deleteByPrimaryKey(Integer srid);

    int insertByMap(Map map);
    
    int insertSelective(TbprisonerSocialRelation record);

    TbprisonerSocialRelation selectByPrimaryKey(Integer srid);
    
    TbprisonerSocialRelation getSocialByMap(Map map);

    int updateByPrimaryKeySelective(TbprisonerSocialRelation record);
    
    TbprisonerSocialRelation findBycrimid(@Param("crimid")String crimid,@Param("isprimarycontact")String isprimarycontact);
    
    List<TbprisonerSocialRelation> findRelationBycrimid(String crimid);

	Map getYorNnullByMap(Map map);
    
}