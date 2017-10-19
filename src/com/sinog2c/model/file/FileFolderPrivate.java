package com.sinog2c.model.file;

import java.util.Date;

import com.sinog2c.model.BlueOALogAnnotation;
import com.sinog2c.model.RequireLog;

@BlueOALogAnnotation(TableName="File_Folder_Private",Description="个人文件柜信息")
public class FileFolderPrivate {
	@RequireLog
    private Long id;

    private Long parentId;

    private Long folderOrder;

    @RequireLog
    private String folderName;

    private String createUser;

    private Date createTime;
    
    private Long attchmentId;
    
    private int isShared;
    
    private Short isEditable;

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

    public Long getFolderOrder() {
        return folderOrder;
    }

    public void setFolderOrder(Long folderOrder) {
        this.folderOrder = folderOrder;
    }

    public String getFolderName() {
        return folderName;
    }

    public void setFolderName(String folderName) {
        this.folderName = folderName == null ? null : folderName.trim();
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser == null ? null : createUser.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

	public Long getAttchmentId() {
		return attchmentId;
	}

	public void setAttchmentId(Long attchmentId) {
		this.attchmentId = attchmentId;
	}

	public int getIsShared() {
		return isShared;
	}

	public void setIsShared(int isShared) {
		this.isShared = isShared;
	}

	public Short getIsEditable() {
		return isEditable;
	}

	public void setIsEditable(Short isEditable) {
		this.isEditable = isEditable;
	}
}