package com.sinog2c.service.api.penaltyPerform;

import com.sinog2c.model.prisoner.TbxfLaboreducation;

public interface TbxfLaboreducationServices {
	
	public String getLaborid();
	public int deleteByPrimaryKey(String laborid);
	public void insertLaboreducation(TbxfLaboreducation laboreducation);
	public void updateLaboreducation(TbxfLaboreducation laboreducation);
	TbxfLaboreducation findLaboreducationBycrimid(String crimid,String year,String tempid);
}
