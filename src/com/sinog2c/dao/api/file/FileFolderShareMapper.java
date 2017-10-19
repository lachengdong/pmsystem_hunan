package com.sinog2c.dao.api.file;

import java.util.List;

import com.sinog2c.model.file.FileFolderShare;

public interface FileFolderShareMapper {
    int deleteByPrimaryKey(Long id);

    int insert(FileFolderShare record);

    int insertSelective(FileFolderShare record);
    
    int deleteByfolderid(Long folderId);

    FileFolderShare selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(FileFolderShare record);

    int updateByPrimaryKey(FileFolderShare record);
    
    List<FileFolderShare> getFolderShareInfosByFolderID(Long folderID);
    
    List<FileFolderShare> getSharedFolderByUserId(String iuid);
    
}