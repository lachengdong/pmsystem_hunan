package com.sinog2c.service.impl.system;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sinog2c.dao.api.system.TbViewMaintainMapper;
import com.sinog2c.service.api.system.TbViewMaintainService;
import com.sinog2c.service.impl.base.ServiceImplBase;
import com.sinog2c.util.common.MapUtil;

@Service("tbViewMaintainService")
public class TbViewMaintainServiceImpl extends ServiceImplBase implements TbViewMaintainService{

	@Autowired
	private TbViewMaintainMapper tbViewMaintainMapper;
	
	public Map selectByCondition(Map map){
		Map temMap = tbViewMaintainMapper.selectByCondition(map);
		if(null==temMap){
			temMap = new HashMap();
			temMap.put("provinceid", "0");
			temMap.put("flowdefid", "no");
			temMap.put("nodeid", "0");
			temMap.put("days", 0);
		}
		return MapUtil.turnKeyToLowerCase(temMap);
	}
	
	public List<Map> selectByProvinceid(Map map){
		List list = tbViewMaintainMapper.selectByProvinceid(map);
		if(null==list||list.size()==0){
			Map temMap = new HashMap();
			temMap.put("provinceid", "0");
			temMap.put("flowdefid", "no");
			temMap.put("nodeid", "0");
			temMap.put("days", 0);
			list.add(temMap);
		}
		return MapUtil.turnKeyToLowerCaseOfList(list);
	}

}
