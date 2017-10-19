package com.sinog2c.service.api.file;

import java.util.List;

import com.sinog2c.model.file.FileFolderPrivate;
import com.sinog2c.model.file.FileFolderShare;
import com.sinog2c.model.system.SystemUser;

public interface FileFolder_PrivateService {

	/**
	 * 获取用户个人文件柜目录
	 * 
	 * @param userid
	 * @return
	 */
	List<FileFolderPrivate> getfolderbyUserid(String userid);

	/**
	 * 获取用户个人文件柜目录,如果当前还没有目录，创建根目录
	 * 
	 * @param userid
	 * @return
	 */
	List<FileFolderPrivate> getfolderbyUseridWithNew(String userid);	

	/**
	 * 创建目录
	 * 
	 * @param folder
	 * @return
	 */
	FileFolderPrivate Add(FileFolderPrivate folder);

	/**
	 * 删除目录
	 * 
	 * @param folderId
	 * @return
	 */
	int Remove(Long folderId);	
	int delete(Long id, String model,String path);

	/**
	 * 更新目录
	 * 
	 * @param folder
	 * @return
	 */
	int update(FileFolderPrivate folder);

	/**
	 * 获取个人文件柜指定目录的共享信息
	 * 
	 * @param folderID
	 * @return
	 */
	List<FileFolderShare> getFolderShareInfosByFolderID(Long folderID);

	/**
	 * 设置个人文件柜共享文件夹信息
	 * 
	 * @param sharedfolder
	 * @param user
	 * @return
	 */
	int setsharedfolderInfo(FileFolderShare sharedfolder, SystemUser user);

	/**
	 * 删除共享目录
	 * 
	 * @param folderId
	 * @return
	 */
	int deleteSharedfolderInfo(Long folderId);

	/**
	 * 根据用户ID获取共享文件目录
	 * 
	 * @param iuid
	 * @return
	 */
	List<FileFolderPrivate> getSharedFolderByUserId(String iuid);

}
