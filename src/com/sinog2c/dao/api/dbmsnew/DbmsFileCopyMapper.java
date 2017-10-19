package com.sinog2c.dao.api.dbmsnew;

import com.sinog2c.model.dbmsnew.DbmsFileCopy;

public interface DbmsFileCopyMapper {
    int deleteByPrimaryKey(String sdid);

    int insert(DbmsFileCopy record);

    int insertSelective(DbmsFileCopy record);

    DbmsFileCopy selectByPrimaryKey(String sdid);

    int updateByPrimaryKeySelective(DbmsFileCopy record);

    int updateByPrimaryKey(DbmsFileCopy record);
}