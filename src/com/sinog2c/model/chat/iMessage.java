package com.sinog2c.model.chat;

import java.util.Date;

public class iMessage {
    private Long id;

    private String createUser;

    private String createusername;

    private String toUid;

    private Short remindFlag;

    private Short deleteFlag;

    private Date createTime;

    private Short msgType;

    private String content;

    private Long duration;

    private Short unread;
    
    private int isauthor;
    
    private String time;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser == null ? null : createUser.trim();
    }

    public String getCreateusername() {
        return createusername;
    }

    public void setCreateusername(String createusername) {
        this.createusername = createusername == null ? null : createusername.trim();
    }

    public String getToUid() {
        return toUid;
    }

    public void setToUid(String toUid) {
        this.toUid = toUid == null ? null : toUid.trim();
    }

    public Short getRemindFlag() {
        return remindFlag;
    }

    public void setRemindFlag(Short remindFlag) {
        this.remindFlag = remindFlag;
    }

    public Short getDeleteFlag() {
        return deleteFlag;
    }

    public void setDeleteFlag(Short deleteFlag) {
        this.deleteFlag = deleteFlag;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Short getMsgType() {
        return msgType;
    }

    public void setMsgType(Short msgType) {
        this.msgType = msgType;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    public Long getDuration() {
        return duration;
    }

    public void setDuration(Long duration) {
        this.duration = duration;
    }

    public Short getUnread() {
        return unread;
    }

    public void setUnread(Short unread) {
        this.unread = unread;
    }

	public int getIsauthor() {
		return isauthor;
	}

	public void setIsauthor(int isauthor) {
		this.isauthor = isauthor;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}
}