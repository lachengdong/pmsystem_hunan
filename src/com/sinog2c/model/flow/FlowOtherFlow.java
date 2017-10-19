package com.sinog2c.model.flow;

import java.util.Date;

public class FlowOtherFlow {
	private String flowdraftid;
	
    private String otherid;

    private String flowid;
    
    private String tempid;

    private String opid;

    private Date optime;

    public String getOtherid() {
        return otherid;
    }

    public String getFlowdraftid() {
		return flowdraftid;
	}

	public void setFlowdraftid(String flowdraftid) {
		this.flowdraftid = flowdraftid;
	}

	public void setOtherid(String otherid) {
        this.otherid = otherid;
    }

    public String getFlowid() {
        return flowid;
    }

    public void setFlowid(String flowid) {
        this.flowid = flowid == null ? null : flowid.trim();
    }

    public String getTempid() {
		return tempid;
	}

	public void setTempid(String tempid) {
		this.tempid = tempid;
	}

	public String getOpid() {
        return opid;
    }

    public void setOpid(String opid) {
        this.opid = opid == null ? null : opid.trim();
    }

    public Date getOptime() {
        return optime;
    }

    public void setOptime(Date optime) {
        this.optime = optime;
    }
}