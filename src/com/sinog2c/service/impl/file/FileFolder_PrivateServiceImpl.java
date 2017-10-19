package com.sinog2c.service.impl.file;

import java.io.File;
import java.util.List;

import org.apache.tools.ant.util.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gkzx.common.OAParameter;
import com.sinog2c.dao.api.attachment.AttachmentMapper;
import com.sinog2c.dao.api.attachment.Attachment_itemMapper;
import com.sinog2c.dao.api.file.FileFolderPrivateMapper;
import com.sinog2c.dao.api.file.FileFolderShareMapper;
import com.sinog2c.model.attachment.Attachment;
import com.sinog2c.model.attachment.Attachment_item2;
import com.sinog2c.model.file.FileFolderPrivate;
import com.sinog2c.model.file.FileFolderShare;
import com.sinog2c.model.system.SystemUser;
import com.sinog2c.service.api.file.FileFolder_PrivateService;
import com.sinog2c.service.impl.base.ServiceImplBase;

@Service("fileFolder_PrivateService")
public class FileFolder_PrivateServiceImpl extends ServiceImplBase implements
		FileFolder_PrivateService {

	@Autowired
	private FileFolderPrivateMapper fileFolderPrivateMapper;

	@Autowired
	private AttachmentMapper attachmentMapper;
	
	@Autowired
	private Attachment_itemMapper attachment_itemMapper;
	
	@Autowired
	private FileFolderShareMapper fileFolderShareMapper;

	@Override
	public List<FileFolderPrivate> getfolderbyUserid(String userid) {
		return this.fileFolderPrivateMapper.getfolderbyUserid(userid);
	}

	@Override
	@Transactional
	public List<FileFolderPrivate> getfolderbyUseridWithNew(String userid) {
		List<FileFolderPrivate> list = this.fileFolderPrivateMapper
				.getfolderbyUserid(userid);
		if (list.size() < 1) {
			Long id = this.fileFolderPrivateMapper.getNextId();
			FileFolderPrivate folder = new FileFolderPrivate();
			folder.setCreateUser(userid);
			folder.setFolderName("根目录");
			folder.setFolderOrder(0L);
			folder.setId(id);
			folder.setParentId(0L);
			this.fileFolderPrivateMapper.insertSelective(folder);

			Attachment attachment = new Attachment();

			String[] config = OAParameter.attachmentsConfig
					.get("privateFolder");
			attachment.setModel(config[1]);
			attachment.setAttribute(config[3]);
			attachment.setModule(config[0]);
			attachment.setPath(String.format("%s/%s", config[2], userid));
			attachment.setPk(id);
			this.attachmentMapper.insertSelective(attachment);
			list.add(folder);
		}
		return list;
	}

	@Override
	@Transactional
	public FileFolderPrivate Add(FileFolderPrivate folder) {
		Long id = this.fileFolderPrivateMapper.getNextId();
		folder.setId(id);
		this.fileFolderPrivateMapper.insertSelective(folder);
		Attachment attachment = new Attachment();
		String[] config = OAParameter.attachmentsConfig.get("privateFolder");
		attachment.setModel(config[1]);
		attachment.setAttribute(config[3]);
		attachment.setModule(config[0]);
		attachment.setPath(String.format("%s/%s", config[2], folder.getCreateUser()));
		attachment.setPk(id);
		this.attachmentMapper.insertSelective(attachment);
		return folder;
	}

	@Override
	public int Remove(Long folderId) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int update(FileFolderPrivate folder) {
		return this.fileFolderPrivateMapper.updateByPrimaryKeySelective(folder);
	}

	@Override
	public List<FileFolderShare> getFolderShareInfosByFolderID(Long folderID) {
		return this.fileFolderShareMapper.getFolderShareInfosByFolderID(folderID);
	}

	@Override
	@Transactional
	public int setsharedfolderInfo(FileFolderShare sharedfolder, SystemUser user) {
		int result=0;
		result=this.fileFolderShareMapper.deleteByfolderid(sharedfolder.getFolderId());
		for(FileFolderShare item: sharedfolder.getItems())
		{
			if(item.getIuid().equalsIgnoreCase(user.getUserid()))continue;
			item.setEndTime(sharedfolder.getEndTime());
			item.setFolderId(sharedfolder.getFolderId());
			item.setStartTime(sharedfolder.getStartTime());			
			result+=this.fileFolderShareMapper.insertSelective(item);			
		}		
		return result;
	}

	@Override
	public int deleteSharedfolderInfo(Long folderId) {
		return this.fileFolderShareMapper.deleteByfolderid(folderId);
	}

	@Override
	public List<FileFolderPrivate> getSharedFolderByUserId(String iuid) {
		return this.fileFolderPrivateMapper.getSharedFolderByUserId(iuid);
	}

	//start modify by bule_lv 2015-10-16
	@Override
	@Transactional
	public int delete(Long id, String model, String path) {
		List<Attachment_item2> attachments = this.attachment_itemMapper
				.getattachmentlistBymodel(id, model);	
		
		this.fileFolderPrivateMapper.deleteByPrimaryKey(id);
		this.fileFolderShareMapper.deleteByfolderid(id);
		
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
