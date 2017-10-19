package com.sinog2c.model.khjc;

public class KhjcCriminalMonthScoreBlSd {
    private String criminalid;

    private String score;

    private String loginfo;

    public String getCriminalid() {
        return criminalid;
    }

    public void setCriminalid(String criminalid) {
        this.criminalid = criminalid == null ? null : criminalid.trim();
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score == null ? null : score.trim();
    }

    public String getLoginfo() {
        return loginfo;
    }

    public void setLoginfo(String loginfo) {
        this.loginfo = loginfo == null ? null : loginfo.trim();
    }
}