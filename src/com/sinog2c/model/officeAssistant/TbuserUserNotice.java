package com.sinog2c.model.officeAssistant;

import java.util.Date;

/**
 * 用户与通知关系表
 * @author shily
 * 2014-7-9 15:34:27
 */
public class TbuserUserNotice {
    private String userid;

    private Integer noticeid;

    private Date optime;

    private String opid;

    
    //start add by blue_lv 2015-07-15
    private String taskid;
    
    public String getTaskid() {
		return taskid;
	}

	public void setTaskid(String taskid) {
		this.taskid = taskid;
	}
	
	//end add by blue_lv 2015-07-15
	
    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid == null ? null : userid.trim();
    }

    public Integer getNoticeid() {
        return noticeid;
    }

    public void setNoticeid(Integer noticeid) {
        this.noticeid = noticeid;
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
    
    
}