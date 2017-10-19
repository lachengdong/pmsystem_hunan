package com.sinog2c.dao.api.courtjoint;

import java.util.List;
import java.util.Map;

import com.sinog2c.model.courtjoint.TbxfBackReplenish;

public interface TbxfBackReplenishMapper {
    int insert(TbxfBackReplenish record);

    int insertSelective(TbxfBackReplenish record);

	int insertMap(Map bmap);

	int countByCrimidAndBatchid(Map<String, Object> map);

	List<Map<String, Object>> findByCrimidAndBatchid(Map<String, Object> map);
}