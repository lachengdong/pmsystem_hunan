package com.sinog2c.service.api.file;

import java.util.List;

import com.sinog2c.model.file.FileFolderPublic;

public interface FileFolder_PulicService {
	
	/**
	 * 获取公共文件柜目录
	 * 
	 * @param userid：登录用户id
	 * @return
	 */
	List<FileFolderPublic> getfolderbyUserid(String userid);

	/**
	 * 获取公共文件柜目录，如果没有跟目录则创建它
	 * 
	 * @param userid
	 * @return
	 */
	List<FileFolderPublic> getfolderbyUseridWithNew(String userid);	

	/**
	 * 创建公共文件柜目录
	 * 
	 * @param folder
	 * @return
	 */
	FileFolderPublic Add(FileFolderPublic folder);

	/**
	 * 删除公共文件柜目录目录
	 * 
	 * @param folderId
	 * @return
	 */
	int Remove(Long folderId);
	int delete(Long id, String model,String path);

	/**
	 * 更新公共文件柜目录
	 * 
	 * @param folder
	 * @return
	 */
	int update(FileFolderPublic folder);

}
