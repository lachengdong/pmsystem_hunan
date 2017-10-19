package com.sinog2c.dao.api.prisoner;

import java.math.BigDecimal;
import java.util.List;

import com.sinog2c.model.prisoner.TbprisonerPrecrime;
/**
 * 犯人以前犯罪纪录的相关数据库操作
 * 
 * @author liuxin 2014-7-24 11:02:32
 */
public interface TbprisonerPrecrimeMapper {
	/**
	 * 根据主键删除数据
	 * 
	 * @author liuxin 2014-7-24 11:02:32
	 */
    int deleteByPrimaryKey(BigDecimal precrimeid);
    /**
	 * 新增数据
	 * 
	 * @author liuxin 2014-7-24 ‏‎11:02:32
	 */
    int insert(TbprisonerPrecrime record);
    /**	
	 * 新增数据（只处理非空字段）
	 * 
	 * @author liuxin 2014-7-24 11:02:32
	 */
    int insertSelective(TbprisonerPrecrime record);
    /**	
	 * 根据主键查询犯人以前犯罪纪录
	 * 
	 * @author liuxin 2014-7-24 ‏‎11:02:32
	 */
    TbprisonerPrecrime selectByPrimaryKey(BigDecimal precrimeid);
    /**	
	 * 修改数据
	 * 
	 * @author liuxin 2014-7-24 ‏‎11:02:32
	 */
    int updateByPrimaryKeySelective(TbprisonerPrecrime record);
    /**	
	 * 修改数据
	 * 
	 * @author liuxin 2014-7-24 ‏‎11:02:32
	 */
    int updateByPrimaryKey(TbprisonerPrecrime record);
    /**	
	 * 根据罪犯编号查询犯人以前犯罪纪录
	 * 
	 * @author liuxin 2014-7-24 ‏‎11:31:14
	 */
    List<TbprisonerPrecrime> selectByCrimid(String crimid);
}