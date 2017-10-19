package com.sinog2c.dao.api.flow;

import com.sinog2c.model.flow.FlowBaseArchivesHis;

public interface FlowBaseArchivesHisMapper {
    int insert(FlowBaseArchivesHis record);

    int insertSelective(FlowBaseArchivesHis record);
}