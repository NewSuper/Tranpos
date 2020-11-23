package com.newsuper.t.juejinbao.bean;

public class SavePictureEvent {
    private int position;

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public SavePictureEvent(int position) {
        this.position = position;
    }
}
