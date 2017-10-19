package com.sinog2c.dao.api.file;

import java.util.List;

import com.sinog2c.model.file.FileFolderPrivate;

public interface FileFolderPrivateMapper {
    int deleteByPrimaryKey(Long id);

    int insert(FileFolderPrivate record);

    int insertSelective(FileFolderPrivate record);

    FileFolderPrivate selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(FileFolderPrivate record);

    int updateByPrimaryKey(FileFolderPrivate record);
    
    List<FileFolderPrivate> getfolderbyUserid(String userid);
    
    List<FileFolderPrivate> getSharedFolderByUserId(String userid);
    
    long getNextId();
}