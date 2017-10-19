package com.sinog2c.service.api.khjc;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.sinog2c.model.khjc.KhjcTbflowBaseDoc;
import com.sinog2c.model.khjc.KhjcTbflowDeliver;

public interface PublicToolMethodService {
	
	//获取序列的下一个编号
	public String getSeqBySeqname(String name);
	
	//获取当前时间
	public String getNowDate();
	
	//获取流程节点
	public KhjcTbflowDeliver getNodeId(String flowdefid,String orderby,String state);
	
	//将页面信息放入Map中
	public Map getNoteinfoMap(String noteinfo);
	
	//通过一个Code类型获取Code值
	public List ajaxKhjcGetCode(HttpServletRequest request);
	
	//通过主键获取签章方案
	public String getSingaBySingaid(String singaid);
}
