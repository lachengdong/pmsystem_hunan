package com.sinog2c.service.impl.release;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sinog2c.dao.api.release.TbprisonerCrjInfoMapper;
import com.sinog2c.service.api.release.ReleaseService;
import com.sinog2c.util.common.MapUtil;

@Service("releaseService")
public class ReleaseServiceImpl implements ReleaseService {
	
	@Autowired
	private TbprisonerCrjInfoMapper tbprisonerCrjInfoMapper;
	@Override
	public List<Map> ajaxCodeShuJu(Map map) {
		// TODO Auto-generated method stub
		return MapUtil.turnKeyToLowerCaseOfList(tbprisonerCrjInfoMapper.ajaxCodeData(map));
	}
	@Override
	public int saveOutPrison(Map map) {
		// TODO Auto-generated method stub
		return tbprisonerCrjInfoMapper.savePrison(map);
	}
	@Override
	public List<Map> getOutPrison(String crimid) {
		// TODO Auto-generated method stub
		return tbprisonerCrjInfoMapper.getOutPrison(crimid);
	}
	
}
