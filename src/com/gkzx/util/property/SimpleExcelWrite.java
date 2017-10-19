package com.gkzx.util.property;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jxl.CellView;
import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

import com.sinog2c.util.common.JsonUtil;
import com.sinog2c.util.common.StringNumberUtil;

public class SimpleExcelWrite {
	public void createExcel(HttpServletRequest request,HttpServletResponse response,Map<String,Object> parmMap) throws WriteException,IOException{
		dooutof(request,response,parmMap);
	}
	public String dooutof(HttpServletRequest request,HttpServletResponse response,Map<String,Object> parmMap) {

		  // 文件名称与路径
		  String realPath = request.getRealPath("");
//		  String fileName = new Date().getTime()+"导出的文件名.xls";
		  String fileName = "基本信息.xls";
		  File file = new File(realPath + "\\tempPath\\");//导出文件存放的位置
		  if (!file.exists()) {
		   file.mkdirs();
		  }
		  realPath = realPath + "\\tempPath\\" + fileName;

		  // 建立工作薄并写表头
		  try {
			  WritableWorkbook wwb = null;
		      File file2 = new File(realPath);
		      if (!file.exists()) {
		    	  Workbook.getWorkbook(file2);
		      }else{
		    	  wwb = Workbook.createWorkbook(file2);
		      }
		      WritableSheet ws = wwb.createSheet("Sheet1", 0);// 建立工作簿
		      
		      
		      //Excel样式
		      WritableCellFormat cellFormat = new WritableCellFormat();
			  cellFormat.setAlignment(jxl.format.Alignment.CENTRE);
			  cellFormat.setWrap(true);
		      
		      
		      // 写表头
		      String columns =  (String) parmMap.get("columns");
		      String excelData =  (String) parmMap.get("excelData");
		      if(columns!=null && !"".equals(columns)){
		        ArrayList list = (ArrayList) JsonUtil.Decode(columns);
		        ArrayList datalist = (ArrayList) JsonUtil.Decode(excelData);
				if(list!=null && list.size()>0){
					for(int i=0; i<list.size();i++){
						Map map  =  (Map) list.get(i);
						Map datamap  =  (Map) datalist.get(i);
						Label label = new Label(i, 0, (String)map.get("header"));
						label.setCellFormat(cellFormat);
						ws.addCell(label);// 放入工作簿
						String key = (String)map.get("field");
						if(datamap.containsKey(key)){
							ws = returnLable(ws,cellFormat,i,datamap);
						}
					}
				}
		      }

		   // 写入Exel工作表
		   wwb.write();
		   // 关闭Excel工作薄对象
		   wwb.close();

		   // 下载
		   String contentType = "application/x-download";
		   InputStream inStream = new FileInputStream(realPath);
           response.reset();
           response.setContentType(contentType);
		   response.setHeader("Content-Disposition", "attachment;filename="
		     + new String(fileName.getBytes("gb2312"), "ISO8859-1"));
//           response.addHeader("Content-Disposition","attachment;   filename=\"" + fileName + "\"");
           byte[] b2 = new byte[100];
           int len;
           while ((len = inStream.read(b2)) > 0) {
               response.getOutputStream().write(b2, 0, len);
           }
           inStream.close();
		   
		  } catch (Exception e) {
		   e.printStackTrace();
		  }// 此处建立路径
		  return null;
	}
	
	public WritableSheet returnLable(WritableSheet ws,WritableCellFormat cellFormat,int number,Map map){
		try {
			 CellView navCellView = new CellView();  
		      navCellView.setAutosize(true); //设置自动大小
		      navCellView.setSize(18);
			Set<Object> set = map.keySet();
			int row = number+1;
			int i=0;
			String filterStr = "emptyval,rn";
		    for(Object k:set){
		    	String obj = StringNumberUtil.returnString2(k.toString());
		    	if((obj.contains("_")) || filterStr.contains(obj)){
		    		
		    	}else{
		    		Label label = new Label(i,row, map.get(k).toString());
			    	label.setCellFormat(cellFormat);
					ws.addCell(label);
					i++;
		    	}
		    }
		} catch (WriteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ws;
	}
}
