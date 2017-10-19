package com.sinog2c.dao.api.system;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.sinog2c.model.system.TbsysQueryplansql;
/**
 * 查询方案SQL表的相关数据库操作
 * 
 * @author liuxin 2014-7-22 14:19:28
 */
public interface TbsysQueryplansqlMapper {
	/**
	 * 删除数据
	 * 
	 * @author liuxin 2014-7-22 14:19:28
	 */
    int deleteByPrimaryKey(Integer sqlid);
    /**
	 * 新增数据
	 * 
	 * @author liuxin 2014-7-22 14:19:28
	 */
    int insert(TbsysQueryplansql record);
    /**
	 * 新增数据
	 * 
	 * @author liuxin 2014-7-22 14:19:28
	 */
    int insertSelective(TbsysQueryplansql record);
    /**
	 * 根据主键查询数据
	 * 
	 * @author liuxin 2014-7-22 14:19:28
	 */
    TbsysQueryplansql selectByPrimaryKey(Integer sqlid);
    /**
	 * 修改数据
	 * 
	 * @author liuxin 2014-7-22 14:19:28
	 */
    int updateByPrimaryKeySelective(TbsysQueryplansql record);
    /**
	 * 修改数据
	 * 
	 * @author liuxin 2014-7-22 14:19:28
	 */
    int updateByPrimaryKey(TbsysQueryplansql record);
    /**
	 * 根据查询方案信息id查找查询方案SQL
	 * 
	 * @author liuxin 2014-7-23 12:24:00
	 */
	TbsysQueryplansql selectByPlanId(Integer planid);
	/**
	 * 保存查询方案SQL
	 * 
	 * @author liuxin 2014-7-22 15:12:2
	 */
	int insertQueryplansql(TbsysQueryplansql queryplansql);
	/**
	 * 更新查询方案SQL
	 * 
	 * @author liuxin 2014-7-22 15:12:2
	 */
	int updateQueryplansql(TbsysQueryplansql queryplansql);
	/**
	 * 删除查询方案SQL
	 * 
	 * @author liuxin 2014-7-23 16:41:22
	 */
	int delQuerySchemesql(TbsysQueryplansql queryplansql);
	/**
	 * 查找查询方案SQL
	 * 
	 * @author liuxin 2014-7-23 12:24:00
	 */
	List<TbsysQueryplansql> selectSqlListByPlanId(HashMap map);
	
	List<TbsysQueryplansql> getScreeningSchemeSqlListByPlanId(Map<String,Object> map);
	List<Map> getQuerySqlForWordByPlanId(@Param("planid")int planid);
}