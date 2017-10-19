package com.sinog2c.service.api.dbmsnew;

import java.util.List;
import java.util.Map;

import com.sinog2c.model.dbmsnew.DbmsDaochuListInfo;

public interface DbmsDaochuListInfoService {
	
    int deleteByPrimaryKey(String fileid);
    
    int deleteDataExport(String fileid,String filepath);

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
	//查找未解压的文件数量
	int countZipDbmsDaochuListInfoData();
	//查找未解压的文件
	List<Map<String,Object>> getZipDbmsDaochuListInfoData(Map<String,Object> map);
	//查找解压的文件数量
	int countUnZipDbmsDaochuListInfoData();
	//查找解压的文件
	List<Map<String,Object>> getUnZipDbmsDaochuListInfoData(Map<String,Object> map);
}
