package com.sinog2c.dao.api.attachment;

import org.apache.ibatis.annotations.Param;

import com.sinog2c.model.attachment.Attachment;

public interface AttachmentMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Attachment record);

    int insertSelective(Attachment record);

    Attachment selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Attachment record);

    int updateByPrimaryKey(Attachment record);
    
    long getNextId();
    
    Attachment getattachmentBymodelandpk(@Param("pk") long id,@Param("model") String model);
    
}