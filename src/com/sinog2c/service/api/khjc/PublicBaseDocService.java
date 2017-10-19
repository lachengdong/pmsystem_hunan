package com.sinog2c.service.api.khjc;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.sinog2c.model.khjc.KhjcTbflowBaseDoc;
import com.sinog2c.model.system.SystemUser;
//附件表相关操作
public interface PublicBaseDocService {
	//新增保存附件表方法
	public Map saveKhjcFlowBaseDoc(Map<String,String> map,SystemUser user);
	
	//更新保存附件表方法(只更新大字段)
	public String updateKhjcFlowBaseDocContentBydocid(String docid,String doccontent,SystemUser user);
	
	//更新保存附件表方法
	public Map updateKhjcFlowBaseDocBydocid(String docId,Map<String,String> map,HttpServletRequest request);
	
	//删除附件表方法
	public int deleteKhjcFlowBaseDoc(String docid);
	
	//根据主键获取附件表大字段
	public String getDocContentByDocid(String docid);
	
	//根据条件查询相关的附件表记录
	public List<Map> getBaseDocByCondition(Map map);
	
	//查询符合条件的附件表count
	public int countBaseDocByCondition(Map map);
	
	/**
	 * author：YangZR
	 * Date：   2015.02.04
	 * @param map
	 * @param user
	 * @return 返回保存的记录数
	 */
	public Map saveFormDoc(Map<String,String> map,SystemUser user);
	
}
