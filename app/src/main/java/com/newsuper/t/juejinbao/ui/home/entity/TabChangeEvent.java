package com.newsuper.t.juejinbao.ui.home.entity;

public class TabChangeEvent {
    private int tabPosition;

    public int getTabPosition() {
        return tabPosition;
    }

    public void setTabPosition(int tabPosition) {
        this.tabPosition = tabPosition;
    }

    public TabChangeEvent(int tabPosition) {
        this.tabPosition = tabPosition;
    }
}
