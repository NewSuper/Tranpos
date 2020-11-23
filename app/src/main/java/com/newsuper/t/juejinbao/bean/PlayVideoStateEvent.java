package com.newsuper.t.juejinbao.bean;


public class PlayVideoStateEvent {
    private boolean isStop;

    public PlayVideoStateEvent(boolean isStop) {
        this.isStop = isStop;
    }

    public boolean isStop() {
        return isStop;
    }

    public void setStop(boolean stop) {
        isStop = stop;
    }
}
