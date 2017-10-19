package com.sinog2c.dao.api.arch;

import java.util.List;
import java.util.Map;

import com.sinog2c.model.arch.ArchBox;

public interface ArchBoxMapper {
	int deleteByPrimaryKey(Long id);

	int insert(ArchBox record);

	int insertSelective(ArchBox record);

	ArchBox selectByPrimaryKey(Long id);

	int updateByPrimaryKeySelective(ArchBox record);

	int updateByPrimaryKey(ArchBox record);

	int getcountofarchboxbycondition(Map<String, Object> map);

	List<ArchBox> getarchboxbycondition(Map<String, Object> map);

	List<ArchBox> getAllArchBoxInfos();
}