package com.sinog2c.model.officeAssistant;

import java.util.Date;
import java.util.List;

import com.sinog2c.model.user.UserGrantDetail;
/**
 * 用户通知信息表
 * @author shily
 * 2014-7-9 15:33:57
 */
public class TbuserNotice {
	
	/**
	 * 用户授权数据,在 messagetype = 1 时用于传参,不参与持久化
	 */
	private List<UserGrantDetail> grantDetails;
	public List<UserGrantDetail> getGrantDetails() {
		return grantDetails;
	}
	public void setGrantDetails(List<UserGrantDetail> grantDetails) {
		this.grantDetails = grantDetails;
	}
	
    private Integer noticeid=0;

    private Integer messagetype;

    private String title;

    private String content;

    // 被授权人
    private String username;

    private Date endtime;

    private Date starttime;

    private int state;

    private Date canceltime;

    private String text1;

    private String text2;

    private String text3;

    private String remark;

    private Date optime;

    private String opid;

    //添加一个返回状态的接收 属性
    private String czstate;
    public Integer getNoticeid() {
        return noticeid;
    }

    public void setNoticeid(Integer noticeid) {
        this.noticeid = noticeid;
    }

    public Integer getMessagetype() {
        return messagetype;
    }

    public void setMessagetype(Integer messagetype) {
        this.messagetype = messagetype;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }

    public Date getEndtime() {
        return endtime;
    }

    public void setEndtime(Date endtime) {
        this.endtime = endtime;
    }

    public Date getStarttime() {
        return starttime;
    }

    public void setStarttime(Date starttime) {
        this.starttime = starttime;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public Date getCanceltime() {
        return canceltime;
    }

    public void setCanceltime(Date canceltime) {
        this.canceltime = canceltime;
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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
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
	public String getCzstate() {
		return czstate;
	}
	public void setCzstate(String czstate) {
		this.czstate = czstate;
	}
    
}