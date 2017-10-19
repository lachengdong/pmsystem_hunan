package com.sinog2c.model.solution;

import java.util.Date;

public class FormSqlGroup implements Cloneable,Comparable<FormSqlGroup>{
    private String sqlgroupid;

    private String solutionid;

    private String detailid;

    private Integer sqlgrouptype;

    private String sqlgroupname;

    private String testsql;

    private String mainsql;

    private String standbysql;

    private String delsql;

    private String resultkey;

    private Integer ismaingroup;

    private Integer startorder;

    private Integer cycleflag;

    private Integer delflag;

    private String remark;

    private Date createtime;

    private Date updatetime;

    private String createmender;

    private String updatemender;

    private Integer signflag;

    private Integer int1;

    private Integer int2;

    private Integer int3;

    private Integer int4;

    private Integer int5;

    private String text1;
    
    // 更新Doc附加条件
    private String text2;

    private String text3;

    private String text4;

    private String text5;

    public String getSqlgroupid() {
        return sqlgroupid;
    }

    public void setSqlgroupid(String sqlgroupid) {
        this.sqlgroupid = sqlgroupid == null ? null : sqlgroupid.trim();
    }

    public String getSolutionid() {
        return solutionid;
    }

    public void setSolutionid(String solutionid) {
        this.solutionid = solutionid == null ? null : solutionid.trim();
    }

    public String getDetailid() {
        return detailid;
    }

    public void setDetailid(String detailid) {
        this.detailid = detailid == null ? null : detailid.trim();
    }

    public Integer getSqlgrouptype() {
        return sqlgrouptype;
    }

    public void setSqlgrouptype(Integer sqlgrouptype) {
        this.sqlgrouptype = sqlgrouptype;
    }

    public String getSqlgroupname() {
        return sqlgroupname;
    }

    public void setSqlgroupname(String sqlgroupname) {
        this.sqlgroupname = sqlgroupname == null ? null : sqlgroupname.trim();
    }

    public String getTestsql() {
        return testsql;
    }

    public void setTestsql(String testsql) {
        this.testsql = testsql == null ? null : testsql.trim();
    }

    public String getMainsql() {
        return mainsql;
    }

    public void setMainsql(String mainsql) {
        this.mainsql = mainsql == null ? null : mainsql.trim();
    }

    public String getStandbysql() {
        return standbysql;
    }

    public void setStandbysql(String standbysql) {
        this.standbysql = standbysql == null ? null : standbysql.trim();
    }

    public String getDelsql() {
        return delsql;
    }

    public void setDelsql(String delsql) {
        this.delsql = delsql == null ? null : delsql.trim();
    }

    public String getResultkey() {
        return resultkey;
    }

    public void setResultkey(String resultkey) {
        this.resultkey = resultkey == null ? null : resultkey.trim();
    }

    public Integer getIsmaingroup() {
        return ismaingroup;
    }

    public void setIsmaingroup(Integer ismaingroup) {
        this.ismaingroup = ismaingroup;
    }

    public Integer getStartorder() {
        return startorder;
    }

    public void setStartorder(Integer startorder) {
        this.startorder = startorder;
    }

    public Integer getCycleflag() {
        return cycleflag;
    }

    public void setCycleflag(Integer cycleflag) {
        this.cycleflag = cycleflag;
    }

    public Integer getDelflag() {
        return delflag;
    }

    public void setDelflag(Integer delflag) {
        this.delflag = delflag;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public Date getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(Date updatetime) {
        this.updatetime = updatetime;
    }

    public String getCreatemender() {
        return createmender;
    }

    public void setCreatemender(String createmender) {
        this.createmender = createmender == null ? null : createmender.trim();
    }

    public String getUpdatemender() {
        return updatemender;
    }

    public void setUpdatemender(String updatemender) {
        this.updatemender = updatemender == null ? null : updatemender.trim();
    }

    public Integer getSignflag() {
        return signflag;
    }

    public void setSignflag(Integer signflag) {
        this.signflag = signflag;
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

    public Integer getInt4() {
        return int4;
    }

    public void setInt4(Integer int4) {
        this.int4 = int4;
    }

    public Integer getInt5() {
        return int5;
    }

    public void setInt5(Integer int5) {
        this.int5 = int5;
    }

    public String getText1() {
        return text1;
    }

    public void setText1(String text1) {
        this.text1 = text1 == null ? null : text1.trim();
    }

    public String getText2() {
        return text2;
    }

    public void setText2(String text2) {
        this.text2 = text2 == null ? null : text2.trim();
    }

    public String getText3() {
        return text3;
    }

    public void setText3(String text3) {
        this.text3 = text3 == null ? null : text3.trim();
    }

    public String getText4() {
        return text4;
    }

    public void setText4(String text4) {
        this.text4 = text4 == null ? null : text4.trim();
    }

    public String getText5() {
        return text5;
    }

    public void setText5(String text5) {
        this.text5 = text5 == null ? null : text5.trim();
    }
    
    @Override
    public FormSqlGroup clone() throws CloneNotSupportedException {
    	return (FormSqlGroup)super.clone();
    }

	@Override
	public int compareTo(FormSqlGroup other) {
		if(null == other){
			return 1;
		}
		Integer startorder2 = other.getStartorder();
		//
		if(null == startorder){
			return -1;
		}
		if(null == startorder2){
			return 1;
		}
		//
		return startorder - startorder2;
	}

	@Override
	public String toString() {
		return "FormSqlGroup [createmender=" + createmender + ", createtime=" + createtime + ", cycleflag=" + cycleflag
				+ ", delflag=" + delflag + ", delsql=" + delsql + ", detailid=" + detailid + ", int1=" + int1
				+ ", int2=" + int2 + ", int3=" + int3 + ", int4=" + int4 + ", int5=" + int5 + ", ismaingroup="
				+ ismaingroup + ", mainsql=" + mainsql + ", remark=" + remark + ", resultkey=" + resultkey
				+ ", signflag=" + signflag + ", solutionid=" + solutionid + ", sqlgroupid=" + sqlgroupid
				+ ", sqlgroupname=" + sqlgroupname + ", sqlgrouptype=" + sqlgrouptype + ", standbysql=" + standbysql
				+ ", startorder=" + startorder + ", testsql=" + testsql + ", text1=" + text1 + ", text2=" + text2
				+ ", text3=" + text3 + ", text4=" + text4 + ", text5=" + text5 + ", updatemender=" + updatemender
				+ ", updatetime=" + updatetime + "]";
	}
}