package com.sinog2c.service.api.system;

import java.util.List;
import java.util.Map;

import com.sinog2c.model.system.TbsysFormDetails;

public interface TbsysFormDetailsService {
	
	public List<TbsysFormDetails> getFormDetails(Map<String,Object> map);

}
