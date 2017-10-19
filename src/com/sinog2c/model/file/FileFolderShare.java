package com.sinog2c.model.file;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

public class FileFolderShare {
    private Long id;

    private Long folderId;

    private String iuid;

    private Short isEditable;

    private Date startTime;

    private Date endTime;
    
    private String username;
    
    private List<FileFolderShare> items;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getFolderId() {
        return folderId;
    }

    public void setFolderId(Long folderId) {
        this.folderId = folderId;
    }

    public String getIuid() {
        return iuid;
    }

    public void setIuid(String iuid) {
        this.iuid = iuid == null ? null : iuid.trim();
    }

    public Short getIsEditable() {
        return isEditable;
    }

    public void setIsEditable(Short isEditable) {
        this.isEditable = isEditable;
    }

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public List<FileFolderShare> getItems() {
		return items;
	}

	public void setItems(List<FileFolderShare> items) {
		this.items = items;
	}
}