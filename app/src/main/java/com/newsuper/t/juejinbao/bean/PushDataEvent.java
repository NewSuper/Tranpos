package com.newsuper.t.juejinbao.bean;


public class PushDataEvent {
    private int pushType = 0;
    private String pushMessge;

    public PushDataEvent(int pushType, String pushMessge) {
        this.pushType = pushType;
        this.pushMessge = pushMessge;
    }

    public int getPushType() {
        return pushType;
    }

    public void setPushType(int pushType) {
        this.pushType = pushType;
    }

    public String getPushMessge() {
        return pushMessge;
    }

    public void setPushMessge(String pushMessge) {
        this.pushMessge = pushMessge;
    }
}
