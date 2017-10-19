package com.sinog2c.model.prisoner;

import java.sql.Date;

public class ZpublicDaMtxx extends ZpublicDaMtxxKey {
    private String mtbm;

    private String mtlb;

    private String lrsj;

    private String sdid;

    private String yhbh;

    private String nr;
    
    
    //Date:20170303
    //author:wangbingkf
    private int isdone;//是否处理
    
    private Date donedate;//处理时间

    public String getMtbm() {
        return mtbm;
    }

    public void setMtbm(String mtbm) {
        this.mtbm = mtbm == null ? null : mtbm.trim();
    }

    public String getMtlb() {
        return mtlb;
    }

    public void setMtlb(String mtlb) {
        this.mtlb = mtlb == null ? null : mtlb.trim();
    }

    public String getLrsj() {
        return lrsj;
    }

    public void setLrsj(String lrsj) {
        this.lrsj = lrsj == null ? null : lrsj.trim();
    }

    public String getSdid() {
        return sdid;
    }

    public void setSdid(String sdid) {
        this.sdid = sdid == null ? null : sdid.trim();
    }

    public String getYhbh() {
        return yhbh;
    }

    public void setYhbh(String yhbh) {
        this.yhbh = yhbh == null ? null : yhbh.trim();
    }

    public String getNr() {
        return nr;
    }

    public void setNr(String nr) {
        this.nr = nr == null ? null : nr.trim();
    }

	public int getIsdone() {
		return isdone;
	}

	public void setIsdone(int isdone) {
		this.isdone = isdone;
	}

	public Date getDonedate() {
		return donedate;
	}

	public void setDonedate(Date donedate) {
		this.donedate = donedate;
	}
    
}