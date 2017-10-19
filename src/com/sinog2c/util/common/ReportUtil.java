package com.sinog2c.util.common;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.sinog2c.util.report.RMEngine;

public class ReportUtil {

	/**
     * 此方法描述的是：报表统计
     */
	public static void dealReportEngine(List<Map> list,String reportName, String dataSetName, HttpServletRequest request){
		try{
			RMEngine engine = new RMEngine();
			if(engine!=null){
				engine.Init();
				engine.ViewerVersion = "12.0.0.6";
				engine.ViewerFileName = "hwpostil.inf";
//				engine.AddDataSet(dataSetName,list);
				
				String path=request.getRealPath("").replace("\\", "/")+"/RMreportReport/";
				engine.SetReportFile(path+reportName+".rmf", 1);
				request.setAttribute("reportName", reportName);
				request.setAttribute("engine", engine);
//				List<String> conlist=new ArrayList<String>();
//				request.setAttribute("conditions", conlist);
				request.setAttribute("reportdata", engine.dedaoReportData().toString());
			}	
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
}
