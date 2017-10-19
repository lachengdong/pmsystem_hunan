package com.sinog2c.service.api.system;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.sinog2c.model.system.SystemUser;
import com.sinog2c.model.system.TbsysDocument;

/**
 * @author liuchaoyang
 * 系统文书信息表相关操作接口
 * 下午01:28:30
 */
public interface TbsysDocumentService {
	/**
	 * 获取当前页面数据（分页）
	 * @author liucy 2014-7-24 15:10:57
	 */
	public List<TbsysDocument> getTbsysDocumentList(int pageIndex, int pageSize,
			String key,String tempid,String crimid,String departid,String sortField,String sortOrder);
	
	/**
	 * 获取符合条件的数据总数（分页时显示总数）
	 * @author liucy 2014-7-24 15:10:57
	 */
	public int getCount(String key,String tempid,String crimid,String departid);
	
	public List<TbsysDocument> getTbsysDocumentList2(Map<String,Object> map);
	
	public int getCount2(Map<String,Object> map);
	
	/**
	 * 获取系统文书的详细信息
	 * @author liucy 2014-7-24 15:10:57
	 */
	public TbsysDocument getTbsysDocument(String docid,String tempid,String crimid,String departid) ;
	
	/**
	 * 方法描述:获取需要走流程，但是不属于减刑假释的表单
	 * @author mushuhong
	 * @version 2014年12月14日17:18:47
	 */
	public Map getTbsysFlowBaseDoc(Map maps);
	
	/**
	 * 方法描述:获取需要走流程，但是不属于减刑假释的表单 宁夏分离
	 * @author mushuhong
	 * @version 2014年12月14日17:18:47
	 */
	public Map getTbsysFlowBaseDoc_nx(Map maps);
	
	public TbsysDocument getTbsysDocument1(String text1,String tempid,String crimid,String departid) ;
	
	/**
	 * 新增保存系统文书
	 * @author liucy 2014-7-24 15:10:57
	 */
	public int saveTbsysDocument(TbsysDocument document);
	
	/**
	 * 修改保存系统文书
	 * @author liucy 2014-7-24 15:10:57
	 */
	public int updateTbsysDocument(TbsysDocument document);
	
	/*
	 * 根据templeid and crimid 更新大字段
	 */
	public int updateTbsysDocuments(TbsysDocument document);

	/**
	 * 删除系统文书
	 * @author liucy 2014-7-24 15:10:57
	 */
	public int deleteTbsysDocument(String docid) ;
	
	/**
	 * 获取系统文书的序号
	 * 
	 * @author liuchaoyang 2014-7-24 16:38:06
	 */
	public String getXuHao(String key,String tempid,String crimid,String departid);
	
	public TbsysDocument getTbsysDocumentByMap(Map map);
	
	public Map getFlowBaseDocByMap(Map map);
	
	public TbsysDocument getTbsysDocumentByMap2(Map map);
	public TbsysDocument selectByText1(String str);
	public int deleteByText1(String text1);
	public int updateTbsysDocumentByDocId(TbsysDocument document);

	public int updateTbsysDocumentByDocId_nx(TbsysDocument document);
	/**
	 * 保存系统文书
	 * @author liucy 2014-7-24 15:10:57
	 */
	public int ajaxSaveDocument(HttpServletRequest request,SystemUser user);
	
	public int getCount(String tempid, String opid);
}
