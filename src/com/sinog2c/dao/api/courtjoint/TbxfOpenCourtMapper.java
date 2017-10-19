package com.sinog2c.dao.api.courtjoint;

import java.util.List;
import java.util.Map;

import com.sinog2c.model.courtjoint.TbxfOpenCourt;

public interface TbxfOpenCourtMapper {
    int insert(TbxfOpenCourt record);

    int insertSelective(TbxfOpenCourt record);

	int insertMap(Map emap);

	int countByCrimidAndBatchid(Map<String, Object> map);

	List<Map<String, Object>> findByCrimidAndBatchid(Map<String, Object> map);
}