package com.sinog2c.model.attachment;

public class Attachment_item2 extends Attachment_item {

	/**
	 * 
	 */
	private static final long serialVersionUID = -182724448721321090L;

	private String module;
	private String path;
	private String model;
	private String attribute;
	private String code;
	private String strSize;
	private int canReade;
	private int canEdit;

	private String pk;

	public String getModule() {
		return module;
	}

	public void setModule(String module) {
		this.module = module;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getAttribute() {
		return attribute;
	}

	public void setAttribute(String attribute) {
		this.attribute = attribute;
	}

	public String getPk() {
		return pk;
	}

	public void setPk(String pk) {
		this.pk = pk;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public int getCanReade() {
		return canReade;
	}

	public void setCanReade(int canReade) {
		this.canReade = canReade;
	}

	public int getCanEdit() {
		return canEdit;
	}

	public void setCanEdit(int canEdit) {
		this.canEdit = canEdit;
	}

	public String getStrSize() {
		return strSize;
	}

	public void setStrSize(String strSize) {
		this.strSize = strSize;
	}
}
