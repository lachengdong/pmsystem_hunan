package com.sinog2c.service.api.system;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import com.sinog2c.model.system.PrintScheme;

public interface PrintSchemeService {

	Object getPrintSchemeList(HttpServletRequest request);

	Object getPrintSchemeById(HttpServletRequest request);

	Object insertPrintScheme(HttpServletRequest request);

	Object updatePrintScheme(HttpServletRequest request);

	Object deletePrintSchemeById(HttpServletRequest request);

	Object deleteBatchPrintSchemeByIds(HttpServletRequest request);

	Object getPrintSchemeConfigList(HttpServletRequest request);

	Object getPrintSchemeConfigById(HttpServletRequest request);
	
	Object insertPrintSchemeConfig(HttpServletRequest request);

	Object updatePrintSchemeConfig(HttpServletRequest request);

	Object deletePrintSchemeConfigById(HttpServletRequest request);

	Object deleteBatchPrintSchemeConfigByIds(HttpServletRequest request);

	Object getBiaoDanOrReportByType(HttpServletRequest request);

	List<PrintScheme> getPrintSchemeComboBox(HttpServletRequest request);

	Object printBatch(HttpServletRequest request); 

	Object selectResourcesByType(HttpServletRequest request);

}
