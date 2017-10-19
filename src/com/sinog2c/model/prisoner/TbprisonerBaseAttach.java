package com.sinog2c.model.prisoner;

import java.util.Date;

public class TbprisonerBaseAttach {
    private String criminalid;

    private Short prisonertype;

    private Date optime;

    private String opid;

    private String content;

    public String getCriminalid() {
        return criminalid;
    }

    public void setCriminalid(String criminalid) {
        this.criminalid = criminalid == null ? null : criminalid.trim();
    }

    public Short getPrisonertype() {
        return prisonertype;
    }

    public void setPrisonertype(Short prisonertype) {
        this.prisonertype = prisonertype;
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }
}