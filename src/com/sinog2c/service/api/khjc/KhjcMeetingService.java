package com.sinog2c.service.api.khjc;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.sinog2c.model.khjc.KhjcMeetingInfo;
import com.sinog2c.model.khjc.KhjcTbflowBaseDoc;

public interface KhjcMeetingService {
	//批量操作(提交，退回，拒绝)
	public String piLiangCaoZuo(HttpServletRequest request);
	
	//获取附件表大字段
	public String getDocContentByDocid(HttpServletRequest request);
	
	//获取附件表大字段
	public String getSingaBySingaid(HttpServletRequest request);
	
	//更新附件表
	public String updateDocByDocid(HttpServletRequest request);
	
	//更新附件表
	public List<HashMap> ajaxKhjcGetCode(HttpServletRequest request);
	
	//保存会议记录
	public String saveMeetingInfo(HttpServletRequest request);

	//获取序列的下一个号
	String getSeqBySeqname(String name);
	
	public List<KhjcMeetingInfo> getKhjcMeetingByType(int pageIndex, int pageSize,
			String key,String tempid,String crimid,String departid,String sortField,String sortOrder,String nodeid);
	
}
