package com.sinog2c.quartz;

import org.springframework.beans.factory.annotation.Autowired;

import com.sinog2c.service.api.dbmsnew.DbmsFileCopyService;
import com.sinog2c.service.api.dbmsnew.DbmsNewDataExportService;
import com.sinog2c.service.api.flow.FlowBaseOtherService;

public class ArchivesExchangeMonitor {

	@Autowired
	private DbmsFileCopyService dbmsFileCopyService;
	@Autowired
	private DbmsNewDataExportService dataExportService;
	@Autowired
	private FlowBaseOtherService flowBaseOtherService;
	
//	public void doArchivesWatch(){
//			System.out.print("====档案変更监视开始执行====");
//			dbmsFileCopyService.archivesWatch();
//			System.out.print("====档案変更监视执行结束====");
//		
//	}
	
	public void filesTransfer(){
//			System.out.println("文件传输执行开始");
//			dataExportService.autoCaseDataExchange();
//			System.out.println("文件传输执行结束");
			
			System.out.println("审批结束后的自动案件归档开始！");
			flowBaseOtherService.autoExecuteCaseFile();
			System.out.println("审批结束后的自动案件归档结束！");
	}
	
	public void autoCaseArchive(){
		
		System.out.println("审批结束后的自动案件归档开始！");
		flowBaseOtherService.autoExecuteCaseFile();
		System.out.println("审批结束后的自动案件归档结束！");
	}

}
