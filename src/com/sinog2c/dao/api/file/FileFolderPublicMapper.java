package com.sinog2c.dao.api.file;

import java.util.List;

import com.sinog2c.model.file.FileFolderPublic;

public interface FileFolderPublicMapper {
	int deleteByPrimaryKey(Long id);

	int insert(FileFolderPublic record);

	int insertSelective(FileFolderPublic record);

	FileFolderPublic selectByPrimaryKey(Long id);

	int updateByPrimaryKeySelective(FileFolderPublic record);

	int updateByPrimaryKey(FileFolderPublic record);

	List<FileFolderPublic> getfolderbyUserid(String userid);

	long getNextId();
}