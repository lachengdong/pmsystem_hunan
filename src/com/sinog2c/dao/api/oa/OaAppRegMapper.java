package com.sinog2c.dao.api.oa;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.sinog2c.model.oa.OaAppReg;

public interface OaAppRegMapper {
    int insert(OaAppReg record);

    int insertSelective(OaAppReg record);

	@SuppressWarnings("rawtypes")
	List getAppRegListByCondition(Map<String, Object> map);

	OaAppReg  getAppRegInfoByUuid(@Param("uuid")String uuid);

	int getAppRegCount(Map<String, Object> paramap);

	int deleteAppReg(@Param("uuid")String uuid);

	int updateAppReg(OaAppReg oaappreg);
}