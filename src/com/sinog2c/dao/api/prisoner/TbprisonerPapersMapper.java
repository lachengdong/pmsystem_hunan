package com.sinog2c.dao.api.prisoner;

import java.math.BigDecimal;
import java.util.List;

import com.sinog2c.model.prisoner.TbprisonerPapers;
/**
 * 证件信息表的相关数据库操作
 * 
 * @author liuxin 2014-7-24 ‏‎11:00:33
 */
public interface TbprisonerPapersMapper {
	/**
	 * 根据主键删除数据
	 * 
	 * @author liuxin 2014-7-24 ‏‎11:00:33
	 */
    int deleteByPrimaryKey(BigDecimal paperid);
    /**
	 * 新增数据
	 * 
	 * @author liuxin 2014-7-24 ‏‎11:00:33
	 */
    int insert(TbprisonerPapers record);
    /**	
	 * 新增数据（只处理非空字段）
	 * 
	 * @author liuxin 2014-7-24 ‏‎11:00:33
	 */
    int insertSelective(TbprisonerPapers record);
    /**	
	 * 根据主键查询证件信息
	 * 
	 * @author liuxin 2014-7-24 ‏‎11:00:33
	 */
    TbprisonerPapers selectByPrimaryKey(BigDecimal paperid);
    /**	
	 * 修改数据
	 * 
	 * @author liuxin 2014-7-24 ‏‎11:00:33
	 */
    int updateByPrimaryKeySelective(TbprisonerPapers record);
    /**	
	 * 修改数据
	 * 
	 * @author liuxin 2014-7-24 ‏‎11:00:33
	 */
    int updateByPrimaryKey(TbprisonerPapers record);
    /**	
	 * 根据罪犯编号查询证件信息
	 * 
	 * @author liuxin 2014-7-24 ‏‎11:14:37
	 */
    List<TbprisonerPapers> selectByCrimid(String crimid);
}