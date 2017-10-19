package com.sinog2c.model.chat;

import java.util.Date;

public class iMessageRelation {
    private Long id;

    private String user1;

    private String user2;

    private Long msgId;

    private Date updateTime;

    private Long counter1;

    private Long counter2;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUser1() {
        return user1;
    }

    public void setUser1(String user1) {
        this.user1 = user1 == null ? null : user1.trim();
    }

    public String getUser2() {
        return user2;
    }

    public void setUser2(String user2) {
        this.user2 = user2 == null ? null : user2.trim();
    }

    public Long getMsgId() {
        return msgId;
    }

    public void setMsgId(Long msgId) {
        this.msgId = msgId;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Long getCounter1() {
        return counter1;
    }

    public void setCounter1(Long counter1) {
        this.counter1 = counter1;
    }

    public Long getCounter2() {
        return counter2;
    }

    public void setCounter2(Long counter2) {
        this.counter2 = counter2;
    }
}