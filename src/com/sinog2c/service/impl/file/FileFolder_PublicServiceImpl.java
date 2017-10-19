package com.sinog2c.service.impl.file;

import java.io.File;
import java.util.List;

import org.apache.tools.ant.util.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gkzx.common.OAParameter;
import com.sinog2c.model.attachment.Attachment;
import com.sinog2c.model.attachment.Attachment_item2;
import com.sinog2c.model.file.FileFolderPublic;
import com.sinog2c.service.api.file.FileFolder_PulicService;
import com.sinog2c.dao.api.attachment.AttachmentMapper;
import com.sinog2c.dao.api.attachment.Attachment_itemMapper;
import com.sinog2c.dao.api.file.FileFolderPublicMapper;

@Service("fileFolder_PulicService")
public class FileFolder_PublicServiceImpl implements FileFolder_PulicService {

	@Autowired
	private FileFolderPublicMapper FileFolderPublicMapper;
	
	@Autowired
	private AttachmentMapper attachmentMapper;
	@Autowired
	private Attachment_itemMapper attachment_itemMapper;
	
	@Override
	public List<FileFolderPublic> getfolderbyUserid(String userid) {
		return this.FileFolderPublicMapper.getfolderbyUserid(userid);
	}

	@Override
	@Transactional
	public List<FileFolderPublic> getfolderbyUseridWithNew(String userid) {
		List<FileFolderPublic> list = this.FileFolderPublicMapper.getfolderbyUserid(userid);
		if (list.size() < 1) {			
			FileFolderPublic folder = new FileFolderPublic();
			folder.setCreateUser(userid);
			folder.setFolderName("根目录");
			folder.setFolderOrder(0L);		
			folder.setParentId(0L);			
			folder=this.add(folder);			
			list.add(folder);
		}
		return list;
	}

	@Override
	@Transactional
	public FileFolderPublic Add(FileFolderPublic folder) {
		return this.add(folder);
	}	

	@Override
	public int Remove(Long folderId) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int update(FileFolderPublic folder) {
		return this.FileFolderPublicMapper.updateByPrimaryKeySelective(folder);
	}

	
	/**
	 * 新增公共文件柜目录以及附件目录
	 * @param folder
	 * @return
	 */
	private FileFolderPublic add(FileFolderPublic folder) {
		Long id = this.FileFolderPublicMapper.getNextId();
		folder.setId(id);
		this.FileFolderPublicMapper.insertSelective(folder);
		Attachment attachment = new Attachment();
		String[] config = OAParameter.attachmentsConfig.get("publicFolder");
		attachment.setModel(config[1]);
		attachment.setAttribute(config[3]);
		attachment.setModule(config[0]);
		attachment.setPath(String.format("%s", config[2]));
		attachment.setPk(id);
		this.attachmentMapper.insertSelective(attachment);
		return folder;
	}

	//start modify by bule_lv 2015-10-16
	@Override
	@Transactional
	public int delete(Long id, String model, String path) {
		List<Attachment_item2> attachments = this.attachment_itemMapper
				.getattachmentlistBymodel(id, model);	
		this.FileFolderPublicMapper.deleteByPrimaryKey(id);	
		if(attachments.size()<1)
		{
			return 0;
		}
		for (Attachment_item2 item : attachments) {			
			String tempth = String.format("%s\\%s\\%s\\%s",path, item.getModule(),
					item.getPath(), item.getFileId());
			FileUtils.delete(new File(tempth));	
		}		
			
		this.attachmentMapper.deleteByPrimaryKey(attachments.get(0).getAttachmentId());
		this.attachment_itemMapper.deletefromAttachmentitemByAttachid(attachments.get(0).getAttachmentId());
		return 1;
	}
	//end modify by bule_lv 2015-10-16
}
