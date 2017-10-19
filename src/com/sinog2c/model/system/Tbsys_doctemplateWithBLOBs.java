package com.sinog2c.model.system;

import com.sinog2c.model.BlueOALogAnnotation;

@BlueOALogAnnotation(TableName="Tbsys_doctemplate",Description="公文模板信息")
public class Tbsys_doctemplateWithBLOBs extends Tbsys_doctemplate {
    private byte[] content;

    private byte[] thumbnail;

    private String content2;

    private String thumbnail2;

    public byte[] getContent() {
        return content;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }

    public byte[] getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(byte[] thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getContent2() {
        return content2;
    }

    public void setContent2(String content2) {
        this.content2 = content2 == null ? null : content2.trim();
    }

    public String getThumbnail2() {
        return thumbnail2;
    }

    public void setThumbnail2(String thumbnail2) {
        this.thumbnail2 = thumbnail2 == null ? null : thumbnail2.trim();
    }
}