package com.sinog2c.dao.api.courtjoint;

import java.util.List;
import java.util.Map;

import com.sinog2c.model.courtjoint.TbdaraPrisoner;

public interface TbdaraPrisonerMapper {
    int insert(TbdaraPrisoner record);

    int insertSelective(TbdaraPrisoner record);

	int countByCrimidAndBatchid(Map<String, Object> map);

	List<Map<String, Object>> findByCrimidAndBatchid(Map<String, Object> map);

	void change(Map tbmap);

	void deleteByBatchidAndCrimid(Map map);
}