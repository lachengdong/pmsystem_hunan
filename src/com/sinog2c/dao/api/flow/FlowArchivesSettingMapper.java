package com.sinog2c.dao.api.flow;

import java.util.List;
import java.util.Map;

import com.sinog2c.model.flow.FlowArchivesSetting;
import com.sinog2c.model.flow.FlowArchivesSettingKey;

public interface FlowArchivesSettingMapper {
    int deleteByPrimaryKey(FlowArchivesSettingKey key);

    int insert(FlowArchivesSetting record);

    int insertSelective(FlowArchivesSetting record);

    FlowArchivesSetting selectByPrimaryKey(FlowArchivesSettingKey key);

    int updateByPrimaryKeySelective(FlowArchivesSetting record);

    int updateByPrimaryKey(FlowArchivesSetting record);
    
    List<FlowArchivesSetting> selectByDepartidAndFileType(Map<String,Object> map);
    
    
}