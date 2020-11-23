package com.newsuper.t.juejinbao.bean;

/**
 * 选中后第二次点击
 * 用于更新页面等
 */
public class TabSelectedEvent {
    public int position;
    public String channelName;

    public TabSelectedEvent(int position, String name) {
        this.position = position;
        this.channelName = name;
    }
}
