package com.sinog2c.model.system;

import java.io.Serializable;
import java.util.Date;

public class SystemResource  implements Serializable,Cloneable {
	
	private static final long serialVersionUID = 1L;

	/**
	 * 是否选中. 额外设置的参数,不参与持久化
	 */
    private boolean checked;
    
    public boolean isChecked() {
		return checked;
	}
    public void setChecked(boolean checked) {
		this.checked = checked;
	}
    public void checkedIt() {
    	setChecked(true);
	}
    /**
     * 因为会有污染状态,所以需要克隆
     */
    @Override
    public SystemResource clone() throws CloneNotSupportedException {
    	return (SystemResource)super.clone();
    }
    
    private String resid;

    private String presid;

    private String name;
    
    private String resname;

    private Integer ismenu;

    private String srurl;

    private String showsite;

    private String openico;

    private String closeico;

    private String showico;

    private String title;

    private Integer sn;

    private String prompt;

    private String linktablename;

    private String linkcolumns;

    private Integer windowwidth;

    private Integer windowhight;

    private String orderbycon;

    private Integer rct;

    private String delflag;

    private String insertsql;

    private String updatesql;

    private String querysql;

    private Integer ismenuleaf;

    private Integer isresourcesleaf;

    private String text1;

    private String text2;

    private String text3;

    private String text4;

    private String text5;

    private String text6;

    private String text7;

    private String text8;

    private Integer x1;

    private Integer x2;

    private Integer y1;

    private Integer y2;

    private Integer isvisible;

    private String formid;

    private Integer restypeid;

    private String remark;

    private String opid;

    private Date optime;

    public String getResid() {
        return resid;
    }

    public void setResid(String resid) {
        this.resid = resid == null ? null : resid.trim();
    }

    public String getPresid() {
        return presid;
    }

    public void setPresid(String presid) {
        this.presid = presid == null ? null : presid.trim();
    }

    public String getResname() {
        return resname;
    }

    public void setResname(String resname) {
        this.resname = resname == null ? null : resname.trim();
    }
    
    public String getName() {
    	return name;
    }
    
    public void setName(String name) {
    	this.name = name == null ? null : name.trim();
    }

    public Integer getIsmenu() {
        return ismenu;
    }

    public void setIsmenu(Integer ismenu) {
        this.ismenu = ismenu;
    }

    public String getSrurl() {
        return srurl;
    }

    public void setSrurl(String srurl) {
        this.srurl = srurl == null ? null : srurl.trim();
    }

    public String getShowsite() {
        return showsite;
    }

    public void setShowsite(String showsite) {
        this.showsite = showsite == null ? null : showsite.trim();
    }

    public String getOpenico() {
        return openico;
    }

    public void setOpenico(String openico) {
        this.openico = openico == null ? null : openico.trim();
    }

    public String getCloseico() {
        return closeico;
    }

    public void setCloseico(String closeico) {
        this.closeico = closeico == null ? null : closeico.trim();
    }

    public String getShowico() {
        return showico;
    }

    public void setShowico(String showico) {
        this.showico = showico == null ? null : showico.trim();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public Integer getSn() {
        return sn;
    }

    public void setSn(Integer sn) {
        this.sn = sn;
    }

    public String getPrompt() {
        return prompt;
    }

    public void setPrompt(String prompt) {
        this.prompt = prompt == null ? null : prompt.trim();
    }

    public String getLinktablename() {
        return linktablename;
    }

    public void setLinktablename(String linktablename) {
        this.linktablename = linktablename == null ? null : linktablename.trim();
    }

    public String getLinkcolumns() {
        return linkcolumns;
    }

    public void setLinkcolumns(String linkcolumns) {
        this.linkcolumns = linkcolumns == null ? null : linkcolumns.trim();
    }

    public Integer getWindowwidth() {
        return windowwidth;
    }

    public void setWindowwidth(Integer windowwidth) {
        this.windowwidth = windowwidth;
    }

    public Integer getWindowhight() {
        return windowhight;
    }

    public void setWindowhight(Integer windowhight) {
        this.windowhight = windowhight;
    }

    public String getOrderbycon() {
        return orderbycon;
    }

    public void setOrderbycon(String orderbycon) {
        this.orderbycon = orderbycon == null ? null : orderbycon.trim();
    }

    public Integer getRct() {
        return rct;
    }

    public void setRct(Integer rct) {
        this.rct = rct;
    }

    public String getDelflag() {
        return delflag;
    }

    public void setDelflag(String delflag) {
        this.delflag = delflag == null ? null : delflag.trim();
    }

    public String getInsertsql() {
        return insertsql;
    }

    public void setInsertsql(String insertsql) {
        this.insertsql = insertsql == null ? null : insertsql.trim();
    }

    public String getUpdatesql() {
        return updatesql;
    }

    public void setUpdatesql(String updatesql) {
        this.updatesql = updatesql == null ? null : updatesql.trim();
    }

    public String getQuerysql() {
        return querysql;
    }

    public void setQuerysql(String querysql) {
        this.querysql = querysql == null ? null : querysql.trim();
    }

    public Integer getIsmenuleaf() {
        return ismenuleaf;
    }

    public void setIsmenuleaf(Integer ismenuleaf) {
        this.ismenuleaf = ismenuleaf;
    }

    public Integer getIsresourcesleaf() {
        return isresourcesleaf;
    }

    public void setIsresourcesleaf(Integer isresourcesleaf) {
        this.isresourcesleaf = isresourcesleaf;
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

    public String getText6() {
        return text6;
    }

    public void setText6(String text6) {
        this.text6 = text6 == null ? null : text6.trim();
    }

    public String getText7() {
        return text7;
    }

    public void setText7(String text7) {
        this.text7 = text7 == null ? null : text7.trim();
    }

    public String getText8() {
        return text8;
    }

    public void setText8(String text8) {
        this.text8 = text8 == null ? null : text8.trim();
    }

    public Integer getX1() {
        return x1;
    }

    public void setX1(Integer x1) {
        this.x1 = x1;
    }

    public Integer getX2() {
        return x2;
    }

    public void setX2(Integer x2) {
        this.x2 = x2;
    }

    public Integer getY1() {
        return y1;
    }

    public void setY1(Integer y1) {
        this.y1 = y1;
    }

    public Integer getY2() {
        return y2;
    }

    public void setY2(Integer y2) {
        this.y2 = y2;
    }

    public Integer getIsvisible() {
        return isvisible;
    }

    public void setIsvisible(Integer isvisible) {
        this.isvisible = isvisible;
    }

    public String getFormid() {
        return formid;
    }

    public void setFormid(String formid) {
        this.formid = formid == null ? null : formid.trim();
    }

    public Integer getRestypeid() {
        return restypeid;
    }

    public void setRestypeid(Integer restypeid) {
        this.restypeid = restypeid;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
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
    

	// 会污染数据
	public static void truncOtherData(SystemResource res){
		//
		if(null == res){
			return ;
		}
		//
		res.srurl=null;
		res.showsite=null;
		res.openico=null;
		res.closeico=null;
		res.showico=null;
		res.prompt=null;
		res.linktablename=null;
		res.linkcolumns=null;
		res.windowwidth=null;
		res.windowhight=null;
		res.orderbycon=null;
		res.rct=null;
		res.delflag=null;
		res.insertsql=null;
		res.updatesql=null;
		res.querysql=null;
		res.ismenuleaf=null;
		res.isresourcesleaf=null;
		res.text1=null;
		res.text2=null;
		res.text3=null;
		res.text4=null;
		res.text5=null;
		res.text6=null;
		res.text7=null;
		res.text8=null;
		res.x1=null;
		res.x2=null;
		res.y1=null;
		res.y2=null;
		res.isvisible=null;
		res.formid=null;
		res.opid=null;
		res.optime=null;
	}
}