package com.sinog2c.model.system;

import java.util.Date;

public class SystemDictionary extends SystemDictionaryKey {
    private String dictype;

    private String cname;

    private String ccolname;

    private Date optime;

    public String getDictype() {
        return dictype;
    }

    public void setDictype(String dictype) {
        this.dictype = dictype == null ? null : dictype.trim();
    }

    public String getCname() {
        return cname;
    }

    public void setCname(String cname) {
        this.cname = cname == null ? null : cname.trim();
    }

    public String getCcolname() {
        return ccolname;
    }

    public void setCcolname(String ccolname) {
        this.ccolname = ccolname == null ? null : ccolname.trim();
    }

    public Date getOptime() {
        return optime;
    }

    public void setOptime(Date optime) {
        this.optime = optime;
    }
}