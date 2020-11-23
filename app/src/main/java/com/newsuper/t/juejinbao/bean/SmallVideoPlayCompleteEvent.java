package com.newsuper.t.juejinbao.bean;

public class SmallVideoPlayCompleteEvent {
    private String type;

    public SmallVideoPlayCompleteEvent(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
