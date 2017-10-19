package com.sinog2c.service.impl.system;

import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.sinog2c.dao.api.system.TbsysLocalcodeMapper;
import com.sinog2c.model.system.TbsysLocalcode;
import com.sinog2c.service.api.system.SystemLocalcodeService;

@Service("systemLocalcodeService")
public class SystemLocalcodeServiceImpl implements SystemLocalcodeService{
	@Autowired
	private TbsysLocalcodeMapper tbsysLocalcodeMapper;
	
	public List<TbsysLocalcode> getLocalcodeList(Map<String,Object> map) {
		return tbsysLocalcodeMapper.getLocalcodeList(map);
	}
	
	public String getLocalcodeUserbytemp(Map<String,Object> map) {
		List<TbsysLocalcode> codeList = tbsysLocalcodeMapper.getLocalcodeList(map);
		String tempStr = "";
		if (codeList.size()>0) {
			for (TbsysLocalcode code : codeList) {
				tempStr += ("[[" + code.getScearch() + "]]" + code.getName() + "||");
			}
			tempStr = tempStr.substring(0, tempStr.length() - 2);
		}
		return tempStr;
	}
}
