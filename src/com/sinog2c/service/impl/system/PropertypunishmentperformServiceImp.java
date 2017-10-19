package com.sinog2c.service.impl.system;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sinog2c.dao.api.system.PropertypunishmentperformMapper;
import com.sinog2c.model.system.Propertypunishmentperform;
import com.sinog2c.service.api.system.PropertypunishmentperformService;
@Service
public class PropertypunishmentperformServiceImp implements
		PropertypunishmentperformService {
	@Autowired
	private PropertypunishmentperformMapper propertypunishmentperformMapper;
	@Override
	public List findByProperty(Map map) {
		return propertypunishmentperformMapper.findByProperty(map);
	}
	@Override
	public int findByPropertyCount(Map map) {
		return propertypunishmentperformMapper.findByPropertyCount(map);
	}
	@Override
	public List findByPropertyDetail(Map map) {
		return propertypunishmentperformMapper.findByPropertyDetail(map);
	}
	@Override
	public int findByPropertyDetailCount(Map map) {
		return propertypunishmentperformMapper.findByPropertyDetailCount(map);
	}
	@Override
	public int insert(Propertypunishmentperform record) {
		return propertypunishmentperformMapper.insert(record);
	}
	@Override
	public int findMaxid() {
		return propertypunishmentperformMapper.findMaxid();
	}
	@Override
	public int updateByPrimaryKey(Propertypunishmentperform propertypunishmentperform) {
		return propertypunishmentperformMapper.updateByPrimaryKey(propertypunishmentperform);
	}
	@Override
	public int deleteByPrimaryKey(String propertyid) {
		return propertypunishmentperformMapper.deleteByPrimaryKey(propertyid);
	}
	@Override
	public String findSumPro(Map map) {
		return propertypunishmentperformMapper.findSumPro(map);
	}

}
