package com.sinog2c.service.impl.prisoner;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sinog2c.dao.api.prisoner.TbprisonerSocialRelationMapper;
import com.sinog2c.model.prisoner.TbprisonerSocialRelation;
import com.sinog2c.service.api.prisoner.TbprisonerSocialRelationService;
import com.sinog2c.service.impl.base.ServiceImplBase;

@Service("tbprisonerSocialRelationService")
public class TbprisonerSocialRelationServiceIpml extends ServiceImplBase implements  TbprisonerSocialRelationService {

	/**
	 * 罪犯社会关系表相关操作
	 */
	@Autowired
	private TbprisonerSocialRelationMapper tbprisonerSocialRelationMapper;
	
	@Override
	public int deleteByPrimaryKey(Integer srid) {
		return tbprisonerSocialRelationMapper.deleteByPrimaryKey(srid);
	}

	@Override
	public TbprisonerSocialRelation findBycrimid(String crimid, String isprimarycontact) {
		return tbprisonerSocialRelationMapper.findBycrimid(crimid, isprimarycontact);
	}

	@Override
	public List<TbprisonerSocialRelation> findRelationBycrimid(String crimid) {
		return tbprisonerSocialRelationMapper.findRelationBycrimid(crimid);
	}

	@Override
	public int insertSelective(TbprisonerSocialRelation record) {
		return tbprisonerSocialRelationMapper.insertSelective(record);
	}

	@Override
	public TbprisonerSocialRelation selectByPrimaryKey(Integer srid) {
		return tbprisonerSocialRelationMapper.selectByPrimaryKey(srid);
	}

	@Override
	public int updateByPrimaryKeySelective(TbprisonerSocialRelation record) {
		return tbprisonerSocialRelationMapper.updateByPrimaryKeySelective(record);
	}

	
}
