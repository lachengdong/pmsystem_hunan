package com.sinog2c.util.report;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public class TRMClientDataSet
{

    private StringBuffer FDataBuffer;
    private int FAddRecordNum;
    private int FRecordCount;
    private int FBlockNum;
    private int OoOOOo00oOOooO00OO000;
    private ByteArrayOutputStream FTmpOutputStream;
    private ZipOutputStream FZipOutStream;
    private int FStartRecordNo;
    private boolean FEndCompress;

    public TRMClientDataSet()
    {
        FDataBuffer = new StringBuffer();
        FAddRecordNum = 0;
        FRecordCount = 0;
        FBlockNum = 0;
        OoOOOo00oOOooO00OO000 = 0;
        FStartRecordNo = -1;
        FEndCompress = false;

        FTmpOutputStream = new ByteArrayOutputStream();
        FZipOutStream = new ZipOutputStream(new BufferedOutputStream(FTmpOutputStream));
    }

	/*
	 * ���һ���ֶ�
	 */
    public void AddFields(String s)
        throws IOException
    {
        _CompressStr(FZipOutStream, "METADATA", s);
    }

	/*
	 * ���һ���¼
	 */
    public void addRow(String s)
        throws IOException
    {
        FRecordCount++;
        FAddRecordNum++;
        FDataBuffer.append(s);
        if(FAddRecordNum >= 512)
        {
            _CompressStr(FZipOutStream, "RM_BLOCK" + FBlockNum++, FDataBuffer.toString());
            FAddRecordNum = 0;
            FDataBuffer.setLength(0);
        }
    }

    public int getRecordCount()
    {
        return FRecordCount;
    }

    private void _StopCompress()
        throws IOException
    {
        if(FDataBuffer.length() > 0)
        {
            _CompressStr(FZipOutStream, "RM_BLOCK" + FBlockNum++, FDataBuffer.toString());
            FDataBuffer.setLength(0);
        }
    }

    private void StopCompress()
        throws IOException
    {
        if(FEndCompress)
        {
            return;
        } 
        else
        {
            FEndCompress = true;
            _StopCompress();
            FZipOutStream.close();
            return;
        }
    }

    public void SetStartRecordNo(int i)
    {
        FStartRecordNo = i;
    }

    public byte[] GetCompressedData()
    {
		try
		{
        	StopCompress();
        	return FTmpOutputStream.toByteArray();
        }
        catch(Exception exception)
        {
        	return null;
        }
    }

    public String getXMLData()
        throws IOException
    {
        return getXMLData(FStartRecordNo);
    }

    public String getXMLData(int i)
        throws IOException
    {
        String lFields = "";
        String lRecordDatas = "";
        int j = 0;
        int k;
        ZipEntry lZipEntry;
        byte lbyte[] = new byte[2048];

        StopCompress();
        ByteArrayInputStream lByteArrayInputStream = new ByteArrayInputStream(FTmpOutputStream.toByteArray());
        ZipInputStream lZipInputStream = new ZipInputStream(new BufferedInputStream(lByteArrayInputStream));
        
        while((lZipEntry = lZipInputStream.getNextEntry()) != null) 
        {
            if(++j > i && i != -1)
                break;

            ByteArrayOutputStream lByteArrayOutputStream = new ByteArrayOutputStream();
            while((k = lZipInputStream.read(lbyte, 0, 2048)) != -1) 
                lByteArrayOutputStream.write(lbyte, 0, k);
                
            lByteArrayOutputStream.flush();
            lByteArrayOutputStream.close();
            if("METADATA".equals(lZipEntry.getName()))
                lFields = new String(lByteArrayOutputStream.toByteArray());
            else
                lRecordDatas = lRecordDatas + new String(lByteArrayOutputStream.toByteArray());
        }

        lZipInputStream.close();
        
        return "<?xml version=\"1.0\" standalone=\"yes\"?>   <DATAPACKET Version=\"2.0\"><METADATA>" + lFields + "</METADATA><ROWDATA>" + lRecordDatas + "</ROWDATA></DATAPACKET>";
    }

    public String toString()
    {
        try
        {
            return getXMLData();
        }
        catch(IOException ioexception)
        {
            return null;
        }
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
