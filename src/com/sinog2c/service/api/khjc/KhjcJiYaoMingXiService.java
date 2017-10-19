package com.sinog2c.service.api.khjc;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.sinog2c.model.khjc.KhjcTbflowBaseDoc;
import com.sinog2c.model.system.SystemUser;
/**
 * 纪要明细/政策法规service
 * @author Administrator
 *
 */
public interface KhjcJiYaoMingXiService {
	//保存纪要明细/政策法规
	public String saveJiYaoMingXiInfo(HttpServletRequest request,SystemUser user);
	
	//删除纪要明细/政策法规
	public String deleteJiYaoMingXiInfo(String id);
	
	//根据状态获取纪要明细/政策法规列表
	public List<KhjcTbflowBaseDoc> getJiYaoMingXiDoc(int pageIndex, int pageSize,
			String key,String tempid,String crimid,String departid,String sortField,String sortOrder);
}
