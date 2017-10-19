package com.sinog2c.model.arch;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sinog2c.model.BlueOALogAnnotation;
import com.sinog2c.model.RequireLog;

@BlueOALogAnnotation(TableName="ArchFile",Description="档案文件信息")
public class ArchFile {
	@RequireLog
    private Long id;

    private String docGuid;

    private String unitCode;

    private String fondsNo;

    private String year;

    private String saveDate;

    private String orgNo;

    private Long boxId;

    private Long fileNo;

    private String archiveNo;

    @RequireLog
    private String fileName;

    private String sendUnit;

    private String fileCode;

    private String carrierNo;

    private Date date1;

    private String carrierType;

    private String type;

    private String secret;

    private String urgency;

    private String remark;

    private String userId;

    private Short isArch;

    private Short isDel;

    private String docFlowid;
    
    private String action;
    
    private String orgName;
    
    private String boxName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDocGuid() {
        return docGuid;
    }

    public void setDocGuid(String docGuid) {
        this.docGuid = docGuid == null ? null : docGuid.trim();
    }

    public String getUnitCode() {
        return unitCode;
    }

    public void setUnitCode(String unitCode) {
        this.unitCode = unitCode == null ? null : unitCode.trim();
    }

    public String getFondsNo() {
        return fondsNo;
    }

    public void setFondsNo(String fondsNo) {
        this.fondsNo = fondsNo == null ? null : fondsNo.trim();
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year == null ? null : year.trim();
    }

    public String getSaveDate() {
        return saveDate;
    }

    public void setSaveDate(String saveDate) {
        this.saveDate = saveDate;
    }

    public String getOrgNo() {
        return orgNo;
    }

    public void setOrgNo(String orgNo) {
        this.orgNo = orgNo == null ? null : orgNo.trim();
    }

    public Long getBoxId() {
        return boxId;
    }

    public void setBoxId(Long boxId) {
        this.boxId = boxId;
    }

    public Long getFileNo() {
        return fileNo;
    }

    public void setFileNo(Long fileNo) {
        this.fileNo = fileNo;
    }

    public String getArchiveNo() {
        return archiveNo;
    }

    public void setArchiveNo(String archiveNo) {
        this.archiveNo = archiveNo == null ? null : archiveNo.trim();
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName == null ? null : fileName.trim();
    }

    public String getSendUnit() {
        return sendUnit;
    }

    public void setSendUnit(String sendUnit) {
        this.sendUnit = sendUnit == null ? null : sendUnit.trim();
    }

    public String getFileCode() {
        return fileCode;
    }

    public void setFileCode(String fileCode) {
        this.fileCode = fileCode == null ? null : fileCode.trim();
    }

    public String getCarrierNo() {
        return carrierNo;
    }

    public void setCarrierNo(String carrierNo) {
        this.carrierNo = carrierNo == null ? null : carrierNo.trim();
    }

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    public Date getDate1() {
        return date1;
    }

    public void setDate1(Date date1) {
        this.date1 = date1;
    }

    public String getCarrierType() {
        return carrierType;
    }

    public void setCarrierType(String carrierType) {
        this.carrierType = carrierType == null ? null : carrierType.trim();
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret == null ? null : secret.trim();
    }

    public String getUrgency() {
        return urgency;
    }

    public void setUrgency(String urgency) {
        this.urgency = urgency == null ? null : urgency.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }

    public Short getIsArch() {
        return isArch;
    }

    public void setIsArch(Short isArch) {
        this.isArch = isArch;
    }

    public Short getIsDel() {
        return isDel;
    }

    public void setIsDel(Short isDel) {
        this.isDel = isDel;
    }

    public String getDocFlowid() {
        return docFlowid;
    }

    public void setDocFlowid(String docFlowid) {
        this.docFlowid = docFlowid == null ? null : docFlowid.trim();
    }

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public String getBoxName() {
		return boxName;
	}

	public void setBoxName(String boxName) {
		this.boxName = boxName;
	}
}