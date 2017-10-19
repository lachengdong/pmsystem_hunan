package com.sinog2c.model.khjc;

public class KhjcJiYaoMingXi {
    private String id;

    private String sendto;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getSendto() {
        return sendto;
    }

    public void setSendto(String sendto) {
        this.sendto = sendto == null ? null : sendto.trim();
    }
}