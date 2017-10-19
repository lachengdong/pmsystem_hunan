package com.sinog2c.service.api.prisoner;

import java.util.List;
import java.util.Map;

import com.sinog2c.model.prisoner.TbprisonerBaseinfo;

public interface TbprisonerBaseInfoService {
	
	public TbprisonerBaseinfo getTbprisonerBaseInfoByCrimid(String crimid);
	
	public void makeNamePinyinShort();
	
}
