package com.sinog2c.model.khjc;

import java.util.Date;

public class KhjcCriminalJiangChengSd {
    private String id;

    private String fenjianqu;

    private String jianqu;

    private String jianyu;

    private String jiangchengleixing;

    private String jiangchengleibie;

    private Date starttime;

    private Date endtime;

    private Date createtime;

    private String createmender;

    private Date updatetime;

    private String updatemender;

    private String approveid;

    private String delflag;

    private String jizifenzinian;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
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

    public Date getStarttime() {
        return starttime;
    }

    public void setStarttime(Date starttime) {
        this.starttime = starttime;
    }

    public Date getEndtime() {
        return endtime;
    }

    public void setEndtime(Date endtime) {
        this.endtime = endtime;
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

    public String getApproveid() {
        return approveid;
    }

    public void setApproveid(String approveid) {
        this.approveid = approveid == null ? null : approveid.trim();
    }

    public String getDelflag() {
        return delflag;
    }

    public void setDelflag(String delflag) {
        this.delflag = delflag == null ? null : delflag.trim();
    }

    public String getJizifenzinian() {
        return jizifenzinian;
    }

    public void setJizifenzinian(String jizifenzinian) {
        this.jizifenzinian = jizifenzinian == null ? null : jizifenzinian.trim();
    }
}