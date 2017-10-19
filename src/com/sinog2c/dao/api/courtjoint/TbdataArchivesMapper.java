package com.sinog2c.dao.api.courtjoint;

import java.util.Map;

import com.sinog2c.model.courtjoint.TbdataArchives;

public interface TbdataArchivesMapper {
    int insert(TbdataArchives record);

    int insertSelective(TbdataArchives record);

	String findByCrimidAndArchivesname(Map<String, String> map);
}