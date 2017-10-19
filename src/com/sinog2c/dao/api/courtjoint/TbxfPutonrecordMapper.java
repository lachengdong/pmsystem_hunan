package com.sinog2c.dao.api.courtjoint;

import java.util.List;
import java.util.Map;

import com.sinog2c.model.courtjoint.TbxfPutonrecord;

public interface TbxfPutonrecordMapper {
    int insert(TbxfPutonrecord record);

    int insertSelective(TbxfPutonrecord record);

	int insertMap(Map dmap);

	int countByCrimidAndBatchid(Map<String, Object> map);

	List<Map<String, Object>> findByCrimidAndBatchid(Map<String, Object> map);
}