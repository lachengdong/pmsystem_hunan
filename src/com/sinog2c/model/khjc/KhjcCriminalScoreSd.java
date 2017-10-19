package com.sinog2c.model.khjc;

import java.util.Date;

public class KhjcCriminalScoreSd {
    private String id;

    private String crimid;

    private String fenjianqu;

    private String jianqu;

    private String jianyu;

    private String jiangchengleixing;

    private String jiangchengleibie;

    private String fenshu;

    private Date filltime;

    private Date cashtime;

    private Date createtime;

    private String createmender;

    private String state;

    private String name;

    private String delflag;

    private String approveid;

    private Date updatetime;

    private String updatemender;

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

    public String getFenjianqu() {
        return fenjianqu;
    }

    public void setFenjianqu(String fenjianqu) {
        this.fenjianqu = fenjianqu == null ? null : fenjianqu.trim();
    }

    public String getJianqu() {
        return jianqu;
    }

    public void setJianqu(String jianqu) {
        this.jianqu = jianqu == null ? null : jianqu.trim();
    }

    public String getJianyu() {
        return jianyu;
    }

    public void setJianyu(String jianyu) {
        this.jianyu = jianyu == null ? null : jianyu.trim();
    }

    public String getJiangchengleixing() {
        return jiangchengleixing;
    }

    public void setJiangchengleixing(String jiangchengleixing) {
        this.jiangchengleixing = jiangchengleixing == null ? null : jiangchengleixing.trim();
    }

    public String getJiangchengleibie() {
        return jiangchengleibie;
    }

    public void setJiangchengleibie(String jiangchengleibie) {
        this.jiangchengleibie = jiangchengleibie == null ? null : jiangchengleibie.trim();
    }

    public String getFenshu() {
        return fenshu;
    }

    public void setFenshu(String fenshu) {
        this.fenshu = fenshu == null ? null : fenshu.trim();
    }

    public Date getFilltime() {
        return filltime;
    }

    public void setFilltime(Date filltime) {
        this.filltime = filltime;
    }

    public Date getCashtime() {
        return cashtime;
    }

    public void setCashtime(Date cashtime) {
        this.cashtime = cashtime;
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

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state == null ? null : state.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getDelflag() {
        return delflag;
    }

    public void setDelflag(String delflag) {
        this.delflag = delflag == null ? null : delflag.trim();
    }

    public String getApproveid() {
        return approveid;
    }

    public void setApproveid(String approveid) {
        this.approveid = approveid == null ? null : approveid.trim();
    }

    public Date getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(Date updatetime) {
        this.updatetime = updatetime;
    }

    public String getUpdatemender() {
        return updatemender;
    }

    public void setUpdatemender(String updatemender) {
        this.updatemender = updatemender == null ? null : updatemender.trim();
    }
}