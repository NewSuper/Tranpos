package com.newsuper.t.juejinbao.ui.home.entity;


public enum HomeListType {
    //{"1":"单图","2":"三图","3":"大图","4":"无图","6":"小视频","7":"图集","8":"短视频"}

    SINGLE_PIC("单图", 1),
    GROUP_PIC("三图", 2),
    LARGE_PIC("大图", 3),
    NULL_PIC("无图", 4),
    SMALL_VIDEO("小视频", 6),
    SHORT_VIDEO("短视频", 8),
    RECOMMED_SHORT_VIDEO("推荐短视频", 9);

    // 成员变量
    private String name;
    private int index;

    // 构造方法
    HomeListType(String name, int index) {
        this.name = name;
        this.index = index;
    }

    // 普通方法
    public static String getName(int index) {
        for (HomeListType c : HomeListType.values()) {
            if (c.getIndex() == index) {
                return c.name;
            }
        }
        return null;
    }

    // get set 方法
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }
}
