package com.sinog2c.service.impl.prisoner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sinog2c.dao.api.prisoner.TbprisonerBaseinfoMapper;
import com.sinog2c.model.prisoner.TbprisonerBaseinfo;
import com.sinog2c.service.api.prisoner.TbprisonerBaseInfoService;
import com.sinog2c.service.impl.base.ServiceImplBase;

@Service("tbprisonerBaseInfoService")
public class TbprisonerBaseInfoServiceImpl extends ServiceImplBase  implements TbprisonerBaseInfoService{

	@Autowired
	TbprisonerBaseinfoMapper tbprisonerBaseinfoMapper;
	
	@Override
	public TbprisonerBaseinfo getTbprisonerBaseInfoByCrimid(String crimid) {
		return tbprisonerBaseinfoMapper.selectByPrimaryKey(crimid);
	}
	
	@Override
	public void makeNamePinyinShort(){
		tbprisonerBaseinfoMapper.makeNamePinyinShort();
	}

}
