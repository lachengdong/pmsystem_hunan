package com.sinog2c.model.commutationParole;

import java.util.Date;

public class TbxfScreeningProperty extends TbxfScreeningPropertyKey {
    private String scontent;

    private String econtent;

    private Integer int1;

    private Integer int2;

    private Integer int3;

    private String tmp1;

    private String tmp2;

    private String tmp3;

    private Date optime;

    public String getScontent() {
        return scontent;
    }

    public void setScontent(String scontent) {
        this.scontent = scontent == null ? null : scontent.trim();
    }

    public String getEcontent() {
        return econtent;
    }

    public void setEcontent(String econtent) {
        this.econtent = econtent == null ? null : econtent.trim();
    }

    public Integer getInt1() {
        return int1;
    }

    public void setInt1(Integer int1) {
        this.int1 = int1;
    }

    public Integer getInt2() {
        return int2;
    }

    public void setInt2(Integer int2) {
        this.int2 = int2;
    }

    public Integer getInt3() {
        return int3;
    }

    public void setInt3(Integer int3) {
        this.int3 = int3;
    }

    public String getTmp1() {
        return tmp1;
    }

    public void setTmp1(String tmp1) {
        this.tmp1 = tmp1 == null ? null : tmp1.trim();
    }

    public String getTmp2() {
        return tmp2;
    }

    public void setTmp2(String tmp2) {
        this.tmp2 = tmp2 == null ? null : tmp2.trim();
    }

    public String getTmp3() {
        return tmp3;
    }

    public void setTmp3(String tmp3) {
        this.tmp3 = tmp3 == null ? null : tmp3.trim();
    }

    public Date getOptime() {
        return optime;
    }

    public void setOptime(Date optime) {
        this.optime = optime;
    }
}