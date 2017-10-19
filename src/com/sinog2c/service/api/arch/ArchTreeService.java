package com.sinog2c.service.api.arch;

import java.util.List;
import java.util.Map;

import com.sinog2c.model.JSONMessage;
import com.sinog2c.model.arch.ArchBox;
import com.sinog2c.model.arch.ArchFile;
import com.sinog2c.model.arch.ArchTree;
import com.sinog2c.model.attachment.Attachment;

public interface ArchTreeService {

	/**
	 * 获取卷库目录
	 * 
	 * @return
	 */
	List<ArchTree> getAllArchFolder();

	/**
	 * 新增卷库
	 * 
	 * @param record
	 * @return
	 */
	Long add(ArchTree record);

	/**
	 * 删除卷库
	 * 
	 * @param id
	 * @return
	 */
	int delete(Long id);

	/**
	 * 修改卷库信息
	 * 
	 * @param record
	 * @return
	 */
	int update(ArchTree record);

	/**
	 * 新增卷盒
	 * 
	 * @param record
	 * @return
	 */
	int addArchBox(ArchBox record);

	/**
	 * 修改卷盒信息
	 * 
	 * @param record
	 * @return
	 */
	int updateArchBox(ArchBox record);

	/**
	 * 删除卷盒信息
	 * 
	 * @param id
	 * @return
	 */
	int deleteArchBox(Long id);
	
	/**
	 * 卷盒归档
	 * @param record
	 * @return
	 */
	int archiveArchBox(ArchBox record);

	/**
	 * 获取卷盒信息列表
	 * 
	 * @param map
	 * @return
	 */
	JSONMessage<ArchBox> getarchboxbycondition(Map<String, Object> map);

	/**
	 * 获取所用卷盒信息
	 * 
	 * @return
	 */
	List<ArchBox> getAllArchBoxInfos();

	/**
	 * 新增档案文件
	 * 
	 * @param record
	 * @param attachment 
	 * @return
	 */
	int addArchFile(ArchFile record, Attachment attachment);

	/**
	 * 修改档案文件
	 * 
	 * @param record
	 * @return
	 */
	int updateArchFile(ArchFile record);
	
	/**
	 * 删除档案文件
	 * @param id
	 * @return
	 */
	int deleteArchFile(Long id);

	/**
	 * 获取档案信息列表
	 * 
	 * @param map
	 * @return
	 */
	JSONMessage<ArchFile> getarchfilebycondition(Map<String, Object> map);
	
	/**
	 * 查询档案文件信息
	 * @param id
	 * @return
	 */
	ArchFile getArchFileByid(Long id);
}
