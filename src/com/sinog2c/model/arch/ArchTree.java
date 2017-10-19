package com.sinog2c.model.arch;

import java.util.Date;

import com.sinog2c.model.BlueOALogAnnotation;
import com.sinog2c.model.RequireLog;

@BlueOALogAnnotation(TableName="ArchTree",Description="案卷信息")
public class ArchTree {
	@RequireLog
    private Long id;

    private String no;
    @RequireLog
    private String name;

    private String remark;

    private Long parentId;

    private Date createDate;

    private String createUser;

    private String type;
    
    private String action;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no == null ? null : no.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser == null ? null : createUser.trim();
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}
}