package com.sinog2c.dao.api.common;
import java.util.Map;
import com.sinog2c.model.common.MeetingRecordFlow;
import com.sinog2c.model.common.MeetingRecordFlowKey;

public interface MeetingRecordFlowMapper {
    int deleteByPrimaryKey(MeetingRecordFlowKey key);

    int insert(MeetingRecordFlow record);

    int insertSelective(MeetingRecordFlow record);

    MeetingRecordFlow selectByPrimaryKey(MeetingRecordFlowKey key);

    int updateByPrimaryKeySelective(MeetingRecordFlow record);

    int updateByPrimaryKey(MeetingRecordFlow record);
    
    /**
     * add yzglybb 20150907
     */
    int addYZGLDataMain(Map MainData);
    int addYZGLDataChild(Map ChildData);
    int updateYZGLDataMain(Map ChildData);
    int deleteOldYZGLDataChild(Map MainData);
    
}