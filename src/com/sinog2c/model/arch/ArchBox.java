package com.sinog2c.model.arch;

import com.sinog2c.model.BlueOALogAnnotation;
import com.sinog2c.model.RequireLog;

@BlueOALogAnnotation(TableName="ArchBox",Description="卷盒信息")
public class ArchBox {
	@RequireLog
    private Long id;
	@RequireLog
    private String boxNo;

    private String createUser;

    private String orgId;

    private Long isMove;

    private Long year;

    private Long placeId;

    private String remark;

    private Long isArch;

    private String saveDate;

    private String secret;

    private String docNum;
    
    private String createUserName;
    
    private String action;
    
    private int docsum;
    
    private String formatName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBoxNo() {
        return boxNo;
    }

    public void setBoxNo(String boxNo) {
        this.boxNo = boxNo;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser == null ? null : createUser.trim();
    }

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId == null ? null : orgId.trim();
    }

    public Long getIsMove() {
        return isMove;
    }

    public void setIsMove(Long isMove) {
        this.isMove = isMove;
    }

    public Long getYear() {
        return year;
    }

    public void setYear(Long year) {
        this.year = year;
    }

    public Long getPlaceId() {
        return placeId;
    }

    public void setPlaceId(Long placeId) {
        this.placeId = placeId;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public Long getIsArch() {
        return isArch;
    }

    public void setIsArch(Long isArch) {
        this.isArch = isArch;
    }

   

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret == null ? null : secret.trim();
    }

    public String getDocNum() {
        return docNum;
    }

    public void setDocNum(String docNum) {
        this.docNum = docNum == null ? null : docNum.trim();
    }

	public String getCreateUserName() {
		return createUserName;
	}

	public void setCreateUserName(String createUserName) {
		this.createUserName = createUserName;
	}

	public String getSaveDate() {
		return saveDate;
	}

	public void setSaveDate(String saveDate) {
		this.saveDate = saveDate;
	}

	public int getDocsum() {
		return docsum;
	}

	public void setDocsum(int docsum) {
		this.docsum = docsum;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getFormatName() {
		return formatName;
	}

	public void setFormatName(String formatName) {
		this.formatName = formatName;
	}
}