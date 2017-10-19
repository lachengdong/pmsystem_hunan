package com.sinog2c.service.api.dbmsnew;

import javax.servlet.http.HttpServletRequest;

import com.sinog2c.model.dbmsnew.DbmsFileCopy;

public interface DbmsFileCopyService {

    int deleteByPrimaryKey(String sdid);

    int insert(DbmsFileCopy record);

    int insertSelective(DbmsFileCopy record);

    DbmsFileCopy selectByPrimaryKey(String sdid);

    int updateByPrimaryKeySelective(DbmsFileCopy record);

    int updateByPrimaryKey(DbmsFileCopy record);
    
    void archivesWatch(HttpServletRequest request);
    
    void filesTransfer();
}
