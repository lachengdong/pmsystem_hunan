package com.sinog2c.dao.api.system;

import java.util.List;
import java.util.Map;

import com.sinog2c.model.system.Tbcourtconnectioninfo;

public interface TbcourtconnectioninfoMapper {
    int deleteByPrimaryKey(Long cid);

    int insert(Tbcourtconnectioninfo record);

    int insertSelective(Tbcourtconnectioninfo record);

    Tbcourtconnectioninfo selectByPrimaryKey(Long cid);

    int updateByPrimaryKeySelective(Tbcourtconnectioninfo record);

    int updateByPrimaryKey(Tbcourtconnectioninfo record);

	int getVideoSessionListCount(Map<String, Object> paramMap);

	List<Map<String, String>> getVideoSessionList(Map<String, Object> paramMap);
	
	int getVideoSessionDepartmentsCount(Map<String, Object> paramMap);

	List<Map<String, String>> getVideoSessionDepartments(
			Map<String, Object> paramMap);

	List<Map<String, String>> getVideoPlayDepartments(
			Map<String, Object> paramMap);




}