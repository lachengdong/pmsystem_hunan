package com.sinog2c.dao.api.courtjoint;

import java.util.List;
import java.util.Map;

import com.sinog2c.model.courtjoint.TbxfRuling;

public interface TbxfRulingMapper {
    int insert(TbxfRuling record);

    int insertSelective(TbxfRuling record);

	int insertMap(Map fmap);

	int countByCrimidAndBatchid(Map<String, Object> map);

	List<Map<String, Object>> findByCrimidAndBatchid(Map<String, Object> map);
}