package com.newsuper.t.juejinbao.bean;

import com.ys.network.base.Entity;


public class ReadArticleLeaveEvent extends Entity{
    private long startTime;
    private String id;

    public ReadArticleLeaveEvent(long startTime, String id) {
        this.startTime = startTime;
        this.id = id;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
