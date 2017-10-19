package com.sinog2c.service.impl.attachment;

import java.io.File;
import java.util.List;
import java.util.Map;

import org.apache.tools.ant.util.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sinog2c.dao.api.attachment.AttachmentMapper;
import com.sinog2c.dao.api.attachment.Attachment_itemMapper;
import com.sinog2c.model.JSONMessage;
import com.sinog2c.model.attachment.Attachment;
import com.sinog2c.model.attachment.Attachment_item;
import com.sinog2c.model.attachment.Attachment_item2;
import com.sinog2c.mvc.controller.file.UploadFileUtils;
import com.sinog2c.service.api.attachment.AttachmentService;
import com.sinog2c.service.impl.base.ServiceImplBase;

@Service("attachmentService")
public class AttachmentServiceImp extends ServiceImplBase implements
		AttachmentService {

	private String[] file_extensions = { "doc,docx,dot;file_extension_doc.png",
			"xls,xlsx;file_extension_xls.png" };
	private String canReadfiles = "|doc,ppt,xls,wps,pdf,html,htm,txt,aip";
	private String canEditfiles = "|doc,xls,wps,aip";

	@Autowired
	private Attachment_itemMapper attachment_itemMapper;

	@Autowired
	private AttachmentMapper attachmentMapper;

	@Override
	public List<Attachment_item2> getattachmentlistBymodel(long id,
			String model, String realPath) throws Exception {
		List<Attachment_item2> attachments = this.attachment_itemMapper
				.getattachmentlistBymodel(id, model);
		this.doSetItemAction(attachments, realPath, 0);
		return attachments;
	}

	@Override
	public Attachment_item getAttachmentBypkandFilename(long id, String model,
			String fileName) {
		return this.attachment_itemMapper.getattachmentbypkandfilename(id,
				model, fileName);
	}

	@Override
	public int addAttachment(Attachment attachment) {
		int result = 0;

		Attachment temp = this.attachmentMapper.getattachmentBymodelandpk(
				attachment.getPk(), attachment.getModel());
		long attachID = 0L;
		if (temp == null) {
			attachID = this.attachmentMapper.getNextId();
			attachment.setId(attachID);
			result += this.attachmentMapper.insertSelective(attachment);
		} else {
			attachID = temp.getId();
		}
		for (Attachment_item item : attachment.getList()) {
			item.setId(null);
			item.setAttachmentId(attachID);
			result += this.attachment_itemMapper.insertSelective(item);
		}
		return result;
	}

	@Override
	public JSONMessage<Attachment_item2> getBymodelwithpagination(
			Map<String, Object> map, String realPath) throws Exception {
		int rowCount = Integer.parseInt(map.get("total").toString());
		int isEditable=1;
		if(map.containsKey("isEditable"))
		{
			isEditable = Integer.parseInt(map.get("isEditable").toString());			
		}

		if (rowCount < 0) {
			rowCount = this.attachment_itemMapper.getcountofattachment(map);
		}
		List<Attachment_item2> attachments = this.attachment_itemMapper
				.getBymodelwithpagination(map);
		this.doSetItemAction(attachments, realPath, isEditable);
		return new JSONMessage<Attachment_item2>(attachments, rowCount);
	}

	@Override
	public int updateByPrimaryKeySelective(Attachment_item item) {
		return this.attachment_itemMapper.updateByPrimaryKeySelective(item);
	}

	@Override
	public int deleteByPrimaryKey(Long id) {
		return this.attachment_itemMapper.deleteByPrimaryKey(id);
	}

	/**
	 * 设置可以对附件的操作，编辑、阅读等
	 * 
	 * @param attachments
	 * @param realPath
	 * @param isEditable
	 * @throws Exception
	 */
	private void doSetItemAction(List<Attachment_item2> attachments,
			String realPath, int isEditable) throws Exception {
		for (Attachment_item2 item : attachments) {
			item.setThumbnail(this.getFile_extensionImgName(
					item.getFileExtName(), realPath));
			String tempth = String.format("%s\\%s\\%s", item.getModule(),
					item.getPath(), item.getFileId());
			String code = String.format("%s&%s&%s", item.getFileName(),
					item.getFileContentype(), tempth);
			// code：文件真实名&文件contentype&文件相对path
			// attachment/fileDownLoad/code/{code}
			code = new sun.misc.BASE64Encoder().encode(code.getBytes("utf-8"));
			// code=code.replace("/", "******");
			item.setDownhref(String.format(
					"attachment/code/%s/fileDownLoad.action", code));
			String extension = item.getFileExtName();
			extension = extension.toLowerCase().replaceAll("docx", "doc")
					.replace("xlsx", "xls").replace("pptx", "ppt");
			item.setFileExtName(extension);
			item.setCanReade(this.canReadfiles.indexOf(extension));

			if (isEditable == 1) {
				item.setCanEdit(this.canEditfiles.indexOf(extension));
			}

			item.setStrSize(this.getFileSize((double) item.getFileSize(), 0));
			item.setCode(code);
		}
	}

	@Override
	@Transactional
	public int batchRemove(List<Attachment_item2> list) {
		int result = 0;
		for (Attachment_item2 item : list) {
			FileUtils.delete(new File(item.getFullPath()));
			this.attachment_itemMapper.deleteByPrimaryKey(item.getId());
		}
		return result;
	}

	private String[] unitName = { "B", "KB", "MB", "GB", "TB" };

	/**
	 * 获取附件的大小（字符串格式）
	 * 
	 * @param size
	 * @param index
	 * @return
	 */
	private String getFileSize(double size, int index) {
		if (size < 1024) {
			return String.format("%.2f%s", size, this.unitName[index]);
		} else {
			index = index + 1;
			return getFileSize(size / 1024, index);
		}
	}

	/**
	 * 获取附件的缩略图
	 * 
	 * @param extension
	 * @param realPath
	 * @return
	 */
	private String getFile_extensionImgName(String extension, String realPath) {
		String imgName = "default.png";
		if (extension == null)
			return String.format("images/file_extension/%s", imgName);
		extension = extension.toLowerCase();
		for (String fileEx : file_extensions) {
			String[] temp = fileEx.split(";");
			if (temp[0].indexOf(extension) > -1) {
				imgName = temp[1];
				return String.format("images/file_extension/%s", imgName);
			}
		}
		String fileName = String.format(
				"images/file_extension/file_extension_%s.png", extension);
		if (UploadFileUtils.isFileExist(fileName, realPath)) {
			return fileName;
		}
		return String.format("images/file_extension/%s", imgName);
	}

	@Override
	@Transactional
	public int deletebyModelandPK(Long pk, String model, String ppath) {
		List<Attachment_item2> attachments = this.attachment_itemMapper
				.getattachmentlistBymodel(pk, model);	
		if(attachments.size()<1)
		{
			return 0;
		}
		for (Attachment_item2 item : attachments) {			
			String tempth = String.format("%s\\%s\\%s\\%s",ppath, item.getModule(),
					item.getPath(), item.getFileId());
			FileUtils.delete(new File(tempth));	
		}		
		this.attachmentMapper.deleteByPrimaryKey(attachments.get(0).getAttachmentId());
		this.attachment_itemMapper.deletefromAttachmentitemByAttachid(attachments.get(0).getAttachmentId());
		return 1;
	}

	

}
