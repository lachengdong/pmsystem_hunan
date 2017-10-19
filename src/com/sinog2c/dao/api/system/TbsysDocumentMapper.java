package com.sinog2c.dao.api.system;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import com.sinog2c.model.system.TbsysDocument;

@Component("tbsysDocumentMapper")
public interface TbsysDocumentMapper {
	 /**
	 * 获取当前页面数据（分页）
	 * 
	 * @author liucy 2014-7-17 10:12:26
	 */
	List<TbsysDocument> getTbsysDocumentList(@Param("start") int start,@Param("end") int end,@Param("key") String key,@Param("tempid") String tempid,
			@Param("crimid") String crimid,@Param("departid") String departid,@Param("sortField") String sortField,@Param("sortOrder") String sortOrder);
	
	List<TbsysDocument> getTbsysDocumentList2(Map<String,Object> map);

	/**
	 * 获取符合条件的数据总数（分页时显示总数）
	 * 
	 * @author liucy 2014-7-24 15:10:57
	 */
	int getCount(@Param("key") String key,@Param("tempid") String tempid,@Param("crimid") String crimid,@Param("departid") String departid);
	
	int getCount2(Map<String,Object> map);
	
	/**
	 * 获取系统文书的详细信息
	 * @author liucy 2014-7-24 15:10:57
	 */
	TbsysDocument getTbsysDocument(@Param("docid")String docid,@Param("tempid")String tempid,@Param("crimid") String crimid,@Param("departid")String departid);
	
	TbsysDocument getTbsysDocument1(@Param("text1")String docid,@Param("tempid")String tempid,@Param("crimid") String crimid,@Param("departid")String departid);
	
	/**
	 * 修改保存系统文书
	 * @author liucy 2014-7-24 15:10:57
	 */
	int deleteTbsysDocument(String docid);

    /**
	 * 新增保存系统文书
	 * @author liucy 2014-7-24 15:10:57
	 */
    int insertSelective(TbsysDocument document);

    /**
	 * 修改保存系统文书
	 * @author liucy 2014-7-24 15:10:57
	 */
    int updateTbsysDocument(TbsysDocument document);
    
    /**
     * 根据Map新增
     * @param paramMap
     * @return
     */
    public int insertByMap(Map<String, Object> paramMap);
    
    /**
     * 根据Map更新
     * @param paramMap
     * @return
     */
    public int updateByMap(Map<String, Object> paramMap);
    
    /**
	 * 获取系统文书的最大编号
	 * 
	 * @author liuchaoyang 2014-7-24 16:38:06
	 */
	String getXuHao(@Param("key")String key,@Param("tempid") String tempid,@Param("crimid") String crimid,@Param("departid") String departid);
	
	TbsysDocument getTbsysDocumentByMap(Map map);
	
	TbsysDocument getTbsysDocumentByMap2(Map map);
	TbsysDocument selectByText1(String str);
	int deleteByText1(String text1);
	
	int updateTbsysDocuments(TbsysDocument document);
	
	int updateTbsysDocumentByDocId(TbsysDocument document);
	
	int updateTbsysDocumentByDocId_nx(TbsysDocument document);
	
	/**
	 * 方法描述：查询出对应的病残鉴定表
	 * @version：2014年12月15日14:06:41
	 */
	Map getFlowBaseDocByMap(Map map);

	int getCount1(@Param("tempid")String tempid, @Param("opid")String opid);
	
	List findreprotList(Map map);
	
	String findreportDataByDocid(Map map);
}