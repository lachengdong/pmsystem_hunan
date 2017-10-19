package com.sinog2c.service.api.system;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.sinog2c.model.system.SignatureScheme;
import com.sinog2c.model.system.SystemOrganization;
import com.sinog2c.model.system.SystemUser;
import com.sinog2c.model.system.TbsysCode;


public interface PrintBatchService {

	/**
	 * 获取案件类型
	 * @param request
	 * @return
	 * 2014年9月1日 10:39:11
	 */
	public List<TbsysCode> getPrintBatchCaseType(HttpServletRequest request);
	
	public SystemOrganization getByOrganizationId(String orgid);
	
	public List<Map<String,Object>> getPrintData(String flowdraftid, SignatureScheme signatureScheme, SystemUser user);

}
