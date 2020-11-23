package com.newsuper.t.juejinbao.ui.share.entity;

import com.newsuper.t.juejinbao.bean.Entity;

import java.util.List;
public class ShareEntity extends Entity {

    public static String TAG_INDEX = "index";
    public static String TAG_VIDEO = "video";
    public static String TAG_TASK  = "task";

    public static String WAY_QQ     = "qq";
    public static String WAY_WECHAT = "wechat";
    public static String WAY_FRIEND = "friend";
    public static String WAY_WEIBO  = "weibo";
    /**
     * 锁粉神器
     */
    public static String WAY_SUOFEN  = "system";




    /**
     *   防封神器
     */
    public static String WAY_WECHAT_FRIEND_FANGFENG = "fangfeng";

    /**
     * 名称
     */
    public String name;
    public String way;
    public String tag = "推荐";

    /**
     * logo
     */
    public int logo;
    /**
     * 是否含有推荐
     */
    public boolean withDot;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getWay() {
        return way;
    }

    public void setWay(String way) {
        this.way = way;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public int getLogo() {
        return logo;
    }

    public void setLogo(int logo) {
        this.logo = logo;
    }

    public boolean isWithDot() {
        return withDot;
    }

    public void setWithDot(boolean withDot) {
        this.withDot = withDot;
    }

    public ShareEntity() {
    }

    public ShareEntity(String name, String way, String tag, int logo, boolean withDot) {
        this.name = name;
        this.way = way;
        this.tag = tag;
        this.logo = logo;
        this.withDot = withDot;
    }
}
