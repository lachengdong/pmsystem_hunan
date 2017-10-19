package com.sinog2c.dao.api.attachment;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.sinog2c.model.attachment.Attachment_item;
import com.sinog2c.model.attachment.Attachment_item2;


public interface Attachment_itemMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Attachment_item record);

    int insertSelective(Attachment_item record);

    Attachment_item selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Attachment_item record);

    int updateByPrimaryKey(Attachment_item record);
    
    /**
     * 根据模块名、模块实例id查询附件
     * @param id
     * @param model
     * @return
     */
    List<Attachment_item2> getattachmentlistBymodel(@Param("id") long id,@Param("model") String model);
    
    Attachment_item getattachmentbypkandfilename(@Param("id") long id,@Param("model") String model,@Param("fileName") String fileName);
    
    /**
     * 根据模块名、模块实例id查询附件（分页）
     * @param map
     * @return
     */
    List<Attachment_item2> getBymodelwithpagination(Map<String, Object> map);    
    int getcountofattachment(Map<String, Object> map);
    
    /**
     * 根据附件id（主表id）删除附件明细信息
     * @param attachid
     * @return
     */
    int deletefromAttachmentitemByAttachid(Long attachid);
    
}