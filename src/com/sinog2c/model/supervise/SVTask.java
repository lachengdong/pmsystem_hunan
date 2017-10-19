package com.sinog2c.model.supervise;

import java.util.Date;

public class SVTask {
    private Long id;

    private Long categoryId;

    private String authorId;

    private Long parentId;

    private String title;

    private String content;

    private String userLeader;

    private String userMain;

    private String userHelp;

    private Short status;

    private Short deleteFlag;

    private Date beginTime;

    private Date endTime;

    private Date createTime;

    private Date updateTime;

    private Short lockStatus;

    private String category;

    private String flowid;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public String getAuthorId() {
        return authorId;
    }

    public void setAuthorId(String authorId) {
        this.authorId = authorId == null ? null : authorId.trim();
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    public String getUserLeader() {
        return userLeader;
    }

    public void setUserLeader(String userLeader) {
        this.userLeader = userLeader == null ? null : userLeader.trim();
    }

    public String getUserMain() {
        return userMain;
    }

    public void setUserMain(String userMain) {
        this.userMain = userMain == null ? null : userMain.trim();
    }

    public String getUserHelp() {
        return userHelp;
    }

    public void setUserHelp(String userHelp) {
        this.userHelp = userHelp == null ? null : userHelp.trim();
    }

    public Short getStatus() {
        return status;
    }

    public void setStatus(Short status) {
        this.status = status;
    }

    public Short getDeleteFlag() {
        return deleteFlag;
    }

    public void setDeleteFlag(Short deleteFlag) {
        this.deleteFlag = deleteFlag;
    }

    public Date getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(Date beginTime) {
        this.beginTime = beginTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Short getLockStatus() {
        return lockStatus;
    }

    public void setLockStatus(Short lockStatus) {
        this.lockStatus = lockStatus;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category == null ? null : category.trim();
    }

    public String getFlowid() {
        return flowid;
    }

    public void setFlowid(String flowid) {
        this.flowid = flowid == null ? null : flowid.trim();
    }
}