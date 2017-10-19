package com.sinog2c.util.common;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.sinog2c.model.prisoner.CrimUserInfo;
import com.sinog2c.model.prisoner.TbprisonerBaseinfo;

/**
 * Util类用于处理日期类型数据字符串之间的转换，
 * 组装罪犯用户类
 * @author sun
 *
 */
public class UtilProcessData {

	/**
	 * 用于组装罪犯用户登录对象
	 * @param baseinfo
	 * @return
	 */
	public static CrimUserInfo getCrimUserInfo(TbprisonerBaseinfo baseinfo){
		CrimUserInfo  cuif=new CrimUserInfo();
		cuif.setCrimid(baseinfo.getCrimid());
		cuif.setCrimpassword(getPassword(baseinfo.getBirthday()));
		cuif.setCrimUserName(baseinfo.getName());
		cuif.setBirthday(baseinfo.getBirthday());
		return cuif ;
	}
	
	/**
	 * date 转换成 字符串 取出密码
	 * @param birthday
	 * @return
	 */
	public static String getPassword(Date birthday ){
		String  converted=null;
		SimpleDateFormat datefmat = new SimpleDateFormat("yyyy-MM-dd");
		//SimpleDateFormat toCharfmat = new SimpleDateFormat("yyyy年MM月dd日");
		
		
		converted=datefmat.format(birthday);
		String array[]=converted.split("-");
		       String passwordd=array[1]+array[2];
		return passwordd;
	}
/*	public static void main(String args[]){
		
		getPassword(new Date());
	}*/
	
}
