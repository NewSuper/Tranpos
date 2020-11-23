package com.newsuper.t.juejinbao.bean;


public class VideoPlayEvent {
    private int type;
    private int position;

    public int getPosition() {
        return position;
    }

    public VideoPlayEvent(int type, int position) {
        this.type = type;
        this.position = position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public VideoPlayEvent(int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
