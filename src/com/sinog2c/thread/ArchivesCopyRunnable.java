package com.sinog2c.thread;

import java.util.List;
import java.util.Map;

import com.sinog2c.service.api.dbmsnew.DataTransferService;

public class ArchivesCopyRunnable implements Runnable{

	private List<Map<String,Object>> criminalidList;
	private DataTransferService dataTransferService;
	
	public ArchivesCopyRunnable(){
	}
	
	public ArchivesCopyRunnable(List<Map<String,Object>> criminalidList, DataTransferService dataTransferService){
		this.criminalidList = criminalidList;
		this.dataTransferService = dataTransferService;
	}
	
	@Override
	public void run(){
		//电子档案复制到临时目录
		if(null != criminalidList && ! criminalidList.isEmpty()){
			dataTransferService.preExportArchive(criminalidList);
		}
		
	}
	
}
