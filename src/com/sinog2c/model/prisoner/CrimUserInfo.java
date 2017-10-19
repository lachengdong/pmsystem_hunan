package com.sinog2c.model.prisoner;

import java.util.Date;

/**
 * 该类用于服刑人员建立 狱务公开系统的操作用户
 * @author sun
 *
 */
public class CrimUserInfo {
public String id;//罪犯用户表id
public String crimid;//罪犯编号
public String crimpassword;//罪犯密码
public  Date birthday;//罪犯出生日期
public  String CrimUserName;//罪犯名称
public String getId() {
	return id;
}
public void setId(String id) {
	this.id = id;
}
public String getCrimid() {
	return crimid;
}
public void setCrimid(String crimid) {
	this.crimid = crimid;
}
public String getCrimpassword() {
	return crimpassword;
}
public void setCrimpassword(String crimpassword) {
	this.crimpassword = crimpassword;
}
public Date getBirthday() {
	return birthday;
}
public void setBirthday(Date birthday) {
	this.birthday = birthday;
}
public String getCrimUserName() {
	return CrimUserName;
}
public void setCrimUserName(String crimUserName) {
	CrimUserName = crimUserName;
}

	
	
}
