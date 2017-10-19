package com.sinog2c.util.report;


import java.io.ByteArrayInputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

public class RMDataRecord
{

    private HashMap FDataRecord;

    public RMDataRecord()
    {
        FDataRecord = new HashMap();
    }

	public void Clear()
	{
		FDataRecord.clear();	
	}
			
    private void _SetValue(String aFieldName, Object aFieldValue)
    {
        _SetValue(aFieldName, aFieldValue, null);
    }

    private void _SetValue(String aFieldName, Object aFieldValue, String aFieldClass)
    {
        FDataRecord.put(aFieldName.toLowerCase(), aFieldValue);
    }

    public void SetValue(String aFieldName, Object aFieldValue)
    {
        _SetValue(aFieldName, aFieldValue);
    }

    public void SetDate(String aFieldName, Date aFieldValue)
    {
        _SetValue(aFieldName, aFieldValue);
    }

    public void SetDate(String aFieldName, String s1, String s2)
    {
		try
		{
        	SimpleDateFormat simpledateformat = new SimpleDateFormat(s2);

        	_SetValue(aFieldName, simpledateformat.parse(s1));
        }
        catch(Exception e)
        {
        }
    }

    public void SetBytes(String aFieldName, byte aFieldValue[])
    {
        _SetValue(aFieldName, aFieldValue);
    }
    
    public Object GetValue(String aFieldName)
    {
    	return FDataRecord.get(aFieldName.toLowerCase());
    }
    
    public byte[] GetBytes(String aFieldName)
    {
        return (byte[])GetValue(aFieldName);
    }
    
    public ByteArrayInputStream GetBinaryStream(String aFieldName)
    {
    	byte[] lData;
    	
    	lData = GetBytes(aFieldName);
    	if (lData == null)
    	{
    		return null;
    	}
    	{
	    	ByteArrayInputStream lTmpStream = new ByteArrayInputStream(lData);
    		return lTmpStream;
    	}
    }
}
