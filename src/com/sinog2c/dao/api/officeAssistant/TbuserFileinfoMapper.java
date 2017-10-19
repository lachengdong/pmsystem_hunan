package com.sinog2c.dao.api.officeAssistant;

import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Component;
import com.sinog2c.model.officeAssistant.TbuserFileinfo;

/**
 * 文件操作
 * 
 * @author wangxf 2014-7-9 15:32:34
 */
@Component("tbuserFileinfoMapper")
public interface TbuserFileinfoMapper {
	/**
	 * 新增文件、文件夹
	 * 
	 * @author wangxf 2014-7-9 15:32:34
	 */
	int addfile(TbuserFileinfo fileInfo);

	/**
	 * 新增文件、文件夹（非空字段）
	 * 
	 * @author wangxf 2014-7-9 15:32:34
	 */
	int insertSelective(TbuserFileinfo record);

	/**
	 * 删除文件
	 * 
	 * @author wangxf 2014-7-9 15:32:34
	 */
	int deleteFile(List idList);
	/**
	 * 查询所有的文件
	 * 
	 */
	List<Map> selectFileName(List idlList);
    /**
     * 修改删除状态
     */
    int updateFileIeaf(String fileid);
	/**
	 * 修改文件
	 * 
	 * @author wangxf 2014-7-9 15:32:34
	 */
	int updateFile(int fileid, String virtualname, String remark);

	/**
	 * 查询共享文件夹和当前用户的个人文件夹
	 * 
	 * @author wangxf 2014-7-9 15:32:34
	 */
	List<Map> selectTree(Map map);

	/**
	 * 查询选中文件夹下的文件
	 * 
	 * @author wangxf 2014-7-9 15:32:34
	 */
	List<Map> selectData(int fileId, String type);
    /**
     * 方法描述:根据文件夹查询出文件夹中的内容
     */
	List<Map> selectFileData(Map map);
	
	int selectFileDataCount(Map map);
	/**
	 * 根据文件id查询文件详细信息
	 * 
	 * @author wangxf 2014-7-9 15:32:34
	 */
	TbuserFileinfo getfileInfo(String id);
	
	/**
	 * 方法描述 ：根据id查询出对应的文件夹信息
	 * @version 2014年10月23日11:50:45
	 */
	List<Map> selectFolderByFolderId(Map map);
	
	List<Map> judgeIsharedByFileid(Map map);
}