package com.sinog2c.model.file;

import java.util.Date;

import com.sinog2c.model.BlueOALogAnnotation;
import com.sinog2c.model.RequireLog;

@BlueOALogAnnotation(TableName="FileFolderPublic",Description="公共文件柜信息")
public class FileFolderPublic {
	@RequireLog
    private Long id;

    private Long parentId;

    @RequireLog
    private String folderName;

    private Long folderOrder;

    private Date createTime;

    private String createUser;
    
    private Long attchmentId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public String getFolderName() {
        return folderName;
    }

    public void setFolderName(String folderName) {
        this.folderName = folderName == null ? null : folderName.trim();
    }

    public Long getFolderOrder() {
        return folderOrder;
    }

    public void setFolderOrder(Long folderOrder) {
        this.folderOrder = folderOrder;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser == null ? null : createUser.trim();
    }

	public Long getAttchmentId() {
		return attchmentId;
	}

	public void setAttchmentId(Long attchmentId) {
		this.attchmentId = attchmentId;
	}
}