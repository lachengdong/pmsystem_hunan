package com.sinog2c.model.attachment;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class Attachment implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5001317479917183280L;

	private Long id;

    private Long posId;

    private String module;

    private String path;

    private String model;

    private String attribute;

    private Long pk;

    private Date createTime;
    
    private List<Attachment_item> list;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPosId() {
        return posId;
    }

    public void setPosId(Long posId) {
        this.posId = posId;
    }

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module == null ? null : module.trim();
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path == null ? null : path.trim();
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model == null ? null : model.trim();
    }

    public String getAttribute() {
        return attribute;
    }

    public void setAttribute(String attribute) {
        this.attribute = attribute == null ? null : attribute.trim();
    }

    public Long getPk() {
        return pk;
    }

    public void setPk(Long pk) {
        this.pk = pk;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

	public List<Attachment_item> getList() {
		return list;
	}

	public void setList(List<Attachment_item> list) {
		this.list = list;
	}
}