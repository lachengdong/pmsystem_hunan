package com.sinog2c.model.attachment;

public class Attachment_position {
    private Long id;

    private String posName;

    private String posPath;

    private String isActive;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPosName() {
        return posName;
    }

    public void setPosName(String posName) {
        this.posName = posName == null ? null : posName.trim();
    }

    public String getPosPath() {
        return posPath;
    }

    public void setPosPath(String posPath) {
        this.posPath = posPath == null ? null : posPath.trim();
    }

    public String getIsActive() {
        return isActive;
    }

    public void setIsActive(String isActive) {
        this.isActive = isActive == null ? null : isActive.trim();
    }
}