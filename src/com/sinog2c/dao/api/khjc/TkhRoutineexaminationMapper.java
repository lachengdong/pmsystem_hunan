package com.sinog2c.dao.api.khjc;

import java.util.List;
import java.util.Map;
import com.sinog2c.model.khjc.TkhRoutineexamination;

public interface TkhRoutineexaminationMapper {
    int deleteByPrimaryKey(Integer routineid);

    int insert(TkhRoutineexamination record);

    int insertSelective(TkhRoutineexamination record);

    TkhRoutineexamination selectByPrimaryKey(Integer routineid);

    int updateByPrimaryKeySelective(TkhRoutineexamination record);

    int updateByPrimaryKey(TkhRoutineexamination record);
    
    int countAllByCondition(Map<String,Object> map);//分数废弃获取罪犯列表信息的条数
    
	List<Map<String,Object>> getBasicInfoList(Map<String,Object> map);//分数废弃获取罪犯列表信息的数据

	int countCondition(Map<String, Object> map);

	List<Map<String, Object>> getInfoList(Map<String, Object> map);

	int getCancelReason(Map<String, Object> map);

	String getCancelReasonView(Map<String, Object> map);
} 