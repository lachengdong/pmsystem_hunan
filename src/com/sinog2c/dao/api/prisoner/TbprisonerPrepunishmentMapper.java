package com.sinog2c.dao.api.prisoner;

import java.util.List;

import com.sinog2c.model.prisoner.TbprisonerPrepunishment;
/**
 * 既往治安处罚信息表的相关数据库操作
 * 
 * @author liuxin 2014-7-24 11:03:46
 */
public interface TbprisonerPrepunishmentMapper {
	/**
	 * 根据主键删除数据
	 * 
	 * @author liuxin 2014-7-24 ‏‎11:03:46
	 */
    int deleteByPrimaryKey(Integer punid);
    /**
	 * 新增数据
	 * 
	 * @author liuxin 2014-7-24 ‏‎11:03:46
	 */
    int insert(TbprisonerPrepunishment record);
    /**	
	 * 新增数据（只处理非空字段）
	 * 
	 * @author liuxin 2014-7-24 11:03:46
	 */
    int insertSelective(TbprisonerPrepunishment record);
    /**	
	 * 根据主键查询既往治安处罚信息
	 * 
	 * @author liuxin 2014-7-24 11:03:46
	 */
    TbprisonerPrepunishment selectByPrimaryKey(Integer punid);
    /**	
	 * 修改数据
	 * 
	 * @author liuxin 2014-7-24 ‏‎11:03:46
	 */
    int updateByPrimaryKeySelective(TbprisonerPrepunishment record);
    /**	
	 * 修改数据
	 * 
	 * @author liuxin 2014-7-24 11:03:46
	 */
    int updateByPrimaryKey(TbprisonerPrepunishment record);
    /**	
	 * 根据罪犯编号既往治安处罚信息
	 * 
	 * @author
	 */
    TbprisonerPrepunishment selectByCrimid(String crimid);
}