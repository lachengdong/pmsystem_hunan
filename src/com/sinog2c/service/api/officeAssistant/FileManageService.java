package com.sinog2c.service.api.officeAssistant;

import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import com.sinog2c.model.officeAssistant.TbuserFileinfo;

/**
 * 个人文件夹
 * @author wangxf
 *
 */
public interface FileManageService {

	/**
	 * 查询共享文件夹和当前用户的个人文件夹
	 * @param filetype
	 * @return
	 */
	public List<Map> selectTree(Map map);
	/**
	 * 根据文件id查询文件详细信息
	 * @param id
	 * @return
	 */
	public TbuserFileinfo getfileInfo(String id);
	/**
	 * 查询选中文件夹下的文件
	 * @param fileId
	 * @param type
	 * @return
	 */
	public List<Map> selectData(int fileId, String type);
	/**
	 * 方法描述：查询出对应的文件夹内容进行分页
	 * @version 2014年10月24日08:58:33
	 */
	public List<Map> selectFileData(Map map);
	/**
	 * 方法描述：查询出文件夹中所有的数据
	 * @version 2014年10月24日09:14:34
	 */
	public int selectFileDataCount(Map map);
	/**
	 *  删除文件
	 * @param id
	 * @return
	 */
	public int deleteFile(List idlist);
	/**
	 * 查询出所有对应的文件
	 */
	public List<Map> selectFileName(List idList);
	/**
	 * 方法 描述：删除所有的文件修改状态
	 * version:2014年10月23日17:35:02
	 */
	
	public int updateFileIeaf(String fileid);
	/**
	 * 新增文件、文件夹（非空字段）
	 * @param record
	 * @return
	 */
	public int insertSelective(TbuserFileinfo record);
	/**
	 * 修改文件
	 * @param fileid
	 * @param virtualname
	 * @param remark
	 * @return
	 */
	public int updateFile(int fileid, String virtualname, String remark);
	/**
	 * 新增文件、文件夹
	 * @param fileInfo
	 * @return
	 */
	public int  addfile(TbuserFileinfo fileInfo);
	/**
	 * 方法描述：通过id找对应的文件夹内容
	 * version 2014年10月23日11:32:47
	 */
	public List<Map> selectFolderByFolderId(HttpServletRequest request);
	
	public List<Map> judgeIsharedByFileid(HttpServletRequest request);
}
