package com.sinog2c.util.report;

import java.io.InputStream;
import java.sql.Types;
import java.util.Vector;



public class RMDataSet
{
	private boolean FErrorFlag;
    private Vector FFieldNames;
    private Vector FFieldTypes;
    private Vector FFieldSizes;
    private Vector FFieldAttrNames;
    private Vector FFieldTypesStr;

    private TRMClientDataSet FClientDataSet;

    public RMDataSet()
    {
		FErrorFlag = false;
		FClientDataSet = new TRMClientDataSet();    	

        FFieldNames = new Vector();
        FFieldTypes = new Vector();
        FFieldSizes = new Vector();
        FFieldAttrNames = new Vector();
        FFieldTypesStr = new Vector();
    }

    public void Clear()
    {
        FErrorFlag = false;

        FFieldNames.clear();
        FFieldTypes.clear();
        FFieldSizes.clear();
        FFieldAttrNames.clear();
        FFieldTypesStr.clear();
    }
    
    public void AddRow(RMDataRecord aDataRecord)
    {
        try
        {
            int i;
            int lFieldType;
            StringBuffer lStrList = new StringBuffer("");
            Object lObj;
            Integer tmp;
            String lFieldName = new String("");

			lStrList.setLength(0);
            lStrList.append("<ROW");

			for (i = 0; i < FFieldNames.size(); i++)
			{
            	lObj = null;
                lFieldName = FFieldAttrNames.get(i).toString();
                tmp = (Integer)(FFieldTypes.get(i));
                lFieldType = tmp.intValue();
                    
                if(lFieldType == Types.BLOB || lFieldType == Types.BINARY || 
                	lFieldType == Types.LONGVARBINARY || lFieldType == Types.VARBINARY)  
                {
					lObj = aDataRecord.GetBinaryStream( FFieldNames.get(i).toString() );
					if (lObj != null)
                    {
	                    String s5 = RMUtils.BASE64EncodeStream((InputStream)lObj);
                        lObj = s5.endsWith("\n") ? ((Object) (s5.substring(0, s5.length() - 2))) : ((Object) (s5));
                        s5 = null;
                    }
                } 
                else
                {
                	lObj = aDataRecord.GetValue(FFieldNames.get(i).toString());
                	if (lObj != null)
                	{
						lObj = RMUtils.XMLEncoding(lObj.toString());
					}
                }

                lObj = lObj != null ? lObj : "";
                lStrList.append(" " + lFieldName + "=\"" + lObj + "\"");
			}

            lStrList.append("/>");
            FClientDataSet.addRow(lStrList.toString());
        }
        catch(Exception e)
        {
        	FErrorFlag = true;
        }
    }

    public void AddField(String aFieldName, int aFieldType, int aFieldSize)
    {
    	try
    	{
            String lStr = new String("");

    		FFieldNames.addElement(aFieldName);
    		FFieldTypes.addElement(new Integer(aFieldType));
    		FFieldSizes.addElement(new Integer(aFieldSize));

            lStr = aFieldName;
            lStr = lStr.replace(' ', '_');
			lStr = lStr.replace('(', '_');
			lStr = lStr.replace(')', '_');
   				
   			FFieldAttrNames.addElement(lStr);
   			FFieldTypesStr.addElement(RMUtils.GetFieldType(aFieldType, 1));
    	}
    	catch(Exception e)
    	{
    		FErrorFlag = true;
    	}
    }
    
    public TRMClientDataSet GetClientDataSet()
    {
    	try
    	{
			if (FErrorFlag)
			{
				return null;
			}

            String lFieldName = new String("");
            String lFieldTypeStr = new String("");
			String lAttrName = new String("");
            StringBuffer lStrList = new StringBuffer("");
            int i;
            int lFieldSize;
            int lFieldType;
            Integer tmp;
            RMDataRecord lRecord;
            Object lObj;

            lStrList.setLength(0);
            for(i = 0; i < FFieldNames.size(); i++)
            {
                lFieldName = FFieldNames.get(i).toString();
                lAttrName = FFieldAttrNames.get(i).toString();
                lFieldTypeStr = FFieldTypesStr.get(i).toString();
				
				if (lAttrName.equalsIgnoreCase(lFieldName))
				{
                	lStrList.append("<FIELD attrname=\"" + lFieldName + "\"");
                }
                else
                {
                	lStrList.append("<FIELD fieldname=\"" + lFieldName + "\" attrname=\"" + lAttrName + "\"");
                }
                
                lStrList.append(lFieldTypeStr);
                tmp = (Integer)FFieldSizes.get(i);
                lFieldSize = tmp.intValue();
                if (lFieldSize < 1)
                {
                	lFieldSize = 512;
                }
                if (lFieldTypeStr.indexOf("string") != -1)
                {
                    lStrList.append(" WIDTH=\"" + lFieldSize + "\"");
                }
                
                lStrList.append("/>");
            }

            FClientDataSet.AddFields("<FIELDS>" + lStrList.toString() + "</FIELDS>");
            return FClientDataSet;
    	}
        catch(Exception exception)
        {
        }
        
        return null;
    }
}
