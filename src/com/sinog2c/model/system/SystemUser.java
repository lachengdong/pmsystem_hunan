package com.sinog2c.model.system;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class SystemUser implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 2248323128735237350L;
	/**
	 * 用户组织信息,不直接参与持久化
	 */
	private SystemOrganization organization;
	/**
	 * 所属监狱.不参与持久化; 只有监区,分监区,监狱,以及监狱的子部门的用户有这个对象
	 */
	private SystemOrganization prison;
	/**
	 * 业务角色, 现在只用于对付天津需要显示角色名字的功能.
	 */
	private Set<SystemRole> bizroles;
	
	/**
	 * 获取组织机构ID,只有登录用户才能获取值
	 * @return
	 */
	public String getOrgid() {
		if(null != organization){
			return organization.getOrgid();
		}
        return null;
    }
	/**
	 * 获取监狱ID,监狱下面的登录用户才能获取到
	 * @return
	 */
	public String getPrisonid() {
		String prisonid = null;
		if(null != prison){
			String unitlevelP = prison.getUnitlevel();
			// 监狱级别
			if(SystemOrganization.UNITLEVEL_PRISON.equals(unitlevelP)){
				prisonid = prison.getOrgid();
			}
		}
		//
		if(null == prisonid){
			if(null != organization){
				// 判断另一个
				String unitlevelO = organization.getUnitlevel();
				// 监狱级别
				if(SystemOrganization.UNITLEVEL_PRISON.equals(unitlevelO)){
					prisonid = organization.getOrgid();
				}
			}
		}
		//
		if(null == prisonid){
			prisonid = "";
		}
        return prisonid;
    }
	/**
	 * 获取监狱简称,监狱下面的登录用户才能获取到
	 * @return
	 */
	public String getPrisonShortName() {
		String prisonShortName = null;
		if(null != prison){
			String unitlevelP = prison.getUnitlevel();
			// 监狱级别
			if(SystemOrganization.UNITLEVEL_PRISON.equals(unitlevelP)){
				prisonShortName = prison.getShortname();
			}
		}
		//
		if(null == prisonShortName){
			if(null != organization){
				// 判断另一个
				String unitlevelO = organization.getUnitlevel();
				// 监狱级别
				if(SystemOrganization.UNITLEVEL_PRISON.equals(unitlevelO)){
					prisonShortName = organization.getShortname();
				}
			}
		}
		//
		if(null == prisonShortName){
			prisonShortName = "";
		}
        return prisonShortName;
    }
	public SystemOrganization getOrganization() {
		return organization;
	}
	public void setOrganization(SystemOrganization organization) {
		this.organization = organization;
	}
	/**
	 * 获取所属监狱
	 * @return
	 */
	public SystemOrganization getPrison() {
		return prison;
	}
	public void setPrison(SystemOrganization prison) {
		this.prison = prison;
	}
	/**
	 * 用户资源信息,只用于单用户缓存; 
	 */
	private List<SystemResource> allResources;
	
	/**
	 * 用户下一个审批人信息,只用于单用户缓存; 
	 */
	private List<Map> nextApprovePerson;
	
	/**
	 *  上次刷新资源的时间. 如果需要到数据库中刷新,可以将此值设置为null
	 */
	private Date refreshResTime=null;
	
	private Date refreshApproveTime=null;
	/**
	 * 程序不应该调用此方法
	 * @return
	 */
	public List<SystemResource> getAllResources() {
		return allResources;
	}
	public void setAllResources(List<SystemResource> allResources) {
		this.allResources = allResources;
	}
	
	public List<Map> getNextApprovePerson() {
		return nextApprovePerson;
	}
	public void setNextApprovePerson(List<Map> nextApprovePerson) {
		this.nextApprovePerson = nextApprovePerson;
	}
	public Date getRefreshResTime() {
		return refreshResTime;
	}
	public void setRefreshResTime(Date refreshResTime) {
		this.refreshResTime = refreshResTime;
	}
	public Date getRefreshApproveTime() {
		return refreshApproveTime;
	}
	public void setRefreshApproveTime(Date refreshApproveTime) {
		this.refreshApproveTime = refreshApproveTime;
	}
	
	private String userid;

    private String name;

    private String password;

    private String duty;

    private String email;

    private Short gender;

    private String qq;

    private Date birthday;

    private String nationality;

    private String city;

    private String address;

    private String postcode;

    private String homephone;

    private String officephone;

    private String fax;

    private String mobile;

    private String question;

    private String answer;

    private Short sn;

    private String islocked;

    private String remark;

    private String delflag;

    private Short opflag;

    private String text1;

    private String text2;

    private String text3;

    private String text4;

    private String text5;

    private String text6;

    // 使用 int1 存放是否是管理员
    private Integer int1;

    private Integer int2;

    private Date optime;

    private String opid;
    
    private String departid;

	private List<UserRoleWrapper> rolelist;
	
	private List<String> roleremarklist;
	
	//start add by blue_lv 2015-07-14
	private String orgname;
	 
	public String getOrgname() {
			return orgname;
	}
	public void setOrgname(String orgname) {
			this.orgname = orgname;
	}
	//end add by blue_lv 2015-07-14
	
    public List<String> getRoleremarklist() {
		return roleremarklist;
	}
	public void setRoleremarklist(List<String> roleremarklist) {
		this.roleremarklist = roleremarklist;
	}
	public List<UserRoleWrapper> getRolelist() {
		return rolelist;
	}
	public void setRolelist(List<UserRoleWrapper> rolelist) {
		this.rolelist = rolelist;
	}
    
    public String getDepartid() {
		return departid;
	}

	public void setDepartid(String departid) {
		this.departid = departid;
	}

	public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid == null ? null : userid.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public String getDuty() {
        return duty;
    }

    public void setDuty(String duty) {
        this.duty = duty == null ? null : duty.trim();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    public Short getGender() {
        return gender;
    }

    public void setGender(Short gender) {
        this.gender = gender;
    }

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq == null ? null : qq.trim();
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality == null ? null : nationality.trim();
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city == null ? null : city.trim();
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode == null ? null : postcode.trim();
    }

    public String getHomephone() {
        return homephone;
    }

    public void setHomephone(String homephone) {
        this.homephone = homephone == null ? null : homephone.trim();
    }

    public String getOfficephone() {
        return officephone;
    }

    public void setOfficephone(String officephone) {
        this.officephone = officephone == null ? null : officephone.trim();
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax == null ? null : fax.trim();
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile == null ? null : mobile.trim();
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question == null ? null : question.trim();
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer == null ? null : answer.trim();
    }

    public Short getSn() {
        return sn;
    }

    public void setSn(Short sn) {
        this.sn = sn;
    }

    public String getIslocked() {
        return islocked;
    }

    public void setIslocked(String islocked) {
        this.islocked = islocked == null ? null : islocked.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }
    public String getDelflag() {
		return delflag;
	}
    public void setDelflag(String delflag) {
    	this.delflag = delflag == null ? null : delflag.trim();
	}
    public Short getOpflag() {
		return opflag;
	}
    public void setOpflag(Short opflag) {
		this.opflag = opflag;
	}

    public String getText1() {
        return text1;
    }

    public void setText1(String text1) {
        this.text1 = text1 == null ? null : text1.trim();
    }

    public String getText2() {
        return text2;
    }

    public void setText2(String text2) {
        this.text2 = text2 == null ? null : text2.trim();
    }

    public String getText3() {
        return text3;
    }

    public void setText3(String text3) {
        this.text3 = text3 == null ? null : text3.trim();
    }

    public String getText4() {
        return text4;
    }

    public void setText4(String text4) {
        this.text4 = text4 == null ? null : text4.trim();
    }

    public String getText5() {
        return text5;
    }

    public void setText5(String text5) {
        this.text5 = text5 == null ? null : text5.trim();
    }

    public String getText6() {
        return text6;
    }

    public void setText6(String text6) {
        this.text6 = text6 == null ? null : text6.trim();
    }

    public Integer getInt1() {
        return int1;
    }

    public void setInt1(Integer int1) {
        this.int1 = int1;
    }

    public Integer getInt2() {
        return int2;
    }

    public void setInt2(Integer int2) {
        this.int2 = int2;
    }

    public Date getOptime() {
        return optime;
    }

    public void setOptime(Date optime) {
        this.optime = optime;
    }

    public String getOpid() {
        return opid;
    }

    public void setOpid(String opid) {
        this.opid = opid == null ? null : opid.trim();
    }

	public Set<SystemRole> getBizroles() {
		return bizroles;
	}
	public void setBizroles(Set<SystemRole> bizroles) {
		this.bizroles = bizroles;
	}

    @Override
    public String toString() {
    	
    	return this.getName();
    }
}