package com.sinog2c.service.impl.arch;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sinog2c.dao.api.arch.ArchBoxMapper;
import com.sinog2c.dao.api.arch.ArchFileMapper;
import com.sinog2c.dao.api.arch.ArchTreeMapper;
import com.sinog2c.dao.api.attachment.AttachmentMapper;
import com.sinog2c.dao.api.attachment.Attachment_itemMapper;
import com.sinog2c.model.JSONMessage;
import com.sinog2c.model.arch.ArchBox;
import com.sinog2c.model.arch.ArchFile;
import com.sinog2c.model.arch.ArchTree;
import com.sinog2c.model.attachment.Attachment;
import com.sinog2c.model.attachment.Attachment_item;
import com.sinog2c.service.api.arch.ArchTreeService;

@Service("archTreeService")
public class ArchTreeServiceImpl implements ArchTreeService {

	@Autowired
	private ArchTreeMapper archTreeMapper;

	@Autowired
	private ArchBoxMapper archBoxMapper;
	
	@Autowired
	private ArchFileMapper archFileMapper;
	
	@Autowired
	private AttachmentMapper attachmentMapper;

	@Autowired
	private Attachment_itemMapper attachment_itemMapper;

	@Override
	public List<ArchTree> getAllArchFolder() {
		return this.archTreeMapper.getAllArchFolder();
	}

	@Override
	public Long add(ArchTree record) {
		Long id = this.archTreeMapper.getNextId();
		record.setId(id);
		this.archTreeMapper.insertSelective(record);
		return id;
	}

	@Override
	public int delete(Long id) {
		return this.archTreeMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int update(ArchTree record) {
		return this.archTreeMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int addArchBox(ArchBox record) {
		return this.archBoxMapper.insertSelective(record);
	}

	@Override
	public JSONMessage<ArchBox> getarchboxbycondition(Map<String, Object> map) {
		int rowCount = Integer.parseInt(map.get("total").toString());

		if (rowCount < 0) {
			rowCount = this.archBoxMapper.getcountofarchboxbycondition(map);
		}
		List<ArchBox> list = this.archBoxMapper.getarchboxbycondition(map);
		return new JSONMessage<ArchBox>(list, rowCount);
	}

	@Override
	public int updateArchBox(ArchBox record) {		
		return this.archBoxMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	@Transactional
	public int deleteArchBox(Long id) {		
		this.archFileMapper.updatearchfilebox(id);		
		return this.archBoxMapper.deleteByPrimaryKey(id);
	}

	@Override
	public List<ArchBox> getAllArchBoxInfos() {
		String[] temp={"10年","30年","永久"};		
		List<ArchBox> list=this.archBoxMapper.getAllArchBoxInfos();		
		String format="%s[%s][%s][%s]";
		for(ArchBox arch:list)
		{
			int index="1030C".indexOf(arch.getSaveDate())/2;
			arch.setFormatName(String.format(format,arch.getId(),arch.getYear(),arch.getSecret(),temp[index]));
		}	
		return list;
	}

	
	@Override
	@Transactional
	public int addArchFile(ArchFile record, Attachment attachment) {
		int result=0;
		Long fileId=this.archFileMapper.getNextId();	
		Long attachId=this.attachmentMapper.getNextId();		
		record.setId(fileId);
		result=this.archFileMapper.insertSelective(record);
		attachment.setPk(fileId);
		attachment.setId(attachId);
		result+=this.attachmentMapper.insertSelective(attachment);		
		for (Attachment_item item : attachment.getList()) {
			item.setId(null);
			item.setAttachmentId(attachId);
			result+=this.attachment_itemMapper.insertSelective(item);
		}		
		return result;
	}

	@Override
	public int updateArchFile(ArchFile record) {
		return this.archFileMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public JSONMessage<ArchFile> getarchfilebycondition(Map<String, Object> map) {
		int rowCount = Integer.parseInt(map.get("total").toString());

		if (rowCount < 0) {
			rowCount = this.archFileMapper.getcountofarchfilebycondition(map);
		}
		List<ArchFile> list = this.archFileMapper.getarchfilebycondition(map);		
		return new JSONMessage<ArchFile>(list, rowCount);
	}

	@Override
	public int deleteArchFile(Long id) {
		return this.archFileMapper.deleteByPrimaryKey(id);
	}

	@Override
	@Transactional
	public int archiveArchBox(ArchBox record) {
		this.archFileMapper.setArchflag(record.getId());		
		return this.archBoxMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public ArchFile getArchFileByid(Long id) {
		return this.archFileMapper.getArchFileByid(id);
	}

	

}
