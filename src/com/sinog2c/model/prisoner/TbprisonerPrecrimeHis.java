package com.sinog2c.model.prisoner;

import java.math.BigDecimal;
import java.util.Date;
/**
 * 犯人以前犯罪纪录历史表
 * @author liuxin
 * 2014-7-24 ‏‎‏‎11:03:03
 */
public class TbprisonerPrecrimeHis {
    private BigDecimal precrimeid;

    private String crimid;

    private String type;

    private String verdictdate;

    private String originaldeadline;

    private String sentence;

    private Date starttime;

    private Date endtime;

    private String crimename;

    private String maincrime;

    private String executingorgan;

    private String organname;

    private String organclass;

    private String organarea;

    private String remark;

    private String opid;

    private Date optime;

    public BigDecimal getPrecrimeid() {
        return precrimeid;
    }

    public void setPrecrimeid(BigDecimal precrimeid) {
        this.precrimeid = precrimeid;
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

    public String getVerdictdate() {
        return verdictdate;
    }

    public void setVerdictdate(String verdictdate) {
        this.verdictdate = verdictdate == null ? null : verdictdate.trim();
    }

    public String getOriginaldeadline() {
        return originaldeadline;
    }

    public void setOriginaldeadline(String originaldeadline) {
        this.originaldeadline = originaldeadline == null ? null : originaldeadline.trim();
    }

    public String getSentence() {
        return sentence;
    }

    public void setSentence(String sentence) {
        this.sentence = sentence == null ? null : sentence.trim();
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

    public String getCrimename() {
        return crimename;
    }

    public void setCrimename(String crimename) {
        this.crimename = crimename == null ? null : crimename.trim();
    }

    public String getMaincrime() {
        return maincrime;
    }

    public void setMaincrime(String maincrime) {
        this.maincrime = maincrime == null ? null : maincrime.trim();
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

    public String getOrganclass() {
        return organclass;
    }

    public void setOrganclass(String organclass) {
        this.organclass = organclass == null ? null : organclass.trim();
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