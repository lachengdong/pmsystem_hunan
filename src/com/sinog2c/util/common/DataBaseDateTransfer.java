package com.sinog2c.util.common;

import java.io.BufferedInputStream;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.security.web.header.writers.frameoptions.StaticAllowFromStrategy;

import com.gkzx.common.GkzxCommon;

/**
 * 项目名称：pmisys 类名称：DataBaseDateTransfer 此类描述的是： json 与 resutl 数据转化
 * 
 * @author: lxf
 * @version: 2014-8-24 上午10:23:28
 */

public class DataBaseDateTransfer {

	/**
	 * 此方法描述的是：json date 类型转化
	 * 
	 * @param name
	 * @param @return
	 * @Exception 异常对象
	 * @author: lxf
	 * @version: 2014-8-24 上午10:29:52
	 */

	public static Timestamp ToDate(Object o) {
		try {
			if (o.getClass() == String.class) {
				if(null!=o&&o.toString().length()==8){
					DateFormat format = new SimpleDateFormat(GkzxCommon.DATESIMFORMAT);
					o = format.parse(o.toString());
					format = null;
				}else if(null!=o&&o.toString().length()==10){
					DateFormat format = new SimpleDateFormat(GkzxCommon.DATEFORMAT);
					o = format.parse(o.toString());
					format = null;
				}
				else{
					DateFormat format = new SimpleDateFormat(GkzxCommon.DATETIMEFORMAT);
					o = format.parse(o.toString());
					format = null;
				}
				return new java.sql.Timestamp(((Date) o).getTime());
			}
			return o != null ? new java.sql.Timestamp(((Date) o).getTime())
					: null;
		} catch (Exception ex) {
			return null;
		}
	}

	/**
	 * 此方法描述的是：json int 类型转化
	 * 
	 * @param name
	 * @param @return
	 * @Exception 异常对象
	 * @author: lxf
	 * @version: 2014-8-24 上午10:30:53
	 */

	public static int ToInt(Object o) {
		if (o == null)
			return 0;
		double d = Double.parseDouble(o.toString());
		int i = 0;
		i -= d;
		return -i;
	}

	/**
	 * 此方法描述的是：json String 类型转化
	 * 
	 * @param name
	 * @param @return
	 * @Exception 异常对象
	 * @author: lxf
	 * @version: 2014-8-24 上午10:31:19
	 */

	public static String ToString(Object o) {
		String rtn = "";
		if (o == null)
			return "";
		try {
			rtn = o.toString();
		} catch (Exception e) {
		}
		return rtn;
	}
	public static BigDecimal ToBigDecimal(Object o) {
		if(null==o||"".equals(o)||"''".equals(o)){
			 o=0;
		 }
		BigDecimal df = new BigDecimal(0);
		try {
			df = new BigDecimal(o.toString()); 
		} catch (Exception e) {
		}
		 return df;
	}
	public static Float ToFloat(Object o) {
		Float rtnFloat = new Float(0);
		if(null==o||"".equals(o)){
			 o=0;
		 }
		try {
			rtnFloat = Float.valueOf(o.toString());
		} catch (Exception e) {
		}
		 return rtnFloat;
	}
	/**
	 * 此方法描述的是：数据库 clob 类型转化string 放入json
	 * 
	 * @param name
	 * @param @return
	 * @Exception 异常对象
	 * @author: lxf
	 * @version: 2014-8-24 上午10:30:23
	 */

	public static String clob2String(Clob clob) {
		try {
			return (clob != null ? clob.getSubString(1, (int) clob.length())
					: null);
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	public static String  getZhuInCreaseZhuJian(String type){
		String value="";
		return value;
	}
	public static byte[] blob2String(Blob blob) {
		try {
			BufferedInputStream bin = new BufferedInputStream(blob.getBinaryStream());
			byte[] bytes = new byte[(int) blob.length()];
			int len = bytes.length;
			int offset = 0;
			int read = 0;
			while (offset < len && (read = bin.read(bytes, offset, len - offset)) >= 0) {
			offset += read;
			}
			if (bin != null){
				bin.close();
			}
			return bytes;

		} catch (Exception  e) {
			e.printStackTrace();
			return null;
		}
	}
	public static Clob toClob(String str){
		Clob clob = null;
		if(null!=str){
			try {
				clob =  new javax.sql.rowset.serial.SerialClob(str.toCharArray());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return clob;
	}
	public static Blob toBlob(String str){
		Blob blob = null;
		if(null!=str){
			try {
				blob = new javax.sql.rowset.serial.SerialBlob(str.getBytes());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return blob;
	}
}
