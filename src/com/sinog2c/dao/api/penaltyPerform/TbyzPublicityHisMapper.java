package com.sinog2c.dao.api.penaltyPerform;

import com.sinog2c.model.penaltyPerform.TbyzPublicityHis;

public interface TbyzPublicityHisMapper {
    int insert(TbyzPublicityHis record);
    /**
	 * 新增保存
	 */
    int insertSelective(TbyzPublicityHis pubhis);
}