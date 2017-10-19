package com.sinog2c.dao.api.khjc;

import java.util.List;

import com.sinog2c.model.khjc.KhjcCode;
import com.sinog2c.model.khjc.KhjcCodeKey;

public interface KhjcCodeMapper {
    int deleteByPrimaryKey(KhjcCodeKey key);

    int insert(KhjcCode record);

    int insertSelective(KhjcCode record);

    KhjcCode selectByPrimaryKey(KhjcCodeKey key);

    int updateByPrimaryKeySelective(KhjcCode record);

    int updateByPrimaryKey(KhjcCode record);

	List selectValueByCodeType(String codeType);
}