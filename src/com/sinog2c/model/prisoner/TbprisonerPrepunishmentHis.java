package com.sinog2c.model.prisoner;

import java.util.Date;

public class TbprisonerPrepunishmentHis {
    private Integer punid;

    private String crimid;

    private String type;

    private Date punishmentdate;

    private String timelimit;

    private Date starttime;

    private Date endtime;

    private String causeaction;

    private String executingorgan;

    private String organname;

    private String organarea;

    private String remark;

    private String opid;

    private Date optime;

    public Integer getPunid() {
        return punid;
    }

    public void setPunid(Integer punid) {
        this.punid = punid;
    }

    public String getCrimid() {
        return crimid;
    }

    public void setCrimid(String crimid) {
        this.crimid = crimid == null ? null : crimid.trim();
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    public Date getPunishmentdate() {
        return punishmentdate;
    }

    public void setPunishmentdate(Date punishmentdate) {
        this.punishmentdate = punishmentdate;
    }

    public String getTimelimit() {
        return timelimit;
    }

    public void setTimelimit(String timelimit) {
        this.timelimit = timelimit == null ? null : timelimit.trim();
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

    public String getCauseaction() {
        return causeaction;
    }

    public void setCauseaction(String causeaction) {
        this.causeaction = causeaction == null ? null : causeaction.trim();
    }

    public String getExecutingorgan() {
        return executingorgan;
    }

    public void setExecutingorgan(String executingorgan) {
        this.executingorgan = executingorgan == null ? null : executingorgan.trim();
    }

    public String getOrganname() {
        return organname;
    }

    public void setOrganname(String organname) {
        this.organname = organname == null ? null : organname.trim();
    }

    public String getOrganarea() {
        return organarea;
    }

    public void setOrganarea(String organarea) {
        this.organarea = organarea == null ? null : organarea.trim();
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