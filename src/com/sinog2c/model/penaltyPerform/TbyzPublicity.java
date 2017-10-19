package com.sinog2c.model.penaltyPerform;

import java.util.Date;

public class TbyzPublicity {
    private Integer pubid;

    private String content;

    private Integer type;

    private Date starttime;

    private Date endtime;

    private Date meetingtime;

    private String meetingaddress;

    private String meetingname;

    private Short ispublish;

    private String remark;

    private String opid;

    private Date optime;

    public Integer getPubid() {
        return pubid;
    }

    public void setPubid(Integer pubid) {
        this.pubid = pubid;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Date getStarttime() {
        return starttime;
    }

    public void setStarttime(Date starttime) {
        this.starttime = starttime;
    }

    public Date getEndtime() {
        return endtime;
    }

    public void setEndtime(Date endtime) {
        this.endtime = endtime;
    }

    public Date getMeetingtime() {
        return meetingtime;
    }

    public void setMeetingtime(Date meetingtime) {
        this.meetingtime = meetingtime;
    }

    public String getMeetingaddress() {
        return meetingaddress;
    }

    public void setMeetingaddress(String meetingaddress) {
        this.meetingaddress = meetingaddress == null ? null : meetingaddress.trim();
    }

    public String getMeetingname() {
        return meetingname;
    }

    public void setMeetingname(String meetingname) {
        this.meetingname = meetingname == null ? null : meetingname.trim();
    }

    public Short getIspublish() {
        return ispublish;
    }

    public void setIspublish(Short ispublish) {
        this.ispublish = ispublish;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public String getOpid() {
        return opid;
    }

    public void setOpid(String opid) {
        this.opid = opid == null ? null : opid.trim();
    }

    public Date getOptime() {
        return optime;
    }

    public void setOptime(Date optime) {
        this.optime = optime;
    }
}