package com.newsuper.t.juejinbao.ui.song.Event;

public class MusicErrorEvent {

    private int errorCode;

    public MusicErrorEvent(int errorCode) {
        this.errorCode = errorCode;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }
}
