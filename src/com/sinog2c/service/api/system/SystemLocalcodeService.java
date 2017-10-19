package com.sinog2c.service.api.system;

import java.util.List;
import java.util.Map;

import com.sinog2c.model.system.TbsysLocalcode;

public interface SystemLocalcodeService {
	List<TbsysLocalcode> getLocalcodeList(Map<String,Object> map);
	
	String getLocalcodeUserbytemp(Map<String,Object> map);
}
