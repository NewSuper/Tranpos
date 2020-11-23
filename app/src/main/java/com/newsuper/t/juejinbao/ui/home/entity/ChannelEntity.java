package com.newsuper.t.juejinbao.ui.home.entity;

import com.newsuper.t.juejinbao.bean.Entity;

import java.util.List;

public class ChannelEntity extends Entity {
    //推荐
    public static final int ID_RECOMMEND = 0;
    //热门
    public static final int ID_HOT = 1;
    /** 掘金宝频道id */
    public static final int ID_JJB = 101;


    private int id;
    private String name;

    public ChannelEntity() {
    }

    public ChannelEntity(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "ChannelEntity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
