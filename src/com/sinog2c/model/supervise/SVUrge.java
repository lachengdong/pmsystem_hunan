package com.sinog2c.model.supervise;

import java.util.Date;
import java.util.List;

import com.sinog2c.model.flow.TbflowInstanceTask;

public class SVUrge {
    private Long id;

    private Long taskId;

    private String authorId;

    private String toId;

    private Short type;

    private String content;

    private Short deleteFlag;

    private Date createTime;
    
    private List<TbflowInstanceTask> items;
    
    private String flowid;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getTaskId() {
        return taskId;
    }

    public void setTaskId(Long taskId) {
        this.taskId = taskId;
    }

    public String getAuthorId() {
        return authorId;
    }

    public void setAuthorId(String authorId) {
        this.authorId = authorId == null ? null : authorId.trim();
    }

    public String getToId() {
        return toId;
    }

    public void setToId(String toId) {
        this.toId = toId == null ? null : toId.trim();
    }

    public Short getType() {
        return type;
    }

    public void setType(Short type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
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

	public List<TbflowInstanceTask> getItems() {
		return items;
	}

	public void setItems(List<TbflowInstanceTask> items) {
		this.items = items;
	}

	public String getFlowid() {
		return flowid;
	}

	public void setFlowid(String flowid) {
		this.flowid = flowid;
	}
}