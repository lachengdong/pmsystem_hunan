package com.sinog2c.model.prisoner;

import java.util.Date;

public class TbPrisonerCaiChanXing {
    private String id;

    private String crimid;

    private Date zhixingdate;

    private String zhixingjine;

    private String zhixingjiguan;

    private String danjuhao;

    private String weizhixing;

    private Date optime;

    private String opid;

    private String caichantype;
    
    private String status;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getCrimid() {
        return crimid;
    }

    public void setCrimid(String crimid) {
        this.crimid = crimid == null ? null : crimid.trim();
    }

    public Date getZhixingdate() {
        return zhixingdate;
    }

    public void setZhixingdate(Date zhixingdate) {
        this.zhixingdate = zhixingdate;
    }

    public String getZhixingjine() {
        return zhixingjine;
    }

    public void setZhixingjine(String zhixingjine) {
        this.zhixingjine = zhixingjine == null ? null : zhixingjine.trim();
    }

    public String getZhixingjiguan() {
        return zhixingjiguan;
    }

    public void setZhixingjiguan(String zhixingjiguan) {
        this.zhixingjiguan = zhixingjiguan == null ? null : zhixingjiguan.trim();
    }

    public String getDanjuhao() {
        return danjuhao;
    }

    public void setDanjuhao(String danjuhao) {
        this.danjuhao = danjuhao == null ? null : danjuhao.trim();
    }

    public String getWeizhixing() {
        return weizhixing;
    }

    public void setWeizhixing(String weizhixing) {
        this.weizhixing = weizhixing == null ? null : weizhixing.trim();
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

    public String getCaichantype() {
        return caichantype;
    }

    public void setCaichantype(String caichantype) {
        this.caichantype = caichantype == null ? null : caichantype.trim();
    }

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
    
}