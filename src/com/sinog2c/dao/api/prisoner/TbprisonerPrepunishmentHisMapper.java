package com.sinog2c.dao.api.prisoner;

import java.util.List;

import com.sinog2c.model.prisoner.TbprisonerPrepunishmentHis;

/**
 * 既往治安处罚信息历史
 * @author wangxf
 *
 */
public interface TbprisonerPrepunishmentHisMapper {
	int insert(TbprisonerPrepunishmentHis record);

    int insertSelective(TbprisonerPrepunishmentHis record);
    
    /**
	 * 根据罪犯id既往治安处罚信息历史
	 * @param crimid
	 * @return
	 */
    TbprisonerPrepunishmentHis findBycrimid(String crimid);
}