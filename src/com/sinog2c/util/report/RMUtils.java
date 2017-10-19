package com.sinog2c.util.report;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.sql.Clob;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Types;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import sun.misc.BASE64Encoder;

import com.sinog2c.mvc.controller.outexecute.returnFilesCotroller;


/*------------------------------------------------------------------------------
 * 
 *----------------------------------------------------------------------------*/
public class RMUtils
{
    
    /*
     *  lientDataSet
     */
    public static TRMClientDataSet CreateClientDataSet(ResultSet aResultSet, 
        String s, String s1, int aUnicodeFlag)
    {
        return CreateClientDataSet(aResultSet, s, s1, aUnicodeFlag, false);
      
    }

    /*
     *  lientDataSet
     */
    public static TRMClientDataSet CreateClientDataSet(ResultSet aResultSet, 
        String s, String s1, int aUnicodeFlag, boolean aGoToFirst)
    {
        try
        {
      		if (aResultSet == null)
      		{
      			return null;
      		}
      		

            TRMClientDataSet lXmlDataSet = new TRMClientDataSet();
            ResultSetMetaData lResultSetMetaData = aResultSet.getMetaData();
            StringBuffer lStrList = new StringBuffer("");
            int i, j;
            int lFieldSize;
            int lFieldType;
            boolean lFlag;
            String lFieldName = new String("");
            String lFieldTypeStr = new String("");
			String lAttrName = new String("");
            String[] lAryFieldNames =  new String[lResultSetMetaData.getColumnCount()];
            String[] lAryAttrNames = new String[lResultSetMetaData.getColumnCount()];
            String[] lAryFieldTypesStr = new String[lResultSetMetaData.getColumnCount()];
			
            if(aGoToFirst)
            {
                try
                {
                    Method method = aResultSet.getClass().getMethod("beforeFirst", null);
                    aResultSet.beforeFirst();
                }
                catch(Exception exception1) 
                { 
                }
            }
            
            for(i = 1; i <= lResultSetMetaData.getColumnCount(); i++)
            {
                lFieldName = lResultSetMetaData.getColumnName(i);

                lAttrName = lFieldName;
                lAttrName = lAttrName.replace(' ', '_');
				lAttrName = lAttrName.replace('(', '_');
				lAttrName = lAttrName.replace(')', '_');
   				lAryFieldNames[i - 1] = lFieldName;
   				lAryAttrNames[i - 1] = lAttrName;       
   				lAryFieldTypesStr[i - 1] = GetFieldType(lResultSetMetaData.getColumnType(i), aUnicodeFlag);
            }
           
            // lientDataSet
            while(aResultSet.next())
            {
        
            	lStrList.setLength(0);
                lStrList.append("<ROW");
                for(i = 1; i <= lResultSetMetaData.getColumnCount(); i++)
                {
                    Object lObj = null;
                    lFieldName = lAryAttrNames[i - 1];
                    lFieldType = lResultSetMetaData.getColumnType(i);
                    
                    if(lFieldType == Types.BLOB || lFieldType == Types.BINARY || 
                    	lFieldType == Types.LONGVARBINARY || lFieldType == Types.VARBINARY)  //
                    {
                        if((lObj = aResultSet.getBinaryStream(i)) != null)
                        {
                            String s5 = BASE64EncodeStream((InputStream)lObj);
                            lObj = s5.endsWith("\n") ? ((Object) (s5.substring(0, s5.length() - 2))) : ((Object) (s5));
                        }
                    } 
                    else
                    {
                        lObj = aResultSet.getString(i);

						lObj = XMLEncoding((String)lObj);
                    }

                    lFieldTypeStr = lAryFieldTypesStr[i - 1];
                    lFlag = lFieldTypeStr.indexOf("string") != -1;
                    if(lFlag) //
                    {
                        try
                        {
                            if(lObj == null)
                                lObj = "";
                                
                            if((s != null) && (s1 != null))
                                lObj = new String(((String)lObj).getBytes(s), s1);
                        }
                        catch(Exception exception2)
                        {
                            throw exception2;
                        }
                    }
                    
                    lObj = lObj != null ? lObj : "";
                    
                 	lStrList.append(" " + lFieldName + "=\"" + lObj + "\"");
                 
                } 
                lStrList.append("/>");
                lXmlDataSet.addRow(lStrList.toString());
            } 
            lStrList.setLength(0);
            for(i = 1; i <= lResultSetMetaData.getColumnCount(); i++)
            {
                lFieldName = lAryFieldNames[i - 1];
                lAttrName = lAryAttrNames[i - 1];
                lFieldTypeStr = lAryFieldTypesStr[i - 1];
				
				if (lAttrName.equalsIgnoreCase(lFieldName))
				{
                	lStrList.append("<FIELD attrname=\"" + lFieldName + "\"");
                }
                else
                {
                	lStrList.append("<FIELD fieldname=\"" + lFieldName + "\" attrname=\"" + lAttrName + "\"");
                }
                
                lStrList.append(lFieldTypeStr);
                lFieldSize = lResultSetMetaData.getColumnDisplaySize(i);
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
            lXmlDataSet.AddFields("<FIELDS>" + lStrList.toString() + "</FIELDS>");
            return lXmlDataSet;
        }
        catch(Exception exception)
        {
        }
        
        return null;
    }
    @SuppressWarnings("all")
    public static TRMClientDataSet CreateClientDataSet1(List<Map> listResult, 
            String s, String s1, int aUnicodeFlag, boolean aGoToFirst,List<Map> typeList){
            try{
            	//内容为空 直接返回 
          		if (listResult.size() == 0){
          			return null;
          		}
                TRMClientDataSet lXmlDataSet = new TRMClientDataSet();
                //组织的标签内容 放到报表当中
                StringBuffer lStrList = new StringBuffer("");
                int lFieldSize;//字段长度
                String lFieldName = new String("");//字段名称
                String lFieldTypeStr = new String("");//字段类型
    			String lAttrName = new String("");
    			String lAtrColumnWid = new String("");//字段大小
                String[] lAryFieldNames =  new String[listResult.get(0).size()];//字段内容
                String[] lAryAttrNames = new String[listResult.get(0).size()];
                String[] lAryFieldTypesStr = new String[listResult.get(0).size()];//字段类型
                String[] lAtrColuWidthStr = new String[listResult.get(0).size()];//创建一个 固定长度 数组
                
                Map laryFieldNamesMap = new HashMap(); //字段内容
                Map lAryAttrNamesMap = new HashMap(); 
                Map lAryFieldTypesStrMap = new HashMap();//字段类型 
                Map lAtrColuWidthStrMap = new HashMap(); //保存 字段内容对应的长度
                
                Map<Object, Object> mapWidth = new HashMap<Object, Object>();
                
                for(int i = 0; i <= listResult.size()-1; i++){
                    Map map = listResult.get(i);
                    Iterator iterator = map.keySet().iterator();
                    int jj = 0;
                    while(iterator.hasNext()){
                    	lFieldName =  iterator.next().toString();
                    	lAttrName = lFieldName;
                        lAttrName = lAttrName.replace(' ', '_');
        				lAttrName = lAttrName.replace('(', '_');
        				lAttrName = lAttrName.replace(')', '_');
        				laryFieldNamesMap.put(lFieldName, lFieldName);
        				lAryAttrNamesMap.put(lAttrName, lAttrName);
           				
           				//如果没有 查询字段类型 默认长度是 内容的长度
           				Object content = map.get(lFieldName)==null?"":map.get(lFieldName);
           				String type = content.getClass().getName();
           				String[] cType = {};
           				Object conLength = 0;
           				//因为查询方案提前把oracle.sql.clob转化为了string格式的数据 所以导致 报表无法根据数据类型来转化为报表对应的数据类型
           				//故需要根据一个标示来判断数据类型，要求所有的生成报表的大字段必须as成docconent
           				if ("docconent".equals(lAttrName)) {
           					//因为已经转为string 所以不需要再通过clob。getSubString方法进行转化
           					cType = getColumnType("oracle.sql.CLOB",content.toString());
           					conLength = content.toString().length();
						}else{
							if (content.toString().contains("oracle.sql.CLOB") || content.toString().contains("dm.jdbc.driver.DmdbClob")) {
	           					Clob clob = (Clob)content;
	           					content=clob.getSubString(1, (int)clob.length());
	           					cType = getColumnType("oracle.sql.CLOB",content.toString());
	           					conLength = content.toString().length();
							}else{
								cType = getColumnType(type,content.toString());
								conLength = content.toString().length();
							}
						}
           				
           				lAryFieldTypesStrMap.put(lFieldName, cType[0]);
   					    //获取内容的长度
   					    
   					    //字段的长度要取 内容最长的那个长度
   					    if (i != 0) {
   					    	// 已经 存在长度
							int width = Integer.parseInt(mapWidth.get(lAttrName)==null?"1":mapWidth.get(lAttrName).toString());
							// 现内容 长度
							int length = Integer.parseInt(conLength.toString());
							if (length > width) {
		   					    mapWidth.put(lAttrName, conLength);
		   					    lAtrColuWidthStrMap.put(lAttrName, conLength);
							}else{
								
							}
						}else{
	   					    mapWidth.put(lAttrName, conLength);
							//第一条数据 直接放到 数组里面
	   					    lAtrColuWidthStrMap.put(lAttrName, conLength);
						}
           				jj++;
                    }
                }
               
                // lientDataSet
                for(int i = 1; i <= listResult.size(); i++){
                	lStrList.setLength(0);
                	lStrList.append("<ROW");
                    Map map = listResult.get(i-1);
                    Iterator iterator1 = map.keySet().iterator();
                    while(iterator1.hasNext()){
                    	Object columnname = (Object) iterator1.next();
                    	Object obj = null;
                    	obj = map.get(columnname.toString())==null?"string":map.get(columnname);
                    	if ("docconent".equals(lAttrName)) {
                    		String[] cType = getColumnType("oracle.sql.CLOB",obj.toString());
	                    	obj = cType[1] != null ? cType[1] : "";
	                     	lStrList.append(" " + columnname + "=\"" + obj + "\"");
						}else{
							if (obj.toString().contains("oracle.sql.CLOB") || obj.toString().contains("dm.jdbc.driver.DmdbClob")) {
								Clob clob = (Clob)obj;
								obj=clob.getSubString(1, (int)clob.length());
								String[] cType = getColumnType("oracle.sql.CLOB",obj.toString());
		                    	obj = cType[1] != null ? cType[1] : "";
		                     	lStrList.append(" " + columnname + "=\"" + obj + "\"");
							}else{
								String clType = obj.getClass().getName();
//		                    	System.out.println(clType+"-------------------内容："+obj+"-------------字段名:"+columnname);
		                    	String[] cType = getColumnType(clType,obj.toString());
		                    	obj = cType[1] != null ? cType[1] : "";
		                     	lStrList.append(" " + columnname + "=\"" + obj + "\"");
							}
						}
                    }
                 	lStrList.append("/>");
                 	lXmlDataSet.addRow(lStrList.toString());
                }
                
            	lStrList.setLength(0);
                for (int k = 0; k < listResult.size(); k++) {
                    int mm = 0;
                    Map map2 = listResult.get(k);
                    Iterator iterator2 = map2.keySet().iterator();
                    while(iterator2.hasNext()){
                    	String key = iterator2.next().toString();
                    	String content = map2.get(key)==null?"":map2.get(key).toString();
                    	//放到数组 中 会出现 和map集合中的顺序不一致的情况 ，所以 把所有的内容都放入到map集合中通过key进行取值
//                    	lFieldName = lAryFieldNames[mm];//
                    	lFieldName = lAryAttrNamesMap.get(key).toString();
                    	
//                      lAttrName = lAryAttrNames[mm];//
                        lAttrName = lAryAttrNamesMap.get(key).toString();
                        
//                      lFieldTypeStr = lAryFieldTypesStr[mm];//字段类型
                        lFieldTypeStr = lAryFieldTypesStrMap.get(key).toString();
                        
//                      lAtrColumnWid = lAtrColuWidthStr[mm];//内容所占长度
//                        lAtrColumnWid = lAtrColuWidthStrMap.get(key).toString();
        				if (lAttrName.equalsIgnoreCase(lFieldName)){
                        	lStrList.append("<FIELD attrname=\"" + lFieldName + "\"");
                        }else{
                        	lStrList.append("<FIELD fieldname=\"" + lFieldName + "\" attrname=\"" + lAttrName + "\"");
                        }
        				String contentMaxLength = mapWidth.get(lAttrName)==null?"1":mapWidth.get(lAttrName).toString();
        				int contentMaxLengthNum = Integer.parseInt(contentMaxLength);
        				if(contentMaxLengthNum<=0){
        					contentMaxLengthNum=1;
        				}
        				lStrList.append(" WIDTH=\"" + contentMaxLengthNum*2 + "\"");
        				if (!"".equals(lFieldTypeStr)) {
        					
						}else{
							String string = "string";
        					lFieldTypeStr=" fieldtype=\"" + string + "\"";
						}
                        lStrList.append(lFieldTypeStr);
                        lStrList.append("/>");
                        mm++;
                    }
                    break;
				}
                lXmlDataSet.AddFields("<FIELDS>" + lStrList.toString() + "</FIELDS>");
//                System.out.println(lXmlDataSet.toString()+"|||||||||||||||||||||");
                return lXmlDataSet;
            }
            catch(Exception exception){
            	exception.printStackTrace();
            	System.out.println("错误原因:复合字段或者decode判断结果,未as成为一个单独的字段。");
            }
            return null;
        }
    /*
     *  lientDataSet
     */
    public static TRMClientDataSet CreateClientDataSet(ResultSet aResultSet, 
        String s, String s1, int aUnicodeFlag, boolean aGoToFirst,Map<String,String> map)
    {
        try
        {
      		if (aResultSet == null)
      		{
      			return null;
      		}
      		

            TRMClientDataSet lXmlDataSet = new TRMClientDataSet();
            ResultSetMetaData lResultSetMetaData = aResultSet.getMetaData();
            StringBuffer lStrList = new StringBuffer("");
            int i, j;
            int lFieldSize;
            int lFieldType;
            boolean lFlag;
            String lFieldName = new String("");
            String lFieldTypeStr = new String("");
			String lAttrName = new String("");
            String[] lAryFieldNames =  new String[lResultSetMetaData.getColumnCount()];
            String[] lAryAttrNames = new String[lResultSetMetaData.getColumnCount()];
            String[] lAryFieldTypesStr = new String[lResultSetMetaData.getColumnCount()];
			
            if(aGoToFirst)
            {
                try
                {
                    Method method = aResultSet.getClass().getMethod("beforeFirst", null);
                    aResultSet.beforeFirst();
                }
                catch(Exception exception1) 
                { 
                }
            }
            
            for(i = 1; i <= lResultSetMetaData.getColumnCount(); i++)
            {
                lFieldName = lResultSetMetaData.getColumnName(i);

                lAttrName = lFieldName;
                lAttrName = lAttrName.replace(' ', '_');
				lAttrName = lAttrName.replace('(', '_');
				lAttrName = lAttrName.replace(')', '_');
   				lAryFieldNames[i - 1] = lFieldName;
   				lAryAttrNames[i - 1] = lAttrName;       
   				lAryFieldTypesStr[i - 1] = GetFieldType(lResultSetMetaData.getColumnType(i), aUnicodeFlag);
            }
           boolean flag = true;
            // lientDataSet
            while(aResultSet.next())
            {	
            	flag = false;
            	Map<String,String> result = new HashMap<String, String>();
            	for(String key:map.keySet()){
            		String resultKey = aResultSet.getString(key);
            		result.put(resultKey, map.get(key));
            	}
            	lStrList.setLength(0);
                lStrList.append("<ROW");
                for(i = 1; i <= lResultSetMetaData.getColumnCount(); i++)
                {
                    Object lObj = null;
                    lFieldName = lAryAttrNames[i - 1];
                    lFieldType = lResultSetMetaData.getColumnType(i);
                    
                    if(lFieldType == Types.BLOB || lFieldType == Types.BINARY || 
                    	lFieldType == Types.LONGVARBINARY || lFieldType == Types.VARBINARY)  //
                    {
                        if((lObj = aResultSet.getBinaryStream(i)) != null)
                        {
                            String s5 = BASE64EncodeStream((InputStream)lObj);
                            lObj = s5.endsWith("\n") ? ((Object) (s5.substring(0, s5.length() - 2))) : ((Object) (s5));
                        }
                    } 
                    else
                    {
                        lObj = aResultSet.getString(i);

						lObj = XMLEncoding((String)lObj);
                    }

                    lFieldTypeStr = lAryFieldTypesStr[i - 1];
                    lFlag = lFieldTypeStr.indexOf("string") != -1;
                    if(lFlag) //
                    {
                        try
                        {
                            if(lObj == null)
                                lObj = "";
                                
                            if((s != null) && (s1 != null))
                                lObj = new String(((String)lObj).getBytes(s), s1);
                        }
                        catch(Exception exception2)
                        {
                            throw exception2;
                        }
                    }
                    
                    lObj = lObj != null ? lObj : "";
                    for(String key:result.keySet()){
                    	lObj = lObj.toString().replace(key, result.get(key));
                    }
                 	lStrList.append(" " + lFieldName + "=\"" + lObj + "\"");
                 
                } 
                lStrList.append("/>");
                lXmlDataSet.addRow(lStrList.toString());
            }
            
            lStrList.setLength(0);
            for(i = 1; i <= lResultSetMetaData.getColumnCount(); i++)
            {
                lFieldName = lAryFieldNames[i - 1];
                lAttrName = lAryAttrNames[i - 1];
                lFieldTypeStr = lAryFieldTypesStr[i - 1];
				
				if (lAttrName.equalsIgnoreCase(lFieldName))
				{
                	lStrList.append("<FIELD attrname=\"" + lFieldName + "\"");
                }
                else
                {
                	lStrList.append("<FIELD fieldname=\"" + lFieldName + "\" attrname=\"" + lAttrName + "\"");
                }
                
                lStrList.append(lFieldTypeStr);
                lFieldSize = lResultSetMetaData.getColumnDisplaySize(i);
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
            lXmlDataSet.AddFields("<FIELDS>" + lStrList.toString() + "</FIELDS>");
            return lXmlDataSet;
        }
        catch(Exception exception)
        {
        }
        
        return null;
    }
    /*
     * Unicode
     */
    public static String _ChangeToUnicodeStr(String s, int i)
    {
        if(i == -1)
            return s;
        StringBuffer stringbuffer = new StringBuffer("");
        for(int j = 0; j < s.length(); j++)
        {
            char c = s.charAt(j);
            if(c > '\377')
                switch(i)
                {
                case 0: // '\0'
                    stringbuffer.append("&#X" + Integer.toHexString(c) + ";");
                    break;

                case 1: // '\001'
                    stringbuffer.append("\\u" + Integer.toHexString(c));
                    break;

                case 2: // '\002'
                    stringbuffer.append("&#" + String.valueOf(c) + ";");
                    break;
                }
            else
                stringbuffer.append(c);
        }

        return stringbuffer.toString();
    }

    /*
     * Unicode
     */
    public static String _ChangeToUnicodeStr(String s)
    {
        return _ChangeToUnicodeStr(s, 0);
    }
    
    /**
     * 
     * @param s
     * @param s1
     * @param s2
     * @return
     */
    private static String _ReplaceStr(String s, String s1, String s2)
    {
        StringBuffer lStrBuffer;
        for(lStrBuffer = new StringBuffer(s); lStrBuffer.toString().indexOf(s1) != -1; lStrBuffer.replace(lStrBuffer.toString().indexOf(s1), lStrBuffer.toString().indexOf(s1) + s1.length(), s2))
            ;

        return lStrBuffer.toString();
    }

    public static String GetFieldType(int aFieldType, int aUnicodeFlag)
    {
        String s = "";
        String s1 = "";
        String s2 = "";
        
        switch(aFieldType)
        {
//            case Types.BOOLEAN:
//                s = "boolean";
//                break;
            case Types.SMALLINT:
                s = "i2";
                break;
            case Types.BIT: 
            case Types.TINYINT: 
            case Types.INTEGER:
            case Types.BIGINT:
                s = "i4";
                break;
            case Types.CHAR:
            case Types.VARCHAR:
                if (aUnicodeFlag == 2)
                {
                    s = "string.uni";
                }
                else
                {
                    s = "string";                   
                }
                break;
            case Types.DECIMAL:
            case Types.FLOAT:
            case Types.NUMERIC:
            case Types.REAL:
                s = "r8";
                break;
            case Types.DOUBLE:
            	s = "r8";
            	break;
            case Types.LONGVARBINARY: 
            case Types.VARBINARY: 
            case Types.BINARY: 
            case Types.BLOB: 
                s = "bin.hex";
                s1 = "Binary";
                break;
            case -1: 
            case Types.CLOB: 
                s = "bin.hex";
                s1 = "Text";
                break;
            case Types.DATE:
            case Types.TIME:
            case Types.TIMESTAMP:
                s = "DateTime";
                break;
            default:
                s = "string";
                break;
        }
        
        s2 = " fieldtype=\"" + s + "\"";
        if(!"".equals(s1))
            s2 = s2 + " SUBTYPE=\"" + s1 + "\"";
        return s2;
    }
    /**
     * 方法描述: 根据数据类型组织 报表需要的数据类型
     * @auhtor mushuhong
     * @version 2014年8月20日00:34:55
     * @param aInputStream
     * @return
     */
    public static String[] getColumnType(String clumntype,String content){
    	String string = "";
        boolean flag = false;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String text = "";
        String[] string1 = new String[2];
        String type ="";
    	try {
			if ("java.sql.Timestamp".equals(clumntype)) {
				string = "DateTime";
				text = format.format(format.parse(content));
			}else if("java.lang.String".equals(clumntype)){
				string = "string";
				text = content;
			}else if("oracle.sql.CLOB".equals(clumntype)){
			    string = "bin.hex";
			    type = "Text";
			    text = content;
			    flag=true;
			}else{
				string = "string";
				text = content;
			}
			string = " fieldtype=\""+string+"\"";
			if (flag) {
				string = string+" SUBTYPE=\""+type+"\"";
			}
			string1[0]=string;
			string1[1]=text;
		} catch (Exception e) {
			e.printStackTrace();
		}
    	return string1;
    }
    /*
     *  base64
     */
    public static String BASE64EncodeStream(InputStream aInputStream)
    {
        String lStr = "";

        if(aInputStream == null)
            return null;
            
        ByteArrayOutputStream lByteArrayOutputStream = new ByteArrayOutputStream();
        try
        {
            (new BASE64Encoder()).encodeBuffer(aInputStream, lByteArrayOutputStream);
            lStr = lByteArrayOutputStream.toString();
        }
        catch(Exception exception)
        {
        }
        finally
        {
            try
            {
                lByteArrayOutputStream.close();
            }
            catch(Exception exception2) 
            { 
            }
        }
        return lStr;
    }

    /*
     *  base64
     */
    public static String BASE64EncodeStream(byte abyte0[])
    {
        ByteArrayInputStream lByteArrayInputStream = new ByteArrayInputStream(abyte0);
        
        return BASE64EncodeStream(((InputStream) (lByteArrayInputStream)));
    }
    
    /**
     * 
     * @param aString
     * @param aTargetFileName
     */
    public static void WriteFile(String aString, String aTargetFileName)
    {
        try 
        {
            java.io.PrintWriter pw = new java.io.PrintWriter(new java.io.FileOutputStream(aTargetFileName));
         
            pw.println(aString);
            pw.close();
            aString = "";
        } 
        catch(java.io.IOException e) 
        {
        }    
    }   

    public static void WriteFile(byte aBytes[], String aTargetFileName)
    {
        byte[] lBuffer =new byte[2048]; 
		int lByteRead = 0;
		
		try
		{
	        ByteArrayInputStream lInStream = new ByteArrayInputStream(aBytes);
    	    FileOutputStream lOutStream = new FileOutputStream(aTargetFileName);

			lByteRead = lInStream.read(lBuffer);
			while (lByteRead != - 1)
			{
				lOutStream.write(lBuffer, 0, lByteRead);
				lByteRead = lInStream.read(lBuffer);
			}
			
			lInStream.close();
			lOutStream.close();
		}
		catch(Exception e)
		{
		}
	}
    
 /**
  * 
  * @param string
  * @return
  */
 	public String getStr(String string)
 	{
  		try
  		{
   			String temp_p=string;
   			byte[] temp_t=temp_p.getBytes("ISO8859-1");
   			String temp=new String(temp_t);
   			return temp;
  		}
  		catch(Exception e)
  		{
  		}
    
  		return "null";
 	}
    
 	/**
 	 * 字符串替换，
 	 * @param aStr
 	 * @param aOldString
 	 * @param aNewString
 	 * @return
 	 */
 	public static String ReplaceStr(String aStr, String aOldString, String aNewString)
 	{
  		int i = 0;
    
		if ((i = aStr.indexOf(aOldString, i)) >= 0) 
  		{
   			char [] line2 = aStr.toCharArray();
   			char [] newString2 = aNewString.toCharArray();
   			int oLength = aOldString.length();
   			StringBuffer lBuf = new StringBuffer(line2.length);
    
   			lBuf.append(line2, 0, i).append(newString2);
   			i += oLength;
   			int j = i;
   			while((i = aStr.indexOf(aOldString, i)) > 0) 
   			{
    			lBuf.append(line2, j, i-j).append(newString2);
    			i += oLength;
    			j = i;
   			}
    
   			lBuf.append(line2, j, line2.length - j);
   			return lBuf.toString();
  		}
    
  		return aStr;
 	}

    public static byte[] ZipStream(InputStream aInStream)
	        throws IOException
    {
		int lCount; 
        byte[] lBuffer = new byte[2048];
        ByteArrayOutputStream lTmpOutStream = new ByteArrayOutputStream();
        ZipOutputStream lZipOutStream = new ZipOutputStream(lTmpOutStream);
        ZipEntry lZipEntry = new ZipEntry("DATA");

		try
		{
	        lZipOutStream.putNextEntry(lZipEntry);
			while ((lCount = aInStream.read(lBuffer, 0, 2048)) != -1)
			{
				lZipOutStream.write(lBuffer, 0, lCount);
			}
		
			lZipOutStream.close();
			lTmpOutStream.close();
			return lTmpOutStream.toByteArray();
		}
    	catch(Exception e)
    	{
    		return null;
    	}
    }


	/**
	 * html编码
	 * @param aInStr
	 * @return
	 */
	public static String HTMLEncoding (String aInStr) 
	{   
		if (aInStr == null)        
   			return (null);     
   
   		char lAryChar[] = new char[aInStr.length()];    
   		aInStr.getChars(0, aInStr.length(), lAryChar, 0); 
   		StringBuffer lTmpBuf = new StringBuffer(lAryChar.length + 50);  
   		   
   		for (int i = 0; i < lAryChar.length; i++) 
   		{        
			switch (lAryChar[i]) 
			{         
				case '<':                
					lTmpBuf.append("<");             
					break;       
				case '>':        
					lTmpBuf.append(">");  
					break;          
				case '&':        
					lTmpBuf.append("&");   
					break;         
				case '"':        
					lTmpBuf.append("\"");       
					break;         
				case '\'':      
					lTmpBuf.append("'");  
					break;          
				default:	
					lTmpBuf.append(lAryChar[i]); 
					break;
			}     
		}     

		return (lTmpBuf.toString());   
	}

	/**
	 * xml编码
	 * @param aInStr
	 * @return
	 */
	public static String XMLEncoding(String aInStr) 
	{   
		if ((aInStr == null) || (aInStr.length() == 0))
   			return aInStr;     
   
   		char lAryChar[] = new char[aInStr.length()];    
   		aInStr.getChars(0, aInStr.length(), lAryChar, 0); 
   		StringBuffer lTmpBuf = new StringBuffer(lAryChar.length + 50); 
   		char lChar;  
   		  
   		for (int i = 0; i < lAryChar.length; i++) 
   		{        
			lChar = lAryChar[i];
			switch (lChar) 
			{         
				case '&':        
					lTmpBuf.append("&amp;");   
					break;         
				case '"':        
					lTmpBuf.append("&quot;");       
					break;   
				case 10:	      
					lTmpBuf.append("&#010;");       
					break;   
				default:	
					lTmpBuf.append(lChar); 
					break;
			}     
		}     

		return (lTmpBuf.toString());   
	}
}
