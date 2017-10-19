package com.sinog2c.service.api.assessAndPunish;

import java.util.Map;

import com.sinog2c.model.system.TbxfPrisonerperformancemerge;

public interface TbxfPrisonerperformancemergeService {

	TbxfPrisonerperformancemerge selectByCrimidAndBatchid(Map map);
	
	String getDepartidByCrimid(String str);
}
