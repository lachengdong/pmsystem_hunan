package com.sinog2c.service.impl.penaltyPerform;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.sinog2c.dao.api.prisoner.TbxfLaboreducationMapper;
import com.sinog2c.model.prisoner.TbxfLaboreducation;
import com.sinog2c.service.api.penaltyPerform.TbxfLaboreducationServices;
import com.sinog2c.service.impl.base.ServiceImplBase;

@Service("TbxfLaboreducationServices")
public class TbxfLaboreducationServicesImpl extends ServiceImplBase implements TbxfLaboreducationServices{
	@Autowired
	private TbxfLaboreducationMapper laboreducationMapper;
	
	
	public void insertLaboreducation(TbxfLaboreducation laboreducation) {
		laboreducationMapper.insertSelective(laboreducation);
	}
	@Override
	public void updateLaboreducation(TbxfLaboreducation laboreducation) {
		laboreducationMapper.updateByPrimaryKeySelective(laboreducation);
	}
	@Override
	public String getLaborid() {
		return laboreducationMapper.getLaborid();
	}
	@Override
	public int deleteByPrimaryKey(String laborid) {
		return laboreducationMapper.deleteByPrimaryKey(laborid);
	}
	@Override
	public TbxfLaboreducation findLaboreducationBycrimid(String crimid,String year,String tempid){
		return laboreducationMapper.findLaboreducationBycrimid(crimid,year,tempid);
	};

}
