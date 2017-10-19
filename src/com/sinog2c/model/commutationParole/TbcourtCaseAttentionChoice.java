package com.sinog2c.model.commutationParole;

import java.util.Date;

public class TbcourtCaseAttentionChoice {
    private Integer cacid;

    private Short sn;

    private String name;

    private String characteristic;

    private String crimetype;

    private String inprison;

    private String dutylevel;

    private String court;

    private Date receivestart;

    private Date receiveend;

    private String crimename;

    private String originalsentence;

    private String text1;

    private String text2;

    private String text3;

    private String text4;

    private String text5;

    private String text6;

    private String opid;

    private Date optime;

    public Integer getCacid() {
        return cacid;
    }

    public void setCacid(Integer cacid) {
        this.cacid = cacid;
    }

    public Short getSn() {
        return sn;
    }

    public void setSn(Short sn) {
        this.sn = sn;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getCharacteristic() {
        return characteristic;
    }

    public void setCharacteristic(String characteristic) {
        this.characteristic = characteristic == null ? null : characteristic.trim();
    }

    public String getCrimetype() {
        return crimetype;
    }

    public void setCrimetype(String crimetype) {
        this.crimetype = crimetype == null ? null : crimetype.trim();
    }

    public String getInprison() {
        return inprison;
    }

    public void setInprison(String inprison) {
        this.inprison = inprison == null ? null : inprison.trim();
    }

    public String getDutylevel() {
        return dutylevel;
    }

    public void setDutylevel(String dutylevel) {
        this.dutylevel = dutylevel == null ? null : dutylevel.trim();
    }

    public String getCourt() {
        return court;
    }

    public void setCourt(String court) {
        this.court = court == null ? null : court.trim();
    }

    public Date getReceivestart() {
        return receivestart;
    }

    public void setReceivestart(Date receivestart) {
        this.receivestart = receivestart;
    }

    public Date getReceiveend() {
        return receiveend;
    }

    public void setReceiveend(Date receiveend) {
        this.receiveend = receiveend;
    }

    public String getCrimename() {
        return crimename;
    }

    public void setCrimename(String crimename) {
        this.crimename = crimename == null ? null : crimename.trim();
    }

    public String getOriginalsentence() {
        return originalsentence;
    }

    public void setOriginalsentence(String originalsentence) {
        this.originalsentence = originalsentence == null ? null : originalsentence.trim();
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