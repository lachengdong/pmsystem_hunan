package com.sinog2c.dao.api.dbmsnew;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.sinog2c.model.dbmsnew.DbmsDaochuListInfo;

@Repository
public interface DbmsDaochuListInfoMapper {
	//查找解压的文件数量
	int countUnZipDbmsDaochuListInfoData();
	//查找解压的文件
	 List<Map<String,Object>> getUnZipDbmsDaochuListInfoData(Map<String,Object> map);
	//查找未解压的文件数量
	int countZipDbmsDaochuListInfoData();
	//查找未解压的文件
	 List<Map<String,Object>> getZipDbmsDaochuListInfoData(Map<String,Object> map);
	//根据文件名查找实体信息
	DbmsDaochuListInfo selectByFileRealName(String filerealname);
    int deleteByPrimaryKey(String fileid);

    int insert(DbmsDaochuListInfo record);

    int insertSelective(DbmsDaochuListInfo record);

    DbmsDaochuListInfo selectByPrimaryKey(String fileid);

    int updateByPrimaryKeySelective(DbmsDaochuListInfo record);

    int updateByPrimaryKey(DbmsDaochuListInfo record);

	/**
	 * 获取数据列表
	 * @return
	 */
	List<Map<String, Object>> listMapByPage(Map<String, Object> map);
	
	// 统计所有
	public int countAll();
	// 获取所有 list
	public List<DbmsDaochuListInfo> listAll();
	// 根据条件统计
	public int countByCondition(Map<String, Object> map);
	// 根据条件获取数据
	public List<DbmsDaochuListInfo> listByCondition(Map<String, Object> map);
	// 根据Map更新
	public int updateByMap(Map<String, Object> map);
	// 根据Map插入
	public int insertByMap(Map<String, Object> map);
}