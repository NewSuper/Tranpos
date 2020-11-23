package com.newsuper.t.juejinbao.ui.home.entity;

public class NewTaskEvent {

    private int tabPosition;

    public int getTabPosition() {
        return tabPosition;
    }

    public void setTabPosition(int tabPosition) {
        this.tabPosition = tabPosition;
    }

    public NewTaskEvent(int tabPosition) {
        this.tabPosition = tabPosition;
    }
}
