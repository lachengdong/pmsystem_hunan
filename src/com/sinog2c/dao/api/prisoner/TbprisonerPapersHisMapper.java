package com.sinog2c.dao.api.prisoner;

import com.sinog2c.model.prisoner.TbprisonerPapersHis;
/**
 * 证件信息表的相关数据库操作
 * 
 * @author liuxin 2014-7-24 ‏‎11:02:00
 */
public interface TbprisonerPapersHisMapper {
	/**
	 * 新增数据
	 * 
	 * @author liuxin 2014-7-24 ‏‎11:02:00
	 */
    int insert(TbprisonerPapersHis record);
    /**	
	 * 新增数据（只处理非空字段）
	 * 
	 * @author liuxin 2014-7-24 ‏‎11:02:00
	 */
    int insertSelective(TbprisonerPapersHis record);
}