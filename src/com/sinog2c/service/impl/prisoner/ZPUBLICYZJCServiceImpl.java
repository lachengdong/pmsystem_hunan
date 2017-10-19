package com.sinog2c.service.impl.prisoner;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sinog2c.dao.api.prisoner.ZPUBLICYZJCMapper;
import com.sinog2c.model.prisoner.ZPUBLICYZJC;
import com.sinog2c.service.api.prisoner.ZPUBLICYZJCService;
@Service("zPUBLICYZJCService")
public class ZPUBLICYZJCServiceImpl implements ZPUBLICYZJCService {
	@Autowired
	private ZPUBLICYZJCMapper zPUBLICYZJCMapper;
	@Override
	public String selectYZJCMaxidByCrimid() {
		return zPUBLICYZJCMapper.selectYZJCMaxidByCrimid();
	}
	@Override
	public int insertSelective(ZPUBLICYZJC zPUBLICYZJC) {
		return zPUBLICYZJCMapper.insertSelective(zPUBLICYZJC);
	}
	@Override
	public List findByYZJCDetail(Map map) {
		return zPUBLICYZJCMapper.findByYZJCDetail(map);
	}
	@Override
	public int findByYZJCDetailCount(Map map) {
		return zPUBLICYZJCMapper.findByYZJCDetailCount(map);
	}
	@Override
	public int updateByPrimaryKey(ZPUBLICYZJC zPUBLICYZJC) {
		return zPUBLICYZJCMapper.updateByPrimaryKey(zPUBLICYZJC);
	}
	@Override
	public int deleteByPrimaryKey(String id) {
		return zPUBLICYZJCMapper.deleteByPrimaryKey(id);
	}
	@Override
	public int findZPUBLICYZJCCount(Map crimid) {
		// TODO Auto-generated method stub
		
		return zPUBLICYZJCMapper.findZPUBLICYZJCCount(crimid);
	}
	@Override
	public List<Map> getZPUBLICYZJClist(Map map) {
		// TODO Auto-generated method stub
	
		return 	zPUBLICYZJCMapper.getZPUBLICYZJClist(map);
	}

}
