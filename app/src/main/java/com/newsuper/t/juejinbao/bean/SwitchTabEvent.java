package com.newsuper.t.juejinbao.bean;


public class SwitchTabEvent {
    public static final String HOME = "home";
    public static final String MOVIE = "movie";
    public static final String ZCZF = "zczf";
    public static final String TASK = "task";
    public static final String ME = "mine";

    public String tab; //是否领成功
    public boolean isNewTaskClick = false;// 是否是点击新手任务跳转
    public boolean isReceiveEggs = false;// 是否是点击领鸡蛋跳转

    public SwitchTabEvent(String tab) {
        this.tab = tab;
    }

    public SwitchTabEvent(String tab, boolean isNewTaskClick) {
        this.tab = tab;
        this.isNewTaskClick = isNewTaskClick;
    }

    public SwitchTabEvent(String tab, boolean isNewTaskClick, boolean isReceiveEggs) {
        this.tab = tab;
        this.isNewTaskClick = isNewTaskClick;
        this.isReceiveEggs = isReceiveEggs;
    }
}
