package com.sinog2c.service.api.attachment;

import java.util.List;
import java.util.Map;

import com.sinog2c.model.JSONMessage;
import com.sinog2c.model.attachment.Attachment;
import com.sinog2c.model.attachment.Attachment_item;
import com.sinog2c.model.attachment.Attachment_item2;

public interface AttachmentService {

	/**
	 * 根据模块以及外键id查询附件信息
	 * 
	 * @param id
	 * @param model
	 * @param realPath
	 * @return
	 * @throws Exception
	 */
	List<Attachment_item2> getattachmentlistBymodel(long id, String model,
			String realPath) throws Exception;

	/**
	 * 更加文件名和attachment id获取attachment信息
	 * 
	 * @param id
	 * @param fileName
	 * @return
	 */
	Attachment_item getAttachmentBypkandFilename(long id, String model,
			String fileName);

	/**
	 * 新增附件
	 * 
	 * @param attachment
	 * @return
	 */
	int addAttachment(Attachment attachment);

	/**
	 * 获取附件明细信息列表
	 * 
	 * @param map
	 * @param realPath
	 * @return
	 * @throws Exception
	 */
	JSONMessage<Attachment_item2> getBymodelwithpagination(
			Map<String, Object> map, String realPath) throws Exception;

	/**
	 * 修改附明细表
	 * 
	 * @param item
	 * @return
	 */
	int updateByPrimaryKeySelective(Attachment_item item);

	/**
	 * 删除附件明细表
	 * 
	 * @param id
	 * @return
	 */
	int deleteByPrimaryKey(Long id);

	/**
	 * 批量删除附件明细信息
	 * 
	 * @param list
	 * @return
	 */
	int batchRemove(List<Attachment_item2> list);
	
	/**
	 * 根据model和pk删除附件信息
	 * @param pk
	 * @param model
	 * @param ppath：父路径
	 * @return
	 */
	int deletebyModelandPK(Long pk, String model, String ppath);

}
