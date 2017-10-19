package com.sinog2c.dao.api.prisoner;

import com.sinog2c.model.prisoner.TbprisonerPrecrimeHis;
/**
 * 犯人以前犯罪纪录的相关数据库操作
 * 
 * @author liuxin 2014-7-24 11:03:03
 */
public interface TbprisonerPrecrimeHisMapper {
	/**
	 * 新增数据
	 * 
	 * @author liuxin 2014-7-24 ‏‎11:03:03
	 */
    int insert(TbprisonerPrecrimeHis record);
    /**	
	 * 新增数据（只处理非空字段）
	 * 
	 * @author liuxin 2014-7-24 11:03:03
	 */
    int insertSelective(TbprisonerPrecrimeHis record);
}