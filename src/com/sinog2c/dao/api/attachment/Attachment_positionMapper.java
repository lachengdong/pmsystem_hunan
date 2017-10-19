package com.sinog2c.dao.api.attachment;

import com.sinog2c.model.attachment.Attachment_position;


public interface Attachment_positionMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Attachment_position record);

    int insertSelective(Attachment_position record);

    Attachment_position selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Attachment_position record);

    int updateByPrimaryKey(Attachment_position record);
}