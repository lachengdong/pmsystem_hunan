package com.sinog2c.dao.api.chat;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.sinog2c.model.chat.iMessage;

public interface iMessageMapper {
    int deleteByPrimaryKey(Long id);

    int insert(iMessage record);

    int insertSelective(iMessage record);

    iMessage selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(iMessage record);

    int updateByPrimaryKey(iMessage record);
    
    int updateReadflagByUid(iMessage record);
    
    List<iMessage> getUserUnreadMsg(@Param("createUser") String createUser, @Param("toUid") String toUid);
}