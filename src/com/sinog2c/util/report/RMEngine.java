package com.sinog2c.util.report;

//import java.util.*;
//import java.sql.*;
//import java.lang.*;
import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class RMEngine
{
    private boolean FFromFirstRecord;
    private int FReportType = 1;
    private String FReportFileName = "";
    
    private ArrayList FDataSets;
    private ArrayList FDataSets_Data;
    private ArrayList FMasterDetail1;
    private ArrayList FMasterDetail2;
    private ArrayList FMasterDetail3;
    
    public String ViewerVersion = "12.0.0.6";
    public String ViewerFileName = "RMViewer.inf";
    
    public RMEngine()
        throws Exception
    {
    	FDataSets = new ArrayList();
    	FDataSets_Data = new ArrayList();
    	FMasterDetail1 = new ArrayList();
    	FMasterDetail2 = new ArrayList();
    	FMasterDetail3 = new ArrayList();
    	
    	this.Init();
    }

	public void Init()
	{
		FFromFirstRecord = true;

		FDataSets.clear();
		FDataSets_Data.clear();
		FMasterDetail1.clear();
		FMasterDetail2.clear();
		FMasterDetail3.clear();	
	}
	
	public void SetReportFile(String aFileName, int aReportType) 
	{
		FReportType = aReportType;
		FReportFileName = RMUtils.ReplaceStr(aFileName, "\\", "/");
	}
	public void AddDataSet(String aDataSetName, List<Map> aResultSet,List<Map> typeList)
	{
    	TRMClientDataSet lClientDataSet;
       
//    	lClientDataSet = RMUtils.CreateClientDataSet1(aResultSet, null, null, -1, FFromFirstRecord);
    	lClientDataSet = RMUtils.CreateClientDataSet1(aResultSet, null, null, -1, FFromFirstRecord,typeList);
    	if(lClientDataSet != null)
    	{
			FDataSets_Data.add(lClientDataSet);
			FDataSets.add(aDataSetName);
    	}
    }
	public boolean AddDataSet(String aDataSetName, ResultSet aResultSet,Map<String,String> map)
	{
    	TRMClientDataSet lClientDataSet;
       
    	lClientDataSet = RMUtils.CreateClientDataSet(aResultSet, null, null, -1, FFromFirstRecord,map);
    	if(lClientDataSet != null)
    	{
			FDataSets_Data.add(lClientDataSet);
			FDataSets.add(aDataSetName);
			return false;
    	}
    	return true;
    }
	public void AddClientDataSet(String aDataSetName, TRMClientDataSet aClientDataSet)
	{
		if (aClientDataSet != null)
		{
			FDataSets_Data.add(aClientDataSet);
			FDataSets.add(aDataSetName);
		}
	}
	
	public void AddDataSetRelation(String aMasterName, String aDetailName, String aRelation)
	{
		FMasterDetail1.add(aMasterName);
		FMasterDetail2.add(aDetailName);
		FMasterDetail3.add(aRelation);
	}
	
	public void AddVariable(String aVarName, String aValue, Boolean aIsString)
	{
	}
	
	public String CreateCourtViewer(String aParams,String aa)
	{
		String ltmpStr = "";
		StringBuffer lStrBuffer = new StringBuffer("");
		try
		{
            
        	lStrBuffer.append("<object classid=\"clsid:3C5015F8-D904-4D6F-A4CF-3C78332885C2\"" + " id=\"MyViewer\" " + aParams + "\n");
        	lStrBuffer.append("\t  codebase=\"" + ViewerFileName + "#version=" + ViewerVersion + "\"" + ">\n");
        	lStrBuffer.append("</object>\n");
        	lStrBuffer.append("<textarea id=\"RMVIEWER_DATA\" style=\"display:none\">" + aa+ "</textarea>\n");

			lStrBuffer.append("<script language=\"JavaScript\">\n");
    		lStrBuffer.append("<!--\n");
	    	lStrBuffer.append("  document.all(\"MyViewer\").Init(window, document, 600);\n");
			lStrBuffer.append("  document.all(\"MyViewer\").ShowProgress = true;\n");
			lStrBuffer.append("  document.all(\"MyViewer\").GetReportData(document.all(\"RMVIEWER_DATA\").value);\n");
	    	lStrBuffer.append("  document.all(\"MyViewer\").ShowReport();\n");
	    	/*lStrBuffer.append("  document.all(\"MyViewer\").PrinterName = \"HWSealPrinter\";\n");
			lStrBuffer.append("  document.all(\"MyViewer\").ShowPrintDialog = false;\n");
			lStrBuffer.append("  document.all(\"MyViewer\").PrintReport();\n");*/
	    	lStrBuffer.append("//-->\n");
	    	lStrBuffer.append("</script>\n");
        
        	ltmpStr = lStrBuffer.toString();
        }
        catch(Exception exception)
        {
        	exception.printStackTrace();
        }
    	
        return ltmpStr;
	}
	public String CreateViewer(String aParams)
	{
		String ltmpStr = "";
		StringBuffer lStrBuffer = new StringBuffer("");
		byte lReportData[];

		try
		{
			lReportData = _GetReportData();
            if(lReportData == null)
            {
                lReportData = new byte[0];
            }

        	lStrBuffer.append("<object classid=\"clsid:3C5015F8-D904-4D6F-A4CF-3C78332885C2\"" + " id=\"MyViewer\" " + aParams + "\n");
        	lStrBuffer.append("\t  codebase=\"" + ViewerFileName + "#version=" + ViewerVersion + "\"" + ">\n");
        	lStrBuffer.append("</object>\n");
        	lStrBuffer.append("<textarea id=\"RMVIEWER_DATA\" style=\"display:none\">" + RMUtils.BASE64EncodeStream(lReportData)+ "</textarea>\n");

			lStrBuffer.append("<script language=\"JavaScript\">\n");
    		lStrBuffer.append("<!--\n");
	    	lStrBuffer.append("  document.all(\"MyViewer\").Init(window, document, 600);\n");
			lStrBuffer.append("  document.all(\"MyViewer\").ShowProgress = true;\n");
			lStrBuffer.append("  document.all(\"MyViewer\").GetReportData(document.all(\"RMVIEWER_DATA\").value);\n");
	    	lStrBuffer.append("  document.all(\"MyViewer\").ShowReport();\n");
	    	/*lStrBuffer.append("  document.all(\"MyViewer\").PrinterName = \"HWSealPrinter\";\n");
			lStrBuffer.append("  document.all(\"MyViewer\").ShowPrintDialog = false;\n");
			lStrBuffer.append("  document.all(\"MyViewer\").PrintReport();\n");*/
	    	lStrBuffer.append("//-->\n");
	    	lStrBuffer.append("</script>\n");
        
        	ltmpStr = lStrBuffer.toString();
        }
        catch(Exception exception)
        {
        }
    	
        return ltmpStr;
	}
	
	public String dedaoReportData(){
		String returnString="";
		byte lReportData[] = null;
		try {
		lReportData = _GetReportData();
		 if(lReportData == null)
		        {
		            lReportData = new byte[0];
		        }
		} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		}
	returnString = RMUtils.BASE64EncodeStream(lReportData);
	return returnString;
	}
	
	private byte[] _GetReportData()
	        throws IOException
	{
	    int i;
	    String lStr = "";
    	TRMClientDataSet lClientDataSet;
	    ByteArrayOutputStream lOutStream = new ByteArrayOutputStream();
        ZipOutputStream lZipOutStream = new ZipOutputStream(lOutStream);
        ZipEntry lZipEntry;
        
        //lZipOutStream.setLevel(ZipOutputStream.STORED);
        lZipEntry = new ZipEntry("CONFIG");
        lZipOutStream.putNextEntry(lZipEntry);
        lStr = "Version=100\n";
        lZipOutStream.write(lStr.getBytes());
        
        for (i = 0; i < FMasterDetail1.size(); i++)
        {
			_CompressStr(lZipOutStream, "RELATION_" + i + "_" + FMasterDetail1.get(i).toString(),
				FMasterDetail2.get(i).toString() + "=" + FMasterDetail3.get(i).toString());
        }

		for (i = 0; i < FDataSets.size(); i++)
		{
			lClientDataSet = (TRMClientDataSet)FDataSets_Data.get(i);
			_CompressBytes(lZipOutStream, "DATASET_" + FDataSets.get(i), 
				lClientDataSet.GetCompressedData());

			lClientDataSet = null;	
			FDataSets_Data.set(i, null);
		}
		
		if((new File(FReportFileName)).exists())
        {
			int lCount;
			byte[] lBuf = new byte[2048];
			FileInputStream lFileInPutStream = new FileInputStream(FReportFileName);
			
			lZipEntry = new ZipEntry("REPORTTEMPORT_" + FReportType + "_0");
	        lZipOutStream.putNextEntry(lZipEntry);
	        
			while ((lCount = lFileInPutStream.read(lBuf, 0, 2048)) != -1)
				lZipOutStream.write(lBuf, 0, lCount);
				
			lFileInPutStream.close();
			lFileInPutStream = null;
			lBuf = null;
		}            
		
		lZipOutStream.close();
	    return lOutStream.toByteArray();
	}

    private int _CompressBytes(ZipOutputStream aZipOutputStream, String aInStr, byte aInByte[])
    {
        try
        {
	        int i;
    	    BufferedInputStream lBufferedInputStream = new BufferedInputStream(new ByteArrayInputStream(aInByte), 2048);
	        byte lByte[] = new byte[2048];
    	    ZipEntry lZipEntry = new ZipEntry(aInStr);
        
	        aZipOutputStream.putNextEntry(lZipEntry);
    	    while((i = lBufferedInputStream.read(lByte, 0, 2048)) != -1) 
        	{
            	aZipOutputStream.write(lByte, 0, i);
        	}
            
        	lBufferedInputStream.close();
        	return 1;
    	}
        catch(Exception exception)
        {
            return -1;
        }
    }

    private int _CompressStr(ZipOutputStream aZipOutputStream, String aInStr1, String aInStr2)
    {
        return _CompressBytes(aZipOutputStream, aInStr1, aInStr2.getBytes());
    }

}