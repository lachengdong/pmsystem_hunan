package com.sinog2c.model.flow;

import java.util.Date;

public class FlowBaseOther {
    private String otherid;

    private Integer sn;

    private String text1;

    private String text2;

    private String text3;

    private String text4;

    private String text5;

    private String text6;
    
    private String text7;

    private Integer int1;

    private String int2;

    private String opid;

    private Date optime;

    private String docconent;

    public String getOtherid() {
		return otherid;
	}

	public void setOtherid(String otherid) {
		this.otherid = otherid;
	}

    public Integer getSn() {
        return sn;
    }

    public void setSn(Integer sn) {
        this.sn = sn;
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

    public String getInt2() {
        return int2;
    }

    public void setInt2(String int2) {
        this.int2 = int2;
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

    public String getDocconent() {
        return docconent;
    }

    public void setDocconent(String docconent) {
        this.docconent = docconent == null ? null : docconent.trim();
    }

	public String getText7() {
		return text7;
	}

	public void setText7(String text7) {
		this.text7 = text7;
	}
    
}