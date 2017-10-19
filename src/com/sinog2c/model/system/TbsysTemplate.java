package com.sinog2c.model.system;

import java.util.Date;

public class TbsysTemplate {
    private String tempid;

    private String functionname;

    private String tempname;

    private Short delflag;

    private Short type;
    
	private Integer findid;
    
    private String remark;

    private String departid;

    private Date optime;

    private String opid;

    private String content;
    
    private String temptype;
    
    private String generaltype;
     
    private String uneditedfields;
    
    private int sn;
    
    //start add by blue_lv 2015-07-15
    private String keyfields;

	private String gridheaders1;

	private String gridheaders2;

	private String mainsqltemp;

	private String mainsqltemp2;

	private String keyfieldsTemp;

	private String maincountsql;
	//end add by blue_lv 2015-07-15
    public String getTempid() {
        return tempid;
    }

    public void setTempid(String tempid) {
        this.tempid = tempid == null ? null : tempid.trim();
    }

    public String getFunctionname() {
        return functionname;
    }

    public void setFunctionname(String functionname) {
        this.functionname = functionname == null ? null : functionname.trim();
    }

    public String getTempname() {
        return tempname;
    }

    public void setTempname(String tempname) {
        this.tempname = tempname == null ? null : tempname.trim();
    }

    public Short getDelflag() {
        return delflag;
    }

    public void setDelflag(Short delflag) {
        this.delflag = delflag;
    }

    public Short getType() {
		return type;
	}

	public void setType(Short type) {
		this.type = type;
	}

	public Integer getFindid() {
		return findid;
	}

	public void setFindid(Integer findid) {
		this.findid = findid;
	}
    
    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public String getDepartid() {
        return departid;
    }

    public void setDepartid(String departid) {
        this.departid = departid == null ? null : departid.trim();
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

	public String getTemptype() {
		return temptype;
	}

	public void setTemptype(String temptype) {
		this.temptype = temptype;
	}

	public String getUneditedfields() {
		return uneditedfields;
	}

	public void setUneditedfields(String uneditedfields) {
		this.uneditedfields = uneditedfields;
	}
	
	
	public String getGeneraltype() {
		return generaltype;
	}

	public void setGeneraltype(String generaltype) {
		this.generaltype = generaltype;
	}

	public int getSn() {
		return sn;
	}

	public void setSn(int sn) {
		this.sn = sn;
	}
	
	//start add by blue_lv 2015-07-15
	public String getKeyfields() {
		return keyfields;
	}

	public void setKeyfields(String keyfields) {
		this.keyfields = keyfields;
	}

	public String getGridheaders1() {
		return gridheaders1;
	}

	public void setGridheaders1(String gridheaders1) {
		this.gridheaders1 = gridheaders1;
	}

	public String getGridheaders2() {
		return gridheaders2;
	}

	public void setGridheaders2(String gridheaders2) {
		this.gridheaders2 = gridheaders2;
	}

	public String getMainsqltemp() {
		return mainsqltemp;
	}

	public void setMainsqltemp(String mainsqltemp) {
		this.mainsqltemp = mainsqltemp;
	}

	public String getMainsqltemp2() {
		return mainsqltemp2;
	}

	public void setMainsqltemp2(String mainsqltemp2) {
		this.mainsqltemp2 = mainsqltemp2;
	}

	public String getKeyfieldsTemp() {
		return keyfieldsTemp;
	}

	public void setKeyfieldsTemp(String keyfieldsTemp) {
		this.keyfieldsTemp = keyfieldsTemp;
	}

	public String getMaincountsql() {
		return maincountsql;
	}

	public void setMaincountsql(String maincountsql) {
		this.maincountsql = maincountsql;
	}
	
	//end add by blue_lv 2015-07-15
}