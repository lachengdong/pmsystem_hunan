package com.sinog2c.model.flow;

public class TbflowDefineWithBLOBs extends TbflowDefine {
    private String nodedata;

    private String linedata;
    
    private String auto="0";

    public String getNodedata() {
        return nodedata;
    }

    public void setNodedata(String nodedata) {
        this.nodedata = nodedata == null ? null : nodedata.trim();
    }

    public String getLinedata() {
        return linedata;
    }

    public void setLinedata(String linedata) {
        this.linedata = linedata == null ? null : linedata.trim();
    }

	public String getAuto() {
		return auto;
	}

	public void setAuto(String auto) {
		this.auto = auto;
	}
}