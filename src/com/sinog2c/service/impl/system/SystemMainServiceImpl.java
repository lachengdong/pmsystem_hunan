package com.sinog2c.service.impl.system;

import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.sinog2c.dao.api.flow.UvFlowMapper;
import com.sinog2c.service.api.system.SystemMainService;
import com.sinog2c.service.impl.base.ServiceImplBase;
import com.sinog2c.util.common.MapUtil;


@Service("systemMainService")
public class SystemMainServiceImpl extends ServiceImplBase implements SystemMainService{

	@Autowired
	private UvFlowMapper uvFlowMapper;
	
	public List<Map> getBaseDocByCondition(Map map){
		return MapUtil.turnKeyToLowerCaseOfList(uvFlowMapper.getBaseDocByCondition(map));
	}
	
	public int countBaseDocByCondition(Map map){
		return uvFlowMapper.countBaseDocByCondition(map);
	}
	
	@Override
	public List<Map> getDoneCaseType(Map map){
		return MapUtil.turnKeyToLowerCaseOfList(uvFlowMapper.getDoneCaseType(map));
	}
	
}
