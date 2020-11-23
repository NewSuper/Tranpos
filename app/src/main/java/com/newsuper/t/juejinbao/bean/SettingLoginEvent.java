package com.newsuper.t.juejinbao.bean;

public class SettingLoginEvent {
    //1 普通退出 2 单点登录被踢下线
    private int eventType;

    public int getEventType() {
        return eventType;
    }

    public void setEventType(int eventType) {
        this.eventType = eventType;
    }

    public SettingLoginEvent(int eventType) {
        this.eventType = eventType;
    }

    public SettingLoginEvent() {

    }
}
