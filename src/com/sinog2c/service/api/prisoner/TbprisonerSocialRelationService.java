package com.sinog2c.service.api.prisoner;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.sinog2c.model.prisoner.TbprisonerSocialRelation;

public interface TbprisonerSocialRelationService {

	int deleteByPrimaryKey(Integer srid);

	int insertSelective(TbprisonerSocialRelation record);

	TbprisonerSocialRelation selectByPrimaryKey(Integer srid);

	int updateByPrimaryKeySelective(TbprisonerSocialRelation record);
	
	TbprisonerSocialRelation findBycrimid(@Param("crimid")String crimid,@Param("isprimarycontact")String isprimarycontact);
	
	List<TbprisonerSocialRelation> findRelationBycrimid(String crimid);
}
