package com.sinog2c.service.impl.system;

import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.sinog2c.dao.api.system.TbsysDocumentMapper;
import com.sinog2c.model.system.SystemUser;
import com.sinog2c.model.system.TbsysDocument;
import com.sinog2c.service.api.system.TbsysDocumentService;
import com.sinog2c.service.impl.base.ServiceImplBase;
import com.sinog2c.util.common.MapUtil;

/**
 * @author liuchaoyang
 * 下午01:45:15
 */
@Service("tbsysDocumentService")
public class TbsysDocumentServiceImpl extends ServiceImplBase implements TbsysDocumentService{
	@Autowired
	private TbsysDocumentMapper tbsysDocumentMapper;

	/**
	 * 获取当前页面数据（分页）
	 * 
	 * @author liucy 2014-7-24 15:10:57
	 */
	public List<TbsysDocument> getTbsysDocumentList(int pageIndex,int pageSize, String key, 
			String tempid, String crimid, String departid,String sortField, String sortOrder) {
		// 查询数据最小数
		int start = pageIndex * pageSize + 1;
		// 查询数据最大数
		int end = start + pageSize - 1;
		return tbsysDocumentMapper.getTbsysDocumentList(start, end, key, tempid, crimid, departid, sortField, sortOrder);
	}
	
	
	public List<TbsysDocument> getTbsysDocumentList2(Map<String,Object> map) {
		return tbsysDocumentMapper.getTbsysDocumentList2(map);
	}

	/**
	 * 获取符合条件的数据总数（分页时显示总数）
	 * 
	 * @author liucy 2014-7-24 15:10:57
	 */
	@Override
	public int getCount(String key, String tempid, String crimid, String departid) {
		// TODO Auto-generated method stub
		return tbsysDocumentMapper.getCount(key, tempid, crimid, departid);
	}
	
	@Override
	public int getCount2(Map<String,Object> map) {
		// TODO Auto-generated method stub
		return tbsysDocumentMapper.getCount2(map);
	}

	/**
	 * 删除系统文书
	 * @author liucy 2014-7-24 15:10:57
	 */
	public int deleteTbsysDocument(String docid) {
		// TODO Auto-generated method stub
		int result = 0;
		String[] docids = docid.split(",");
		for(String id:docids){
			result = tbsysDocumentMapper.deleteTbsysDocument(id);
		}
		return result;
	}

	/**
	 * 获取系统文书的详细信息
	 * @author liucy 2014-7-24 15:10:57
	 */
	@Override
	public TbsysDocument getTbsysDocument(String docid,String tempid,String crimid,String departid) {
		// TODO Auto-generated method stub
		return tbsysDocumentMapper.getTbsysDocument(docid,tempid,crimid,departid);
	}
	/**
	 * 获取系统文书的详细信息
	 * @author liucy 2014-7-24 15:10:57
	 */
	@Override
	public TbsysDocument getTbsysDocument1(String text1,String tempid,String crimid,String departid) {
		// TODO Auto-generated method stub
		return tbsysDocumentMapper.getTbsysDocument1(text1,tempid,crimid,departid);
	}
	/**
	 * 新增保存系统文书
	 * @author liucy 2014-7-24 15:10:57
	 */
	@Override
	public int saveTbsysDocument(TbsysDocument document) {
		// TODO Auto-generated method stub
		return tbsysDocumentMapper.insertSelective(document);
	}

	/**
	 * 修改保存系统文书
	 * @author liucy 2014-7-24 15:10:57
	 */
	@Override
	public int updateTbsysDocument(TbsysDocument document) {
		// TODO Auto-generated method stub
		return tbsysDocumentMapper.updateTbsysDocument(document);
	}
	
	/**
	 * 获取系统文书的序号
	 * 
	 * @author liuchaoyang 2014-7-24 16:38:06
	 */
	public String getXuHao(String key,String tempid,String crimid,String departid){
		return tbsysDocumentMapper.getXuHao(key,tempid,crimid,departid);
	}
	
	
	public TbsysDocument getTbsysDocumentByMap(Map map){
		return tbsysDocumentMapper.getTbsysDocumentByMap(map);
	}
	
	public TbsysDocument getTbsysDocumentByMap2(Map map){
		return tbsysDocumentMapper.getTbsysDocumentByMap2(map);
	}
	public TbsysDocument selectByText1(String str){
		return tbsysDocumentMapper.selectByText1(str);
	}

	@Override
	public int deleteByText1(String text1) {
		return tbsysDocumentMapper.deleteByText1(text1);
	}

	@Override
	public int updateTbsysDocuments(TbsysDocument document) {
		return tbsysDocumentMapper.updateTbsysDocuments(document);
	}
	/**
	 * 修改保存系统文书
	 * @author shily 2014-10-22 14:17:47
	 */
	@Override
	public int updateTbsysDocumentByDocId(TbsysDocument document) {
		// TODO Auto-generated method stub
		return tbsysDocumentMapper.updateTbsysDocumentByDocId(document);
	}
	/**
	 * 修改保存系统文书
	 * @author shily 2014-10-22 14:17:47
	 * 宁夏分离
	 */
	@Override
	public int updateTbsysDocumentByDocId_nx(TbsysDocument document) {
		// TODO Auto-generated method stub
		return tbsysDocumentMapper.updateTbsysDocumentByDocId_nx(document);
	}
	@Override
	public int ajaxSaveDocument(HttpServletRequest request,SystemUser user) {
		int rows = 0;
		TbsysDocument  document = null;
		String docid = request.getParameter("docid");
		String tempid = request.getParameter("tempid");
		String content = request.getParameter("content");
		String introduction = request.getParameter("introduction");
		document.setContent(content);
		document.setDepartid(user.getDepartid());
		document.setIntroduction(introduction);
		document.setOpid(user.getUserid());
		document.setTempid(tempid);
		
		if(document!=null){
			if(docid!=null){
				document.setOptime(new Date());
				rows = tbsysDocumentMapper.updateTbsysDocuments(document);
			}else{
				rows = tbsysDocumentMapper.insertSelective(document);
			}
			
		}
		return rows;
	}
    /**
     * 此方法用在批量签章 的方法中，暂时不用
     */
	@Override
	public Map getTbsysFlowBaseDoc(Map maps) {
		
		
		return null;
	}
	/**
     * 此方法用在批量签章 的方法中，暂时不用
     */
	@Override
	public Map getTbsysFlowBaseDoc_nx(Map maps) {
		
		
		return null;
	}
	/**
	 * 方法描述：查询出大字段（病残鉴定表）
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Map getFlowBaseDocByMap(Map map) {
		return MapUtil.trimKeyValue(tbsysDocumentMapper.getFlowBaseDocByMap(map));
	}

	@Override
	public int getCount(String tempid, String opid) {
		return tbsysDocumentMapper.getCount1(tempid,opid);
	}
}

