package com.sinog2c.dao.api.oa;

import com.sinog2c.model.oa.OaSickLeave;

public interface OaSickLeaveMapper {
    int insert(OaSickLeave record);

    int insertSelective(OaSickLeave record);
}