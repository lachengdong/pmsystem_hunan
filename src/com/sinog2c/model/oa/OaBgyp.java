package com.sinog2c.model.oa;

import java.util.Date;

public class OaBgyp {
    private String bgypid;

    private String xuhao;

    private String wpname;

    private String lysl;

    private String lyrsign;

    private Date lyrq;

    private String remark;
    
    private Date optime;

    private String opid;

    public String getBgypid() {
        return bgypid;
    }

    public void setBgypid(String bgypid) {
        this.bgypid = bgypid == null ? null : bgypid.trim();
    }

    public String getXuhao() {
        return xuhao;
    }

    public void setXuhao(String xuhao) {
        this.xuhao = xuhao == null ? null : xuhao.trim();
    }

    public String getWpname() {
        return wpname;
    }

    public void setWpname(String wpname) {
        this.wpname = wpname == null ? null : wpname.trim();
    }

    public String getLysl() {
        return lysl;
    }

    public void setLysl(String lysl) {
        this.lysl = lysl == null ? null : lysl.trim();
    }

    public String getLyrsign() {
        return lyrsign;
    }

    public void setLyrsign(String lyrsign) {
        this.lyrsign = lyrsign == null ? null : lyrsign.trim();
    }

    public Date getLyrq() {
        return lyrq;
    }

    public void setLyrq(Date lyrq) {
        this.lyrq = lyrq;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

	public Date getOptime() {
		return optime;
	}

	public void setOptime(Date optime) {
		this.optime = optime;
	}

	public String getOpid() {
		return opid;
	}

	public void setOpid(String opid) {
		this.opid = opid == null ? null : opid.trim();
	}
    
}