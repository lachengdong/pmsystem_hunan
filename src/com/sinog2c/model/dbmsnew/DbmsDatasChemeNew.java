package com.sinog2c.model.dbmsnew;

import java.util.Date;
import java.util.List;

public class DbmsDatasChemeNew {
    private String ddcid;

    private String ddcname;

    private String ddctype;

    private String fromdatabaseid;
    
    private String fromdatabasename;

    private String todatabaseid;
    
    private String todatabasename;

    private String updatemender;

    private Date updatetime;

    private String ddcexpscheme;

    private Date createtime;

    private String createmender;

    private String delflg;

    private String sdid;

    private String autorunorder;

    private String autoruncondition;

    private String autoruninsertonly;
    
    // 数据表.不参与持久化
    private List<DbmsDatasTableNew> tableList = null;
    // 数据表.不参与持久化
    private List<DbmsDatasChemeDetailNew> detailList = null;
    
    public void setTableList(List<DbmsDatasTableNew> tableList) {
		this.tableList = tableList;
	}
    public List<DbmsDatasTableNew> getTableList() {
		return tableList;
	}
    
    public void setDetailList(List<DbmsDatasChemeDetailNew> detailList) {
		this.detailList = detailList;
	}
    
    public List<DbmsDatasChemeDetailNew> getDetailList() {
		return detailList;
	}

    public String getDdcid() {
        return ddcid;
    }

    public void setDdcid(String ddcid) {
        this.ddcid = ddcid == null ? null : ddcid.trim();
    }

    public String getDdcname() {
        return ddcname;
    }
    public void setDdcname(String ddcname) {
        this.ddcname = ddcname == null ? null : ddcname.trim();
    }
    public String getDdctype() {
        return ddctype;
    }

    public void setDdctype(String ddctype) {
        this.ddctype = ddctype == null ? null : ddctype.trim();
    }

    public String getFromdatabaseid() {
        return fromdatabaseid;
    }

    public void setFromdatabaseid(String fromdatabaseid) {
        this.fromdatabaseid = fromdatabaseid == null ? null : fromdatabaseid.trim();
    }
    public String getFromdatabasename() {
		return fromdatabasename;
	}
    public void setFromdatabasename(String fromdatabasename) {
		this.fromdatabasename = fromdatabasename;
	}

    public String getTodatabaseid() {
        return todatabaseid;
    }

    public void setTodatabaseid(String todatabaseid) {
        this.todatabaseid = todatabaseid == null ? null : todatabaseid.trim();
    }
    public String getTodatabasename() {
		return todatabasename;
	}
    public void setTodatabasename(String todatabasename) {
		this.todatabasename = todatabasename;
	}

    public String getUpdatemender() {
        return updatemender;
    }

    public void setUpdatemender(String updatemender) {
        this.updatemender = updatemender == null ? null : updatemender.trim();
    }

    public Date getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(Date updatetime) {
        this.updatetime = updatetime;
    }

    public String getDdcexpscheme() {
        return ddcexpscheme;
    }

    public void setDdcexpscheme(String ddcexpscheme) {
        this.ddcexpscheme = ddcexpscheme == null ? null : ddcexpscheme.trim();
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public String getCreatemender() {
        return createmender;
    }

    public void setCreatemender(String createmender) {
        this.createmender = createmender == null ? null : createmender.trim();
    }

    public String getDelflg() {
        return delflg;
    }

    public void setDelflg(String delflg) {
        this.delflg = delflg == null ? null : delflg.trim();
    }

    public String getSdid() {
        return sdid;
    }

    public void setSdid(String sdid) {
        this.sdid = sdid == null ? null : sdid.trim();
    }

    public String getAutorunorder() {
        return autorunorder;
    }

    public void setAutorunorder(String autorunorder) {
        this.autorunorder = autorunorder == null ? null : autorunorder.trim();
    }

    public String getAutoruncondition() {
        return autoruncondition;
    }

    public void setAutoruncondition(String autoruncondition) {
        this.autoruncondition = autoruncondition == null ? null : autoruncondition.trim();
    }

    public String getAutoruninsertonly() {
        return autoruninsertonly;
    }

    public void setAutoruninsertonly(String autoruninsertonly) {
        this.autoruninsertonly = autoruninsertonly == null ? null : autoruninsertonly.trim();
    }
}