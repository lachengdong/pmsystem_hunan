package com.sinog2c.service.api.khjc;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.sinog2c.model.khjc.KhjcTbflowBaseDoc;
//流程相关操作

public interface PublicApproveService {
	
	//提交附件方法，返回map(附件表的主键，保存状态)
	public Map approveKhjcFlowBaseDoc(String saveType,String depType,Map<String,String> map,HttpServletRequest request);

}
