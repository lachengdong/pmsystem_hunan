package com.sinog2c.model.flow;

import java.util.Date;

public class FlowBaseArchives {
    private String personid;

    private String personname;

    private String archiveid;

    private Integer docyear;

    private String name;

    private String docid;

    private Short type;

    private Short rank;

    private Short isattached;

    private Short retention;

    private String classification;

    private String departid;
    
    private String remark;

    private String opid;

    private Date optime;

    public String getPersonid() {
        return personid;
    }

    public void setPersonid(String personid) {
        this.personid = personid == null ? null : personid.trim();
    }

    public String getPersonname() {
        return personname;
    }

    public void setPersonname(String personname) {
        this.personname = personname == null ? null : personname.trim();
    }

    public String getArchiveid() {
        return archiveid;
    }

    public void setArchiveid(String archiveid) {
        this.archiveid = archiveid;
    }

    public Integer getDocyear() {
        return docyear;
    }

    public void setDocyear(Integer docyear) {
        this.docyear = docyear;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getDocid() {
        return docid;
    }

    public void setDocid(String docid) {
        this.docid = docid == null ? null : docid.trim();
    }

    public Short getType() {
        return type;
    }

    public void setType(Short type) {
        this.type = type;
    }

    public Short getRank() {
        return rank;
    }

    public void setRank(Short rank) {
        this.rank = rank;
    }

    public Short getIsattached() {
        return isattached;
    }

    public void setIsattached(Short isattached) {
        this.isattached = isattached;
    }

    public Short getRetention() {
        return retention;
    }

    public void setRetention(Short retention) {
        this.retention = retention;
    }

    public String getClassification() {
        return classification;
    }

    public void setClassification(String classification) {
        this.classification = classification;
    }

    public String getDepartid() {
		return departid;
	}

	public void setDepartid(String departid) {
		this.departid = departid;
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