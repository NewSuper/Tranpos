package com.newsuper.t.juejinbao.ui.song.entity;

import com.newsuper.t.juejinbao.bean.Entity;

import java.util.List;

public class CategorysBean extends Entity {
    /**
     * thumbnail : https://dss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=1188387633,958216909&fm=26&gp=0.jpg
     * title : 经典歌曲
     * desc : 8090都在听
     * play_count : 5151
     * alias : 333
     */

    private String thumbnail;
    private String title;
    private String cid;
    private String desc;
    private String play_count;
    private String alias;

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getPlay_count() {
        return play_count;
    }

    public void setPlay_count(String play_count) {
        this.play_count = play_count;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }
}
