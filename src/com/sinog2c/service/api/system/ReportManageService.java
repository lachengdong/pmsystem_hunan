package com.sinog2c.service.api.system;

import javax.servlet.http.HttpServletRequest;

public interface ReportManageService {

	Object getReportList(HttpServletRequest request);

	Object getReportById(HttpServletRequest request);

	Object insertReportManage(HttpServletRequest request);

	Object updateReportManage(HttpServletRequest request);

	Object deleteReportById(HttpServletRequest request);

	Object deleteBatchReportByIds(HttpServletRequest request);
}
