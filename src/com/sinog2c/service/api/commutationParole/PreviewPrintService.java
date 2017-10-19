package com.sinog2c.service.api.commutationParole;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.sinog2c.model.system.TbsysCode;

/**
 * 预览打印
 * @author liuyi
 *
 */
public interface PreviewPrintService {
	/**
	 * 获取预览打印列表
	 * @param request
	 * @return
	 * 2014年8月18日 10:10:12
	 */
	public  Object  getPreviewReportList(HttpServletRequest request);

	/**
	 * 获取案件类型
	 * @param request
	 * @return
	 * 2014年8月18日 10:10:27
	 */
	public List<TbsysCode> getPreviewPrintCaseType(HttpServletRequest request);
}
